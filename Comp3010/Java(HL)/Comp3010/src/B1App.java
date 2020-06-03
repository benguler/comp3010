import java.util.ArrayList;

public class B1App implements B1Expr{
	private ArrayList<B1Expr> exprs;
	
	public B1App(ArrayList<B1Expr> exprs) {	//(e ... e)
		this.exprs = exprs;
		
	}

	@Override
	public int interp() {
		B1Val prim = (B1Val)exprs.get(0);	//First expr in an app contains a prim
		
		switch(prim.pPrint()) {
			case "+":
				return exprs.get(1).interp() + exprs.get(2).interp();
				
			case "*":
				return exprs.get(1).interp() * exprs.get(2).interp();
				
			case "/":
				return exprs.get(1).interp() / exprs.get(2).interp();
				
			case "-":
				return exprs.get(1).interp() - exprs.get(2).interp();
				
			case "<=":
				if(exprs.get(1).interp() <= exprs.get(2).interp()) {
					return 1;
				}
				
				return 0;
			case "<":
				if(exprs.get(1).interp() < exprs.get(2).interp()) {
					return 1;
				}
				
				return 0;
			case "=":
				if(exprs.get(1).interp() == exprs.get(2).interp()) {
					return 1;
				}
				
				return 0;
				
			case ">":
				if(exprs.get(1).interp() > exprs.get(2).interp()) {
					return 1;
				}
				
				return 0;
				
			case ">=":
				if(exprs.get(1).interp() >= exprs.get(2).interp()) {
					return 1;
				}
				
				return 0;

			default:
				return (Integer) null;	//Error
				
		}
		
	}

	@Override
	public String pPrint() {
		String msg = "(";
		
		for(B1Expr expr : exprs) {
			msg +=expr.pPrint() + " ";
			
		}
		
		msg += ")";
		
		return msg;
		
	}

	@Override
	public ExprType getExprType() {
		return ExprType.APP;
		
	}

	public ArrayList<B1Expr> getExprs() {
		return exprs;
		
	}

}
