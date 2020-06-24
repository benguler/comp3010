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
			expr->data.b3val.prim = expr1;
			expr->data.b3val.type = VALPRIM;
			break;
			
		case LAMB:
			expr->data.b3val.lamb = expr1;
			expr->data.b3val.type = VALPRIM;
			break;
			
		case CLOS:
			expr->data.b3val.lamb = expr1;
			expr->data.b3val.type = VALCLOS;
			break;
	
		default:
			break;
			
	}
	
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

struct B3Expr *newLambda(int n, struct B3Expr *expr1, ...){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));

	expr->type = LAMB;

	expr->data.b3lambda.expr = expr1;

	std::vector<B3Expr *> *vars;

	va_list args;

	va_start(args, *expr1);
	
	expr->data.b3lambda.vars = new std::vector<B3Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b3lambda.vars->push_back(va_arg(args, struct B3Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}

struct B3Expr *newClosure(struct B3Expr *lambda, struct VarMap *env){
	struct B3Expr *expr = (struct B3Expr *)malloc(sizeof(struct B3Expr));
	
	expr->type = CLOS;
	
	expr->data.b3closure.lambda = lambda;
	expr->data.b3closure.env = env;
	
	return expr;
	
}

struct B3Con *newKRet(){
	struct B3Con *con = (struct B3Con *)malloc(sizeof(struct B3Con));
	
	con->type = KRET;
	
	return con;
	
}

struct B3Con *newKIf(struct B3Expr *expr1, struct B3Expr *expr2, struct B3Con *k){
	struct B3Con *con = (struct B3Con *)malloc(sizeof(struct B3Con));
	
	con->type = KIF;
	
	con->data.kif.expr1 = expr1;
	con->data.kif.expr2 = expr2;
	con->data.kif.k = k;
	
	return con;
	
}

struct B3Con *newKApp(std::vector<B3Expr *> *values, std::vector<B3Expr *> *exprs, struct B3Con *k){
	struct B3Con *con = (struct B3Con *)malloc(sizeof(struct B3Con));
	
	con->type = KAPP;
	
	con->data.kapp.values = values;
	con->data.kapp.exprs = exprs;
	con->data.kapp.k = k;
	
	return con;
	
}

struct B3Con *newKIf(struct VarMap *env, struct B3Expr *expr1, struct B3Expr *expr2, struct B3Con *k){
	struct B3Con *con = (struct B3Con *)malloc(sizeof(struct B3Con));
	
	con->type = KIF;
	
	con->data.kif.expr1 = expr1;
	con->data.kif.expr2 = expr2;
	con->data.kif.k = k;
	con->data.kif.env = env;
	
	return con;
	
}

struct B3Con *newKApp(std::vector<B3Expr *> *values, struct VarMap *env, std::vector<B3Expr *> *exprs, struct B3Con *k){
	struct B3Con *con = (struct B3Con *)malloc(sizeof(struct B3Con));
	
	con->type = KAPP;
	
	con->data.kapp.values = values;
	con->data.kapp.exprs = exprs;
	con->data.kapp.k = k;
	con->data.kapp.env = env;
	
	return con;
	
}

struct VarMap *newVarMap(){
	struct VarMap *vm = (struct VarMap *)malloc(sizeof(struct VarMap));
	
	vm->keys = new std::vector<const char *>;
	vm->values = new std::vector<struct B3Expr *>; 
	
	return vm;
	
}

void plugVar(struct VarMap *varMap, struct B3Expr *var, struct B3Expr *val){
	const char *vName = var->data.b3var.vName;
	
	int index = findIndex(varMap->keys, vName);
	
	if(index == -1){
		varMap->keys->push_back(vName);
		varMap->values->push_back(val);
		
	}else{
		varMap->values->at(index) = val;
		
	}
	
}

struct B3Expr *getVar(struct VarMap *varMap, B3Expr *var){
	const char *vName = var->data.b3var.vName;
	
	int index = findIndex(varMap->keys, vName);
	
	if (index == -1){
		cout<<"As it should be"<<endl;
		return newVal(false);
		
	}
	
	return varMap->values->at(index);
	
}

struct B3Con *copyK(struct B3Con *k){
	struct B3Con *con = (struct B3Con *)malloc(sizeof(struct B3Con));
	
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

struct VarMap *copyVarMap(struct VarMap *vm){
	struct VarMap *newVm = newVarMap();
	
	for(int i = 0; i < vm->keys->size(); i++){
		newVm->keys->push_back(vm->keys->at(i));
		newVm->values->push_back(vm->values->at(i));
		
	}
	
	return newVm;
	
}

int valEval(struct B3Expr *expr){
	switch(expr->data.b3val.type){
		case VALNUM:
			return expr->data.b3val.n;
			break;	
		case VALBOOL:
			return expr->data.b3val.b;
			break;	
		default:
			return 0;
			break;	
			
	}
	
}

int findIndex(std::vector<const char *> *v, const char *s){
	for(int i = 0; i < v->size(); i++){
		if(strcmp(v->at(i), s) == 0){
			return i;
			
		}
		
	}
	
	return -1;
	
}

struct B3Expr *delta(struct B3Expr *e0, std::vector<B3Expr *> *values, int t){
		switch(t){
			case 0:
				{
				const char *pType = values->at(1)->data.b3val.prim->data.b3prim.pType;
				
				int val1 = values->at(0)->data.b3val.n;
				int val2 = e0->data.b3val.n;
		
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
					
					
				}
				
				break;
			
		}
			
		return 0;
	
}


struct B3Expr *cek1(struct B3Expr *expr, struct VarMap *env){
	struct B3Expr *c = expr;
	
	struct B3Con *k = newKRet();
	
	while(true){
		switch(c->type){
			case VAR:
				c = getVar(env, c);
				env = newVarMap();
				//k = k
				break;
			
			case IF:
				k = newKIf(copyVarMap(env), c->data.b3if.expr2, c->data.b3if.expr3, copyK(k));
				c = c->data.b3if.expr1;
				//env = env
				break;
				
			case APP:
				{
					std::vector<B3Expr *> *values = new std::vector<B3Expr *>;
					
					std::vector<B3Expr *> *exprs = new std::vector<B3Expr *>;
					for(int i = 1; i < c->data.b3app.exprs->size(); i++){
						exprs->push_back(c->data.b3app.exprs->at(i));
						
					}
					
					k = newKApp(values, copyVarMap(env), exprs, copyK(k));
					c = c->data.b3app.exprs->at(0);
					//env = env
				}
				
				break;
				
			case VAL:
				switch(c->data.b3val.type){
					case VALLAMB:
						c = newVal(newClosure(c, env));
						env = newVarMap();
						//k = k
					
					default:
						switch(k->type){
							case KRET:
						 		return c;
						 		
							case KIF:
								if(c->data.b3val.b == false){
									env = k->data.kif.env;
									c = k->data.kif.expr2;
									k = k->data.kif.k;
									
								}else{
									env = k->data.kif.env;
									c = k->data.kif.expr1;
									k = k->data.kif.k;
									
								}
								
								break;
								
							case KAPP:
								if(k->data.kapp.exprs->size() != 0){
									std::vector<B3Expr *> *exprs = new std::vector<B3Expr *>;
						
									for(int i = 1; i < k->data.kapp.exprs->size(); i++){
										exprs->push_back(k->data.kapp.exprs->at(i));
										
									}
									
									std::vector<B3Expr *> *values = new std::vector<B3Expr *>;
									
									for(int i = 0; i < k->data.kapp.values->size(); i++){
										values->push_back(k->data.kapp.values->at(i));
										
									}
									
									values->push_back(c);
								 	
								 	c = k->data.kapp.exprs->at(0);
								 	env = k->data.kapp.env;
								 	k = newKApp(values, k->data.kapp.env, exprs, k->data.kapp.k);
									
								}else{
									std::vector<B3Expr *> *values = k->data.kapp.values;
									 			
									
									switch(values->at(0)->data.b3val.type){
										case VALCLOS:
											{
												struct B3Expr *lambda =  values->at(0)->data.b3val.closure->data.b3closure.lambda;
												
												c = lambda->data.b3lambda.expr;
												
												env = c->data.b3val.closure->data.b3closure.env;
												
												std::vector<B3Expr *> *vars = lambda->data.b3lambda.vars;
												
												for(int i = 1; i < values->size(); i++){
													plugVar(env, vars->at(i-1), values->at(i));
														
												}
												
												plugVar(env, vars->at(vars->size()-1), c);
												
												k = k->data.kapp.k;
											}
											
											break;
										
									 	default:
									 		{
										 		std::vector<B3Expr *> *revValues = new std::vector<B3Expr *>;
										 		
									 			for(int i = 0; i <  k->data.kapp.values->size(); i++){
									 				revValues->push_back(values->at(values->size()-1-i));
									 			
											 	}
								 				c = delta(c, revValues, 0);
								 				k = k->data.kapp.k;
							 					
							 				}
							 				
							 				break;
						 					
						 			}
									
								}
								
								break;
							
						default:
							break;
						
					}
				}
			
            	break;

		}
		
	}
	
}

