#include <stdio.h>

char *output = "hello world\n";
char length = 12;

void subroutine() {
	for ( int i = 0; i < length; i++ )
		putchar(output[i]);
}

int main() {
	subroutine();
	return 0;
}