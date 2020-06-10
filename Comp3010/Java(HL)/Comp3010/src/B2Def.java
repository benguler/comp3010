import java.util.ArrayList;

public class B2Def{

	private B2Func func;
	private ArrayList<B2Var> vars;
	private B2Expr expr;
	
	public B2Def(B2Func func, ArrayList<B2Var> vars, B2Expr expr) {
		this.func = func;
		this.vars = vars;
		this.expr = expr;
		
	}

	public B2Func getFunc() {
		return func;
		
	}

	public ArrayList<B2Var> getVars() {
		return vars;
	}

	public B2Expr getExpr() {
		return expr;
	}
	
}
