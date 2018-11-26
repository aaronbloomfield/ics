ICS: Programming Homework: Cryptocurrency
==========================================

[Go up to the ICS HW page](index.html) ([md](index.md))

You must design your own cryptocurrency!  Your implementation must be
secure enough that you could actually use it.

You may use any programming language for this, but if you want to use
a language other than C, C++, Java, or Python, please check with us
first.

## Overview

Your cryptocurrency should have a name -- ideally, think of a neat
name, but make sure it is 
[not already taken](https://en.wikipedia.org/wiki/List_of_cryptocurrencies).
It will have to use public/private key encryption to generate the
wallets.  You do not have to use symmetric encryption (such as AES) to
protect the private key in the wallet.  You will need to use a good
hashing algorithm.  In practice, SHA-256 would be the minimum, but you
can use a shorter SHA hash for this assignment.

The intent is not for you to write the hashing or encryption /
decryption routines -- use existing libraries for that.  In
particular, both Java and Python can do that for you.  Be careful
about requiring an uncommon Python module, Java library, or C/C++
library, as it still has to run on the submission server. 

One option will be to copy how Bitcoin handles these functions, as
described in the [Cryptocurrency slide
set](../slides/cryptocurrency.html#/) slide set.  However, coming up
with new ideas will get you more points on the assignment.


## Languages

As mentioned above, you may use any programming language for this, but
if you want to use a language other than C, C++, Java, or Python,
please check with us first.

For Java and Python, we provide you with the code to convert a hex
string to an array of bytes, to convert an array of bytes to a hex
string, getting the SHA-256 hash of a file, and to read and write RSA
keys.  For Java, that is in the form of methods that you can use.  For
Python, it is links to the library functions that performs that task.

**Python:** you have to use Python 3 for this.  We will install the
[rsa](https://pypi.org/project/rsa/) package; the documentation for
that library, including code examples, is
[here](https://stuvel.eu/rsa).  Some of these functions will require
you to encode your message as ascii (it gives 'Unicode-objects must be
encoded before hashing' mesage otherwise) -- just call
`.encode('ascii')` on the string first.  Encoding from a byte array to
a string is done via the `binascii` library (example:
`binascii.b2a_hex(byte)`); the other direction uses `a2b_hex()`.
Hashes in Python are in hashlib, which is part of the Python
installation.  To take the SHA-256 hash of a file, see [this code on
stack overflow](https://stackoverflow.com/a/44873382).



**Java:** RSA and SHA are already in the standard Java library; you
can look [here](https://gist.github.com/dmydlarz/32c58f537bb7e0ab9ebf)
for how to handle RSA in Java, and see the [MessageDigest
class](https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html)
for SHA (and the example
[here](https://www.baeldung.com/sha-256-hashing-java)).  An example
about how to sign a message can be found
[here](https://niels.nu/blog/2016/java-rsa.html).  You may use any of
the code on those pages for your program.  Note that the Java version
on the submission server is 1.8.  We provide some methods in the
[Sample.java](cryptocurrency/Sample.java)
([html](cryptocurrency/Sample.java.html)) file for you to use.

**C/C++:** Boost is installed, as well as the openssl library.  There
are many great things about C and C++, but we think this will give you
a real headache if you try to implement it in C or C++.


## File format

**Blockchain**

The blockchain will be in files named `block_0.txt`, `block_1.txt`,
etc.  The zeroth block will be the genesis block, and will not have
any actual transactions in it.  Each successive block will be of the
following format:

- Line 1 will be the hash of the previous block
- Lines 2 and beyond will be a transaction record (which is *not* the
  same as a transaction statement), one per line; these will be of the
  form `S transferred x to D`, where S is the source wallet, D is the
  destination wallet, and x is the (floating point) amount to transfer

When generating the next block, you can assume that the previous block
file exists.  This means that we will generate the genesis block
`block_0.txt` from the `genesis` command, below.  Note that we are not
dealing with nonces in this assignment.

**Ledger**

The blocks described above represent transactions that have been
completed.  Pending transaction records are to be placed in the
ledger, which we will appropriately call `ledger.txt`.  One of the
functionalities that you have to implement is the transfer of the
transaction records in the ledger into a block in the block chain.

Each line will be of the the same form as the lines in the ledger (`S
transferred x to D`).  You can have additional information after the
required text, but it must all be on the same line.  This is important
-- each transaction record in the ledger must be exactly one line!

**Transaction statement versus transaction record**

A transaction *statement* is a multi-line text file that contains the
sender, the recipient, the amount, and ends with a digital signature.
Each transaction statement will be in its own file.

A transaction *record* is a single line in a ledger (and, later, in a
block in the blockchain) that contains similar information, but on a
single line.

Another of the functionalities that you will have to implement is the
verification of a transaction record (checking it's signature and that
there is enough money), which will then insert a single transaction
record for that transaction into the ledger.

**Wallet address versus wallet file**

A wallet is a public / private key set.  When signing a transaction,
or checking a signature, then the full wallet file will be needed.  A
wallet address is a shortened version of the public key.  For our
purposes, we should take the SHA-256 hash of the public key, and use
that.  However, that's a bit long, so we can use only the first 16
bytes (characters) of that hash for brevity.


## Requirements

Since this can be done in any programming language, you will have to
write a shell script (described below) so that we can call your code.
While the shell script is described below, the particular function to
be implemented is shown like `this` in the requirements below.  That
function will be the first command line parameter provided to the
program, and any additional command line parameters are specified
below.  For example, to generate a wallet, you would call `java CMoney
wallet.txt` or `python3 cmoney.py wallet.txt`.  Note that these actual
calls are in the shell script, described below.

**Output:** Each command below should print out EXACTLY ONE LINE to
standard output indicating the success (or failure) of the command.
It will make your life much easier if this one line is from the source
code, not the shell script.

The requirements are:

1. Print the name of the cryptocurrency (`name`).  This is the name
   from the Overview section, above.  There are no additional command
   line parameters to this function.
2. Create the genesis block (`genesis`): this is the initial block in
   the block chain, and the block should always be the same.  Come up
   with a fun quote!  It should be written to a `block_0.txt`
   file. There are no additional command line parameters to this
   function.
3. Generate a wallet (`generate`): this will create RSA public/private
   key set (1024 bit keys is appropriate for this assignment).  The
   resulting wallet file MUST BE TEXT -- you will have to convert any
   binary data to text to save it (and convert it in the other
   direction when loading).  You can see the provided helper
   functions, above, to help with this.  The file name to save the
   wallet to will be provided as an additional command line parameter.
4. Get wallet tag (`address`): this will print out the tag the public
   key for a given wallet, which is likely the hash of the public key.
   Note that it *only* prints out that tag (no other output).  When
   the other commands talk about naming a wallet, this is what it
   actually means.  You are welcome to use the first 16 characters of
   the hash of the public key for this assignment; you don't need to
   use the entire hash.  The file name of the wallet file will be
   provided as an additional command line parameter.
5. Fund wallets (`fund`): this allows us to add as much money as we
   want to a wallet.  While this is obviously not practical in the
   real world, it will allow us to test your program.  Create a
   special case ID ('bigfoot', 'daddy_warbucks', 'lotto', or whatever)
   that your program knows to use as the source for a fund request,
   and also knows not to verify when handling verification, below.
   This means that 'bigfoot' (or whatever) will appear alongside the
   hash of the public keys as the source of funds.  This function will
   be provided with three additional command line parameters: the
   destination wallet address, the amount to transfer, and the file
   name to save the transaction statement to.
6. Transfer funds (`transfer`): this is how we pay with our
   cryptocurrency.  It will be provided with four additional command
   line parameters: the source wallet file name (not address!), the
   destination wallet address, the amount to transfer, and the file
   name to save the transaction statement to.  Any reasonable format for
   the transaction statement is fine for this, as long as the
   transaction statement is text and thus readable by a human.  Note
   that this command does *NOT* add anything to the ledger.
7. Check a balance (`balance`): based on the transactions in the block
   chain AND ALSO in the ledger, compute the balance for the provided
   wallet.  The wallet address to compute the balance for is provided
   as an additional command line parameter.
8. Verify a transaction (`verify`): verify that a given transaction
   statement is valid, which will require checking the signature
   **and** the availability of funds.  Once verified, it should be
   added to the ledger.  Once in the ledger, it becomes a transaction
   record.  The wallet file name (whichever wallet created the
   transaction) and the transaction statement are the additional
   command line parameters.
9. Create and sign block (`createblock`): this will form another block
   in the blockchain.  The ledger will be emptied of transaction
   records, as they will all go into the current block being computed.
   Recall that the first line in any block is the SHA-256 of the last
   block file.  There are no additional command-line parameters for
   this function.
10. Validate the blockchain (`validate`): this should go through the
    entire block chain, validating each one.  This means that starting
    with block 1 (the block *after* the genesis block), ensure that
    the hash listed in that file, which is the has for the *previous*
    block file, is correct.  There are no additional command-line
    parameters for this function.


## Shell script

Since different programming languages can be used, we are going to
have you submit a shell script called `cryptomoney.sh` that we will
use to test your code.  It is basically a big case statement for each
of the 10 functions listed above.  A sample such shell script is
available: [cryptomoney.sh](cryptocurrency/cryptomoney.sh)
([html](cryptocurrency/cryptomoney.sh.html)).  Note that you will have
to run `chmod 755 cryptomoney.sh` before you can run the shell script.
This is set up for Java -- for Python, you would replace all
occurrences of "java CMoney" with "python3 cmoney.py" (or similar).
Note that the parameters provided to the shell script are listed in
that shell script -- you are welcome to provide different parameters
to your program.  It is the shell script interface that we will be
using to test your code.

We realize that the bash syntax for case statements is pretty horrid,
which is why we are providing the sample script.  When calling the
shell script, the function (genesis, generate, fund, etc.)  will
always be the first command line parameter (thus in $1).  If any
additional ones are needed, they will be after that (thus in $2, $3,
etc.).  We will never provide invalid parameters (either the wrong
number, or the wrong type) to the shell script.


## Sample execution

This is just meant to show the syntax -- it is not meant to be a full
fledged testing suite of your homework.  However, it does call each of
the commands listed above. Note that the lines with the equals signs
are setting variables to be used later on.  The commands used here are
in the [basic-test.sh](cryptocurrency/basic-test.sh)
([html](cryptocurrency/basic-test.sh.html)) script.  Note that you
will have to run `chmod 755 basic-test.sh` before you can run the
shell script.

```
# ./cryptomoney.sh name
AaronDollar(TM)
# ./cryptomoney.sh genesis
Genesis block created in 'block_0.txt'
# ./cryptomoney.sh generate alice.wallet.txt
New wallet generated in 'alice.wallet.txt' with signature abdfa7a347c40443
# export alice=`./cryptomoney.sh address alice.wallet.txt`
# echo alice.wallet.txt wallet signature: $alice
alice.wallet.txt wallet signature: abdfa7a347c40443
# ./cryptomoney.sh fund $alice 100 01-alice-funding.txt
Funded wallet abdfa7a347c40443 with 100
# ./cryptomoney.sh generate bob.wallet.txt
New wallet generated in 'bob.wallet.txt' with signature 007fa96df53eb514
# export bob=`./cryptomoney.sh address bob.wallet.txt`
# echo bob.wallet.txt wallet signature: $bob
bob.wallet.txt wallet signature: 007fa96df53eb514
# ./cryptomoney.sh fund $bob 100 02-bob-funding.txt
Funded wallet 007fa96df53eb514 with 100
# ./cryptomoney.sh transfer alice.wallet.txt $bob 12.5 03-alice-to-bob.txt
Transfered 12.5 from alice.wallet.txt to 007fa96df53eb514 and the statement to '03-alice-to-bob.txt'
# ./cryptomoney.sh transfer bob.wallet.txt $alice 2.5 04-bob-to-alice.txt
Transfered 2.5 from bob.wallet.txt to abdfa7a347c40443 and the statement to '04-bob-to-alice.txt'
# ./cryptomoney.sh verify alice.wallet.txt 01-alice-funding.txt
Any fund request (i.e., from bigfoot) is considered valid; written to the ledger
# ./cryptomoney.sh verify bob.wallet.txt 02-bob-funding.txt
Any fund request (i.e., from bigfoot) is considered valid; written to the ledger
# ./cryptomoney.sh verify alice.wallet.txt 03-alice-to-bob.txt
The transaction in file '03-alice-to-bob.txt' with wallet 'alice.wallet.txt' is valid, and was written to the ledger
# ./cryptomoney.sh verify bob.wallet.txt 04-bob-to-alice.txt
The transaction in file '04-bob-to-alice.txt' with wallet 'bob.wallet.txt' is valid, and was written to the ledger
# cat ledger.txt
bigfoot transfers 100.0 to abdfa7a347c40443
bigfoot transfers 100.0 to 007fa96df53eb514
abdfa7a347c40443 transfers 12.5 to 007fa96df53eb514
007fa96df53eb514 transfers 2.5 to abdfa7a347c40443
# ./cryptomoney.sh balance $alice
The balance for wallet abdfa7a347c40443 is: 90.0
# ./cryptomoney.sh balance $bob
The balance for wallet 007fa96df53eb514 is: 110.0
# ./cryptomoney.sh createblock
All transactions in the ledger moved to block_1.txt
# ./cryptomoney.sh validate
The entire blockchain is valid.
# 
```

## Notes

There are a number of assumptions you can make for your code:

- We will always provide the proper parameters to the shell script --
  both the number, order, and validity of the parameters can be
  assumed.
- The block_?.txt files will be consecutive -- so there won't be a
  block_3.txt missing when there is a block_4.txt.
- The block_0.txt file will exist before any calls to verify,
  validate, or balance; however, the ledger.txt file may not yet
  exist.



## Submission requirements

You will submit exactly three files for this assignment:

- Your source code file.  All your code must be in a single file.  We
  realize that a Java file may compile to multiple .class files, which
  is fine.  As mentioned above, if you want to use a language other
  than C, C++, Java, or Python, please check with us first.
- The shell script, which should be called `cryptomoney.sh`.  We are
  going to call that script to test your entire code, so make sure
  it's named properly!
- A Makefile that will compile your code when we call `make`, which
  will be called on submission.  For languages that do not need
  compilation (such as Python), just put in a single `echo` statement
  so that `make` still runs properly.
