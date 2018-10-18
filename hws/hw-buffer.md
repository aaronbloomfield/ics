ICS: Programming Homework: Buffer Overflow
==========================================

[Go up to the ICS HW page](index.html) ([md](index.md))

You will want to see the
[homeworks policies page](../uva/hw-policies.html)
([md](../uva/hw-policies.md)) for formatting and other details.  The
due dates are listed on the [UVa course page](../uva/index.html)
([md](../uva/index.md)).


### Purpose 

This assignment will have you implement a shellcode-based buffer
overflow attack against a program executable.  You will need to be
familiar with the content in the
[buffer overflow slide set](../slides/buffer-overflows.html#/).

This assignment has five parts (or tasks), where tasks 2, 3, and 4
are parts to a full buffer overflow attack, and thus are typically
done in order.


### Reference platform

This program must run on the 64-bit Ubuntu 18.04 VirtualBox image
provided to the class.


### Relevant links

These links are all described below, but are included here, all in one place.

- The [buffer overflow slide set](../slides/buffer-overflows.html#/)
- The CS 2150 [Makefile tutorial](https://uva-cs.github.io/pdr/tutorials/05-make/index.html),
- CS 2150 assembly references:
  [slides](https://uva-cs.github.io/pdr/slides/08-assembly-64bit.html#/),
  labs ([1](https://uva-cs.github.io/pdr/labs/lab08-64bit/index.html)
  & [2](https://uva-cs.github.io/pdr/labs/lab09-64bit/index.html)),
  book chapters
  ([1](https://uva-cs.github.io/pdr/book/x86-64bit-asm-chapter.pdf) &
  [2](https://uva-cs.github.io/pdr/book/x86-64bit-ccc-chapter.pdf)),
  and/or
  [recommended online document](https://www.cs.cmu.edu/~fp/courses/15213-s07/misc/asm64-handout.pdf)
- The source code (grade.c) for the executable to be attacked:
  [HTML version](buffer/grade.c.html), [C version](buffer/grade.c)
- [Linux 64-bit syscall reference](http://blog.rchapman.org/posts/Linux_System_Call_Table_for_x86_64/)
- The [CS 2150 GDB tutorial](https://uva-cs.github.io/pdr/tutorials/02-gdb/index.html)

### The executable target

The executable we are attacking is called `grade`.  When run, you
enter your name, and it will tell you your grade.  An example run of
the program:

```
$ ./grade
Please enter your name:
Aaron Bloomfield

Aaron Bloomfield, your grade on this assignment is a F
$
```

Note that the first instance of the name is what the user input, and
the second is what the program printed back.

The grade reported by this program is the grade you will receive on
(part of) this assignment.

The source code for this program is available:
[HTML version](buffer/grade.c.html), [C version](buffer/grade.c).  It
MUST be compiled with the following command: `gcc -g
-fno-stack-protector -m64 -fomit-frame-pointer -o grade grade.c`.  You
are welcome to add the `-g` option to help with debugging, but when we
test your program, we will not add the `-g` option.

Your job is to create a shellcode-based buffer overflow attack that
will assign you a different grade.  For example:

```
$ ./grade < input.txt
Please enter your name:
Aaron Bloomfield, your grade on this assignment is an A
$
```

The contents of input.txt are output by a program that you will write in tasks 3 & 4.


### Step 1: Create a Makefile

Not much to do here yet, but we are going to call `make` to compile
your code.  All of your compilation lines must be in that Makefile,
and all under one target.  If you forget how to write a Makefile, you
can see the
[Makefile tutorial from CS 2150](https://uva-cs.github.io/pdr/tutorials/05-make/index.html),
or any other reference online.

Most of the following steps will have you add some lines to the
Makefile.  A collection of all the compilation commands is at the end
of this document (in the Submission section).


### Step 2: Create the shellcode

Your first step is to create shellcode that will print out the grade
you want to receive, and then gracefully exit.  This should be in a
file called `shellcode.s`.  Recall that you will have to remove all
end-of-string characters from the machine code generated.  While this
will mostly be 0x00 bytes, you will also have to check for newlines
(0x0a) and carriage returns (0x0d).  The
[buffer overflow slide set](../slides/buffer-overflows.html#/) goes
over how to do this.

To test that your code works, you will need a `main()` function to
call your shellcode; this will go into a `shellcode-test.c` file.
Here is an example such file:

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

The `mprotect()` calls work the same as described in the grade.c
program ([HTML](buffer/grade.c.html), [C](buffer/grade.c)) -- they
allow execution on the stack (the first call) and modifying the
machine code (the second call).  Needless to say, this requires that
your assembly routine be called `shellcode`.

Your Makefile will need to have three lines to compile this program:
one to call nasm to compile shellcode.s, one to compile
shellcode-test.c, and one to link them together.

Some notes and hints for developing this shellcode:

- If you forget how to write assembly, see the appropriate
  [slides](https://uva-cs.github.io/pdr/slides/08-assembly-64bit.html#/),
  labs ([1](https://uva-cs.github.io/pdr/labs/lab08-64bit/index.html)
  & [2](https://uva-cs.github.io/pdr/labs/lab09-64bit/index.html)),
  book chapters
  ([1](https://uva-cs.github.io/pdr/book/x86-64bit-asm-chapter.pdf) &
  [2](https://uva-cs.github.io/pdr/book/x86-64bit-ccc-chapter.pdf)),
  and/or
  [recommended online document](https://www.cs.cmu.edu/~fp/courses/15213-s07/misc/asm64-handout.pdf)
  from CS 2150.
- You can use `objdump -d shellcode.o` to see the machine language
  that your assembly opcodes compiled into.
- The system calls for 32-bit x86 are DIFFERENT than those for 64-bit
  x86.  In particular, syscall 1 is exit in 32-bit and sys_write in
  64-bit.  We want this to be a 64-bit program, so be sure to use the
  syscalls that are listed
  [here](http://blog.rchapman.org/posts/Linux_System_Call_Table_for_x86_64/).
  You are welcome to use any other online table for your syscall
  reference, but be SURE it's the 64-bit version.
- When invoking `syscall` in assembly, it looks at the *entire*
  register rax for the syscall function.  So if you only set the value
  in the lower 32 bits (i.e., `mov eax, 1`), then the overall value in
  rax may not be 1 if any of the upper 32 bits were set previously
  (which is likely), and you will not get the syscall you expect.  So
  be sure to zero out the 64-bit versions of all the registers that
  syscall is using.
- Note that `syscall` can modify the registers, so if the value in rax
  was 1 before syscall was invoked, it may be quite different
  afterward -- so zero out your registers again for your second
  syscall invocation.
- As mentioned in the slides, you will want your string to be in your
  .text section.
- To save yourself a headache, don't bother putting in a newline at
  the end of the string at this time.

Some VERY important notes for compilation:

- We are using C, not C++, so you will have to use the `gcc` compiler.
- We are doing this as a 64-bit program, so you will have to pass `-f
  elf64` to nasm, and `-m64` to BOTH of the gcc compilation commands.
- You will have to add the `-no-pie` flag to the final link line.  (If
  you are interested, the default for the current version of gcc is to
  create a position independent executable (PIE), which means the
  addresses for everything are relative, not absolute.  This does not
  work with what we are compiling, so we turn it off with that flag.)
- Your compiled shellcode.s file should be named `shellcode.o` -- we
  are going to run a disassembler on that file, so please name it
  correctly.
- Your final binary should be called `shellcode-test` -- we are going
  to execute it, so please name it correctly.


### Task 3: perform the overflow

This is what we are all here for: the buffer overflow itself.
Presumably, from task 2, you have shell code that does not contain any
0x00 bytes (or newlines or carriage returns), prints out the
grade you want, and then exits.

In this part, you are going to create a C program called
`attack-shellcode.c` that will write the carefully constructed data to
stdout.  You can run your program as follows (this is how we will do
it to test):

```
$ ./attack-shellcode > input.txt
```

Needless to say, be sure to name your executable `attack-shellcode`.
You will then be able to run the buffer overflow via `./grade <
input.txt`, as shown above.

The [buffer overflow slide set](../slides/buffer-overflows.html#/)
will be a useful reference for this.  The output of your
`attack-shellcode` executable, which is what is stored in input.txt,
will likely have three parts:

- The NOP sled
- The shellcode
- The return address

You are going to have become friendly with gdb -- there is no way
around that.  Here are a bunch of commands that will be of help; you
can also see the
[CS 2150 GDB tutorial](https://uva-cs.github.io/pdr/tutorials/02-gdb/index.html)
(although some of the commands below are not listed there).

- 'n' steps *over* the next instruction, so it will pause (break) at
  the instruction following the one it is currently stopped at
- `stepi` steps into the assembly being executed -- this will be
  necessary to trace your shellcode.
- `layout asm` will split the screen so that the assembly being
  executed is shown at the top (with an arrow at the current
  instruction pointer), and a place to enter commands below.  This can
  be *very* useful for tracing your assembly, especially once it is on
  the stack.
- If you want to repeat the last command, just hit Enter -- this is
  particularly useful when you are repeatedly stepping through
  assembly commands via `stepi`.
- To print the value in a register, use `p/x $rsi`
- When paused in a part of the executable code, you can see the
  assembly listing via `disassemble` (or just `disas`).  However,
  when you are executing on the stack, gdb can't figure out what to
  disassemble, so it won't show anything via a `disas` command.
  Instead, you can give it a range, such as: `disassemble 0x7fffffffdcb0,0x7fffffffdd30`.
  As x86 instructions are variable length, if your start address is
  not on an instruction boundary, you will not get the output you
  intend.
- To print a number of values on the stack, try `x/40xw $rsp`.  Note
  that this displays the data in little Endian, whereas `hexdump -C`
  (see below) displays it in big Endian.

Some other notes and tips:

- We are not, at this time, expecting a newline to be printed at the
  end of your desired grade -- that's the next task.
- Nothing here will work unless you have run `setarch x86_64 -v -LR
  bash` first, as described in the
  [buffer overflow slide set](../slides/buffer-overflows.html#/).
- The address of the `name` buffer in `vulnerable()` should EXACTLY
  match ours.  As you are running the code on the exact same system
  (the VirtualBox image), and you used the EXACT same compilation line
  from above (you did, didn't you?), your `name` buffer should start
  at 0x7fffffffddc0 when you are in gdb.
- When you are *not* running it in gdb, the buffer address will change
  slightly.  It's up to you to figure out what that is (you can modify
  grade.c to find out; just be sure to change it back).
- You can look at your created file via `hexdump -C input.txt`.  With
  the `-C` parameter, hexdump will display it in big Endian (different
  parameters to hexdump will show it in different Endian-nesses).
- You may be tempted to use
  [puts()](http://www.cplusplus.com/reference/cstdio/puts/) to write
  your data to standard output.  However, `puts()` will write a
  newline character ('\\n', hex value 0x0a) after the data it prints.
  You can use
  [fputs()](http://www.cplusplus.com/reference/cstdio/fputs/) instead
  (use `stdout` as the file descriptor) -- this will print to standard
  output without the trailing newline.

Your Makefile will need to compile your program into an executable
named `attack-shellcode`.


### Task 4: add a newline

***BACK UP YOUR CODE FIRST!!!*** You are about to modify your program,
and if things go poorly, then you want to be able to revert back to
the code that successfully completes task 3.

If you have made it this far, then your code, when run, looks
something like:

```
$ ./grade < input.txt
Please enter your name:
Aaron Bloomfield, your grade on this assignment is an A$
```

You will notice that the prompt (the '$') is on the same line as the
desired grade because of the lack of a newline character in the
string.  You obviously can not put a newline in the string itself, as
that would indicate end-of-string, and the rest of your carefully
constructed data in input.txt would never make it onto the stack -- so
your buffer overflow attack would never occur.

For this task, you are to modify your shellcode (in shellcode.s), and
your attack-shellcode.c file, to put a newline at the end of the
string.  You will likely want to compute that value and store it in
the string, since you can't include a newline in the shellcode itself.
You should test this out via your `shellcode-test` program first.
Note that one of the `mprotect()` calls in the provided
shellcode-test.c allows for modification of the .text section, so such
a modification will work in `shellcode-test`.

This is just a modification of the attack-shellcode.c file, so your
modifications for task 4 can overwrite parts of your task 3 (if you
did task 4, then you obviously did task 3).

### Task 5: brief write-up

Please include, as a file named writeup.pdf, the following information:

- Your name and userid
- How far did you get (i.e., which tasks did you complete)?
- What thing(s) did you get stuck on for this assignment?
- Compared to some of the harder assignments you've done -- such as CS
  2150's hash lab -- how hard was this assignment?
- Other than just giving the answers, what could we do to make this
  assignment run more smoothly in the future?

We are not looking for any significant length here, just candid answers.

### Submission requirements

You are going to submit a number of files:

- Makefile: this calls four compilation lines (three from task 2, and
  one from task 3; the line for task 3 will also apply to task 4).
    - Make sure that things are compiled to the right names!
      shellcode.s should compile to shellcode.o, all of task 2 should
      compile into the `shellcode-test` executable, and the code from
      tasks 3 and 4 should compile into the `attack-shellcode`
      executable.
- shellcode.s and shellcode-test.c from task 2
- attack-shellcode.c from task 3 & 4 (if you completed task 4, just
  submit that, as that shows you also completed task 3)
- writeup.pdf from task 5
