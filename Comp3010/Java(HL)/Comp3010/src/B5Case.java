
public class B5Case implements B5Expr {

	private B5Expr x;
	private B5Expr e;
	
	public B5Case(B5Expr x, B5Expr e) {
		super();
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
		return ExprType.CASE;
		
	}

}
