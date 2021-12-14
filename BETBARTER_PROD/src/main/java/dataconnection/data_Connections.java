package dataconnection;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class data_Connections {
	
	static Connection connection = null;
	static Recordset recordset = null;
	
	public static Recordset qry_DataFile(String strQuery) {
		
	try {
			Fillo fillo=new Fillo();
			connection=fillo.getConnection("./test_Data/Betbarter_Automation.xlsx");
			recordset=connection.executeQuery(strQuery);
			connection.close();
			
		} catch (Exception e){
		 System.out.println(e);
		}
		return recordset;
	}
	
	public static void update_DataFile(String strQuery) {
		
	try {
			Fillo fillo=new Fillo();
			connection=fillo.getConnection("./test_Data/Betbarter_Automation.xlsx");
			connection.executeUpdate(strQuery);
			connection.close();
		}catch (Exception e){
		 System.out.println(e);
		}
	}
}

