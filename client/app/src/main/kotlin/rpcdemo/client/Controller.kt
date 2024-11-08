package rpcdemo.client

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException
import java.lang.NumberFormatException
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

/**
 * A controller that uses a client to retrieve and analyze random numbers.
 */
class Controller(
    private val client: Client,
) {
    private var success = false

    /**
     * Run the business logic.
     *
     * @return A boolean indicating whether the server behaved as expected.
     */
    suspend fun run(): Boolean {
        success = true
        var numbers: List<Int>
        try {
            success = success and greet()
            numbers = getRandomNumbers(100)
        } finally {
            shutDown()
        }

        numbers = numbers.sorted()
        numbers.forEach { number -> println(number) }

        val median = numbers.medianOfPresorted()
        val average = numbers.average()
        println("Median: $median")
        println("Average: $average")

        return success
    }

    /**
     * @return A boolean indicating whether the greeting handshake was performed correctly.
     */
    private suspend fun greet(): Boolean {
        val response = client.call("Hi")
        return (response == "Hi").also { it ->
            if (!it) {
                logger.warn { "Greeting handshake with server failed! Continuing anyway..." }
            }
        }
    }

    private suspend fun getRandomNumbers(number: Int): List<Int> =
        List(number) {
            val result = client.call("GetRandom")
            try {
                result.toInt()
            } catch (exception: NumberFormatException) {
                throw IllegalStateException("Server returned invalid data: $result")
            }
        }

    private fun shutDown() {
        client.exec("Shutdown")
    }
}

suspend fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Usage: server path/to/server")
        exitProcess(1)
    }

    val serverPath = args[0]

    try {
        var success =
            Client.runProcess(serverPath) { client ->
                Controller(client).run()
            }
        if (!success) {
            println("Server behaved unexpected")
            exitProcess(1)
        }
    } catch (exception: IOException) {
        logger.error { "Could not run server: $exception" }
        exitProcess(2)
    } catch (exception: IllegalStateException) {
        logger.error { "Unexpected server behavior: $exception" }
        exitProcess(3)
    }
}

/**
 * Computes the median of a list of integers. ASSUMES the list is already sorted!
 *
 * @return The median as a Double.
 * @throws IllegalArgumentException If the list is empty.
 */
fun List<Int>.medianOfPresorted(): Double {
    if (this.isEmpty()) {
        throw IllegalArgumentException("Cannot compute median of an empty list.")
    }

    val sortedList = this.sorted()
    val size = sortedList.size
    return if (size % 2 == 1) {
        // Odd number of elements, return the middle one
        sortedList[size / 2].toDouble()
    } else {
        // Even number of elements, return the average of the two middle ones
        (sortedList[size / 2] + sortedList[size / 2 - 1]) / 2.0
    }
}
