import java.util.ArrayList;

public class B5Lambda implements B5Expr{

	private ArrayList<B5Var> vars;
	private B5Expr expr;
	private B5Var recName;
	
	public B5Lambda(B5Var recName, ArrayList<B5Var> vars, B5Expr expr) {
		super();
		this.vars = vars;
		this.expr = expr;
		this.recName = recName;
	}
	
	public B5Lambda(B5Var recName, B5Var var0, B5Expr expr) {
		super();
		this.vars = new ArrayList<B5Var>();
		this.vars.add(var0);
		this.expr = expr;
		this.recName = recName;
	}
	
	

	public ArrayList<B5Var> getVars() {
		return vars;
	}

	public B5Expr getExpr() {
		return expr;
	}

	@Override
	public ExprType getType() {
		return ExprType.LAMB;
		
	}

	public B5Var getRecName() {
		return recName;
	}
	
}
