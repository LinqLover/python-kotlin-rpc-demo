import os
from random import Random
from parameterized import parameterized
import subprocess
from unittest import TestCase


# constants for tests
MIN_VALUE = 0
MAX_VALUE = 1000
SEED = 123

_random = Random(SEED)
RANDOM_VALUES = [_random.randint(MIN_VALUE, MAX_VALUE) for _ in range(10)]
del _random


class TestServer(TestCase):
    """Integration tests for the server module."""
    def setUp(self):
        self.process = subprocess.Popen(
            [
                './run.sh',
                f'--min-value={MIN_VALUE}',
                f'--max-value={MAX_VALUE}',
                f'--seed={SEED}',
                '--log-level=DEBUG',
            ],
            cwd=os.path.dirname(os.path.dirname(__file__)),
            stdin=subprocess.PIPE,
            stdout=subprocess.PIPE,
            stderr=subprocess.DEVNULL,  # comment this out for debugging
        )

    def tearDown(self):
        self.process.stdin.close()
        self.process.stdout.close()
        self.process.kill()
        self.process.wait()

    @parameterized.expand([
        # Test 1: Valid session
        (
            [
                ('Hi', 'Hi'),
                *[('GetRandom', str(val)) for val in RANDOM_VALUES],
                ('Shutdown', None),
            ],
            False
        ),
        # Test 2: Invalid commands
        (
            [
                ('Hi', 'Hi'),
                ('Hi', 'Hi'),
                ('Ciao', None),
                ('Hi', 'Hi'),
            ],
            True
        ),
    ])
    def test_integration(self, test_data, expected_alive):
        for (_input, expected_output) in test_data:
            self.assertIsNone(self.process.poll(), "process should be alive")
            self.process.stdin.write((_input + "\n").encode('utf-8'))
            self.process.stdin.flush()
            if expected_output is not None:
                actual_output = self.process.stdout.readline().decode('utf-8')
                self.assertEqual(expected_output + "\n", actual_output)
        if expected_alive:
            self.assertIsNone(self.process.poll(), "process should be alive")
        else:
            self.process.wait(timeout=5)
            self.assertEqual(self.process.poll(), 0)
