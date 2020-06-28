import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class B4Functions {
	public static String toC(B4Expr expr) {
		switch(expr.getType()) {
			case IF:
				B4If ifExpr = (B4If)expr;
				return "newIf(" + toC(ifExpr.getExpr(0)) + ", " + toC(ifExpr.getExpr(1)) + ", " + toC(ifExpr.getExpr(2)) + ")";
				
			case APP:
				B4App appExpr = (B4App)expr;
				ArrayList<B4Expr> exprs = appExpr.getExprs();
				
				String appC = "newApp(" + String.valueOf(exprs.size());
				
				for(B4Expr e : exprs) {
					appC += ", " + toC(e);
					
				}
				
				appC += ")";
				
				return appC;
				
			case LAMB:
				B4Lambda lambdaExpr = (B4Lambda)expr;
				ArrayList<B4Var> vars = lambdaExpr.getVars();
				
				String lambC = "newLambda(" + String.valueOf(vars.size())+ ", ";
				
				lambC += toC(lambdaExpr.getRecName()) + ", ";
				lambC += toC(lambdaExpr.getExpr()); 
				
				for(B4Expr v : vars) {
					lambC += ", " + toC(v);
					
				}
				
				lambC += ")";
				
				return lambC;
				
			case VAL:
				B4Val valExpr = (B4Val)expr;
				
				switch(valExpr.getValType()) {
					case NUM:
						return "newVal(" + String.valueOf(valExpr.getNum()) +")";
				
					case BOOL:
						return valExpr.getBool() ? "newVal(true)":"newVal(false)";
						
					case PRIM:
						return "newVal(" + toC(valExpr.getPrim()) + ")";
						
					case LAMB:
						return "newVal(" + toC(valExpr.getLamb()) + ")";
						
					default:
						break;
					
				
				}
				
			case VAR:
				B4Var varExpr = (B4Var)expr;
				
				return "newVar(\""+ varExpr.getVarName() +"\")";
				
			case PRIM:
				B4Prim primExpr = (B4Prim)expr;
				
				return "newPrim(\"" + primExpr.getPrimType() + "\")";
				
			default:
				return null;
		
		}
		
	}
	
	public static void connectTestSuite(B4Expr... exprs) {
		String fileName = "../../C(LL)/B4/main.cpp";
		
		try {
		      File myObj = new File(fileName);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        
		      } else {
		        System.out.println("File already exists.");
		        
		      }
		      
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      
		    }
		
		try {
		      FileWriter myWriter = new FileWriter(fileName);
		      myWriter.write("#include <iostream>\n#include \"B4.h\"\n\n");
		      myWriter.write("int main( int argc, char** argv ) {\n");
		      
		      int i = 1;
		      for(B4Expr expr : exprs) {
		    	  myWriter.write("	cout<<\"expr" + i + " == \"<<valEval(cek2(" + toC(expr) + ", newVarMap()))<<endl;\n");
		    	  i++;
		    	  
		      }
		      
		      myWriter.write("\n	return 0;\n\n}");
		      myWriter.close();
		      
		      System.out.println("Successfully wrote to the file.");
		      
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
}
