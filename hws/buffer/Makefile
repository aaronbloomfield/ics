ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
else
        SED=sed -i
endif

all:
	gcc -o stack_addr stack_addr.c 
	gcc -fno-stack-protector -m64 -fomit-frame-pointer -o grade grade.c
	nasm -g -f elf64 -o shellcode.o shellcode.s
	gcc -g -m64 -c shellcode_test.c
	gcc -g -no-pie -m64 -o shellcode_test shellcode.o shellcode_test.o
	gcc -o read-buffer-address read-buffer-address.c
	make source

source:
	source-highlight -d *.c *.s *.py Makefile
	$(SED) s/"<html>"/"<html lang='en'>"/ *.c.html *.s.html *.py.html Makefile.html
	$(SED) s/"<pre>"/"<main><pre>"/ *.c.html *.s.html *.py.html Makefile.html
	$(SED) s_"</pre>"_"</pre></main>"_ *.c.html *.s.html *.py.html Makefile.html
	/bin/rm -f *.todel
