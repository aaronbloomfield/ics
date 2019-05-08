CS 3501: Course grades
======================

As per the [syllabus](syllabus.html) ([md](syllabus.md)), the grades were computed by:

- Programming and written assignments (45%)
- Two midterms (15% each)
- Final exam (20%)
- Class participation, pop quizzes (5%)


### Participation, quizzes, and attendance

I did not give as many quizzes -- or take attendance -- as many times as I would have liked.  There were only two quizzes, and only one attendance day, and having so few would cause missing any one (or doing poorly on any one) to have a particularly large impact.  However, the low number of quizzes was factored into the final course curve (below).  Also, this was worth a relatively small percentage of the course grade (only 5%).  I realize this does not necessarily capture all the ways that people could have participated in the course.


### Exam 2 curve

I overdid it with exam 2, and I said there would be a 20 percentage point curve.  Since exam 2 was worth 15% of the final grade, 20% of that is 3%.  So everybody gets 3 points added to their final course average because of exam 2.


### Rounding

Yes, we round grades. Note that `round(x) == floor(x+0.5)`. So we added 0.5 points to everybody's curve to take into account rounding. Note that this means rounding is already taken care of, so if you get an 89.9999999999999999999, it's still a B+, no matter how many times you ask - you don't get to round a second time (in reality, it was an 89.49 before rounding).


### Course curve

The course curve so far was 3.0 points for exam 2.  Taking into account factors such as the grades on the final exam, as well as the participation from above, I added another 1.5 points to the overall course curve.  This was also to make up for some harder than intended grading on the homeworks, and to bring the average of the course up a bit.  Thus, the course curve was 4.5 points.  With rounding, as discussed above, that yields a bonus of 5.0 points to one's average.


### Course grades

**The overall course got a curve of 4.5 points.**  With rounding, that is an additional **5.0 points** to add to your final course average.  Thus, add 4.5 to your course average from the [course gradebook](https://libra.cs.virginia.edu/~pedagogy/gradebook.php), and that round that up if necessary.  Or add 5.0 to your course average, and don't round it.

Rather than add this to everybody's average, I instead lowered the course threshold.  Whereas the A- threshold is 90.0 (89.5 with rounding), it was lowered to 85.0 (84.5 with rounding).  This is mathematically equivalent, but far easier to implement.

We followed the standard decade curve, as shown in the table below.  Note that this table does *NOT* include the course curve of 4.5 (or 5.0) points -- it's just the standard decade table of grades.


| Minimum course average | Letter grade |
|------------------------|--------------|
| 60 | D- |
| 63 | D |
| 67 | D+ |
| 70 | C- |
| 73 | C |
| 77 | C+ |
| 80 | B- |
| 83 | B |
| 87 | B+ |
| 90 | A- |
| 93 | A |
| 98 | A+ |

Note that the A+ range range follows a slightly different pattern -- it is the top few students in the class by average, and ended up being 93.0 (98.0 with the course curve) this semester.
