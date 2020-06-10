
public class B1Val implements B1Expr {
	public enum ValType{
		NUM,
		BOOL,
		PRIM
	}
	
	private ValType valType;
	
	private int num;
	private boolean bool;
	private B1Prim prim;
	
	public B1Val(int num) {			//v := n
		this.num = num;
		
		valType = ValType.NUM;
		
	}
	
	public B1Val(boolean bool) { 	//v := bool
		this.bool = bool;
		
		valType = ValType.BOOL;
		
	}
	
	public B1Val(B1Prim prim) {		//v := prim
		this.prim = prim;
		
		valType = ValType.PRIM;
		
	}

	@Override
	public int interp() {
		switch(valType) {
			case NUM:
				return num;
			case BOOL:
				if(bool) {
					return 1;
				}
				
				return 0;
			default:
				return 0;
				
		
		}
	}

	@Override
	public String pPrint() {
		switch(valType) {
		case NUM:
			return String.valueOf(num);
		case BOOL:
			if(bool) {
				return "true";
			}
			
			return "false";
		default:
			return prim.pPrint();
			
		
		}
		
	}

	public ValType getValType() {
		return valType;
	}

	public int getNum() {
		return num;
	}

	public boolean getBool() {
		return bool;
	}

	public B1Prim getPrim() {
		return prim;
	}

	@Override
	public ExprType getExprType() {
		return ExprType.VAL;
		
	}
	
}
