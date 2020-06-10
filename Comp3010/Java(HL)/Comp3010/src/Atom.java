
public class Atom implements BSexpr{
	String atom;
	
	public Atom(String atom) {
		this.atom = atom;
		
	}

	//Desugar for B1
	@Override
	public BExpr desugar() {
		
		switch(atom) {
			case "+":
				return new BAdd();
			case "*":
				return new BMult();
			default:
				return new BVal(Integer.parseInt(atom));
		}
		
	}

	@Override
	public type getType() {
		return type.Atom;
		
	}

	public String getAtom() {
		return atom;
		
	}

	public void setAtom(String atom) {
		this.atom = atom;
		
	}

	//Desugar for B1
	@Override
	public B1Expr desugarB1() {
		 try { 												//If atom contains an integer
		        return new B1Val(Integer.parseInt(atom));
		       
		        
		    } catch(NumberFormatException e) { 
		        
		    }
		 
		 switch(atom) {
		 	case "true":
		 		return new B1Val(true);
		 	case "false":
		 		return new B1Val(false);
		 	default:										//If atom does not contain an int or a bool, it contains a prim
		 		return new B1Val(new B1Prim(atom));
		 
		 }
		
	}

	@Override
	public B2Expr desugarExprB2() {
		try { 												//If atom contains an integer
	        return new B2Val(Integer.parseInt(atom));
	       
	        
	    } catch(NumberFormatException e) { 
	        
	    }
	 
		switch(atom) {
		 	case "true":
		 		return new B2Val(true);
		 	case "false":
		 		return new B2Val(false);
		 	default:										//If atom does not contain an int or a bool, it contains a prim
		 		if(atom == "+" || atom == "*" || atom == "/" || atom == "-" 
				|| atom == ">" || atom == ">=" || atom == "=" || atom == "<"
				|| atom == "<=") {
		 			return new B2Val(new B2Prim(atom));
		 		}
		 		
		 		break;
		 		
		}
		
		if(atom == atom.toLowerCase()) {
			return new B2Var(atom);
			
		}
		
		return new B2Func(atom);
	 
	}

	@Override
	public Pair desugarB2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B2Def desugarDefB2() {
		// TODO Auto-generated method stub
		return null;
	}

}
