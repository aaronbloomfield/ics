CC=clang
CFLAGS =-m64 -fno-stack-protector -fomit-frame-pointer -Wno-unused-result
DASFLAGS=-S -masm=intel
ASFLAGS=-mllvm --x86-asm-syntax=intel -S

main:
	$(CC) $(CFLAGS) -ggdb -o overflow.exe overflow.c
	$(CC) $(CFLAGS) $(ASFLAGS) -o overflow.s overflow.c
	source-highlight -d *.c *.s Makefile
	objdump -d overflow.exe > overflow.exe.objdump.txt
