ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
else
        SED=sed -i
endif

convert:
	astyle --style=java --pad-header RSA.java
	make source

source:
	source-highlight -d *.java *.py *.sh *.bat Makefile
	$(SED) s/"<html>"/"<html lang='en'>"/ *.java.html *.py.html *.sh.html *.bat.html Makefile.html
	$(SED) s/"<pre>"/"<main><pre>"/ *.java.html *.py.html *.sh.html *.bat.html Makefile.html
	$(SED) s_"</pre>"_"</pre></main>"_ *.java.html *.py.html *.sh.html *.bat.html Makefile.html
	/bin/rm -f *.todel
