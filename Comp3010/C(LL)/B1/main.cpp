#include <iostream>
#include "B1.h"

int main( int argc, char** argv ) {
	cout<<"(if true then 1 else 2) == "<<valEval(ck0(newIf(newVal(true), newVal(1), newVal(2))))<<endl;
	cout<<"(if false then 1 else 2) == "<<valEval(ck0(newIf(newVal(false), newVal(1), newVal(2))))<<endl;
	cout<<"(if (< 1 2 ) then 1 else 2) == "<<valEval(ck0(newIf(newApp(3, newVal(newPrim("<")), newVal(1), newVal(2)), newVal(1), newVal(2))))<<endl;
	cout<<"(+ 1 1 ) == "<<valEval(ck0(newApp(3, newVal(newPrim("+")), newVal(1), newVal(1))))<<endl;
	cout<<"(* 2 2 ) == "<<valEval(ck0(newApp(3, newVal(newPrim("*")), newVal(2), newVal(2))))<<endl;
	cout<<"(- 2 1 ) == "<<valEval(ck0(newApp(3, newVal(newPrim("-")), newVal(2), newVal(1))))<<endl;
	cout<<"(/ 4 2 ) == "<<valEval(ck0(newApp(3, newVal(newPrim("/")), newVal(4), newVal(2))))<<endl;
	cout<<"(< 3 2 ) == "<<valEval(ck0(newApp(3, newVal(newPrim("<")), newVal(3), newVal(2))))<<endl;
	cout<<"(<= 3 3 ) == "<<valEval(ck0(newApp(3, newVal(newPrim("<=")), newVal(3), newVal(3))))<<endl;
	cout<<"(= 3 3 ) == "<<valEval(ck0(newApp(3, newVal(newPrim("=")), newVal(3), newVal(3))))<<endl;
	cout<<"(> 3 2 ) == "<<valEval(ck0(newApp(3, newVal(newPrim(">")), newVal(3), newVal(2))))<<endl;
	cout<<"(>= 3 5 ) == "<<valEval(ck0(newApp(3, newVal(newPrim(">=")), newVal(3), newVal(5))))<<endl;
	
return 0;

}