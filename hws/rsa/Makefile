ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
else
        SED=sed -i
endif

convert:
	astyle --style=java --pad-header RSA.java
	source-highlight -d RSA.java
	source-highlight -d rsa.py
	source-highlight -d test-rsa.sh test-rsa.bat
	$(SED) s/"<html>"/"<html lang='en'>"/g RSA.java.html rsa.py.html test-rsa.sh.html test-rsa.bat.html
	/bin/rm -f *.todel

	#doxygen
