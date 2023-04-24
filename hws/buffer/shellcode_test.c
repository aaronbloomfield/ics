#include <stdio.h>
#include <sys/mman.h>

extern void shellcode();

void main() {
  int on_stack;
  mprotect((char *)((long)&on_stack & -0x1000), 1, PROT_READ | PROT_WRITE | PROT_EXEC);
  mprotect((char *)((long)&main & -0x1000), 1, PROT_READ | PROT_WRITE | PROT_EXEC);
  printf ("Before shell code is executed.\n");
  shellcode();
  printf ("After shell code is executed.\n");
}
