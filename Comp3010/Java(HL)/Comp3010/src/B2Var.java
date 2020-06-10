public class B2Var implements B2Expr {

	private String varName;
	
	public B2Var(String varName) {
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