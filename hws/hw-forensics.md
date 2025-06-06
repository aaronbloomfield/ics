ICS: Programming Homework: Forensics
====================================

[Go up to the ICS HW page](index.html) ([md](index.md))

### Scenario

Professor Bloomfield has been acting strange lately.  Ever since sometime the start of the summer term he has been unfocused in his lectures, and seems to not know the material -- even though he's taught this course many times before.  He doesn't show up to office hours or his required departmental meetings.  And he often just misses class.  When asked about it, he seems to just brush it off, giving vague answers.  There have been many conspiracy theories about what happened, and alien abduction is the latest craze.

He recently left his laptop behind after class.  Some enterprising students were able to obtain a disk image of his hard drive before he came and retrieved it.

Where is Bloomfield?  Is he safe?  Is anybody else in danger?  What's going on?

Take a look at the image and figure out what the situation is.

Note: something you will find along the way is quite obviously satirical -- you will know it when you see it.  This was done partly for fun, and also so that nobody actually thought that anything illegal was going on.


### Overview

Find out what happened from the disk image.

Spoiler: there is obviously a story that this disk image can tell, otherwise there wouldn't be this assignment.

The intent is for you to use Autopsy / SleuthKit for this assignment, as well as some command-line tools and the file explorer.  You will want to be familiar with the [digital forensics slide set](../slides/forensics.html#/).  All the techniques needed for this assignment are in that slide set somewhere.




### Changelog

Any changes to this page will be put here for easy reference.  Typo fixes and minor clarifications are not listed here.  So far there aren't any significant changes to report.



### Platform

As the deliverable for this is a PDF report, you can use any system you want.  The system running autopsy, described below, can be accessed via any web browser.

You can get a copy of the hard drive image by going to the URL listed on the Canvas landing page.  There is a disk image for each student in the course.  **You can not use somebody else's disk image!**  You will download your image with the wget command posted on the Canvas landing page -- change `mst3k` in that command to your userid.

**WARNING:** It will Autopsy a while to analyze your disk image.  You need to start that part ahead of time!

<!--
#### Using your own machine

You are also welcome to do this homework on your computer, but you are on your own to install [Autopsy](https://www.sleuthkit.org/autopsy/).  You can see installation details at [Autopsy's website](https://www.sleuthkit.org/autopsy/download.php).  The version on the Virginia Cyber Range is version 4.16.0 with SleuthKit version 4.10.0.  You should have somewhat similar versions -- later is fine, as is slightly earlier (but no earlier than 4.0.0).

You are allowed to use any system tools that are equivalents on your operating system of the ones discussed below.

Lastly, you can run autopsy on your own machine, and use the Cyber Range to deal with a few things such as mounting the disk image and file undeletion.
-->

#### Using the Cyber Range

There is an invitation code on the Canvas landing page to join the cyber range course, if you haven't done so already.  Once in the course, use the environment for the Forensics HW.  You will download the image onto that machine, using the `wget` command.  You then start autopsy via: `autopsy`.

From a web browser, go to the URL on the Canvas landing page to download your disk image.  Once it's downloaded, you have to uncompress it: `gzip -d mst3k.img.gz`.

#### Starting Autopsy

When you are adding the disk image to the autopsy case, you will be presented with a series of check boxes as to which modules you want to enable.  It will look something like this:

![](forensics/autopsy-modules.webp)

***IMPORTANT:*** You ***MUST*** de-select the 'keyword search' box.  All of the other modules take 10 minutes to run, combined.  The keyword search takes 5 hours.  And the cyber range resets everything after 4 hours.

The Cyber Range has a few limitations that you need to be aware of:

- **It can't play videos.**  If you find any (not all scenarios have them), you can instead view them online at the URL provided on the Canvas landing page
- The Cyber Range will disconnect after a certain amount of inactivity.  However, Autopsy needs to run for quite some time.  Thus, you should do this while you are already using the computer for something else so that you can keep it from disconnecting (move a window around or something).
- If you want to use a tool that is not installed, the terminal will tell you what command to run to install the tool.  Just run that command and then use the tool.

#### Using your own machine

We do not recommend using your own machine.  Autopsy is notoriously hard to install, and has caused a lot of frustration to students trying to do so.  And it does not work on M1 or M2 Macs.

#### Autopsy limitations

Autopsy is great, but there are a few limitations that you need to be aware of.  These are limitations on either the Cyber Range or your machine.

- It will find a *lot* of things to look at.  Not all of them are relevant.
- It is not very good at detecting or undeleting files because of the type of filesystem (ext4).  Use other tools, as discussed in the slide set, for this
	- You can run autopsy on your own machine, and use the Cyber Range to deal with a few things such as mounting the disk image and file undeletion.  photorec is good for that.


#### Tools allowed

The intent of this assignment is for you to use [Autopsy](https://www.sleuthkit.org/autopsy/) / [SleuthKit](https://www.sleuthkit.org/sleuthkit/).  In addition to Autopsy and SleuthKit, you can use the standard tools that come with an operating system -- in particular, file explorers, searching contents of files for text, etc.  You can use any of the utilities discussed in the [digital forensics slide set](../slides/forensics.html#/).  You can use differently named equivalents in your computer's operating system.  You may also use your operating system's default file explorer.

**You may NOT use any OTHER existing digital forensic tools for this assignment,** beyond the what is mentioned in the previous two paragraphs.  You are to use your knowledge of forensics, the [forensics slide set](../slides/forensics.html#/), and the existing OS commands. In particular, the "Techniques" section of that slide set has the necessary techniques to find what can be found on this disk image.  We recommend learning a few commands: `strings`, `find`, and `grep` may be particularly useful for Linux.  You can Google for equivalents in other OSes.  Note that some of the information on the disk image was specifically designed to NOT be able to be (easily) found with Autopsy / SleuthKit.



### Notes

A few notes to help you through the process:

- We can't really help you on this assignment.  The whole point is for *you* to find things, not for us to tell you where things are.  This means we are not going to tell you how many things there are to find, if there is more to find, or where else to look.  You are on your own.
	- If you are having technical issues, then we will certainly help you
	- Otherwise, we are going to give you the same advice: all of the techniques you need are in the [digital forensics slide set](../slides/forensics.html#/)
	- You should be able to discern a full story from the disk image -- when you have a full story, then you are probably done
- As mentioned above, the Cyber Range can't play videos.  If you find any, you can instead view them online at the URL provided on the Canvas landing page.
- If you are doing this on your own computer, you can also use the Cyber Range for a few other aspects, such as mounting the disk image and file undeletion.
- If you want to view the image directly in the Cyber Range (or Linux or Mac), you can try a command such as: `sudo mount -t ext4 -o loop,ro mst3k.img /mnt`.  
	- Play around with that command a bit -- your OS may not support the ext4 file system, and you can try to remove the `-t ext4` part.  You have to create the `/mnt` directory first, of course.
	- The `,ro` part adds the read-only attribute.  If you are having problems with this, you can remove it (so the option is just `-o loop`), and then you can write to the file system.  You can download it again if needed (but delete the other one first -- there isn't a lot of disk space on the Cyber Range system, and you don't want to have excess disk images hanging around on your own machine)
- Autopsy sometimes crashes.  You should be able to restart it, and it will pick up where it left off.
- If there is an AES encrypted message (some scenarios have it, some do not), you have to add the `-pbkdf2` flag to the [decryption line](../slides/forensics.html#/password-protected-files).

#### Mounting the disk image

You can also mount the disk image directly from the CyberRange system.  To do so:

```
mkdir -p /mnt
mount -o ro /path/to/mst3k.img /mnt
```

You can then look in the `/mnt/` directory to find the files on the disk image.  This way of mounting the image -- with the `-o ro` flags -- makes it a read-only file system, so you can't modify anything therein.

### Deliverable

The deliverable is a PDF report, named forensics.pdf.

Make sure you put your name and userid at the top of the PDF!  We need to know who you are when we grade it.

The report will be divided into a number of parts.  So that we can easily figure out what you found out, please make it clear which part of your report are for which part.

Part 1 is the primary questions of answer:

- What happened to Bloomfield?  Is he safe?
- Is anybody else in danger?
- What's going on?
- What else can you tell me about the situation?

This part of this report should contain as much of the story as you can determine.  Note that you will be able to get partial credit based on what you find, even if you don't find everything.

The second part of this report should contain the information that you found, and how you found it.  The how-you-found-it is is important, as there are a multiple ways to find each piece of information.  Please be brief, but concise -- we have to read through all of these, and we don't want to read through pages and pages of content when it can be summarized in a much shorter amount.

The third part is to comment on this assignment:

- What did you think of it?  We are looking for an honest answer here, not a sycophantic one.
- Was there anything that we screwed up on?  Specifically, was there a particular piece of evidence in which we did not properly hide something?  Or did we give something away?
- Give one (or more!) suggestions for additional content to hide, or where to hide it, or improvements to the story.

### Submission

The PDF report, named `forensics.pdf`, is the only item to submit.
