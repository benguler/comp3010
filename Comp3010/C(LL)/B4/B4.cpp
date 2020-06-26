#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#include "B4.h"

struct B4Expr *newIf(struct B4Expr *expr1, struct B4Expr *expr2, struct B4Expr *expr3){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));
	
	expr->type = IF;
	
	expr->data.b4if.expr1 = expr1;
	expr->data.b4if.expr2 = expr2;
	expr->data.b4if.expr3 = expr3;
	
	return expr;
	
}

struct B4Expr *newApp(int n, ...){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));

	expr->type = APP;

	std::vector<B4Expr *> *exprs;

	va_list args;

	va_start(args, n);
	
	expr->data.b4app.exprs = new std::vector<B4Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b4app.exprs->push_back(va_arg(args, struct B4Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}

struct B4Expr *newApp(std::vector<B4Expr *> *exprs){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));

	expr->type = APP;
	
	expr->data.b4app.exprs = exprs;
	
	return expr;
	
}

struct B4Expr *newVal(int n){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));
	
	expr->type = VAL;
	
	expr->data.b4val.n = n;
	expr->data.b4val.type = VALNUM;
	
	return expr;
	
}

struct B4Expr *newVal(bool b){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));
	
	expr->type = VAL;
	
	expr->data.b4val.b = b;
	expr->data.b4val.type = VALBOOL;
	
	return expr;
	
}

struct B4Expr *newVal(struct B4Expr *expr1){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));
	
	expr->type = VAL;
	
	switch(expr1->type){
		case PRIM:
			expr->data.b4val.prim = expr1;
			expr->data.b4val.type = VALPRIM;
			break;
			
		case LAMB:
			expr->data.b4val.lambda = expr1;
			expr->data.b4val.type = VALLAMB;
			break;
			
		case CLOS:
			expr->data.b4val.closure = expr1;
			expr->data.b4val.type = VALCLOS;
			break;
	
		default:
			break;
			
	}
	
	return expr;
	
}

struct B4Expr *newPrim(const char *pType){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));
	
	expr->type = PRIM;
	
	expr->data.b4prim.pType = pType;
	
	return expr;
	
}

struct B4Expr *newVar(const char *vName){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));
	
	expr->type = VAR;
	
	expr->data.b4var.vName = vName;
	
	return expr;
	
}

struct B4Expr *newLambda(int n, struct B4Expr *var, struct B4Expr *expr1, ...){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));

	expr->type = LAMB;

	expr->data.b4lambda.expr = expr1;
	expr->data.b4lambda.var = var;

	std::vector<B4Expr *> *vars;

	va_list args;

	va_start(args, *expr1);
	
	expr->data.b4lambda.vars = new std::vector<B4Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b4lambda.vars->push_back(va_arg(args, struct B4Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}

struct B4Expr *newClosure(struct B4Expr *lambda, struct VarMap *env){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));
	
	expr->type = CLOS;
	
	expr->data.b4closure.lambda = lambda;
	expr->data.b4closure.env = env;
	
	return expr;
	
}

struct B4Con *newKRet(){
	struct B4Con *con = (struct B4Con *)malloc(sizeof(struct B4Con));
	
	con->type = KRET;
	
	return con;
	
}

struct B4Con *newKIf(struct B4Expr *expr1, struct B4Expr *expr2, struct B4Con *k){
	struct B4Con *con = (struct B4Con *)malloc(sizeof(struct B4Con));
	
	con->type = KIF;
	
	con->data.kif.expr1 = expr1;
	con->data.kif.expr2 = expr2;
	con->data.kif.k = k;
	
	return con;
	
}

struct B4Con *newKApp(std::vector<B4Expr *> *values, std::vector<B4Expr *> *exprs, struct B4Con *k){
	struct B4Con *con = (struct B4Con *)malloc(sizeof(struct B4Con));
	
	con->type = KAPP;
	
	con->data.kapp.values = values;
	con->data.kapp.exprs = exprs;
	con->data.kapp.k = k;
	
	return con;
	
}

struct B4Con *newKIf(struct VarMap *env, struct B4Expr *expr1, struct B4Expr *expr2, struct B4Con *k){
	struct B4Con *con = (struct B4Con *)malloc(sizeof(struct B4Con));
	
	con->type = KIF;
	
	con->data.kif.expr1 = expr1;
	con->data.kif.expr2 = expr2;
	con->data.kif.k = k;
	con->data.kif.env = env;
	
	return con;
	
}

struct B4Con *newKApp(std::vector<B4Expr *> *values, struct VarMap *env, std::vector<B4Expr *> *exprs, struct B4Con *k){
	struct B4Con *con = (struct B4Con *)malloc(sizeof(struct B4Con));
	
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
	vm->values = new std::vector<struct B4Expr *>; 
	
	return vm;
	
}

void plugVar(struct VarMap *varMap, struct B4Expr *var, struct B4Expr *val){
	const char *vName = var->data.b4var.vName;
	
	int index = findIndex(varMap->keys, vName);
	
	if(index == -1){
		varMap->keys->push_back(vName);
		varMap->values->push_back(val);
		
	}else{
		varMap->values->at(index) = val;
		
	}
	
}

struct B4Expr *getVar(struct VarMap *varMap, B4Expr *var){
	const char *vName = var->data.b4var.vName;
	
	int index = findIndex(varMap->keys, vName);
	
	if (index == -1){
		cout<<"As it should be"<<endl;
		return newVal(false);
		
	}
	
	return varMap->values->at(index);
	
}

struct B4Con *copyK(struct B4Con *k){
	struct B4Con *con = (struct B4Con *)malloc(sizeof(struct B4Con));
	
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

int valEval(struct B4Expr *expr){
	switch(expr->data.b4val.type){
		case VALNUM:
			return expr->data.b4val.n;
			break;	
		case VALBOOL:
			return expr->data.b4val.b;
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

struct B4Expr *delta(struct B4Expr *e0, std::vector<B4Expr *> *values, int t){
	cout<<"All good "<<"13"<<endl;
		switch(t){
			case 0:
				cout<<"All good "<<"14"<<endl;
				//cout<<"AAAA "<<values->at(1)->data.b4val.prim->data.b4prim.pType<<" AAAA"<<endl;
				{
				const char *pType = values->at(1)->data.b4val.prim->data.b4prim.pType;
				
				int val1 = values->at(0)->data.b4val.n;
				int val2 = e0->data.b4val.n;
		
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
				
			default:
				break;
			
		}
			
		return 0;
	
}


struct B4Expr *cek1(struct B4Expr *expr, struct VarMap *env){
	struct B4Expr *c = expr;
	
	struct B4Con *k = newKRet();

	while(true){
		switch(c->type){
			case VAR:
				c = getVar(env, c);
				env = newVarMap();
				//k = k
				break;
			
			case IF:
				k = newKIf(copyVarMap(env), c->data.b4if.expr2, c->data.b4if.expr3, copyK(k));
				c = c->data.b4if.expr1;
				//env = env
				break;
				
			case APP:
				{
					std::vector<B4Expr *> *values = new std::vector<B4Expr *>;
					
					std::vector<B4Expr *> *exprs = new std::vector<B4Expr *>;
					for(int i = 1; i < c->data.b4app.exprs->size(); i++){
						exprs->push_back(c->data.b4app.exprs->at(i));
						
					}
					
					k = newKApp(values, copyVarMap(env), exprs, copyK(k));
					c = c->data.b4app.exprs->at(0);
					//env = env
				}
				
				break;
				
			case VAL:
				switch(c->data.b4val.type){
					case VALLAMB:
						c = newVal(newClosure(c, env));
						env = newVarMap();
						//k = k
						
						break;
					
					default:
						switch(k->type){
							case KRET:
						 		return c;
						 		
							case KIF:
								if(c->data.b4val.b == false){
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
									std::vector<B4Expr *> *exprs = new std::vector<B4Expr *>;
						
									for(int i = 1; i < k->data.kapp.exprs->size(); i++){
										exprs->push_back(k->data.kapp.exprs->at(i));
										
									}
									
									std::vector<B4Expr *> *values = new std::vector<B4Expr *>;
									
									for(int i = 0; i < k->data.kapp.values->size(); i++){
										values->push_back(k->data.kapp.values->at(i));
										
									}
									
									values->push_back(c);
								 	
								 	c = k->data.kapp.exprs->at(0);
								 	env = k->data.kapp.env;
								 	k = newKApp(values, k->data.kapp.env, exprs, k->data.kapp.k);
									
								}else{
									std::vector<B4Expr *> *values = k->data.kapp.values;
									 			
									switch(values->at(0)->data.b4val.type){
										case VALCLOS:
											{
												struct B4Expr *lambda =  values->at(0)->data.b4val.closure->data.b4closure.lambda->data.b4val.lambda;
	
												env = values->at(0)->data.b4val.closure->data.b4closure.env;

												std::vector<B4Expr *> *vars = lambda->data.b4lambda.vars;

												for(int i = 1; i < values->size(); i++){
													plugVar(env, vars->at(i-1), values->at(i));
														
												}

												plugVar(env, vars->at(vars->size()-1), c);

												k = k->data.kapp.k;

												c = lambda->data.b4lambda.expr;

											}
											
											break;
										
									 	default:
									 		{
										 		std::vector<B4Expr *> *revValues = new std::vector<B4Expr *>;
										 		
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

			default:
				exit(-1);

		}
		
	}
	
}

