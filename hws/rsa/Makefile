ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
else
        SED=sed -i
endif

convert:
	astyle --style=java --pad-header RSA.java
	make source

source:
	custom-source-highlight.sh *.java *.py *.sh *.bat Makefile
