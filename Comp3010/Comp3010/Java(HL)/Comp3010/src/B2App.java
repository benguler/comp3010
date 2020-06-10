import java.util.ArrayList;

public class B2App implements B2Expr {

	private ArrayList<B2Expr> exprs;
	
	public B2App(ArrayList<B2Expr> exprs) {	//(e ... e)
		this.exprs = exprs;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.APP;
		
	}
	
}
