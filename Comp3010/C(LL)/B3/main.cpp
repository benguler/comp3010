#include <iostream>
#include "B3.h"

int main( int argc, char** argv ) {
	cout<<"expr1 == "<<valEval(cek1(newApp(2, newVal(newLambda(1, newApp(3, newVal(newPrim("+")), newVar("x"), newVal(1)), newVar("x"))), newVal(5)), newVarMap()))<<endl;
	cout<<"expr2 == "<<valEval(cek1(newApp(2, newVal(newLambda(1, newApp(3, newVal(newPrim("+")), newVar("x"), newVal(2)), newVar("x"))), newVal(8)), newVarMap()))<<endl;
	cout<<"expr3 == "<<valEval(cek1(newApp(2, newVal(newLambda(1, newApp(2, newVal(newLambda(1, newApp(3, newVal(newPrim("+")), newVar("x"), newVar("y")), newVar("y"))), newVal(8)), newVar("x"))), newVal(8)), newVarMap()))<<endl;
	cout<<"expr4 == "<<valEval(cek1(newApp(2, newVal(newLambda(1, newApp(2, newVal(newLambda(1, newApp(3, newVal(newPrim("+")), newVar("x"), newVar("x")), newVar("x"))), newApp(3, newVal(newPrim("+")), newVar("x"), newVal(1))), newVar("x"))), newVal(8)), newVarMap()))<<endl;
	cout<<"expr5 == "<<valEval(cek1(newApp(2, newVal(newLambda(1, newApp(2, newVar("f"), newVal(3)), newVar("f"))), newApp(2, newVal(newLambda(1, newVal(newLambda(1, newApp(3, newVal(newPrim("+")), newVar("x"), newVar("y")), newVar("y"))), newVar("x"))), newVal(1))), newVarMap()))<<endl;
	cout<<"expr6 == "<<valEval(cek1(newApp(2, newVal(newLambda(2, newApp(3, newVal(newPrim("+")), newVar("x"), newVar("y")), newVar("x"), newVar("y"))), newVal(5)), newVarMap()))<<endl;

	return 0;

}