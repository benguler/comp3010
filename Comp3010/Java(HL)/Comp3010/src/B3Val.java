public class B3Val implements B3Expr {
	public enum ValType{
		NUM,
		BOOL,
		PRIM,
		LAMB
	}
	
	private ValType valType;
	
	private int num;
	private boolean bool;
	private B3Prim prim;
	private B3Lambda lamb;
	
	public B3Val(int num) {			//v := n
		this.num = num;
		
		valType = ValType.NUM;
		
	}
	
	public B3Val(boolean bool) { 	//v := bool
		this.bool = bool;
		
		valType = ValType.BOOL;
		
	}
	
	public B3Val(B3Prim prim) {		//v := prim
		this.prim = prim;
		
		valType = ValType.PRIM;
		
	}
	
	public B3Val(B3Lambda lamb) {		//v := prim
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

	public B3Prim getPrim() {
		return prim;
	}
	
	public B3Lambda getLamb() {
		return lamb;
	}

	public ValType getValType() {
		return valType;
	}
	
	
	
}
