import java.util.ArrayList;

public class B4Lambda implements B4Expr{

	private ArrayList<B4Var> vars;
	private B4Expr expr;
	private B4Var var;
	
	public B4Lambda(B4Var var, ArrayList<B4Var> vars, B4Expr expr) {
		super();
		this.vars = vars;
		this.expr = expr;
		this.var = var;
	}
	
	public B4Lambda(B4Var var, B4Var var0, B4Expr expr) {
		super();
		this.vars = new ArrayList<B4Var>();
		this.vars.add(var0);
		this.expr = expr;
		this.var = var;
	}
	
	

	public ArrayList<B4Var> getVars() {
		return vars;
	}

	public B4Expr getExpr() {
		return expr;
	}

	@Override
	public ExprType getType() {
		return ExprType.LAMB;
		
	}
	
}
