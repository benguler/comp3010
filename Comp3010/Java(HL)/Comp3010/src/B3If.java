
public class B3If implements B3Expr {
	
	private B3Expr expr1;
	private B3Expr expr2;
	private B3Expr expr3;
	
	public B3If(B3Expr expr1, B3Expr expr2, B3Expr expr3) { //(if e e e)
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.expr3 = expr3;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.IF;
	}

	public B3Expr getExpr(int i) {
		if(i == 0) {
			return expr1;
			
		}else if(i == 1) {
			return expr2;
			
		}else {
			return expr3;
			
		}
		
	}
	
}
