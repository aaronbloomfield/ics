ICS: Programming Homework: SQL, CSRF, and XSS
=============================================

[Go up to the main ICS homeworks page](index.html) ([md](index.md))


### Introduction

There are three parts to this assignment.  As part of this assignment, you will have to create a document, called `mst3k-websecurity.pdf` (where mst3k is your userid), and submit that file.  We aren't looking for any fancy write-up - just an explanation of what you did, and the results you got.  That being said, it should be legible.  So make sure you indicate what answers are for what questions, etc.

Your final report should be a PDF file.  It need not be long, but it must answer the questions posed below.


### Part 1: SQL Injection Attack

First, you should be familiar with SQL and SQL injection attacks.  For review, look at the first half of the [SQL, XSS, and CSRF slide set](../slides/sql-xss-csrf.html#/).


View the page at [https://libra.cs.virginia.edu/~insecure/sqlinjection.php](https://libra.cs.virginia.edu/~insecure/sqlinjection.php) - note that you will have to log in via Netbadge to view this page.  From this page, you will need to execute an SQL injection attack.  Note that the only confidential data in that database is the names and userids of the participants, and all of that information is considered "public" knowledge to the participants of the course (FERPA allows release of names; all students in this course are in the [UVa LDAP server](http://www.virginia.edu/cgi-local/ldapweb?asb2t), and the ID numbers were randomly generated).

Your task is to execute at least two SQL injection attacks using this page.  The first is a read-only attack, and from it you must obtain a piece of hidden information that is not otherwise displayed from the script.  For the second, you must make a modification to **your entry** in the table.  What modification you make is up to you.  However, the grade listed in the DB after this assignment is completed will be the grade you receive on this part of the assignment.

A note about comments: the slides stated that `-- ` (dash-dash-space) was how you start a comment in SQL, analogous to `//` in C++.  If that doesn't work (it varies by SQL version), try just `--` (dash-dash).

In your report, you should list the following:

- The exact 'userid' that you entered for each of the attacks
- The hidden information that you obtained
- The modification that you made to the database
- The **exact** time stamp for each of the attacks.  This allows us to verify it against the log file.  The time stamp is listed at the bottom of the page, and is the time stamp of the page that was served in response to your attack.

__YOU MAY NOT EXECUTE A DROP TABLE OR TRUNCATE TABLE OR DELETE COMMAND__.  Doing so is an honor violation.  Or any other command that interferes with other students completing their assignment.  This includes updating anybody else's grade.  I don't want to have to go and repair the database because somebody executed this command.

Honor pledge details: you are given permission to execute an SQL injection attack against particular URL for this assignment, as long as the attack does NOT contain a 'drop table', 'truncate', or 'delete' command, or a command that intentionally interferes with other students completing their assignment, or a command that updates another student's grade.

Lastly, please note that all entries are logged (and are not logged in the DB!).  Thus, if the DB is later erased, we can verify that you did (or did not!) properly execute the SQL injection attack.

### Part 2: Cross-site Scripting (XSS) Attack

**NOTE:** Some modern browsers have anti-XSS capability built in that prevents this type of attack.  So if things are working, try a different browser.  Chrome, in particular, does not work well with this type of attack, but Firefox is fine.

First, you should be familiar with Javascript and cross-site scripting attacks.  For review, look at the the [SQL, XSS, and CSRF slide set](../slides/sql-xss-csrf.html#/) slide set.

View the page at [https://libra.cs.virginia.edu/~insecure/xss.php](https://libra.cs.virginia.edu/~insecure/xss.php) - again, you will have to log in via Netbadge to view that page.  From this page, you will need to execute multiple XSS attacks, described below.  Also note that the the account number (which you will need to obtain) is a randomly set number - it is set the first time you access the page, stored in a cookie, and not changed again.  But if you try it from a different computer, you will see a separate account number.

There are six XSS attacks that you must do against this page.  While it seems like a lot, it's really only three separate XSS attacks, and one of them is exactly from the slide set.  So, really, you just have two XSS attacks to perform.

1. Perform an XSS attack that will change the account balance to a sufficient enough quantity to make the purchase.  This should be done via a posting to the web form
2. Perform the same XSS attack as above, but via a GET variable (i.e. via a URL).
3. Perform an XSS attack that will display the account number to the screen.  This must read the Javascript variable and display it, and should be done via a posting to the web form.
4. Perform the same XSS attack as above, but via a GET variable (i.e. via a URL).
5. Perform an XSS attack that will display the account number to the screen.  This must read via a cookie from the web browser, and display it, and should be done via a posting to the web form.
    - Note that a sophisticated XSS attack would send that account number somewhere over the network - we are just displaying it to the screen
6. Perform the same XSS attack as above, but via a GET variable (i.e. via a URL).

In your report, you should list the following:

- The data used in your XSS attack, and whether it was a GET or POST attack
- The 'secret' information that you obtained for the last two attacks (this is additional information in the database itself, and was RANDOMLY generated)
- The **exact** time stamp for each of the attacks.  This allows us to verify it against the log file.  The time stamp is listed at the bottom of the page, and is the time stamp of the page that was served in response to your attack.

A few notes:

- You can use the script at [https://meyerweb.com/eric/tools/dencoder/](https://meyerweb.com/eric/tools/dencoder/) to encode your Javascript into URL-encoded text
- When submitting an XSS attack via the submission of the form, you should enter `\n` to represent returns.  When submitting it via  GET variable (i.e. in the URL), you should enter '%0a' for a return.  Note that the conversion script (above) may not convert the returns properly - you may have to do that manually
- To write some text from Javascript to the web page, use 'document.write("foo");'
- To read a cookie in Javascript, print out the document.cookie variable
- Typically, once you have the form posting, you will encode that using the encoder URL, and put that onto the XSS script URL setting the 'userid' variable to the URL-encoded text
- Do not use the plus sign inside your script!  This includes in the document.write() call.  The server sees them as separating GET variables, when you want it to all be one variable.

Honor pledge details: you are given permission to execute XSS attacks against this particular URL for this assignment.

### Part 3 Cross-site Request Forgery (CSRF) Attack

View the page at [https://libra.cs.virginia.edu/~insecure/csrf.php](https://libra.cs.virginia.edu/~insecure/csrf.php) - again, you will have to log in via Netbadge to view that page.  From this page, you will need to execute multiple CSRF attacks, described below.

Your goal is to transfer $200 to 'mallory' via that URL.  This must be done two ways: 

- Via GET variables, so that it's an attack in a single URL.  This URL could be hidden through a link, a picture, etc.
- Via a form posting, which uses that URL as the 'action' value of the form.

Now try visiting the site but add `?token` to the link (so it's [https://libra.cs.virginia.edu/~insecure/csrf.php?token](https://libra.cs.virginia.edu/~insecure/csrf.php?token)).  This adds a CSRF token to the form.  Unfortunately for this bank's security, the token that was added is always the same.  Perform the two CSRF attacks (via GET and via POST) against this variant URL.  For the GET, your URL would be something like `.../csrf.php?token&foo=bar&...`, and for the post, the `action` field of the `form` tag would have the `csrf.php` part be `csrf.php?token`.

In your write-up, show the two URLs used (for the two GET attacks), and the two HTML forms used for the two POST attacks).

Honor pledge details: you are given permission to execute CSRF attacks against this particular URL for this assignment.

### Submission

You should submit a file called `mst3k-websecurity.pdf`, where mst3k is your userid.  Answers to all the above questions should be in that file.
