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
			std::vector<B1Expr *> *exprs;
			
		} b1app;
		
		struct {
			bool isBool;
			bool b;
			int n;
			struct B1Expr *prim;
			
		} b1val;
		
		struct {
			const char *pType;
			
		} b1prim;
		
	} data;
	
};

enum KType {KRET, KIF, KAPP};

struct B1Con {
	enum KType type;
	
	union{
		struct {
			
		} kret;
		
		struct {
			struct B1Expr *expr1;
			struct B1Expr *expr2;
			struct B1Con *k;
			
		} kif;
		
		struct {
			std::vector<B1Expr *> *values;
			std::vector<B1Expr *> *exprs;
			struct B1Con *k;
			
		} kapp;
		
	} data;
	
};

struct B1Expr *newIf(struct B1Expr *expr1, struct B1Expr *expr2, struct B1Expr *expr3);

struct B1Expr *newApp(int n, ...);

struct B1Expr *newVal(int n);

struct B1Expr *newVal(bool b);

struct B1Expr *newVal(struct B1Expr *prim);

struct B1Expr *newPrim(const char *pType);

int interp(struct B1Expr *expr);

struct B1Con *newKRet();

struct B1Con *newKIf(struct B1Expr *expr1, struct B1Expr *expr2, struct B1Con *k);

struct B1Con *newKApp(std::vector<B1Expr *> *values, std::vector<B1Expr *> *exprs, struct B1Con *k);

int main(int argc, char**argv);

	
#endif

