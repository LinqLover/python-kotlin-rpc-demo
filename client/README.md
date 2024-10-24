# rpcdemo_client

## Requirements

- Java 21, Kotlin 2.0, Gradle 8.10
- A compatible [`rpcdemo_server`](../rpcdemo_server)

## Running

```
./gradlew run
```

This assumes that the `rpcdemo_server` is located in the parent directory.

Optional arguments:

```
./gradlew run [-PserverPath=path/to/server] [-PlogLevel=SEVERE|WARNING|DEBUG]
```

On Windows, you could use `gradlew.bat` instead and provide a path to an executable file or script (the current implementation of the `rpcdemo_server` does not yet offer such a script).

## Testing

```
./gradlew test
```

## Linting

Get [ktlint](https://pinterest.github.io/ktlint/latest/).

## Package Structure

- [`app/build.gradle.kts`](./app/build.gradle.kts): Build configuration for dependencies and command line arguments.
- [`app/src/main/kotlin/rpcdemo/client/`](./app/src/main/kotlin/rpcdemo/client/):
  - [`Client.kt`](./app/src/main/kotlin/rpcdemo/client/Client.kt): Runs the server and calls procedures.
  - [`Controller.kt`](./app/src/main/kotlin/rpcdemo/client/Controller.kt): Business logic for retrieving and analyzing random numbers; main entry point.
- [`app/src/main/resources/logback.xml`](./app/src/main/resources/logback.xml): Logging config.
- [`app/test/kotlin/rpcdemo/client/`](./app/test/kotlin/rpcdemo/client/): Non-exhaustive unit tests.
