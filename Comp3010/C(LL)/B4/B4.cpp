#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#include "B4.h"

bool testing = false;

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

	expr->data.b4app.exprs = new std::vector<B4Expr *>;

	va_list args;

	va_start(args, n);

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

struct B4Expr *newLambda(int n, struct B4Expr *recName, struct B4Expr *expr1, ...){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));

	expr->type = LAMB;

	expr->data.b4lambda.expr = expr1;
	expr->data.b4lambda.recName = recName;

	va_list args;

	va_start(args, *expr1);
	
	expr->data.b4lambda.vars = new std::vector<B4Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b4lambda.vars->push_back(va_arg(args, struct B4Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}

struct B4Expr *newLambda(struct B4Expr *recName, struct B4Expr *expr1, std::vector<B4Expr *> *vars){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));

	expr->type = LAMB;

	expr->data.b4lambda.expr = expr1;
	expr->data.b4lambda.recName = recName;	
	expr->data.b4lambda.vars = vars;

	return expr;
	
}

struct B4Expr *newClosure(struct B4Expr *lambda, struct VarMap *env){
	struct B4Expr *expr = (struct B4Expr *)malloc(sizeof(struct B4Expr));
	
	expr->type = CLOS;
	
	expr->data.b4closure.lambda = lambda;
	expr->data.b4closure.env = env;
	
	return expr;
	
}

struct B4Expr *copyExpr(struct B4Expr *expr){
	switch(expr->type){
		case VAL:
			switch(expr->data.b4val.type){
				case VALCLOS:
					return newVal(newClosure(expr->data.b4val.closure->data.b4closure.lambda, expr->data.b4val.closure->data.b4closure.env));
					
				case VALLAMB:
					return newVal(newLambda(expr->data.b4val.lambda->data.b4lambda.recName, expr->data.b4val.lambda->data.b4lambda.expr, expr->data.b4val.lambda->data.b4lambda.vars));
					
				case VALPRIM:
					return newVal(expr->data.b4val.prim);
					
				case VALBOOL:
					return newVal(expr->data.b4val.b);
				case VALNUM:
					return newVal(expr->data.b4val.n);
				
			}
			
		case IF:
			return newIf(expr->data.b4if.expr1, expr->data.b4if.expr2, expr->data.b4if.expr3);
			
		case APP:
			return newApp(expr->data.b4app.exprs);
			
			
	}
	
	exit(-1);
	return newVal(false);
	
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

struct VarMap *newVarMap(){
	struct VarMap *vm = (struct VarMap *)malloc(sizeof(struct VarMap));
	
	vm->keys = new std::vector<const char *>;
	vm->values = new std::vector<struct B4Expr *>; 
	
	return vm;
	
}

void plugVar(struct VarMap *varMap, struct B4Expr *var, struct B4Expr *val){
	if(testing){cout<<"All Good 9.6.1"<<endl;}
	const char *vName = var->data.b4var.vName;
	if(testing){cout<<"All Good 9.6.2"<<endl;}
	int index = findIndex(varMap->keys, vName);
	if(testing){cout<<"All Good 9.6.3"<<endl;}
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
		cout<<vName<<endl;
		cout<<varMap->keys->size()<<endl;
		exit(-1);
		return newVal(false);
		
	}
	
	return varMap->values->at(index);
	
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
			if(expr->data.b4val.b){
				return 1;
					
			}
			
			return 0;
			
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
	if(testing){cout<<"All Good 11"<<endl;}
		switch(t){
			case 0:
				if(testing){cout<<"All Good 12"<<endl;}
				{
				const char *pType = values->at(1)->data.b4val.prim->data.b4prim.pType;
				
				int val1 = valEval(values->at(0));
				int val2 = valEval(e0);
		
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


struct B4Expr *cek2(struct B4Expr *expr, struct VarMap *env){
	struct B4Expr *c = expr;
	env = newVarMap();
	struct B4Con *k = newKRet();

	while(true){
		if(testing){cout<<"All Good XX"<<endl;}
		switch(c->type){
			case VAR:
				if(testing){cout<<"All Good 1"<<endl;}
				c = getVar(env, c);
				env = newVarMap();
				//k = k
				break;
			
			case IF:
				if(testing){cout<<"All Good 2"<<endl;}
				k = newKIf(env, c->data.b4if.expr2, c->data.b4if.expr3, k);
				c = c->data.b4if.expr1;
				//env = env
				break;
				
			case APP:
				{
					if(testing){cout<<"All Good 3"<<endl;}
					std::vector<B4Expr *> *values = new std::vector<B4Expr *>;

					std::vector<B4Expr *> *exprs = new std::vector<B4Expr *>;

					for(int i = 1; i < c->data.b4app.exprs->size(); i++){
						exprs->push_back(c->data.b4app.exprs->at(i));
						
					}
					
					k = newKApp(values, env, exprs, k);
					c = c->data.b4app.exprs->at(0);
					//env = env
				}
				
				break;
				
			case VAL:
				switch(c->data.b4val.type){
					case VALLAMB:
						if(testing){cout<<"All Good 4"<<endl;}
						{
							struct VarMap *newEnv = newVarMap();

							struct B4Expr *recName = c->data.b4val.lambda->data.b4lambda.recName;
							
							plugVar(newEnv, recName, newVal(newClosure(c, newEnv)));

							c = newVal(newClosure(c, newEnv));
							env = newVarMap();
							//k = k
							
							break;
						}
						
					default:
						switch(k->type){
							case KRET:
								if(testing){cout<<"All Good 5"<<endl;}
						 		return c;
						 		
							case KIF:
								if(testing){cout<<"All Good 7"<<endl;}
								if(c->data.b4val.b == false){
									c = k->data.kif.expr2;
									
								}else{
									c = k->data.kif.expr1;
									
								}
								
								env = k->data.kif.env;
								k = k->data.kif.k;
								
								break;
								
							case KAPP:
								if(k->data.kapp.exprs->size() != 0){
									if(testing){cout<<"All Good 8"<<endl;}
									std::vector<B4Expr *> *exprs = new std::vector<B4Expr *>;
						
									for(int i = 1; i < k->data.kapp.exprs->size(); i++){
										exprs->push_back(k->data.kapp.exprs->at(i));
										
									}
									
									std::vector<B4Expr *> *values = new std::vector<B4Expr *>;
									
									for(int i = 0; i < k->data.kapp.values->size(); i++){
										values->push_back(k->data.kapp.values->at(i));
										
									}
									
									values->push_back(copyExpr(c));
								 	
								 	c = k->data.kapp.exprs->at(0);
								 	env = k->data.kapp.env;
								 	k = newKApp(values, k->data.kapp.env, exprs, k->data.kapp.k);
									
								}else{
									std::vector<B4Expr *> *values = k->data.kapp.values;
									 			
									switch(values->at(0)->data.b4val.type){
										case VALCLOS:
											if(testing){cout<<"All Good 9"<<endl;}
											{
												struct B4Expr *lambda = values->at(0)->data.b4val.closure->data.b4closure.lambda->data.b4val.lambda;
											
												env = copyVarMap(values->at(0)->data.b4val.closure->data.b4closure.env);
											
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
									 			if(testing){cout<<"All Good 10"<<endl;}
										 		std::vector<B4Expr *> *revValues = new std::vector<B4Expr *>;
										 		
									 			for(int i = 0; i <  k->data.kapp.values->size(); i++){
									 				revValues->push_back(values->at(values->size()-1-i));
									 			
											 	}
								 				c = delta(c, revValues, 0);
								 				env = newVarMap();
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
