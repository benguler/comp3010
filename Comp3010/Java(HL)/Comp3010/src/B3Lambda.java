import java.util.ArrayList;

public class B3Lambda implements B3Expr{

	private ArrayList<B3Var> vars;
	private B3Expr expr;
	
	public B3Lambda(ArrayList<B3Var> vars, B3Expr expr) {
		super();
		this.vars = vars;
		this.expr = expr;
	}
	
	public B3Lambda(B3Var var, B3Expr expr) {
		super();
		this.vars = new ArrayList<B3Var>();
		this.vars.add(var);
		this.expr = expr;
	}
	
	

	public ArrayList<B3Var> getVars() {
		return vars;
	}

	public B3Expr getExpr() {
		return expr;
	}

	@Override
	public ExprType getType() {
		return ExprType.LAMB;
		
	}
	
}
