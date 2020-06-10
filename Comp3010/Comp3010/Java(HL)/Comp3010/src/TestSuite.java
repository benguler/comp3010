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
		
		Cons if1 = new Cons(new Atom("if"), new Cons(new Atom("true"), 
							new Cons(new Atom("1"), new Atom("2"))));	//If true == true then 1, else 2
		
		Cons if2 = new Cons(new Atom("if"), new Cons(new Atom("false"), 
				   		 	new Cons(new Atom("1"), new Atom("2"))));	//If false == true then 1, else 2
		
		Cons if3 = new Cons(new Atom("if"), new Cons(new Cons(new Atom("<"), new Cons(new Atom("1"), new Atom("2"))), 
				   			new Cons(new Atom("1"), new Atom("2"))));	//If 1 < 2 then 1, else 2
		
		Cons app1 = new Cons(new Atom("+"), new Cons(new Atom("1"), new Atom("1")));	//1 + 1 = 2
		Cons app2 = new Cons(new Atom("*"), new Cons(new Atom("2"), new Atom("2")));	//2 * 2 = 4
		Cons app3 = new Cons(new Atom("-"), new Cons(new Atom("2"), new Atom("1")));	//2 - 1 = 1
		Cons app4 = new Cons(new Atom("/"), new Cons(new Atom("4"), new Atom("2")));	//4 / 2 = 2
		Cons app5 = new Cons(new Atom("<"), new Cons(new Atom("3"), new Atom("2")));	//3 < 2 = 0  (false)
		Cons app6 = new Cons(new Atom("<="), new Cons(new Atom("3"), new Atom("3")));	//3 <= 3 = 1 (true)
		Cons app7 = new Cons(new Atom("="), new Cons(new Atom("3"), new Atom("3")));	//3 == 3 = 1 (true)
		Cons app8 = new Cons(new Atom(">"), new Cons(new Atom("3"), new Atom("2")));	//3 > 2 = 1  (true)
		Cons app9 = new Cons(new Atom(">="), new Cons(new Atom("3"), new Atom("5")));	//3 >= 5 = 0 (false)
		
		
		assertEquals(if1.desugarB1().interp(), 1 ," != 1");
		assertEquals(if2.desugarB1().interp(), 2 ," != 2");
		assertEquals(if3.desugarB1().interp(), 1 ," != 1");
		assertEquals(app1.desugarB1().interp(), 2 ," != 2");
		assertEquals(app2.desugarB1().interp(), 4 ," != 4");
		assertEquals(app3.desugarB1().interp(), 1 ," != 1");
		assertEquals(app4.desugarB1().interp(), 2 ," != 2");
		assertEquals(app5.desugarB1().interp(), 0 ," != 0");
		assertEquals(app6.desugarB1().interp(), 1 ," != 1");
		assertEquals(app7.desugarB1().interp(), 1 ," != 1");
		assertEquals(app8.desugarB1().interp(), 1 ," != 1");
		assertEquals(app9.desugarB1().interp(), 0 ," != 0");
		
		System.out.println(if3.desugarB1().pPrint());
		
		B1Functions b1Functions = new B1Functions();
		
		//Small-Step Tests
		
		assertEquals(if3.desugarB1().interp(), b1Functions.smallStep(if3.desugarB1()).interp() ," !!! ");
		
		System.out.println(if3.desugarB1().pPrint() + " == " + b1Functions.smallStep(if3.desugarB1()).pPrint());
		
		//emit tests
		
		b1Functions.emit(if3.desugarB1());
		
		b1Functions.connectTestSuite(if1.desugarB1(), 
									 if2.desugarB1(),
									 if3.desugarB1(),
									 app1.desugarB1(),
									 app2.desugarB1(),
									 app3.desugarB1(),
									 app4.desugarB1(),
									 app5.desugarB1(),
									 app6.desugarB1(),
									 app7.desugarB1(),
									 app8.desugarB1(),
									 app9.desugarB1());
		
	}
	
	//B2 Desugar Tests 
	
	Cons func1 = new Cons(new Atom("def"), new Cons(new Cons(new Atom("DOUBLE"), new Atom("x")),	//f1(x) = x + x
											new Cons(new Atom("+"), new Cons(new Atom("x"), new Atom("x")))));
	
	Cons func2 = new Cons(new Atom("def"), new Cons(new Cons(new Atom("QUADRUPLE"), new Atom("y")),	//f2(x) = (x + x) + (x + x)
											new Cons(new Atom("DOUBLE"), new Cons(new Atom ("DOUBLE"), new Atom("y")))));
	
	//Cons func3 = new Cons(new Atom("def"), new Cons(new Cons(new Atom("FIB"), new Atom("z")),
											

}
