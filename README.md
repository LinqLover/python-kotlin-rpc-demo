# RPC Demo for Python and Kotlin

Implementation for an application test task.

This repository includes two packages:

- [`server/`](./server): An RPC Demo Server written in Python.
- [`client/`](./client): An RPC Demo Client written in Kotlin.

Please refer to the readmes in each folder for installation and usage instructions. However, to run the complete client-server combination after the installation, you can run here:

```
( cd client && ./gradlew run -PserverPath=../server/run.sh )
```
