import java.util.HashMap;
import java.util.Map; 

public class FuncMap {
	Map <String, B2Def> fm = new HashMap< String, B2Def>();
	
	public FuncMap() {
		
	}
	
	public void setFunc(B2Func func, B2Def def) {
		fm.put(func.getFuncName(), def);
		
	}
	
	public B2Def getDef(B2Func func) {
		return fm.get(func.getFuncName());
	
	}
	
}
