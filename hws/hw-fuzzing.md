ICS: Programming HW: Fuzzing
============================

[Go up to the ICS HW page](index.html) ([md](index.md))


### Overview 

In this assignment, you will be creating a *network fuzzer*, which is a program that will try out many different URL variants to see if any exist on a web server.  Given a base URL, such as `http://server.com/FUZZ`, and a word list containing many items, it will replace the string `FUZZ` with each of the words in the word list, and see if that URL exists.

Credit: this homework is based heavily on [Will Shand's xfuzz assignment](https://github.com/kernelmethod/xfuzz/).

#### Background

- You can read about what fuzzing is on the [Fuzzing Wikipedia page](https://en.wikipedia.org/wiki/Fuzzing).  You should know what fuzzing is before you proceed.
- You should be familiar with the [networking slide set](../slides/networks.html).
- You should be familiar with the [HTTP status codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status) -- whenever a web page is requested, one of these codes is provided.  For example, if the page is valid, it provides code 200 (OK) along with the page data.  If the page is not found, then it returns 404 (not found).
- You should have a recent version of Python, and you will need to be able to install packages, via pip

### Changelog

Any changes to this page will be put here for easy reference.  Typo fixes and minor clarifications are not listed here.  <!-- So far there aren't any significant changes to report. -->

- Sat, Oct 14: improved the explanations in the advanced fuzzing section; no changes made to the requirements.


### Setup

This assignment must be done in a recent version of Python.  The only additional package you will need is `uvicorn` -- to install it, enter `pip install uvicorn` (or `pip3 install uvicorn`).

You will need a number of files from this repository to work on this assignment:

- [fuzzer.py](fuzzing/fuzzer.py.html) ([src](fuzzing/fuzzer.py)): this is where you will write the code for this assignment, and the only file that will be submitted.
- [args.py](fuzzing/args.py.html) ([src](fuzzing/args.py)) is supporting code for fuzzer.py that does the command-line argument parsing.  You should not modify this file!
- [common.txt](fuzzing/common.txt): the word list that we will be using for testing.  The format is one word per line, with no spaces in the words.  If a space is in the URL, then `%20` (the URL representation for a space) will be used instead; you do NOT have to replace `%20` with a space in your code.
- [common-reduced.txt](fuzzing/common-reduced.txt): a smaller word list, described in detail below (in the 'Testing' section)
- [server.py](fuzzing/server.py.html) ([src](fuzzing/server.py)) code to run a mini web server on your machine for testing.


#### Calling the code

The provided code in args.py already handles the command line argument parsing.  Run `python3 fuzzer.py -h` to see the various options.  You are going to write the `fuzz()` function in fuzzer.py -- the parameter passed into that function contains the parse command line arguments -- you can print it to the screen during development to see how they are stored (but be sure to remove that print statement -- and any others beyond what is required -- before submission).


#### Writing the code

You have to write the `fuzz()` function in [fuzzer.py](fuzzing/fuzzer.py.html) ([src](fuzzing/fuzzer.py)) -- remove the `pass` line that is there once you insert your own code.  The intent is for you to use the `urllib.request` library; documentation on that can be found [here](https://docs.python.org/3/library/urllib.request.html).  

For the basic fuzzing, you will likely need the `urllib.request.urlopen()` function and the `status` field of the object it returns.

For advanced fuzzing, you will want to create a `urllib.request.Request` object so that you can set more parameters.


### Task 1: Basic fuzzing

The first task is to perform basic testing.  Given a URL, such as `http://server.com/FUZZ`, you will replace `FUZZ` with each word in the word list file, and then try that URL to see if you get a response other than 404 (not found).  You will do this by modifying the `fuzz()` function in the [fuzzer.py](fuzzing/fuzzer.py.html) ([src](fuzzing/fuzzer.py)) skeleton code.  Try printing out the parameter to the function to see how the command-line arguments are passed into the function.  You can see in the skeleton code how one might load a URL from a string.

When you are ready to test this, read the 'Testing' section, below, for easy ways to test it locally on your computer.


#### Output

The output for this assignment is very specific.  Once you have completed the above task, any URL that does *NOT* return a 404 should display the output in the following EXACT format.  (Note that you will modify what URLs to print later).

For example, if you were to call the fuzzer as: `python3 fuzzer.py -u http://ffuf.me/cd/basic/FUZZ -w common.txt`, then the output would be exactly:


```
200 http://ffuf.me/cd/basic/class
200 http://ffuf.me/cd/basic/development.log
```

Note that the output is the status number, a single space, and the URL.  The order you print it out the lines does not matter, only the content on each line.

We are going to use this particular call the visible test when you submit your assignment (although we will use a smaller word list to save time, but it will contain both `class` and `development.log`).  Other hidden tests will be used to grade your assignment.

**DO NOT HAVE ANY OTHER OUTPUT!**  We are going to test it by doing a file comparison, so if you have any other output it will report as not the same, and you will fail that test.  Again, the order of the lines in your output does not matter.

### Task 2: Advanced fuzzing

There are a number of command-line parameters that the fuzzer.py file will accept.  You have to implement usage of the others.  You can find them all via `python3 fuzzer.py -h`.  The remaining ones to implement are as follows.  Note that these are already parsed for you; you just have to handle when those values are in the `args` parameter to the `fuzz()` function.

- `-e EXTENSIONS` or `--extension EXTENSIONS`: One or more extensions to append (e.g. php, html, etc.). Multiple extensions may be provided.  So if `-e php -e html` is provided, and the wordlist contains `hello` and `world`, then you should be replacing `FUZZ` with six different values: `hello`, `hello.php`, `hello.html`, `world`, `world.php`, and `world.html`.  
	- The value to the `-e` parameter assumes that it will be prefixed with a period before being added to each word in the word list.  So `-e html` means you will add `.html` to each word in the word list.  However, note that the command line parameter inserts that period for you.
	- Note that adding any number of extensions still means you try the base word as well.  So adding `-e html` means your program will try *both* `alert` and `alert.html`.
- `-X METHOD` or `--method METHOD`: HTTP method to use (GET, POST, or PUT) (default: GET)
	- Your code should allow both upper-case and lower-case values ("get" and "GET")
	- You can also use the [urllib.request.Request](https://docs.python.org/3/library/urllib.request.html#urllib.request.Request) class to set the method; you just pass that object into [urllib.request.urlopen()](https://docs.python.org/3/library/urllib.request.html#urllib.request.urlopen).  To check the method is received correctly, update `server.py` to print out the `scope` variable.
- `-H HEADERS` or `--header HEADERS`: One or more HTTP headers to add to requests, in the form "HeaderName:HeaderValue" (e.g. "Content-Type:application/json" or "Host:FUZZ.example.com"). This may be specified one or more times.
	- You can again use [urllib.request.Request](https://docs.python.org/3/library/urllib.request.html#urllib.request.Request) to set the headers; to check the header is received correctly, print out the `scope` variable in `server.py`.  As before, you then past the Request object into `urllib.request.urlopen`.
	- Example usage: adding `-H "MyHeader:foobarbaz"` will cause each request sent to the URL to include that header; note that there is no space after the colon
	- Note that, in `fuzz()`, the headers are received as a string with a colon (`:`) separating the key and value.  You have to `split()` that, as what needs to be passed to the `urllib.request.Request` constructor is a dictionary of key-value pairs.
- `-d DATA` or `--data DATA`: Data to send in the body of the HTTP request.
	- To see how to read it from uvicorn, look [here](https://www.uvicorn.org/) -- specifically, look at the `read_body()` function, which is called (in the `app()` function in server.py) as `body = await read_body(receive)`.  You can cut-and-paste that function right into server.py if you want to use it.
	- You can also use the `urllib.request.Request` class to set the data; to check the data is received correctly, print out the `scope` variable in `server.py`.  As before, you then past the Request object into `urllib.request.urlopen`.
	- Warning: the data passed in to the `Request` object must be `bytes`, not a string or `None`.
- `-mc MATCH_CODES`: Match [HTTP response codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes). May be specified multiple times. If left unspecified, defaults to the following response codes: [200, 301, 302, 401, 403].  Previously you printed out any URLs that did not return 404 (not found).  That should now be modified to print out the URLs that return one of the response codes in this list (which is parsed for you and passed into the `fuzz()` function in the `args` parameter).
	- Specifying just one response code via `-mc` will replace the default list with just that one.  So `-mc 200` will not check for any of the defaults other than 200.  Note that the command line argument parsing does this for you.
	- **NOTE:** this can be specified *multiple* times, at which point you would then have to check for *multiple* match codes.

When we test your code, we will never give it invalid parameters, and there will always be a `-u` parameter and a `-w` parameter.  So you do not have to error-check the parameters.


### Testing

First test it locally, then test it remotely.  Both are described below.

#### Local testing

Initially, as you are developing your code, you will want to test it out locally.  One of the Python packages you installed is uvicorn, which is a small Python-based web server.  To run it, you first need to save the [server.py](fuzzing/server.py.html) ([src](fuzzing/server.py)) file.  This file has a `urls` list that is the set of URLs that it will respond with a 200 (OK) to; everything else it responds with a 404 (not found).  

You are welcome to, and will eventually have to, change the elements in that list during testing -- just note that each USLS part has to start with a forward slash.  If you are running uvicorn, and you edit the server.py file, it should detect this and reload the contents so that you don't have to restart it.

You will run your uvicorn server as follows:

```
uvicorn server:app --reload --port 5000
```

This runs the server on port 5000 on your computer.  To test against this, you will run your fuzzer as follows:

```
python3 fuzzer.py -u http://localhost:5000/FUZZ -w common.txt
```

Note: it may be that your computer needs to use `http://127.0.0.1:5000/FUZZ` instead.

Also note: sometimes Macs have issues with port 5000.  If that is the case, then try any other port (5001, 5002, etc.).

Following the output specifications described above, the output should be exactly as follows.  Note that the order of the lines does not matter, as long as the format of each line is exact.

```
200 http://localhost:5000/.gitignore
200 http://localhost:5000/employers
200 http://localhost:5000/~admin
```

#### Uvicorn server.py edits

The [server.py](fuzzing/server.py.html) ([src](fuzzing/server.py)) file, which is what configures the uvicorn server, will need to be modified for you to test the additional command line flags.  For example, you will may to try reporting 200 (OK) on `alert.html`, to test that your `-e html` works.  Likewise, you will want to test out different server status values other than 200 to ensure that your `-mc` flag works.


#### Remote testing

As a remote test, you can try it with the URL of http://ffuf.me/cd/basic:

```
python3 fuzzer.py -u http://ffuf.me/cd/basic/FUZZ -mc 200 -w common.txt 
```

The output would be:

```
200 http://ffuf.me/cd/basic/class
200 http://ffuf.me/cd/basic/development.log
```

Please do ***NOT*** try it out against any other machines, as ITS will get very cranky at you for doing so.


#### Word lists

As mentioned above, the word list we are using is the [common.txt](fuzzing/common.txt) file that we provide.  You can also use word lists in the [SecLists repository](https://github.com/danielmiessler/SecLists/) repository; you will probably want to use a word list in the Discovery/Web-Content/ directory.  You do not need any word lists beyond the [common.txt](fuzzing/common.txt) file for this assignment -- the additional ones are just if you are interested.

There is also a [common-reduced.txt](fuzzing/common-reduced.txt) file that contains only 100 words.  It contains the five URLs that are expected to be found in the tests herein (`.gitignore`, `employers`, and `~admin` for the uvicorn testing, and `class` and `development.log` for the fuff.me testing), as well as 95 other randomly chosen words from common.txt.  This file is useful for testing, as it will take much less time to print the results.


### Submission

You should only submit your `fuzzer.py` file to Gradescope.  The only visible test will be the `python3 fuzzer.py -u http://ffuf.me/cd/basic/FUZZ -w common-reduced.txt`.
