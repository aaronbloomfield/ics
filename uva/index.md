ICS: UVa specific material, spring 2023
=======================================

[Go up to the main ICS readme](../readme.html) ([md](../readme.md))

Much of the rest of this git repo is meant to be generic to anybody who has a class such as this one. But this page contains details specific to the specific version of the course at the University of Virginia.

------------------------------------------------------------

Links
-----

There are a number of links and other parts of this course that are **NOT** included in this repository.  They are:

- Any concerns you have should be handled via a support request; the link is on the [Canvas landing page][1]
- Assignment submission is through the Gradescope tool in Canvas
- The [Piazza forum for this course](https://piazza.com/class/lcyvdla676110z); Canvas can log you in directly -- the Canvas tool link is [here](https://canvas.its.virginia.edu/courses/59093/external_tools/21)
- The link for the VirtualBox image will be available on the [Canvas landing page][1].  For how to install it, see  [here](https://uva-cs.github.io/pdr/tutorials/01-intro-unix/virtual-box.html).
- Apparently anonymous feedback is not currently available in Canvas

<!-- no longer available in canvas:

- ~~[Email list archive](https://collab.its.virginia.edu/portal/directtool/23262987-1288-4c6d-912f-c1b031973f44/), which is a Collab tool~~
- ~~[Anonymous feedback](https://collab.its.virginia.edu/portal/directtool/b166e2b1-f967-4df0-8e7e-1b25f58a30e2/), which is a Collab tool~~

-->

The parts of this course that are in this repo are:

- [Course syllabus](syllabus.html) ([md](syllabus.md))
- [Daily announcements](daily-announcements.html#/)
- [Homework policies page](hw-policies.html) ([md](hw-policies.md))
- [Final course grade determination](grades.html) ([md](grades.md))


Readings
--------

All scholarly articles (such as from the ACM digital library) can be obtained from free from any UVa wireless network.  Some of them you will *NOT* be able to get it for free from your home Internet provider such as Comcast (unless you live in a UVa dorm, of course) without using a UVa VPN.  All readings are due by the start of lecture that day.

<!--
- Due Friday, September 13th:
    - [An Introduction to Cybersecurity Ethics](https://www.scu.edu/media/ethics-center/technology-ethics/IntroToCybersecurityEthics.pdf): you can skip the questions (the blue boxes therein); once you remove those, the table of contents, and the appendices, it's about 35 typed pages
-->

- Due Friday, January 27th:
	- [ACM Code of Ethics](https://www.acm.org/code-of-ethics)
    - [Reflections on Trusting Trust](https://dl.acm.org/citation.cfm?id=358210)
	- [Morris Worm Wikipedia page](https://en.wikipedia.org/wiki/Morris_worm)


Homeworks
-----------

Unless otherwise noted, all submissions are due by the end of the day of the due date given -- this means by 11:59:59 pm.  The late policies are discussed in the [homework policies page](hw-policies.html) ([md](hw-policies.md)).  Submission is through the Gradescope -- all submissions should open up 3 days (i.e., 72 hours) prior to the due date/time.

- [HW 1: Rational Paranoia](../hws/hw-paranoia.html) ([md](../hws/hw-paranoia.md)) is due Friday, January 27th
- [HW 2: Policy](../hws/hw-policy.html) ([md](../hws/hw-policy.md)) is due Thursday, February 2nd

<!-- 

- [HW 13: Forensics](../hws/hw-forensics.html) ([md](../hws/hw-forensics.md)) is due Friday, December 6th
- [HW 12: Movie Night](../hws/hw-movie-night.html) ([md](../hws/hw-movie-night.md)) is due Wednesday, December 4th
- [HW 11: Buffer Overflow](../hws/hw-buffer.html) ([md](../hws/hw-buffer.md)) is due Friday, November 22nd
- [HW 10: Celebrity Visit](../hws/hw-celebrity-visit.html) ([md](../hws/hw-celebrity-visit.md)) is due Thursday, November 21st, and there is all of 12 hours of lateness allowed on this!
- [HW 9: Rootkits](../hws/hw-rootkits.html) ([md](../hws/hw-rootkits.md)) is due Friday, November 15th
- [HW 8: Cryptocurrency](../hws/hw-cryptocurrency.html) ([md](../hws/hw-cryptocurrency.md)) is due Friday, November 1st
- [HW 7: Networks](../hws/hw-networks.html) ([md](../hws/hw-networks.md)) is due Friday, October 25th
- [HW 6: SQL, XSS, & CSRF](../hws/hw-sql-xss-csrf.html) ([md](../hws/hw-sql-xss-csrf.md)) is due Friday, October 18th
- [HW 5: Hashing](../hws/hw-hashing.html) ([md](../hws/hw-hashing.md)) is due Friday, October 4th
- [HW 4: RSA](../hws/hw-rsa.html) ([md](../hws/hw-rsa.md)) is due Friday, September 27th
- [HW 3: Ethics](../hws/hw-ethics.html) ([md](../hws/hw-ethics.md)) is due Friday, September 20th

-->

Course calendar
---------------

| Week # | Week of Monday | Lecture days | HWs due | Topics | Progress |
|----|----|----|----|----|----|
| 1  | Jan 16 | W,F    |                   | [Course introduction](../slides/introduction.html#/), [The Security Mindset](../slides/security-mindset.html#/) | Wed: finished course intro; Fri: security mindset to 6.13 |
| 2  | Jan 23 | M,W,F  | Rational Paranoia | [The Security Mindset](../slides/security-mindset.html#/), [Terminology](../slides/terminology.html#/), [US Cybersecurity Policy](../slides/policy.html#/) | Mon: finished security mindset, terminology to 4.13; Wed: finished terminology, policy to 3.18; Fri: policy to 6.11 |
| 3  | Jan 30 | M,W,F  | Policy            | [US Cybersecurity Policy](../slides/policy.html#/), [Ethics](../slides/ethics.html#/) | Mon: finished policy; Wed: ethics to 6.6; Fri: finished ethics, encryption to 3.9 |
| 4  | Feb 6  | M,W,F  | Ethics            | [Encryption](../slides/encryption.html#/) | |
| 5  | Feb 13 | M,W,F  | RSA               | [Encryption](../slides/encryption.html#/) | |
| 6  | Feb 20 | M,W,F  | Hashing           | [Networking Overview](../slides/networks.html#/), [Web Security](../slides/web-security.html#/) | |
| 7  | Feb 27 | M,W,F  | (midterm week)    | [Web Security](../slides/web-security.html#/), midterm | |
|    | Mar 6  | (none) | (none)            | (spring break)  | |
| 8  | Mar 13 | M,W,F  | Networks          | [Viruses](../slides/viruses.html#/)| |
| 9  | Mar 20 | M,W,F  | (new virus HW)    | [Buffer Overflows](../slides/buffer-overflows.html#/) | |
| 10 | Mar 27 | M,W,F  | Buffer overflow   | [Binary Exploits](../slides/binary-exploits.html#/), [SQL, XSS, and CSRF](../slides/sql-xss-csrf.html#/) |
| 11 | Apr 3  | M,W,F  | SQL/XSS/CSRF      | [Cryptocurrency](../slides/cryptocurrency.html#/), [Anonymity](../slides/anonymity.html#/) | |
| 12 | Apr 10 | M,W,F  | Cryptocurrency    | [Rootkits](../slides/rootkits.html#/), [Stuxnet](../slides/stuxnet.html#/) | |
| 13 | Apr 17 | M,W,F  | Rootkits          | (guest lecture, catch-up lectures) | |
| 14 | Apr 24 | M,W,F  | (new ??? HW)      | [Digital Forensics](../slides/forensics.html#/), [Virtual Machines](../slides/vms.html#/) | |
| 15 | May 1  | M      | Digital forensics | [Course reflection](../slides/reflection.html#/) | |

[1]: https://canvas.its.virginia.edu/courses/59093
