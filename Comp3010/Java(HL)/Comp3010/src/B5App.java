import java.util.ArrayList;

public class B5App implements B5Expr {

	private ArrayList<B5Expr> exprs;
	
	public B5App(ArrayList<B5Expr> exprs) {	//(e ... e)
		this.exprs = exprs;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.APP;
		
	}

	public ArrayList<B5Expr> getExprs() {
		return exprs;
		
	}
	
}
