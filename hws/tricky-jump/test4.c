#include <stdio.h>

char *output = "hello world\n";
char length = 12;

void print_char(int i) {
	if ( i > length )
		return;
	putchar(output[i]);
	print_char(++i);
}

int main() {
	print_char(0);
	return 0;
}