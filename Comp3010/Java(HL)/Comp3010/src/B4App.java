import java.util.ArrayList;

public class B4App implements B4Expr {

	private ArrayList<B4Expr> exprs;
	
	public B4App(ArrayList<B4Expr> exprs) {	//(e ... e)
		this.exprs = exprs;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.APP;
		
	}

	public ArrayList<B4Expr> getExprs() {
		return exprs;
		
	}
	
}
