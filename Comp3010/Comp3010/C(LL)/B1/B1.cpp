#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream> 
#include <vector>
#include <stdarg.h>

#include "B1.h"

using namespace std;

struct B1Expr *newIf(struct B1Expr *expr1, struct B1Expr *expr2, struct B1Expr *expr3){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = IF;
	
	expr->data.b1if.expr1 = expr1;
	expr->data.b1if.expr2 = expr2;
	expr->data.b1if.expr3 = expr3;
	
	return expr;
	
}

struct B1Expr *newApp(int n, ...){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));

	expr->type = APP;

	va_list args;

	va_start(args, n);
	
	expr->data.b1app.exprs = new std::vector<B1Expr *>;

	for(int i = 0; i<n; i++){
		expr->data.b1app.exprs->push_back(va_arg(args, struct B1Expr *));
		
	}
	
	va_end(args);

	return expr;
	
}

struct B1Expr *newVal(int n){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = VAL;
	
	expr->data.b1val.n = n;
	expr->data.b1val.isBool = false;
	
	return expr;
	
}

struct B1Expr *newVal(bool b){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = VAL;
	
	expr->data.b1val.b = b;
	expr->data.b1val.isBool = true;
	
	return expr;
	
}

struct B1Expr *newVal(struct B1Expr *prim){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = VAL;
	
	expr->data.b1val.prim = prim;
	
	return expr;
	
}

struct B1Expr *newPrim(const char *pType){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = PRIM;
	
	expr->data.b1prim.pType = pType;
	
	return expr;
	
}

struct B1Con *newKRet(){
	struct B1Con *con = (struct B1Con *)malloc(sizeof(struct B1Con));
	
	con->type = KRET;
	
	return con;
	
}

struct B1Con *newKIf(struct B1Expr *expr1, struct B1Expr *expr2, struct B1Con *k){
	struct B1Con *con = (struct B1Con *)malloc(sizeof(struct B1Con));
	
	con->type = KIF;
	
	con->data.kif.expr1 = expr1;
	con->data.kif.expr2 = expr2;
	con->data.kif.k = k;
	
	return con;
	
}

struct B1Con *newKApp(std::vector<B1Expr *> *values, std::vector<B1Expr *> *exprs, struct B1Con *k){
	struct B1Con *con = (struct B1Con *)malloc(sizeof(struct B1Con));
	
	con->type = KAPP;
	
	con->data.kapp.values = values;
	con->data.kapp.exprs = exprs;
	con->data.kapp.k = k;
	
	return con;
	
}

struct B1Expr *ck0(struct B1Expr *expr){
	struct B1Expr *e = expr;
	
	struct B1Con *k = newKRet();
	
	while(true){
		switch(e->type){
			case IF:
				k = newKIf(e->data.b1if.expr2, e->data.b1if.expr3, copyK(k));
				e = e->data.b1if.expr1;
				break;
				
			case APP:
				{
					std::vector<B1Expr *> *values = new std::vector<B1Expr *>;
					
					std::vector<B1Expr *> *exprs = new std::vector<B1Expr *>;
					
					for(int i = 1; i < e->data.b1app.exprs->size(); i++){
						exprs->push_back(e->data.b1app.exprs->at(i));
						
					}
					
					k = newKApp(values, exprs, copyK(k));
					e = e->data.b1app.exprs->at(0);
				}
				break;
			
			case VAL:
				 switch(k->type){
				 	case KRET:
				 		return e;
				 		
				 	case KIF:
				 		if(e->data.b1val.b){
				 			e = k->data.kif.expr1;
				 			
						}else{
							e = k->data.kif.expr2;
						 	
						}
				 		
				 		k = k->data.kif.k;
				 		
				 		break;
				 	
				 	case KAPP:
				 		{	 
					 		if(k->data.kapp.exprs->size() == 0){
					 			e = delta(e, k->data.kapp.values);
					 			k = k->data.kapp.k;
					 			
							 }else{
								std::vector<B1Expr *> *exprs = new std::vector<B1Expr *>;
					
								for(int i = 1; i < k->data.kapp.exprs->size(); i++){
									exprs->push_back(k->data.kapp.exprs->at(i));
									
								}
								
								std::vector<B1Expr *> *values = new std::vector<B1Expr *>;
								
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

struct B1Expr *delta(struct B1Expr *e, std::vector<B1Expr *> *values){
		const char *pType = values->at(1)->data.b1val.prim->data.b1prim.pType;
		
		int val1 = values->at(0)->data.b1val.n;
		int val2 = e->data.b1val.n;

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
		
		return 0;
	
}

struct B1Con *copyK(struct B1Con *k){
	struct B1Con *con = (struct B1Con *)malloc(sizeof(struct B1Con));
	
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

int valEval(struct B1Expr *expr){
	if(!expr->data.b1val.isBool){
		return expr->data.b1val.n;
		
	}else if(expr->data.b1val.b){
		return 1;
		
	}
	
	return 0;
	
}
