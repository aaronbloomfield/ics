ICS: Programming Homework: Tricky Jump
======================================

[Go up to the ICS HW page](index.html) ([md](index.md))


### Overview

In this assignment, you are going to read in a binary file, add in a tricky jump, and write it the modified binary file.  This is similar to what viruses do; however, you are going to do this in Python, whereas viruses do this in assembly.  However, you have to read in the *binary* file, and cannot use any external tools (such as objdump) to help you.

The goal of this is to write out the string "Not a virus" (with the trailing newline) to the screen, and then having the program continue on as normal.  Be sure to write that exact string, as that is what the auto-grader will check for!  If you write anything different, the auto-grader will deduct points.

This assignment is organized into steps, each one more complicated than the previous.  However, each one also builds upon the previous.  You can get partial credit if you do the first few, but not the last ones.


### Changelog

Any changes to this page will be put here for easy reference.  Typo fixes and minor clarifications are not listed here.   So far there aren't any significant changes to report.


### Platform

This assignment will be reading in ELF binaries used in Linux operating systems.  You can develop this on any platform that you want; however, the binary file will only be Linux ELF, and will only run on a Linux system.  If you do not have access to a Linux machine, you can use the [Virginia Cyber Range](https://www.virginiacyberrange.org/) to test your modified ELF files to ensure that they work.  See the [Rootkits assignment](hw-rootkits-tabbed.html) for how to sign into the Virginia Cyber Range, if you have not already done so in previous assignments.

**WARNING:** This site is free to use for *class purposes* for all students in the course.  Using it for non-class purposes is an honor violation, and will be dealt with as such.  Anything outside the reasonable bounds of an assignment in this course is considered a non-class purpose.

This is a great resource, but it is a *finite* resource.  If you decide to wait to the last minute to start the assignment, and the rest of your class-mates do so as well, it's going to be slooooooow.  You cannot get an extension because you waited until the last minute along with everybody else, and the system was slow as a result.


#### `setarch`

All the commands herein should be done after running `setarch $(uname -m) -L -R /bin/bash`.  Nothing will work right without running that command!

#### Compiled targets

We provide a number of executable files for you try to modify.  If you compile them yourself, then the executable files may be different due to differences in compiler versions, etc.  So please use the ones we provide!

- [test1.c](tricky-jump/test1.c.html) ([src](tricky-jump/test1.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test1](tricky-jump/test1) executable file.  You can also see the [objdump -d](tricky-jump/test1.objdump.txt), the [hexdump -C](tricky-jump/test1.hexdump.txt), and the [results of readelf](tricky-jump/test1.readelf.txt).
- [test2.c](tricky-jump/test2.c.html) ([src](tricky-jump/test2.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test2](tricky-jump/test2) executable file.  You can also see the [objdump -d](tricky-jump/test2.objdump.txt), the [hexdump -C](tricky-jump/test2.hexdump.txt), and the [results of readelf](tricky-jump/test2.readelf.txt).
- [test3.c](tricky-jump/test3.c.html) ([src](tricky-jump/test3.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test3](tricky-jump/test3) executable file.  You can also see the [objdump -d](tricky-jump/test3.objdump.txt), the [hexdump -C](tricky-jump/test3.hexdump.txt), and the [results of readelf](tricky-jump/test3.readelf.txt).
- [test4.c](tricky-jump/test4.c.html) ([src](tricky-jump/test4.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test4](tricky-jump/test4) executable file.  You can also see the [objdump -d](tricky-jump/test4.objdump.txt), the [hexdump -C](tricky-jump/test4.hexdump.txt), and the [results of readelf](tricky-jump/test4.readelf.txt).
- [test5.c](tricky-jump/test5.c.html) ([src](tricky-jump/test5.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test5](tricky-jump/test5) executable file.  You can also see the [objdump -d](tricky-jump/test5.objdump.txt), the [hexdump -C](tricky-jump/test5.hexdump.txt), and the [results of readelf](tricky-jump/test5.readelf.txt).
- [test6.cpp](tricky-jump/test6.cpp.html) ([src](tricky-jump/test6.cpp)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test6](tricky-jump/test6) executable file.  You can also see the [objdump -d](tricky-jump/test6.objdump.txt), the [hexdump -C](tricky-jump/test6.hexdump.txt), and the [results of readelf](tricky-jump/test6.readelf.txt).
- The [Makefile](tricky-jump/Makefile.html) ([src](tricky-jump/Makefile)) that compiled all of these.

All of the programs just print "hello world".

<!-- ============================================================= -->

### Step 1: Payload

For this assignment, we are just going to insert a small assembly routine that prints out "Not a virus", with a newline at the end.  You should create an assembly file, called `print.s`, that does this.  You can see sample code [in the buffer overflow slides](../slides/buffer-overflows.html#/4/10).   However, this code has both a `.data` section and a `.text` section -- you need to put it all in the `.text` section, as shown on [this slide](../slides/buffer-overflows.html#/4/17).  You need to modify that code to print out the required string ("Not a virus" with a newline) -- the `0x0a` at the end of that string is the newline.  Note that for this assignment, you don't have to remove null bytes (0x00), as we are not having this machine code read in by a `scanf()` call.

As we want to return to the main program after our assembly routine runs, you need to end that `print` assembly subroutine with a `ret`.

Put that assembly code into a file called `print.s`, and create a `part1.c` file:

```
extern void print();
int main() {
	print();
	return 0;
}
```

You should put all this into a Makefile, which should look like:

```
all:
	nasm -g -f elf64 -o print.o print.s
	gcc -c part1.c
	gcc -o part1 part1.o print.o
```

Remember that the indentation is tabs, not spaces!  The executable here is just `part1`.  You are welcome to use the Makefile provided above, and add your lines to that.  Just be sure to put *your* compilation lines *above* what is already in the Makefile.  At this point, you should be able to type `make` it should compile, and it should print:

```
Not a virus
```

#### What to submit

The files to submit from this part are your `print.s`, `part1.c`, and `Makefile`.

<!-- ============================================================= -->

### Part 2: Hard-coded

We are going to make a modification to the [test1](tricky-jump/test1) executable file.  This step has you making modifications to specific hard-coded addresses -- which means it will only work with that one executable file.

This executable was compiled from [test1.c](tricky-jump/test1.c.html) ([src](tricky-jump/test1.c)), [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)), and this [Makefile](tricky-jump/Makefile.html) ([src](tricky-jump/Makefile)).  Note that if you compile it, you might get a slightly different binary version (due to compiler differences, etc.), so you should use the provided test1](tricky-jump/test1) binary file.  You can also see the [objdump of test1](tricky-jump/test1.objdump.txt) and the [hexdump of test1](tricky-jump/test1.hexdump.txt).

This part should be in a file called `part2.py`.

#### Your inserted code

You need to run `objdump -d` on your `part1` executable that you created above.  Look for the `print()` subroutine.  This will have three parts: the `jmp`, the string itself, and the body of the subroutine (which ends with a `ret` (0xc3)).  Yours will likely be around 40 bytes -- it's fine if it's more or less, as long as it prints the desired output.  These hex bytes are what you are going to write to the executable.

#### Inserted code target

If you look at the [objdump of test1](tricky-jump/test1.objdump.txt), you will see an entire subroutine of `nop` instructions; that subroutine is called `nops()`.  While this is quite convenient for us, it's not realistic -- but a good start for our first binary executable modification.  Pick any spot at the beginning of that set of `nop` instructions.  Remember the address you chose, but remember that the addresses in the `objdump` file are in hex.


#### `push`

We are going to write a `push` command right before the `ret`.  The format we are going to use is 5 bytes: 0x68 followed by the four bytes of the value we are pushing (in little-Endian!).  The value you are writing is the absolute address of the jump target -- what you chose above -- in the file.


#### Modifying `main()`

If you look at the objdump, you can see the two assembly instructions right before the `ret` in `main()`:

```
40113d:	b8 00 00 00 00       	mov    $0x0,%eax
401142:	48 83 c4 08          	add    $0x8,%rsp
```

The address listed is 0x40113d, and if you remove the leading '0x40', you get the address in the file: byte 0x113d to 0x1145.  You can also see this in the hexdump:

```
00001130  05 cf 0e 00 00 48 89 c7  e8 f3 fe ff ff b8 00 00  |.....H..........|
00001140  00 00 48 83 c4 08 c3 66  0f 1f 84 00 00 00 00 00  |..H....f........|
```

These nine bytes are going to be replaced with the five byte `push` instruction, and four bytes of `nop` instructions (0x90).  It doesn't matter if you put the `nop` instructions before the `push` or after.

Save those nine bytes that you are about to overwrite!  Then overwrite them with four `nop` instructions and the `push` instruction.  Remember that the address for the `push` instruction is in little-Endian, so you have to reverse the bytes.


#### Writing your code

You need to write the 40 (or so) bytes of your `print()` code to the address you chose above.  As this is in Python, you can read the `test1` file into an array of bytes via:

```
with open("test1","rb") as f:
	bin = list(f.read())
```

This opens the file in binary mode, reads in the entire file in one go as a `bytes` type, and then converts that to a list of bytes (which will be printed as base-10 integers if you print it out).

You are going to write your `print()` code to that address.  HOWEVER, you have to put in the saved instructions from `main()` -- those 9 bytes -- before the `ret`.  So you are going to write all of `print()` *except* the ret, then the 9 bytes of saved instructions, then the ret (0xc3).


#### Outputting the file

Your output file should be called `test1mod` -- do NOT overwrite `test1`!

In Python, given a list of byte values, you can write the file as such:

```
binout = b''
for i in bin:
	binout += bytes.fromhex("0x{:02x}".format(i).replace('0x',''))
with open("test1mod","wb") as f:
	f.write(binout)
```

#### Ensuring it works

Run your program!  It should look like this:

```
$ ./test1mod 
hello world
Not a virus
$
```

If it didn't work, here are a few things to try:

- Make sure you are running this after running `setarch $(uname -m) -L -R /bin/bash`, otherwise this won't work
- Run `objdump -d` on your `test1mod` file, and look at where you made changes -- both at the end of `main()` and the inserted code in the `nops()` function
- Compare the two objdumps (the [one provided](tricky-jump/test1.objdump.txt) and the one you generated in the bullet point above) to see the differences
- Run it through gdb, the debugger.  You can see a list of gdb commands in the [buffer overflow](hw-buffer-tabbed.html) ([md](hw-buffer.md)) homework.  Here are a few more useful gdb commands:
	- `x/2x $rsp` will show you the 8-byte value in the rsp register -- you want to make sure that is 0x1160 right before the `ret` at the end of `main()`


#### What to submit

The file to submit from this part is `part2.py`.

<!-- ============================================================= -->

### Step 3: Variable target

The previous section had you hard-code addresses -- both where in `main()` to replace the instructions, and also where in the file to put your payload.  This part will have you determine those two addresses.  Once they are determined, the program will be much the same as the previous part.

There are a few different pieces of information that you will need to determine:

- Where the `.text` section starts in the file
- Where in `main()` the `ret` is
- Identify 5 (or more) bytes of instructions before the `ret` to replace with your `push` opcode
- Where to put the payload

We will look at teach of these in turn.   As you are working on determining those four parts, you can hard-code the values for the `test` binary so that you can work on the other parts.

Your code for this part must be in a file called `part3.py`.

#### Where `.text` starts

This starts at 0x1040 in `test1`.

The best way to do this is to read through the section header table in an ELF file.  But it turns out there is a shortcut for the programs we are dealing with in this assignment.

The entry point address is, for the programs in this assignment, the same as the start of the `.text` section.  And that address is at bytes 0x18 to 0x1b.  You can see that in the hexdump of `test1`:

```
00000010  02 00 3e 00 01 00 00 00  40 10 40 00 00 00 00 00  |..>.....@.@.....|
```

That's little-Endian for 0x00401040.  We remove the leading '0x0040', so we really only want bytes 0x18 and 0x19: `0x1040`.  That's the start of `.text`.

#### Where in `main()` the `ret` is

This is at 0x1146 in `test1`.

For our purposes, we will just look for a `ret` opcode in binary (0xc3).  It's possible that a 0xc3 byte is present due to being part of another instruction, such as a data value.  If we can identify the instructions *before* the `ret` (that's next), then we can safely assume that we have found a `ret` and not a data value.

#### Identify previous instructions

For `test1`, these are the nine bytes of instructions that you dealt with in the previous part (bytes 0x113d to 0x1145).

Your program needs to analyze the bytes before the `ret` to determine which instructions they are, and how many bytes each instruction is.  For our purposes, we will assume that the instructions before the `ret` will always be one of: `add`, `mov`, `pop`, and `nop`.  This is a reasonable assumption in general, as those are the types of instructions that a compiler will put at the end of a subroutine.  All the test files provided here, and that will be used for grading, will follow this assumption.

You can see how these instructions translate into machine code in the next section.  For each instruction, we are only using the 2 (or so) most common encodings.  This means there are a total of 7 possible patterns to look for (as `nop` only has one encoding).  Your program needs five bytes to write the `push` instruction, so you should continue to identify instructions until you get to five (or more) bytes.  If you get to more than 5 bytes, you will use `nop` instructions, as you did in the previous part.

If your code does not find the correct instructions before a `ret`, then it should continue looking forward in the file for the next `ret`, and then analyze the bytes before that one.  This could happen because you did not find an actual `ret` (a 0xc3 byte was a data value, for example), or if the instructions before the `ret` were not of the form that you need to idenfity.  If you hit the end of the file, then output "Unable to find a suitable ret".  

#### Where to put the payload

You identified this location for `test1` in the previous part.

The programs we are using have a large set of consecutive `nop` statements for you to put your payload in.  However big your payload is (likely around 40 or so bytes), you should look for a sequence of `nop` bytes (0x90) that is of that length.  That is where you should put your payload.

#### Putting it all together

When you can determine the four previous data items, you can use your binary modification code from part 2.  You can test this on the three executable files provided with this assignment:

- [test1.c](tricky-jump/test1.c.html) ([src](tricky-jump/test1.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test1](tricky-jump/test1) executable file.  You can also see the [objdump -d](tricky-jump/test1.objdump.txt), the [hexdump -C](tricky-jump/test1.hexdump.txt), and the [results of readelf](tricky-jump/test1.readelf.txt).
	- This is the one used in the previous part; `main()` starts at 0x1126, the commands to replace are at 0x113d to 0x1145.
- [test2.c](tricky-jump/test2.c.html) ([src](tricky-jump/test2.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test2](tricky-jump/test2) executable file.  You can also see the [objdump -d](tricky-jump/test2.objdump.txt), the [hexdump -C](tricky-jump/test2.hexdump.txt), and the [results of readelf](tricky-jump/test2.readelf.txt).
	- This has a subroutine (conveniently named `subroutine()`) before `main()`, so the first `ret` that you find will be the `ret` from `subroutine`.
- [test3.c](tricky-jump/test3.c.html) ([src](tricky-jump/test3.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test3](tricky-jump/test3) executable file.  You can also see the [objdump -d](tricky-jump/test3.objdump.txt), the [hexdump -C](tricky-jump/test3.hexdump.txt), and the [results of readelf](tricky-jump/test3.readelf.txt).
	- This is similar to the previous one (`subroutine()` is first), but with a different way of printing "hello world" to give you an executable with different addresses to test your code on.
- [test4.c](tricky-jump/test4.c.html) ([src](tricky-jump/test4.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test4](tricky-jump/test4) executable file.  You can also see the [objdump -d](tricky-jump/test4.objdump.txt), the [hexdump -C](tricky-jump/test4.hexdump.txt), and the [results of readelf](tricky-jump/test4.readelf.txt).
	- This is similar to the previous one (`subroutine()` is first), but with a different way of printing "hello world" to give you an executable with different addresses to test your code on.
- [test5.c](tricky-jump/test5.c.html) ([src](tricky-jump/test5.c)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test5](tricky-jump/test5) executable file.  You can also see the [objdump -d](tricky-jump/test5.objdump.txt), the [hexdump -C](tricky-jump/test5.hexdump.txt), and the [results of readelf](tricky-jump/test5.readelf.txt).
	- `subroutine()` is before `main()` in the `.text` section, but it does NOT have the assumed opcodes before it, so your program should skip over that one and modify the `ret` in `main()`.
- [test6.cpp](tricky-jump/test6.cpp.html) ([src](tricky-jump/test6.cpp)) and [nops.s](tricky-jump/nops.s.html) ([src](tricky-jump/nops.s)) compile to the [test6](tricky-jump/test6) executable file.  You can also see the [objdump -d](tricky-jump/test6.objdump.txt), the [hexdump -C](tricky-jump/test6.hexdump.txt), and the [results of readelf](tricky-jump/test6.readelf.txt).
	- This program has a different entry point address (0x1070) than the others (0x1040).  It does not have a subroutine, so the `ret` in `main()` is what should be modified.

You are welcome to create your own, but check (with `objdump -d`) that the assembly instructions before the `ret` are what you expect them to be.

#### What to submit

The file to submit from this part is `part3.py`.

<!-- ============================================================= -->


### Machine code

When a compiler compiles a function, there are three parts: the prologue, the function body, and the epilogue.  The *prologue* sets up the function -- allocation of local variables, and saving registers that it is going to overwrite.  The *function body* is the compiled code from the C code.  The *epilogue* will deallocate local variables, restore registers, and then calls `ret`.

If the epilogue is of a standard format (a safe assumption for this assignment), then there are only a few instructions that will appear immediately before the the `ret`: `mov`, `pop`, `add`, and `nop`.  It is certainly possible to have other instructions before the `ret` -- especially if it's a simpler subroutine -- but we are going to assume that it is only going to be one of those.  All the test files will follow this assumption.

Thus, we only have to scan for those three types of instructions before the `ret`.

We are furthermore assuming that the instructions will only be 64-bit instructions using 64-bit registers.  Thus, `pop %rax` is a valid instruction for this assignment, but `pop %eax` is not.

This assignment makes a number of assumptions about the assembly code structure in an effort to make this assignment more feasible.  These assumptions are listed below.

#### The `add` instruction

Any `add` instruction in the epilogue is adding a value to `rsp`, and that is the only type of add we have to consider for this assignment.  These instructions can be in one of two forms:

- `0x48 0x83 0xc4 0xXX` will add 0xXX to %rsp, where 0xXX is the value in hex.  This assumes 0xXX is less than or equal to 0xff (255).
- `0x48 0x81 0xc4 0xXX 0xXX 0xXX 0xXX` will add the 4-byte value (0xXX 0xXX 0xXX 0xXX) to %rsp.  Note that the second digit is 0x81 here, and not 0x83 as above.  Also note that the value is in ***little Endian***.  So the hex instruction `0x48 0x81 0xc4 0x12 0x34 0x56 0x78` will add 0x12345678 (little-Endian) to %rsp, which is 0x78563412 in big-Endian format.

#### The `mov` instruction

There are many encodings of the `mov` instruction, but we will only study a few such forms:

- `0xb8 0xXX 0xXX 0xXX 0xXX` will move the value `0xXX 0xXX 0xXX 0xXX` (in little-Endian) into the %eax register; this is setting up the return value
- ...

#### The `pop` instruction

The `pop` instruction can take a few forms:

| Instruction | Hex encoding|
|--------|--------|
| pop %rax | 58 |
| pop %rbx | 5b |
| pop %rcx | 59 |
| pop %rdx | 5a |
| pop %rbp | 5d |
| pop %rdi | 5f |
| pop %rsi | 5e |
| pop %r8 | 41 58 |
| pop %r9 | 41 59 |
| pop %r10 | 41 5a |
| pop %r11 | 41 5b |
| pop %r12 | 41 5c |
| pop %r13 | 41 5d |
| pop %r14 | 41 5e |
| pop %r15 | 41 5f |

Nobody should pop into %rsp, so it is not listed above.

Thus, the `pop` instructions we have to look at are hex 0x58 to 0x5e, and hex 0x41 0x58 to 0x41 0x5f.

#### The `nop` instruction

This will occasionally appear.  It's a single byte of value 0x90.

<!-- ============================================================= -->


### Submission

You should submit to Gradescope the five files that you developed:

- From part 1: `print.s`, `part1.c`, and `Makefile`
- From part 2: `part2.py`
- From part 3: `part3.py`

If you did not finish all the parts, submit a blank file of the same name for the parts you did not finish -- otherwise Gradescope will fail on the file-check part, and never get to giving you partial credit.

