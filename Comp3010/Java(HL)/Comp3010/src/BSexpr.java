
public interface BSexpr {
	enum type{
		Atom,
		Cons,
		Empty;
	}
	
	public BExpr desugar();
	
	public B1Expr desugarB1();
	
	public Pair desugarB2();
	
	public B2Expr desugarExprB2();
	
	public B2Def desugarDefB2();
	
	public type getType();
	
}
