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

struct B2Con *newKRet(){
	struct B2Con *con = (struct B2Con *)malloc(sizeof(struct B2Con));
	
	con->type = KRET;
	
	return con;
	
}

struct B2Con *newKIf(struct B2Expr *expr1, struct B2Expr *expr2, struct B2Con *k){
	struct B2Con *con = (struct B2Con *)malloc(sizeof(struct B2Con));
	
	con->type = KIF;
	
	con->data.kif.expr1 = expr1;
	con->data.kif.expr2 = expr2;
	con->data.kif.k = k;
	
	return con;
	
}

struct B2Con *newKApp(std::vector<B2Expr *> *values, std::vector<B2Expr *> *exprs, struct B2Con *k){
	struct B2Con *con = (struct B2Con *)malloc(sizeof(struct B2Con));
	
	con->type = KAPP;
	
	con->data.kapp.values = values;
	con->data.kapp.exprs = exprs;
	con->data.kapp.k = k;
	
	return con;
	
}

struct VarMap *newVarMap(){
	struct VarMap *vm = (struct VarMap *)malloc(sizeof(struct VarMap));
	
	vm->keys = new std::vector<const char *>;
	vm->values = new std::vector<struct B2Expr *>; 
	
}

struct FuncMap *newFuncMap(){
	struct FuncMap *fm = (struct FuncMap *)malloc(sizeof(struct FuncMap));
	
	fm->keys = new std::vector<const char *>;
	fm->values = new std::vector<struct B2Def *>; 
	
}

struct B2Expr *ck1(struct B2Expr *expr, struct VarMap *vm, struct FuncMap *fm){
	struct B2Expr *e = expr;
	
	struct B2Con *k = newKRet();
	
	while(true){
		switch(e->type){
			case IF:
				k = newKIf(e->data.b2if.expr2, e->data.b2if.expr3, copyK(k));
				e = e->data.b2if.expr1;
				break;
				
			case APP:
				{
					std::vector<B2Expr *> *values = new std::vector<B2Expr *>;
					
					std::vector<B2Expr *> *exprs = new std::vector<B2Expr *>;
					
					for(int i = 1; i < e->data.b2app.exprs->size(); i++){
						exprs->push_back(e->data.b2app.exprs->at(i));
						
					}
					
					k = newKApp(values, exprs, copyK(k));
					e = e->data.b2app.exprs->at(0);
				}
				break;
			
			case VAL:
				 switch(k->type){
				 	case KRET:
				 		return e;
				 		
				 	case KIF:
				 		if(e->data.b2val.b){
				 			e = k->data.kif.expr1;
				 			
						}else{
							e = k->data.kif.expr2;
						 	
						}
				 		
				 		k = k->data.kif.k;
				 		
				 		break;
				 	
				 	case KAPP:
				 		{	 
					 		if(k->data.kapp.exprs->size() == 0){
					 			
					 			switch(k->data.kapp.values->at(0)->type){
								 	case FUNC:
								 		break;
								 		
								 	default:
						 				e = delta(e, k->data.kapp.values, 0, vm, fm);
						 				k = k->data.kapp.k;
					 					break;
					 					
					 			}
					 			
							 }else{
								std::vector<B2Expr *> *exprs = new std::vector<B2Expr *>;
					
								for(int i = 1; i < k->data.kapp.exprs->size(); i++){
									exprs->push_back(k->data.kapp.exprs->at(i));
									
								}
								
								std::vector<B2Expr *> *values = new std::vector<B2Expr *>;
								
								values->push_back(e);
								
								for(int i = 0; i < k->data.kapp.values->size(); i++){
									values->push_back(k->data.kapp.values->at(i));
									
								}
							 	
							 	e = k->data.kapp.exprs->at(0);
							 	k = newKApp(values, exprs, k->data.kapp.k);
							 	
							}
							
				 		}
				 		
				 		break;
				 	
				 	default:
				 		break;
				 	
				 }
				 
			default:
				break;
			
		}
		
	}
	
}

void plugVar(struct VarMap *varMap, struct B2Expr *var, struct B2Expr *val){
	const char *vName = var->data.b2var.vName;
	
	int index = findIndex(varMap->keys, vName);
	
	if(index == -1){
		varMap->keys->push_back(vName);
		varMap->values->push_back(val);
		
	}else{
		varMap->values->at(index) = val;
		
	}
	
}

void plugFunc(struct FuncMap *funcMap, struct B2Expr *func, struct B2Def *def){
	const char *fName = var->data.b2func.fName;
	
	int index = findIndex(funcMap->keys, fName);
	
	if(index == -1){
		funcMap->keys->push_back(fName);
		funcMap->values->push_back(def);
		
	}else{
		funcMap->values->at(index) = def;
		
	}
	
}


struct B2Expr *delta(struct B2Expr *e0, std::vector<B2Expr *> *values, int t, struct VarMap *vm, struct FuncMap *fm){
		
		switch(t){
			case 0:
				{
				const char *pType = values->at(1)->data.b2val.prim->data.b2prim.pType;
				
				int val1 = values->at(0)->data.b2val.n;
				int val2 = e0->data.b2val.n;
		
				if(strcmp(pType, "+") == 0){
					return newVal(val1 + val2);
					
				}else if(strcmp(pType, "*") == 0){
					return newVal(val1 * val2);
					
				}else if(strcmp(pType, "/") == 0){
					return newVal(val1 / val2);
					
				}else if(strcmp(pType, "-") == 0){
					return newVal(val1 - val2);
					
				}else if(strcmp(pType, "<=") == 0){
					if(val1 <= val2){
						return newVal(true);
						
					}
					
					return newVal(false);
					
				}else if(strcmp(pType, "<") == 0){
					if(val1 < val2){
						return newVal(true);
						
					}
					
					return newVal(false);
					
				}else if(strcmp(pType, "=") == 0){
					if(val1 == val2){
						return newVal(true);
						
					}
					
					return newVal(false);
					
				}else if(strcmp(pType, ">") == 0){
					if(val1 > val2){
						return newVal(true);
						
					}
					
					return newVal(false);
					
				}else if(strcmp(pType, ">=") == 0){
					if(val1 >= val2){
						return newVal(true);
						
					}
					
					return newVal(false);
					
				}
				
				}
				
				break;
				
			case 1:
				{
					const char *fName = values->at(values->size()-1)->data.b2func.fName;
					struct B2Def *fDef = fm->values->at(findIndex(fm->keys, fName));
					
					struct B2Expr *fBody = fDef->expr;
					
					for(int i = 1; int i < values->size()-1; i++){
						plugVar(vm, fDef->vars->at(i), values->at(i));
												
					}
					
					plugVar(vm, fDef->vars->at(0), e0);
					
				}
				
				return fBody;
				
				break;
			
		}
			
		
		return 0;
	
}

struct B2Con *copyK(struct B2Con *k){
	struct B2Con *con = (struct B2Con *)malloc(sizeof(struct B2Con));
	
	con->type = k->type;
	
	switch(k->type){
		case KIF:
			con->data.kif.expr1 = k->data.kif.expr1;
			con->data.kif.expr2 = k->data.kif.expr2;
			con->data.kif.k = k->data.kif.k;
			break;
			
		case KAPP:
			con->data.kapp.exprs = k->data.kapp.exprs;
			con->data.kapp.values = k->data.kapp.values;
			con->data.kapp.k = k->data.kapp.k;
			break;
		
	}
	
	return con;	
	
}

int valEval(struct B2Expr *expr){
	if(!expr->data.b2val.isBool){
		return expr->data.b2val.n;
		
	}else if(expr->data.b2val.b){
		return 1;
		
	}
	
	return 0;
	
}

int findIndex(std::vector<const char *> *v, const char *s){
	for(int i = 0; i < v->size(); i++){
		if(strcmp(v->at(i), s) == 0){
			return i;
			
		}
		
	}
	
	return -1;
	
}
