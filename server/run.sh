#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"
python -m rpc_demo_server "${@}"
