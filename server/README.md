# rpcdemo_server

## Setup

System requirements: Python 3.12, Linux/Bash (works on Ubuntu 22.04).

- Get pyenv.
- `pyenv install`
  - If the required Python version is not installed, you may need to install a newer version of pyenv first.
- `pip install -r requirements.txt`

## Running (standalone)

Minimal invocation (will read commands from stdin):

```bash
./run.sh
```

Full command line interface:

```
usage: ./run.sh [-h] [--log-level {CRITICAL,FATAL,ERROR,WARN,WARNING,INFO,DEBUG,NOTSET}] [--min-value MIN_VALUE] [--max-value MAX_VALUE] [--seed SEED]

RPC Demo Server

options:
  -h, --help            show this help message and exit
  --log-level {CRITICAL,FATAL,ERROR,WARN,WARNING,INFO,DEBUG,NOTSET}
  --min-value MIN_VALUE
  --max-value MAX_VALUE
  --seed SEED
```

On Windows, you can run `python -m rpcdemo_server` manually instead.

## Testing

```
python -m unittest tests/test*.py
```

## Linting

```
python -m flake8
```

## Package Structure

- [`rpcdemo_server/`](./rpcdemo_server/):
  - [`__main__.py`](./rpcdemo_server/__main__.py): CLI entry point.
  - [`framework.py`](./rpcdemo_server/framework.py): General logic for servers that can expose procedures.
  - [`random_server.py`](./rpcdemo_server/random_server.py): Server that generates pseudo-random numbers.
  - [`simple.py`](./rpcdemo_server/simple.py): Alternative minimal implementation of the random server without any supportability concerns.
- [`tests/`](./tests/): Non-exhaustive integration tests.
