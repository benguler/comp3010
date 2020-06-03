
public class BMult implements BExpr {
	
	private BExpr lhs;
	private BExpr rhs;
	
	public BMult() {
		
	}

	public BMult (BExpr lhs, BExpr rhs) {	//(* e e)
		this.lhs = lhs;
		this.rhs = rhs;
		
	}

	@Override
	public String pPrint() {
		return "(* " + lhs.pPrint() + " " + rhs.pPrint() + ")";
		
	}

	@Override
	public int interp() {
		return lhs.interp() * rhs.interp();
	}
	
}
