import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSuite {

	@Test
	void test() {
		
		//B0 Tests
		
		BVal val1 = new BVal(1);	//1
		BVal val2 = new BVal(2);	//2
		BVal val3 = new BVal(3);	//3
		
		BAdd add1 = new BAdd(new BVal(1), new BVal(2));		//1 + 2 = 3
		BAdd add2 = new BAdd(new BVal(2), new BVal(3));		//2 + 3 = 5
		BAdd add3 = new BAdd(new BVal(3), new BVal(4));		//3 + 4 = 7

		BMult mult1 = new BMult(new BVal(1), new BVal(2));	//1 * 2 = 2
		BMult mult2 = new BMult(new BVal(2), new BVal(3));	//2 * 3 = 6
		BMult mult3 = new BMult(new BVal(3), new BVal(4));	//3 * 4 = 12
		
		BMult mix1 = new BMult(new BAdd(new BVal(1), new BVal(2)), new BVal(3));						//(1 + 2) * 3 = 9
		BMult mix2 = new BMult(new BAdd(new BVal(1), new BVal(2)), new BAdd(new BVal(2), new BVal(2)));	//(1 + 2) * (2 * 2) = 12
		BAdd mix3 = new BAdd(new BMult(new BVal(1), new BVal(2)), new BMult(new BVal(3), new BVal(4)));	//(1 * 2) + (3 * 4) = 14
		
		assertEquals(val1.interp(), 1 ," != 1");
		assertEquals(val2.interp(), 2 ," != 2");
		assertEquals(val3.interp(), 3 ," != 3");
		
		assertEquals(add1.interp(), 3 ," != 3");
		assertEquals(add2.interp(), 5 ," != 5");
		assertEquals(add3.interp(), 7 ," != 7");
		
		assertEquals(mult1.interp(), 2 ," != 2");
		assertEquals(mult2.interp(), 6 ," != 6");
		assertEquals(mult3.interp(), 12 ," != 12");
		
		assertEquals(mix1.interp(), 9 ," != 9");
		assertEquals(mix2.interp(), 12 ," != 12");
		assertEquals(mix3.interp(), 14 ," != 14");
		
		System.out.println(mix3.pPrint());
		
		//B0 Desugar Tests
		
		Atom val4 = new Atom("1");	//1
		Atom val5 = new Atom("2");	//2
		Atom val6 = new Atom("3");	//3
		
		Cons add4 = new Cons(new Atom("+"), new Cons(new Atom("1"), new Atom("2")));	//1 + 2 = 3
		Cons add5 = new Cons(new Atom("+"), new Cons(new Atom("2"), new Atom("3")));	//2 + 3 = 5
		Cons add6 = new Cons(new Atom("+"), new Cons(new Atom("3"), new Atom("4")));	//3 + 4 = 7
		
		Cons mult4 = new Cons(new Atom("*"), new Cons(new Atom("1"), new Atom("2")));	//1 * 2 = 2
		Cons mult5 = new Cons(new Atom("*"), new Cons(new Atom("2"), new Atom("3")));	//2 * 3 = 6
		Cons mult6 = new Cons(new Atom("*"), new Cons(new Atom("3"), new Atom("4")));	//3 * 4 = 12
		
		Cons mix4 = new Cons(new Atom("*"), 
											new Cons(new Cons(new Atom("+"), 
											new Cons(new Atom("1"), new Atom("2"))), new Atom("3")));	//(1 + 2) * 3 = 9
		Cons mix5 = new Cons(new Atom("*"),
					new Cons(new Cons(new Atom("+"), new Cons(new Atom("1"), new Atom("2"))),
							 new Cons(new Atom("+"), new Cons(new Atom("2"), new Atom("2")))));			//(1 + 2) * (2 * 2) = 12
		Cons mix6 = new Cons(new Atom("+"),
					new Cons(new Cons(new Atom("*"), new Cons(new Atom("1"), new Atom("2"))),
							 new Cons(new Atom("*"), new Cons(new Atom("3"), new Atom("4")))));			//(1 * 2) + (3 * 4) = 14
		
		assertEquals(val4.desugar().interp(), 1 ," != 1");
		assertEquals(val5.desugar().interp(), 2 ," != 2");
		assertEquals(val6.desugar().interp(), 3 ," != 3");
		
		assertEquals(add4.desugar().interp(), 3 ," != 3");
		assertEquals(add5.desugar().interp(), 5 ," != 5");
		assertEquals(add6.desugar().interp(), 7 ," != 7");
		
		assertEquals(mult4.desugar().interp(), 2 ," != 2");
		assertEquals(mult5.desugar().interp(), 6 ," != 6");
		assertEquals(mult6.desugar().interp(), 12 ," != 12");
		
		assertEquals(mix4.desugar().interp(), 9 ," != 9");
		assertEquals(mix5.desugar().interp(), 12 ," != 12");
		assertEquals(mix6.desugar().interp(), 14 ," != 14");
		
		//B1 Desugar Tests 
		
		Cons b1if1 = new Cons(new Atom("if"), new Cons(new Atom("true"), 
							new Cons(new Atom("1"), new Atom("2"))));	//If true == true then 1, else 2
		
		Cons b1if2 = new Cons(new Atom("if"), new Cons(new Atom("false"), 
				   		 	new Cons(new Atom("1"), new Atom("2"))));	//If false == true then 1, else 2
		
		Cons b1if3 = new Cons(new Atom("if"), new Cons(new Cons(new Atom("<"), new Cons(new Atom("1"), new Atom("2"))), 
				   			new Cons(new Atom("1"), new Atom("2"))));	//If 1 < 2 then 1, else 2
		
		Cons b1App1 = new Cons(new Atom("+"), new Cons(new Atom("1"), new Atom("1")));	//1 + 1 = 2
		Cons b1App2 = new Cons(new Atom("*"), new Cons(new Atom("2"), new Atom("2")));	//2 * 2 = 4
		Cons b1App3 = new Cons(new Atom("-"), new Cons(new Atom("2"), new Atom("1")));	//2 - 1 = 1
		Cons b1App4 = new Cons(new Atom("/"), new Cons(new Atom("4"), new Atom("2")));	//4 / 2 = 2
		Cons b1App5 = new Cons(new Atom("<"), new Cons(new Atom("3"), new Atom("2")));	//3 < 2 = 0  (false)
		Cons b1App6 = new Cons(new Atom("<="), new Cons(new Atom("3"), new Atom("3")));	//3 <= 3 = 1 (true)
		Cons b1App7 = new Cons(new Atom("="), new Cons(new Atom("3"), new Atom("3")));	//3 == 3 = 1 (true)
		Cons b1App8 = new Cons(new Atom(">"), new Cons(new Atom("3"), new Atom("2")));	//3 > 2 = 1  (true)
		Cons b1App9 = new Cons(new Atom(">="), new Cons(new Atom("3"), new Atom("5")));	//3 >= 5 = 0 (false)
		
		
		assertEquals(b1if1.desugarB1().interp(), 1 ," != 1");
		assertEquals(b1if2.desugarB1().interp(), 2 ," != 2");
		assertEquals(b1if3.desugarB1().interp(), 1 ," != 1");
		assertEquals(b1App1.desugarB1().interp(), 2 ," != 2");
		assertEquals(b1App2.desugarB1().interp(), 4 ," != 4");
		assertEquals(b1App3.desugarB1().interp(), 1 ," != 1");
		assertEquals(b1App4.desugarB1().interp(), 2 ," != 2");
		assertEquals(b1App5.desugarB1().interp(), 0 ," != 0");
		assertEquals(b1App6.desugarB1().interp(), 1 ," != 1");
		assertEquals(b1App7.desugarB1().interp(), 1 ," != 1");
		assertEquals(b1App8.desugarB1().interp(), 1 ," != 1");
		assertEquals(b1App9.desugarB1().interp(), 0 ," != 0");
		
		System.out.println(b1if3.desugarB1().pPrint());
		
		B1Functions b1Functions = new B1Functions();
		
		//Small-Step Tests
		
		assertEquals(b1if3.desugarB1().interp(), b1Functions.smallStep(b1if3.desugarB1()).interp() ," !!! ");
		
		System.out.println(b1if3.desugarB1().pPrint() + " == " + b1Functions.smallStep(b1if3.desugarB1()).pPrint());
		
		//emit tests
		
		B1Functions.emit(b1if3.desugarB1());
		
		B1Functions.connectTestSuite(b1if1.desugarB1(), 
									 b1if2.desugarB1(),
									 b1if3.desugarB1(),
									 b1App1.desugarB1(),
									 b1App2.desugarB1(),
									 b1App3.desugarB1(),
									 b1App4.desugarB1(),
									 b1App5.desugarB1(),
									 b1App6.desugarB1(),
									 b1App7.desugarB1(),
									 b1App8.desugarB1(),
									 b1App9.desugarB1());
	
		//B2 Desugar Tests 

		FuncMap fm = new FuncMap();
		
		Cons b2Func1 = new Cons(new Atom("def"), new Cons(new Atom("DOUBLE"), new Cons(new Atom("x"),
								new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("x"))))));
		
		Cons b2Func2 = new Cons(new Atom("def"), new Cons(new Atom("DIFFERENCE"), new Cons(new Cons(new Atom("x"), new Atom("y")),
								new Cons(new Atom("if"), new Cons(new Cons(new Atom(">"), new Cons(new Atom("x"), new Atom("y"))),
										new Cons(new Cons(new Atom("-"), new Cons(new Atom("x"), new Atom("y"))), 
												 new Cons(new Atom("-"), new Cons(new Atom("y"), new Atom("x")))))))));
		
		Cons b2Func3 = new Cons(new Atom("def"), new Cons(new Atom("RECUR"), new Cons(new Atom("r"), 
			  	new Cons(new Atom("if"), new Cons(new Cons(new Atom("<="), new Cons(new Atom("r"), new Atom("6"))),
			  			new Cons(new Atom("r"),
			  					new Cons(new Atom("RECUR"), new Cons(new Atom("-"), new Cons(new Atom("r"), new Atom("1"))))))))));
		
		Cons b2Func4 = new Cons(new Atom("def"), new Cons(new Atom("FIB"), new Cons(new Atom("n"), 
							  	new Cons(new Atom("if"), new Cons(new Cons(new Atom("<="), new Cons(new Atom("n"), new Atom("1"))),
							  			new Cons(new Atom("n"),
							  					 new Cons(new Atom("+"), new Cons(new Cons(new Atom("FIB"), new Cons(new Atom("-"), new Cons(new Atom("n"), new Atom("1")))),
							  							 				 		  new Cons(new Atom("FIB"), new Cons(new Atom("-"), new Cons(new Atom("n"), new Atom("2"))))))))))));
		
		Cons b2Func5 = new Cons(new Atom("def"), new Cons(new Atom("FIVE"), new Cons(new Empty(), new Atom("5"))));
		
		Cons b2Func6 = new Cons(new Atom("def"), new Cons(new Atom("QUADRUPLE"), new Cons(new Atom("q"),
				new Cons(new Atom("DOUBLE"), new Cons(new Atom("DOUBLE"), new Atom("q"))))));
		
		Cons b2Func7 = new Cons(new Atom("def"), new Cons(new Atom("UNTILNI"), new Cons(new Atom("x"),
								new Cons(new Atom("if"), new Cons(new Cons(new Atom("<"), new Cons(new Atom("x"), new Atom("0"))),
										new Cons(new Atom("x"), new Cons(new Atom("UNTILNII"),new Cons(new Atom("-"), new Cons(new Atom("x"), new Atom("1"))))))))));
		
		Cons b2Func8 = new Cons(new Atom("def"), new Cons(new Atom("UNTILNII"), new Cons(new Atom("x"),
				new Cons(new Atom("if"), new Cons(new Cons(new Atom("<"), new Cons(new Atom("x"), new Atom("0"))),
						new Cons(new Atom("x"), new Cons(new Atom("UNTILNI"), new Cons(new Atom("-"), new Cons(new Atom("x"), new Atom("1"))))))))));
		
	
		B2Functions.define(b2Func1.desugarB2Def(), fm);
		B2Functions.define(b2Func2.desugarB2Def(), fm);
		B2Functions.define(b2Func3.desugarB2Def(), fm);
		B2Functions.define(b2Func4.desugarB2Def(), fm);
		B2Functions.define(b2Func5.desugarB2Def(), fm);
		B2Functions.define(b2Func6.desugarB2Def(), fm);
		B2Functions.define(b2Func7.desugarB2Def(), fm);
		B2Functions.define(b2Func8.desugarB2Def(), fm);
		
		Cons b2App1 = new Cons(new Atom("DOUBLE"), new Cons(new Atom("+"), new Cons(new Atom("1"), new Atom("1"))));
		Cons b2App2 = new Cons(new Atom("DIFFERENCE"), new Cons(new Atom("8"), new Atom("3")));
		Cons b2App3 = new Cons(new Atom("RECUR"), new Atom("13"));
		Cons b2App4 = new Cons(new Atom("FIB"), new Atom("8"));
		Cons b2App5 = new Cons(new Atom("FIVE"), new Empty());
		Cons b2App6 = new Cons(new Atom("QUADRUPLE"), new Atom("2"));
		Cons b2App7 = new Cons(new Atom("UNTILNI"), new Atom("2"));
		Cons b2App8 = new Cons(new Atom("UNTILNI"), new Atom("7"));
		
		//Cons b2App1 = new Cons(new Atom("FIB"), new Atom("0"));
		//Cons b2App2 = new Cons(new Atom("FIB"), new Atom("1"));
		//Cons b2App3 = new Cons(new Atom("FIB"), new Atom("2"));
		//Cons b2App4 = new Cons(new Atom("FIB"), new Atom("3"));
		//Cons b2App5 = new Cons(new Atom("FIB"), new Atom("4"));
		//Cons b2App6 = new Cons(new Atom("FIB"), new Atom("5"));
		
		assertEquals(B2Functions.bigStep(b2App1.desugarB2Expr(), new VarMap(), fm), 4,  "DOUBLE");
		assertEquals(B2Functions.bigStep(b2App2.desugarB2Expr(), new VarMap(), fm), 5,  "DIFFERENCE");
		assertEquals(B2Functions.bigStep(b2App3.desugarB2Expr(), new VarMap(), fm), 6,  "RECUR");
		assertEquals(B2Functions.bigStep(b2App4.desugarB2Expr(), new VarMap(), fm), 21, "FIB");
		assertEquals(B2Functions.bigStep(b2App5.desugarB2Expr(), new VarMap(), fm), 5,  "FIVE");
		assertEquals(B2Functions.bigStep(b2App6.desugarB2Expr(), new VarMap(), fm), 8,  "QUADRUPLE");
		assertEquals(B2Functions.bigStep(b2App7.desugarB2Expr(), new VarMap(), fm), -1, "UNTILNI");
		assertEquals(B2Functions.bigStep(b2App8.desugarB2Expr(), new VarMap(), fm), -1, "UNTILNII");
		
		//B2 Desugar Tests 
		
		Cons b3App1 = new Cons(new Cons(new Atom("lambda"), new Cons(new Atom("x"), new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("1"))))), new Atom("5"));
		Cons b3App2 = new Cons(new Atom("let"), new Cons(new Cons(new Atom ("x"), new Atom("8")), new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("2")))));
		Cons b3App3 = new Cons(new Atom("let"), new Cons(new Cons(new Atom ("x"), new Atom("8")), new Cons(new Atom("let"), 
								new Cons(new Cons(new Atom ("y"), new Atom("8")), new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("y")))))));
		Cons b3App4 = new Cons(new Atom("let"), 
								new Cons(new Cons(new Atom ("x"), new Atom("8")), 
						new Cons(new Atom("let"), 
								new Cons(new Cons(new Atom ("x"), new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("1")))), new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("x")))))));
		
		Cons b3App5 = new Cons(new Atom("let"), 
								new Cons(new Cons(new Atom ("f"), new Cons(new Atom("let"), 
																	new Cons(new Cons(new Atom ("x"), new Atom("1")), new Cons(new Atom("lambda"), new Cons(new Atom("y"), new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("y")))))))), 
										new Cons(new Atom("f"), new Atom("3"))));
		
		Cons b3App6 = new Cons(new Cons(new Atom("lambda"), new Cons(new Cons(new Atom("x"), new Atom("y")), new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("y"))))), new Cons(new Atom("5"), new Atom("3")));
		
		B3Functions.connectTestSuite(b3App1.desugarB3Expr(), 
								  	 b3App2.desugarB3Expr(),
								  	 b3App3.desugarB3Expr(),
								  	 b3App4.desugarB3Expr(),
								  	 b3App5.desugarB3Expr(),
								  	 b3App6.desugarB3Expr()
								  	 );
		
	}

	Atom a(String atom) {
		return new Atom(atom);
		
	}
	
	Cons c(BSexpr sexpr1, BSexpr sexpr2) {
		return new Cons(sexpr1, sexpr2);
		
	}
	
	Empty e() {
		return new Empty();
		
	}
	
}
