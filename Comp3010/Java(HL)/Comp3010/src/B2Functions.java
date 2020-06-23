import java.util.ArrayList;

public class B2Functions {
	
	public B2Functions() {
		
	}
	
	public int bigStep(B2Expr expr, VarMap vm, FuncMap fm) {
		switch(expr.getType()) {
			case IF:
				B2If ifExpr = (B2If) expr;
				
				if(bigStep(ifExpr.getExpr(0), vm, fm) == 0) {
					return bigStep(ifExpr.getExpr(2), vm, fm);
					
				}else {
					return bigStep(ifExpr.getExpr(1), vm, fm);
					
				}
				
			case APP:		
				B2App appExpr = (B2App) expr;
				
				ArrayList<B2Expr> dExprs = new ArrayList<B2Expr>();
				
				for (int i = 1; i < appExpr.getExprs().size(); i++) {
					dExprs.add(appExpr.getExprs().get(i));
					
				}
				
				return delta(appExpr.getExprs().get(0), dExprs, vm, fm);
				
			case VAL:
				B2Val valExpr = (B2Val) expr;
				
				switch(valExpr.getValType()) {
					case NUM:
						return valExpr.getNum();
						
					case BOOL:
						return valExpr.getBool()? 1:0;
				
					default:
						return 0;
				
				}
				
			case VAR:
				B2Var varExpr = (B2Var) expr;
				
				return bigStep(vm.getVal(varExpr), vm, fm);
				
			default:
				break;
		
		}
		
		return 0;
		
	}
	
	public void define(B2Def def, FuncMap fm) {
		fm.setFunc(def.getFunc(), def);
		
	}
	
	public int delta(B2Expr op, ArrayList<B2Expr> exprs, VarMap vm, FuncMap fm) {
		
		B2Val valExpr = (B2Val)op;
		
		switch(valExpr.getValType()) {
			case PRIM:
				switch(valExpr.getPrim().getPrimType()) {
					case "+":
						return bigStep(exprs.get(0), vm, fm) + bigStep(exprs.get(1), vm, fm);
						
					case "*":
						return bigStep(exprs.get(0), vm, fm) * bigStep(exprs.get(1), vm, fm);
						
					case "/":
						return bigStep(exprs.get(0), vm, fm) / bigStep(exprs.get(1), vm, fm);
						
					case "-":
						return bigStep(exprs.get(0), vm, fm) - bigStep(exprs.get(1), vm, fm);
						
					case "<=":
						if(bigStep(exprs.get(0), vm, fm) <= bigStep(exprs.get(1), vm, fm)) {
							return 1;
						}
						
						return 0;
					case "<":
						if(bigStep(exprs.get(0), vm, fm) < bigStep(exprs.get(1), vm, fm)) {
							return 1;
						}
						
						return 0;
					case "=":
						if(bigStep(exprs.get(0), vm, fm) == bigStep(exprs.get(1), vm, fm)) {
							return 1;
						}
						
						return 0;
						
					case ">":
						if(bigStep(exprs.get(0), vm, fm) > bigStep(exprs.get(1), vm, fm)) {
							return 1;
						}
						
						return 0;
						
					case ">=":
						if(bigStep(exprs.get(0), vm, fm) >= bigStep(exprs.get(1), vm, fm)) {
							return 1;
						}
						
						return 0;
		
					default:
						return 0;	//Error
						
				}
				
			case FUNC:
				
				B2Func funcExpr = valExpr.getFunc();
				
				B2Def def = fm.getDef(funcExpr);
				
				VarMap vmNew = new VarMap();	//Pass a new VarMap to maintain scope
				
				for(int i = 0; i < exprs.size(); i ++) {
					vmNew.setVar(def.getVars().get(i), new B2Val(bigStep(exprs.get(i), vm, fm)));
					
				}
				
				return bigStep(def.getExpr(), vmNew, fm);
				
			default:
				break;
				
		}
		
		return 0;
		
	}
	
}
