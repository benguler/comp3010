import java.util.*; 

public class VarMap {
	Map <String, B2Expr> vm = new HashMap< String, B2Expr>();
	
	public VarMap() {
		
	}
	
	public void setVar(B2Var var, B2Expr val) {
		vm.put(var.getVarName(), val);
		
	}
	
	public B2Expr getVal(B2Var var) {
		return vm.get(var.getVarName());
	
	}
	
}
