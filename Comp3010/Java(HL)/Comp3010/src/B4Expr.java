public interface B4Expr {
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
