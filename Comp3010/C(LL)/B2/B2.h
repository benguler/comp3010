#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#ifndef B1_H
#define B1_H

using namespace std;

enum ExprType { IF, APP, VAL, PRIM, VAR, FUNC, DEF};

struct B2Expr {
	enum ExprType type;
	
	union{
		struct {
			struct B2Expr *expr1;
			struct B2Expr *expr2;
			struct B2Expr *expr3;
			
		} b2if;
		
		struct {
			std::vector<B2Expr *> *exprs;
			
		} b2app;
		
		struct {
			bool isBool;
			bool b;
			int n;
			struct B2Expr *prim;
			struct B2Expr *func;
			
		} b2val;
		
		struct {
			const char *pType;
			
		} b2prim;
		
		struct{
			const char *vName;
			
		}b2var;
			
		struct{
			const char *fName;
			
		}b2func;
			
		struct{
			struct B2Expr *func;
			std::vector<B2Expr *> *vars;
			struct B2Expr *expr;
			
		}b2def;
		
	} data;
	
};

struct B2Expr *newIf(struct B2Expr *expr1, struct B2Expr *expr2, struct B2Expr *expr3);

struct B2Expr *newApp(int n, ...);

struct B2Expr *newVal(int n);

struct B2Expr *newVal(bool b);

struct B2Expr *newVal(struct B2Expr *prim);

struct B2Expr *newPrim(const char *pType);

struct B2Expr *newVar(const char *vName);

struct B2Expr *newFunc(const char *fName);

struct B2Expr *newDef(struct B2Expr *func, struct B2Expr *expr, ...);

#endif
