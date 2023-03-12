# Fuzzer skeleton code

import aiohttp, asyncio, args

async def fuzz(args):
    """Fuzz a target URL with the command-line arguments specified by ``args``."""

    # asynchronous loading of a URL:
    async with aiohttp.ClientSession() as session:
        async with session.get(args.url) as response:
            await response.text()
            print(response)


# do not modify this!
if __name__ == "__main__":
    arguments = args.parse_args()
    asyncio.run(fuzz(arguments))
