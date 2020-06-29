public interface B5Expr {
	public enum ExprType{
		IF,
		APP,
		VAL,
		PRIM,
		VAR,
		LAMB,
		CASE,
		OBJ,
		REF
		
	}
	
	public ExprType getType(); 
	
}
