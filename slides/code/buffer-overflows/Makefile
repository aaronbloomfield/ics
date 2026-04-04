SED=sed -i
ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
endif
CC=clang
CFLAGS =-m64 -fno-stack-protector -fomit-frame-pointer -Wno-unused-result
DASFLAGS=-S -masm=intel
ASFLAGS=-mllvm --x86-asm-syntax=intel -S

main:
	$(CC) $(CFLAGS) -ggdb -o overflow.exe overflow.c
	$(CC) $(CFLAGS) $(ASFLAGS) -o overflow.s overflow.c
	objdump -d overflow.exe > overflow.exe.objdump.txt
	make source

source:
	source-highlight -d *.c *.s Makefile
	$(SED) s/"<html>"/"<html lang='en'>"/ *.c.html *.s.html Makefile.html
	$(SED) s/"<pre>"/"<main><pre>"/ *.c.html *.s.html Makefile.html
	$(SED) s_"</pre>"_"</pre></main>"_ *.c.html *.s.html Makefile.html
	/bin/rm -f *.todel
