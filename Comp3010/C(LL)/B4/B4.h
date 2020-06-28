#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#ifndef B4_H
#define B4_H

using namespace std;

enum ExprType { IF, APP, VAL, PRIM, VAR, LAMB, CLOS};
enum ValType { VALBOOL, VALNUM, VALPRIM, VALLAMB, VALCLOS};

struct B4Expr {
	enum ExprType type;
	
	union{
		struct {
			struct B4Expr *expr1;
			struct B4Expr *expr2;
			struct B4Expr *expr3;
			
		} b4if;
		
		struct {
			std::vector<B4Expr *> *exprs;
			
		} b4app;
		
		struct {
			enum ValType type;
			bool b;
			int n;
			struct B4Expr *prim;
			struct B4Expr *lambda;
			struct B4Expr *closure;
			
		} b4val;
		
		struct {
			const char *pType;
			
		} b4prim;
		
		struct{
			const char *vName;
			
		}b4var;
			
		struct{
			struct B4Expr *recName;
			std::vector<B4Expr *> *vars;
			struct B4Expr *expr;
			
		}b4lambda;
		
		struct{
			struct B4Expr *lambda;
			struct VarMap *env;
			
		}b4closure;
		
	} data;
	
};

enum KType {KRET, KIF, KAPP, KIFCEK, KAPPCEK};

struct B4Con {
	enum KType type;
	
	union{
		struct {
			
		} kret;
		
		struct {
			struct B4Expr *expr1;
			struct B4Expr *expr2;
			struct B4Con *k;
			struct VarMap *env;
			
		} kif;
		
		struct {
			std::vector<B4Expr *> *values;
			std::vector<B4Expr *> *exprs;
			struct B4Con *k;
			struct VarMap *env;
			
		} kapp;
		
	} data;
	
};

struct VarMap{
	std::vector<const char *> *keys;
	std::vector<B4Expr *> *values;
	
};

struct B4Expr *newIf(struct B4Expr *expr1, struct B4Expr *expr2, struct B4Expr *expr3);

struct B4Expr *newApp(int n, ...);

struct B4Expr *newApp(std::vector<B4Expr *> *exprs);

struct B4Expr *newVal(int n);

struct B4Expr *newVal(bool b);

struct B4Expr *newVal(struct B4Expr *expr1);

struct B4Expr *newPrim(const char *pType);

struct B4Expr *newVar(const char *vName);

struct B4Expr *newLambda(int n, struct B4Expr *recName, struct B4Expr *expr1, ...);

struct B4Expr *newClosure(struct B4Expr *lambda, struct VarMap *env);

struct B4Con *newKIf(struct B4Expr *expr1, struct B4Expr *expr2, struct B4Con *k);

struct B4Con *newKApp(std::vector<B4Expr *> *values, std::vector<B4Expr *> *exprs, struct B4Con *k);

struct B4Con *newKIf(struct VarMap *env, struct B4Expr *expr1, struct B4Expr *expr2, struct B4Con *k);

struct B4Con *newKApp(std::vector<B4Expr *> *values, struct VarMap *env, std::vector<B4Expr *> *exprs, struct B4Con *k);

struct VarMap *newVarMap();

struct B4Con *copyK(struct B4Con *k);

void plugVar(struct VarMap *varMap, struct B4Expr *var, struct B4Expr *val);

struct B4Expr *getVar(struct VarMap *varMap, B4Expr *var);

struct VarMap *copyVarMap(struct VarMap *vm);

int findIndex(std::vector<const char *> *v, const char *s);

int valEval(struct B4Expr *expr);

struct B4Expr *delta(struct B4Expr *e0, std::vector<B4Expr *> *values, int t);

struct B4Expr *cek2(struct B4Expr *expr, struct VarMap *env);

struct B4Expr *copyExpr(struct B4Expr *expr);

int main(int argc, char**argv);

#endif
