#!/usr/bin/env python3.12
"""
This is a minimal implementation of the rpcdemo_server that favors simplicity
over advanced concerns such as extensibility, robustness, and observability.
"""
from random import randint


def main():
    while True:
        command = input()
        match command:
            case 'Hi':
                print('Hi', flush=True)
            case 'GetRandom':
                rand = randint(0, 255)
                print(rand, flush=True)
            case 'Shutdown':
                break
            case _:
                pass


if __name__ == '__main__':
    main()
