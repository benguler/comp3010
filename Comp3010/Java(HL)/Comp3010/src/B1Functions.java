import java.util.ArrayList;

public class B1Functions {
	public B1Functions() {
		
	}
	
	public B1Expr plug(Context context, B1Expr expr) {
		switch(context.getContextType()) {
			case EMPTY:
				return expr;
				
			case APP:
				ArrayList<B1Expr> appExprs = context.getAppContext();
				
				for(int i = 0; i < appExprs.size(); i++) {
					switch(appExprs.get(i).getExprType()) {
						case CON:
							appExprs.set(i, expr);
							
							return new B1App(appExprs);
					
						default:
							break;
						
					}
					
				}
				
			case IF:
				B1Expr[] ifExprs = context.getIfContext();
				
				for(int i = 0; i < 3; i++) {
					switch(ifExprs[i].getExprType()) {
						case CON:
							ifExprs[i] = expr;
							
							return new B1If(ifExprs[0], ifExprs[1], ifExprs[2]);
					
						default:
							break;
					
					}
					
					
				}
				
			default:
				return null;
		
		}
		
	}
	
	public B1Expr[] findRedex(B1Expr expr){
		B1Expr[] pair = new B1Expr[2];
		boolean hasRedEx = false;
		
		switch(expr.getExprType()) {
			case IF:
				B1If ifExpr = (B1If)expr;
				
				for(int i = 0; i < 3; i++) {
					if(hasRedEx) {
						break;
						
					}
					
					switch(ifExpr.getExpr(i).getExprType()) {
						case VAL:
							break;
							
						default:
							hasRedEx = true;
							
							pair[1] = ifExpr.getExpr(i);
							
							if(i == 0) {
								pair[0] = new Context(new Context(), ifExpr.getExpr(1), ifExpr.getExpr(2), 0);
								
							}else if(i == 1) {
								pair[0] = new Context(new Context(), ifExpr.getExpr(0), ifExpr.getExpr(2), 1);
								
							}else {
								pair[0] = new Context(new Context(), ifExpr.getExpr(0), ifExpr.getExpr(1), 2);
								
							}
							
							break;
							
					}
					
				}
				
				break;
				
			case APP:
				B1App appExpr = (B1App)expr;
				
				ArrayList<B1Expr> appExprs = appExpr.getExprs();
				ArrayList<B1Expr> newExprs1 = new ArrayList<B1Expr>();
				ArrayList<B1Expr> newExprs2 = new ArrayList<B1Expr>();
				
				for(int i = 0; i < appExprs.size(); i++) {
					if(hasRedEx) {
						break;
						
					}
					
					switch(appExprs.get(i).getExprType()) {
					case VAL:
						break;
					
					default:
						pair[1] = appExprs.get(i);
						
						if(i != 0) {
							newExprs1 = new ArrayList<B1Expr>(appExprs.subList(0, i-1));
							
						}
						
						if(i != appExprs.size()-1) {
							newExprs2 =  new ArrayList<B1Expr> (appExprs.subList(i+1, appExprs.size()-1));
							
						}
						
						pair[0] = new Context(newExprs1, new Context(), newExprs2);
						
						break;
						
					}
					
				}
				
				break;
				
			default:
				return null;
		
		}
		
		return hasRedEx? pair:null;
		
	}
	
	public B1Expr smallStep(B1Expr expr) {
		B1Expr[] pair = findRedex(expr);
		
		if(pair == null) {
			return new B1Val(expr.interp());
			
		}
		
		return plug((Context)pair[0], smallStep(pair[1]));
		
	}

}
