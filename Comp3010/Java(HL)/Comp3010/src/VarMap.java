import java.util.*; 

public class VarMap {
	Map <String, B2Val> vm;
	
	public VarMap() {
		vm = new HashMap< String, B2Val>();
		
	}
	
	public void setVar(B2Var var, B2Val val) {
		vm.put(var.getVarName(), val);
		
	}
	
	public B2Val getVal(B2Var var) {
		return vm.get(var.getVarName());
	
	}
	
}
