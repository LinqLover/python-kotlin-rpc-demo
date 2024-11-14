package rpcdemo.client

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.withTimeout
import java.io.BufferedReader
import java.io.PrintWriter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

private val logger = KotlinLogging.logger {}

/**
 * Generic client that runs a server process and calls procedures via stdin/stdout.
 *
 * @param serverPath Path to an executable server file.
 */
class Client(
    private val serverPath: String,
) {
    companion object {
        /**
         * Runs a server process and use a client to interact with it through the block argument.
         *
         * @param T The return value of the block.
         * @param serverPath Path to an executable server file.
         */
        suspend fun <T> runProcess(
            serverPath: String,
            block: suspend (Client) -> T,
        ): T {
            val client = Client(serverPath)
            return client.runProcess { block(client) }
        }
    }

    private var process: Process? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null

    /**
     * Runs a server process and interacts with it through the block argument.
     *
     * @param T The return value of the block.
     */
    suspend fun <T> runProcess(block: suspend CoroutineScope.() -> T): T =
        coroutineScope {
            val processBuilder = ProcessBuilder(serverPath)
            if (logger.isDebugEnabled()) {
                processBuilder.command(processBuilder.command() + "--log-level=DEBUG")
            }
            process =
                withContext(Dispatchers.IO) {
                    processBuilder.start()
                }
            if (logger.isDebugEnabled()) {
                // manually forward stderr without buffering to synchronize with our own logs
                launch {
                    process!!.errorStream.reader().use { reader ->
                        reader.forEachLine { logger.debug { "server: $it" } }
                    }
                }
            }

            try {
                process!!.inputStream.bufferedReader().use { thisReader ->
                    reader = thisReader
                    try {
                        PrintWriter(process!!.outputStream, true).use { thisWriter ->
                            writer = thisWriter
                            try {
                                this@coroutineScope.block()
                            } finally {
                                writer = null
                            }
                        }
                    } finally {
                        reader = null
                    }
                }
            } finally {
                withContext(Dispatchers.IO) {
                    process!!.waitFor()
                }
                if (process!!.isAlive) {
                    logger.warn { "Server should have exited, killing it now" }
                    process!!.destroy()
                }
                process = null
            }
        }

    /**
     * Sends a command to the server without any timeout.
     *
     * @param command The command string to send.
     * @throws IllegalStateException If the process is not initialized or has died.
     */
    fun exec(command: String) {
        if (process == null) {
            throw IllegalStateException("Process is not initialized")
        }
        if (!process!!.isAlive) {
            throw IllegalStateException("Process has died")
        }
        logger.debug { "Sending command: $command" }
        writer!!.println(command)
    }

    /**
     * Sends a command to the server and awaits a response with a specified timeout.
     *
     * @param command The command string to send.
     * @param timeout The timeout duration.
     * @return The response string from the server.
     * @throws kotlinx.coroutines.TimeoutCancellationException If a timeout occurs or reading the response fails.
     */
    suspend fun call(
        command: String,
        timeout: Duration = 5.seconds,
    ): String {
        exec(command)

        return withTimeout(timeout.toJavaDuration()) {
            withContext(Dispatchers.IO) {
                // Do not block main thread
                reader!!.readLine()
            }
        }.also { response ->
            logger.debug { "Received response: $response" }
        }
    }
}
