import java.util.ArrayList;

public class Context implements B1Expr{
	
	public enum ContextType{
		EMPTY,
		APP,
		IF
		
	}
	
	private ContextType contextType;
	
	private B1Expr[] ifContext = new B1Expr[3];
	
	private ArrayList<B1Expr> appContext = new ArrayList<B1Expr> ();
	
	public Context() {
		this.contextType = ContextType.EMPTY;
		
	}
	
	public Context(ArrayList<B1Expr> exprs1, Context context, ArrayList<B1Expr> exprs2) {	//(e ... [] ... e)
		this.contextType = ContextType.APP;
		
		ArrayList<B1Expr> exprs = new ArrayList<B1Expr> ();
		
		for(B1Expr e: exprs1) {
			this.appContext.add(e);		//e ...
			
		}
		
		this.appContext.add(context);	//[]
		
		for(B1Expr e: exprs2) {
			this.appContext.add(e);		//... e
			
		}
		
	}
	
	public Context(Context context, B1Expr expr1, B1Expr expr2, int intType) {
		this.contextType = ContextType.IF;
		
		switch(intType) {
			case 0:	//(if [] e e)
				this.ifContext[0] = context;
				this.ifContext[1] = expr1;
				this.ifContext[2] = expr2;
				break;
				
			case 1:	//(if e [] e)
				this.ifContext[1] = context;
				this.ifContext[0] = expr1;
				this.ifContext[2] = expr2;
				break;
				
			case 2:	//(if e e [])
				this.ifContext[2] = context;
				this.ifContext[0] = expr1;
				this.ifContext[1] = expr2;
				break;
				
			default:
				break;
				
		}
		
	}
	
	

	public ContextType getContextType() {
		return contextType;
	}

	public void setContextType(ContextType contextType) {
		this.contextType = contextType;
	}

	public B1Expr[] getIfContext() {
		return ifContext;
	}

	public void setIfContext(B1Expr[] ifContext) {
		this.ifContext = ifContext;
	}

	public ArrayList<B1Expr> getAppContext() {
		return appContext;
	}

	public void setAppContext(ArrayList<B1Expr> appContext) {
		this.appContext = appContext;
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
