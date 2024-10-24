plugins {
    alias(libs.plugins.kotlin.jvm)

    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.8.0") // for Kotlin test DSL
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "rpcdemo.client.ControllerKt"
}

tasks.named<JavaExec>("run") {
    workingDir = project.rootDir

    if (project.hasProperty("logLevel")) {
        systemProperty("LOG_LEVEL", project.property("logLevel").toString())
    }

    var serverPath = "../server/run.sh"
    if (project.hasProperty("serverPath")) {
        serverPath = project.property("serverPath").toString()
    }
    args = listOf(serverPath)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
