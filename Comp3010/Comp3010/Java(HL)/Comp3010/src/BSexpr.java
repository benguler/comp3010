
public interface BSexpr {
	enum type{
		ATOM,
		CONS,
		EMPTY;
	}
	
	public BExpr desugar();
	
	public B1Expr desugarB1();
	
	public Pair desugarB2();
	
	public B2Expr desugarB2Expr();
	
	public B2Def desugarB2Def();
	
	public type getType();
	
}
