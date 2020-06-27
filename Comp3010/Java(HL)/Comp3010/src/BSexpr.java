
public interface BSexpr {
	enum type{
		ATOM,
		CONS,
		EMPTY;
	}
	
	public BExpr desugar();
	
	public B1Expr desugarB1();

	public B2Expr desugarB2Expr();
	
	public B2Def desugarB2Def();
	
	public B3Expr desugarB3Expr();
	
	public B4Expr desugarB4Expr();
	
	public type getType();
	
}
