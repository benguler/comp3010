

public class B2Prim implements B2Expr {

	String primType;	//+, *, -, /, <, <=, =, >, >=
	
	public B2Prim(String primType) {
		this.primType = primType;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.PRIM;
	}

}
