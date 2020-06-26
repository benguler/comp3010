public class B4If implements B4Expr {
	
	private B4Expr expr1;
	private B4Expr expr2;
	private B4Expr expr3;
	
	public B4If(B4Expr expr1, B4Expr expr2, B4Expr expr3) { //(if e e e)
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.expr3 = expr3;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.IF;
	}

	public B4Expr getExpr(int i) {
		if(i == 0) {
			return expr1;
			
		}else if(i == 1) {
			return expr2;
			
		}else {
			return expr3;
			
		}
		
	}
	
}
