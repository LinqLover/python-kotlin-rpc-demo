# rpc_demo_client

## Requirements

- Java 21, Kotlin 2.0, Gradle 8.10
- A compatible [`rpc_demo_server`](../rpc_demo_server)

## Running

```
./gradlew run
```

This assumes that the rpc_demo_server is located in the parent directory.

Optional arguments:

```
./gradlew run [-PserverPath=path/to/server] [-PlogLevel=SEVERE|WARNING|DEBUG]
```

On Windows, you could use `gradlew.bat` instead and provide a path to an executable file or script (the current implementation of the `rpc_demo_server` does not yet offer such a script).

## Testing

```
./gradlew test
```

## Package Structure

- [`app/build.gradle.kts`](./app/build.gradle.kts): Build configuration for dependencies and command line arguments.
- [`app/src/main/kotlin/rpc_demo/client/`](./app/src/main/kotlin/rpc_demo/client/):
  - [`Client.kt`](./app/src/main/kotlin/rpc_demo/client/Client.kt): Runs the server and calls procedures.
  - [`Controller.kt`](./app/src/main/kotlin/rpc_demo/client/Controller.kt): Business logic for retrieving and analyzing random numbers; main entry point.
- [`app/test/kotlin/rpc_demo/client/`](./app/test/kotlin/rpc_demo/client/): Non-exhaustive unit tests.
