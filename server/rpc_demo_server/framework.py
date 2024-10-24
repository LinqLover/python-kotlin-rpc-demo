import logging


class Server:
    """
    The abstract base class for any servers.

    Each server can expose procedures by marking any instance methods with the
    @_procedure decorator.
    """
    _procedures = {}  # static because only managed by decorators currently

    @staticmethod
    def _procedure(name):
        """
        Expose this method to the server.
        """
        def decorator(func):
            Server._procedures[name] = func
            return func
        return decorator

    def call(self, name):
        return self._procedures[name](self)

    def run_stdio(self):
        """
        Serve this instance on stdin/stdout until a procedure raises a
        ServerTermination exception.
        """
        logging.info("Server is ready.")
        alive = True
        try:
            while alive:
                command = input()
                logging.info("Received command: %s", command)
                try:
                    result = self.call(command)
                    print(result, flush=True)
                except KeyError:
                    logging.error("Unknown command received, skipping.")
                except ServerTermination:
                    break
            logging.info("Server was terminated.")
            alive = False
        except EOFError:
            pass  # interrupted
        finally:
            if alive:
                logging.warning("Server was interrupted.")


class ServerTermination(Exception):
    """
    Raised when a procedure wishes to terminate the server gracefully.
    """
    pass
