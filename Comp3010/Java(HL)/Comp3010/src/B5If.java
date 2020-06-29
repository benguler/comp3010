public class B5If implements B5Expr {
	
	private B5Expr expr1;
	private B5Expr expr2;
	private B5Expr expr3;
	
	public B5If(B5Expr expr1, B5Expr expr2, B5Expr expr3) { //(if e e e)
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.expr3 = expr3;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.IF;
	}

	public B5Expr getExpr(int i) {
		if(i == 0) {
			return expr1;
			
		}else if(i == 1) {
			return expr2;
			
		}else {
			return expr3;
			
		}
		
	}
	
}