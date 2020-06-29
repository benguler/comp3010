
public class B5Var implements B5Expr {

	private String varName;
	
	public B5Var(String varName) {
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