#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#ifndef B3_H
#define B3_H

using namespace std;

enum ExprType { IF, APP, VAL, PRIM, VAR, LAMB};
enum ValType { VALBOOL, VALNUM, VALPRIM, VALLAMB};

struct B3Expr {
	enum ExprType type;
	
	union{
		struct {
			struct B3Expr *expr1;
			struct B3Expr *expr2;
			struct B3Expr *expr3;
			
		} b3if;
		
		struct {
			std::vector<B3Expr *> *exprs;
			
		} b3app;
		
		struct {
			enum ValType type;
			bool b;
			int n;
			struct B3Expr *prim;
			struct B3Expr *lamb;
			
		} b3val;
		
		struct {
			const char *pType;
			
		} b3prim;
		
		struct{
			const char *vName;
			
		}b3var;
			
		struct{
			std::vector<B3Expr *> *vars;
			struct B3Expr *expr;
			
		}b3lambda;
		
	} data;
	
};

struct B3Expr *newIf(struct B3Expr *expr1, struct B3Expr *expr2, struct B3Expr *expr3);

struct B3Expr *newApp(int n, ...);

struct B3Expr *newApp(std::vector<B3Expr *> *exprs);

struct B3Expr *newVal(int n);

struct B3Expr *newVal(bool b);

struct B3Expr *newVal(struct B3Expr *expr1);

struct B3Expr *newPrim(const char *pType);

struct B3Expr *newVar(const char *vName);

struct B3Expr *newLambda(int n, B3Expr *expr1, ...);

int main(int argc, char**argv);

#endif
