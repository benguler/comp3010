public class B2If implements B2Expr {
	
	private B2Expr expr1;
	private B2Expr expr2;
	private B2Expr expr3;
	
	public B2If(B2Expr expr1, B2Expr expr2, B2Expr expr3) { //(if e e e)
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.expr3 = expr3;
		
	}
	
}
