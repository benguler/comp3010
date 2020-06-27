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
		
		Atom val4 = a("1");	//1
		Atom val5 = a("2");	//2
		Atom val6 = a("3");	//3
		
		Cons add4 = c(a("+"), c(a("1"), a("2")));	//1 + 2 = 3
		Cons add5 = c(a("+"), c(a("2"), a("3")));	//2 + 3 = 5
		Cons add6 = c(a("+"), c(a("3"), a("4")));	//3 + 4 = 7
		
		Cons mult4 = c(a("*"), c(a("1"), a("2")));	//1 * 2 = 2
		Cons mult5 = c(a("*"), c(a("2"), a("3")));	//2 * 3 = 6
		Cons mult6 = c(a("*"), c(a("3"), a("4")));	//3 * 4 = 12
		
		Cons mix4 = c(a("*"), 
											c(c(a("+"), 
											c(a("1"), a("2"))), a("3")));	//(1 + 2) * 3 = 9
		Cons mix5 = c(a("*"),
					c(c(a("+"), c(a("1"), a("2"))),
							 c(a("+"), c(a("2"), a("2")))));			//(1 + 2) * (2 * 2) = 12
		Cons mix6 = c(a("+"),
					c(c(a("*"), c(a("1"), a("2"))),
							 c(a("*"), c(a("3"), a("4")))));			//(1 * 2) + (3 * 4) = 14
		
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
		
		Cons b1if1 = c(a("if"), c(a("true"), 
							c(a("1"), a("2"))));	//If true == true then 1, else 2
		
		Cons b1if2 = c(a("if"), c(a("false"), 
				   		 	c(a("1"), a("2"))));	//If false == true then 1, else 2
		
		Cons b1if3 = c(a("if"), c(c(a("<"), c(a("1"), a("2"))), 
				   			c(a("1"), a("2"))));	//If 1 < 2 then 1, else 2
		
		Cons b1App1 = c(a("+"), c(a("1"), a("1")));	//1 + 1 = 2
		Cons b1App2 = c(a("*"), c(a("2"), a("2")));	//2 * 2 = 4
		Cons b1App3 = c(a("-"), c(a("2"), a("1")));	//2 - 1 = 1
		Cons b1App4 = c(a("/"), c(a("4"), a("2")));	//4 / 2 = 2
		Cons b1App5 = c(a("<"), c(a("3"), a("2")));	//3 < 2 = 0  (false)
		Cons b1App6 = c(a("<="), c(a("3"), a("3")));	//3 <= 3 = 1 (true)
		Cons b1App7 = c(a("="), c(a("3"), a("3")));	//3 == 3 = 1 (true)
		Cons b1App8 = c(a(">"), c(a("3"), a("2")));	//3 > 2 = 1  (true)
		Cons b1App9 = c(a(">="), c(a("3"), a("5")));	//3 >= 5 = 0 (false)
		
		
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
		
		Cons b2Func1 = c(a("def"), c(a("DOUBLE"), c(a("x"),
								c(a("+"), c(a("x"), a("x"))))));
		
		Cons b2Func2 = c(a("def"), c(a("DIFFERENCE"), c(c(a("x"), a("y")),
								c(a("if"), c(c(a(">"), c(a("x"), a("y"))),
										c(c(a("-"), c(a("x"), a("y"))), 
												 c(a("-"), c(a("y"), a("x")))))))));
		
		Cons b2Func3 = c(a("def"), c(a("RECUR"), c(a("r"), 
			  	c(a("if"), c(c(a("<="), c(a("r"), a("6"))),
			  			c(a("r"),
			  					c(a("RECUR"), c(a("-"), c(a("r"), a("1"))))))))));
		
		Cons b2Func4 = c(a("def"), c(a("FIB"), c(a("n"), 
							  	c(a("if"), c(c(a("<="), c(a("n"), a("1"))),
							  			c(a("n"),
							  					 c(a("+"), c(c(a("FIB"), c(a("-"), c(a("n"), a("1")))),
							  							 				 		  c(a("FIB"), c(a("-"), c(a("n"), a("2"))))))))))));
		
		Cons b2Func5 = c(a("def"), c(a("FIVE"), c(e(), a("5"))));
		
		Cons b2Func6 = c(a("def"), c(a("QUADRUPLE"), c(a("q"),
				c(a("DOUBLE"), c(a("DOUBLE"), a("q"))))));
		
		Cons b2Func7 = c(a("def"), c(a("UNTILNI"), c(a("x"),
								c(a("if"), c(c(a("<"), c(a("x"), a("0"))),
										c(a("x"), c(a("UNTILNII"),c(a("-"), c(a("x"), a("1"))))))))));
		
		Cons b2Func8 = c(a("def"), c(a("UNTILNII"), c(a("x"),
				c(a("if"), c(c(a("<"), c(a("x"), a("0"))),
						c(a("x"), c(a("UNTILNI"), c(a("-"), c(a("x"), a("1"))))))))));
		
	
		B2Functions.define(b2Func1.desugarB2Def(), fm);
		B2Functions.define(b2Func2.desugarB2Def(), fm);
		B2Functions.define(b2Func3.desugarB2Def(), fm);
		B2Functions.define(b2Func4.desugarB2Def(), fm);
		B2Functions.define(b2Func5.desugarB2Def(), fm);
		B2Functions.define(b2Func6.desugarB2Def(), fm);
		B2Functions.define(b2Func7.desugarB2Def(), fm);
		B2Functions.define(b2Func8.desugarB2Def(), fm);
		
		Cons b2App1 = c(a("DOUBLE"), c(a("+"), c(a("1"), a("1"))));
		Cons b2App2 = c(a("DIFFERENCE"), c( c(a("DOUBLE"), a("2")), a("9")));
		Cons b2App3 = c(a("RECUR"), a("13"));
		Cons b2App4 = c(a("FIB"), a("8"));
		Cons b2App5 = c(a("FIVE"), e());
		Cons b2App6 = c(a("QUADRUPLE"), a("2"));
		Cons b2App7 = c(a("UNTILNI"), a("2"));
		Cons b2App8 = c(a("UNTILNI"), a("7"));
		
		assertEquals(B2Functions.bigStep(b2App1.desugarB2Expr(), new VarMap(), fm), 4,  "DOUBLE");
		assertEquals(B2Functions.bigStep(b2App2.desugarB2Expr(), new VarMap(), fm), 5,  "DIFFERENCE");
		assertEquals(B2Functions.bigStep(b2App3.desugarB2Expr(), new VarMap(), fm), 6,  "RECUR");
		assertEquals(B2Functions.bigStep(b2App4.desugarB2Expr(), new VarMap(), fm), 21, "FIB");
		assertEquals(B2Functions.bigStep(b2App5.desugarB2Expr(), new VarMap(), fm), 5,  "FIVE");
		assertEquals(B2Functions.bigStep(b2App6.desugarB2Expr(), new VarMap(), fm), 8,  "QUADRUPLE");
		assertEquals(B2Functions.bigStep(b2App7.desugarB2Expr(), new VarMap(), fm), -1, "UNTILNI");
		assertEquals(B2Functions.bigStep(b2App8.desugarB2Expr(), new VarMap(), fm), -1, "UNTILNII");
		
		//B3 Desugar Tests 
		
		Cons b3App1 = c(c(a("lambda"), c(a("x"), c(a("+"), c(a("x"), a("1"))))), a("5"));
		Cons b3App2 = c(a("let"), c(c(a ("x"), a("8")), c(a("+"), c(a("x"), a("2")))));
		Cons b3App3 = c(a("let"), c(c(a ("x"), a("8")), c(a("let"), 
								c(c(a ("y"), a("8")), c(a("+"), c(a("x"), a("y")))))));
		Cons b3App4 = c(a("let"), 
								c(c(a ("x"), a("8")), 
						c(a("let"), 
								c(c(a ("x"), c(a("+"), c(a("x"), a("1")))), c(a("+"), c(a("x"), a("x")))))));
		
		Cons b3App5 = c(a("let"), 
								c(c(a ("f"), c(a("let"), 
																	c(c(a ("x"), a("1")), c(a("lambda"), c(a("y"), c(a("+"), c(a("x"), a("y")))))))), 
										c(a("f"), a("3"))));
		
		Cons b3App6 = c(c(a("lambda"), c(c(a("x"), a("y")), c(a("+"), c(a("x"), a("y"))))), c(a("5"), a("3")));
		
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
