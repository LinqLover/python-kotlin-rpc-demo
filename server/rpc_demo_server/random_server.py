from random import Random
from .framework import Server, ServerTermination


class RandomServer(Server):
    """
    A server that generates pseudo-random numbers.
    """
    def __init__(self, seed=None):
        super().__init__()
        self.random = Random(seed)

    min_value = 0
    max_value = 255

    @Server._procedure("Hi")
    def greet(self):
        return "Hi"

    @Server._procedure("GetRandom")
    def get_random(self):
        return self.random.randint(self.min_value, self.max_value)

    @Server._procedure("Shutdown")
    def shutdown(self):
        raise ServerTermination()
