#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#include "B3.h"

struct B3Expr *newIf(struct B3Expr *expr1, struct B3Expr *expr2, struct B3Expr *expr3){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));
	
	expr->type = IF;
	
	expr->data.b3if.expr1 = expr1;
	expr->data.b3if.expr2 = expr2;
	expr->data.b3if.expr3 = expr3;
	
	return expr;
	
}

struct B3Expr *newApp(int n, ...){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));

	expr->type = APP;

	std::vector<B3Expr *> *exprs;

	va_list args;

	va_start(args, n);
	
	expr->data.b3app.exprs = new std::vector<B3Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b3app.exprs->push_back(va_arg(args, struct B3Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}

struct B3Expr *newApp(std::vector<B3Expr *> *exprs){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));

	expr->type = APP;
	
	expr->data.b3app.exprs = exprs;
	
	return expr;
	
}

struct B3Expr *newVal(int n){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));
	
	expr->type = VAL;
	
	expr->data.b3val.n = n;
	expr->data.b3val.type = VALNUM;
	
	return expr;
	
}

struct B3Expr *newVal(bool b){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));
	
	expr->type = VAL;
	
	expr->data.b3val.b = b;
	expr->data.b3val.type = VALBOOL;
	
	return expr;
	
}

struct B3Expr *newVal(struct B3Expr *expr1){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));
	
	expr->type = VAL;
	
	switch(expr1->type){
		case PRIM:
			expr->data.b3val.prim = prim;
			expr->data.b3val.type = VALPRIM;
			break;
			
		case LAMB:
			expr->data.b3val.prim = prim;
			expr->data.b3val.type = VALPRIM;
			break;
	
		default:
			break;
			
	}
	
	expr->data.b3val.prim = prim;
	expr->data.b3val.type = VALPRIM;
	
	return expr;
	
}

struct B3Expr *newPrim(const char *pType){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));
	
	expr->type = PRIM;
	
	expr->data.b3prim.pType = pType;
	
	return expr;
	
}

struct B3Expr *newVar(const char *vName){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));
	
	expr->type = VAR;
	
	expr->data.b3var.vName = vName;
	
	return expr;
	
}

struct B3Expr *newLambda(int n, B3Expr *expr1, ...){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));

	expr->type = LAMB;

	expr->data.b3lambda.expr = expr1;

	std::vector<B3Expr *> *vars;

	va_list args;

	va_start(args, n);
	
	expr->data.b3lambda.vars = new std::vector<B3Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b3lambda.vars->push_back(va_arg(args, struct B3Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}
