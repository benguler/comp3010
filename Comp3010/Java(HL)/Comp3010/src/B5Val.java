public class B5Val implements B5Expr {
	public enum ValType{
		NUM,
		BOOL,
		PRIM,
		LAMB,
		UNIT,
		PAIR,
		INL,
		INR,
		OBJ
	}
	
	private ValType valType;
	
	private int num;
	private boolean bool;
	private B5Prim prim;
	private B5Lambda lamb;
	private B5Obj obj;
	private B5App app;
	
	public B5Val(int num) {			//v := n
		this.num = num;
		
		valType = ValType.NUM;
		
	}
	
	public B5Val(boolean bool) { 	//v := bool
		this.bool = bool;
		
		valType = ValType.BOOL;
		
	}
	
	public B5Val(B5Prim prim) {		//v := prim
		this.prim = prim;
		
		valType = ValType.PRIM;
		
	}
	
	public B5Val(B5Lambda lamb) {		//v := lambda
		this.lamb = lamb;
		
		valType = ValType.LAMB;
		
	}
	
	public B5Val(B5Obj obj) {
		this.obj = obj;
		
		valType = ValType.OBJ;
	}
	
	public B5Val() {
		valType = valType.UNIT;
		
	}
	
	public B5Val(B5App app) {
		this.app = app;
		
		B5Val subVal = (B5Val)(app.getExprs().get(0));
		String subPrimType = subVal.getPrim().getPrimType();
		
		if (subPrimType == "pair") {
			valType = ValType.PAIR;
		
		}else if (subPrimType == "inl") {
			valType = ValType.INL;
		
		}else if(subPrimType == "inr") {
			valType = ValType.INR;
			
		}
		
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

	public B5Prim getPrim() {
		return prim;
	}
	
	public B5Lambda getLamb() {
		return lamb;
	}
	
	public B5App getApp() {
		return app;
	}

	public ValType getValType() {
		return valType;
	}
	
	
	
}
