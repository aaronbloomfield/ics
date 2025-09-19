ICS: Programming HW: Hashing
============================

[Go up to the ICS HW page](index.html) ([md](index.md))

### Overview 

In this assignment, you will be examining some of the issues surrounding hashes and their security applications.

There are two separate tasks for this assignment, as described below.

You should be familiar with the [hashing section of the encryption slides](../slides/encryption.html#/hashing).


### Changelog

Any changes to this page will be put here for easy reference.  Typo fixes and minor clarifications are not listed here.  So far there aren't any significant changes to report.


### Task 1: CRC insecurity

Your job is to write a program that, when given an input file and a CRC checksum, will modify that message, and ensure that the modified version matches the provided CRC checksum.  We are using CRC-16 here for speed, but the concepts are the exact same as for CRC-32, which would be much slower.

While you can use any language, we provide some sample code in Python.  You will have to install the [crc library](https://pypi.org/project/crc/) (documentation is [here](https://nicoretti.github.io/crc/)) -- you can do this via `pip install crc` or `pip3 install crc`.  If you are using PyCharm, you should do this through PyCharm's terminal shell: View -> Tool Windows -> Terminal, and then run the `pip` command from above.

#### Getting the CRC-16 checksum

Note: there are apparently [many versions of CRC16](https://reveng.sourceforge.io/crc-catalogue/all.htm); we are using the MODBUS version.  You can use a site such as [this one](https://crccalc.com) to compute a CRC hash, but be sure to look at the MODBUS version line when it presents the hash results.  If you do use that site, be sure to take into account a ending newline -- most text files will have it, and if so, you have to make sure that the online form has it as well.

You can also use the program below to do so; this demonstrates the use of the "crc" library in Python:

```
import crc, sys
assert len(sys.argv) == 2
with open(sys.argv[1]) as f:
    data = f.read()
bindata = bytes(data,"ascii")
calculator = crc.Calculator(crc.Crc16.MODBUS)
result = calculator.checksum(bindata)
print(sys.argv[0]+":",hex(result))
```

Run it as such:

```
$ echo "Things are going just great" > input.txt
$ python3 crc16.py input.txt                       
crc16.py: 0x766a
$
```

The first line (`echo "Things are going just great" > input.txt`) creates a text file that has the indicated string.  The second line calls the program above.

#### Assignment

Your program can be named anything, and there will have to be a `crc16crack.sh` shell script, described below, to run your program.  Your program will be run with two command-line parameters:

1. the input file name to read from
2. the desired CRC value (in hex) - this will be 4 hexadecimal characters, such as 'abcd' (we will leave out the leading '0x')

Note that the execution of `crc16crack.sh` can take a few minutes, depending on the platform it was run on.  This means the Gradescope submission will take a while as well -- see the hints in the Submission section, below, for ways to handle this.

The program should write its output to a file named `output.txt`.  Note that the output.txt file above does not have to have a trailing newline character. That file should contain the following:

1. The contents of the original file in its entirety (it will consist only of printable ASCII characters, as well as newlines)
2. A message of your own, which demonstrates that you *could* modify the original message, possibly maliciously, if desired. (Do not actually modify the original message -- just add your message after the original, as that simplifies this assignment.) 
3. A reasonable amount of PRINTABLE ASCII characters (decimal values 32 - 127) to the end of the input file (reasonable means 10 or fewer), such that the new output file has the same CRC as the desired CRC value (the second command-line parameter).  The only purpose of these characters is to affect the CRC value.

A sample execution:

```
$ echo "Things are going just great" > input.txt
$ cat input.txt 
Things are going just great
$ python3 crc16.py input.txt 
crc16.py: 0x766a
$ ./crc16crack.sh input.txt abcd 
$ cat output.txt 
Things are going just great!

If you think things can't get worse it's probably only because you lack sufficient imagination.

0000|4
$ python3 crc16.py output.txt         
crc16.py: 0xabcd
$ 
```

Here is what is happening in the above execution run:

- `echo "Things are going just great" > input.txt`
    - This is creating a text file, called `input.txt`, that has the string "Things are going just great"
- `cat input.txt`
    - `cat` is how you display a text file to screen in UNIX; the equivalent command on Windows is `type input.txt`
- `python3 crc16.py input.txt`
    - This uses the program provided above to check what the CRC-16 hash of `input.txt` is -- and it's reports back that it is 0x766a
- `./crc16crack.sh input.txt abcd`
    - This runs your crack program using `input.txt` as the input file, with a desired hash of 0xabcd
- `cat output.txt`
    - This displays the resulting output file to the screen -- you can see the original contents of `input.txt`, the additions (like 3 in the file), and the extra few characters needed to make `output.txt` have hash 0xabcd
- `python3 crc16.py output.txt`
    - This runs the program provided above to verify that the hash of `output.txt` really is 0xabcd


### Task 2: Dictionary Attacks

Modern computer systems do not store the password in plain text, but instead store a hash of that password.  When a user logs in, a hash is taken of the password the user enters, and that hash is compared to the saved one -- if they match, then the login is successful.  Since hashes are one-way functions, we cannot determine a password based solely on the hash.  Instead, we perform a [dictionary attack](https://en.wikipedia.org/wiki/Dictionary_attack): we take every word in the dictionary, hash each one, and then compare the hashes.  If we had more computing power, we could compute *all* passwords of 8 printable characters, and hash each one of those.

If all that was stored was the hash of the password, then it would be easy to figure out one from the other.  For example, an Internet search for the hash 5f0974ee455c4cd57c58dfb04f3d070b1f365d0ed4401dbf28089b308b019a67 yields [this site](https://hashdecryption.com/h/plain/tableau), which tells us that the password is 'tableau'.

To prevent a leaked password hash from being easily looked up online, sites will add a *salt*, which is a suffix added to each password.  If the salt is `_abcdefg`, then the password `tableau` would have the string `tableau_abcdefg` hashed (salting is just string concatenation), and the hash of that is stored in the password file.  That hash of `tableau_abcdefg` is 4b9b0b7c0b94c98828d6eac32e0bf081dba5c686e2cae111bdfd5c042ac53e8f, and searching for that does not yield any results.  In practice, salts are long strings -- 50 characters is more typical.  We'll use much smaller salts in this assignment.

For this part, we are only using SHA-256 hashes.  This part may be written in any programming language.

#### Set-up

A password file will be provided in the following format:

```
aaron 1d06b71965f3c7466467c7a89dd1aadfffe9da9f409017c1fa363b49312d70f4
eddie f9c6b88c040ec9388f78d50a06d2da92362f9d9a219a3180fde59c768da3f366
fiona 83352f28d732fa2a255d6c69d5c332f6b9f421d89bacb2ed334b0841bfce6661
matt 553c165c0199e45e6c44ea743650d7f48a62c82357d46a03179b86719d670b2a
varun 36a095da4e23a3b35db038092aef1b8c3dbd4eb7068d3fdc869af10849476f82
```

The file is ASCII, and all usernames will be alphanumeric strings.  There is exactly one space between the two tokens on any given line.  The hashes shown are SHA-256 hashes.  The passwords used to generate those hashes are shown in the execution run, below.

The dictionary file we will use is located at /usr/share/dict/words on most Linux systems (assuming that the `wamerican` package has been installed); you can find a copy in Canvas' Files.  It contains about 100,000 words.  The file has one word per line, with no whitespace.  We have a version stored on Canvas' Files, and you should use that (the one in /usr/share/dict/words is UTF-8, and we want it to be ASCII; the one on Canvas' Files was converted to ASCII for this assignment).

You are welcome to create your own versions of these files -- meaning you can create a file with only 100 (or so) words to use for testing.  If you want to find the SHA-256 password for a string, try running: `echo -n banana | sha256sum` in Linux or Mac OS X.  Note that the `-n` part is important in that echo statement -- it ensures that there is no return (`\n`) put at the end of the string that you are taking the SHA-256 hash of.  You can also use any online SHA-256 generator (the hash of `banana`, by the way, is b493d48364afe44d11c0165cf470a4164d1e2609911ef998be868d46ade3de4e).  

#### Assignment

Your program should find any and all passwords matches in the password file by hashing each of the words in the dictionary file.  Your program will be provided with three command-line parameters: the dictionary file, the password file, and the salt, in that order.  For this assignment, the salt will always be an alphanumeric string, and underscores are also allowed.

You may write this in any programming language (although you have to let us know two days ahead of time if you want to use a language that the autograder is not already configured for).  You are expected to use the SHA-256 routines in your language.  Because we do not know the language you are using, you will have to provide a Makefile and a `dictionary.sh` shell script to compile and run the program.

Your program should print out one line for the passwords that it finds.  Here is a sample execution run for the password file shown above:

```
$ ./dictionary.sh words passwords.txt _l337_h4x0r
password for fiona is: astound
password for aaron is: flotation
password for matt is: patois
password for varun is: plastic
password for eddie is: spade
$ 

```

The order you print it out does not matter (we are going to sort your output), but the exact text on each line does, as we are going to have it graded by an auto-grader.  Not all the passwords may be found in the words file.  We will not give you invalid input (meaning files that do not exist, a wrong number of command-line parameters, etc.).  Both the words file and the password file will be ASCII files with at least 1 line in each file.  The salt will only be alphanumeric characters and underscores.

If no passwords are found, then the program should produce no output.

While we do not really care about efficiency, your program must run in a reasonable time.  Basically it -- along with the program in the previous task -- should not time out in Gradescope.

### Task 3: Poor passwords

Now that we can tell if their passwords are in a dictionary, from the previous section, we also need to also check if they made a very simple change from their previous password.  What we are trying to prevent is if their password is `password`, then they should not be able to change it to `passwordxx`.

Formally, given the hash of their current password, the *plaintext* password that they had used previously, and the salt (as above), did they add two characters to their old password to make the new one?  If so, output `Found: xy`, where `xy` are the two characters added (note the capitalization, punctuation, and spacing!).  If not found, output `Not found`.

Here is a sample execution run; details about this run are described below.

```
$ echo -n "passwordab_l337_h4x0r" | sha256sum
2b87e4d56ae6e2a761da88f1f16956c6d1590648daf868754baefb57f840416a  -
$ ./passwords.sh 2b87e4d56ae6e2a761da88f1f16956c6d1590648daf868754baefb57f840416a password _l337_h4x0r
Found: ab
$ echo -n "password.._l337_h4x0r" | sha256sum
c5a05edf9891ee111b9feab37667093d6d74538e0f33d31cc7cc993441c360dd  -
$ /passwords.sh c5a05edf9891ee111b9feab37667093d6d74538e0f33d31cc7cc993441c360dd password _l337_h4x0r
bash: /passwords.sh: No such file or directory
$ ./passwords.sh c5a05edf9891ee111b9feab37667093d6d74538e0f33d31cc7cc993441c360dd password _l337_h4x0r
Found: ..
$ echo -n "password123_l337_h4x0r" | sha256sum
1eb837cbffa28125d7f141f8578748615581972cea3ec5a59bd6e2b3c349e976  -
$ ./passwords.sh 1eb837cbffa28125d7f141f8578748615581972cea3ec5a59bd6e2b3c349e976 password _l337_h4x0r
Not found
$ 
```

This run assumes the previous password was just `password`.  The `echo` lines show a quick way to determine the hash of a value from the command line; make sure you put in the `-n` parameter to `echo` if doing it this way!  You can also use `hashlib.sha256(bytes('passwordab_l337_h4x0r','ascii')).hexdigest()` in Python.  

The first run creates a hash where they just added `ab` to their previous password of `password`.  The salt, which is `_l337_h4x0r`, is added as well, so the full value hashed is `passwordab_l337_h4x0r`.  The program output of `Found: ab` indicates that it determined that they just added `ab` to their password.  The second execution run does the same, but detects adding `..` (two periods) to their password.  The third execution run adds three characters to their old password; as we are only checking for exactly two added characters, it outputs `Not found`.

Some notes:

- We are only using ASCII characters for this.
- In Python, `string.printable` (you have to import the `string` module first) will provide a string of all printable characters that you can iterate through.
- Although white space characters (space, tab, return) are in the printable character set, we are not going to be testing your code with white space as either of the two additional characters.
- If the character causes problems with the shell, such as the exclamation point, you can ignore that one -- we will be using characters that do not cause such issues.
- Make sure your output is exactly as specified above!
- Don't overthink this -- the solution code is under 10 lines in Python.


### Other Files

#### Makefile

You will need to submit a `Makefile`.  It will compile your code, as needed; if you are using Python (or similar), the no compilation is necessary, but a valid Makefile must still be submitted.

If you are using a language for the dictionary attack code that does not need compilation, such as Python, your Makefile will look like the following:

```
main:
    echo 'hello world'
```

Note that the indentation is a tab, not spaces!  Make is annoying that way.

If you are using a language for your dictionary attack code that *does* need compilation, such as Java, your Makefile will look like the following:


```
main:
    javac CRC16Crack.java
    javac Dictionary.java
    javac Passwords.java
```

Change the name of your source code file as appropriate.

#### Shell scripts

Each of the three parts will have to have its own shell script.  All these shell scripts are exactly two lines.  The first line is exactly:

```
#!/bin/bash
```

The name of the shell script and the second line will depend on which shell script and what programming language.  You are welcome to name your source code anything reaosnale; be sure to change the second line in your shell scripts accordingly.  Here are some examples of the second line:

- Task 1: `crc16crack.sh`:
    - Python: `python3 crc16crack.py $@`
    - Java: `java CRC16Crack $@`
    - C/C++: `crc16crack $@`
- Task 2: `dictionary.sh`:
    - Python: `python3 dictionary.py $@`
    - Java: `java Dictionary $@`
    - C/C++: `dictionary $@`
- Task 3: `passwords.sh`:
    - Python: `python3 passwords.py $@`
    - Java: `java Passwords $@`
    - C/C++: `passwords $@`

To run it yourself, be sure to run `chmod 755 crc16crack.sh dictionary.sh passwords.sh`


### Submission


There are seven files to submit:

- `Makefile`, which should compile the code, as necessary, from all three tasks; you can just have an `echo` statement if you do not need to compile your code
- `crc16crack.sh` and your source code from task 1
- `dictionary.sh` and your source code from task 2
- `passwords.sh` and your source code from task 3

We will be compiling your submission with `make`.

#### Submission hints

The CRC-16 cracking code will take some time on Gradescope.  You can write a temporary version of that program that just writes anything to `output.txt` to start -- this will obviously fail the Gradescope tests, but it will allow you to ensure that everything else in your submission works (Makefile, the other file parts, the shell scripts, etc.).  Once you know everything else works, you can submit your real version of the CRC-16 cracking code.

Note: there will be other, hidden, tests for this assignment.  However, the hidden tests for part 1 (CRC cracking) are disabled for now so as not to prolong the Gradescope submission time.  We'll run those tests later.
