import java.util.ArrayList;

public class B1App implements B1Expr{
	private ArrayList<B1Expr> exprs;
	
	public B1App(ArrayList<B1Expr> exprs) {
		this.exprs = exprs;
		
	}

	@Override
	public int interp() {
		B1Prim prim = (B1Prim)exprs.get(0);
		
		switch(prim.getPrimType()) {
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
				
			case "=>":
				if(exprs.get(1).interp() >= exprs.get(2).interp()) {
					return 1;
				}
				
				return 0;

			default:
				return (Integer) null;
				
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

}
