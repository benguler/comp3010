public class B4Var implements B4Expr {

	private String varName;
	
	public B4Var(String varName) {
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