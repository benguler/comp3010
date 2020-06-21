import java.util.ArrayList;

public class B3App implements B3Expr {

	private ArrayList<B3Expr> exprs;
	
	public B3App(ArrayList<B3Expr> exprs) {	//(e ... e)
		this.exprs = exprs;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.APP;
		
	}

	public ArrayList<B3Expr> getExprs() {
		return exprs;
		
	}
	
}
