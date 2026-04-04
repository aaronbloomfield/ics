ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
else
        SED=sed -i
endif

source:
	source-highlight -d *.py Makefile
	$(SED) s/"<html>"/"<html lang='en'>"/ *.py.html Makefile.html
	$(SED) s/"<pre>"/"<main><pre>"/ *.py.html Makefile.html
	$(SED) s_"</pre>"_"</pre></main>"_ *.py.html Makefile.html
	/bin/rm -f *.todel
