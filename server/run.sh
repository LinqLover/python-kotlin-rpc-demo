#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"
python -m rpcdemo_server "${@}"
