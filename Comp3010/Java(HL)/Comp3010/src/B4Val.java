public class B4Val implements B4Expr {
	public enum ValType{
		NUM,
		BOOL,
		PRIM,
		LAMB
	}
	
	private ValType valType;
	
	private int num;
	private boolean bool;
	private B4Prim prim;
	private B4Lambda lamb;
	
	public B4Val(int num) {			//v := n
		this.num = num;
		
		valType = ValType.NUM;
		
	}
	
	public B4Val(boolean bool) { 	//v := bool
		this.bool = bool;
		
		valType = ValType.BOOL;
		
	}
	
	public B4Val(B4Prim prim) {		//v := prim
		this.prim = prim;
		
		valType = ValType.PRIM;
		
	}
	
	public B4Val(B4Lambda lamb) {		//v := lambda
		this.lamb = lamb;
		
		valType = ValType.LAMB;
		
	}

	@Override
	public ExprType getType() {
		return ExprType.VAL;
	}

	public int getNum() {
		return num;
	}

	public boolean getBool() {
		return bool;
	}

	public B4Prim getPrim() {
		return prim;
	}
	
	public B4Lambda getLamb() {
		return lamb;
	}

	public ValType getValType() {
		return valType;
	}
	
	
	
}
