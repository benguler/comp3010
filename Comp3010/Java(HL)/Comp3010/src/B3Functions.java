import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class B3Functions {
	
	public static String toC(B3Expr expr) {
		switch(expr.getType()) {
			case IF:
				B3If ifExpr = (B3If)expr;
				return "newIf(" + toC(ifExpr.getExpr(0)) + ", " + toC(ifExpr.getExpr(1)) + ", " + toC(ifExpr.getExpr(2)) + ")";
				
			case APP:
				B3App appExpr = (B3App)expr;
				ArrayList<B3Expr> exprs = appExpr.getExprs();
				
				String appC = "newApp(" + String.valueOf(exprs.size());
				
				for(B3Expr e : exprs) {
					appC += ", " + toC(e);
					
				}
				
				appC += ")";
				
				return appC;
				
			case LAMB:
				B3Lambda lambdaExpr = (B3Lambda)expr;
				ArrayList<B3Var> vars = lambdaExpr.getVars();
				
				String lambC = "newLambda(" + String.valueOf(vars.size());
				
				lambC += ", " + toC(lambdaExpr.getExpr()); 
				
				for(B3Expr v : vars) {
					lambC += ", " + toC(v);
					
				}
				
				lambC += ")";
				
				return lambC;
				
			case VAL:
				B3Val valExpr = (B3Val)expr;
				
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
				B3Var varExpr = (B3Var)expr;
				
				return "newVar(\""+ varExpr.getVarName() +"\")";
				
			case PRIM:
				B3Prim primExpr = (B3Prim)expr;
				
				return "newPrim(\"" + primExpr.getPrimType() + "\")";
				
			default:
				return null;
		
		}
		
	}
	
	public static void connectTestSuite(B3Expr... exprs) {
		String fileName = "../../C(LL)/B3/main.cpp";
		
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
		      myWriter.write("#include <iostream>\n#include \"B3.h\"\n\n");
		      myWriter.write("int main( int argc, char** argv ) {\n");
		      
		      int i = 1;
		      for(B3Expr expr : exprs) {
		    	  myWriter.write("	cout<<\"expr" + i + " == \"<<valEval(cek1(" + toC(expr) + ", newVarMap()))<<endl;\n");
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
