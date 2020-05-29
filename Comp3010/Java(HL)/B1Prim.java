
public class B1Prim implements B1Expr {

	String primType;
	
	public B1Prim(String primType) {
		this.primType = primType;
		
	}

	@Override
	public int interp() {
		return 0;
		
	}
	
	public String getPrimType() {
		return primType;
		
	}

	public void setPrimType(String primType) {
		this.primType = primType;
		
	}

	@Override
	public String pPrint() {
		return primType;
		
	}
	
}
