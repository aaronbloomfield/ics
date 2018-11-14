ICS: Programming Homework: Cryptocurrency
==========================================

[Go up to the ICS HW page](index.html) ([md](index.md))

You must design your own cryptocurrency!  Your implementation must be
secure enough that you could actually use it.

You may use any programming language for this, but if you want to use a language other than C, C++, Java, or Python, please check with us first.

### Overview

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

Some libraries will be installed for you:

- Python: we will install the [rsa](https://pypi.org/project/rsa/)
  package; hashes in Python are in hashlib, which is part of the
  Python installation.  Note that you have to use Python 3 for this
  assignment if you are using Python; the version installed on the
  submission server is 3.5.2.
- Java: RSA and SHA are already in the standard Java library; you can
  look [here](https://gist.github.com/dmydlarz/32c58f537bb7e0ab9ebf)
  for how to handle RSA in Java, and see the [MessageDigest
  class](https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html)
  for SHA.  The Java version on the submission server is 1.8.
- C/C++: Boost is installed, as well as the openssl library.  There
  are many great things about C and C++, but we think this will give
  you a real headache if you try to implement it in C or C++.

One option will be to copy how Bitcoin handles these functions, as
described in the [Cryptocurrency slide
set](../slides/cryptocurrency.html#/) slide set.  However, coming up
with new ideas will get you more points on the assignment.

### File format

**Blockchain**

The blockchain will be in files named `block_0.txt`, `block_1.txt`,
etc.  The zeroth block will be the genesis block, and will not have
any actual transactions in it.  Each successive block will be of the
following format:

- Line 1 will be the hash of the previous block
- Lines 2 and beyond will be a transaction record (which is *not* the
  same as a transaction statement), one per line

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

Each line will be of the form `S transfers x to D`, where S is the
source wallet, D is the destination wallet, and x is the (floating
point) amount to transfer.  You can have additional information after
the required text, but it must all be on the same line.  This is
important -- each transaction record in the ledger must be exactly one
line!

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


### Requirements

Since this can be done in any programming language, you will have to
write a shell script (described below) so that we can call your code.
While the shell script is described below, the particular function to
be implemented is shown like `this` in the requirements below.  Note
that handling output (such as which file to write it to) is likely
easier done in the shell script rather than in your code.

**Output:** Each command below should print out EXACTLY ONE LINE of
output indicating the success (or failure) of the command.  It will
make your life much easier if this one line is from the source code,
not the shell script.

The requirements are:

1. Print the name of the cryptocurrency (`name`): this one command
   doesn't need to call your program; it can be part of the shell
   script instead.
1. Create the genesis block (`genesis`): this is the initial block in
   the block chain, and the block should always be the same.  Come up
   with a fun quote!
2. Generate wallets (`generate`): this will create a public/private
   key set.  It will output it (but how?)
3. Get wallet tag (`address`): this will print out the tag the public
	key for a given wallet, which is likely the hash of the public
	key.  Note that it *only* prints out that tag (no other output).
	When the other commands talk about naming a wallet, this is what
	it actually means.
4. Fund wallets (`fund`): this allows us to add as much money as we
   want to the wallet.  While this is obviously not practical in the
   real world, it will allow us to test your program.  Create a
   special case ID ('bigfoot', 'daddy_warbucks', or whatever) that your
   program knows to use as the source for a fund request, and also
   knows not to verify when handling verification, below.  This means
   that 'bigfoot' will appear alongside the hash of the public keys as
   the source of funds.
5. Create a transaction statement (`create`): this statement will
   contain the sender, the recipient, the amount, and will have to be
   digitally signed with the private key of the wallet.  Any reasonable
   format is fine for this, as long as the transaction statement is
   text and thus readable by a human.  Note that this command does
   *NOT* add anything to the ledger.  There will be four additional
   parameters to this command -- in order, they are: the sender, the
   recipient, the amount, and the name of the file to write the
   transaction record to.
6. Verify a transaction (`verify`): verify that a given transaction
   statement is valid, which will require checking the signature and
   also the availability of funds.  Once verified, it should be added
   to the ledger.  Once in the ledger, it becomes a transaction
   record.
7. Check a balance (`balance`): based on the transactions in the block
   chain AND ALSO in the ledger, compute the balance for the provided
   wallet.
8. Sign block (`sign`): this will form another block in the
   blockchain.  The ledger will be emptied of transaction records, as
   they will all go into the current block being computed.
9. Validate the blockchain (`validate`): this should go through the
   entire block chain, validating each one.  This means that starting
   with block 1 (the block *after* the genesis block), ensure that the
   hash for that block is correct in the next block.


### Shell script

Since different programming languages can be used, we are going to
have you submit a shell script called `cryptomoney.sh` that we will
use to test your code.  Be sure to name it properly, as we will be
calling this to test your code!  Below is a sample shell script for
you to adapt.  In particular, it should have a separate case entry for
*each* of the enumerated requirements, above.  A sample line to call
your program is given for Java, Python, and C/C++ programs.  Your
shell script can handle much of the output work for you, such as
redirecting to a file so that your code only has to print to standard
output.

```
#!/bin/bash

# the first command-line parameter is in $1, the second in $2, etc.

case "$1" in

  genesis) java Money genesis > block_0
           ;;

  generate) python3 money.py generate > $2.key
           ;;

  fund) ./money fund $2
           ;;

  ## put the rest of the cases here, and end each one with the double semi-colon line

esac
```

We realize that the bash syntax for case statements is pretty horrid,
which is why we are providing this outline of the shell script.  When
calling the shell script, the function (genesis, generate, fund, etc.)
will always be the first command line parameter (thus in $1).  If any
additional ones are needed, they will be after that (thus in $2, $3,
etc.).


### Sample execution

This is just meant to show the syntax -- it is not meant to be a full
fledged testing suite of your homework.  However, it does call each of
the commands listed above. Note that the lines with the equals signs
are setting variables to be used later on

```
./cryptomoney.sh name
./cryptomoney.sh genesis > block_0.txt
./cryptomoney.sh generate > alice.wallet.txt
export alice=`./cryptomoney.sh address alice.wallet.txt`
./cryptomoney.sh fund $alice 100
./cryptomoney.sh generate > bob.wallet.txt
export bob=`./cryptomoney.sh address bob.wallet.txt`
./cryptomoney.sh fund $bob 100
./cryptomoney.sh create $alice $bob 12.5 > 01-alice-to-bob.txt
./cryptomoney.sh verify 01-alice-to-bob.txt
./cryptomoney.sh create $bob $alice 2.5 > 02-bob-to-alice.txt
./cryptomoney.sh verify 02-bob-to-alice.txt
cat ledger.txt
./cryptomoney.sh balance $alice
./cryptomoney.sh balance $bob
./cryptomoney.sh sign
./cryptomoney.sh validate
```

### Submission requirements

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
