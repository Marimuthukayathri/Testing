package Framework;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BO_Action {

	private static final JavascriptExecutor driver = null;
	public static List<WebElement> objActObj;
	public static String strBcFnClmFstNm ,data,strFlag, strBcFnClmLstNm, strDrpdnObjNm, strChkObjValue,strTemp,strRdbtObjNm,strTmpClmLst,currentWindow,strTitle,strChkObjtxt, browserName,strXpath,strBcFnFieldValueVf = null;
	public static boolean strreexeFlg=true;
	public static int incwaitstl, count, time;
	public static WebElement checkbox; 
	public static boolean result;
//	public static ArrayList<String> handles = new ArrayList<String> (((RemoteWebDriver) driver).getWindowHandles());
	
		
	
	@SuppressWarnings("unchecked")
	public static void Performaction(RemoteWebDriver driver, String strBcFnFieldname, String strBcFnFieldValue,
			String strBusFn, String strSheet) throws Throwable {

		// For splitting the Business function field value for verification method and
		// get object method

		if (strBcFnFieldValue.contains("##")) {
			String[] strTmpBcFnFieldValue = strBcFnFieldValue.split("##");
			strBcFnFieldValue = strTmpBcFnFieldValue[0];
			strBcFnFieldValueVf = strTmpBcFnFieldValue[1];
		}

		else {
			strBcFnFieldValueVf = "NO value";
		}

		if (strBcFnFieldValue.contains("$$") && !strBcFnFieldValue.contains("::")) {
			strBcFnFieldValue = BO_Utility.readOutputData(strBcFnFieldValue);

		}

		strBcFnClmFstNm = strBcFnFieldname.substring(0, strBcFnFieldname.indexOf('_'));
		strTmpClmLst = strBcFnFieldname.substring(strBcFnFieldname.indexOf('_') + 1, strBcFnFieldname.length());

		if (strTmpClmLst.contains("_"))

			strBcFnClmLstNm = strTmpClmLst.substring(0, strTmpClmLst.indexOf('_'));
		else

			strBcFnClmLstNm = strBcFnFieldname.substring(strBcFnFieldname.indexOf('_') + 1, strBcFnFieldname.length());

		if (!strBcFnClmFstNm.equals("swtclose") && !strBcFnClmFstNm.equals("swtto") && !strBcFnClmFstNm.equals("scrshot") && !strBcFnClmFstNm.equals("swtbk")
				&& !strBcFnClmFstNm.equals("cal") && !strBcFnClmFstNm.equals("Put")) {
			objActObj = fetching_exceldata.getObject(strBcFnFieldname, strBcFnFieldValue, strSheet);

		}

		if (!strBcFnClmFstNm.equals("swtclose") && !strBcFnClmFstNm.equals("swtto") && !strBcFnClmFstNm.equals("swtbk") && !strBcFnClmFstNm.equals("scrshot")
				&& !strBcFnClmFstNm.equals("cal") && !strBcFnClmFstNm.equals("Put")) {
			int incwait = 0;

			while ((objActObj.isEmpty()) && (incwait < 1) && (objActObj == null)) {
				Thread.sleep(5000);
				objActObj = fetching_exceldata.getObject(strBcFnFieldname, strBcFnFieldValue, strSheet);
				incwait = incwait + 1;
			}
		}

		if (!strBcFnFieldValue.contains("::")) {
			switch (strBcFnClmFstNm.toUpperCase()) {

	case "BTN":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found btn");
					strreexeFlg = false;
				}
		try {
					objActObj.get(0).click();
					custom_Reports.print_Report("INFO", "Perform Action",
							strBcFnClmLstNm + " button is clicked..<a href='" + custom_Reports.takeSnap()
									+ "'> Click here </a> to view the screen shot");

				} catch (Exception e) {

					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action", e.getLocalizedMessage() + "<a href='"
							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
					strreexeFlg = false;
				}
				break;

	case "BTNACT":

		if (objActObj.isEmpty() && (objActObj == null)) {
			custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found btn");
			strreexeFlg = false;
		}

		try {

			Actions actions = new Actions(driver);
			actions.moveToElement(objActObj.get(0)).click().build().perform();
			custom_Reports.print_Report("INFO", "Perform Action",
					strBcFnClmLstNm + " button is clicked..<a href='" + custom_Reports.takeSnap()
							+ "'> Click here </a> to view the screen shot");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			custom_Reports.print_Report("FAIL", "Perform Action", e.getLocalizedMessage() + "<a href='"
					+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
			strreexeFlg = false;
		}
		
		break;

				
			case "BTNJCLK":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found btn");
					strreexeFlg = false;
				}

				try {

					WebElement element = objActObj.get(0);
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", element);
					custom_Reports.print_Report("INFO", "Perform Action",
							strBcFnClmLstNm + " button is clicked..<a href='" + custom_Reports.takeSnap()
									+ "'> Click here </a> to view the screen shot");
				} catch (Exception e) {

					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action", e.getLocalizedMessage() + "<a href='"
							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
					strreexeFlg = false;
				}

				break;

			
			case "LNK":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found lnk");
					strreexeFlg = false;
				}

				try {

					WebElement lnkEle = objActObj.get(0);
					strTemp = lnkEle.getAttribute("href");
					String text = lnkEle.getText();
					// System.out.println(text);
					// System.out.println(strTemp);
					lnkEle.click();
					custom_Reports.print_Report("INFO", "Perform Action",
							strBcFnClmLstNm + " Link is clicked..<a href='" + custom_Reports.takeSnap()
									+ "'> Click here </a> to view the screen shot");
					
				} catch (Exception e) {
					// System.out.println(e);
					custom_Reports.print_Report("FAIL", "Perform Action",
							"Unknown exception occured while clicking the :" + strBcFnClmLstNm + "Link.<a href='"
									+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
					strreexeFlg = false;
				}

				break;

			case "CHK":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found chk");
					strreexeFlg = false;
				}

				try {

					WebElement chkEle = objActObj.get(0);
					String pathe = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet);
					WebDriverWait wait = new WebDriverWait(driver, 20);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pathe)));
					Actions actions = new Actions(driver);
					actions.moveToElement(chkEle).click().build().perform();

					custom_Reports.print_Report("INFO", "Perform Action",
							strBcFnClmLstNm + " value is selected in the checkbox");
					// chkEle.click();

				} catch (WebDriverException e) {
					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action",
							"Unknown exception occured while selecting the :" + strBcFnClmLstNm
									+ "value in the checkbox.<a href='" + custom_Reports.takeSnap()
									+ "'> Click here </a> to view the screen shot");

					strreexeFlg = false;
				}
				break;

			case "CHKALL":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found chk");
					strreexeFlg = false;
				}

				try {

					List<WebElement> chkEle;
					do {
						chkEle = driver.findElements(By.xpath("//label[@class='custom-control-label']"));
						System.out.println("Check Boxes : " + chkEle.size());
						// wait(1);
					} while (chkEle.size() == 0);

				} catch (WebDriverException e) {
					custom_Reports.print_Report("FAIL", "Perform Action",
							"Unknown exception occured while selecting the :" + strBcFnClmLstNm
									+ "value in the checkbox.<a href='" + custom_Reports.takeSnap()
									+ "'> Click here </a> to view the screen shot");
					strreexeFlg = false;
				}
				break;

			case "CUSCHK":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found chk");
					strreexeFlg = false;
				}

				try {

					WebElement chkEle = objActObj.get(0);

					String var = chkEle.getAttribute("for");

					checkbox = driver.findElement(By.xpath("//input[contains(@id,'" + var + "')]"));

					if (strBcFnFieldValue.equalsIgnoreCase("Enable")) {

						if (checkbox.getAttribute("class").contains("ng-empty")) {

							chkEle.click();

							Thread.sleep(2000);
							custom_Reports.print_Report("INFO", "Perform Action",
									strBcFnClmLstNm + " value is enabled  in the checkbox.");
						}

						else

							custom_Reports.print_Report("INFO", "Perform Action",
									strBcFnClmLstNm + " value is already enabled  in the checkbox.<a href='"
											+ custom_Reports.takeSnap()
											+ "'> Click here </a> to view the screen shot'");

					}

					else if (strBcFnFieldValue.equalsIgnoreCase("Disable")) {

						if (checkbox.getAttribute("class").contains("ng-not-empty")) {

							chkEle.click();

							custom_Reports.print_Report("INFO", "Perform Action",
									strBcFnClmLstNm + " value is disabled in the checkbox.<a href='"
											+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");

						} else

							custom_Reports.print_Report("INFO", "Perform Action",
									strBcFnClmLstNm + " value is already disabled in the checkbox.");

					}

				} catch (WebDriverException e) {
					custom_Reports.print_Report("FAIL", "Perform Action",
							"Unknown exception occured while selecting the :" + strBcFnClmLstNm
									+ "value in the checkbox.<a href='" + custom_Reports.takeSnap()
									+ "'> Click here </a> to view the screen shot");
					strreexeFlg = false;
				}
				break;

			case "GTXT":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found txt");
					strreexeFlg = false;
				}
				try {

					WebElement txtEle = objActObj.get(0);
					// System.out.println("String_value: " + strBcFnFieldValue);
					String[] strTemp = strBcFnFieldValue.split("\\@\\@");
					String strTestName = strTemp[0];
					String strFieldId = strTemp[1];
					if (!txtEle.isDisplayed()) {
						txtEle.click();
					}
					String text = txtEle.getText();

					if (text == null || text == "" || text == " " || text.isEmpty()) {
						text = txtEle.getAttribute("value");
					}

					// System.out.println(text);
					BO_Utility.writeOutputData(strTestName, strFieldId, text);

					custom_Reports.print_Report("INFO", "Perform Action",
							text + " value is enterted in " + strBcFnClmLstNm + " textbox");

				} catch (Exception e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Unknown exception occured ");
					strreexeFlg = false;
				}
				break;

			case "GETXT":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found txt");
					strreexeFlg = false;
				}
				try {

					WebElement txtEle = objActObj.get(0);
					// System.out.println("String_value: " + strBcFnFieldValue);
					String[] strTemp = strBcFnFieldValue.split("\\%\\%");
					String strTestName = strTemp[0];
					String strFieldId = strTemp[1];
					if (!txtEle.isDisplayed()) {
						txtEle.click();
					}
					String text = txtEle.getText();
					if (text == null || text == "" || text == " " || text.isEmpty()) {
						text = txtEle.getAttribute("value");
					}
					System.out.println(text);
					BO_Utility.writeOutputData(strTestName, strFieldId, text);

					custom_Reports.print_Report("INFO", "Perform Action",
							text + " value is enterted in " + strBcFnClmLstNm + " textbox");

				} catch (Exception e) {
					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action", "Unknown exception occured ");
					strreexeFlg = false;
				}
				break;

			case "GETDRPDN":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found txt");
					strreexeFlg = false;
				}
				try {
					WebElement txtEle = objActObj.get(0);
					System.out.println("String_value: " + strBcFnFieldValue);
					String[] strTemp = strBcFnFieldValue.split("\\%\\%");
					String strTestName = strTemp[0];
					String strFieldId = strTemp[1];
					if (!txtEle.isDisplayed()) {
						txtEle.click();
					}
					Select select = new Select(txtEle);
					txtEle = select.getFirstSelectedOption();
					String text = txtEle.getText();
				//	System.out.println("Selected element: " + text);
					BO_Utility.writeOutputData(strTestName, strFieldId, text);
					custom_Reports.print_Report("INFO", "Perform Action",
							text + " value is enterted in " + strBcFnClmLstNm + " textbox");

				} catch (Exception e) {
					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action", "Unknown exception occured ");
					strreexeFlg = false;
				}
				break;

			case "TXT":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found txt");
					strreexeFlg = false;
				}
				try {

					WebElement txtEle = objActObj.get(0);
						/*
						 * String pathe = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet);
						 * WebDriverWait wait = new WebDriverWait (driver, 30);
						 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pathe)));
						 */
					if(txtEle!=null){
						Thread.sleep(1000); 
						txtEle.click();
						Thread.sleep(1000);
						txtEle.clear();
					 }
										txtEle.sendKeys(strBcFnFieldValue);
						custom_Reports.print_Report("INFO", "Perform Action",
								strBcFnFieldValue + " value is enterted in " + strBcFnClmLstNm + " textbox.<a href='"
										+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
				} catch (Exception e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Unknown exception occured.<a href='"
							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
					strreexeFlg = false;
				}
				break;

			case "MHOV":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found txt");
					strreexeFlg = false;
				}
		try {
					
					//WebElement txtEle = objActObj.get(0);
					/*
					 * String text = txtEle.getText(); String pathe =
					 * BO_Utility.readJSONRepository(strBcFnFieldname, strSheet); WebDriverWait wait
					 * = new WebDriverWait(driver, 20);
					 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pathe)));
					 */
					
					Actions actions = new Actions(driver);
					actions.moveToElement(objActObj.get(0)).perform();
					Thread.sleep(5000);
					custom_Reports.print_Report("PASS", "Perform Action",
							" Mouse hovered in " + strBcFnClmLstNm + " element.<a href='"
									+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
					
				} catch (Exception e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Unknown exception occured ");
					strreexeFlg = false;
				}
				break;

			case "TAG":

				if (objActObj.isEmpty() && (objActObj == null)) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found txt");
					strreexeFlg = false;
				}
				try {

					List<WebElement> tagEles = objActObj;

					BO_Utility.addtag(strBcFnFieldValue, objActObj);

					custom_Reports.print_Report("INFO", "Perform Action",
							strBcFnFieldValue + " value is clicked in " + strBcFnClmLstNm + " tagbox");

				} catch (Exception e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Unknown exception occured ");
					strreexeFlg = false;
				}
				break;

			case "DRPDN":

				if (objActObj.isEmpty() || objActObj == null) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found drpdn");
					strreexeFlg = false;
				}

				Select select = new Select(objActObj.get(0));

				try {

					WebElement drpdnobj = null;
					int incDrpWait = 0;

					while ((drpdnobj == null) && (incDrpWait < 4)) {

						List<WebElement> objSltOpt = select.getOptions();
//						 for(WebElement options: objSltOpt)
	//					 System.out.println(options.getText());
						drpdnobj = BO_CommonFunction.getdrpdowntoselect(objSltOpt, strBcFnFieldValue); 

						if (!drpdnobj.equals(null)) {
							drpdnobj.click();
							break;
						} else {
							incDrpWait = incDrpWait + 1;
							Thread.sleep(3000);
						}

					}

					custom_Reports.print_Report("INFO", "Perform Action",
							strBcFnFieldValue + " value is Selected in " + strBcFnClmLstNm + " Dropdown");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action",
							"Unknown exception occured " + e + ", " + strBcFnFieldname);
					strreexeFlg = false;
				}

				break;

			case "MSDRPDN":

				if (objActObj.isEmpty() || objActObj == null) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found msdrpdn");
					strreexeFlg = false;
				}

				try {

					WebElement obj1 = objActObj.get(0);

					obj1.click();

					if (strBcFnFieldValue.contains("_")) {

						String[] strTmpBcFnFieldValue = strBcFnFieldValue.split("_");
						String strBcFnFieldValue1 = strTmpBcFnFieldValue[0];
						String strBcFnFieldValue2 = strTmpBcFnFieldValue[1];

						BO_Utility.callall(strBcFnFieldValue1, strBcFnFieldValue2, checkbox, strBcFnClmLstNm, driver);
					}
				} catch (Exception e) {
					custom_Reports.print_Report("FAIL", "Perform Action",
							"Unknown exception occured " + e + ", " + strBcFnFieldname);
					strreexeFlg = false;
				}

				break;

			case "SWTTO":
				try {
					Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
					browserName = cap.getBrowserName().toLowerCase();
					System.out.println(browserName);

					if ((browserName.equals("chrome")) && (strBcFnFieldValue.equalsIgnoreCase("Switch"))) {
						Thread.sleep(1000);
						driver.switchTo().frame(0);
						Thread.sleep(1000);
					}

					else {

				//		driver.switchTo().window(tabs.get(1));
						currentWindow = driver.getWindowHandle();
						System.out.println(currentWindow);
						ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
					 //   Set<String> handles = driver.getWindowHandles();
						System.out.println(handles);

						int winWait = 0;
						while (handles.size() < 2 && winWait < 100) {
							handles = (ArrayList<String>) driver.getWindowHandles(); 
							winWait++;
						}

						for (String windowsHandles : handles) {
							if (!windowsHandles.equals(currentWindow)) {
								driver.switchTo().window(windowsHandles);
								System.out.println(windowsHandles);
		//						strTitle = driver.getTitle();
		//						System.out.println(strTitle);
							}
						}
					}
				} catch (Exception e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Window not found ");
					strreexeFlg = false;
				}
				break;

			case "SWTCLOSE":

				try {
					
					ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
					int tabnum=Integer.parseInt(strBcFnFieldValue);  
					driver.switchTo().window(handles.get(tabnum));
					System.out.println(handles.get(tabnum));
					driver.close();
					System.out.println("SWITCH CLOSE");
				} catch (WebDriverException e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Window not found ");
				}

				break;
				
			case "SWTBK":

				try {

					if ((strBcFnFieldValue.equalsIgnoreCase("Switch"))) {

					driver.switchTo().window(currentWindow);
					System.out.println("SWITCH BACK");
					System.out.println(currentWindow);
					}
				} catch (WebDriverException e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Window not found ");
				}

				break;

			case "SCRSHOT":

				try {
					Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
					browserName = cap.getBrowserName().toLowerCase();
					// System.out.println(browserName);

					if ((browserName.equals("chrome")) && (strBcFnFieldValue.equalsIgnoreCase("Takesnap"))) {
						// Thread.sleep(3000);
						custom_Reports.print_Report("INFO", "Perform Action",
								"Screen shot" + " Screen shot is taken..<a href='" + custom_Reports.takeSnap()
										+ "'> Click here </a> to view the screen shot");
					}

				} catch (WebDriverException e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Screenshot is not taken ");
				}
				break;

			case "RDBT":

				try {
					if (objActObj.isEmpty()) {
						custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found rdbt");
						strreexeFlg = false;
					}
					if (strBcFnFieldValue.equals("done"))
						System.out.println("yes");
					WebElement rdbtEle = objActObj.get(0);
					rdbtEle.click();
					custom_Reports.print_Report("INFO", "Perform Action",
							strBcFnClmLstNm + " value is selected in radio options ");
				} catch (Exception e) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Unknown exception is occured");
					strreexeFlg = false;
				}
				break;

			case "PUT":

				if (strBcFnClmLstNm.equalsIgnoreCase("CurrentDate"))
					BO_CommonFunction.getCurrentDate(strBcFnFieldValueVf);

				break;

			case "GET":

				if (strBcFnClmLstNm.equalsIgnoreCase("Notify")) 
					BO_CommonFunction.Notify(driver, objActObj, strBcFnFieldname, strSheet);
				if (strBcFnClmLstNm.equalsIgnoreCase("Popup")) 
					BO_CommonFunction.Popup(driver, objActObj, strBcFnFieldname, strSheet);  
				if (strBcFnClmLstNm.equalsIgnoreCase("Prevdt"))
					BO_CommonFunction.datepickerprev(objActObj, driver, strBcFnFieldValue);   
				if (strBcFnClmLstNm.equalsIgnoreCase("Nextdt"))
					BO_CommonFunction.datepickernext(objActObj, driver, strBcFnFieldValue);  
				if (strBcFnClmLstNm.equalsIgnoreCase("gettext1"))
					BO_CommonFunction.getattrivalue(objActObj, strBcFnFieldValueVf, driver, strBcFnFieldname, strSheet,strBcFnClmFstNm);
				if (strBcFnClmLstNm.equalsIgnoreCase("pageload1"))
					BO_CommonFunction.pageloading(objActObj, strBcFnFieldValue, driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("assert"))
					BO_CommonFunction.assertcall(objActObj, strBcFnFieldValue, strSheet);  
				if (strBcFnClmLstNm.equalsIgnoreCase("gettext"))
					BO_CommonFunction.gettext(objActObj, strBcFnFieldValueVf, driver, strBcFnFieldname, strSheet,strBcFnClmFstNm);
				if (strBcFnClmLstNm.equalsIgnoreCase("staleissue"))
					BO_CommonFunction.staleissue(driver, objActObj);   


				break;

							
			case "CAL":

				if (strBcFnClmLstNm.equalsIgnoreCase("Numupdate"))
					BO_CommonFunction.numericupdate(strBcFnFieldValue); 
				if (strBcFnClmLstNm.equalsIgnoreCase("Relidcheck"))
					BO_CommonFunction.releaseidchecking(objActObj, strBcFnFieldValue, strSheet, data);  
				if (strBcFnClmLstNm.equalsIgnoreCase("Relidupdate"))
					BO_CommonFunction.releaseidupdating(strBcFnFieldValue);  
				if (strBcFnClmLstNm.equalsIgnoreCase("Notify"))
					BO_CommonFunction.Notify(driver, objActObj, strBcFnFieldname, strSheet);   
				if (strBcFnClmLstNm.equalsIgnoreCase("date"))
					BO_CommonFunction.datepicker(driver, strBcFnFieldValue); 
				if (strBcFnClmLstNm.equalsIgnoreCase("max"))
					BO_CommonFunction.maxwind(driver); 
				if (strBcFnClmLstNm.equalsIgnoreCase("updatenewpwd"))
					BO_CommonFunction.updatenewpwd(); 
				if (strBcFnClmLstNm.equalsIgnoreCase("resetpwd"))
					BO_CommonFunction.Clickresetlinklatest(objActObj, driver, strBcFnFieldValue); 
				if (strBcFnClmLstNm.equalsIgnoreCase("newtabfun"))
					BO_CommonFunction.newtab(driver, strBcFnFieldValue);
				if (strBcFnClmLstNm.equalsIgnoreCase("screenshot"))
					BO_CommonFunction.takeSnap(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("clickallchk"))
					BO_CommonFunction.check_All(driver, strBcFnFieldValue);
				if (strBcFnClmLstNm.equalsIgnoreCase("iframe"))
					BO_CommonFunction.iframes(driver, strBcFnFieldValue);
				if (strBcFnClmLstNm.equalsIgnoreCase("iframelmt"))
					BO_CommonFunction.switchToFrame(driver, strBcFnFieldValue ); 
				if (strBcFnClmLstNm.equalsIgnoreCase("UserUpdate"))
					BO_CommonFunction.userupdate(strBcFnFieldValue);
				if (strBcFnClmLstNm.equalsIgnoreCase("PwdUpdate"))
					BO_CommonFunction.pwdupdate();
				if (strBcFnClmLstNm.equalsIgnoreCase("zoom"))
					BO_CommonFunction.Zooming(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("inviswait"))
					BO_CommonFunction.actiononwaitinvis(driver, strBcFnFieldValue, strSheet);
				if (strBcFnClmLstNm.equalsIgnoreCase("waitforloc"))
					BO_CommonFunction.waitforlocator(driver, strBcFnFieldValue, strSheet);
				if (strBcFnClmLstNm.equalsIgnoreCase("waitforelemt"))
					BO_CommonFunction.waitForWebele(driver, objActObj, strBcFnFieldValue, strSheet, strBcFnClmFstNm);
				if (strBcFnClmLstNm.equalsIgnoreCase("pdownend"))
					BO_CommonFunction.pagedownend(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("chromewindow"))
					BO_CommonFunction.chromewindow(driver, strBcFnFieldValue);
				if (strBcFnClmLstNm.equalsIgnoreCase("waithalfmin"))
					BO_CommonFunction.waithalfmin(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("waitonemin"))
					BO_CommonFunction.waitonemin(driver); 
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononwait"))
					BO_CommonFunction.actiononwait(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("waitonesecond"))
					BO_CommonFunction.waitonesec(driver); 
				if (strBcFnClmLstNm.equalsIgnoreCase("nooftabs"))
					BO_CommonFunction.nooftabs(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("swtdef"))
					BO_CommonFunction.swtdef(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("swttab"))
					BO_CommonFunction.swtfirst(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("pdown"))
					BO_CommonFunction.pagedown(driver, strBcFnFieldValue); 
				if (strBcFnClmLstNm.equalsIgnoreCase("wait"))
					BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
				if (strBcFnClmLstNm.equalsIgnoreCase("tab"))
					BO_CommonFunction.tabfunction(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("qrimage"))
					BO_CommonFunction.qrverify(driver, strBcFnFieldValueVf);
				if (strBcFnClmLstNm.equalsIgnoreCase("waitforpageload"))
					BO_CommonFunction.drvreadystate(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononscrldn"))
					BO_CommonFunction.actiononscrldn(driver, objActObj);
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononrefresh"))
					BO_CommonFunction.actiononrefresh(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononclose"))
					BO_CommonFunction.actiononclose(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononalert"))
					BO_CommonFunction.actiononalert(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononwindback"))
					BO_CommonFunction.actiononwindback(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononenter"))
					BO_CommonFunction.actiononenter(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("actionwithinalert"))
					BO_CommonFunction.actionwithinalert(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononclick"))
					BO_CommonFunction.actiononclick(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("actiononrefpage"))
					BO_CommonFunction.actiononrefpage(driver);
				if (strBcFnClmLstNm.equalsIgnoreCase("fileDelete"))
					BO_CommonFunction.fileDelete(strBcFnFieldValue);
				if (strBcFnClmLstNm.equalsIgnoreCase("fileExist"))
					BO_CommonFunction.fileExist(strBcFnFieldValue);

			}

		}
		if ((strBcFnFieldValueVf.contains("$$")) && (!strBcFnClmFstNm.equals("Get")) && (!strBcFnClmFstNm.equals("Put"))
				&& (!strBcFnClmFstNm.equals("Cal"))) {
			BO_Verification.Resultverification(strBcFnFieldValueVf, strBusFn, strSheet);
		}

		if (strBcFnFieldValue.contains("::")) {

			BO_Validation_Verification.resultVerVal(strBcFnFieldValue, strBusFn, strSheet, objActObj, strBcFnClmLstNm,
					driver);

		}

	}

	}
