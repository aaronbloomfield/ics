	.text
	.intel_syntax noprefix
	.file	"overflow.c"
	.globl	do_something_with       # -- Begin function do_something_with
	.p2align	4, 0x90
	.type	do_something_with,@function
do_something_with:                      # @do_something_with
	.cfi_startproc
# %bb.0:
	sub	rsp, 24
	.cfi_def_cfa_offset 32
	movabs	rax, offset .L.str
	mov	qword ptr [rsp + 16], rdi
	mov	rsi, qword ptr [rsp + 16]
	mov	rdi, rax
	mov	al, 0
	call	printf
	mov	dword ptr [rsp + 12], eax # 4-byte Spill
	add	rsp, 24
	ret
.Lfunc_end0:
	.size	do_something_with, .Lfunc_end0-do_something_with
	.cfi_endproc
                                        # -- End function
	.globl	vulnerable              # -- Begin function vulnerable
	.p2align	4, 0x90
	.type	vulnerable,@function
vulnerable:                             # @vulnerable
	.cfi_startproc
# %bb.0:
	sub	rsp, 120
	.cfi_def_cfa_offset 128
	movabs	rdi, offset .L.str
	lea	rsi, [rsp + 16]
	mov	al, 0
	call	__isoc99_scanf
	lea	rdi, [rsp + 16]
	mov	dword ptr [rsp + 12], eax # 4-byte Spill
	call	do_something_with
	add	rsp, 120
	ret
.Lfunc_end1:
	.size	vulnerable, .Lfunc_end1-vulnerable
	.cfi_endproc
                                        # -- End function
	.globl	main                    # -- Begin function main
	.p2align	4, 0x90
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:
	push	rax
	.cfi_def_cfa_offset 16
	mov	dword ptr [rsp + 4], 0
	call	vulnerable
	xor	eax, eax
	pop	rcx
	ret
.Lfunc_end2:
	.size	main, .Lfunc_end2-main
	.cfi_endproc
                                        # -- End function
	.type	.L.str,@object          # @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3


	.ident	"clang version 6.0.0-1ubuntu2 (tags/RELEASE_600/final)"
	.section	".note.GNU-stack","",@progbits
