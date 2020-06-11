import java.util.*; 

public class VarMap {
	Map <String, ArrayList<B2Val>> vm = new HashMap< String, ArrayList<B2Val>>();
	B2Val oldVal;
	
	public VarMap() {
		
	}
	
	public void removeVal(B2Var var) {
		vm.get(var.getVarName()).remove(vm.get(var.getVarName()).size()-1);
		
	}
	
	public void setVar(B2Var var, B2Val val) {
		if(!vm.containsKey(var.getVarName())) {
			vm.put(var.getVarName(), new ArrayList<B2Val>());
			
		}
		
		vm.get(var.getVarName()).add(val);
		
	}
	
	public B2Val getVal(B2Var var) {
		B2Val val = vm.get(var.getVarName()).get(vm.get(var.getVarName()).size()-1);
		
		return val;
	
	}
	
}
