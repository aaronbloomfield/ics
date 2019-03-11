ICS: Programming Homework: Networks
===================================

[Go up to the ICS HW page](index.html) ([md](index.md))

You will want to see the
[homeworks policies page](../uva/hw-policies.html)
([md](../uva/hw-policies.md)) for formatting and other details.  The
due dates are listed on the [UVa course page](../uva/index.html)
([md](../uva/index.md)).

### Purpose

This homework will have you explore a number of topics and security vulnerabilities related to networking.

There are four parts to this assignment.  As part of this assignment, you will have to create a document, called `mst3k-networks.pdf` (where mst3k is your userid), and submit that file.  We aren't looking for any fancy write-up - just an explanation of what you did, and the results you got.  That being said, it should be legible.  So make sure you indicate what answers are for what questions, etc.

In addition, you will have to have a working SSL configuration for part 1 on the network -- we will be testing that connection as well.


### Part 1: Apache SSL configuration

(this part will be filled in within the next few days -- ideally by Monday, March 11th)


### Part 2: Break WEP

In this part you will need to break into a [WEP](https://en.wikipedia.org/wiki/Wired_Equivalent_Privacy) protected wireless network.

The WEP protected network is accessible from the upper floors of Rice Hall on the west side of the building (the west side is the side with the main front door onto Engineer's Way).  The router itself is located in the 5th floor glass enclosed machine room in Rice Hall; how far away it is visible will depend on your particular machine.  The network SSID is `CS_ICS_wireless`.  Note that this SSID is ***not broadcasted***, so you will have enter the SSID directly (and exactly as specified) in order to connect to it.  There is a range extender for this network on the 4th floor of Rice Hall; the SSID for this is `CS_ICS_wireless_ext`.  Both have the same configuration, and you should pick the one to use based on the floor you are on.  Neither has their SSID broadcasted.

Students in this class (and *ONLY* students in this class) are given permission to break into this network for this homework assignment (and for this assignment *ONLY*).

There are many online tools that will allow you to do this.  One option is Aircrack-ng at [http://www.aircrack-ng.org/](http://www.aircrack-ng.org/).

Once you have accessed the network, you will need to scan for machines connected to that network, see what port(s) are open on those machines, and try to access those port(s).  You can do this through the `nmap` command, as described [here](https://itsfoss.com/how-to-find-what-devices-are-connected-to-network-in-ubuntu/).  If nmap is not on the VirtualBox image, you can install it via `sudo apt-get update` and `sudo apt-get install nmap` -- you are on your own for the other operating systems.  Once you have a port number, nmap should tell you what the protocol for that port is -- if not, you can look in the `/etc/services` file.

In the report, you should include:

- The WEP password for that network
- The internal network IP address your computer was assigned
- The IP address(es) and port(s) of the computer on the network that you are to access
- What you found at those IP address(es) and port(s)

### Part 3: Explore Tor

Before you start, you should determine your IP address and the route to a given machine.  Visit [https://whatismyipaddress.com/](https://whatismyipaddress.com/) to get your IP address (for reasons we'll see in a bit, this is preferred over Googling for 'what is my ip').

You will need to install (and, briefly, use) Tor.  You can find out out Tor [here](https://www.torproject.org/), and installation -- which is particular to your operating system -- is described [here](https://www.torproject.org/docs/installguide.html.en).  Once you install Tor, you will also need to install the [Tor browser](https://www.torproject.org/projects/torbrowser.html.en).

Load up the Tor browser and visit [https://whatismyipaddress.com/](https://whatismyipaddress.com/) again to get your IP address.  Hit reload a couple of times, and note how the IP address changes each time.  These are the IP addresses of the Tor *exit nodes*.

Visit a ***LEGAL*** Tor hidden service.  You can Google for a list of Tor hidden services -- their URL will always end in ".onion".  One such site is [https://thehiddenwiki.org/](https://thehiddenwiki.org/).  As an example of a ***LEGAL*** sites to visit, consider [http://3g2upl4pq6kufc4m.onion/](http://3g2upl4pq6kufc4m.onion/), which is the DuckDuckGo search engine.

Visit another website that is not a hidden service.  Note how much slower it is to view this website.  Can you stream a video from Youtube via a Tor connection?

Note that Tor is a service as well as a web browser.  You may (or may not) want to stop the Tor service when you are done.  Under Ubuntu Linux, that's `sudo service tor stop`.  This will stop it for that one time only -- and on the next reboot, it will reactivate.  To disable it for good, you can run `sudo systemctl stop tor` (or just uninstall it).  You are on your own for other operating systems.

In the report, you should include:

- Your IP address before you were running Tor
- The IP address(es) used when you were using the Tor browser
- What ***LEGAL*** hidden service(s) that you visited


### Part 4: Packet Sniffing

For this part, we are going to 'listen' to network traffic, and see what interesting information we can find.  We will use a UNIX utility called tcpdump.  This utility will print out all the network traffic on a given interface.  tcpdump must be run as root; thus, you probably cannot run it on any UVa server.  You can download the tcpdump.zip file from Collab's resources -- that file is NOT in this repo due to it's size.  This file contains a dump of a tcpdump session.  We will be analyzing this file.

This file contains my password, so I wanted to ensure that it was properly protected, and I used a 6-character ZIP file password on it.  It's your job to crack that password.  While you can use any ZIP file password cracker that is out there (and there are lots!), we recommend [fcrackzip](http://www.ubuntugeek.com/howto-crack-zip-files-password.html), which you can install on your Linux VirtualBox image via `sudo apt-get install fcrackzip`.  Note that you will need to understand what it does - just running it with very few parameters will just spew out gibberish to the screen.

A packet sniffing utility would run tcpdump, and parse the contents in real-time.  We can do this using something as simple as lex, although more advanced programs (including parsers like yacc or bison) would be more effective.  For this assignment, you won't need to write a program, but can instead just search for the data using any text-search mechanism (including opening it up in your favorite editor and searching the data).  The data was collected using the following command:

```
tcpdump -A -l -s 10000 -i eth0
```

The command line switches do the following:

- `-A` causes the packets to be printed in ASCII (as opposed to binary or hex, which are other command-line switches)
- `-s 10000` causes the maximum printed packet to be 10,000 bytes - if not set, it will only print the first 64 bytes of each packet
- `-i eth0` causes it to 'listen' to the first (and, in this case, only) Ethernet connection.  If you have multiple network connections, this will allow you to monitor only one.  You could set this to listen to the wireless connection, or to all connections.

Many of the pages returned by tcpdump are compressed to save network bandwidth.  This is particularly relevant for popular sites that send a lot of data, such as Facebook or CNN.  You can see this in the packet by the 'Content-Encoding: gzip' header.  One can easily write the data to file, reverse the base-64 encoding, and the un-gzip it (and there are programs that do just that).  For this assignment, we'll be looking just at the non-compressed data.

You will need to analyze the tcpdump.txt file.  Download the tcpdump.zip file from Collab's resources -- that file is NOT in this repo due to it's size.  In your report, you need to answer the following questions:

- What websites were visited that encoded the data using gzip?  We are looking for the domain names (domain.tld), not the exact URL (foo.bar.baz.domain.tld).
- What types of files were transferred?  This is encoded in the 'Content-Type' header.
- What network ports were accessed?  A network port corresponds to an application-level protocol, such as http and https.  This is encoded as `gemini.http-alt` (here `http-alt` means an alternative to http) - see the example packet explanation, below.  The `http-alt` port is 8080 -- you can find this out by looking in `/etc/services`, which maps port names (such as `http-alt`) to port numbers.  We aren't interested in port numbers above 10,000.
- What is the username(s) and password(s) were used when logging in?  Where were they used to log in to?  Not surprisingly, all passwords were changed for this file.  (There is only one that can be sniffed)
- Can you determine my ebay password?  Why or why not?
- What other network-level and transport-level protocols were used, other than TCP?  TCP is used quite frequently (so much so that TCP packets are not labeled as TCP).  You can find a listing of the protocols at http://www.wildpackets.com/resources/compendium/tcp_ip/overview.

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


### Submission

You should submit a file called `mst3k-networks.pdf`, where mst3k is your userid.  Answers to all the above questions should be in that file.  So that it is viable for us to read, please clearly label the various sections of the file.
