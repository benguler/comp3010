

public class B2Val implements B2Expr {
	public enum ValType{
		NUM,
		BOOL,
		PRIM
	}
	
	private ValType valType;
	
	private int num;
	private boolean bool;
	private B2Prim prim;
	
	public B2Val(int num) {			//v := n
		this.num = num;
		
		valType = ValType.NUM;
		
	}
	
	public B2Val(boolean bool) { 	//v := bool
		this.bool = bool;
		
		valType = ValType.BOOL;
		
	}
	
	public B2Val(B2Prim prim) {		//v := prim
		this.prim = prim;
		
		valType = ValType.PRIM;
		
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

	public B2Prim getPrim() {
		return prim;
	}

	public ValType getValType() {
		return valType;
	}
	
	
	
}
