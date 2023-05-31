# Fuzzer skeleton code

import args, urllib.request

def fuzz(args):
    """Fuzz a target URL with the command-line arguments specified by ``args``."""

    # your code here...
    pass


# do not modify this!
if __name__ == "__main__":
    arguments = args.parse_args()
    fuzz(arguments)
