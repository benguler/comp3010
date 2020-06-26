public class B4Prim implements B4Expr {

	String primType;	//+, *, -, /, <, <=, =, >, >=
	
	public B4Prim(String primType) {
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
