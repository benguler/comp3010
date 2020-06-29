public class B5Prim implements B4Expr {

	String primType;	//+, *, -, /, <, <=, =, >, >=, pair, fst, snd, inl, inr
	
	public B5Prim(String primType) {
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
