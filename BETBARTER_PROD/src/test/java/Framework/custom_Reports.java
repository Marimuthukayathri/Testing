package Framework;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.log4testng.Logger;

//import com.relevantcodes.extentreports.HTMLReporter;
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.MediaEntityModelProvider;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class custom_Reports {
	public static ExtentReports extent;
	public static ExtentTest logger;
	static String filePath;
	static final Logger log = Logger.getLogger(custom_Reports.class);
    static String envExecStatus;

	public static void startReport() {

		String strCurDate = new SimpleDateFormat("yyyyMMdd_HH_mm_ss").format(new Date());
		filePath = "./execution_Reports/Betbarter automation report_desktop"+strCurDate+".html";// +strCurDate+".html";
	
		extent = new ExtentReports(filePath, true);
		extent.addSystemInfo("Application", "BETBARTER").addSystemInfo("User Name", "Mari");
		extent.loadConfig(new File("./config.xml"));
	}

	public static void strtTest(String Tc_Name) {

		logger = extent.startTest(Tc_Name);
    }
	
	public static void print_Report(String strStatus, String strStep, String strDesc) throws IOException {

		System.out.println(strStatus + "--->" + strStep + ": " + strDesc);
		if (strStatus.toUpperCase().equals("PASS")) {
			logger.log(LogStatus.PASS, strStep, strDesc);

		} else if (strStatus.toUpperCase().equals("FAIL")) {
			logger.log(LogStatus.FAIL, strStep, strDesc);
			envExecStatus = "Fail";
		} else if (strStatus.toUpperCase().equals("INFO")) {
			logger.log(LogStatus.INFO, strStep, strDesc);

		} else if (strStatus.toUpperCase().equals("WARNING")) {
			logger.log(LogStatus.WARNING, strStep, strDesc);

		}
	}

	public static void endReport() {
		
		extent.flush();
		extent.endTest(logger);
	}
	
	
	public static void copy_LatestReport() throws IOException{
		
	try {
	
			File source = new File(filePath);			
			File dest = new File("./recent_execution_Reports/Betbarter automation report_desktop.html");
			if(dest.exists()) 
				dest.delete();
			FileUtils.copyFile(source, dest);
			
			if(envExecStatus.equals("Fail")) {
				Assert.fail();
	        }

	} catch(NullPointerException e) {
			System.out.println(e.getMessage()); 
		} 
	}
	

	public static String takeSnap() throws WebDriverException, IOException {

		String Sharepath;
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; // Snapshort for unique Name
		String destination = System.getProperty("user.dir") + "/recent_execution_Reports/reports/images/" + number + ".jpg";
		String name = getSystemName();
		File finalDestination = new File(destination);
		FileUtils.copyFile(Load_driver.driver.getScreenshotAs(OutputType.FILE), finalDestination);
		if(name.equalsIgnoreCase("DESKTOP-OA1OO22"))
		 Sharepath = System.getProperty("user.dir") + "/recent_execution_Reports/reports/images/" + number + ".jpg";
		else
		Sharepath = "file://"+name+"/recent_execution_Reports/reports/images/" + number + ".jpg";
		// To open the screenshots in different machine
		return Sharepath;
	}
	
	
	public static String  getSystemName(){  
	
	try{
	        InetAddress inetaddress=InetAddress.getLocalHost(); //Get LocalHost reference
	 //	    System.out.println(inetaddress);
	        String name = inetaddress.getHostName(); //Get Host Name
	//        System.out.println(name);
	      return name;
	    }
	    catch(Exception E){
	        E.printStackTrace();  //print Exception StackTrace
	        return null;
	    }
	}
}
