
public interface BSexpr {
	enum type{
		Atom,
		Cons,
		Empty;
	}
	
	public BExpr desugar();
	
	public B1Expr desugarB1();
	
	public B1Expr desugarB2();
	
	public type getType();
	
}
