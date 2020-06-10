import java.util.ArrayList;

public class Cons implements BSexpr{
	private BSexpr lhs;
	private BSexpr rhs;
	
	public Cons(BSexpr lhs, BSexpr rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
		
	}

	//desugar for b0
	@Override
	public BExpr desugar() {
		if(lhs.getType() == type.Atom) {
			Atom atom = (Atom)lhs;
			Cons cons = (Cons)rhs;
			
			//When the first element of the cons is an operator
			//The second element of the cons is another cons containing params
			
			if(atom.getAtom() == "*") {
				return new BMult(cons.getLhs().desugar(), cons.getRhs().desugar());
				
			}else if(atom.getAtom() == "+") {
				return new BAdd(cons.getLhs().desugar(), cons.getRhs().desugar());
				
			}
			
		}
		
		return null;
		
	}
	
	//desugar for b1
	@Override
	public B1Expr desugarB1() {
		if(lhs.getType() == type.Atom) {
			String atom = ((Atom) lhs).getAtom();
			Cons cons = (Cons)rhs;
			
			if(atom == "if") {
				Cons cons2 = (Cons)cons.getRhs();
				
				return new B1If(cons.getLhs().desugarB1(), cons2.getLhs().desugarB1(), cons2.getRhs().desugarB1());	//cons(if, cons(e, cons(e, e))) 
				
			}
			
			ArrayList<B1Expr> params = new ArrayList<B1Expr>();
			
			params.add(new B1Val(new B1Prim(atom)));
			params.add(cons.getLhs().desugarB1());
			params.add(cons.getRhs().desugarB1());
			
			return new B1App(params);	//cons(*, cons(e, e)) 
			
		}
		
		return null;
		
	}
	
	//desugar for b2 exprs
	@Override
	public B2Expr desugarExprB2() {
		if(lhs.getType() == type.Atom) {
			String atom = ((Atom) lhs).getAtom();
			
			if(atom == "if") {
				Cons cons = (Cons)rhs;
				Cons cons2 = (Cons)cons.getRhs();
				
				return new B2If(cons.getLhs().desugarExprB2(), cons2.getLhs().desugarExprB2(), cons2.getRhs().desugarExprB2());	//cons(if, cons(e, cons(e, e))) 
				
			} else if(atom == "+" || atom == "*" || atom == "/" || atom == "-" 
					|| atom == ">" || atom == ">=" || atom == "=" || atom == "<"
					|| atom == "<=") {
				
				Cons cons = (Cons)rhs;
				ArrayList<B2Expr> params = new ArrayList<B2Expr>();
				
				params.add(new B2Val(new B2Prim(atom)));
				params.add(cons.getLhs().desugarExprB2());
				params.add(cons.getRhs().desugarExprB2());
				
				return new B2App(params);	//cons(*, cons(e, e)) 
				
			} else if (atom == atom.toUpperCase()) {
				ArrayList<B2Expr> params = new ArrayList<B2Expr>();
				
				params.add(new B2Func(atom));
				params.add(rhs.desugarExprB2());
				
				return new B2App(params);
				
			}
			
		}
		
		return null;
	}

	//desugar for b2 function definitions
	@Override
	public B2Def desugarDefB2() {
		ArrayList<B2Var> vars = new ArrayList<B2Var>();
		
		B2Func func = (B2Func)lhs.desugarExprB2();
		
		Cons cons = (Cons)rhs;
		
		B2Expr expr = (B2Expr)cons.rhs.desugarExprB2(); 
		
		if(cons.lhs.getType() == type.Atom) {
			vars.add((B2Var)cons.lhs.desugarExprB2());
			
		}else if (cons.lhs.getType() == type.Cons) {
			
			cons = (Cons)cons.lhs;
			
			while(true){
				
				vars.add((B2Var)cons.lhs.desugarExprB2());
				
				if(cons.rhs.getType() == type.Atom) {
					vars.add((B2Var)cons.rhs.desugarExprB2());
					break;
					
				}
				
				cons = (Cons)cons.rhs;
				
			}
		
		}
		
		return new B2Def(func, vars, expr);
	}
	
	@Override
	public Pair desugarB2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public type getType() {
		return type.Cons;
		
	}

	public BSexpr getLhs() {
		return lhs;
		
	}

	public void setLhs(BSexpr lhs) {
		this.lhs = lhs;
		
	}

	public BSexpr getRhs() {
		return rhs;
		
	}

	public void setRhs(BSexpr rhs) {
		this.rhs = rhs;
		
	}
	
}
