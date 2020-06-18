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

enum ExprType { IF, APP, VAL, PRIM, VAR, FUNC};

struct B2Def{
	struct B2Expr *func;
	std::vector<B2Expr *> *vars;
	struct B2Expr *expr;
	
};

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
		
	} data;
	
};

enum KType {KRET, KIF, KAPP, KIFCEK, KAPPCEK};

struct B2Con {
	enum KType type;
	
	union{
		struct {
			
		} kret;
		
		struct {
			struct B2Expr *expr1;
			struct B2Expr *expr2;
			struct B2Con *k;
			struct VarMap *env;
			
		} kif;
		
		struct {
			std::vector<B2Expr *> *values;
			std::vector<B2Expr *> *exprs;
			struct B2Con *k;
			struct VarMap *env;
			
		} kapp;
		
	} data;
	
};

struct VarMap{
	std::vector<const char *> *keys;
	std::vector<B2Expr *> *values;
};

struct FuncMap{
	std::vector<const char *> *keys;
	std::vector<B2Def *> *values;
};

struct B2Expr *newIf(struct B2Expr *expr1, struct B2Expr *expr2, struct B2Expr *expr3);

struct B2Expr *newApp(int n, ...);

struct B2Expr *newApp(std::vector<B2Expr *> *exprs);

struct B2Expr *newVal(int n);

struct B2Expr *newVal(bool b);

struct B2Expr *newVal(struct B2Expr *prim);

struct B2Expr *newPrim(const char *pType);

struct B2Expr *newVar(const char *vName);

struct B2Expr *newFunc(const char *fName);

struct B2Def *newDef(int n, struct B2Expr *func, struct B2Expr *expr, ...);

struct B2Con *newKIf(struct B2Expr *expr1, struct B2Expr *expr2, struct B2Con *k);

struct B2Con *newKApp(std::vector<B2Expr *> *values, std::vector<B2Expr *> *exprs, struct B2Con *k);

struct B2Con *newKIf(struct VarMap *env, struct B2Expr *expr1, struct B2Expr *expr2, struct B2Con *k);

struct B2Con *newKApp(std::vector<B2Expr *> *values, struct VarMap *env, std::vector<B2Expr *> *exprs, struct B2Con *k);

struct VarMap *newVarMap();

struct FuncMap *newFuncMap();

struct B2Expr *ck1(struct B2Expr *expr, struct FuncMap *fm);

struct B2Expr *cek1(struct B2Expr *expr, struct VarMap *vm, struct FuncMap *fm);

struct B2Expr *delta(struct B2Expr *e0, std::vector<B2Expr *> *values, int t, struct FuncMap *fm);

struct B2Expr *substitute(struct B2Def *def, std::vector<B2Expr *> *values, struct VarMap *vm);

void plugVar(struct VarMap *varMap, struct B2Expr *var, struct B2Expr *val);

struct B2Expr *getVar(struct VarMap *varMap, B2Expr *var);

struct B2Def *getDef(struct FuncMap *funcMap, B2Expr *func);

void define(struct FuncMap *funcMap, struct B2Def *def);

struct B2Con *copyK(struct B2Con *k);

struct VarMap *copyVarMap(struct VarMap *vm);

int valEval(struct B2Expr *expr);

int findIndex(std::vector<const char *> *v, const char *s);

int main(int argc, char**argv);

#endif
