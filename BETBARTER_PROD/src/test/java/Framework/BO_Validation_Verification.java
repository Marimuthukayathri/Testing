package Framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BO_Validation_Verification {

	public static List<WebElement> objActObj;
	public static String strBcFnFieldName, strVerfValue,strReadDataText,strTestCasenFName,strAttrValue1;
	public static String [] strDrpdnVerf;

	public static void resultVerVal(String strBcFnFieldValue, String strBusFn, String strSheet, List<WebElement> objActObj,String strBcFnClmLstNm, RemoteWebDriver driver)  throws Throwable {

		String [] strTmpBcFnFieldValue = strBcFnFieldValue.split("::");
		strBcFnFieldValue = strTmpBcFnFieldValue[1];
    	strVerfValue =strTmpBcFnFieldValue[2];
	//	strAttrValue = strTmpBcFnFieldValue[3]; 
    	if(strVerfValue.contains("$$"))
    	{
				strTestCasenFName =strVerfValue.substring(1);
				strVerfValue =	BO_Utility.readOutputData(strTestCasenFName);
				Thread.sleep(5000);
    	}
    	
    	if(strVerfValue.contains(";"))
		{
			
			strDrpdnVerf = strVerfValue.split(";");
			
		}
    	
		switch(strBcFnFieldValue.toUpperCase())
		{
   	 
   	case "DRPDNVALUE" :
   		
   		ArrayList<String> strDrpInpVal = new ArrayList<String>();
   		
   		for(int i=0;i<strDrpdnVerf.length;i++)
		 {
			strDrpInpVal.add(strDrpdnVerf[i]);
		 }
   		
   		Select objDrpSlt = new Select(objActObj.get(0));
   		
   		ArrayList<String> strDrpOutVal = new ArrayList<String>();
   		
   		List<WebElement> objOptions = objDrpSlt.getOptions();
   		
   		for (WebElement objIndOptions : objOptions) {
   			
  			 String strOptionsVal = objIndOptions.getText();
  			strDrpOutVal.add(strOptionsVal);
   		}
   		
   		strDrpInpVal.removeAll(strDrpOutVal);
   		
   		//System.out.println(strDrpInpVal);
   		 
   		if(strDrpInpVal.isEmpty())
   		 {
   			custom_Reports.print_Report("PASS", "Verification",
					"All values are available in dropdown and successfully validated.<a href='" + custom_Reports.takeSnap()  
			+ "'> Click here </a> to view the screen shot");
   		 }
   		 
   		 else
   		 {
   			 System.out.println(strDrpInpVal);
   			custom_Reports.print_Report("FAIL", "Verification",
   					strDrpInpVal+	"values are not available in dropdown.<a href='" + custom_Reports.takeSnap()  
			+ "'> Click here </a> to view the screen shot");
   		 }
 
   		 break;
   		
   		 
   	case "ISSELECTED" :
   		
   	 
   		if (objActObj.isEmpty()||objActObj==null)
		 {
	          	custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found " );   	
       	 }
   		
   		 if(String.valueOf(objActObj.get(0).isSelected()).equalsIgnoreCase(strVerfValue))
   		 {
   			 
   			custom_Reports.print_Report("PASS", "Verification",
   					strBcFnClmLstNm +"CheckBox is Selected.<a href='" + custom_Reports.takeSnap()
					+ "'> Click here </a> to view the screen shot");
   		 }
   		 
   		 else
   		 {
   			custom_Reports.print_Report("FAIL", "Verification",
   					strBcFnClmLstNm + "CheckBox is Not Selected.<a href='" + custom_Reports.takeSnap()
					+ "'> Click here </a> to view the screen shot");
   		 }
   		
   		 break;
   		 
	case "TEXT":
   		
   		try  {
   			
   			if (objActObj.isEmpty()||objActObj==null)
   		 {
   	          	custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found " );   	
          	 }
      		
   			@SuppressWarnings("unused")
			boolean wait = false;
   	   		WebElement element  = objActObj.get(0);
   	   		String text = null;
			
   			/*if(!element.isDisplayed()) { 
   			WebDriverWait waitforelmt = new WebDriverWait(driver, 30);
			waitforelmt.until(ExpectedConditions.visibilityOf(element));
			System.out.println("Waitng for Element Visible");
			}*/ 
   	   		if (strBcFnClmLstNm.equalsIgnoreCase("Country")) {
				Select select = new Select(element);
				  element = select.getFirstSelectedOption();
			      text = element.getText();
			 }  else {
			text = element.getText(); 
			}
			
			//   System.out.println("Text is: "+text);
   			
			if(text.equals("") || text.equals(" "))
			text = "Null";
   			
			if (text.contains(strVerfValue) || strVerfValue.contains(text) || text.equalsIgnoreCase(strVerfValue)|| strVerfValue.equalsIgnoreCase(text)) {
				wait =true;
			custom_Reports.print_Report("PASS", "Verification",
					strBusFn + " funtionality is successfully validated." +text+ " is available <a href='" + custom_Reports.takeSnap()
							+ "'> Click here </a> to view the screen shot");
		} 	else
   				{
   					custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed." +text+ " is not available  <a href='"
   							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
   				}  
   		}	catch(Exception e) 
   		{
   			custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed. <a href='"
						+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
   		}
   		
		break;
	
   	case "ISDISPLAYED" :
   		
      	 
   		if (objActObj.isEmpty()||objActObj==null)
		 {
	          	custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found" );   	
       	 }
   		try
   		{
   			boolean displayed = objActObj.get(0).isDisplayed();
   			Thread.sleep(2000);
   		 if(String.valueOf(objActObj.get(0).isDisplayed()).equalsIgnoreCase(strVerfValue))
   		 {
   			 
   			custom_Reports.print_Report("PASS", "Verification",
   					strBcFnClmLstNm+ "Button is displayed");
   			custom_Reports.print_Report("PASS", "Verification",
					strBusFn + " funtionality is successfully validated.<a href='" + custom_Reports.takeSnap()
							+ "'> Click here </a> to view the screen shot");
   		 }
   		 
   		 else
   		 {
   			custom_Reports.print_Report("FAIL", "Verification",
   					strBcFnClmLstNm+ "CheckBox is Not Selected.<a href='" + custom_Reports.takeSnap()
					+ "'> Click here </a> to view the screen shot");
   		 }
   		}catch(Exception e)
   		{
   			custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed. <a href='"
					+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
   		}
   		 break;
   		 
   	case "ATTRIBUTE" :
   		
   		if (objActObj.isEmpty()||objActObj==null)
		 {
	          	custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found" );   	
      	 }
   		
   		try
   		{
   			
   			if(strVerfValue.contains(":")) 
			{
				
    		String[] strAttrValue =  strVerfValue.split(":");   
    		strAttrValue1 = strAttrValue[0];
    		strVerfValue = strAttrValue[1];

			}
    		
    		   			
   			String strattrivalue = objActObj.get(0).getAttribute(strAttrValue1); 
   			if(strattrivalue.equals(""))
   				strattrivalue = "Null";
   			if (strattrivalue.contains(strVerfValue) || strVerfValue.contains(strattrivalue)) { 
   				custom_Reports.print_Report("PASS", "Verification",
   						strBusFn + " funtionality is successfully validated."+strattrivalue+" is available <a href='" + custom_Reports.takeSnap()
   								+ "'> Click here </a> to view the screen shot");
   			}  else
   	   				{
   	   					custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed." +strVerfValue+ " is not a hyperlink <a href='"
   	   							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
   	   						}
   	   		}catch(Exception e)
   	   		{
   	   			custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed. <a href='"
   							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
   	   		}
   			break;
   			
   			
   	case "SELECTEDOPTION":
   		
   		Select objDrpSlt1 = new Select(objActObj.get(0));
   		
   		WebElement selectedOption = objDrpSlt1.getFirstSelectedOption();
   		String text = selectedOption.getText();
   		if(text.equalsIgnoreCase(" ")||text.equalsIgnoreCase("")) 
   		{
   			text="Null";
   		}
   		
   		if(text.equalsIgnoreCase(strVerfValue))
   		{
   			custom_Reports.print_Report("PASS", "Verification",
					strBusFn + " funtionality is successfully validated.<a href='" + custom_Reports.takeSnap()
							+ "'> Click here </a> to view the screen shot");
   		}
   		else
   		{
   			custom_Reports.print_Report("FAIL", "Verification",
					strBusFn + " funtionality is successfully validated.<a href='" + custom_Reports.takeSnap()
							+ "'> Click here </a> to view the screen shot");
   		}
   		
   			break;
	
	case "MHOV":
		
   		try {
   		
   			System.out.println("Mouse Hover validation is started"); 
   			String mhovtext = objActObj.get(0).getText();
  
   			if(mhovtext.equals("") || mhovtext.equals(" "))  
   				mhovtext = "Null";
   			if(strVerfValue.contains(",") ) {
   				String[] strfldvalues =  strVerfValue.split(",");   
    			for (int i = 0; i < strfldvalues.length; i++) {	
    		
    	if (mhovtext.contains(strfldvalues[i]) || strfldvalues[i].contains(mhovtext)) { 
			custom_Reports.print_Report("PASS", "Verification",
					strBusFn + " funtionality is successfully validated. " +strfldvalues[i]+ " is available <a href='" + custom_Reports.takeSnap()
							+ "'> Click here </a> to view the screen shot");
	  			} 
   				else
   				
   					custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed. " + strfldvalues[i]+ " is not available  <a href='"
   							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
   				
    			}	} else  if (mhovtext.contains(strVerfValue) || strVerfValue.contains(mhovtext)) {
    					
    				custom_Reports.print_Report("PASS", "Verification", 
    						strBusFn + " funtionality is successfully validated." +strVerfValue+ " is available <a href='" + custom_Reports.takeSnap()
    								+ "'> Click here </a> to view the screen shot");
    			} 
    			
    	   				else
    	   				
    	   					custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed." +strVerfValue+ " is not available  <a href='"
    	   							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
    	   				
   		
	}	catch(Exception e)
   		{
   			custom_Reports.print_Report("FAIL", "Verification", strBusFn + " funtionality is failed. <a href='"
						+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
   		}
		break;   	 
   


   	 
   	 		}
		}
	}


