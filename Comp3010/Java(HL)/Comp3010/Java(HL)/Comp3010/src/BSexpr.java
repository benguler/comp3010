
public interface BSexpr {
	enum type{
		Atom,
		Cons,
		Empty;
	}
	
	public BExpr desugar();
	
	public B1Expr desugarB1();
	
	public type getType();
	
}
