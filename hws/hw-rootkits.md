ICS: Programming Homework: Rootkits
===================================

[Go up to the ICS HW page](index.html) ([md](index.md))


### Overview

In this assignment, you will investigate both types of rootkits: application and kernel.  There are four tasks to be completed for submission.  You will want to review the [rootkit slides](../slides/rootkits.html#/).

In this assignment, you will create both types of rootkits: a kernel-level one and an application-level one.


***YOU MUST RUN THIS IN THE CYBER DEFENSE RANGE!!!***, described below.  You are going to *negatively affect the machine you develop this on*.  Do not think that you can or should do this on your computer and still have your computer work.

We are not responsible if you render your computer inoperable because you did not follow this warning!  This is why we provided the Cyber Defense Range account!


### Changelog

Any changes to this page will be put here for easy reference.  Typo fixes and minor clarifications are not listed here.   So far there aren't any significant changes to report.


<!--
### Backups and Restoration

You may well render your VirtualBox image inoperable.  That's fine, as we can easily fix this.  First, before you start this assignment, back up any code that you have on the VirtualBox machine that you want to save.  As you are writing your program, you will want to back it up in some fashion before you attempt to run it EACH TIME you are running something that could negatively affect the system.  A few options exist for that:

- Email it to yourself
- Set up [VirtualBox shared
  folders](https://help.ubuntu.com/community/VirtualBox/SharedFolders)
  and save it there -- we recommend this option, since you won't have
  to reconfigure it each time you wipe your VirtualBox image (you can
  just run the code from that folder)
- Use git
- Install Dropbox

Once you have backed up your code for this assignment, if you brick your image, you can do the following:

- Shutdown the VirtualBox machine
- Download the .zip of the image, if you don't have it already
- Unzip it right over the existing (and bricked) image
- Restart VirtualBox
- Copy your files back over, and you are ready to go

-->

### Platform

We will be using the Virginia Cyber Range at [https://www.virginiacyberrange.org/](https://www.virginiacyberrange.org/). This site basically allows you to run a remote virtual environment; we are using Ubunmtu 22.04.

Go to [https://www.virginiacyberrange.org/](https://www.virginiacyberrange.org/).  Click on "sign up", then "join as a student".  Use the invitation code on the canvas landing page, then sign in (presumably with your Google UVA account).  You will need to accept the agreement to continue.

If you already have an account, there is (presumably) a way to add this course via the invitation code...

Once you log in, click on the course.  There will then be a list of exercise environments at the bottom of the page -- there should be only one, called "Rootkits HW Environment".  Click on it, then click on the power button on the page that appears (it's in the lower left of the page).  It will take a minute or so to start (boot).  Once it does, hit the play button (the right-arrow that replaced the power button) to attach to a screen in the virtual environment.

You can load up a terminal via one of the icons on the bottom of the screen.  You will then have to install a bit of the software.  To do so, enter the following two commands:

```
sudo apt update
sudo apt install -y make g++ emacs
```

You are welcome to install any other software that you would like to use.  gedit is already installed, as is python3.

When you are done, you should close that window, and you can stop the virtual machine (the stop button, which is next to the play button that started this whole party).

**WARNING:** This site is free to use for *class purposes* for all students in the course.  Using it for non-class purposes is an honor violation, and will be dealt with as such.  Anything outside the reasonable bounds of an assignment in this course is considered a non-class purpose.

This is a great resource, but it is a *finite* resource.  If you decide to wait to the last minute to start the assignment, and the rest of your class-mates do so as well, it's going to be slooooooow.  You cannot get an extension because you waited until the last minute along with everybody else, and the system was slow as a result.

#### Working as root

Some of these commands will require to to execute them as the root user.  You can do this by prepending `sudo` in front of the command. For example, to insert a module called `root.ko`, you would call `sudo insmod root.ko` -- this executes the `insmod root.ko` command as root.



### Task 1: Kernel-level rootkit

**Kernel level rootkit:** Recall that a Linux kernel level rootkit installs a loadable kernel module (LKM) that gives the attacker more privileges on the compromised machine.  We are going to build such a module here. There are two tasks to be done for the kernel level rootkit aspect of this assignment.

Follow [this tutorial at 0x00SEC](https://0x00sec.org/t/kernel-rootkits-getting-your-hands-dirty/1485). This will require that you read two sub-tutorials ([1](http://derekmolloy.ie/writing-a-linux-kernel-module-part-1-introduction/) and [2](http://derekmolloy.ie/writing-a-linux-kernel-module-part-2-a-character-device/)). This rootkit will allow you to gain root access on the machine from a non-root user.  Note: the sub-tutorials should be used to understand what is happening on [the main tutorial at 0x00SEC page](https://0x00sec.org/t/kernel-rootkits-getting-your-hands-dirty/1485); they do not all compile at this point.

A few notes from those tutorials:

- You don't have to install the package that the [first sub-tutorial](http://derekmolloy.ie/writing-a-linux-kernel-module-part-1-introduction/) states (the one obtained via `wget` and installed via `dpkg -i`), nor clone the git repo
- When creating the Makefile, recall that the indentation must be a tab, not a space (if you copy-and-paste it, it pastes as a space)
- For the [second sub-tutorial](http://derekmolloy.ie/writing-a-linux-kernel-module-part-2-a-character-device/), you have to save both ebbchar.c and testebbchar.c before the Makefile will work

The first task for this assignment is to create this rootkit as specified in the tutorial.

Note that to complete this part, you can just copy the code that is listed there -- that's the code that you have to submit, after all. However, you have to explain to us how the code works.  We are looking for a half-page, and it needs to be specific enough that we see that you really understand what is going on in that code.  This should go into a `kernel-rootkits.pdf` file (more content will go into that file for the next task).

For submission of your code, you should put your code in a `root.c` file (the same name as in the tutorial), and put the appropriate compilation lines in the Makefile.  The module name MUST be `root.ko`, otherwise we won't be able to load it ourselves.


### Task 2: What else?

What else can rootkits do?

This was a fairly simple rootkit, as far as complexity goes, although it's a lot to learn all at once.  All this does is allow one to gain root access -- useful, to be sure, but there is much more that rootkits can do.

The second task for this assignment is to investigate other things that rootkits can perform.  We are looking for specific things that can be done, not high-level mumbo-jumbo.  For each thing, a sentence describing it would be sufficient.  For example, the access that the rootkit that you just implemented might be phrased such as:

- *root access*: by writing a known string to a character device, one can allow a non-root user to gain root access

How many to find?  That's up to you.  If you have a bunch, and you are not finding anymore, then you are done.  You can use Google searches, look at rootkit code online -- take a look [here](https://github.com/f0rb1dd3n/Reptile) to start.  That source code lists 12 different functionalities (one of which being giving root access to non-root users).  Thus, we would expect a list of 20 or more.

This part should be put into a PDF called `kernel-rootkits.pdf`; note that there will also be content in that file from the first task.

### Task 3: Wrapper files

**Application level rootkit:** Recall that an application level rootkit will replace specific utilities with compromised versions.  There are two tasks to be done for the application level rootkit aspect of this assignment.

You are going to create a "wrapper" program that will perform the "malicious" task, and then call the original program.  For this one, we will attack the `sha224sum` program, which is located at `/usr/bin/sha224sum`.  We chose this file because it's not all that critical, and if it gets messed up, you can still use the operating system.

The `sha224sum` program has a number of command-line parameters that it accepts.  See [here](https://www.systutorials.com/docs/linux/man/1-sha224sum/) for a list of these.  Note that different versions of sha224sum, as well as different OSes, will have different options.  In particular, you cannot assume any particular set of options.

First, *BACK UP THE PROGRAM*.  For example: `sudo cp /usr/bin/sha224sum /usr/bin/sha224sum.bak`.  This way if you mess up, you can easily restore it.

Next, copy the sha224sum program to another name -- we will use `/usr/bin/sha224sum.original` for this task.  When we test your program, we are going to rename our `/usr/bin/sha224sum` to `/usr/bin/sha224sum.original`, so if you use a different name, your submission will not work.

You are going to write a wrapper program that you will name `sha224sum`, and put it in `/usr/bin/` -- thus, when you normally call `sha224sum`, it will call your wrapper file.  Your wrapper file will then call the renamed (and original) `sha224sum.original`.

- Whenever run, it will print out a one-line witty quote
- If the `--be-evil` flag is passed (note the two leading dashes!), it will print out some (different) witty multi-line quote
- Any other command-line parameters other than `--be-evil` will be passed to a call to the `sha224sum.original` file

Thus, once installed, if you were to call `sha224sum <file>`, it would print out the witty one-line quote and then the SHA-224 sum.

You can do this in any language that you want:

- A bash shell script will be the shortest, but familiarize yourself with how to deal command-line parameters in bash -- you can see the cryptmoney.sh shell script in the [cryptocurrency](hw-cryptocurrency.html) ([md](hw-cryptocurrency.md)) homework for examples
- C/C++ program: if you need a refresher as to how to parse command-line parameters, see [this slide from CS 2150](https://uva-cs.github.io/pdr/slides/04-arrays-bigoh.html#/cmdlineparams) and the source code linked to on that page; to execute another program, you will want to use the [execl()](https://www.systutorials.com/docs/linux/man/3-exec/) C function, which is in the `<unistd.h>` library.
- We can't use Java -- we need the executable name to be `sha224sum`, not `sha224sum.class`
- Python 3 is fine -- be sure to put `#!/usr/bin/python3` and run`chmod 755 <scriptfilename>` on the file

For this assignment, you should name your source code file `sha224sum-fake.?` where the "?" is the appropriate filename extension (use 'sh' for a shell script).

Your Makefile MUST create a `sha224sum` *executable* file.  If your code requires compilation, then compile it to the `sha224sum` file name.  If you are using a scripting language (Python or bash), then copy the file over *WITHOUT* a .py or .sh extension.  If you are using C, C++, or Go, then compile it to that file name.  We are going to run `make`, then assume that the `sha224sum` file is present.

### Task 4: Modify source code

For this task, we will attack the `sha384sum` binary, for similar reasons as the previous task -- it's not likely to be critical to your OS booting.

First, *BACK UP THE PROGRAM*.  For example: `sudo cp /usr/bin/sha384sum /usr/bin/sha384sum.bak`.  This way if you mess up, you can easily restore it.

For this task we are going to modify the source code to insert our compromise into the program itself.  Linux is open source, and all of the core utilities are GNU, so their source code is freely available.  You can download the latest (highest-numbered) version from [https://ftp.gnu.org/gnu/coreutils/](https://ftp.gnu.org/gnu/coreutils/) -- at the time of this writing, it's 8.31 (NOTE: later versions do not work as well, so stay with version 8.31).  The coreutils are the basic utilities used by all Linux systems -- ls, cp, mv, etc.  Once you've downloaded it, uncompress it (`tar xfa coreutils-8.31.tar.xz`). Once in that directory, run `./configure` then `make`.  This will work on the VirtualBox image -- you are on your own if you are using your own computer's OS.

The source code for the various files in coreutils is all inter-dependent on each other, and it's not worth our time for this assignment to figure out the details or try to separate it.  So you are going to edit the appropriate files in the coreutils archive and compile it with the Makefile that the archive provides.  The `main()` function for the sha384sum binary is in src/md5sum.c (SHA-1 was originally added as an "extension" of MD5, and it stayed that way as successive SHA versions were created).  Once you modify that file, you can run `make src/sha384sum`.

To ensure that you can get it to work, put a `printf()` statement in the top of the `main()` method in `md5sum.c`, then run `make src/sha384sum`.  When you run the command (`src/sha384sum <file>`), it should print your output from the `printf()` line, then the 384-bit SHA hash.  Then remove that `printf()` statement.

The challenge for this task is that you have to find out some information about the host system and then send that over a network connection.  This all has to happen when `sha384sum` is run.

One can find out a lot of information about the system.  For this part, though, we aren't as focused on *what* you can find out, but instead *how* you send that information back.  The information we want is the output of `lsb_release -a`.  On the VirtualBox image, it would print out:

```
No LSB modules are available.
Distributor ID: Ubuntu
Description:    Ubuntu 18.04.2 LTS
Release:        18.04
Codename:       bionic
```

Note that the first line will vary, as that is reporting on the hardware on your machine (mine said "LSB Version: core-9.20170808ubuntu1-noarch:security-9.20170808ubuntu1-noarch"). Also note that this is multiple lines -- how you send it back is up to you.  It can be multiple calls (one per line) -- or, more easily, just one call.  It's fine if you just remove spaces and newlines so that the text sent back is:

```
NoLSBmodulesareavailable.DistributorID:UbuntuDescription:Ubuntu18.04.2LTSRelease:18.04Codename:bionic
```

You can even replace all spaces with underscores, if you wish.  We are less focused on text formatting and more on the obtaining and transmission of the information.  And if your system does not print the first line ("No LSB modules are available."), that's fine as well.

From the C code, you will need to execute a command-line program and capture the output.  There are a few ways to do this:

- You can do this as series of command-line arguments that create a file, and then read in that file.  For example, `system("command > filename.txt");` will create the file, and then you can look into file I/O to read in that file (see the [fileio.cpp](https://uva-cs.github.io/pdr/labs/lab10/fileio.cpp.html) program from CS 2150's Huffman coding lab as an example)
- You can call the command directly (without redirecting it to a file) from C -- see the code [here](https://stackoverflow.com/questions/7292642/grabbing-output-from-exec) for how to do this (you can copy the code at that link for this assignment).

You will then need to transmit this over the network.  To simplify it, you just call a URL, which is shwown on the Canvas landing page.  In that URL, mst3k is your userid and the `...` is the data you are sending. You can do this via a `wget` call (executing a command-line program from the C/C++ program via [system()](https://www.systutorials.com/docs/linux/man/3-system): `wget <URL>`.  This puts the information into the apache web server log.  There are more efficient ways to do this, but putting it into the apache log is fine for this assignment.  Note that you should supress the output from the `wget` call -- you can do this with the `-q` flag.

You may also want to familiarize yourself with the [system()](https://www.systutorials.com/docs/linux/man/3-system) function.

To see what information has been sent, go directly to the rootkit data viewer URL, which is on the Canvas landing page.  Once you log in with Netbadge, you will be able to see all the data sent in under that userid.

Note that the logs that are displayed only keep the information sent in the last few hours -- we will look at the entire log history when we grade the assignment, but the information that you see will be only the most recent.  So don't worry if the entries that you saw on a previous day are no longer present.  As long as they were there at *some* point, we'll see them when we grade the assignment.

The only file you should submit from this is the modified `md5sum.c` file.  We will unpack and configure the entire coreutils archive, put the md5sum.c file in there, and then recompile the `sha384sum` program.  Thus, you do NOT need anything in your Makefile for this task.

### Hints

We've collected a few of these here...


- The `which` command in linux tells you where the specified system binary is in the filesystem; for example, if you enter 'which ls', the path to the ls binary (/bin/ls) should be printed out
- Similarly, the `PATH` environment variable indicates all the possible locations that the system looks for when you want to execute a command. You can view it with `echo $PATH`
- If you finished the backdoored `sha224sum` rootkit, but it's not printing out anything, consider flushing out stdout to force print the contents in the stdout buffer.
- `exec()` refers to a family of functions that can execute other processes; we recommend using `execl()` for this assignment (though you are free to do otherwise!)

### Submission

The following files will need to be submitted:

- `root.c` from task 1
- `kernel-rootkits.pdf` from tasks 1 and 2
- `sha224sum-fake.?` from task 3 (replace '?' with the appropriate
  file extension)
- `md5sum.c` from task 4
- A `Makefile` -- see below

The primary Makefile target must:

- Compile the rootkit from task 1 to a `root.ko` module -- the compilation line from the tutorial is sufficient
- For task 3, either compile a C / C++ / Go program to `sha224sum` or copy the Python / bash script to the same file name (with no extension!)

Below is the Makefile that we used (recall that the indentation in the Makefile must be tabs, not spaces), however your mileage may vary.

```
obj-m+=root.o

all:
	make -C /lib/modules/$(shell uname -r)/build/ M=$(PWD) modules
	/bin/cp -a sha224sum-fake.py sha224sum

clean:
	make -C /lib/modules/$(shell uname -r)/build/ M=$(PWD) clean
```

Recall that the sha224sum compilation (or copy) line will vary based on the language used.
