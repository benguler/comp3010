
public class Pair {
	private Context c;
	private B1Expr e;
	
	private B2Def def;
	private B2Expr e1;
	
	public Pair() {
		
	}
	
	public Pair(Context c, B1Expr e) {
		this.c =c;
		this.e = e;
		
	}
	
	public Pair(B2Def def, B2Expr e1) {
		this.def =def;
		this.e1 = e1;
		
	}

	public Context getC() {
		return c;
	}

	public void setC(Context c) {
		this.c = c;
	}

	public B1Expr getE() {
		return e;
	}

	public void setE(B1Expr e) {
		this.e = e;
	}
	
}
