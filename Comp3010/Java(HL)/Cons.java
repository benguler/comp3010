import java.util.ArrayList;

public class Cons implements BSexpr{
	private BSexpr lhs;
	private BSexpr rhs;
	
	public Cons(BSexpr lhs, BSexpr rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
		
	}

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
	
	@Override
	public B1Expr desugarB1() {
		if(lhs.getType() == type.Atom) {
			String atom = ((Atom) lhs).getAtom();
			Cons cons = (Cons)rhs;
			
			if(atom == "if") {
				Cons cons2 = (Cons)cons.getRhs();
				
				return new B1If(cons.getLhs().desugarB1(), cons2.getLhs().desugarB1(), cons2.getRhs().desugarB1());
				
			}
			
			ArrayList<B1Expr> params = new ArrayList<B1Expr>();
			
			params.add(new B1Val(new B1Prim(atom)));
			params.add(cons.getLhs().desugarB1());
			params.add(cons.getRhs().desugarB1());
			
			return new B1App(params);
			
		}
		
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
