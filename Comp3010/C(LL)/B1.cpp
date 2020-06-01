#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream> 
#include <vector>

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
			std::vector<B1Expr> *exprs;
			
		} b1app;
		
		struct {
			bool b;
			int n;
			struct B1Expr *prim;
			
		} b1val;
		
		struct {
			const char *t;
			
		} b1prim;
		
	} data;
	
};

struct B1Expr *newIf(struct B1Expr *expr1, struct B1Expr *expr2, struct B1Expr *expr3){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = IF;
	
	expr->data.b1if.expr1 = expr1;
	expr->data.b1if.expr2 = expr2;
	expr->data.b1if.expr3 = expr3;
	
	return expr;
	
}

struct B1Expr *newApp(std::vector<B1Expr> *exprs){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->type = APP;
	
	expr->data.b1app.exprs = exprs;
	
	return expr;
	
}

struct B1Expr *newVal(int n){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->data.b1val.n = n;
	
	return expr;
	
}

struct B1Expr *newVal(bool b){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->data.b1val.b = b;
	
	return expr;
	
}

struct B1Expr *newVal(struct B1Expr *prim){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->data.b1val.prim = prim;
	
	return expr;
	
}

struct B1Expr *newPrim(const char *t){
	struct B1Expr *expr = (struct B1Expr *)malloc(sizeof(struct B1Expr));
	
	expr->data.b1prim.t = t;
	
	return expr;
	
}

int main( int argc, char** argv ) {

}
