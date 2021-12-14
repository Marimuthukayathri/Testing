package Framework;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import dataconnection.data_Connections;

public class BO_Utility {
	
	public static WebElement checkbox, checkbox1;
	

	public static void writeOutputData(String strTestCase, String fieldName, String value) {
		
	try {
		
		String strQuery;
		strQuery = "Update OutputData Set value='" + value + "' where Test_Case_Name='" + strTestCase
				+ "' and Field_Name='" + fieldName + "'";
		data_Connections.update_DataFile(strQuery);
		
		} catch (IndexOutOfBoundsException e) { 
			
			System.out.println(e.getMessage()); 
		}
	}

	public static String readOutputData(String strBcFnFieldValue) throws FilloException {

		String[] strTemp = strBcFnFieldValue.split("\\$\\$");

		String strTestName = strTemp[0];
		String strFieldName = strTemp[1];

		String strDriverQry = " Select * from OutputData where Test_Case_Name = '" + strTestName
				+ "' AND Field_Name = '" + strFieldName + "'";
		Recordset rs = data_Connections.qry_DataFile(strDriverQry);

		while (rs.next()) {
			strBcFnFieldValue = rs.getField("value");
		}

		return strBcFnFieldValue;

	}

	public static String readOutputData1(String strBcFnFieldValue) throws FilloException {

		String[] strTemp = strBcFnFieldValue.split("\\ ");

		String strTestName = strTemp[0];
		String strFieldName = strTemp[1];

		String strDriverQry = " Select * from OutputData where Test_Case_Name = '" + strTestName
				+ "' AND Field_Name = '" + strFieldName + "'";
		Recordset rs = data_Connections.qry_DataFile(strDriverQry);

		while (rs.next()) {
			strBcFnFieldValue = rs.getField("value");
		}

		return strBcFnFieldValue;

	}
	
	public static String excelreaddata( String strTestName, String strFieldName, String value) throws FilloException {

			String strDriverQry = " Select * from OutputData where Test_Case_Name = '" + strTestName
				+ "' AND Field_Name = '" + strFieldName + "'";
		Recordset rs = data_Connections.qry_DataFile(strDriverQry);

		while (rs.next()) {
			value = rs.getField("value");
		}

		return value;

	}

		
	public static String readJSONRepository(String strObjName, String strSheet) {
		String strXpath = null;
		try {

			String strActualJson = FileUtils.readFileToString(
					new File("./TCC.JSON"),
					Charset.defaultCharset());
			JSONParser parser = new JSONParser(); 
			JSONObject objActJsn = (JSONObject) parser.parse(strActualJson);
		JSONObject objActJsnIn = (JSONObject) objActJsn.get(strSheet);
		strXpath =objActJsnIn.get(strObjName).toString();
				} catch (Exception e) {
			System.out.println(e);
		}
		return strXpath;

	}
	
	public static Object chromeoptsDwnld(RemoteWebDriver driver)
	{
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("safebrowsing.enabled", "true"); //this is the needed configuration
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		return options;
	}
	
	
	public static void callall(String strBcFnFieldValue1, String strBcFnFieldValue2, WebElement checkbox, 
			String strBcFnClmLstNm, RemoteWebDriver driver) throws InterruptedException, IOException {  
		
		try {
		
		List <WebElement>  fr2 = driver.findElementsByXPath("//label[contains(text(),'"+strBcFnFieldValue1+ "')]");
		
		if(strBcFnFieldValue2.equalsIgnoreCase("Enable") || strBcFnFieldValue2.equalsIgnoreCase("Disable")) {
			WebElement e1;
			for (int l = 0; l < fr2.size(); l++) {
			if (fr2.get(l).getText().equals(strBcFnFieldValue1) ) { 
				 String var = fr2.get(l).getAttribute("for"); 
				 checkbox = driver.findElementByXPath("//input[contains(@id,'"+var+"')]");
				 e1 = fr2.get(l);  
				 if (strBcFnFieldValue1.equalsIgnoreCase(var)) {
					 int value;   
					 switch (strBcFnClmLstNm) {
					 	
			            case "Permloc":  value = 1;   				            	          	
			              	checkbox = driver.findElementByXPath("(//input[contains(@id,'all')])["+value+"]");
			 	           e1 =	 driver.findElementByXPath("(//label[contains(text(),'All')])["+value+"]");    
			 	          e1 = fr2.get(l);           	
			            	break; 
			            
			            case "SysRoles": value = 2;
			            checkbox = driver.findElementByXPath("(//input[contains(@id,'all')])["+value+"]");
			 	           e1 =	 driver.findElementByXPath("(//label[contains(text(),'All')])["+value+"]");    
			 	          e1 = fr2.get(l);          
			 	          break;
			            
			            case "FuncRoles": value = 3; 
			            checkbox = driver.findElementByXPath("(//input[contains(@id,'all')])["+value+"]");
			 	           e1 =	 driver.findElementByXPath("(//label[contains(text(),'All')])["+value+"]");    
			 	          e1 = fr2.get(l);          
			            break;
			            
			            case "Mailbox": value = 4; 
			            checkbox = driver.findElementByXPath("(//input[contains(@id,'all')])["+value+"]");
			 	           e1 =	 driver.findElementByXPath("(//label[contains(text(),'All')])["+value+"]");    
			 	          e1 = fr2.get(l);           
			            break; 
			            
			            case "default" : value = 15; break;
				 }  
					}    	if (strBcFnFieldValue2.equalsIgnoreCase("Enable") && checkbox.getAttribute("checked")==null )  {		 
						e1.click();  
						Thread.sleep(2000); 
					 	 driver.findElement(By.xpath("//div[starts-with(@class,'pull-left text-white')]")).click();
					    	
		
		custom_Reports.print_Report("INFO", "Perform Action", strBcFnFieldValue1 + " value is enabled in " +strBcFnClmLstNm + " Dropdown"  );
		break;
	}	else if (strBcFnFieldValue2.equalsIgnoreCase("Enable") && checkbox.getAttribute("checked")!=null ) { 
		
		driver.findElement(By.xpath("//div[starts-with(@class,'pull-left text-white')]")).click();
		custom_Reports.print_Report("INFO", "Perform Action", strBcFnFieldValue1 + " value is already enabled in " +strBcFnClmLstNm + " Dropdown"); 
	}
	 
					if (strBcFnFieldValue2.equalsIgnoreCase("Disable") && checkbox.getAttribute("checked")!=null ) {  
							
	        			 	 e1.click(); 
	        			 	 driver.findElement(By.xpath("//div[starts-with(@class,'pull-left text-white')]")).click();
	    			custom_Reports.print_Report("INFO", "Perform Action", strBcFnFieldValue1 + " value is disabled in " +strBcFnClmLstNm + " Dropdown"  );
	    			break;
	    	 }	else  if ( strBcFnFieldValue2.equalsIgnoreCase("Disable") && checkbox.getAttribute("checked")==null ) {   
	    		 
	    		 driver.findElement(By.xpath("//div[starts-with(@class,'pull-left text-white')]")).click();
	    								//	driver.findElement(By.xpath("//div[contains(text(),'Add User')][contains(@translate,'AddUserLabel')]")).click();
							 custom_Reports.print_Report("INFO", "Perform Action", strBcFnFieldValue1 + " value is already disabled in " +strBcFnClmLstNm + " Dropdown"  );
	    }     					 
      }					 
	}			
  }   
		} catch(Exception e) { 
			
			System.out.println(e.getMessage()); 
		}

}
	public static void addtag(String strBcFnFieldValue, List<WebElement> objActObj) throws InterruptedException { 
		
  try {
		 List <WebElement> tagEles = objActObj;
		 if (strBcFnFieldValue.contains(",") )  { 
		 String[] strfldvalues = strBcFnFieldValue.split(",");  
	     //System.out.println(" strfldvalues length = " +strfldvalues.length);  
		 Thread.sleep(2000); 
		
		 for (int i = 0; i < strfldvalues.length; i++) {
					for (int l = 0; l < tagEles.size(); l++) {
						if (tagEles.get(l).getText().equals(strfldvalues[i]))
						{
							tagEles.get(l).click();
						}
					}
				}
		 } else   
		
		for (int l = 0; l < tagEles.size(); l++) { 
		if (tagEles.get(l).getText().equals(strBcFnFieldValue))   
		{
			tagEles.get(tagEles.size()).click();
			break;
		  }
		}
	} catch (Exception e) { 
			
			System.out.println(e.getMessage()); 
  }

}
	
	public static void fetchdrpdnselectedval(RemoteWebDriver driver, List<WebElement> objActObj) throws IOException
	{

		WebElement element = objActObj.get(0);
		Select select = new Select(element);
		  element = select.getFirstSelectedOption();
	      String selectedoption = element.getText();
	      System.out.println("Selected element: " + selectedoption);
			 custom_Reports.print_Report("INFO", "Perform Action", selectedoption + " value is enterted"  );


	
	}
				
	
	
	
}