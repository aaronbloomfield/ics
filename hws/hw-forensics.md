ICS: Programming Homework: Forensics
====================================

[Go up to the ICS HW page](index.html) ([md](index.md))

You will want to see the
[homeworks policies page](../uva/hw-policies.html)
([md](../uva/hw-policies.md)) for formatting and other details.  The
due dates are listed on the [UVa course page](../uva/index.html)
([md](../uva/index.md)).

### Introduction

Emma Turner led a very secluded life. You knew little about her, or
what she did. She rented an apartment down the hall from you, and you
saw her occasionally -- but probably spoke a total of 5 words to her
in the two years that you lived across from each other.

About a month ago, though, she disappeared. You could tell that
something was up as the lights in her apartment no longer went on at
night. The police were called, but the could find no indication of
foul play, so they closed the criminal part of the case and listed her
as a missing person.

Her brother, Joss, showed up a few weeks later.  You've known Joss for
a while, and have done some (paid) off-the-books work for him in the
past.  Joss is worried that something was "up" with Emma's
disappearance, although he had no idea what it was. And he has no idea
where she is.  He searched the apartment, but to no avail. The only
thing that Joss was not able to properly search was Emma's computer.
He stated that he has no idea what could have happened to her.

Joss has asked you to look into Emma's computer to see what you can
find out. You took an image of the hard drive, which uses Linux, and
you have that to work from.

Joss would like you to help him find out (1) where Emma went, and (2)
who he can contact about Emma's sudden disappearance.

But you have no idea what to look for...

(Note: in the real world, you might get into legal trouble for doing
this type of search, since the owner of the computer did not give you
permission. But this is an assignment, and FAKE, so there is no legal
trouble to get into for this assignment.)

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
somebody else's disk image!** If it says that your image is not yet
ready, try again in an hour or so.  The image was compressed with gzip
-- if your operating system does not natively support it, then you can
use [7-zip](https://www.7-zip.org/) to extract it.  The compressed .gz
file is 4 Gb in size, and the uncompressed image is 10 Gb in size.

This image was designed using Linux (it's an ext4 file system).
However, you can do this homework on any host OS.  Due to installation
issues, you can not install autopsy on the VirtualBox image.

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
recommend learning a few commands: `strings` and `grep` are going to
be particularly useful for Linux.  You can Google for equivalents in
other OSes.  Note that some of the information on the disk image was
specifically designed to NOT be able to be (easily) found with Autopsy
/ SleuthKit.

You can install Autopsy by viewing the [details at Autopsy's
website](https://www.sleuthkit.org/autopsy/download.php).  The current
version is 4.10 -- make sure you are using that version (or a more
recent one if available).

### Installation on the VirtualBox image

Autopsy uses Java, but requires a very specific version of Java.
Specifically, it needs Oracle's version of Java 8 (not more recent!),
along with the JavaFX add-on.

- First, make *sure* you start with the more recent of the two
  VirtualBox images that was released this semester, as that one was
  set up to allow this install to work smoothly.
	- To see which one you have, you can run `dpkg -l | grep sleuth`.
	- If you see nothing listed, or you see `sleuthkit` listed, then
      it's the first version of the image.
	- If you see `sleuthkit-java` listed, then it's the second
      version.
- Make sure you have enough space for all of this
    - How much space will you need?  For the download and installation
      of Autopsy and Java, you'll need 2 Gb.  For the uncompressed
      disk image, 10 Gb.  For the compressed disk image, 4 Gb.
	    - If you download the disk image first, and then decompress
          it, you can do all of this in 15 Gb of free space
		- If you install autopsy and Java first, you'll need 17 Gb of
          free space
    - Run `df -h`, and find the line that has `/` in the "Mounted on"
      column (likely /dev/sda1)
	- In the "Used" column, it should have enough space
	- If you don't have enough space, you can either (1) get a fresh
      VB image from
      [here](https://virginia.box.com/s/lrlrokq3hul8lr5hgdymio3ppw9to60b)
      (that's the link for the spring 2019 version 2 image), or (2)
      increase your disk size as per
      [here](https://uva-cs.github.io/pdr/tutorials/01-intro-unix/vb-image-details.html)
      (see the "increasing the disk size" section)
- Download the Oracle version of Java 8 from
  [https://www.java.com/en/download/linux_manual.jsp](https://www.java.com/en/download/linux_manual.jsp)
  -- you want the "Linux x64" version, and the downloaded file is
  about 84 Gb in size
    - Once downloaded, extract that file: 
      `tar xfz jre-8u211-linux-x64.tar.gz` -- that will create a 
      jre1.8.0_211 directory
    - cd into that directory, and then run `pwd` -- this will print
      out the full path of the directory you are in, and you will need
      that directory path -- we'll assume it's
      `/home/student/jre1.8.0_211` in these instructions
- Download Autopsy: from the [autopsy download
  page](https://www.sleuthkit.org/autopsy/download.php), you want the
  "Autopsy ZIP file" link.  The current version is 4.10, and that .zip
  file is 579 Mb.
    - Unzip that file
- Configure autopsy:
    - cd into the autopsy directory, and run: 
	  `JAVA_HOME=/home/student/jre1.8.0_211 bash unix_setup.sh`
    - Change the directory to the location of the Oracle JRE that you
      installed above
	- It should report "Autopsy is now configured"
- To run autopsy, you have to tell it the path to Oracle's version of
  Java -- from the autopsy-4.10.0 directory (that was created when you
  unzipp'ed autopsy), run 
  `bin/autopsy --jdkhome /home/student/jre1.8.0_211`
    - If you don't do the jdkhome part, it will use the default
      installed version of Java, which WILL NOT WORK

### Deliverable

The deliverable is a PDF report, named mst3k.pdf (change 'mst3k' to
your userid).

The report will be divided into a number of parts.  So that we can
easily figure out what you found out, please make it clear which part
of your report are for which part.

Part 1 is the two questions of Joss' to answer:

- Where is our missing person?
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
