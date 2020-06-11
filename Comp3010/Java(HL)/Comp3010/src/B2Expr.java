
public interface B2Expr {
	public enum ExprType{
		IF,
		APP,
		VAL,
		PRIM,
		VAR,
		FUNC
		
	}
	
	public ExprType getType(); 
	
}
