public class B3Var implements B3Expr {

	private String varName;
	
	public B3Var(String varName) {
		this.varName = varName;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.VAR;
	}

	public String getVarName() {
		return varName;
	}


}