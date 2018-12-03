ICS: Programming Homework: Forensics
====================================

[Go up to the ICS HW page](index.html) ([md](index.md))

You will want to see the
[homeworks policies page](../uva/hw-policies.html)
([md](../uva/hw-policies.md)) for formatting and other details.  The
due dates are listed on the [UVa course page](../uva/index.html)
([md](../uva/index.md)).

### Introduction

Mae Cartly led a very secluded life.  You knew little about her, or
what she did.  She rented an apartment across from you, and you saw
her occasionally -- but probably spoke a total of 5 words to her in
the two years that you lived across from each other.

Suddenly, though, she disappeared.  You could tell that something was
up as the lights in her apartment no longer went on at night.  The
police were called, but the could find no indication of foul play, so
they closed the criminal part of the case and listed her as a missing
person.

Her sister Laila showed up a few weeks later, and struck up a
conversation with you.  Laila worried that something was "up" with
Mae's disappearance, although she had no idea what it was.  She
searched the apartment, but to no avail.  The only thing that Laila
was not able to properly search was Mae's computer.

Laila has asked you to look into her computer to see what you can find
out.  You took an image of the hard drive, which uses Linux, and you
have that to work from.

Laila would like you to help her find out (1) where Mae went, and (2)
who she can contact locally about Mae's sudden departure.

But you have no idea what to look for...

### Assignment

Find out what's "up".

Spoiler: there is obviously a story that this disk image can tell,
otherwise there wouldn't be this assignment.

The intent is for you to use Autopsy / SleuthKit for this assignment,
as well as some command-line commands.

### Details

You can get a copy of the hard drive image by going to
[https://libra.cs.virginia.edu/forensics/](https://libra.cs.virginia.edu/forensics/)
-- you will have to use Netbadge to log in.  **You can not use
somebody else's disk image!** If it says that your image is not yet
ready, try again in an hour or so.  The image was compressed with gzip
-- if your operating system does not natively support it, then you can
use [7-zip](https://www.7-zip.org/) to extract it.  The compressed .gz
file is 3.7 Gb in size, and the uncompressed image is 9.0 Gb in size.

This image was designed using Linux (it's an ext4 file system).
However, you can do this homework on any host OS.  This can also be
done on the VirtualBox Linux image provided for the class. Due to the
size of the image file for this homework, you will either need to
increase the disk size (see the bottom of
[here](https://uva-cs.github.io/pdr/tutorials/01-intro-unix/vb-image-details.html)
for how to do that) or mount a shared folder through VirtualBox, which
will allow the guest OS (Linux in the VirtualBox image) to access the
file on your host OS.

### Tools allowed

The intent of this assignment is for you to use
[Autopsy](https://en.wikipedia.org/wiki/Autopsy_(software%29)) /
[SleuthKit](https://en.wikipedia.org/wiki/The_Sleuth_Kit).  You can
install them on your VirtualBox image via:

```
sudo apt-get install autopsy sleuthkit
```

You then have to run autopsy as root:

```
sudo autopsy
```

You can then view the UI via
[http://localhost:9999/autopsy](http://localhost:9999/autopsy).

You can install it on other OSes (Windows, Mac, etc.) by viewing the
details at [Autopsy's
website](https://en.wikipedia.org/wiki/Autopsy_(software%29)).

In addition to Autopsy and SleuthKit, you can use the standard tools
that come with an operating system -- in particular, file explorers,
searching contents of files for text, etc.

**You may NOT use any OTHER existing digital forensic tools for this
assignment,** beyond the standard OS commands installed by default
when the OS is installed (and Autopsy / SleuthKit, of course).  You
are to use your knowledge of forensics, the [forensics slide
set](../slides/forensics.html#/), and the existing OS commands. We
recommend learning a few commands: `strings` and `grep` are going to
be particularly useful for Linux.  You can Google for equivalents in
other OSes.  Note that some of the information on the disk image was
specifically designed to NOT be able to be (easily) found with Autopsy
/ SleuthKit.


### Deliverable

The deliverable is a PDF report, named mst3k.pdf (change 'mst3k' to
your userid).

The report will be divided into a number of parts.  So that we can
easily figure out what you found out, please make it clear which part
of your report are for which part.

Part 1 is the two questions of Laila's to answer:

- Where did Mae go?
- Who can Laila speak to, locally, to find out more?

The second part of this report should contain as much information as
you can determine about where Mae is, and as much of the story as you
can determine.  Note that you will be able to get partial credit based
on what you find, even if you don't find everything.

The third part of this report should contain the information that you
found, and how you found it.  The how-you-found-it is is important, as
there are a multiple ways to find each piece of information.  Please
be brief, but concise -- we have to read through all of these, and we
don't want to read through pages and pages of content when it can be
summarized in a much shorter amount.

The fourth part is to comment on this assignment:

- What did you think of it?  We are looking for an honest answer here,
  not a sycophantic one.
- Was there anything that we screwed up on?  Specifically, was there a
  particular piece of evidence in which we did not properly hide
  something?  Or did we give something away?
- Give one (or more!) suggestions for additional content to hide, or
  where to hide it, or improvements to the story.

The PDF report is the only item to submit.
