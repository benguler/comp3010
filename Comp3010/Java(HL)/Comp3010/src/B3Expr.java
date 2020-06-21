public interface B3Expr {
	public enum ExprType{
		IF,
		APP,
		VAL,
		PRIM,
		VAR,
		LAMB
		
	}
	
	public ExprType getType(); 
	
}
