import java.util.ArrayList;

public class Context implements B1Expr{
	
	public enum ContextType{
		EMPTY,
		APP,
		IF
		
	}
	
	private ContextType contextType;
	
	private Context ifContext;
	private B1Expr ifExpr1;
	private B1Expr ifExpr2;
	private int ifType;
	
	private ArrayList<B1Expr> appExprs1 = new ArrayList<B1Expr> ();
	private Context appContext;
	private ArrayList<B1Expr> appExprs2 = new ArrayList<B1Expr> ();
	
	
	public Context() {
		this.contextType = ContextType.EMPTY;
		
	}
	
	public Context(ArrayList<B1Expr> exprs1, Context context, ArrayList<B1Expr> exprs2) {	//(e ... [] ... e)
		this.contextType = ContextType.APP;
		
		this.appExprs1 = exprs1;
		this.appContext = context;
		this.appExprs2 = exprs2;
		
	}
	
	public Context(Context context, B1Expr expr1, B1Expr expr2, int ifType) {
		this.contextType = ContextType.IF;
		
		this.ifContext = context;
		this.ifExpr1 = expr1;
		this.ifExpr2 = expr2;
		
	}
	
	

	public ContextType getContextType() {
		return contextType;
	}

	public void setContextType(ContextType contextType) {
		this.contextType = contextType;
	}
	

	public ArrayList<B1Expr> getAppExprs1() {
		return appExprs1;
	}

	public Context getAppContext() {
		return appContext;
	}

	public ArrayList<B1Expr> getAppExprs2() {
		return appExprs2;
	}
	
	

	public Context getIfContext() {
		return ifContext;
		
	}

	public B1Expr getIfExpr1() {
		return ifExpr1;
		
	}

	public B1Expr getIfExpr2() {
		return ifExpr2;
		
	}

	public int getIfType() {
		return ifType;
		
	}

	@Override
	public int interp() {		//Never call
		return 0;
		
	}

	@Override
	public String pPrint() {	//Never call
		return null;
		
	}

	@Override
	public ExprType getExprType() {
		return ExprType.CON;
		
	}
	
}
