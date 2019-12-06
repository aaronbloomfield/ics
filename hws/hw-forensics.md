ICS: Programming Homework: Forensics
====================================

[Go up to the ICS HW page](index.html) ([md](index.md))

You will want to see the
[homeworks policies page](../uva/hw-policies.html)
([md](../uva/hw-policies.md)) for formatting and other details.  The
due dates are listed on the [UVa course page](../uva/index.html)
([md](../uva/index.md)).

### Where is Sam?

Our teaching assistant Sam has disappeared!  All the teaching
assistants, as well as Professor Bloomfield, were at a department
sponsored TA retreat.  There were some issues getting home, but
everybody eventually did.  However, nobody can find Sam.  Where could
he be?

As part of the investigation, the authorities have confiscated a
computer of the "fifth" teaching assistant (the one who does all the
grading but doesn't hold office hours).  While they are working on it,
they thought you could help, and have provided you with a image of the
hard drive for you to analyze.

But you have no idea what to look for...

(Note: in the real world, you might get into legal trouble for doing
this type of search, since the owner of the computer did not give you
permission, and you don't have a search warrant. But this is an
assignment, and FAKE, so there is no legal trouble to get into for
this assignment.)

(And yes, Sam is just fine in the real world.)

### Assignment

Find out what's "up".

Spoiler: there is obviously a story that this disk image can tell,
otherwise there wouldn't be this assignment.

The intent is for you to use Autopsy / SleuthKit for this assignment,
as well as some command-line tools.  You will also want to be familiar
with the [digital forensics slide set](../slides/forensics.html#/).

### Details

You can get a copy of the hard drive image by going to
[https://libra.cs.virginia.edu/forensics/](https://libra.cs.virginia.edu/forensics/)
-- you will have to use Netbadge to log in.  **You can not use
somebody else's disk image!** You have to wait until you are told the
images are ready for this semester.  The image was compressed with
gzip -- if your operating system does not natively support it, then
you can use [7-zip](https://www.7-zip.org/) to extract it.  The
compressed .gz file is 4 Gb in size, and the uncompressed image is 10
Gb in size.

This image was designed using Linux (it's an ext4 file system).
However, you can do this homework on any host OS.  The course
VirtualBox image is configured to run Autopsy.  Within that image, you
can download your disk image, un-gzip it, and run autopsy right from
the command line.

### Tools allowed

The intent of this assignment is for you to use
[Autopsy](https://en.wikipedia.org/wiki/Autopsy_(software%29)) /
[SleuthKit](https://en.wikipedia.org/wiki/The_Sleuth_Kit).  In
addition to Autopsy and SleuthKit, you can use the standard tools that
come with an operating system -- in particular, file explorers,
searching contents of files for text, etc.  You can use any of the
utilities discussed in the [digital forensics slide
set](../slides/forensics.html#/).  You can use differently named
equivalents in differing operating systems.

**You may NOT use any OTHER existing digital forensic tools for this
assignment,** beyond the what is mentioned in the previous paragraph.
You are to use your knowledge of forensics, the [forensics slide
set](../slides/forensics.html#/), and the existing OS commands. In
particular, the "Techniques" section of that slide set has the
necessary techniques to find what can be found on this disk image.  We
recommend learning a few commands: `strings`, `find`, and `grep` may
be particularly useful for Linux.  You can Google for equivalents in
other OSes.  Note that some of the information on the disk image was
specifically designed to NOT be able to be (easily) found with Autopsy
/ SleuthKit.

If you are ***NOT*** using the course VirtualBox image, you can
install Autopsy by viewing the [details at Autopsy's
website](https://www.sleuthkit.org/autopsy/download.php).  It's
already installed on the VirtualBox image.  The current version is
4.10 -- make sure you are using that version (or a more recent one if
available).

### Running on the VirtualBox image

To run Autopsy on the VirtualBox image, just run `autopsy`.

Note that Autopsy will report a number of "Zip bombs".  We did not
place any of those on the hard drive image, so you can ignore all
those warnings.

### Deliverable

The deliverable is a PDF report, named mst3k.pdf (change 'mst3k' to
your userid).

The report will be divided into a number of parts.  So that we can
easily figure out what you found out, please make it clear which part
of your report are for which part.

Part 1 is the primary questions of answer:

- Where is Sam? (and any other related info, such as why is he gone, who knows about it, etc.)
- What parts of the story can you put together?

This part of this report should contain as much of the story as you
can determine.  Note that you will be able to get partial credit based
on what you find, even if you don't find everything.

The second part of this report should contain the information that you
found, and how you found it.  The how-you-found-it is is important, as
there are a multiple ways to find each piece of information.  Please
be brief, but concise -- we have to read through all of these, and we
don't want to read through pages and pages of content when it can be
summarized in a much shorter amount.

The third part is to comment on this assignment:

- What did you think of it?  We are looking for an honest answer here,
  not a sycophantic one.
- Was there anything that we screwed up on?  Specifically, was there a
  particular piece of evidence in which we did not properly hide
  something?  Or did we give something away?
- Give one (or more!) suggestions for additional content to hide, or
  where to hide it, or improvements to the story.

The PDF report is the only item to submit.
