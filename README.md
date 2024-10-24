# RPC Demo for Python and Kotlin

Implementation for an application test task.

## Overview

This repository includes two packages:

- [`server/`](./server): An RPC Demo Server written in Python.
- [`client/`](./client): An RPC Demo Client written in Kotlin.

Please refer to the readmes in each folder for installation and usage instructions. However, to run the complete client-server combination after the installation, you can run here:

```
( cd client && ./gradlew run -PserverPath=../server/run.sh )
```

## Continous Integration

If you do not want to set up everything manually, you can instead read the CI logs:

[![CI Pipeline](https://github.com/LinqLover/python-kotlin-rpc-demo/actions/workflows/ci.yml/badge.svg)](https://github.com/LinqLover/python-kotlin-rpc-demo/actions/workflows/ci.yml) 

- `server-tests`: Non-exhaustive integration tests for the server
- `client-tests`: Non-exhaustive unit tests for the client
- `lint-server`: PEP8 coding style checking for server
- `integration-test`: Smoke tests for running both server and client together in different configurations.

See [`.github/workflows/ci.yml`](./.github/workflows/ci.yml) for more details.
