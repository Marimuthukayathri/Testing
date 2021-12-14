package Framework;

import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import com.codoid.products.fillo.Recordset;
import dataconnection.data_Connections;

public class fetching_exceldata {

	public static RemoteWebDriver driver;
	public static String fieldvalue, strSheet, strBusFn, strField, strBcFnClmFstNm, strBcFnClmLstNm, strBcFnFieldValue,
			             strFieldTC, strTmpClmLst, strTemp, strstrBcFnClmLstNmV1, strstrBcFnClmLstNmV2;
	static List<WebElement> objActObj;
	public static int incremt = 0, incwait = 0;

	@Test
	public void exceldata() throws Throwable {
 
		try {

			incremt = 0;
			custom_Reports.startReport();
			String strDriverQry;
			strDriverQry = "Select * from Business_Driven_Flow where Exec_Flag='YES'";
			Recordset rs = data_Connections.qry_DataFile(strDriverQry);

			while (rs.next()) {
				
				custom_Reports.strtTest(rs.getField("TestCaseName"));
				custom_Reports.print_Report("INFO", "<font size =3.3><b>Initiating Execution</b></font>",
				"<font size =3.3><b>Executing " + rs.getField("TestCaseName") + " test case .</b></font>");
				custom_Reports.print_Report("INFO", "Test Case Description", rs.getField("MainFunction"));
				driver = Load_driver.invokeBrowser();
				ArrayList<String> exlFieldNames = rs.getFieldNames();

				for (int i = 0; i < exlFieldNames.size(); i++) 	{

					if ((exlFieldNames.get(i)).contains("TestCaseName")) {
						strFieldTC = rs.getField(exlFieldNames.get(i));
					}
					if ((exlFieldNames.get(i)).contains("Business_Component")) {
						strField = rs.getField(exlFieldNames.get(i));
						if (strField.equals("")) {
							break;
						} else {
							strSheet = strField.substring(strField.indexOf('_') + 1, strField.length()); 
							strBusFn = strField.substring(0, strField.indexOf('_'));
							custom_Reports.print_Report("INFO", "<b>" + strSheet + " Screen</b>",
									"<b>Executing " + strBusFn + " functionality in the " + strSheet + " screen<b>");
							fetchsheet(strSheet, strBusFn, strFieldTC);

						}

					}

				}

				Load_driver.closeBrowser();
				custom_Reports.endReport();

			}

			custom_Reports.copy_LatestReport();
		} catch(Exception e) {
		
			custom_Reports.print_Report("FAIL", "Perform Action", "Unknown exception occured " + e);
			Load_driver.closeBrowser();
			custom_Reports.endReport();
		}

	}

	public static void fetchsheet(String strSheet, String strBusFn, String strFieldTC) throws Throwable { 

		try {
			
			String strDriverQry = " Select * from " + strSheet + " where BusinessFuntion = '" + strBusFn
					+ "' AND TestCaseName = '" + strFieldTC + "'";
			Recordset rs = data_Connections.qry_DataFile(strDriverQry);

			while (rs.next()) {
				ArrayList<String> astrBcFnFieldNames = rs.getFieldNames();

				for (String strBcFnFieldname : astrBcFnFieldNames) {
					if (strBcFnFieldname.contains("_") && !strBcFnFieldname.equals("")) {

						strBcFnClmFstNm = strBcFnFieldname.substring(0, strBcFnFieldname.indexOf('_'));
						strBcFnClmLstNm = strBcFnFieldname.substring(strBcFnFieldname.indexOf('_') + 1,
								strBcFnFieldname.length());
						strBcFnFieldValue = rs.getField(strBcFnFieldname);
						if (!strBcFnFieldValue.equals("")) {

							BO_Action.Performaction(driver, strBcFnFieldname, strBcFnFieldValue, strBusFn, strSheet);

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static List<WebElement> getObject(String strBcFnFieldname, String strBcFnFieldValue, String strSheet)
			throws Throwable {

		String strXpath;
		strBcFnClmFstNm = strBcFnFieldname.substring(0, strBcFnFieldname.indexOf('_'));
		strTmpClmLst = strBcFnFieldname.substring(strBcFnFieldname.indexOf('_') + 1, strBcFnFieldname.length());

		if (strTmpClmLst.contains("_"))
			strBcFnClmLstNm = strTmpClmLst.substring(0, strTmpClmLst.indexOf('_'));
		else
			strBcFnClmLstNm = strBcFnFieldname.substring(strBcFnFieldname.indexOf('_') + 1, strBcFnFieldname.length());

		if (BO_CommonFunction.drvreadystate(driver)) {

		try {
				strXpath = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet);
				strXpath = BO_CommonFunction.dynamicData(strXpath);

				switch (strBcFnClmFstNm.toUpperCase()) {

				case "BTN":
				case "BTNACT":
				case "BTNJCLK":
				case "TXT":
				case "GTXT":
				case "GETXT":
				case "GETDRPDN" :
				case "TAG":
				case "RDBT":
				case "MHOV":

				case "GET":
					objActObj = driver.findElementsByXPath(strXpath);
					BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
					break;

				case "LNK":
					objActObj = driver.findElementsByXPath(strXpath);
					if (objActObj.isEmpty())
						objActObj = driver.findElementsByXPath("//a[text()='" + strBcFnFieldValue + "']");
					BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
					break;

				case "CHK":
					objActObj = driver.findElementsByXPath(strXpath);
					if (objActObj.isEmpty())
						objActObj = driver.findElementsByXPath(
								"//a[text()='" + strBcFnFieldValue + "']//preceding::*[1][self::input]");
					BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
					break;

				case "CUSCHK":
					objActObj = driver.findElementsByXPath(strXpath);
					if (objActObj.isEmpty())
						objActObj = driver.findElementsByXPath(
								"//a[text()='" + strBcFnFieldValue + "']//preceding::*[1][self::input]");
					BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
					break;

				case "DRPDN":

					strXpath = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet);
					objActObj = driver.findElementsByXPath(strXpath);

					Select objDrpSlt = new Select(objActObj.get(0));
					List<WebElement> objSltOpt = objDrpSlt.getOptions();
					int size = objSltOpt.size();
					if (size == 1 || size == 0) {
						Thread.sleep(3000);
						objActObj = driver.findElementsByXPath(strXpath);
					}
					BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
					break;

				case "MSDRPDN":

					strXpath = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet);
					objActObj = driver.findElementsByXPath(strXpath);
					BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
					break;
				}

			} catch (Throwable e) {

				custom_Reports.print_Report("FAIL", "Perform Action",
				strBcFnClmFstNm + "Element could not be found in getobject " + strBcFnClmLstNm);
			}
		}

		incwait = 0;
		return objActObj;

	}


	
}
