import argparse
import logging
from nameof import nameof

from .random_server import RandomServer


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="RPC Demo Server")
    parser.add_argument('--log-level',
                        choices=logging.getLevelNamesMapping().keys(),
                        default=nameof(logging.CRITICAL))
    parser.add_argument('--min-value', type=int, default=0)
    parser.add_argument('--max-value', type=int, default=255)
    parser.add_argument('--seed', type=int)
    args = parser.parse_args()
    logging.basicConfig(level=args.log_level)

    server = RandomServer(args.seed)
    server.min_value = args.min_value
    server.max_value = args.max_value
    server.run_stdio()
