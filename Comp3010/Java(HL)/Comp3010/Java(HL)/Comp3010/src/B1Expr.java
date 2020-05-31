
public interface B1Expr {
	
	public enum ExprType{
		VAL,
		IF,
		APP,
		PRIM,
		CON
	}
	
	public int interp();
	
	public String pPrint();
	
	public ExprType getExprType();
	
	public boolean isContext();
	
}
