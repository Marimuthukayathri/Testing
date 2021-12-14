package Framework;

import java.util.List;
import org.openqa.selenium.WebElement;

public class BO_Verification {

	public static List<WebElement> objActObj;
	public static String strBcFnFieldName, strBcFnFieldValue, strBcFnVfFieldfvalue, strFirstBcFnFieldName,
			strSecondBcFnFieldName, text;

	public static void Resultverification(String strBcFnFieldValueVf, String strBusFn, String strSheet) throws Throwable {

		if(strBcFnFieldValueVf.contains("$$$"))
    	{
		    	String[] strTemp = strBcFnFieldValueVf.split("\\$\\$\\$");
		    	
				strBcFnFieldName = strTemp[0];
				strBcFnVfFieldfvalue = strTemp[1];
				//strBcFnVfFieldfvalue=strBcFnVfFieldfvalue.substring(1, strBcFnVfFieldfvalue.length()-1);
				strBcFnVfFieldfvalue=strBcFnVfFieldfvalue.substring(1);
				strBcFnVfFieldfvalue =	BO_Utility.readOutputData(strBcFnVfFieldfvalue); 
				Thread.sleep(5000);
				
    	}
		
		else 
		{
		
		String[] strTemp = strBcFnFieldValueVf.split("\\$\\$");

		strBcFnFieldName = strTemp[0];
		strBcFnVfFieldfvalue = strTemp[1];
		Thread.sleep(5000);

		}

		objActObj = fetching_exceldata.getObject(strBcFnFieldName, strBcFnVfFieldfvalue, strSheet);

		try {
			text = objActObj.get(0).getText();
		//	System.out.println(text);
			if(text.equals(""))
				text = "validated";
			if (text.contains(strBcFnVfFieldfvalue) || strBcFnVfFieldfvalue.contains(text)) {
				custom_Reports.print_Report("PASS", "Verification",
						strBusFn + " funtionality is successfully validated.<a href='" + custom_Reports.takeSnap()
								+ "'> Click here </a> to view the screen shot");

					}
			else
			{
				int intMdnNum = Integer.parseInt(text);
				int length = String.valueOf(intMdnNum).length();
				if(length==8)
				{
					custom_Reports.print_Report("PASS", "Verification",
							strBusFn + " funtionality is successfully validated.<a href='" + custom_Reports.takeSnap()
									+ "'> Click here </a> to view the screen shot");
				}
			}
		} catch (Exception e) {

			custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed. <a href='"
					+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");

		}
	}
}
