ICS: Programming Homework: Networks
===================================

[Go up to the ICS HW page](index.html) ([md](index.md))

### Purpose

This homework will have you explore a few topics related to security vulnerabilities and networking.

There are three parts to this assignment.  As part of this assignment, you will have to create a document, called `networks.pdf` (where mst3k is your userid), and submit that file.  We aren't looking for any fancy write-up - just an explanation of what you did, and the results you got.  That being said, it should be legible.  So make sure you indicate what answers are for what questions, etc.

You will also have to submit one source code file, `keylogger.py`.


### Changelog

Any changes to this page will be put here for easy reference.  Typo fixes and minor clarifications are not listed here.  <!-- So far there aren't any significant changes to report. -->

- Mon, 4/10: changed the name of the PDF file to submit to `networks.pdf`
- Sun, 4/9: Updated DuckDuckGo's .onion URL to [https://duckduckgogg42xjoc72x3sjasowoarfbgcmvfimaftt6twagswzczad.onion](https://duckduckgogg42xjoc72x3sjasowoarfbgcmvfimaftt6twagswzczad.onion)


### Part 1: Explore Tor

Before you start, you should determine your IP address and the route to a given machine.  Visit [https://whatismyipaddress.com/](https://whatismyipaddress.com/) to get your IP address (for reasons we'll see in a bit, this is preferred over Googling for 'what is my ip').

You will need to install (and, briefly, use) Tor.  You can find out out Tor [here](https://www.torproject.org/), and installation -- which is particular to your operating system -- is described [here](https://www.torproject.org/download/).  If your operating system has another installation method for Tor, you are welcome to use that instead.

Load up the Tor browser and visit [https://whatismyipaddress.com/](https://whatismyipaddress.com/) again to get your IP address.  Hit reload a couple of times, and note how the IP address changes each time.  These are the IP addresses of the Tor *exit nodes*.

Visit a ***LEGAL*** Tor hidden service.  You can Google for a list of Tor hidden services -- their URL will always end in ".onion".  One such site is [https://thehiddenwiki.org/](https://thehiddenwiki.org/).  As an example of a ***LEGAL*** sites to visit, consider [https://duckduckgogg42xjoc72x3sjasowoarfbgcmvfimaftt6twagswzczad.onion/](https://duckduckgogg42xjoc72x3sjasowoarfbgcmvfimaftt6twagswzczad.onion/), which is the DuckDuckGo search engine.

Visit another website that is not a hidden service.  Note how much slower it is to view this website.  Can you stream a video from Youtube via a Tor connection?

Note that Tor is a service as well as a web browser.  You may (or may not) want to stop the Tor service when you are done.  

- Under Ubuntu Linux, that's `sudo service tor stop`.  This will stop it for that one time only -- and on the next reboot, it will reactivate.  To disable it for good, you can run `sudo systemctl stop tor` (or just uninstall it).  
- For Mac OS X: ... (to be filled in)
- For Windows: ... (to be filled in)

In the report, you should include:

- Your IP address before you were running Tor
- The IP address(es) used when you were using the Tor browser
- What ***LEGAL*** hidden service(s) that you visited


### Part 2: Packet Sniffing

For this part, we are going to 'listen' to network traffic, and see what interesting information we can find.  We will use a UNIX utility called tcpdump.  This utility will print out all the network traffic on a given interface.  tcpdump must be run as root; thus, you probably cannot run it on any UVa server.  You can download the tcpdump.zip file from Canvas' Files -- that file is NOT in this repo due to its size.  This file contains a dump of a tcpdump session.  We will be analyzing this file.

#### Opening the file

This file contains my password, so I wanted to ensure that it was properly protected, and I used a 6-character ZIP file password on it.  It's your job to crack that password.  While you can use any ZIP file password cracker that is out there (and there are lots!), we recommend fcrackzip.

- Windows: you can download the binaries from the [fcrackzip home page](http://oldhome.schmorp.de/marc/fcrackzip.html)
- Mac OS X: you can install it via homebrew or MacPorts.  `brew install fcrackzip` seems to work.  Also see [here](https://macappstore.org/fcrackzip/) for another example how to install it.
- Linux: you can install on your Linux VirtualBox image via `sudo apt-get install fcrackzip`

In all cases, you will need to understand what it does - just running it with very few parameters will just spew out gibberish to the screen.

#### tcpdump

A packet sniffing utility would run tcpdump, and parse the contents in real-time.  We can write such programs using Python or, for more speed, C or C++.  For this assignment, you won't need to write a program, but can instead just search for the data using any text-search mechanism (including opening it up in your favorite editor and searching the data).  The data was collected using the following command:

```
tcpdump -A -l -s 10000 -i eth0
```

The command line switches do the following:

- `-A` causes the packets to be printed in ASCII (as opposed to binary or hex, which are other command-line switches)
- `-s 10000` causes the maximum printed packet to be 10,000 bytes - if not set, it will only print the first 64 bytes of each packet
- `-i eth0` causes it to 'listen' to the first (and, in this case, only) Ethernet connection.  If you have multiple network connections, this will allow you to monitor only one.  You could set this to listen to the wireless connection, or to all connections.

Many of the pages returned by tcpdump are compressed to save network bandwidth.  This is particularly relevant for popular sites that send a lot of data, such as Facebook or CNN.  You can see this in the packet by the 'Content-Encoding: gzip' header.  One can easily write the data to file, reverse the base-64 encoding, and the un-gzip it (and there are programs that do just that).  For this assignment, we'll be looking just at the non-compressed data.

#### The tcpdump.txt file

You will need to analyze the tcpdump.txt file.  Download the tcpdump.zip file from Canvas' Files -- that file is NOT in this repo due to its size.  In your report, you need to answer the following questions:

- What websites were visited that encoded the data using gzip?  We are looking for the domain names (domain.tld), not the exact URL (foo.bar.baz.domain.tld).
- What types of files were transferred?  This is encoded in the 'Content-Type' header.
- What network ports were accessed?  A network port corresponds to an application-level protocol, such as http and https.  This is encoded as `gemini.http-alt` (here `http-alt` means an alternative to http) - see the example packet explanation, below.  The `http-alt` port is 8080 -- you can find this out by looking in `/etc/services`, which maps port names (such as `http-alt`) to port numbers.  We aren't interested in port numbers above 10,000.
- What is the username(s) and password(s) were used when logging in?  Where were they used to log in to?  Not surprisingly, all passwords were changed for this file.  (There is only one that can be sniffed)
- Can you determine my ebay password?  Why or why not?
- What other network-level and transport-level protocols were used, other than TCP?  TCP is used quite frequently (so much so that TCP packets are not labeled as TCP).  You can find a listing of the protocols on [Wikipedia](https://en.wikipedia.org/wiki/Internet_protocol_suite) -- specifically in the gray box on the right hand side of that page entitled "Internet protocol suite".

#### Packet representation in the file

A given packet could look like the following.

```
10:31:35.293018 IP gemini.http-alt > pegasus.58878: Flags [P.], seq 4642:4790, ack 2176, win 80, options [nop,nop,TS val 43850520 ecr 38675081], length 148
E`....@.9.....E..........O.........P.......
.....N".st">
  <p>Please enter your UVa userid to obtain your balance: <input type="text" name="userid"
></p>
<input type="submit">
</form>
<hr>
</html>
```

This packet was sent at 10:31:35 from gemini (aka gemini.cs.virginia.edu) to pegasus (my home computer).  It was a http connection (the 'http-alt' means the alternative http port that gemini uses) - so it was sending data.  In fact, this is the last half of the SQL injection attack web page that you used above in part 2.

The uncompressed tcpdump file is 16 Mb.  Thus, it may have problems opening in Notepad.  And just doing Notepad-style searches for all the answers to the above questions will not get you very far - you certainly aren't going to be able to find out all the protocols via searching through Notepad for each packet header (some of the questions may be answered via that type of search, though).  The UNIX utility grep will be your friend here.  Consider the following, which you type from the UNIX command line.

```
grep gemini tcpdump.txt
```

This will search the tcpdump.txt file for all occurrences of gemini.  While it returns 375 lines, that is still only 8% of the entire length of the tcpdump.txt file.  You can also use 'egrep', which allows you to enter a regular expression.  Consider the following.

```
egrep "\[a-z\]\[4\]\[4\]+" tcpdump.txt
```

This takes in the regular expression \[a-z\]\[4\]\[4\]+, and searches for all occurrences of it in the file (there are 20).  Make sure you put your double quotes around the regular expression.

Lastly, note that 'sextans' is the name of one of my routers (all my machines are named after constellations).  So when you see data being transfered between sextans and another host, that's between my computer and said host.

Honor pledge details: you are given permission to search the tcpdump.txt file to answer the above questions for this assignment.  After that, you will need to delete the file.


### Part 3: Keyboard logger

You are going to see how easy it is to build a keyboard logger.  We will use the the [pynput](https://pypi.org/project/pynput/) Python package to do so.  Your code will be in a `keylogger.py` file.

#### Compatibility

This works on the three major platforms:

- Mac OS X: install the package via `pip install pynput`.  When you run the software, it will prompt you to change a setting to allow Terminal to monitor the keyboard.  Be sure to change it back once this assignment is done!
- Windows: It should work out of the box.  If Windows Defender is blocking it ("operation did not complete successfully because the file contains a virus or potentially unwanted software"), you may have to turn off real-time protection in Windows Defender to complete this part.  Be sure to turn it back on once done!
- Linux: It should work out of the box.

It does not work on a few less common platforms:

- Chrome OS, running Linux in a virtual container, will not work

(Others will be added here as we find more that do not work)

#### Getting started

First, start with the code sample code provided on the [pynput](https://pypi.org/project/pynput/) Python package page; look at the "Monitoring the keyboard" section.  Enter that code (fixing the three line breaks that Python will complain about).

When you run it in a terminal, and type `hello world`, you should see some output like the following:

```
$ python3 keylogger.py
Key.enter released
alphanumeric key h pressed
h'h' released
alphanumeric key e pressed
e'e' released
alphanumeric key l pressed
l'l' released
alphanumeric key l pressed
l'l' released
alphanumeric key o pressed
o'o' released
special key Key.space pressed
 Key.space released
alphanumeric key w pressed
w'w' released
alphanumeric key o pressed
o'o' released
alphanumeric key r pressed
r'r' released
alphanumeric key l pressed
l'l' released
alphanumeric key d pressed
d'd' released
special key Key.esc pressed
^[Key.esc released
$
```

Note that the exact output will be a bit different, as it depends on how quickly you type and release each of the keys.

If you see output like the following:

```
$ python3 keylogger.py
hello world
```

Then the pynput package is NOT working on your system -- review the Comparability sub-section, above.

#### The Task

The task is create a keylogger that will monitor the keyboard and, if the userid `mst3k` is entered, it will print the next 10 characters.

You need to modify that code so that:

- If the characters `mst3k` are entered, and ONLY that string (case-sensitive!), then keep track of the next *ten* characters.  Once found, print those to the screen.
  - For sanity reasons (if doing this in the same terminal), put curly brackets (`{}`) around the printed password
  - You do not have to capture the space key or any special keys -- only letters (both cases), numbers, and punctuation
- The escape key should exit the program -- this is how it is currently configured in the sample code you copied above

The output might look like the following:

```
{passwordXX}
{1234567890}
```

#### Testing

The best way to test this is to launch it in one terminal or window, and start typing text in another one.


### Submission

You should submit two files to Gradescope:

- `networks.pdf`, where mst3k is your userid, which is your report for parts 1 (Tor) and 2 (packet sniffing).  Answers to all the questions in those parts should be in that file.  So that it is viable for us to read, please clearly label the various sections of the file.
- `keylogger.py`: from part 3 (packet sniffing)

