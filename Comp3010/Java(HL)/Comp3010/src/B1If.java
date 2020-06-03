public class B1If implements B1Expr {
	private B1Expr expr1;
	private B1Expr expr2;
	private B1Expr expr3;
	
	public B1If(B1Expr expr1, B1Expr expr2, B1Expr expr3) { //(if e e e)
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.expr3 = expr3;
		
	}

	@Override
	public int interp() {			//If e1 == false, e3, else e2
		if(expr1.interp() == 0) {
			return expr3.interp();
			
		}else {
			return expr2.interp();
			
		}
		
	}

	@Override
	public String pPrint() {
		return "(if " + expr1.pPrint() + " then " + expr3.pPrint() + " else " + expr2.pPrint() + ")";
	}

	@Override
	public ExprType getExprType() {
		return ExprType.IF;
		
	}

	public B1Expr getExpr(int i) {
		if(i == 0) {
			return expr1;
			
		}else if(i == 1) {
			return expr2;
			
		}else {
			return expr3;
			
		}
		
	}
	
}
