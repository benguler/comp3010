#include <iostream>

#include "B2.h"

/* run this program using the console pauser or add your own getch, system("pause") or input loop */

int main(int argc, char** argv) {
	struct FuncMap *fm = newFuncMap();
	
	struct B2Def *func1 = newDef(0, newFunc("F1"), newVal(8));
	struct B2Def *func2 = newDef(1, newFunc("F2"), newApp(3, newVal(newPrim("+")), newVar("x"), newVar("x")), newVar("x"));
	struct B2Def *func3 = newDef(2, newFunc("F3"), newApp(3, newVal(newPrim("-")), newVar("x"), newVar("y")), newVar("x"), newVar("y"));
	struct B2Def *func4 = newDef(1, newFunc("F4"), newVar("b"), newVar("a"));
	struct B2Def *func5 = newDef(1, newFunc("F5"), newApp(2, newFunc("F4"), newVal(0)), newVar("b"));
	
	define(fm, func1);
	define(fm, func2);
	define(fm, func3);
	define(fm, func4);
	define(fm, func5);
	
	struct B2Expr *app1 = newApp(1, newFunc("F1"));
	struct B2Expr *app2 = newApp(2, newFunc("F2"), newVal(7));
	struct B2Expr *app3 = newApp(3, newFunc("F3"), newVal(4), newVal(2));
	struct B2Expr *app4 = newApp(2, newFunc("F5"), newVal(1));
	
	return valEval(cek0(app4, newVarMap(), fm));
	
}
