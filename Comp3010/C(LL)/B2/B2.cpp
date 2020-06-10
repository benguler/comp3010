#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#include "B2.h"
 
 struct B2Expr *newIf(struct B2Expr *expr1, struct B2Expr *expr2, struct B2Expr *expr3){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = IF;
	
	expr->data.b2if.expr1 = expr1;
	expr->data.b2if.expr2 = expr2;
	expr->data.b2if.expr3 = expr3;
	
	return expr;
	
}

struct B2Expr *newApp(int n, ...){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));

	expr->type = APP;

	std::vector<B2Expr *> *exprs;

	va_list args;

	va_start(args, n);
	
	expr->data.b2app.exprs = new std::vector<B2Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b2app.exprs->push_back(va_arg(args, struct B2Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}

struct B2Expr *newVal(int n){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = VAL;
	
	expr->data.b2val.n = n;
	expr->data.b2val.isBool = false;
	
	return expr;
	
}

struct B2Expr *newVal(bool b){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = VAL;
	
	expr->data.b2val.b = b;
	expr->data.b2val.isBool = true;
	
	return expr;
	
}

struct B2Expr *newVal(struct B2Expr *prim){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = VAL;
	
	expr->data.b2val.prim = prim;
	
	return expr;
	
}

struct B2Expr *newPrim(const char *pType){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = PRIM;
	
	expr->data.b2prim.pType = pType;
	
	return expr;
	
}

struct B2Expr *newVar(const char *vName){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = VAR;
	
	expr->data.b2var.vName = vName;
	
	return expr;
	
}

struct B2Expr *newFunc(const char *fName){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = FUNC;
	
	expr->data.b2func.fName = fName;
	
	return expr;
	
}

struct B2Def *newDef(int n, struct B2Expr *func, struct B2Expr *expr1, ...){
	struct B2Def *def = (struct B2Def *)malloc(sizeof(struct B2Def));
	
	def->func = func;
	def->expr = expr1;
	
	va_list args;

	va_start(args, expr1);
	
	def->vars = new std::vector<B2Expr *>; 
	
	for(int i = 0; i<n; i++){
		def->vars->push_back(va_arg(args, struct B2Expr *));
		
	}
	
	va_end(args);

	return def;
	
}
