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

enum ExprType { IF, APP, VAL, PRIM, VAR, LAMB, CLOS};
enum ValType { VALBOOL, VALNUM, VALPRIM, VALLAMB, VALCLOS};

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
			struct B3Expr *lambda;
			struct B3Expr *closure;
			
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
		
		struct{
			struct B3Expr *lambda;
			struct VarMap *env;
			
		}b3closure;
		
	} data;
	
};

enum KType {KRET, KIF, KAPP, KIFCEK, KAPPCEK};

struct B3Con {
	enum KType type;
	
	union{
		struct {
			
		} kret;
		
		struct {
			struct B3Expr *expr1;
			struct B3Expr *expr2;
			struct B3Con *k;
			struct VarMap *env;
			
		} kif;
		
		struct {
			std::vector<B3Expr *> *values;
			std::vector<B3Expr *> *exprs;
			struct B3Con *k;
			struct VarMap *env;
			
		} kapp;
		
	} data;
	
};

struct VarMap{
	std::vector<const char *> *keys;
	std::vector<B3Expr *> *values;
};

struct B3Expr *newIf(struct B3Expr *expr1, struct B3Expr *expr2, struct B3Expr *expr3);

struct B3Expr *newApp(int n, ...);

struct B3Expr *newApp(std::vector<B3Expr *> *exprs);

struct B3Expr *newVal(int n);

struct B3Expr *newVal(bool b);

struct B3Expr *newVal(struct B3Expr *expr1);

struct B3Expr *newPrim(const char *pType);

struct B3Expr *newVar(const char *vName);

struct B3Expr *newLambda(int n, struct B3Expr *expr1, ...);

struct B3Expr *newClosure(struct B3Expr *lambda, struct VarMap *env);

struct B3Con *newKIf(struct B3Expr *expr1, struct B3Expr *expr2, struct B3Con *k);

struct B3Con *newKApp(std::vector<B3Expr *> *values, std::vector<B3Expr *> *exprs, struct B3Con *k);

struct B3Con *newKIf(struct VarMap *env, struct B3Expr *expr1, struct B3Expr *expr2, struct B3Con *k);

struct B3Con *newKApp(std::vector<B3Expr *> *values, struct VarMap *env, std::vector<B3Expr *> *exprs, struct B3Con *k);

struct VarMap *newVarMap();

struct B3Con *copyK(struct B3Con *k);

void plugVar(struct VarMap *varMap, struct B3Expr *var, struct B3Expr *val);

struct B3Expr *getVar(struct VarMap *varMap, B3Expr *var);

struct VarMap *copyVarMap(struct VarMap *vm);

int findIndex(std::vector<const char *> *v, const char *s);

int valEval(struct B3Expr *expr);

struct B3Expr *delta(struct B3Expr *e0, std::vector<B3Expr *> *values, int t);

struct B3Expr *cek1(struct B3Expr *expr, struct VarMap *env);

int main(int argc, char**argv);

#endif
