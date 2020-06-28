#include <iostream>
#include "B4.h"

int main( int argc, char** argv ) {
	cout<<"expr1 == "<<valEval(cek2(newApp(2, newVal(newLambda(1, newVar("until0"), newIf(newApp(3, newVal(newPrim("<=")), newVar("x"), newVal(0)), newVal(0), newApp(2, newVar("until0"), newApp(3, newVal(newPrim("-")), newVar("x"), newVal(1)))), newVar("x"))), newVal(12)), newVarMap()))<<endl;
	cout<<"expr2 == "<<valEval(cek2(newApp(3, newVal(newLambda(2, newVar("rec"), newApp(3, newVal(newPrim("+")), newVar("x"), newVar("y")), newVar("x"), newVar("y"))), newVal(5), newVal(3)), newVarMap()))<<endl;
	cout<<"expr3 == "<<valEval(cek2(newApp(4, newVal(newLambda(3, newVar("nat-unfold"), newIf(newApp(3, newVal(newPrim("=")), newVar("n"), newVal(0)), newVar("z"), newApp(4, newVar("nat-unfold"), newVar("f"), newVar("z"), newApp(3, newVal(newPrim("-")), newVar("n"), newVal(1)))), newVar("f"), newVar("z"), newVar("n"))), newVal(1), newVal(2), newVal(3)), newVarMap()))<<endl;
	cout<<"expr4 == "<<valEval(cek2(newApp(2, newVal(newLambda(1, newVar("quadruple"), newApp(2, newVal(newLambda(1, newVar("double"), newApp(3, newVal(newPrim("+")), newVar("y"), newVar("y")), newVar("y"))), newApp(3, newVal(newPrim("+")), newVar("x"), newVar("x"))), newVar("x"))), newVal(3)), newVarMap()))<<endl;
	cout<<"expr5 == "<<valEval(cek2(newApp(2, newVal(newLambda(1, newVar("fib"), newIf(newApp(3, newVal(newPrim("<=")), newVar("n"), newVal(1)), newVar("n"), newApp(3, newVal(newPrim("+")), newApp(2, newVar("fib"), newApp(3, newVal(newPrim("-")), newVar("n"), newVal(1))), newApp(2, newVar("fib"), newApp(3, newVal(newPrim("-")), newVar("n"), newVal(2))))), newVar("n"))), newVal(6)), newVarMap()))<<endl;
	cout<<"expr6 == "<<valEval(cek2(newApp(2, newVal(newLambda(1, newVar("fac"), newIf(newApp(3, newVal(newPrim("=")), newVar("n"), newVal(0)), newVal(1), newApp(3, newVal(newPrim("*")), newVar("n"), newApp(2, newVar("fac"), newApp(3, newVal(newPrim("-")), newVar("n"), newVal(1))))), newVar("n"))), newVal(6)), newVarMap()))<<endl;

	return 0;

}