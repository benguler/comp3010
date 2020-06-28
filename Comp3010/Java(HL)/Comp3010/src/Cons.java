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
				
				switch(rhs.getType()) {
					case EMPTY:
						break;
						
	
					case ATOM:
						params.add(rhs.desugarB2Expr());
						break;
					
					case CONS:
						Cons cons = (Cons)rhs;
						
						boolean keepGoing = true;
						
						while(keepGoing) {
							//Parse left hand side of cons
							switch(cons.getLhs().getType()) {
								case ATOM:
									Atom subAtom1 = (Atom)cons.getLhs();
									
									if(subAtom1.getAtom().charAt(0) != '0' && subAtom1.getAtom().charAt(0) != '1' && subAtom1.getAtom().charAt(0) != '2' && 
									   subAtom1.getAtom().charAt(0) != '3' && subAtom1.getAtom().charAt(0) != '4' && subAtom1.getAtom().charAt(0) != '5' && 
									   subAtom1.getAtom().charAt(0) != '6' && subAtom1.getAtom().charAt(0) != '7' && subAtom1.getAtom().charAt(0) != '8' && 
									   subAtom1.getAtom().charAt(0) != '9' && subAtom1.getAtom() != "true"        && subAtom1.getAtom() != "false") {
										
										params.add(cons.desugarB2Expr());
										keepGoing = false;
										
									}else {
										params.add(cons.getLhs().desugarB2Expr());
										
									}
									
									break;
									
								case CONS:
									params.add(cons.getLhs().desugarB2Expr());
									
									break;
									
								default:
									keepGoing = false;
									
									break;
									
							}
							
							if(!keepGoing) {
								break;
								
							}
							
							//Parse right hand side of cons
							switch(cons.getRhs().getType()) {
								case ATOM:
									params.add(cons.getRhs().desugarB2Expr());
									keepGoing = false;
									break;
									
								case CONS:
									Cons subCons = (Cons)cons.getRhs();
									
									if(subCons.getLhs().getType() == type.ATOM) {
										Atom subAtom2 = (Atom)subCons.getLhs();
										
										if(subAtom2.getAtom().charAt(0) != '0' && subAtom2.getAtom().charAt(0) != '1' && subAtom2.getAtom().charAt(0) != '2' && 
										   subAtom2.getAtom().charAt(0) != '3' && subAtom2.getAtom().charAt(0) != '4' && subAtom2.getAtom().charAt(0) != '5' && 
										   subAtom2.getAtom().charAt(0) != '6' && subAtom2.getAtom().charAt(0) != '7' && subAtom2.getAtom().charAt(0) != '8' && 
										   subAtom2.getAtom().charAt(0) != '9' && subAtom2.getAtom() != "true"        && subAtom2.getAtom() != "false") {
											params.add(cons.getRhs().desugarB2Expr());
											keepGoing = false;
											
										}
										
									}
									
									break;
									
								default:
									keepGoing = false;
									
									break;
								
							}
							
							if(keepGoing) {
								cons = (Cons)cons.getRhs();
								
							}
						
						}
						
						break;
					
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
		
		B2Func func = new B2Func(((Atom) cons.getLhs()).getAtom());
		
		cons = (Cons)cons.getRhs();
		
		B2Expr expr = (B2Expr)cons.getRhs().desugarB2Expr(); 
		
		if(cons.getLhs().getType() == type.ATOM) {
			vars.add((B2Var)cons.getLhs().desugarB2Expr());
			
		}else if (cons.getLhs().getType() == type.CONS) {
			
			cons = (Cons)cons.getLhs();
			
			while(true){
				
				vars.add((B2Var)cons.getLhs().desugarB2Expr());
				
				if(cons.getRhs().getType() == type.ATOM) {
					vars.add((B2Var)cons.getRhs().desugarB2Expr());
					break;
					
				}
				
				cons = (Cons)cons.getRhs();
				
			}
		
		}
		
		return new B2Def(func, vars, expr);
		
	}
	
	//desugar for b3 exprs
		
	@Override
	public B3Expr desugarB3Expr() {
		switch(lhs.getType()) {
		
			case ATOM:
				String atom = ((Atom) lhs).getAtom();
				
				if(atom == "if") {
					Cons cons = (Cons)rhs;
					Cons cons2 = (Cons)cons.getRhs();
					
					return new B3If(cons.getLhs().desugarB3Expr(), cons2.getLhs().desugarB3Expr(), cons2.getRhs().desugarB3Expr());	//cons(if, cons(e, cons(e, e))) 
				
				}else if(atom == "lambda") {
					Cons cons = (Cons)rhs;
					ArrayList<B3Var> vars = new ArrayList<B3Var>();
					B3Expr expr = cons.getRhs().desugarB3Expr();
					
					boolean keepGoing = true;
					
					switch(cons.getLhs().getType()) {
						case ATOM:
							vars.add((B3Var)(cons.getLhs().desugarB3Expr()));
							keepGoing = false;
							break;
							
						case CONS:
							cons = (Cons)cons.getLhs();
							break;
							
						case EMPTY:
							keepGoing = false;
							break;
							
						default:
							break;
							
					}
					
					while(keepGoing) {
						vars.add((B3Var)(cons.getLhs().desugarB3Expr()));
						
						switch(cons.getRhs().getType()) {
							case ATOM:
								vars.add((B3Var)(cons.getRhs().desugarB3Expr()));
								keepGoing = false;
								break;
								
							case CONS:
								cons = (Cons)cons.getRhs();
								break;
								
							case EMPTY:
								keepGoing = false;
								break;
								
							default:
								break;
								
						}
					
					}
					
					return new B3Val(new B3Lambda(vars, expr));
					
					
				}else if(atom == "+" || atom == "*" || atom == "/" || atom == "-" 
						|| atom == ">" || atom == ">=" || atom == "=" || atom == "<"
						|| atom == "<=") {
					
					Cons cons = (Cons)rhs;
					ArrayList<B3Expr> params = new ArrayList<B3Expr>();
					
					params.add(new B3Val(new B3Prim(atom)));
					params.add(cons.getLhs().desugarB3Expr());
					params.add(cons.getRhs().desugarB3Expr());
					
					return new B3App(params);	//cons(*, cons(e, e)) 
					
				}else if (atom == "let") {
					Cons cons = (Cons)rhs;
					ArrayList<B3Expr> params = new ArrayList<B3Expr>();
					B3Expr lambdaExpr = cons.getRhs().desugarB3Expr();
					
					Cons subCons = (Cons)cons.getLhs();
					B3Var lamdaVar = (B3Var)(subCons.getLhs().desugarB3Expr());
					B3Expr varVal = (B3Expr)(subCons.getRhs().desugarB3Expr());
					
					params.add(new B3Val(new B3Lambda(lamdaVar, lambdaExpr)));
					params.add(varVal);
					
					return new B3App(params);
					

				}
				
			case CONS:	//Lambda app
				ArrayList<B3Expr> params = new ArrayList<B3Expr>();
				
				params.add(lhs.desugarB3Expr());
				
				switch(rhs.getType()) {
					case EMPTY:
						break;
						
	
					case ATOM:
						params.add(rhs.desugarB3Expr());
						break;
					
					case CONS:
						Cons cons = (Cons)rhs;
						
						boolean keepGoing = true;
						
						while(keepGoing) {
							//Parse left hand side of cons
							switch(cons.getLhs().getType()) {
								case ATOM:
									Atom subAtom1 = (Atom)cons.getLhs();
									
									if(subAtom1.getAtom().charAt(0) != '0' && subAtom1.getAtom().charAt(0) != '1' && subAtom1.getAtom().charAt(0) != '2' && 
									   subAtom1.getAtom().charAt(0) != '3' && subAtom1.getAtom().charAt(0) != '4' && subAtom1.getAtom().charAt(0) != '5' && 
									   subAtom1.getAtom().charAt(0) != '6' && subAtom1.getAtom().charAt(0) != '7' && subAtom1.getAtom().charAt(0) != '8' && 
									   subAtom1.getAtom().charAt(0) != '9' && subAtom1.getAtom() != "true"        && subAtom1.getAtom() != "false") {
										
										params.add(cons.desugarB3Expr());
										keepGoing = false;
										
									}else {
										params.add(cons.getLhs().desugarB3Expr());
										
									}
									
									break;
									
								case CONS:
									params.add(cons.getLhs().desugarB3Expr());
									
									break;
									
								default:
									keepGoing = false;
									
									break;
									
							}
							
							if(!keepGoing) {
								break;
								
							}
							
							//Parse right hand side of cons
							switch(cons.getRhs().getType()) {
								case ATOM:
									params.add(cons.getRhs().desugarB3Expr());
									
									keepGoing = false;
									break;
									
								case CONS:
									Cons subCons = (Cons)cons.getRhs();
									
									if(subCons.getLhs().getType() == type.ATOM) {
										Atom subAtom2 = (Atom)subCons.getLhs();
										
										if(subAtom2.getAtom().charAt(0) != '0' && subAtom2.getAtom().charAt(0) != '1' && subAtom2.getAtom().charAt(0) != '2' && 
										   subAtom2.getAtom().charAt(0) != '3' && subAtom2.getAtom().charAt(0) != '4' && subAtom2.getAtom().charAt(0) != '5' && 
										   subAtom2.getAtom().charAt(0) != '6' && subAtom2.getAtom().charAt(0) != '7' && subAtom2.getAtom().charAt(0) != '8' && 
										   subAtom2.getAtom().charAt(0) != '9' && subAtom2.getAtom() != "true"        && subAtom2.getAtom() != "false") {
											params.add(cons.getRhs().desugarB3Expr());
											keepGoing = false;
											
										}
										
									}
									
									break;
									
								default:
									keepGoing = false;
									
									break;
								
							}
							
							if(keepGoing) {
								cons = (Cons)cons.getRhs();
								
							}
						
						
						}
						
						break;
					
				}
				
				return new B3App(params);
				
			default:
				break;
			
		}
		
		return null;
			
	}
	
	@Override
	public B4Expr desugarB4Expr() {
		switch(lhs.getType()) {
		
		case ATOM:
			String atom = ((Atom) lhs).getAtom();
			
			if(atom == "if") {
				Cons cons = (Cons)rhs;
				Cons cons2 = (Cons)cons.getRhs();
				
				return new B4If(cons.getLhs().desugarB4Expr(), cons2.getLhs().desugarB4Expr(), cons2.getRhs().desugarB4Expr());	//cons(if, cons(e, cons(e, e))) 
			
			}else if(atom == "lambda") {
				Cons cons = (Cons)rhs;
				ArrayList<B4Var> vars = new ArrayList<B4Var>();
				B4Expr expr = null;
				B4Var recName = null;
				
				boolean keepGoing = true;
				
				switch(cons.getLhs().getType()) {
					case ATOM:
						recName = ((B4Var)(cons.getLhs().desugarB4Expr()));
						break;
						
					case CONS:
						return null;
						
					case EMPTY:
						recName = new B4Var("rec");
						break;
						
					default:
						return null;
					
				}
				
				cons = (Cons)cons.getRhs();
				
				expr = cons.getRhs().desugarB4Expr();
				
				switch(cons.getLhs().getType()) {
					case ATOM:
						vars.add((B4Var)(cons.getLhs().desugarB4Expr()));
						keepGoing = false;
						break;
						
					case CONS:
						cons = (Cons)cons.getLhs();
						break;
						
					case EMPTY:
						keepGoing = false;
						break;
						
					default:
						break;
						
				}
				
				while(keepGoing) {
					vars.add((B4Var)(cons.getLhs().desugarB4Expr()));
					
					switch(cons.getRhs().getType()) {
						case ATOM:
							vars.add((B4Var)(cons.getRhs().desugarB4Expr()));
							keepGoing = false;
							break;
							
						case CONS:
							cons = (Cons)cons.getRhs();
							break;
							
						case EMPTY:
							keepGoing = false;
							break;
							
						default:
							break;
							
					}
				
				}
				
				return new B4Val(new B4Lambda(recName, vars, expr));
						
			}else if(atom == "+" || atom == "*" || atom == "/" || atom == "-" 
					|| atom == ">" || atom == ">=" || atom == "=" || atom == "<"
					|| atom == "<=") {
				
				Cons cons = (Cons)rhs;
				ArrayList<B4Expr> params = new ArrayList<B4Expr>();
				
				params.add(new B4Val(new B4Prim(atom)));
				params.add(cons.getLhs().desugarB4Expr());
				params.add(cons.getRhs().desugarB4Expr());
				
				return new B4App(params);	//cons(*, cons(e, e)) 
				
			}else if (atom == "let") {
					Cons cons = (Cons)rhs;
					ArrayList<B4Expr> params = new ArrayList<B4Expr>();
					B4Expr lambdaExpr = cons.getRhs().desugarB4Expr();
					
					Cons subCons = (Cons)cons.getLhs();
					B4Var lamdaVar = (B4Var)(subCons.getLhs().desugarB4Expr());
					B4Expr varVal = (B4Expr)(subCons.getRhs().desugarB4Expr());
					
					params.add(new B4Val(new B4Lambda( new B4Var("rec"), lamdaVar, lambdaExpr)));
					params.add(varVal);
					
					return new B4App(params);
					
			}else if(atom == "call") {	//App with variable that contains a Lambda
				ArrayList<B4Expr> params = new ArrayList<B4Expr>();
				
				Cons cons = (Cons)rhs;
				
				params.add(cons.getLhs().desugarB4Expr());
				
				switch(cons.rhs.getType()) {
					case EMPTY:
						break;
						
	
					case ATOM:
						params.add(cons.rhs.desugarB4Expr());
						break;
					
					case CONS:
						cons = (Cons)cons.rhs;
						
						boolean keepGoing = true;
						
						while(keepGoing) {
							//Parse left hand side of cons
							switch(cons.getLhs().getType()) {
								case ATOM:
									Atom subAtom1 = (Atom)cons.getLhs();
									
									if(subAtom1.getAtom().charAt(0) == '+' || subAtom1.getAtom().charAt(0) == '-' || subAtom1.getAtom().charAt(0) == '*' || 
									   subAtom1.getAtom().charAt(0) == '/' || subAtom1.getAtom().charAt(0) == '>' || subAtom1.getAtom().charAt(0) == '<' || 
									   subAtom1.getAtom().charAt(0) == '=' || subAtom1.getAtom() == "if" 		  || subAtom1.getAtom() == "call") {
										
										params.add(cons.desugarB4Expr());
										keepGoing = false;
										
									}else {
										params.add(cons.getLhs().desugarB4Expr());
										
									}
									
									break;
									
								case CONS:
									params.add(cons.getLhs().desugarB4Expr());
									
									break;
									
								default:
									keepGoing = false;
									
									break;
									
							}
							
							if(!keepGoing) {
								break;
								
							}
							
							//Parse right hand side of cons
							switch(cons.getRhs().getType()) {
								case ATOM:
									params.add(cons.getRhs().desugarB4Expr());
									
									keepGoing = false;
									break;
									
								case CONS:
									Cons subCons = (Cons)cons.getRhs();
									
									if(subCons.getLhs().getType() == type.ATOM) {
										Atom subAtom2 = (Atom)subCons.getLhs();
										
										if(subAtom2.getAtom().charAt(0) == '+' || subAtom2.getAtom().charAt(0) == '-' || subAtom2.getAtom().charAt(0) == '*' || 
										   subAtom2.getAtom().charAt(0) == '/' || subAtom2.getAtom().charAt(0) == '>' || subAtom2.getAtom().charAt(0) == '<' || 
										   subAtom2.getAtom().charAt(0) == '=' || subAtom2.getAtom() == "if"          || subAtom2.getAtom() == "call") {
											params.add(cons.getRhs().desugarB4Expr());
											keepGoing = false;
											
										}
										
									}
									
									break;
									
								default:
									keepGoing = false;
									
									break;
								
							}
							
							if(keepGoing) {
								cons = (Cons)cons.getRhs();
								
							}
						
						
						}
						
						break;
					
				}
				
				return new B4App(params);
				
			}
			
		case CONS:	//Lambda app
			ArrayList<B4Expr> params = new ArrayList<B4Expr>();
			
			params.add(lhs.desugarB4Expr());
			
			switch(rhs.getType()) {
				case EMPTY:
					break;
					

				case ATOM:
					params.add(rhs.desugarB4Expr());
					break;
				
				case CONS:
					Cons cons = (Cons)rhs;
					
					boolean keepGoing = true;
					
					while(keepGoing) {
						//Parse left hand side of cons
						switch(cons.getLhs().getType()) {
							case ATOM:
								Atom subAtom1 = (Atom)cons.getLhs();
								
								if(subAtom1.getAtom().charAt(0) == '+' || subAtom1.getAtom().charAt(0) == '-' || subAtom1.getAtom().charAt(0) == '*' || 
								   subAtom1.getAtom().charAt(0) == '/' || subAtom1.getAtom().charAt(0) == '>' || subAtom1.getAtom().charAt(0) == '<' || 
								   subAtom1.getAtom().charAt(0) == '=' || subAtom1.getAtom() == "if"  		  || subAtom1.getAtom() == "call") {
									
									params.add(cons.desugarB4Expr());
									keepGoing = false;
									
								}else {
									params.add(cons.getLhs().desugarB4Expr());
									
								}
								
								break;
								
							case CONS:
								params.add(cons.getLhs().desugarB4Expr());
								
								break;
								
							default:
								keepGoing = false;
								
								break;
								
						}
						
						if(!keepGoing) {
							break;
							
						}
						//Parse right hand side of cons
						switch(cons.getRhs().getType()) {
							case ATOM:
								params.add(cons.getRhs().desugarB4Expr());
								keepGoing = false;
								break;
								
							case CONS:
								Cons subCons = (Cons)cons.getRhs();
								
								if(subCons.getLhs().getType() == type.ATOM) {
									Atom subAtom2 = (Atom)subCons.getLhs();
									
									if(subAtom2.getAtom().charAt(0) == '+' || subAtom2.getAtom().charAt(0) == '-' || subAtom2.getAtom().charAt(0) == '*' || 
									   subAtom2.getAtom().charAt(0) == '/' || subAtom2.getAtom().charAt(0) == '>' || subAtom2.getAtom().charAt(0) == '<' || 
									   subAtom2.getAtom().charAt(0) == '=' || subAtom2.getAtom() == "if"		  || subAtom2.getAtom() == "call") {
										params.add(cons.getRhs().desugarB4Expr());
										keepGoing = false;
										
									}
									
								}
								
								break;
								
							default:
								keepGoing = false;
								
								break;
							
						}
						
						if(keepGoing) {
							cons = (Cons)cons.getRhs();
							
						}
					
					
					}
					
					break;
				
			}
			
			return new B4App(params);
			
		default:
			break;
		
	}
	
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
