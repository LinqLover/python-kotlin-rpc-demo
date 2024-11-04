from random import Random
from .framework import Server, ServerTermination


class RandomServer(Server):
    """
    A server that generates pseudo-random numbers.
    """
    def __init__(self, seed=None, min_value=0, max_value=255):
        super().__init__()
        if min_value > max_value:
            raise ValueError("min_value must be smaller or equal to max_value")
        self.random = Random(seed)
        self.min_value = min_value
        self.max_value = max_value

    @Server._procedure("Hi")
    def greet(self):
        return "Hi"

    @Server._procedure("GetRandom")
    def get_random(self):
        return self.random.randint(self.min_value, self.max_value)

    @Server._procedure("Shutdown")
    def shutdown(self):
        raise ServerTermination()
