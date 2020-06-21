public class B3Prim implements B3Expr {

	String primType;	//+, *, -, /, <, <=, =, >, >=
	
	public B3Prim(String primType) {
		this.primType = primType;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.PRIM;
	}

	public String getPrimType() {
		return primType;
		
	}

}
