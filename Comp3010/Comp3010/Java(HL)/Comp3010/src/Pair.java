
public class Pair {
	private Context c;
	private B1Expr e;
	
	public Pair() {
		
	}
	
	public Pair(Context c, B1Expr e) {
		this.c =c;
		this.e = e;
		
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
