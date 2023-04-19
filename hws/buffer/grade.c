/*
 * grade.c
 *
 * by Aaron Bloomfield, 2018.  This is part of the aaronbloomfield/ics
 * github repository, and is released under the same license (CC
 * BY-SA) as that entire repo.
 *
 * This program is designed to be vulnerable to a shellcode-based
 * buffer overflow.  Students must have it print a grade other than an
 * F to receive any credit.
 *
 * To run it, be sure to invoke `setarch x86_64 -v -LR bash` first
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>

// used to indicate if we are printing the buffer address
int print_buffer_address = 0;

// we use this to pass the name back from the vulnerable() function to
// main()
char global_name[100];

// the vulnerable function
void vulnerable() {
  char name[200];
  if ( print_buffer_address ) {
    unsigned long addr = (unsigned long) name + 0x20;
    printf("%lx\n",addr);
    exit(0);
  } else {
    printf ("Please enter your name:\n");
    fgets (name, 1000000, stdin);
    strncpy (global_name, name, 199); // look, ma, no buffer overflow possible here!
  }
  return; // not necessary, but useful to set a breakpoint at grade.c:38
}


void main(int argc, char *argv[]) {

  if ( (argc == 2) && (!strcmp(argv[1],"--print-buffer-address")) )
    print_buffer_address = 1;

  // we use this to help find the address of the stack in the next command
  int on_stack;

  // we set the stack to allow execution; it already allows reading
  // and writing
  mprotect((char *)((long)&on_stack & -0x1000), 1, PROT_READ | PROT_WRITE | PROT_EXEC);

  // we set the executable code (the .text section) to be writable; it
  // already allws reading and execution
  mprotect((char *)((long)&main & -0x1000), 1, PROT_READ | PROT_WRITE | PROT_EXEC);

  // call the vulnerable function
  vulnerable();

  // remove trailing newline
  global_name[strlen(global_name)-1] = 0;

  // print the grade
  printf ("\n%s, your grade on this assignment is a F\n", global_name);
}
