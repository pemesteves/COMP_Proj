.class public WhileAndIF
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 7
	bipush	20
	istore_1
	bipush	10
	istore_2
	bipush	10
	newarray int
	astore	4
	iload_1
	iload_2
	if_icmpge notLess_33
	iconst_1
	goto endLess_33
notLess_33:
	iconst_0
endLess_33:
	ifeq else_33
	iload_1
	iconst_1
	isub
	istore_3
	goto endIf_33
else_33:
	iload_2
	iconst_1
	isub
	istore_3
endIf_33:
while_12:
	iconst_0
	iconst_1
	isub
	iload_3
	if_icmpge notLess_34
	iconst_1
	goto endLess_34
notLess_34:
	iconst_0
endLess_34:
	ifeq endWhile_12
	aload	4
	iload_3
	iload_1
	iload_2
	isub
	iastore
	iload_3
	iconst_1
	isub
	istore_3
	iload_1
	iconst_1
	isub
	istore_1
	iload_2
	iconst_1
	isub
	istore_2
	goto while_12
endWhile_12:
	iconst_0
	istore_3
while_13:
	iload_3
	aload	4
	arraylength
	if_icmpge notLess_35
	iconst_1
	goto endLess_35
notLess_35:
	iconst_0
endLess_35:
	ifeq endWhile_13
	aload	4
	iload_3
	iaload
	invokestatic io/println(I)V
	iload_3
	iconst_1
	iadd
	istore_3
	goto while_13
endWhile_13:
	return
.end method


