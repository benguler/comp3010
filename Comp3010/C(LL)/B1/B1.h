#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#ifndef B1_H
#define B1_H

using namespace std;

enum ExprType { IF, APP, VAL, PRIM};

struct B1Expr {
	enum ExprType type;
	
	union{
		struct {
			struct B1Expr *expr1;
			struct B1Expr *expr2;
			struct B1Expr *expr3;
			
		} b1if;
		
		struct {
			std::vector<B1Expr> *exprs;
			
		} b1app;
		
		struct {
			bool b;
			int n;
			struct B1Expr *prim;
			
		} b1val;
		
		struct {
			char t;
			
		} b1prim;
		
	} data;
	
};

struct B1Expr *newIf(struct B1Expr *expr1, struct B1Expr *expr2, struct B1Expr *expr3);

struct B1Expr *newApp(int n, B1Expr *expr, ...);

struct B1Expr *newVal(int n);

struct B1Expr *newVal(bool b);

struct B1Expr *newVal(struct B1Expr *prim);

struct B1Expr *newPrim(char t);


	
#endif

