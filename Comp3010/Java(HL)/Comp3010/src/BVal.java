
public class BVal implements BExpr {
	
	private int n;
	
	public BVal (int n) {	//v := n
		this.n = n;
		
	}

	@Override
	public String pPrint() {
		return "("+String.valueOf(n)+")";
		
	}

	@Override
	public int interp() {
		return n;
		
	}
	

}
