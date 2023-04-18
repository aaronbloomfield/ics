ICS: Programming Homework: Buffer Overflow
==========================================

[Go up to the ICS HW page](index.html) ([md](index.md))


### Purpose 

This assignment will have you implement a shellcode-based buffer overflow attack against a program executable.  You will need to be familiar with the content in the [buffer overflow slide set](../slides/buffer-overflows.html#/), specifically the [how to do it](../slides/buffer-overflows.html#/how2doit) column of slides.  That and the lecture recordings will guide you through many of the steps of this assignment.

This assignment has five parts (or tasks), and are meant to be done in order, as that will help guide you through the assignment.  In any case, be sure to do task 5 -- a brief write-up -- as that will help us tell how far you got in the assignment.

#### Getting stuck?

There are many parts of this assignment where it is easy to get stuck. You can look at the troubleshooting section, and the end of this page, for some hints.  We provide a *lot* of details in each section -- try reading through those again.  We know it's a lot to read (and to read repeatedly), but one can easily miss something the first time (or two) through it.


#### Reference platform

This program must run on the Cyber Range account.  You are welcome to develop it on your own machine, but make sure it works there before you submit it.  And if it isn't working on your machine, try it on the Cyber Range to see if that allows it to work.


### Changelog

Any changes to this page will be put here for easy reference.  Typo fixes and minor clarifications are not listed here.  So far there aren't any significant changes to report.


### Executable

#### grade.c

The executable we are attacking is called `grade`.  When run, you enter your name, and it will tell you your grade.  An example run of the program:

```
$ ./grade
Please enter your name:
Albert Einstein

Albert Einstein, your grade on this assignment is a F
$
```

Note that the first instance of the name in the output above is what the user input, and the second is what the program printed back.

The grade reported by this program is the grade you will receive on (part of) this assignment.

The source code for this program is available: [grade.c](buffer/grade.c.html) ([src](buffer/grade.c)).  It MUST be compiled with the following command:

```
gcc -fno-stack-protector -m64 -fomit-frame-pointer -o grade grade.c
```

You are welcome to add the `-g` option to help with debugging, but when we test your program, we will not add the `-g` option.

Your job is to create a shellcode-based buffer overflow attack that will assign you a different grade.  For example:

```
$ ./grade < input.txt
Please enter your name:
Albert Einstein, your grade on this assignment is an A
$
```

The contents of input.txt are output by a program that you will write in tasks 3 & 4.

The `--print-buffer-address` is described below.

### ASLR

Address Space Layout Randomization (ASLR) is an operating system defense against buffer overflows.  Consider the following program, which is available as [stack-addr.c](buffer/stack-addr.c.html) ([src](buffer/stack-addr.c)):

```
#include <stdio.h>
int main() {
  char buffer[256];
  printf ("%.8lx\n",(unsigned long)buffer);
}
```

All it does is print out the address of a scoped variable, which means the variable is on the stack.  We would expect that the variable is at the same position on the stack, relative to the top, for each execution.  We compile it (via `gcc -o stack-addr stack-addr.c`) and run it a number of times:

```
$ ./stack-addr 
9dbfc92c
$ ./stack-addr 
773ee00c
$ ./stack-addr 
b4f4c21c
$ ./stack-addr 
e811414c
$ ./stack-addr 
0f0e077c
$ ./stack-addr 
be185c6c
$ 
```

The stack position keeps changing.  Actually what is happening is the address where the stack *starts* keeps changing -- `buffer` is still in the same spot relative to that end of the stack, but the address of that end is what varies each time.  This is an operating system defense to make buffer overflows quite difficult, since the address of a buffer keeps changing.

For this assignment, we need to turn that defense off.

We are going to run it differently.  Try: `setarch $(uname -m) -L -R ./stack-addr`.  The `-R` option tells it to turn off ASLR for the program being run.  The `-L` flag tells it to use an older memory model that is more appropriate for our first buffer overflow.  You can execute this command many times, and you will get the exact same stack address each time.

However, it's annoying to do that each time, so we can just do it once: `setarch $(uname -m) -L -R /bin/bash`.  This runs bash -- the shell -- and any commands run in that shell will not have ASLR turned on.  You can now run `./stack-addr` many times, and get the same address each time.

We will use the `setarch` command when we run grade.  So your buffer overflow will run via:

`setarch $(uname -m) -L -R ./stack-addr ./grade < input.txt`

The `input.txt` part is explained below.


### Buffer Address

As this is a first take at a stack buffer overflow, we are making the project more viable.  Specifically, we will provide the address of the buffer that your program can use when determining the buffer contents.

If you run the `grade` executable with the `--print-buffer-address` flag, it will show the address of the buffer:

```
$ ./grade --print-buffer-address
7ffca71f59f0
$ ./grade --print-buffer-address
7fff6deb9320
$ ./grade --print-buffer-address
7ffe5a3aefb0
$ ./grade --print-buffer-address
7ffd3328b150
$
```

Again, we see that the address changes each time.  So we can run it with `setarch` in one of two ways.  First, each time we call the program:

```
$ setarch $(uname -m) -L -R ./grade --print-buffer-address
7fffffffe050
$ setarch $(uname -m) -L -R ./grade --print-buffer-address
7fffffffe050
$ setarch $(uname -m) -L -R ./grade --print-buffer-address
7fffffffe050
$ setarch $(uname -m) -L -R ./grade --print-buffer-address
7fffffffe050
$
```

Or just once to run `bash` and then run the program:

```
$ ./grade --print-buffer-address
7fffffffe030
$ ./grade --print-buffer-address
7fffffffe030
$ ./grade --print-buffer-address
7fffffffe030
$ ./grade --print-buffer-address
7fffffffe030
$ 
```

Prior to running your program, there will be an `address.txt` file that will contain the (hex) address of the buffer.  We will generate it as such:

```
$ setarch $(uname -m) -L -R ./grade --print-buffer-address > address.txt
$
```

You can then read in that file to determine the address of the buffer.  The following C code will do that for you:

```
#include <stdio.h>
void main() {
  void* buffer;
  FILE *fp = fopen("address.txt","r");
  fscanf(fp, "%lx", (unsigned long)&buffer);
  fclose(fp);
  printf("Buffer address: %lx\n",buffer);
}
```

You may assume that the `address.txt` file is present, properly formed, and has the correct buffer address value.

### Step 1: Makefile

Not much to do here yet, but we are going to call `make` to compile your code.  All of your compilation lines must be in that Makefile, and all under one target.  Most of the following steps will have you add some lines to the Makefile.  A collection of all the compilation commands is at the end of this document (in the Submission section).

If you forget how to write a Makefile, you can see [this Makefile tutorial](https://uva-cs.github.io/pdr/tutorials/05-make/index.html), and [this C/assembly Makefile example](https://uva-cs.github.io/pdr/labs/lab08-64bit/index.html), or any other reference online.

You may want to put the grade.c compilation line, from above, into your Makefile.  So far, your Makefile should look like this:

```
all:
    gcc -fno-stack-protector -m64 -fomit-frame-pointer -o grade grade.c
```

Note that the indentation must be a tab, not spaces!


### Step 2: Shellcode

Your first step is to create shellcode that will print out the grade you want to receive, and then gracefully exit.  This should be in a file called `shellcode.s`.  Recall that you will have to remove all end-of-string characters from the machine code generated.  While this will mostly be 0x00 bytes, you will also have to check for newlines (0x0a) and carriage returns (0x0d).  The [buffer overflow slide set](../slides/buffer-overflows.html#/) goes over how to do this.

#### C Code

To test that your code works, you will need a `main()` function to call your shellcode; this will go into a `shellcode_test.c` file. Here is an example such file (you are welcome to use this as-is or modify it):

```
#include <stdio.h>
#include <sys/mman.h>

extern void shellcode();

void main() {
  int on_stack;
  mprotect((char *)((long)&on_stack & -0x1000), 1, PROT_READ | PROT_WRITE | PROT_EXEC);
  mprotect((char *)((long)&main & -0x1000), 1, PROT_READ | PROT_WRITE | PROT_EXEC);
  shellcode();
  printf ("After shell code is executed.\n");
}
```

The `mprotect()` calls work the same as described in the grade.c program ([HTML](buffer/grade.c.html), [C](buffer/grade.c)) -- they allow execution on the stack (the first call) and modifying the machine code (the second call).  Needless to say, this requires that your assembly routine be called `shellcode`.

This file is provided in [shellcode_test.c](buffer/shellcode_test.c.html) ([src](buffer/shellcode_test.c)).

#### Assembly code

Your assembly code will go in a `shellcode.s` file; here is the start fo that file:

```
; shellcode.s

  global shellcode

  section .text

shellcode:

  ; your code here

  ; for now, a ret, but you will want to eventually remove it
  ret
```

This file is provided in [shellcode_test.c](buffer/shellcode.s.html) ([src](buffer/shellcode.s)).

Forget assembly?  Here are some CS 2150 assembly references: [slides](https://uva-cs.github.io/pdr/slides/08-assembly-64bit.html#/), labs ([1](https://uva-cs.github.io/pdr/labs/lab08-64bit/index.html) & [2](https://uva-cs.github.io/pdr/labs/lab09-64bit/index.html)), book chapters ([1](https://uva-cs.github.io/pdr/book/x86-64bit-asm-chapter.pdf) & [2](https://uva-cs.github.io/pdr/book/x86-64bit-ccc-chapter.pdf)), and/or [recommended online document](https://www.cs.cmu.edu/~fp/courses/15213-s07/misc/asm64-handout.pdf).


#### Makefile

Your Makefile will need to have three lines to compile this program: one to call nasm to compile shellcode.s, one to compile shellcode_test.c, and one to link them together.

You will add the following lines to your Makefile (remember that the indents are a single tab, not spaces):

```
nasm -g -f elf64 -o shellcode.o shellcode.s
gcc -g -m64 -c shellcode_test.c
gcc -g -no-pie -m64 -o shellcode_test shellcode.o shellcode_test.o
```

This goes after the line to compile grade.c from the previous step.


Some VERY important notes for compilation:

- We are using C, not C++, so you will have to use the `gcc` (not `clang`) compiler.
- We are doing this as a 64-bit program, so you will have to pass `-f elf64` to nasm, and `-m64` to BOTH of the gcc compilation commands.
- You will have to add the `-no-pie` flag to the final link line.  (If you are interested, the default for the current version of gcc is to create a position independent executable (PIE), which means the addresses for everything are relative, not absolute.  This does not work with what we are compiling, so we turn it off with that flag.)
- Your compiled shellcode.s file should be named `shellcode.o` -- we are going to run a disassembler on that file, so please name it correctly.
- Your final binary should be called `shellcode_test` -- we are going to execute it, so please name it correctly.


#### Notes and hints

Some notes and hints for developing this shellcode:

- If you forget how to write assembly, see the appropriate [slides](https://uva-cs.github.io/pdr/slides/08-assembly-64bit.html#/), labs ([1](https://uva-cs.github.io/pdr/labs/lab08-64bit/index.html) & [2](https://uva-cs.github.io/pdr/labs/lab09-64bit/index.html)), book chapters ([1](https://uva-cs.github.io/pdr/book/x86-64bit-asm-chapter.pdf) & [2](https://uva-cs.github.io/pdr/book/x86-64bit-ccc-chapter.pdf)), and/or [recommended online document](https://www.cs.cmu.edu/~fp/courses/15213-s07/misc/asm64-handout.pdf) from CS 2150.
- You can use `objdump -d shellcode.o` to see the machine language that your assembly opcodes compiled into.
- The system calls for 32-bit x86 are DIFFERENT than those for 64-bit x86.  In particular, syscall 1 is exit in 32-bit but is sys_write in 64-bit.  We want this to be a 64-bit program, so be sure to use the syscalls that are listed [here](http://blog.rchapman.org/posts/Linux_System_Call_Table_for_x86_64/). You are welcome to use any other online table for your syscall reference, but be SURE it's the 64-bit version.
- When invoking `syscall` in assembly, it looks at the *entire* register rax for the syscall function.  So if you only set the value in the lower 32 bits (i.e., `mov eax, 1`), then the overall value in `rax` may not be 1 if any of the upper 32 bits were set previously (which is likely), and you will not get the syscall you expect.  So be sure to zero out the 64-bit versions of all the registers that syscall is using.
- Note that `syscall` can modify the registers, so if the value in rax was 1 before syscall was invoked, it may be quite different afterward -- so zero out your registers again for your second syscall invocation.
- As mentioned in the slides, you will want your string to be in your .text section.
- To save yourself a headache, don't bother putting in a newline at the end of the string at this time.


Where to start?

- First, get the C code (from above) properly compiling with some assembly code -- you can adapt the vecsum example from the [first x86 lab in CS 2150](https://uva-cs.github.io/pdr/labs/lab08-64bit/index.html) for this.
- Make sure your Makefile can compile the code
- Review the [buffer overflow slides](../slides/buffer-overflows.html#/), specifically the [how to do it](../slides/buffer-overflows.html#/how2doit) column
- Change the assembly code to print out a string (via a `syscall`)
- Add more assembly code to gracefully exit (also via a `syscall`)
- View the machine code (via `objdump -d`), and work on removing all the invalid characters (mostly 0x00 bytes).
- As a sanity check, make sure that none of the other invalid bytes are present in the machine code other than 0x00: newlines (0x0a), carriage returns (0x0d), tabs (0x09), vertical tab (0x0b), and spaces (0x20).

### Task 3: Overflow

This is what we are all here for: the buffer overflow itself. Presumably, from task 2, you have shell code that does not contain any 0x00 bytes (or any of the other token-ending characters), prints out the grade you want, and then exits.

In this part, you are going to create a C program called `attack_shellcode.c` that will write the carefully constructed data to stdout.  You can run your program as follows (this is how we will do it to test it):

```
$ ./attack_shellcode > input.txt
```

As your program will be writing binary data, the input.txt will be binary.  You can use `hexdump -C` to view the contents of that file -- but make sure you put the `-C` parameter in there!

Needless to say, be sure to name your executable `attack_shellcode`. You will then be able to run the buffer overflow via `./grade < input.txt`, as shown above.  Recall that the `grade` executable was compiled from the grade.c source code, as shown above.

The [buffer overflow slide set](../slides/buffer-overflows.html#/) will be a useful reference for this.  The output of your `attack_shellcode` executable, which is what is stored in input.txt, will likely have three parts:

- The NOP sled
- The shellcode
- The return address

#### Debugger

You are going to have become friendly with gdb -- there is no way around that.  Here are a bunch of commands that will be of help; you can also see the [CS 2150 GDB tutorial](https://uva-cs.github.io/pdr/tutorials/02-gdb/index.html) (although some of the commands below are not listed there).

- 'n' steps *over* the next instruction, so it will pause (break) at the instruction following the one it is currently stopped at
- `stepi` steps into the assembly being executed -- this will be necessary to trace your shellcode.
- `layout asm` will split the screen so that the assembly being executed is shown at the top (with an arrow at the current instruction pointer), and a place to enter commands below.  This can be *very* useful for tracing your assembly, especially once it is on the stack.
- If you want to repeat the last command, just hit Enter -- this is particularly useful when you are repeatedly stepping through assembly commands via `stepi`.
- To print the value in a register, use `p/x $rsi`
- When paused in a part of the executable code, you can see the assembly listing via `disassemble` (or just `disas`).  However, when you are executing on the stack, gdb can't figure out what to disassemble, so it won't show anything via a `disas` command. Instead, you can give it a range, such as: `disassemble 0x7fffffffdcb0,0x7fffffffdd30`. As x86 instructions are variable length, if your start address is not on an instruction boundary, you will not get the output you intend.
- To print a number of values on the stack, try `x/40xw $rsp`.  Note that this displays the data in little Endian, whereas `hexdump -C` (see below) displays it in big Endian.


#### Notes and hints

Some other notes and tips:

- We are not, at this time, expecting a newline to be printed at the end of your desired grade -- that's the next task.
- Nothing here will work unless you have run `setarch x86_64 -v -LR bash` first, as described in the [buffer overflow slide set](../slides/buffer-overflows.html#/).
- The address of the `name` buffer in `vulnerable()` MUST be specified as described below, otherwise your program will not work and you will not receive credit for that part
- When you are *not* running it in gdb, the buffer address will change slightly.  It's up to you to figure out what that is (you can modify grade.c to find out; just be sure to change it back).
- You can look at your created file via `hexdump -C input.txt`.  With the `-C` parameter, hexdump will display it in big Endian (different parameters to hexdump will show it in different Endian-ness).
- In your `attack_shellcode` program, you may be tempted to use [puts()](http://www.cplusplus.com/reference/cstdio/puts/) to write your data to standard output.  However, `puts()` will write a newline character ('\\n', hex value 0x0a) after the data it prints. You can use [fputs()](http://www.cplusplus.com/reference/cstdio/fputs/) instead (use `stdout` as the file descriptor) -- this will print to standard output without the trailing newline.

#### Makefile

Your Makefile will need to compile your program into an executable named `attack_shellcode`.  A possible compilation line might look like the following -- this would also go into your Makefile.

```
gcc -o attack_shellcode attack_shellcode.c
```


### Task 4: Newline

***BACK UP YOUR CODE FIRST!!!*** You are about to modify your program, and if things go poorly, then you want to be able to revert back to the code that successfully completes tasks 3 and 4.

If you have made it this far, then your code, when run, looks something like:

```
$ ./grade < input.txt
Please enter your name:
Albert Einstein, your grade on this assignment is an A$
```

You will notice that the prompt (the '$') is on the same line as the desired grade because of the lack of a newline character in the string.  You obviously can not put a newline in the string itself, as that would indicate end-of-string, and the rest of your carefully constructed data in input.txt would never make it onto the stack -- so your buffer overflow attack would never occur.

For this task, you are to modify your shellcode (in shellcode.s), and your attack_shellcode.c file, to put a newline at the end of the string.  You will likely want to compute that value and store it in the string, since you can't include a newline in the shellcode itself. You should test this out via your `shellcode_test` program first. Note that one of the `mprotect()` calls in the provided shellcode_test.c allows for modification of the .text section, so such a modification will work in `shellcode_test`.

This is just a modification of the attack_shellcode.c file, so your modifications for this task can overwrite parts from the last two tasks.  For grading purposes, if you did this task, then you obviously did the previous two tasks.

### Task 5: Write-up

This is a *brief* write-up, and should be in the [buffer.py](buffer/buffer.py.html) ([src](buffer/buffer.py)) file -- just fill in the blanks as necessary.

Please include, as a file named writeup.pdf, the following information:

- Your name and userid
- How far did you get (i.e., which tasks did you complete)?
- What thing(s) did you get stuck on for this assignment?
- Compared to some of the harder assignments you've done -- such as CS 2150's hash lab -- how hard was this assignment?
- Other than just giving the answers, what could we do to make this assignment run more smoothly in the future?

We are not looking for any significant length here, just candid answers.


### Troubleshooting

You are likely to run into problems, which will probably be segmentation faults.  Here are some ideas about how to solve that.

Recall that g++ often leaves some extra space between the buffer and the return address.  How much?  You have to figure that out yourself. You can look at the assembly generated for grade.c (run `g++ -S -o grade.s grade.c`) to start.

One issue is to figure out whether you are setting the return address correctly -- if not, that would be the cause of the segfault.  First, we need to find what the return address should be.  To do this, we first need to find the return address from `vulnerable()` back to `main()`.  Run `objdump -d grade`, and find the line *after* the call (in `main()`) to `vulnerable()` -- that is the return address from vulnerable.  Next, load up the executable in gdb, and set up a break point right when you enter into the `vulnerable()` function.  You can list the stack with a command such as, `x/100xw $rsp`.  This will print out 100 hex values, each one being 4 bytes.  A typical gdb display will list four 32-bit (4 byte) values per line, which is 16 bytes per line.  Since you know the size of the buffer (200 bytes), the return address is likely to be about 200/16 lines down, or about a dozen lines down.  Keep in mind that a return address is 64 bits, which will be *two* of the 32-bit values listed in this display.  That return address should (more or less) match the value you gleaned from the objdump command.  Once you have the return address, figure out the command to just print out that return address: `x/2xw 0x7fffffffdcc0` might work (if the return address were located at 0x7fffffffdcc0). Next, set up a break point for the *end* of the `vulnerable()` function.  Check that return address value again -- it should look similar to (but not the exact same value as) the value in the rsp register (`print $rsp`).  When split among two values, it will list like, `0xffffde00 0x00007fff`.  Once you see that value, it should be the exact address of the `name` buffer.  If not, then your program is going to seg fault.

If you are sure the return address is set correctly, the next step is to trace the injected assembly.  Set a break point for the end of the `vulnerable()` function.  If you set it *before* the last function call (the `strncpy()` call), hit `n` to step over that function.  You will then start using `stepi` to single-step through the assembly instructions.  Running `layout asm` will help the trace.  You should step through your assembly code to make sure it works -- presumably there will be the nop sled, then your injected code.  Note that x86 instructions are of a different lengths, so if your jump is off, it may jump to the middle of an instruction, and you will get an illegal instruction error.  You can disassemble a region of memory with a command such as `disassemble 0x7fffffffdcb0,0x7fffffffdd30`.  Also note that when you are disassembling the contents of the stack, gdb will attempt to map the string (that you should receive, presumably, an A) to x86 instructions, many of which will be invalid.  But if you do a disassembly from the start of your shell code (which is the target of your initial jump), you should see the assembly that you wrote.

### Submission

You are going to submit five files:

- `Makefile`: this calls four compilation lines (three from task 2, and one from task 3; the line for task 3 will also apply to tasks 4 and 5).
    - Make sure that things are compiled to the right names! shellcode.s should compile to shellcode.o, all of task 2 should compile into the `shellcode_test` executable, and the code from task 3 should compile into the `attack_shellcode` executable.
  - You can also include the compilation line for grade.c or not -- we will provide it during testing regardless.
- `shellcode.s` and `shellcode_test.c` from task 2
- `attack_shellcode.c` from task 3 & 4 (if you completed task 4, just submit that version, as that shows you also completed task 3)
- `buffer.py` from task 5

If you did not get to a section, you will still have to submit the required files, as the submission will check that all 5 files are present.  You can just create an empty file (or a file with `hello world` in it) if you did not get to that part.
