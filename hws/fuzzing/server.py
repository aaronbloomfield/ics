# This creates a very basic uvicorn server that will respond with a 200(OK) to
# a small set of URLs, and 404 (not found) to anything else.  It is modified
# from the example code from https://www.uvicorn.org/.
#
# Run as:
# uvicorn server:app --reload --port 5000

# The URLs to respond with a 200 to
urls = [ '/employers', '/.gitignore', '/~admin' ]

async def app(scope, receive, send):
    assert scope['type'] == 'http'
    await send({
        'type': 'http.response.start',
        'status': 200 if scope['path'] in urls else 404, # the only line changed from the original
        'headers': [
            [b'content-type', b'text/plain'],
        ],
    })
    await send({
        'type': 'http.response.body',
        'body': b'Hello, world!',
    })
