

public class B2Func implements B2Expr {

	private String funcName;
	
	public B2Func(String funcName) {
		this.funcName = funcName;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.FUNC;
	}

	public String getFuncName() {
		return funcName;
		
	}

}