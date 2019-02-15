ICS: Programming HW: Hashes
===========================

[Go up to the ICS HW page](index.html) ([md](index.md))

You will want to see the
[homeworks policies page](../uva/hw-policies.html)
([md](../uva/hw-policies.md)) for formatting and other details.  The
due dates are listed on the [UVa course page](../uva/index.html)
([md](../uva/index.md)).

The reference platform for this assignment will be 64-bit Ubuntu Linux, version 18.04.  The VirtualBox image (see the Collab landing page for details) is configured for this.

### Purpose

In this assignment, you will be examining some of the issues surrounding hashes and their security applications.

There are four separate tasks for this assignment, as described below.  Please note that task 2 is computationally intensive and requires **_several_** hours to run.  Extensions will not be granted because you waited until the last minute to start this assignment.


## Task 1: CRC insecurity

Your job is to write a C/C++ program (necessary for speed reasons) that, when given an input file and a CRC checksum, will modify that message, and ensure that the modified version matches the CRC checksum.

### Set-up

For this task, you will need functions from the Boost C++ library.  This is already installed on the VirtualBox image.  Alternatively, you can download the three necessary files directly: [crc.hpp](http://www.boost.org/doc/libs/1_68_0/boost/crc.hpp), [cstdint.hpp](http://www.boost.org/doc/libs/1_68_0/boost/cstdint.hpp), and [config.hpp](http://www.boost.org/doc/libs/1_68_0/boost/config.hpp).

Your program should include the files as follows:

```
#include <boost/crc.hpp>
#include <boost/cstdint.hpp>
```

If you didn't install the library, use `#include "crc.hpp"` for development, and replace with the above lines before submission.

Optionally, you may want to use the `crc32` binary in Ubuntu -- this, also, is already installed on the VirtualBox image. Now if you run `crc32 file_name` in a terminal, you will get the CRC32 value for that file. 

 
### Resources 

- Boost libraries:
    - [Wikipedia overview](https://en.wikipedia.org/wiki/Boost_(C++_libraries))
    - [Documentation](https://www.boost.org/doc/libs/1_68_0/libs/crc/crc.html)
    - [Example usage of CRC functions](https://stackoverflow.com/questions/2573726/how-to-use-boostcrc)
- [Online CRC16 calculator](https://www.lammertbies.nl/comm/info/crc-calculation.html) (does not include trailing newlines)

### Assignment

Your program should be called `crc.cpp` and compile into an executable called `crc` (see the Makefile, below).  It will be run with two command-line parameters:

1. the input file name to read from
2. the desired CRC value (in hex) - this will be 4 hexadecimal characters, such as 'abcd' (we will leave out the leading '0x')

A sample execution:

```
./crc input.txt abcd
```

The program should write its output to a file named `output.txt`, which should contain the following:

1. The contents of the original file in its entirety (it will consist only of printable ASCII characters, as well as newlines)
2. A message of your own, which demonstrates that you *could* modify the original message, possibly maliciously, if desired. (Do not actually modify the original message -- just add your message after the original.) 
3. A reasonable amount of PRINTABLE ASCII characters (decimal values 32 - 127) to the end of the input file (reasonable means 10 or fewer), such that the new output file has the same CRC as the desired CRC value (the second command-line parameter).  The only purpose of these characters is to affect the CRC value, as changing the input *almost* always results in a different output (in this task, you are looking for the exceptions to that rule).


### Important Notes

**CRC16:** We will be using the CRC16 algorithm, NOT the CRC32 algorithm, to allow the program to run in a reasonably short time frame.  Boost can do both, so be careful.

**Newlines:** There are two things to be watch out for with newlines:

1. There are differences between Linux and Windows platforms (see [Wikipedia](http://en.wikipedia.org/wiki/Newline) for details).  Your program will be run (and graded) in a Linux environment.
2. Trailing newlines (`\n`) affect the CRC16 value of a file, but are often overlooked. Be careful not to unintentionally add any extra newlines that would change the CRC value of `output.txt`.  For example, the CRC16 for "hello world" with no trailing newline is 0x39c1 on a UNIX system, but with a trailing newline it is 0x9778.  

### Additional Hints

- You need to create a new `crc_16_type` result EACH time you compute the CRC value; you can't re-use it very easily.
- Your program will be given 60 seconds to run when we grade it.  This should be enough time for CRC16, but you may want to include the `-O2` compilation flag
- During development, it may be easier to compute the CRC32 value because of the convenient `crc32` utility in Ubuntu (described above).  Once you verify that your program is computing the hash properly, change it over to CRC16.  (Note you need to change over to CRC16 *before* you search for collisions, or you will be searching a *long* time.)


## Task 2: MD5 collisions

How easy is it to create a malicious program with a specific MD5 hash?  In this part we'll find out.  

For this task, we are going to follow these online [instructions](http://www.mscs.dal.ca/~selinger/md5collision/).  This code is released under the Modified BSD and/or the GPL license, so I am allowed to use it here, as long as I don't claim credit for it (I'm not), and I include the license in the source code (it's included there).

### Set-up

**Platform:** You will likely want to use the Linux VirtualBox for this task. For one, we can make no guarantees about the safety of the program, although when we ran it on our own computer, and the world didn't end.  Additionally, you will be creating binary executables, which must be *Linux* binary executables (elf), **not** MacOS (Mach-O) or Windows (exe). We will be running them on a 64 bit Linux system, but 32 or 64 bit elf executables are fine.

You can download the source code from the [instructions website](http://www.mscs.dal.ca/~selinger/md5collision/) or from Collab; the file is called `evilize-0.2.tar.gz` or `evilize-0.2.zip` (you only need one).


### Assignment

Your task is to create two binary executables, `good` and `evil`, that have the same MD5 hash ([instructions](http://www.mscs.dal.ca/~selinger/md5collision/)).  Those executables should print something relevant (i.e., something "good" and something "evil") - it can be interesting quotations, good/evil instructions, etc.  __IT SHOULD NOT DO ANYTHING MALICIOUS__, as that would be a violation of your [Ethics honor pledge](../uva/ethics-pledge.pdf).  **ONLY** print something.  Find some interesting quotations to entertain us!

This process took 90 minutes on my home computer (3.4 Ghz machine) to run; your mileage may vary.  And since the program only runs on one core, multi-core machines do not get much of a boost.  When complete, check the MD5 checksums of "good" and "evil" - they should match.  You should also run both programs to ensure they exhibit the behaviors you programmed in the `main_good()` and `main_evil()` functions.

Additionally, you should answer the following question in a file called `md5.pdf`: how does this whole thing work?  We just want an overview of how the entire evilize and md5coll programs work, not an in-depth mathematical analysis of the collision algorithm.  You should be able to explain this adequately in around 1/2 page single-spaced, and definitely not more than 1 page. 

For this part, you should submit four files:

- `good` and `evil` binary executables
- `multiple_personalities.c`, the source code file that contains `main_good()` and `main_evil()`, the `main()` functions from the `good` and `evil` executables -- we aren't going to compile this, we just want to look over the source code
- `md5.pdf`


## Task 3: Dictionary Attacks

Modern computer systems do not store the password in plain text, but instead store a hash of that password.  When a user logs in, a hash is taken of the password the user enters, and that hash is compared to the saved one -- if they match, then the login is successful.  Since hashes are one-way functions, we cannot determine a password based solely on the hash.  Instead, we perform a [dictionary attack](https://en.wikipedia.org/wiki/Dictionary_attack): we take every word in the dictionary, hash each one, and then compare the hashes.

### Set-up

A password file will be provided in the following format:

```
aaron 34388d7a0c8eb31b74c40d6415676379
sam 642b58ba419517c47a6b94c3905aaa88
felix 6c43c0a88fbf0f44ba944d00524e45c3
helen ca6ad39adfc023f2c32e1e9afd062386
daniel 80edd055513bbdd360e48c089755659a
```

The hashes shown are MD5 hashes (because MD5 is super-secure, right?).  Those passwords are used are shown in the execution run, below.

The dictionary file we will use is  /usr/share/dict/words (it's on the VirtualBox image), which contains about 100,000 words, and can be found online [here](https://gist.githubusercontent.com/wchargin/8927565/raw/d9783627c731268fb2935a731a618aa8e95cf465/words).  That file has one word per line, with no whitespace.  

You are welcome to create your own versions of these files.  If you want to find the MD5 password for a string, try running: `echo -n apple | md5sum`.  Note that the `-n` part is important -- it ensures that there is no return (`\n`) put at the end of the string that you are taking the MD5 hash of.

### Assignment

Your program must find any and all passwords matches in the password file by hashing each of the words in the dictionary file.  Your program will be provided with two command-line parameters: the password file name and the dictionary file name.

For speed reasons, this program is to be developed in C++, and should be named dictionary.cpp.  It will be compiled by the Makefile (see below) into an executable named `dictionary`.  We will run this program providing only the password file and the dictionary file, in that order, as the command line parameters.  Note that you will have to pass the `-lcrypto` flag to the compilation line. If you are working on this homework on the VirtualBox image, you will likely have to install the `libssl-dev` package if you want to include the `openssl/md5.h` file.

You can look [here](https://stackoverflow.com/questions/1220046/how-to-get-the-md5-hash-of-a-file-in-c) for how to generate a MD5 hash in C++ -- you are welcome to copy that code directly (but state that you are doing such in your code comments).

Your program should print out one line for the passwords that it finds.  A format such as the following is reasonable:

```
$ ./dictionary passwords.txt /usr/share/dict/words
password for sam is: evenly
password for daniel is: inherits
password for felix is: pet
password for helen is: rhetorical
password for aaron is: stockpiles
$
```

Note that a human will be grading this, so feel free to modify that format, as long as it's clear which username / password pairs have been found.  Please don't print out any extra output, else we will not be able to find your answers therein.


## Task 4: Miscellaneous write-up

There are a few questions to answer, and they should be in a `misc.pdf` file.  Each one can reasonably be answered in 4 lines or less, so please be brief.

- What is the string that has the MD5 hash of abc20d7bde1df257f890e152af2e3470?  How did you determine this?
- What is password salting?  Why would we use it?


## Submission

There are **eight** files to submit:

- `crc.cpp` (from task 1)
- `good`, `evil`, `multiple_personalities.c`, and `md5.pdf` (from task 2); note that `good` and `evil` are binary executables
- `dictionary.cpp` (from task 3)
- `misc.pdf` (from task 4)
- `Makefile` (described below)

The Makefile needs to compile `crc.cpp` (from task 1) into a binary executable called `crc`.  It also needs to compile `dictionary.cpp` (from task 3) into a binary called `dictionary`.  It does not need to do anything for tasks 2 and 4. Below is a sample Makefile.  Recall that with Makefiles, you must replace the leading 5 spaces on each target task with a single tab.

```
main:
     g++ -O2 -o crc crc.cpp
	 g++ -O2 -o dictionary dictionary.cpp -lcrypto
```

We will be compiling your submission with `make`.
