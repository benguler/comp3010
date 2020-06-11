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
		if(lhs.getType() == type.ATOM) {
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
		if(lhs.getType() == type.ATOM) {
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
	public B2Expr desugarB2Expr() {
		if(lhs.getType() == type.ATOM) {
			String atom = ((Atom) lhs).getAtom();
			
			if(atom == "if") {
				Cons cons = (Cons)rhs;
				Cons cons2 = (Cons)cons.getRhs();
				
				return new B2If(cons.getLhs().desugarB2Expr(), cons2.getLhs().desugarB2Expr(), cons2.getRhs().desugarB2Expr());	//cons(if, cons(e, cons(e, e))) 
				
			} else if(atom == "+" || atom == "*" || atom == "/" || atom == "-" 
					|| atom == ">" || atom == ">=" || atom == "=" || atom == "<"
					|| atom == "<=") {
				
				Cons cons = (Cons)rhs;
				ArrayList<B2Expr> params = new ArrayList<B2Expr>();
				
				params.add(new B2Val(new B2Prim(atom)));
				params.add(cons.getLhs().desugarB2Expr());
				params.add(cons.getRhs().desugarB2Expr());
				
				return new B2App(params);	//cons(*, cons(e, e)) 
				
			} else if (atom == atom.toUpperCase()) {
				
				ArrayList<B2Expr> params = new ArrayList<B2Expr>();
				
				params.add(lhs.desugarB2Expr());
				
				if(rhs.getType() == type.EMPTY) {
					
					
				}else if(rhs.getType() == type.ATOM) {
					params.add(rhs.desugarB2Expr());
				
				}else {
					Cons cons = (Cons)rhs;
					
					Atom subAtom2 = (Atom)cons.getLhs();
					
					if(subAtom2.getAtom().charAt(0) != '0' && subAtom2.getAtom().charAt(0) != '1' && subAtom2.getAtom().charAt(0) != '2' && 
					   subAtom2.getAtom().charAt(0) != '3' && subAtom2.getAtom().charAt(0) != '4' && subAtom2.getAtom().charAt(0) != '5' && 
					   subAtom2.getAtom().charAt(0) != '6' && subAtom2.getAtom().charAt(0) != '7' && subAtom2.getAtom().charAt(0) != '8' && 
					   subAtom2.getAtom().charAt(0) != '9' && subAtom2.getAtom() != "true"        && subAtom2.getAtom() != "false") {
						
						params.add(cons.desugarB2Expr());
						
					}else {
					
						while(true) {
							params.add(cons.lhs.desugarB2Expr());
							
							if(cons.rhs.getType() == type.ATOM) {
								params.add(cons.rhs.desugarB2Expr());
								break;
								
							}else if(cons.rhs.getType() == type.CONS) {
								Cons subCons = (Cons)cons.rhs;
								
								if(subCons.getLhs().getType() == type.ATOM) {
									Atom subAtom1 = (Atom)subCons.getLhs();
									
									if(subAtom1.getAtom().charAt(0) != '0' && subAtom1.getAtom().charAt(0) != '1' && subAtom1.getAtom().charAt(0) != '2' && 
									   subAtom1.getAtom().charAt(0) != '3' && subAtom1.getAtom().charAt(0) != '4' && subAtom1.getAtom().charAt(0) != '5' && 
									   subAtom1.getAtom().charAt(0) != '6' && subAtom1.getAtom().charAt(0) != '7' && subAtom1.getAtom().charAt(0) != '8' && 
									   subAtom1.getAtom().charAt(0) != '9' && subAtom1.getAtom() != "true"        && subAtom1.getAtom() != "false") {
										
										params.add(cons.rhs.desugarB2Expr());
										break;
										
									}
									
								}
								
							}
							
							cons = (Cons)cons.rhs;
							
						}
					
					}
					
				}
				
				return new B2App(params);
				
			}
			
		}
		
		return null;
	}

	//desugar for b2 function definitions
	@Override
	public B2Def desugarB2Def() {
		Cons cons = (Cons)rhs;
		
		ArrayList<B2Var> vars = new ArrayList<B2Var>();
		
		B2Func func = new B2Func(((Atom) cons.lhs).getAtom());
		
		cons = (Cons)cons.rhs;
		
		B2Expr expr = (B2Expr)cons.rhs.desugarB2Expr(); 
		
		if(cons.lhs.getType() == type.ATOM) {
			vars.add((B2Var)cons.lhs.desugarB2Expr());
			
		}else if (cons.lhs.getType() == type.CONS) {
			
			cons = (Cons)cons.lhs;
			
			while(true){
				
				vars.add((B2Var)cons.lhs.desugarB2Expr());
				
				if(cons.rhs.getType() == type.ATOM) {
					vars.add((B2Var)cons.rhs.desugarB2Expr());
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
		return type.CONS;
		
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
