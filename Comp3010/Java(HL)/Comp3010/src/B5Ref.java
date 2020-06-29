
public class B5Ref implements B5Expr {

	private B5Expr x;
	private B5Expr e;
	
	public B5Ref(B5Expr x, B5Expr e) {
		this.x = x;
		this.e = e;
	}


	public B5Expr getX() {
		return x;
	}

	public B5Expr getE() {
		return e;
	}
	
	@Override
	public ExprType getType() {
		return ExprType.REF;
		
	}

}
