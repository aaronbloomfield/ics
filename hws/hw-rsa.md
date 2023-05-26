ICS: Programming Homework: RSA
==============================

[Go up to the ICS HW page](index.html) ([md](index.md))

### Overview 

This assignment will focus on the implementation of the RSA algorithm. Specifically, you will have to implement key generation, encryption and decryption, signing and signature checking, and cracking of RSA messages.

The intent is to implement this in either Java (since the JDK provides the functionality to handle the necessary math) or Python (similar).  The installed versions are Java 1.11 and Python 3.10.  You must speak to me first if you want to use another language!

You will want to see the [homeworks policies page](../uva/hw-policies.html) ([md](../uva/hw-policies.md)) for formatting and other details.  The due dates are listed on the [UVa course page](../uva/index.html) ([md](../uva/index.md)).

You should be familiar with how the RSA algorithm works from the [the encryption lecture](../slides/encryption.html#/rsa).  More details are available online (see the [Wikipedia article on RSA](https://en.wikipedia.org/wiki/RSA_(cryptosystem))). Keep in mind, however, that the Wikipedia page uses different variable names than what the lecture and slide set used. You will also want to reference the [Java SDK documentation](https://docs.oracle.com/javase/11/docs/api/), specifically the [java.math.BigInteger](https://docs.oracle.com/javase/11/docs/api/java/math/BigInteger.html) and [java.security.MessageDigest](https://docs.oracle.com/javase/11/docs/api/java/security/MessageDigest.html) classes.  The Python code does not use anything beyond the [standard Python library](https://docs.python.org/3/library/).

### Changelog

Any changes to this page will be put here for easy reference.  Typo fixes and minor clarifications are not listed here.  So far there aren't any significant changes to report.


### Links

These links are all described below, but are included here, all in one place.

- Java: [RSA.java](rsa/RSA.java) skeleton code, which you ***MUST*** use; you can also view an [HTML version](rsa/RSA.java.html)
- Python: The skeleton code is in the [rsa.py](rsa/rsa.py) file; you can see an HTML version of that code [here](rsa/rsa.py.html).
- Doxygen documentation for the [CipherText](rsa/html/classCipherText.html), [RSAKey](rsa/html/classRSAKey.html), and [RSA](rsa/html/classRSA.html) classes
- [Command line parameter usage](rsa/html/cmdparam.html) -- note that the provided RSA.java file calls the correct functions based on the parameters passed
- [The encryption lecture](../slides/encryption.html#/rsa), and the [Wikipedia article on RSA](https://en.wikipedia.org/wiki/RSA_(cryptosystem))
- [Java SDK documentation](https://docs.oracle.com/javase/8/docs/api/), including the [java.math.BigInteger](https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html) and [java.security.MessageDigest](https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html) classes
- [Python3 standard library](https://docs.python.org/3/library/).
- The [test-rsa.sh](rsa/test-rsa.sh) ([HTML version](rsa/test-rsa.sh.html)) shell script

### Other files

#### Shell script

Since different programming languages can be used, and you may name your file differently, we are going to have you submit a shell script called `rsa.sh` that we will use to test your code.  All it does is pass the command-line parameters on to your program.  If you are using Python, then your shell script would look like the following:

```
#!/bin/bash
python3 rsa.py $@
```

Be sure to call `python3`, not `python` in your shell script!  Otherwise it will not work.  And change `rsa.py` to the name of your program file.

If you are using Java, then your shell script would look like the following:

```
#!/bin/bash
java RSA $@
```

Change `RSA` to the name of your `public` Java class.

Save the two lines above to a text file called `rsa.sh`; change the name of the file as appropriate to your code.  Then run `chmod 755 rsa.sh`.  You should then be able to run your program through the shell script.  Examples of how to call the shell script are given below, but it uses the same command line parameters described above.

#### Makefile

Separately from the shell script, you will also need a Makefile.  This will allow your program to be compiled prior to execution (if your program needs compilation).  For languages that do not need compilation (such as Python), just put in a single `echo` statement so that `make` still runs properly.

If you are using Python, your Makefile will look like the following:

```
main:
     echo hello world
```

Note that the indentation is a tab, not spaces!  Makefiles are very strict on that.

If you are using Java, your Makefile will look like the following:

```
main:
     javac RSA.java
```

Note that the indentation is a tab, not spaces!  Makefiles are very strict on that.  And change the Java file name as appropriate for your source code.

### Code

To simplify the assignment, and to allow easy interoperability between your code and our test cases, we have provided a significant amount of skeleton code.

#### Java

The skeleton code is in the [RSA.java](rsa/RSA.java) file; you can see an HTML version of that code [here](rsa/RSA.java.html).  That code is split into three classes, the first two of which are just to hold data and have no methods of their own.  Each link in the list below will go to the Doxygen documentation for that class.

- [CipherText](rsa/html/classCipherText.html): a class to hold the cipher text for any RSA encrypted data
- [RSAKey](rsa/html/classRSAKey.html): a class to hold a public or private (or both) key
- [RSA](rsa/html/classRSA.html): the main class that implements RSA. There are six methods within that need to be completed for this assignment: `generateKeys()`, `encrypt()`, `decrypt()`, `crack()`, `sign()`, `checkSign()`.

#### Python

The skeleton code is in the [rsa.py](rsa/rsa.py) file; you can see an HTML version of that code [here](rsa/rsa.py.html).


### Details 

For this assignment, you must implement eight methods in the RSA algorithm. They are the ones that state "requires completion" in the comments.  This assignment is to be done in either Java (as you will need to use the BigInteger and MessageDigest classes) or Python (as you will need the functions in the standard Python library).  You can use another language if you would like, but you need to check with the course staff -- and at least 2 days are needed to ensure that the auto-grader works with that language.

You must start with the [RSA.java](rsa/RSA.java) skeleton code ([HTML version](rsa/RSA.java.html)) or the [rsa.py](rsa/rsa.py) skeleton code ([HTML version](rsa/rsa.py.html)). This code, described more fully below, provides various methods to ensure that the format of the key files and the encrypted files are consistent, so that we may easily test your code.  Furthermore, we are going to *EXPLICITLY* call individual methods from your code, so if they are not named exactly as they are therein, your code will fail those tests.

You need to use the provided `convertToASCII()` and `convertFromASCII()` methods in your code, specifically to convert the text for encryption and then back for the decryption.

You may add any other methods or fields that you would like to add. The following is required for our testing harness to work properly on your code:

- The `main()` method exactly as provided, as we want to make sure the command line parameters are interpreted the way we expect them to be interpreted
- The eight methods that you are to implement should not have their signatures changed (return type, visibility, static-ness, name, or parameter types).  All these methods have `throws Exception` in case your implementation decides to throw an exception.
- The two utility classes (`RSAKey` and `CipherText`) should not be modified
- The utility methods, which are the six methods that you *don't* have to implement, should not be modified.  Those are: `writeKeyToFile()`, `readKeyFromFile()`, `readCipherTextFromFile()`, `writeCipherTextToFile()`, `getFileContents()`, and `convertHash()`.
- As mentioned above, you may add any other methods or fields that you would like to add.

These requirements are meant to allow for easy for interoperability, and to ensure that your code works with our test cases.

You must implement six methods within the [RSA.java](rsa/RSA.java) ([HTML version](rsa/RSA.java.html)) file or within the [rsa.py](rsa/rsa.py) skeleton code ([HTML version](rsa/rsa.py.html)) file.  Those methods are: `generateKeys()`, `encrypt()`, `decrypt()`, `crack()`, `sign()`, and `checkSign()`.  Details for how they should work can be found in the [the encryption lecture](../slides/encryption.html#/) as well as [online](http://en.wikipedia.org/wiki/RSA).

The documentation contained in the comments in the skeleton code details what the methods and classes do (or should do).  That commenting was run through [Doxygen](http://www.doxygen.nl/), and the results are: [CipherText](rsa/html/classCipherText.html), [RSAKey](rsa/html/classRSAKey.html), and [RSA](rsa/html/classRSA.html).  ***You will want to look at this!***


### Parameters 

The `main()` method should not need to be modified for the final submission (feel free to modify it any way you want to test your code).  It will call the appropriate methods as indicated by the command line parameters, which are described [here](rsa/html/cmdparam.html).  That page is from the Java code, but the command line parameters are the same with the Python code as well. In almost all cases, output (progress, status messages, etc.) should ONLY be printed to the standard output if the `-verbose` option is set, and should be enough that we can understand what is happening.  The *only* time output should be printed to the terminal is when (1) a signature does not match, and (2) an error condition is encountered (which, in theory, should not happen with our tests on properly implemented code).

Note that the command-line parameters are parsed __in order__ - this means that if you call `java RSA -keygen 10 -verbose` (or `python3 rsa.py -keygen 10 -verbose`), you will not get any verbosity, as that parameter was specified *after* the `-keygen` parameter was given.  We provide a `main()` method in the [RSA.java](rsa/RSA.java) ([HTML version](rsa/RSA.java.html)) and the [rsa.py](rsa/rsa.py) skeleton code ([HTML version](rsa/rsa.py.html)) skeleton code, which can handle the parameters and, again, *should not be modified*.

Note that normal operation (i.e. without the `-verbose` flag) is for it to print *nothing* - the only exception is the `-checksign` flag.

Again, the command line parameters are listed [here](rsa/html/cmdparam.html).  That page is from the Java code, but the command line parameters are the same with the Python code as well.

You may assume (and, in fact, should) that you will only receive one of `-encrypt`, `-decrypt`, `-sign`, `-checksign`, `-crack`, or `-keygen`; this specifies what the program is going to do. The program should not output any messages on normal execution (it should output error messages, as appropriate -- but we won't be giving any invalid combination of parameters) other than on `-checksign`. With the `-showpandq` option, it will of course output *p* and *q*. And with the `-verbose` option, it can output a lot of informational messages.

Furthermore, you may assume that we will not give you invalid input, either in the files we provide, or for the command-line parameters.

#### The `-showpandq` parameter

When this is provided, it should print both $p$ and $q$ on separate lines (first $p$ and then $q$) as base-10 integers.  For example:

```
$ java RSA -showpandq -keygen 10
859
701
$
```

### Testing

A sample usage of the program, in which is Alice and Bob are sending messages is available.  This code can be found in a shell script named [test-rsa.sh](rsa/test-rsa.sh) ([HTML version](rsa/test-rsa.sh.html)). If you are on a UNIX environment, run `chmod 755 test-rsa.sh`, and then run it via `./test-rsa.sh`.  Note that this is not a complete test suite!  Just a quick check to see if the basics work.  But if your program does not work with this, then it's incomplete, and will receive a low grade.  If you are not on a UNIX system, then you may have to copy-and-paste those commands into a command terminal.

The script is as follows:

```
#!/bin/bash
# setup: create the message1.txt and message2.txt files
/bin/rm -f message[12].txt
echo "Two things are infinite: the universe and human stupidity;" > message1.txt
echo "and I'm not sure about the the universe." >> message1.txt
echo "by Albert Einstein" >> message1.txt
echo "The quick brown fox jumped over the lazy dog." > message2.txt
# 1: create keys alice-public.key and alice-private.key
./rsa.sh -key alice -keygen 200
# 2: create keys bob-public.key and bob-private.key
./rsa.sh -key bob -keygen 200
# 3: alice is going to encrypt a message for bob
./rsa.sh -key bob -input message1.txt -output encrypted1.txt -encrypt
# 4: bob will decrypt the message
./rsa.sh -key bob -input encrypted1.txt -output message1b.txt -decrypt
# 5: are they the same?
diff message1.txt message1b.txt
# 6: bob now sends a message to alice
./rsa.sh -key alice -input message2.txt -output encrypted2.txt -encrypt
# 7: alice will decrypt the message
./rsa.sh -key alice -input encrypted2.txt -output message2b.txt -decrypt
# 8: are they the same?
diff message2.txt message2b.txt
# 9: alice signs her message1.txt
./rsa.sh -key alice -input message1.txt -sign
# 10: bob checks that sign; they should match
./rsa.sh -key alice -input message1.txt -checksign
# 11: modify message1.txt
/bin/mv -f message1.txt message1.txt.bak
echo hi >> message1.txt
# 12: bob checks that sign; they should NOT match
./rsa.sh -key alice -input message1.txt -checksign
# 13: restore message1.txt
/bin/mv -f message1.txt.bak message1.txt
./rsa.sh -key alice -input message1.txt -checksign
# 14: charlie generates an easy-to-crack key
./rsa.sh -key charlie -keygen 10
# eve tries to crack alice's key
./rsa.sh -key charlie -crack
# 15: is the cracked key the same as the original key?
diff charlie-cracked-private.key charlie-private.key
# 16: clean up files (commented out by default)
#/bin/rm -f alice*.key bob*.key charlie*.key message*.sign message?b.txt encrypted?.txt message?.txt
```

This file creates two files (message1.txt and message2.txt), and will overwrite those files if they already exist; those two files are deleted by the last line in the script.  You are welcome (and encouraged) to use other, longer, message test files -- might we suggest some [great speeches](http://www.historyplace.com/speeches/previous.htm)? However, make ***SURE*** that the text is all ASCII, since your program will likely not be able to handle UTF-8 characters -- and when you cut-and-paste, characters like the dash and quotes often do not cut-and-paste into their ASCII equivalents. Run `file message1.txt` to check if it is ASCII or not (not ASCII is typically reported as 'UTF-8').

Assuming you don't have any extraneous output (which you shouldn't), the only command that should output anything is step 12.  Again, you can see the test script at [test-rsa.sh](rsa/test-rsa.sh) ([HTML version](rsa/test-rsa.sh.html)).

Note that the last line deletes the intermediate files; during development, you may want to comment that line out to see those files.

The output should be just:

```
Signatures do not match!
```

While we are not going to try to break your program with strange combinations of command line parameters (trying to decrypt but not specifying a key), we would encourage you to put some sanity error‐checking code in your methods for your own sanity while developing the program.

#### Windows

If you are using Microsoft Windows, and are *NOT* using the Linux subsystem, then you may want to try the [test-rsa.bat](rsa/test-rsa.bat) ([HTML version](rsa/test-rsa.bat.html)) file.  ***WARNING:*** this is a beta release of this batch file, and it's not yet clear that it works.  To use it, create a `rsa.bat` (not `rsa.sh`) file that calls your code, as above.  Note that you still have to submit a `rsa.sh` file when you submit the assignment.


### Notes

__Determining SHA-256 hashes:__ We are using the SHA-256 hash for this assignment.  If you want to see if a string has the same SHA-256 as a file, make sure they are EXACTLY the same.  If the file has a ending newline (`\n`) character, and the string does not, then the SHA-256 sums will not match!  You can find the SHA-256 hash of a file via the `sha256sum` command (`CertUtil -hashfile message1.txt SHA256` on Windows):

```
$ sha256sum message1.txt 
bdf712419bb34b8c0f0d08126f191ecab5da9c0dbb4d2711d3c8eed6f5d42f2a  message1.txt
```

If your system does not have sha256sum, you can switch over to MD5 **but only for development**!  To do that, change the value of the `hashAlgorithm` variable to "MD5".  You can then use the `md5` or `md5sum` commands similarly to what is shown above for `sha256sum`. Be sure to change it back to "SHA-256" before submission!

Note that, given an input file, your SHA-256 hash computation should print out the *exact* same result as the `sha256sum` command on your favorite Unix system.  But see the warning, above, about making sure the contents are the exact same.

__Creating the block:__ You will have a series of characters to put into a block (or, for decryption, to extract out of the block).  There are a few ways you can do this.  You can convert it to a byte array (the `String getBytes()` method does this) -- each value in that array is a single (ASCII-encoded) value.  You can optionally multiply the current running block by 256, then add the current value to be appended to the block.  Obviously, your number will need to be in a `BigInteger`.

__Block size determination:__ To figure out your block size (which we'll call *b*) -- which is the number of characters you can encode in one block -- let *x* be the number of bits in *n* (found via the BigInteger `bitLength()` method).  Divide *x-1* by 8 (the minus one is important here to prevent rounding issues).  As mentioned below, you can assume that we will always use keys that support a block size of at least 2.  This is for *encryption* only.  For decryption, the block size will be indicated in the ciphertext file which, when read in by the provided `readCipherTextFromFile()` method, will be in the `blockLength` field of the returned `CipherText` object.

__Block size minimum:__ Each block will be a whole number of 8-bit characters, so we will not be splitting characters between blocks. Thus, if your keys can hold up to 31 bits per block (if 2^^31^^ < *n* < 2^^32^^), we will only encode 24 bits (3 characters) in that block, not 31 bits.  That being said, you can assume that all blocks we will use will allow for at least 2 characters per block.  Note that the number passed in via `-keygen` is the bit size for *p* and *q*; *n* is roughly twice that size.

__ASCII:__ All files (messages, keys, ciphertext, what‐not) will have only printable ASCII characters, so you need not worry about binary files.  But there may be whitespace as well: newlines, tabs, linefeeds, etc.  Make sure that your code does not have UTF-8 characters in it!  Given a file, you can tell what type of characters it has via the `file foo.txt` command in Linux and Mac OS X.  Under Windows, you can right-click on the file in Explorer, and it should tell you the file type.  In particular, if you use IntelliJ (or similar), make sure it writes the files as ASCII (or UTF-8) files, *not* Unicode files.

### Submission

You should submit three files: your source code, `Makefile`, and `rsa.sh`.
