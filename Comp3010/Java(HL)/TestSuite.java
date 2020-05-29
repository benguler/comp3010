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
		
		//B0 Desugar Tests
		
		Atom val4 = new Atom("1");
		Atom val5 = new Atom("2");
		Atom val6 = new Atom("3");
		
		Cons add4 = new Cons(new Atom("+"), new Cons(new Atom("1"), new Atom("2")));
		Cons add5 = new Cons(new Atom("+"), new Cons(new Atom("2"), new Atom("3")));
		Cons add6 = new Cons(new Atom("+"), new Cons(new Atom("3"), new Atom("4")));
		
		Cons mult4 = new Cons(new Atom("*"), new Cons(new Atom("1"), new Atom("2")));
		Cons mult5 = new Cons(new Atom("*"), new Cons(new Atom("2"), new Atom("3")));
		Cons mult6 = new Cons(new Atom("*"), new Cons(new Atom("3"), new Atom("4")));
		
		Cons mix4 = new Cons(new Atom("*"), new Cons(new Cons(new Atom("+"), new Cons(new Atom("1"), new Atom("2"))), new Atom("3")));
		Cons mix5 = new Cons(new Atom("*"),
					new Cons(new Cons(new Atom("+"), new Cons(new Atom("1"), new Atom("2"))),
							 new Cons(new Atom("+"), new Cons(new Atom("2"), new Atom("2")))));
		Cons mix6 = new Cons(new Atom("+"),
					new Cons(new Cons(new Atom("*"), new Cons(new Atom("1"), new Atom("2"))),
							 new Cons(new Atom("*"), new Cons(new Atom("3"), new Atom("4")))));
		
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
		
	}

}
