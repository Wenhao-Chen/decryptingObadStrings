import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class main {
	

	

	
    public static String obadString (int i, int j, int k) {
    	byte[] OlICCCco = null;
    	byte[] result = new byte[k+56], source;
    	int[] resultInt = new int[k+56];
    	source = OlICCCco;
    	resultInt[0]=i+82;
    	for (int ii = 1; ii<=k+55; ii++) {
    		resultInt[ii] = resultInt[ii-1]+source[j+ii-1]-4;
    	}
    	for (int jj = 0; jj<=k+55; jj++)
    		result[jj] = (byte)resultInt[jj];
    	String results = new String(result);
    	return results;
    }
    
    public static void main (String[] args) {
    	
    	String[] clses = {
    			"OocIOCIo", "ocOlcICo", "CIOIIolc", "cOOCoCc", "lOIlloc",
    			"oCOllOO", "IlIIlCI", "IcCcCOIC", "OOOOlIO", "olcCIIC",
    			"OooOOOo", "oclClII", "OclIIOlC", "occcclc", "IlOIcII",
    			"loOOIclc", "IcCcCOIC$cOIcOOo", "CIlOCClc", "cOIcOOo", "oIlclcIc",
    			"OlCCcIl", "cIoCcIo", "CIcIoICo", "ooCclcC", "cCOIcIlo",
    			"CcoCIcI", "OlICCCco", "cOoOICO", "MainService", "OIccoIlO", 
    			"oOCCOOI", "COcocOlo", "OcooIclc", "OlOClICl", "lCICoIO",
    			"OcIcoOlc", "OoCOocll", "CICCCcCI", "ololCCOc" };
    	
    	int[] regs = {
    			1,0,1,0,0,0,0,0,0,0,
    			0,0,0,0,1,0,0,1,0,1,
    			0,0,0,1,0,1,0,0,1,0,
    			0,0,1,0,0,0,0,1,0  	};
    	
    	int[] lengths = {
    			239, 1828, 530, 996, 1437, 756, 2414, 1463, 229, 107, 
    			636, 313, 184, 440, 935, 113, 248, 903, 90, 1233, 
    			76, 261, 291, 58, 101, 391, 137, 323, 1243, 91,
    			1716, 878, 1130, 61, 275, 59, 245, 675, 218	};
    	
    	//String test = obadString(24,599,-27);
    	//System.out.println(test);
    	System.out.println("string test:");
    	String stmt = "$r5 = staticinvoke <com.android.system.admin.oOCCOOI: java.lang.String cOIcOOo(int,int,int)>(36, 1256, -45);";
    	int[] parameters = obadGetParameters(stmt);
    	for (int i = 0; i<parameters.length; i++)
    		System.out.format("%d, ", parameters[i]);
    	// count obad encryption
    	for (int i = 0; i< 39; i++)	;
    			//getKeys(clses[i], "r"+Integer.toString(regs[i]), i+1, lengths[i]);
    	try {
    		File tmpfile = new File("/home/wenhaoc/Soot_Logs/temp.txt");
    		PrintWriter out = new PrintWriter (new FileWriter(tmpfile, true));
    		for (int j = 0; j<39; j++)	{
    			out.write("final byte[] Key_No_" + Integer.toString(j+1) + " = new byte[" + Integer.toString(lengths[j]) + "];\n");
    			out.flush();
    		}
    		out.close();
    	}	catch (Exception e) {}
    	
    		
    	
    }
	
	public static int[] obadGetParameters(String stmt) {
		int[] result = new int[3];
		String[] crap = {};
		String shortOne = stmt.substring(stmt.lastIndexOf(">(")+2, stmt.lastIndexOf(");")).trim();
		shortOne = shortOne.replaceAll(",", "").trim();
		String[] pieces = shortOne.split(" ");
		for (int i = 0; i<pieces.length; i++) {
			result[i] = Integer.parseInt(pieces[i].trim());
		}
		return result;
	}
    
	public static void getKeys(String cls, String key, int keyNo, int length) {
		String readLine, value = null;
		boolean start = false;
		int counter = 0;
		
		try {
			File targetFile = new File("/home/wenhaoc/Soot_BenignApps_Output/obad_apk/com.android.system.admin." + cls + ".jimple");
			BufferedReader reader = new BufferedReader(new FileReader(targetFile));
			File outFile = new File("/home/wenhaoc/Soot_Logs/obad/Key_No_" + Integer.toString(keyNo) + ".txt");
			PrintWriter out = new PrintWriter (new FileWriter(outFile, true));
			while ((readLine = reader.readLine())!=null) {
				if (!start && readLine.contains("$" + key + " = newarray"))	{ 	start = true;	System.out.println("start from:  "+readLine);	continue;}
				if (start)
					if (readLine.contains(key + "[" + Integer.toString(counter) + "] = ")) {
						value = readLine.substring(readLine.lastIndexOf("= ")+2, readLine.lastIndexOf(";"));
						out.write(value + "\n");
						counter++;
						out.flush();
						if (counter>= length)	{start = false;	break;	}
					}	else System.out.println("oops, order is wrong:  " + readLine);
			}
			out.close();
			reader.close();
		}	catch (Exception e) {e.printStackTrace();}
	}
	
}
