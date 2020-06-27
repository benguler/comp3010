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

struct B2Expr *newApp(std::vector<B2Expr *> *exprs){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));

	expr->type = APP;
	
	expr->data.b2app.exprs = exprs;
	
	return expr;
	
}

struct B2Expr *newVal(int n){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = VAL;
	
	expr->data.b2val.n = n;
	expr->data.b2val.type = VALNUM;
	
	return expr;
	
}

struct B2Expr *newVal(bool b){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = VAL;
	
	expr->data.b2val.b = b;
	expr->data.b2val.type = VALBOOL;
	
	return expr;
	
}

struct B2Expr *newVal(struct B2Expr *expr1){
	struct B2Expr *expr = (struct B2Expr *)malloc(sizeof(struct B2Expr));
	
	expr->type = VAL;
	
	switch(expr1->type){
		case PRIM:
			expr->data.b2val.prim = expr1;
			expr->data.b2val.type = VALPRIM;
			break;
		case FUNC:
			expr->data.b2val.func = expr1;
			expr->data.b2val.type = VALFUNC;
			break;
		default:
			break;
	}
	
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
	
	def->func = func->data.b2val.func;
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

struct B2Con *newKIf(struct VarMap *env, struct B2Expr *expr1, struct B2Expr *expr2, struct B2Con *k){
	struct B2Con *con = (struct B2Con *)malloc(sizeof(struct B2Con));
	
	con->type = KIF;
	
	con->data.kif.expr1 = expr1;
	con->data.kif.expr2 = expr2;
	con->data.kif.k = k;
	con->data.kif.env = env;
	
	return con;
	
}

struct B2Con *newKApp(std::vector<B2Expr *> *values, struct VarMap *env, std::vector<B2Expr *> *exprs, struct B2Con *k){
	struct B2Con *con = (struct B2Con *)malloc(sizeof(struct B2Con));
	
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
	vm->values = new std::vector<struct B2Expr *>; 
	
	return vm;
	
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

struct B2Expr *getVar(struct VarMap *varMap, B2Expr *var){
	const char *vName = var->data.b2var.vName;
	
	int index = findIndex(varMap->keys, vName);
	
	if (index == -1){
		cout<<"As it should be"<<endl;
		return newVal(false);
		
	}
	
	return varMap->values->at(index);
	
}

struct B2Def *getDef(struct FuncMap *funcMap, B2Expr *func){
	const char *fName = func->data.b2func.fName;
	
	int index = findIndex(funcMap->keys, fName);
	
	if (index == -1){
		return NULL;
		
	}
	
	return funcMap->values->at(index);
	
}

struct FuncMap *newFuncMap(){
	struct FuncMap *fm = (struct FuncMap *)malloc(sizeof(struct FuncMap));
	
	fm->keys = new std::vector<const char *>;
	fm->values = new std::vector<struct B2Def *>; 
	
	return fm;
	
}

void define(struct FuncMap *funcMap, struct B2Def *def){
	const char *fName = def->func->data.b2func.fName;
	
	int index = findIndex(funcMap->keys, fName);
	
	if(index == -1){
		funcMap->keys->push_back(fName);
		funcMap->values->push_back(def);
		
	}else{
		funcMap->values->at(index) = def;
		
	}
	
}

struct B2Expr *ck1(struct B2Expr *expr, struct FuncMap *fm){
	struct B2Expr *c = expr;
	
	struct B2Con *k = newKRet();
	
	while(true){
		switch(c->type){
			case IF:
				k = newKIf(c->data.b2if.expr2,  c->data.b2if.expr3, copyK(k));
				c = c->data.b2if.expr1;
				break;
				
			case APP:
				{
					std::vector<B2Expr *> *values = new std::vector<B2Expr *>;
					
					std::vector<B2Expr *> *exprs = new std::vector<B2Expr *>;
					for(int i = 1; i < c->data.b2app.exprs->size(); i++){
						exprs->push_back(c->data.b2app.exprs->at(i));
						
					}
					
					k = newKApp(values, exprs, copyK(k));
					c = c->data.b2app.exprs->at(0);
				}
				break;
			
			default:
				 switch(k->type){
				 	case KRET:
				 		return c;
				 		
				 	case KIF:
				 		if(c->data.b2val.b){
				 			c = k->data.kif.expr1;
				 			
						}else{
							c = k->data.kif.expr2;
						 	
						}
				 		
				 		k = k->data.kif.k;
				 		
				 		break;
				 	
				 	case KAPP:
				 		{	 
					 		if(k->data.kapp.exprs->size() == 0){
				 				if(k->data.kapp.values->size() != 0){
					 				switch(k->data.kapp.values->at(k->data.kapp.values->size()-1)->data.b2val.type){
									 	case VALFUNC:
									 		c = delta(c, k->data.kapp.values, 1, fm);
							 				k = k->data.kapp.k;
									 		break;
									 		
									 	case VALPRIM:
							 				c = delta(c, k->data.kapp.values, 0, fm);
							 				k = k->data.kapp.k;
						 					break;
						 					
						 				default:
						 					break;
						 					
						 			}
						 			
								}else{
									c = delta(c, k->data.kapp.values, 1, fm);
					 				k = k->data.kapp.k;
									
								}

							 }else{
								std::vector<B2Expr *> *exprs = new std::vector<B2Expr *>;
					
								for(int i = 1; i < k->data.kapp.exprs->size(); i++){
									exprs->push_back(k->data.kapp.exprs->at(i));
									
								}
								
								std::vector<B2Expr *> *values = new std::vector<B2Expr *>;
								
								values->push_back(c);
								
								for(int i = 0; i < k->data.kapp.values->size(); i++){
									values->push_back(k->data.kapp.values->at(i));
									
								}
							 	
							 	c = k->data.kapp.exprs->at(0);
							 	k = newKApp(values, exprs, k->data.kapp.k);
							 	
							}
							
				 		}
				 		
				 		break;
				 	
				 	default:
				 		break;
				 	
				 }
				 
				break;

		}
		
	}
	
}

struct B2Expr *delta(struct B2Expr *e0, std::vector<B2Expr *> *values, int t, struct FuncMap *fm){
		switch(t){
			case 0:
				{
				const char *pType = values->at(1)->data.b2val.prim->data.b2prim.pType;
				
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
					if(values->size() != 0){
						const char *fName = values->at(values->size()-1)->data.b2val.func->data.b2func.fName;
						struct B2Def *fDef = fm->values->at(findIndex(fm->keys, fName));
						
						std::vector<B2Expr *> *newValues = new std::vector<B2Expr *>; 
		
						newValues->push_back(e0);
						
						for(int i = 0; i < values->size()-1; i++){
							newValues->push_back(values->at(i));
			
						}
						
						struct B2Expr *fBody = substitute(fDef, newValues, newVarMap());
						return fBody;
						
					}else{
						const char *fName = e0->data.b2val.func->data.b2func.fName;
						struct B2Def *fDef = fm->values->at(findIndex(fm->keys, fName));
						
						return fDef->expr;
						
					}
					
				}
				
				break;
			
		}
			
		return 0;
	
}

struct B2Expr *substitute(struct B2Def *def, std::vector<B2Expr *> *values, struct VarMap *vm){
	for(int i = 0; i < values->size(); i++){
		plugVar(vm, def->vars->at(i), values->at(values->size()-1-i));
												
	}
	
	switch(def->expr->type){
		case APP:
			{
				std::vector<B2Expr *> *exprs =  new std::vector<B2Expr *>;
				
				for (int i = 0; i < def->expr->data.b2app.exprs->size(); i++){;
					switch(def->expr->data.b2app.exprs->at(i)->type){
						case VAR:
							exprs->push_back(getVar(vm, def->expr->data.b2app.exprs->at(i)));
							break;
							
						default:
							exprs->push_back(def->expr->data.b2app.exprs->at(i));
							break;
						
					}
					
				}	
				
				return newApp(exprs);
				
			}
			
		case IF:
			{
				struct B2Expr *expr1;
				struct B2Expr *expr2;
				struct B2Expr *expr3;
				
				switch(def->expr->data.b2if.expr1->type){
					case VAR:
						expr1 = getVar(vm, def->expr->data.b2if.expr1);
						break;
						
					default:
						expr1 = def->expr->data.b2if.expr1;
						break;
					
				}
				
				switch(def->expr->data.b2if.expr2->type){
					case VAR:
						expr2 = getVar(vm, def->expr->data.b2if.expr2);
						break;
						
					default:
						expr2 = def->expr->data.b2if.expr2;
						break;
					
				}
				
				switch(def->expr->data.b2if.expr3->type){
					case VAR:
						expr3 = getVar(vm, def->expr->data.b2if.expr3);
						break;
						
					default:
						expr3 = def->expr->data.b2if.expr2;
						break;
					
				}
				
				return newIf(expr1, expr2, expr3);
			}
			
		case VAL:
			return def->expr;
			
		case VAR:
			{	
				return getVar(vm, def->expr);
				
			}
			
	}
	
}

struct B2Expr *cek0(struct B2Expr *expr, struct VarMap *env, struct FuncMap *fm){
	struct B2Expr *c = expr;
	
	struct B2Con *k = newKRet();
	
	while(true){
		switch(c->type){
			case VAR:
				c = getVar(env, c);
				env = newVarMap();
				//k = k
				break;
			
			case IF:
				k = newKIf(copyVarMap(env), c->data.b2if.expr2, c->data.b2if.expr3, copyK(k));
				c = c->data.b2if.expr1;
				//env = env
				break;
				
			case APP:
				{
					std::vector<B2Expr *> *values = new std::vector<B2Expr *>;
					
					std::vector<B2Expr *> *exprs = new std::vector<B2Expr *>;
					for(int i = 1; i < c->data.b2app.exprs->size(); i++){
						exprs->push_back(c->data.b2app.exprs->at(i));
						
					}
					
					k = newKApp(values, copyVarMap(env), exprs, copyK(k));
					c = c->data.b2app.exprs->at(0);
					//env = env
				}
				
				break;
				
			case VAL:
				switch(k->type){
					case KRET:
				 		return c;
				 		
					case KIF:
						if(c->data.b2val.b == false){
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
							std::vector<B2Expr *> *exprs = new std::vector<B2Expr *>;
				
							for(int i = 1; i < k->data.kapp.exprs->size(); i++){
								exprs->push_back(k->data.kapp.exprs->at(i));
								
							}
							
							std::vector<B2Expr *> *values = new std::vector<B2Expr *>;
							
							for(int i = 0; i < k->data.kapp.values->size(); i++){
								values->push_back(k->data.kapp.values->at(i));
								
							}
							
							values->push_back(c);
						 	
						 	c = k->data.kapp.exprs->at(0);
						 	env = k->data.kapp.env;
						 	k = newKApp(values, k->data.kapp.env, exprs, k->data.kapp.k);
							
						}else{
							if(k->data.kapp.values->size() != 0){
								
								std::vector<B2Expr *> *values = k->data.kapp.values;
								 			
								
								switch(values->at(0)->data.b2val.type){
								 	case VALFUNC:
								 		{
									 		struct B2Def *def = getDef(fm, values->at(0)->data.b2val.func);
									 		
									 		struct VarMap *newVm = newVarMap();	//Without dynamic scope
									 		//struct VarMap *newVm = env;	//With dynamic scope
									 		
									 		for(int i = 1; i < values->size(); i++){
												plugVar(newVm, def->vars->at(i-1), values->at(i));
													
											}
											
											plugVar(newVm, def->vars->at(def->vars->size()-1), c);
									 		
									 		env = newVm;
									 		c = def->expr;
							 				k = k->data.kapp.k;
						 				}
								 		break;
								 		
								 	default:
								 		std::vector<B2Expr *> *revValues = new std::vector<B2Expr *>;
								 		
							 			for(int i = 0; i <  k->data.kapp.values->size(); i++){
							 				revValues->push_back(values->at(values->size()-1-i));
							 			
									 	}
						 				c = delta(c, revValues, 0, fm);
						 				k = k->data.kapp.k;
					 					break;
					 					
					 			}
								
							}else{
								struct B2Def *def = getDef(fm, c->data.b2val.func);
								c = def->expr;
			 					k = k->data.kapp.k;
			 					env = newVarMap();
							
							}
							
						}
						
						break;
						
					default:
						break;
					
				}

			
            break;

		}
		
	}
	
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

struct VarMap *copyVarMap(struct VarMap *vm){
	struct VarMap *newVm = newVarMap();
	
	for(int i = 0; i < vm->keys->size(); i++){
		newVm->keys->push_back(vm->keys->at(i));
		newVm->values->push_back(vm->values->at(i));
		
	}
	
	return newVm;
	
}

int valEval(struct B2Expr *expr){
	switch(expr->data.b2val.type){
		case VALNUM:
			return expr->data.b2val.n;
			break;	
		case VALBOOL:
			if(expr->data.b2val.b){
				return 1;
					
			}
			
			return 0;
			
			break;	
		case VALPRIM:
			return 0;
			break;	
		case VALFUNC:
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
