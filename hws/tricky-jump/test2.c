#include <stdio.h>


void subroutine() {
	char buffer[100] = "hello world";
	printf("%s\n",buffer);
}

int main() {
	subroutine();
	return 0;
}