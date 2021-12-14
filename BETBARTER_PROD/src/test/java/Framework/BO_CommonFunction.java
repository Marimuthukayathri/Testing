package Framework;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import dataconnection.data_Connections;

public class BO_CommonFunction {

	private static final Object Null = null;
	public static RemoteWebDriver driver;
	public static Alert alert;
	public static List<WebElement> objActObj;
	public static List<WebElement> objSltOpt; 
	private static int i, orderCount = 0;
	public static boolean strreexeFlg = true;
	public static String strWkGrpdf, strLogin, strPwd, strDrpdnObjNm, qrCodeFile, QRResult, strBcFnFieldValue1,
			filePath;
	public static File lastModifiedFile, lastModifiedFile1;

	public static JavascriptExecutor executor = (JavascriptExecutor) driver;

	public static void userupdate(String strBcFnFieldValue) throws IOException, FilloException, InterruptedException {

		// System.out.println(strBcFnFieldValue);
		String newuser = BO_Utility.readOutputData1(strBcFnFieldValue);
		String[] strTemp = strBcFnFieldValue.split("\\ ");
		String strTestName = strTemp[0];
		String strFieldName = strTemp[1];

		int Value = Integer.parseInt(newuser.replaceAll("[^0-9]", "")); // returns 123
		Value++;

		newuser = newuser.replaceAll("[^a-zA-Z]", "");
		// newuser = newuser.substring(0, 4);
		newuser = newuser + Value;
		System.out.println(newuser);
		BO_Utility.writeOutputData(strTestName, strFieldName, newuser);
		// Thread.sleep(4000);
	}

	public static void numericupdate(String strBcFnFieldValue) throws IOException, FilloException, InterruptedException {

		//System.out.println(strBcFnFieldValue);
		String newuser = BO_Utility.readOutputData1(strBcFnFieldValue);
		String[] strTemp = strBcFnFieldValue.split("\\ ");
		String strTestName = strTemp[0];
		String strFieldName = strTemp[1];
		long num = Long.valueOf(newuser);
		num++;
		newuser=String.valueOf(num);
		BO_Utility.writeOutputData(strTestName, strFieldName, newuser);
	}

	
	public static void pwdupdate() throws IOException, FilloException, InterruptedException {

		try {

			String newpass = BO_Utility.readOutputData("New_pass$$new_pass");
			int Value = Integer.parseInt(newpass.replaceAll("[^0-9]", "")); // returns 123
			Value++;
			newpass = newpass.substring(0, 5);
			newpass = newpass + Value;
			System.out.println(newpass);
			BO_Utility.writeOutputData("New_pass", "new_pass", newpass);

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void getCurrentDate(String strBcFnFieldValueVf) {

		String[] strTemp = strBcFnFieldValueVf.split("\\$\\$");
		String strTestName = strTemp[0];
		String strFieldId = strTemp[1];
		String strCurDate = new SimpleDateFormat("MM/dd/yyy").format(new Date());
		System.out.println(strCurDate);
		BO_Utility.writeOutputData(strTestName, strFieldId, strCurDate);
	}

	public static void actiononscrldn(RemoteWebDriver driver, List<WebElement> objActObj) {

		System.out.println("Scroll Down method called");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)", "");
	}

	public static void actiononrefresh(RemoteWebDriver driver) throws IOException, InterruptedException

	{
		WebElement refrshBtn = driver.findElementByXPath("//*[text()='Refresh']");
		custom_Reports.print_Report("INFO", "Perform Action", "Refresh button is clicked");
		for (int i = 1; i <= 500; i++) {
			refrshBtn.click();
			try {
				refrshBtn = driver.findElementByXPath("//button[text()='Refresh']");
				if (i == 500) {
					custom_Reports.print_Report("FAIL", "Perform Action", "Stamping is not performed");
					break;
					// driver.close(); In Progress Typesetting
				}
			} catch (Throwable e) {
				try {
					driver.findElementByXPath("//button[text()='Next']").click();
					custom_Reports.print_Report("INFO", "Perform Action", "Next button is clicked");
					break;
				} catch (Exception r) {
					BO_Action.strreexeFlg = false;
					custom_Reports.print_Report("FAIL", "Perform Action", "Stamping is not performed");
				}
			}
		}
	}

	public static void actiononwait(RemoteWebDriver driver) throws Throwable

	{
		Thread.sleep(3000);
		System.out.println("waiting");

	}

	public static void waitonesec(RemoteWebDriver driver) throws Throwable

	{
		Thread.sleep(1000);
		System.out.println("waiting");

	}

	public static void waithalfmin(RemoteWebDriver driver) throws Throwable

	{
		Thread.sleep(30000);
		System.out.println("waiting");

	}

	public static void waitonemin(RemoteWebDriver driver) throws Throwable

	{
		Thread.sleep(60000);
		System.out.println("waiting");

	}
	public static void actiononclose(RemoteWebDriver driver) {
		driver.close();
	}

	public static void actiononalert(RemoteWebDriver driver) {
		if (isAlertPresents(driver)) {
			alert.dismiss();
		}

	}

	public static void actionwithinalert(RemoteWebDriver driver) throws Throwable {
		if (isAlertPresents(driver)) {
			alert.accept();
			Thread.sleep(3000);
		}

	}

	public static boolean isAlertPresents(RemoteWebDriver driver) {
		try {
			alert = driver.switchTo().alert();
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch

	}

	public static void actiononwindback(RemoteWebDriver driver)

	{
		driver.navigate().back();
	}

	public static void actiononenter(RemoteWebDriver driver) {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();

	}

	public static void actiononclick(RemoteWebDriver driver) throws IOException

	{
		try {
			driver.findElementByXPath("//a").click();
		} catch (WebDriverException e) {
			driver.findElementByXPath("//a").click();
			custom_Reports.print_Report("INFO", "Perform Action", "Project linked is clicked");
		}
	}

	public static void actiononrefpage(RemoteWebDriver driver) throws IOException

	{
		driver.navigate().refresh();
	}

	public static void tabfunction(RemoteWebDriver driver) throws IOException

	{
		Actions keyDown = new Actions(driver);
		keyDown.sendKeys(Keys.chord(Keys.TAB)).perform();

	}

	public static void wait(RemoteWebDriver driver, List<WebElement> objActObj, String strBcFnClmFstNm)
			throws IOException {

		try {

			WebDriverWait waitForElement = new WebDriverWait(driver, 15);
			if (strBcFnClmFstNm.equals("drpdn"))
				waitForElement.until(ExpectedConditions.visibilityOfAllElements(objActObj));
			else {
				waitForElement.until(ExpectedConditions.visibilityOf(objActObj.get(0)));
			}

		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
	}

	public static WebElement getdrpdowntoselect(List<WebElement> objSltOpt, String strBcFnFieldValue)
			throws InterruptedException {
		WebElement drpdnobjret = null;
//		 objSltOpt = driver.findElementsByXPath("//select[@id='categoryId']/option[normalize-space()]");
		for ( WebElement drpdnobj : objSltOpt) { 
		//	drpdnobj=driver.findElementByXPath("//option[normalize-space()='"+strBcFnFieldValue+"']");
			strDrpdnObjNm = drpdnobj.getText(); 
			// strDrpdnObjNm.replaceAll(" ", "");
			strDrpdnObjNm = strDrpdnObjNm.trim();
			if (strDrpdnObjNm.isEmpty()) {
				Thread.sleep(2000);
				strDrpdnObjNm = drpdnobj.getText();
			}
			if (strBcFnFieldValue.equals("Null")) {
				strBcFnFieldValue = "";
			}
			if (strDrpdnObjNm.equalsIgnoreCase(strBcFnFieldValue)
					|| strDrpdnObjNm.equalsIgnoreCase("" + strBcFnFieldValue + "")
					|| strDrpdnObjNm.equalsIgnoreCase(" " + strBcFnFieldValue + " ")) {
				// System.out.println(strDrpdnObjNm);
				drpdnobjret = drpdnobj;
				break;
			} else if (drpdnobj.getAttribute("value").equalsIgnoreCase(strBcFnFieldValue)) {
				drpdnobjret = drpdnobj;
				break;
			}

		}
		return drpdnobjret;
	}

	public static boolean drvreadystate(RemoteWebDriver driver) throws Throwable {
	
	//	System.out.println("Driver Ready State is Running"); 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			while (!(js.executeScript("return document.readyState").toString().equals("complete"))) {
				Thread.sleep(4000);
			}
			return true;
		} catch (Exception e) {
//			JavascriptExecutor js = (JavascriptExecutor) driver;
			int incwait = 0;
			while (!(js.executeScript("return document.readyState").toString().equals("complete")) && (incwait < 3)) {
				Thread.sleep(5000);
				incwait++;
			}
			if ((js.executeScript("return document.readyState").toString().equals("complete"))) {
				return true;
			} else {
				System.out.println("Window is not in ready state" + e);
				return false;
			}
		}

	}

	public static String dynamicData(String strXpath) {
		if (strXpath.contains("$$")) {
			String[] strTemp = strXpath.split("\\+");
			String strTemp1 = strTemp[0];
			String strTcaseFnm = strTemp[1];
			String strTemp2 = strTemp[2];
			// strTemp1 = strTemp1 + "\"";
			// strTemp2 = "\""+ strTemp2 ;
			// td[text()='+ AsyncProcessList_Add_Edit_Delete$$txt_Implement +']

			try {
				strTcaseFnm = BO_Utility.readOutputData(strTcaseFnm);
			} catch (FilloException e) {

				System.out.println("Unable to readData in dynamicData Method" + e);
			}
			System.out.println(strTemp1);
			System.out.println(strTcaseFnm);
			System.out.println(strTemp2);
			strXpath = strTemp1 + strTcaseFnm + strTemp2;

		}
		return strXpath;
	}

	public static void chromewindow(RemoteWebDriver driver, String strBcFnFieldValue) throws Exception {

		String Sharepath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + strBcFnFieldValue + ".jpg";
		Runtime.getRuntime().exec("./FileUploader/FileUpload.exe" + " " + Sharepath);
		Thread.sleep(5000); 
		custom_Reports.print_Report("PASS", "Verification", strBcFnFieldValue + " is uploaded.<a href='"
				+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");

	}

	public static void fileDelete(String strBcFnFieldValue) throws AWTException, FilloException {

		String Sharepath = System.getProperty("user.dir") + "\\src\\test\\resources\\";
		File file = new File(Sharepath);
		for (File f : file.listFiles()) {

			if (f.getName().startsWith(strBcFnFieldValue)) {
				if (f.delete())
					System.out.println(strBcFnFieldValue + " is Deleted");
			}

		}
	}

	public static void fileExist(String strBcFnFieldValue)
			throws FilloException, WebDriverException, IOException, InterruptedException {
		Thread.sleep(5000);
		String Sharepath = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\";

		File file = new File(Sharepath);
		for (File f : file.listFiles()) {
			if (f.getName().startsWith(strBcFnFieldValue)) {
				if (f.exists()) {
					custom_Reports.print_Report("PASS", "Verification", strBcFnFieldValue + " is downloaded.<a href='"
							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
					break;
				}

				custom_Reports.print_Report("FAIL", "Verification", strBcFnFieldValue + " is NOT downloaded.<a href='"
						+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");

			}
		}
	}

	public static void gettext(List<WebElement> objActObj, String strBcFnFieldValueVf, RemoteWebDriver driver,
			String strBcFnFieldname, String strSheet, String strBcFnClmFstNm)
			throws IOException, FilloException, InterruptedException {

		try {

			String strXpath;
			strXpath = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet);
			objActObj = driver.findElementsByXPath(strXpath);
			BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
			// System.out.println("String_value: " + strBcFnFieldValueVf);
			String[] strTemp = strBcFnFieldValueVf.split("\\$\\$");
			String strTestName = strTemp[0];
			String strFieldId = strTemp[1];
			String text;
			if (!objActObj.get(0).isDisplayed()) {
				objActObj.get(0).click();
			}
			Thread.sleep(3000);
			text = objActObj.get(0).getText();
			// System.out.println(text);
			BO_Utility.writeOutputData(strTestName, strFieldId, text);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void getattrivalue(List<WebElement> objActObj, String strBcFnFieldValueVf, RemoteWebDriver driver,
			String strBcFnFieldname, String strSheet, String strBcFnClmFstNm)
			throws IOException, FilloException, InterruptedException {

		try {
			String strXpath;
			strXpath = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet);
			objActObj = driver.findElementsByXPath(strXpath);
			BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
			// drivers = driver;
			// System.out.println("String_value: " + strBcFnFieldValueVf);
			String[] strTemp = strBcFnFieldValueVf.split("\\$\\$");
			String strTestName = strTemp[0];
			String strFieldId = strTemp[1];
			String text;
			if (!objActObj.get(0).isDisplayed()) {
				objActObj.get(0).click();
			}
			Thread.sleep(3000);
			text = objActObj.get(0).getAttribute("value");
			// System.out.println(text);
			BO_Utility.writeOutputData(strTestName, strFieldId, text);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void Actclk(RemoteWebDriver driver, String path) {

		Actions ac = new Actions(driver);
		WebElement ele = driver.findElementByXPath(path);
		ac.moveToElement(ele).click().perform();
	}

	public static void Zooming(RemoteWebDriver driver) throws AWTException, InterruptedException {

		try {
			/*
			 * Dimension d = new Dimension(1000,800); //Resize current window to the set
			 * dimension driver.manage().window().setSize(d);
			 * System.out.println("Browser size is set");
			 */
			/*
			 * JavascriptExecutor jse = (JavascriptExecutor)driver;
			 * jse.executeScript("document.body.style.zoom = '100%';");
			 */
			Robot robot = new Robot(); // System.out.println("About to zoom in");
			for (int i = 0; i < 3; i++) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ADD);
				robot.keyRelease(KeyEvent.VK_ADD);
				robot.keyRelease(KeyEvent.VK_CONTROL);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void qrverify(RemoteWebDriver driver, String strBcFnFieldValueVf) throws Throwable {

		try {
			int att = 0;
			boolean res = false;

			while (att < 10) {

				try {
					driver.navigate().refresh();
					Thread.sleep(3000);
					// Runtime.getRuntime().exec("./QRCodeReader/qrread.exe");
					BO_CommonFunction.takeSnap(driver);
					BO_CommonFunction.actiononwait(driver);
					String destination = System.getProperty("user.dir") + "/QRCodeReader/images/QR_Code.jpg";

					File fileInput = new File(destination);
					BufferedImage bufferedImage = ImageIO.read(fileInput);
					LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
					BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
					Result result = new MultiFormatReader().decode(bitmap);
					QRResult = result.getText();
					System.out.println(QRResult);

					if (QRResult != null) {
						System.out.println("Success");
						System.out.println("QR Code is : " + result);
						System.out.println("QR image is available");
						custom_Reports.print_Report("PASS", "Perform Action", "QR code is available and Read.<a href='"
								+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
						res = true;
						break;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				att++;
				System.out.println(att);
			}
			if (res == false) {
				custom_Reports.print_Report("FAIL", "Perform Action", "QR code is not available.<a href='"
						+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void pageloading(List<WebElement> objActObj, String strBcFnFieldValue, RemoteWebDriver driver)
			throws Throwable {

		try { // System.out.println("Page load started");
			int att = 0;
			boolean res = false;
			while (att < 10) {
				try {
					BO_CommonFunction.actiononwait(driver);
					String text = objActObj.get(0).getText();
					// System.out.println(text);
					if (text.equals("") || text.equals(" "))
						text = "Null";
					String value = "Processing...", value1 = "%";
					if (!text.contains(value) && !text.contains(value1) && text.contains(strBcFnFieldValue)) {
						res = true;
						break;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				att++;
			}

			if (res == false) {
				custom_Reports.print_Report("FAIL", "Perform Action", "Page is not loading.<a href='"
						+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
			} else
				custom_Reports.print_Report("PASS", "Perform Action", "Page is loaded. <a href='"
						+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public static void drwait(RemoteWebDriver driver, List<WebElement> objActObj, String strBcFnClmFstNm)
			throws IOException {

		try {

			WebDriverWait waitForElement = new WebDriverWait(driver, 10);
			if (strBcFnClmFstNm.equals("drpdn"))
				waitForElement.until(ExpectedConditions.visibilityOfAllElements(objActObj));
			else {
				waitForElement.until(ExpectedConditions.visibilityOf(objActObj.get(0)));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void pagedown(RemoteWebDriver driver, String strBcFnFieldValue) throws IOException {

//		Actions action = new Actions(driver);
//		action.sendKeys(Keys.PAGE_DOWN).perform();
		  System.out.println("Page Down is Called"); JavascriptExecutor js =
				  (JavascriptExecutor) driver; js.executeScript("window.scrollBy(0,"+
				  strBcFnFieldValue +")");
			
	
	}

	public static void pagedownend(RemoteWebDriver driver) throws IOException {

		// System.out.println("Page Down is Called");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public static void createdoc(String docmt, RemoteWebDriver driver) throws Exception {

		String Sharepath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + docmt + ".docx";
		XWPFDocument document = new XWPFDocument();
		FileOutputStream out = new FileOutputStream(new File(Sharepath));
		document.write(out);
		out.close();
//		System.out.println("createdocument written successully");
	}

	public static void swtfirst(RemoteWebDriver driver) {
		System.out.println("Swt tab method is called");
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).build().perform();
	}

	public static void newtab(RemoteWebDriver driver, String strBcFnFieldValue) throws AWTException {

		System.out.println("New tab method is called");

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.open()");
			System.out.println("New tab is clicked");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			driver.navigate().to(strBcFnFieldValue);
			/*
			 * Robot robot = new Robot(); robot.delay(1000);
			 * robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_T);
			 * robot.keyRelease(KeyEvent.VK_T); robot.keyRelease(KeyEvent.VK_CONTROL);
			 * robot.delay(2000);
			 */
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void Clickresetlinklatest(List<WebElement> objActObj, RemoteWebDriver driver, String strBcFnFieldValue )
			throws InterruptedException {

		System.out.println("Reset link method is Called");
	try {
			objActObj = driver.findElementsByXPath(strBcFnFieldValue);
			List<WebElement> Linkele = objActObj;
			int size = Linkele.size();
			System.out.println("Size is  " + size);
			driver.findElement(By.xpath("("+strBcFnFieldValue+")[" + size + "]")).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void swtdef(RemoteWebDriver driver) {

		try {
			System.out.println("Swt DEFAULT method is called");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void maxwind(RemoteWebDriver driver) {

		try {
			System.out.println("Window Maximize method is called");
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	
	public static void nooftabs(RemoteWebDriver driver) {

		try {
			System.out.println("No. of tabs method is called");
			
			String parent_handle= driver.getWindowHandle();
			System.out.println(parent_handle);
			new WebDriverWait(driver,10).until(ExpectedConditions.numberOfWindowsToBe(2));
			Set<String> handles = driver.getWindowHandles();
			System.out.println(handles);
			for(String handle1:handles)
			    if(!parent_handle.equals(handle1))
			    {
			        driver.switchTo().window(handle1);
			        System.out.println(handle1);
			    }
			driver.manage().window().maximize();

		} catch (Exception e) {
		System.out.println(e.getMessage());
		}
	}

	public static void Webdrwait(RemoteWebDriver driver, int time, By path) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(path));
	}

	public static void inviswait(RemoteWebDriver driver, int time, By path) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(path));
	}

	public static void waitForWebele(RemoteWebDriver driver, List<WebElement> objActObj, String strBcFnFieldValue,
			String strSheet, String strBcFnClmFstNm) throws Throwable {

		try {
			String elementXPath = BO_Utility.readJSONRepository(strBcFnFieldValue, strSheet);
			// System.out.println("EleXpath" +elementXPath);
			WebElement element = driver.findElementByXPath(elementXPath);
			element = objActObj.get(0);

			int att = 0;
			boolean res = false;

			while (att < 10) {
				try {
					BO_CommonFunction.wait(driver, objActObj, strBcFnClmFstNm);
					if (element.isDisplayed()) {
						System.out.println("Element is Visible");
						res = true;
						break;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				att++;
				// System.out.println("att. No: "+att);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void waitforlocator(RemoteWebDriver driver, String strBcFnFieldValue, String strSheet)
			throws Throwable {

		try {
	
			String elementXPath = BO_Utility.readJSONRepository(strBcFnFieldValue, strSheet);
//			System.out.println("EleXpath" + elementXPath);
			By locator = By.xpath(elementXPath);
			int att = 0;
			boolean res = false;
			while (att < 60) {

				
					BO_CommonFunction.Webdrwait(driver, 2, locator);
					WebElement element = driver.findElement(locator);
					if (element.isDisplayed()) {
						res = true;
						custom_Reports.print_Report("INFO", "Perform Action", " Element is visible..<a href='"
								+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
						break;
					
				} 
				att++;
	} } catch (Exception e) {
			/*
			 * custom_Reports.print_Report("FAIL", "Perform Action", e.getLocalizedMessage()
			 * + "<a href='" + custom_Reports.takeSnap() +
			 * "'> Click here </a> to view the screen shot");
			 */	}	
	}

	public static void iframes(RemoteWebDriver driver, String strBcFnFieldValue) throws Throwable {

		List<WebElement> elements = driver.findElements(By.tagName("iframe"));
		int numberOfTags = elements.size();
		System.out.println("No. of Iframes on this Web Page are: " + numberOfTags);
		// Switch to the frame using the frame id
		System.out.println("Switching to the frame");
		driver.switchTo().frame(strBcFnFieldValue);
		custom_Reports.print_Report("INFO", "Perform Action", " Screen shot is taken..<a href='"
				+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
	}

	
	public static void switchToFrame(RemoteWebDriver driver, String strBcFnFieldValue) {
		
		WebElement frameElement = driver.findElementByXPath(strBcFnFieldValue); 
		
		try {
			
			List<WebElement> elements = driver.findElements(By.tagName("iframe"));
			int numberOfTags = elements.size();
			System.out.println("No. of Iframes on this Web Page are: " + numberOfTags);
			// Switch to the frame using the frame id
			System.out.println("Switching to the frame");
				if (frameElement.isDisplayed()) {
				driver.switchTo().frame(frameElement);
				System.out.println("Navigated to frame with element "+ frameElement);
			} else {
				System.out.println("Unable to navigate to frame with element "+ frameElement);
			}
		} catch (NoSuchFrameException e) {
			System.out.println("Unable to locate frame with element " + frameElement + e.getStackTrace());
		} catch (StaleElementReferenceException e) {
			System.out.println("Element with " + frameElement + "is not attached to the page document" + e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Unable to navigate to frame with element " + frameElement + e.getStackTrace());
		}
	}
	
	
	public static void actiononwaitinvis(RemoteWebDriver driver, String strBcFnFieldValue, String strSheet)
			throws Throwable {

		try {
			String elementXPath = BO_Utility.readJSONRepository(strBcFnFieldValue, strSheet);
			System.out.println("EleXpath is : " + elementXPath);
			By locator = By.xpath(elementXPath);

			int att = 0;
			boolean res = false;
			while (att < 10) {

				try {

					BO_CommonFunction.inviswait(driver, 5, locator);
					WebElement element = driver.findElement(locator);

					if (!element.isDisplayed()) {
						System.out.println("Element is not visible");
						executor.executeScript("arguments[0].click();", element);
						System.out.println("Element is clicked");
						res = true;
						custom_Reports.print_Report("INFO", "Perform Action", " Screen shot is taken..<a href='"
								+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
						break;
					}
				} catch (Exception e) {
					e.getMessage();
				}
				att++;
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public static String takeSnap(RemoteWebDriver driver) throws WebDriverException, IOException {
		String Sharepath;

		String destination = System.getProperty("user.dir") + "/Screenshots/ss1.jpg";

		String name = custom_Reports.getSystemName();
		File finalDestination = new File(destination);
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, finalDestination);
		if (name.equalsIgnoreCase("DESKTOP-OA1OO22"))
			Sharepath = System.getProperty("user.dir") + "/Screenshots/ss1.jpg";
		// "file://"+name+"/Users/111527/git/JMS_Automation_Suite/recent_execution_Reports/reports/images/"
		// + number + ".jpg";
		else
			Sharepath = "file://" + name + "/Screenshots/ss1.jpg";
		// To open the screenshots in different machine
		return Sharepath;
	}

	public static String testtype(RemoteWebDriver driver) throws Throwable {

		String testtype = null;
		try {
			String strDriverQry;
			strDriverQry = "Select * from Configuration";
			Recordset rs = data_Connections.qry_DataFile(strDriverQry);
			while (rs.next()) {
				ArrayList<String> arrFiledNames = rs.getFieldNames();
				for (String i : arrFiledNames) {
					if (i.contains("Test_Type")) {
						testtype = rs.getField(i);
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return testtype;
	}

	public static void check_All(RemoteWebDriver driver, String strBcFnFieldValue) throws Throwable {

		try {
			List<WebElement> chkEle;
			do {
				chkEle = driver.findElements(By.xpath(strBcFnFieldValue));
				System.out.println("Check Boxes : " + chkEle.size());
				for (WebElement chkbox : chkEle) {
					chkbox.click();
					System.out.println("Check Box is Selected");
					custom_Reports.print_Report("PASS", "Perform Action", "checkbox is selected.<a href='"
							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
		}
			} while (chkEle.size() == 0);

		} catch (WebDriverException e) {
			custom_Reports.print_Report("FAIL", "Perform Action",
					"Unknown exception occured while selecting in the checkbox.<a href='" + custom_Reports.takeSnap()
							+ "'> Click here </a> to view the screen shot");
			strreexeFlg = false;
		}

	}
	
	public static void updatenewpwd() {
		
	try {
		BO_Utility.writeOutputData("Newpwd", "pwdvalue", "Test@12345"); 
			
	} catch (IndexOutOfBoundsException e) { 
			System.out.println(e.getMessage()); 
		}
	}
	
	public static void assertcall (List<WebElement> objActObj, String strBcFnFieldValue, String strSheet) throws Throwable {

		try {
			
			if (objActObj.isEmpty() || (objActObj == null))  {
				BO_CommonFunction.waitforlocator(driver, strBcFnFieldValue, strSheet); 
				custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found..<a href='" 
			+ custom_Reports.takeSnap()+ "'> Click here </a> to view the screen shot");
		}
//			SoftAssert softAssertion= new SoftAssert();
			String actual = objActObj.get(0).getText();
			System.out.println("Actual Value is: "+actual);
			String expected = strBcFnFieldValue;  
			Assert.assertEquals(expected, actual); 
		} catch (WebDriverException e) {
		System.out.println(e.getMessage()); 
		if (objActObj.isEmpty() || (objActObj == null)) {
			custom_Reports.print_Report("FAIL", "Perform Action", "Element could not be found..<a href='" 
		+ custom_Reports.takeSnap()+ "'> Click here </a> to view the screen shot");
		}
	
	}

}

	public static void releaseidchecking (List<WebElement> objActObj, String strBcFnFieldValue, String strSheet, String data) throws Throwable {

		try {
			
			String s="ACTUAL AND EXPECTED VALUES SHOULD NOT BE SAME. UPDATE RELEASE ID IN CONFIG.XML AND EXCEL";
			/*
			 * Font font1 = new Font("SansSerif", Font.BOLD, 20); JLabel textfield = new
			 * JLabel(s); textfield.setFont(font1); textfield.setForeground(Color.RED); s =
			 * textfield.getText();
			 */
			String actual = BO_Utility.excelreaddata("Release", "Release Id", data);   
			String expected = strBcFnFieldValue;  
			Assert.assertNotEquals(s, expected, actual); 
		} catch (WebDriverException e) {
		System.out.println(e.getMessage()); 
	}

}

	public static void releaseidupdating (String strBcFnFieldValue) throws Throwable {

		try {
			
			BO_Utility.writeOutputData ("Release", "Release Id", strBcFnFieldValue);   
		} catch (WebDriverException e) {
		System.out.println(e.getMessage()); 
	}

}
	
	
	public static void datepicker(RemoteWebDriver driver, String strBcFnFieldValue) throws WebDriverException, IOException { 
				
try {
	//	String strToday = new  SimpleDateFormat("dd").format(new Date());
    WebElement dateWidget = driver.findElementByXPath("(//div[@class='datepicker datepicker-dropdown dropdown-menu datepicker-orient-left datepicker-orient-bottom']//table/tbody)[1]"); 
    List<WebElement> columns = dateWidget.findElements(By.xpath("(//div[@class='datepicker datepicker-dropdown dropdown-menu datepicker-orient-left datepicker-orient-bottom']//tbody)[1]/tr/td[@class='day']")); 
    
    	for (WebElement cell: columns) {
	      if (cell.getText().equals(strBcFnFieldValue)) {
	              cell.click();
	              custom_Reports.print_Report("PASS", "Perform Action",	strBcFnFieldValue + " is selected..<a href='"
	              + custom_Reports.takeSnap()+ "'> Click here </a> to view the screen shot");
	               break;
		            }
    			}
	} catch (Exception e) {
			System.out.println(e.getMessage());
			custom_Reports.print_Report("FAIL", "Perform Action", e.getLocalizedMessage() + "<a href='"
					+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
		}

	}   

	
	
	public static void datepickernext(List<WebElement> objActObj, RemoteWebDriver driver, String strBcFnFieldValue) throws WebDriverException, IOException { 
		
		try {
		    
			WebElement next = objActObj.get(0); 
			int count=Integer.parseInt(strBcFnFieldValue);  
				for (int i=0;i<count;i++) {
					next.click();
			 }         custom_Reports.print_Report("PASS", "Perform Action",	strBcFnFieldValue + " times clicked..<a href='"
		              + custom_Reports.takeSnap()+ "'> Click here </a> to view the screen shot");
			} catch (Exception e) {
					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action", e.getLocalizedMessage() + "<a href='"
							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
				}

			}   

	
	public static void Popup (RemoteWebDriver driver,  List<WebElement> objActObj, String strBcFnFieldname, String strSheet) throws WebDriverException, IOException { 
		
		try {
				
				int att = 0;
				boolean res = false;
		        String elementXPath = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet); 
				System.out.println("EleXpath" + elementXPath);
				By locator = By.xpath(elementXPath);

				while (att < 10) {
		try {
				WebDriverWait wait = new WebDriverWait(driver, 5);
				WebElement elemt = objActObj.get(0); 
		        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)); 
				driver.findElementByXPath(elementXPath).click();
				System.out.println("Element is Clicked");
			res=true;
			break;
		} catch (Exception e) {
			}
			att++;
		} 	} catch (Exception e) {
				System.out.println(e.getMessage());
		}   
	}

	
	
public static void Notify (RemoteWebDriver driver,  List<WebElement> objActObj, String strBcFnFieldname, String strSheet) throws WebDriverException, IOException { 
		
	try {
			int att = 0;
			boolean res = false;
	        String elementXPath = BO_Utility.readJSONRepository(strBcFnFieldname, strSheet); 
			System.out.println("EleXpath" + elementXPath);
			By locator = By.xpath(elementXPath);

			while (att < 10) {
	try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			WebElement elemt = objActObj.get(0); 
	        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)); 
				driver.findElementByXPath(elementXPath).click();
				System.out.println("Element is Clicked");
		res=true;
		break;
	} catch (Exception e) {
		}
		att++;
	} 	} catch (Exception e) {
			System.out.println(e.getMessage());
	}   
}
	
	public static void datepickerprev(List<WebElement> objActObj, RemoteWebDriver driver, String strBcFnFieldValue)
		throws WebDriverException, IOException { 
		
		try {
		    
			WebElement next = objActObj.get(0); 
			int count=Integer.parseInt(strBcFnFieldValue);  
				for (int i=0;i<count;i++) {
					next.click();
			 }         custom_Reports.print_Report("PASS", "Perform Action",	strBcFnFieldValue + " times clicked..<a href='"
		              + custom_Reports.takeSnap()+ "'> Click here </a> to view the screen shot");
			} catch (Exception e) {
					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action", e.getLocalizedMessage() + "<a href='"
							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
				}

			}   
	
	public static void staleissue (RemoteWebDriver driver, List<WebElement> objActObj ) throws Throwable {

	try {
			Actions actions = new Actions(driver);
			System.out.println("Stale issue function called"); 
			int att = 0;
			boolean res = false;
			WebElement elemt = objActObj.get(0);
			
			while (att < 5) {

			try {
					if(elemt.isDisplayed()) {
					System.out.println("Element is available");
					actions.moveToElement(elemt).click().build().perform();
					res = true;
					custom_Reports.print_Report("PASS", "Perform Action", "Element is clicked.<a href='"
								+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
					break;
				}
				} catch (StaleElementReferenceException e) { 
					System.out.println(e.getMessage());
					custom_Reports.print_Report("FAIL", "Perform Action", "Element is not clicked.<a href='"
							+ custom_Reports.takeSnap() + "'> Click here </a> to view the screen shot");
				}

				att++;
				System.out.println(att);
		}
	
		} catch (Exception e) { 
			System.out.println(e.getMessage());
		}
	
	}


	

}    