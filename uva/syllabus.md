CS 3710: Syllabus for Introduction to Cybersecurity (ICS), spring 2023
=========================================================

[Go up to the ICS UVa page](index.html) ([md](index.md))

## Quick version

I realize that nobody reads these through.  So the TL;DR version:

- This course is using Canvas, not Collab; the Canvas workspace is [here](https://canvas.its.virginia.edu/courses/59093)
- One midterm, on Wednesday, March 1st, during lecture; one final, on Monday, May 8th, from 2pm - 5pm
- Grades are 45% homeworks, 20% midterm, 25% final exam, and 10% participation (quizzes & attendance)
- Everything is going to be auto-graded, but you can submit multiple times; the grades won't be known until the late due date has passed
- If you encounter any rough spots in the course -- please be patient, and provide constructive feedback, and I'll do my best to get it all fixed
- There will be both larger programming homeworks and smaller tutorial-style  homeworks or readings -- sometimes one of each will be assigned at any given time
- Lecture attendance is required, and occasional attendance quizzes will be taken to ensure that
- Readings will typically have in-class quizzes when they are due
- Attendance quizzes and reading quizzes may be online, so bring a connected device (phone is fine)
- Due to the size of the course, please contact me via support request; the link is on the Canvas landing page
- Office hours are posted on the Canvas landing page
- I want you to succeed in this course and enjoy it -- if you are having any problems (personal, academic, what-not) that I can help with, please let me know

And now for the really long-winded version...


## Spring 2023

### Course Staff ###

- [Aaron Bloomfield](https://www.cs.virginia.edu/~asb) / aaron at virginia dot edu.  Office: Rice Hall, room 402.  Office hours are posted on the Canvas landing page, and will start the second week of the semester
- Multiple undergraduate TAs and possibly a Masters TA; their office hours are also posted on the Canvas landing page, and will start the second week of the semester

### Course Info ###

- Lecture: M/W/F 9:00-9:50 in Olsson 120
- Coordination is through Canvas, not Collab; the Canvas workspace is [here](https://canvas.its.virginia.edu/courses/59093)
- Email: none; fill out a support request instead; the link is on the Canvas landing page
- Announcements will be done through the [daily announcements slide set](daily-announcements.html#/)
- TAs and their office hours will be posted on the Canvas landing page; all office hours start the second day of classes


**Course content:** All the course content is available free online at 
[http://aaronbloomfield.github.io/ics](http://aaronbloomfield.github.io/ics), which is from a public github repository: [https://github.com/aaronbloomfield/ics](https://github.com/aaronbloomfield/ics).  All the source code in that repository is released under a [GPL 3.0 license](https://www.gnu.org/licenses/gpl-3.0.en.html), and all the non-source code material in that repository is released under a [Creative Commons license](https://creativecommons.org/licenses/by-sa/4.0/) (CC BY-SA).  Note that this license and the public availability does NOT apply to the lecture videos.

**Lectures:** Lecture attendance is required.  There will be occasional attendance quizzes to verify this.  These quizzes may be online, so bring a connected device (phone is fine) to take said quizzes.

**Readings:** Due to the rapidly changing nature of this topic, there is no assigned textbook.  Various online readings will be assigned.  You should expect a reading quiz on the day that a reading is due.  These quizzes may be online, so bring a connected device (phone is fine) to take said quizzes.

**Purchases:** As mentioned above, no textbook is required for this course.  
You don't have to purchase anything else for this course.  But you do have to have a relatively modern *notebook* computer -- both for the course content (online meetings and video) and for VirtualBox (see Computing Resources, below).  If you do not have one, or yours breaks during the semester, speak to me as the department can loan you one.

**Course Description:** This course is meant as a general introduction to cybersecurity.  It will prepare students to take any of the follow-on cybersecurity courses within the curriculum.  The course content is split into six "modules": introduction, ethics and policy, cryptography, networks, binary exploits, modern topics, and digital forensics.  

The course objectives are:

- Understand the ethical and policy context for cybersecurity in today's society
- Understand how to better safeguard one's personal computer
- Understand the basics of advanced topics in cybersecurity including encryption, digital forensics, binary exploits, and networks
- Understand the modern concepts in cybersecurity attacks and prevention

**Availability:** It is important to me to be available to my students, and to address their concerns. If you cannot meet with me during office hours, e-mail (or, ideally, submit a support request) and I will try to find the time to meet. That being said, like everybody else I am quite busy, so it may take a day or more to find a time to meet. And if you have any comments on the course - what is working, what is not working, what can be done better, etc. - I am very interested in hearing about them. There is an anonymous feedback tool through Canvas, or you can send me an e-mail (please do NOT email the TAs directly). I tend to get bogged down by e-mail as the semester progresses, so seeing me "in person" (during office hours or right after class) is often a good way to get a more immediate response.

**Prerequisites:** CS 2150 (Program and Data Representation) or CS 3100 (Data Structures and Algorithms 2) with a grade of C- or above.  As per departmental policy, this pre-req will be checked, and if it is not met, you will be de-registered from the course.

**Grades:** Grades will be calculated by the following formula:

- Programming and written assignments (45%)
- Midterm (20%)
- Final exam (25%)
- Class participation, pop quizzes (10%)

I *expect* that grades will follow the standard decade curve: 90 and above is an A of some sort (A-, A, or A+), 80 and above is some sort of a B, etc.  **Note:** I reserve the right to modify the weighting (changing the curve, adding pop quizzes, etc.), especially if attendance drops off significantly.  In particular, if the grade averages are very high, then you will need higher than a 90 to get an A-.

**Assignments:** During a regular (fall or spring) semester, the intent is for each module to have two assignments: a written homework and a programming homework.  During a summer term, there will be less than that.  The written homeworks will be submitted as a PDF, and will consist of writing, math, and short programs.  
All submissions are through Gradescope, the link to which is on the Canvas landing page.  Due to the class size, and the limited TA support, all assignments will be auto-graded.  The programming assignments will have their source code submitted.  You will need to be familiar with the [homework policies](hw-policies.html) ([md](hw-policies.md)), as you will be bound by them on the assignments.

**Course rules:** You are fully responsible for all material presented in class and on the required readings. Exams and due dates are scheduled in advance. A grade of zero will be recorded for missed exams and late assignments unless prior arrangements are made (see below for details) or there are truly extenuating circumstances (which will require appropriate documentation). Assignments turned in after the due date are penalized 25% per day (or fraction thereof) late; this means a maximum of 3 days (i.e. 72 hours) late. In most cases, you are free to develop assignments on any language/platform/OS you wish. Some assignments may require a specific operating system setup, or a particular required programming language, which will be provided if necessary.  However, you are responsible for porting your code to the system the class is using and ensuring that it runs correctly for us when we grade your assignment. Our reference system will specified for each assignment, but will generally be Ubuntu Linux.  The Gradescope submission auto-grader will verify this upon submission (and immediately report the results back to you). Most assignments will be graded by automated testing.

**Disabilities:**  The University of Virginia strives to provide accessibility to all students. If you require an accommodation to fully access this course, please contact the Student Disability Access Center (SDAC) at (434) 243-5180 or sdac@virginia.edu. If you are unsure if you require an accommodation, or to learn more about their services, you may contact the SDAC at the number above or by visiting their website at http://studenthealth.virginia.edu/student-disability-access-center/faculty-staff.

**Special Circumstances:** Students with special circumstances (athletics, extra time required on exams, final exam conflicts, SDAC considerations, etc.) need to let me know during the **first two weeks of class**.

**Exams:** There will be one midterm exam (worth 20% of the final grade) and one final exam (worth 25% of the final grade).

- Midterm exam: Wednesday, March 1st, during lecture
- Final exam: Monday, May 8th, from 2pm to 5pm

Under **NO** circumstances will anybody be allowed to take the final exam early.  You may **ONLY** request to take the final exam at a different time if you have a final exam **CONFLICT**, not a busy final exam schedule.  Since there are no other exams scheduled during that time, it is unlikely that you will have a conflict.

**Regrades:** You may submit graded homeworks and exams for regrading within one week of when they are returned to you (less time for the final due to the end of the semester).  Regrades are submitted via Gradescope (for written assignments) or support requests (for programming assignments).  As long as people submit responsibly, I will not institute a frivolous regrade policy that existed in some semesters of CS 2150 (if one is instituted, you will be told of this ahead of time).  This time limit will be strictly enforced.

**Honor pledge:** The UVA Honor Code is in effect for this course.  There is one course specific addition: You may not look at any other student's code for ANY reason, period.  Not to debug, not to help, not to learn.  You may not let another student look at *your* code for any reason.  Needless to say, you cannot copy code from online sources unless the assignment specifically allows it (and in those cases, you must cite your source).  The next paragraph describes this more.

Students are encouraged to discuss programs and assignments in general. However, helping others find bugs in existing programs, using another person's code, or writing code for someone else is cheating and a violation of the University's Honor System. Basically, you should not be looking at another person's code for **ANY** reason (obviously you can after BOTH have submitted the assignment, or if there is a group project). This includes consulting programming solutions to assignments from previous years. This also includes looking up solutions online.  Any honor violation or cheating will be referred to the honor committee, and will result in an *immediate failure for the course*, regardless of the outcome of the honor trial or your other grades. No exceptions! I am very strict on this, and one have successfully raised honor charges against students in the past due to violations of this policy - and I've blocked people from graduation because of honor offenses.

**Your Well Being:** The Engineering School proudly serves as a safe space for its students and aims to promote their well being. If you are feeling overwhelmed, stressed, or isolated, there are many individuals here who are ready and wanting to help.   In addition to the course instructors, you can seek help through the Engineering Undergraduate office (Thornton A122), or Alex Hall (aec5d, 924-7601) who is the assistant dean of students for the Engineering school.

Alternatively, there are also other University of Virginia resources available. The Student Health Center offers Counseling and Psychological Services (CAPS) for its students. Call 434-243-5150 (or 434-972-7004 for after hours and weekend crisis assistance) to get started and schedule an appointment. If you prefer to speak anonymously and confidentially over the phone, call Madison House's HELP Line at any hour of any day: 434-295-8255.

If you or someone you know is struggling with gender, sexual, or domestic violence, there are many community and University of Virginia resources available. The Office of the Dean of Students, Sexual Assault Resource Agency (SARA), Shelter for Help in Emergency (SHE), and UVA Women's Center are ready and eager to help. Contact the Director of Sexual and Domestic Violence Services at 434-982-2774.
