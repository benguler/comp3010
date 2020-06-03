#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#include "B1.h"

using namespace std;

struct B1Expr *newIf(struct B1Expr *expr1, struct B1Expr *expr2, struct B1Expr *expr3){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = IF;
	
	expr->data.b1if.expr1 = expr1;
	expr->data.b1if.expr2 = expr2;
	expr->data.b1if.expr3 = expr3;
	
	return expr;
	
}

struct B1Expr *newApp(int n, B1Expr *e, ...){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = APP;
	
	va_list valist;
	
	std::vector<B1Expr> *exprs;
	
	for(int i = 0; i < n; i++){
		exprs->push_back(va_arg(valist, B1Expr));
		
	}
	
	expr->data.b1app.exprs = exprs;
	
	return expr;
	
}

struct B1Expr *newVal(int n){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->data.b1val.n = n;
	
	return expr;
	
}

struct B1Expr *newVal(bool b){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->data.b1val.b = b;
	
	return expr;
	
}

struct B1Expr *newVal(struct B1Expr *prim){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->data.b1val.prim = prim;
	
	return expr;
	
}

struct B1Expr *newPrim(char t){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->data.b1prim.t = t;
	
	return expr;
	
}

