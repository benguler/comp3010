import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class B1Functions {
	public B1Functions() {
		
	}
	
	public B1Expr plug(Context context, B1Expr expr) {
		switch(context.getContextType()) {
			case EMPTY:
				return expr;									//[] x e -> e
				
			case APP:
				ArrayList<B1Expr> appExprs = context.getAppContext();
				
				for(int i = 0; i < appExprs.size(); i++) {
					switch(appExprs.get(i).getExprType()) {
						case CON:								//Case found []
							appExprs.set(i, expr);				//Plug [] in app
							
							return new B1App(appExprs);			//(e' ... [] ... e'') x e -> (e' ... e ... e'') 
					
						default:
							break;
						
					}
					
				}
				
			case IF:
				B1Expr[] ifExprs = context.getIfContext();
				
				for(int i = 0; i < 3; i++) {
					switch(ifExprs[i].getExprType()) {
						case CON:							//Case found []
							ifExprs[i] = expr;				//Plug [] in if statement
							
							return new B1If(ifExprs[0], ifExprs[1], ifExprs[2]);	//(if [] e' e'') x e -> (if e e' e'')
					
						default:
							break;
					
					}
					
					
				}
				
			default:
				return null;
		
		}
		
	}
	
	public B1Expr[] findRedex(B1Expr expr){
		B1Expr[] pair = new B1Expr[2];	//[context, redex]
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
							
						default:	//If expr is not a val, it is reducible
							hasRedEx = true;
							
							pair[1] = ifExpr.getExpr(i);
							
							if(i == 0) {		//1st expr is reducible
								pair[0] = new Context(new Context(), ifExpr.getExpr(1), ifExpr.getExpr(2), 0);
								
							}else if(i == 1) {	//2nd expr is reducible
								pair[0] = new Context(new Context(), ifExpr.getExpr(0), ifExpr.getExpr(2), 1);
								
							}else {				//3rd expr is reducible
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
					
					default:	//If expr is not a val, it is reducible
						pair[1] = appExprs.get(i);
						
						newExprs1 = new ArrayList<B1Expr>(appExprs.subList(0, i-1));	//exprs before redex
						
						if(i != appExprs.size()-1) {	//If there are exprs after redex
							newExprs2 =  new ArrayList<B1Expr> (appExprs.subList(i+1, appExprs.size()-1));	//exprs before redex
							
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
	
	//Small step interpreter
	public B1Expr smallStep(B1Expr expr) {
		B1Expr[] pair = findRedex(expr);		//Find redex
		
		if(pair == null) {						//If there is no redex in expr
			return new B1Val(expr.interp());	//Return interpreted expr
			
		}	//else
		
		return plug((Context)pair[0], smallStep(pair[1]));	//retun expression after step
		
	}
	
	//Emits B1 programs written in java to B1 programs written in c
	
	public void emit(B1Expr expr) {
		try {
		      File myObj = new File("filename.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  }
	
	}

}
