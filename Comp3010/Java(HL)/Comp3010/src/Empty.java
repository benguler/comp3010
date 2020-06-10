
public class Empty implements BSexpr{
	
	public Empty() {
		
	}

	@Override
	public BExpr desugar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public type getType() {
		return type.Empty;
		
	}

	@Override
	public B1Expr desugarB1() {
		return null;
		
	}

	@Override
	public Pair desugarB2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B2Expr desugarExprB2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B2Def desugarDefB2() {
		// TODO Auto-generated method stub
		return null;
	}

}
