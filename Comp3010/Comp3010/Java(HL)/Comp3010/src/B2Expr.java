
public interface B2Expr {
	public enum ExprType{
		DEF,
		IF,
		APP,
		VAL,
		PRIM,
		VAR,
		FUNC
		
	}
	
	public ExprType getType(); 
	
}
