ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
else
        SED=sed -i
endif

all:
	source-highlight -d *.py *.s *.c
	$(SED) s/"<html>"/"<html lang='en'>"/g *.py.html *.s.html *.c.html
	/bin/rm -f *.todel
	gcc -o stack_addr stack_addr.c 
	gcc -fno-stack-protector -m64 -fomit-frame-pointer -o grade grade.c
	nasm -g -f elf64 -o shellcode.o shellcode.s
	gcc -g -m64 -c shellcode_test.c
	gcc -g -no-pie -m64 -o shellcode_test shellcode.o shellcode_test.o
	gcc -o read-buffer-address read-buffer-address.c
