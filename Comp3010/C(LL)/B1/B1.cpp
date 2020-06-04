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

struct B1Expr *newApp(int n, ...){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));

	expr->type = APP;

	std::vector<B1Expr *> *exprs;

	va_list args;

	va_start(args, n);
	
	expr->data.b1app.exprs = new std::vector<B1Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b1app.exprs->push_back(va_arg(args, struct B1Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}

struct B1Expr *newVal(int n){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = VAL;
	
	expr->data.b1val.n = n;
	expr->data.b1val.isBool = false;
	
	return expr;
	
}

struct B1Expr *newVal(bool b){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = VAL;
	
	expr->data.b1val.b = b;
	expr->data.b1val.isBool = true;
	
	return expr;
	
}

struct B1Expr *newVal(struct B1Expr *prim){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = VAL;
	
	expr->data.b1val.prim = prim;
	
	return expr;
	
}

struct B1Expr *newPrim(const char *pType){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = PRIM;
	
	expr->data.b1prim.pType = pType;
	
	return expr;
	
}

int interp(struct B1Expr *expr){
	switch(expr->type){
		case IF:
			if(interp(expr->data.b1if.expr1) == 0){
				return interp(expr->data.b1if.expr3);
				
			}else{
				return interp(expr->data.b1if.expr2);
				
			}
			
		case APP:
			{
				vector<B1Expr *> *exprs = expr->data.b1app.exprs;
				
				const char *pType = exprs->at(0)->data.b1val.prim->data.b1prim.pType;
				
				if(strcmp(pType, "+") == 0){
					return interp(exprs->at(1)) + interp(exprs->at(2));
					
				}else if(strcmp(pType, "*") == 0){
					return interp(exprs->at(1)) * interp(exprs->at(2));
					
				}else if(strcmp(pType, "/") == 0){
					return interp(exprs->at(1)) / interp(exprs->at(2));
					
				}else if(strcmp(pType, "-") == 0){
					return interp(exprs->at(1)) - interp(exprs->at(2));
					
				}else if(strcmp(pType, "<=") == 0){
					if(interp(exprs->at(1)) <= interp(exprs->at(2))){
						return 1;
						
					}
					
					return 0;
					
				}else if(strcmp(pType, "<") == 0){
					if(interp(exprs->at(1)) < interp(exprs->at(2))){
						return 1;
						
					}
					
					return 0;
					
				}else if(strcmp(pType, "=") == 0){
					if(interp(exprs->at(1)) == interp(exprs->at(2))){
						return 1;
						
					}
					
					return 0;
					
				}else if(strcmp(pType, "=") == 0){
					if(interp(exprs->at(1)) > interp(exprs->at(2))){
						return 1;
						
					}
					
					return 0;
					
				}else if(strcmp(pType, ">=") == 0){
					if(interp(exprs->at(1)) >= interp(exprs->at(2))){
						return 1;
						
					}
					
					return 0;
					
				}
				
				return 0;
				
			}
		case VAL:
			if(expr->data.b1val.isBool == false){
				return expr->data.b1val.n;
				
			}
			
			if(expr->data.b1val.b){
				return 1;
				
			}
			
			return 0;
			
		default:
			return 0;
		
	}
	
}

