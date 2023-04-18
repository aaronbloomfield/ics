#include <stdio.h>
void main() {
	void* buffer;
	FILE *fp = fopen("address.txt","r");
	fscanf(fp, "%lx", (unsigned long)&buffer);
	fclose(fp);
	printf("Buffer address: %lx\n",buffer);
}