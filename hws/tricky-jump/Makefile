CC 		= gcc
TARGETS = test1 test2 test3
CFLAGS	= -m64 -fomit-frame-pointer -fno-stack-protector -g -no-pie -O0

all: $(TARGETS)
	nasm -g -f elf64 -o nops.o nops.s
	gcc -c $(CFLAGS) test1.c
	gcc -o test1 $(CFLAGS) test1.o nops.o
	objdump -d test1 > test1.objdump.txt
	hexdump -C test1 > test1.hexdump.txt
	readelf -e test1 > test1.readelf.txt
	gcc -c $(CFLAGS) test2.c
	gcc -o test2 $(CFLAGS) test2.o nops.o
	objdump -d test2 > test2.objdump.txt
	hexdump -C test2 > test2.hexdump.txt
	readelf -e test2 > test2.readelf.txt
	gcc -c $(CFLAGS) test3.c
	gcc -o test3 $(CFLAGS) test3.o nops.o
	objdump -d test3 > test3.objdump.txt
	hexdump -C test3 > test3.hexdump.txt
	readelf -e test3 > test3.readelf.txt
	gcc -c $(CFLAGS) test4.c
	gcc -o test4 $(CFLAGS) test4.o nops.o
	objdump -d test4 > test4.objdump.txt
	hexdump -C test4 > test4.hexdump.txt
	readelf -e test4 > test4.readelf.txt
	g++ -c $(CFLAGS) test5.cpp
	g++ -o test5 $(CFLAGS) test5.o nops.o
	objdump -d test5 > test5.objdump.txt
	hexdump -C test5 > test5.hexdump.txt
	readelf -e test5 > test5.readelf.txt
	source-highlight -d *.c *.s *.cpp Makefile
	/bin/rm -f *.o
