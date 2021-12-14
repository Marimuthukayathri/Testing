package Framework;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.codoid.products.fillo.Recordset;
import dataconnection.data_Connections;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Load_driver {

	public static RemoteWebDriver driver;     

	public static RemoteWebDriver invokeBrowser() throws Throwable  {
	 
		String strDriverQry;
		
		try {
			
			String strDrvJekins = System.getProperty("browser");
			//System.out.println(strDrvJekins);

			if(strDrvJekins==null) 
		{
			strDriverQry = "Select * from Configuration";
			Recordset rs = data_Connections.qry_DataFile(strDriverQry);
			while (rs.next()) {
				ArrayList<String> arrFiledNames = rs.getFieldNames();
				for (String i : arrFiledNames) {
					if (i.contains("browser")) {
						if(rs.getField(i).equalsIgnoreCase("Chrome"))
					{
								
								  System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
								  ChromeOptions options = disable(); 
								  WebDriverManager.chromedriver().setup();
								  driver = new ChromeDriver(options);
								 	
								/*
								 * System.setProperty("webdriver.gecko.driver","./drivers/geckodriver.exe");
								 * @SuppressWarnings("unused") WebDriver driver = new FirefoxDriver();
								 */	
								  break;
			}
						else if (rs.getField(i).equalsIgnoreCase("IE"))
						{
							System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
							driver = new InternetExplorerDriver();
							break;
						} 
					}
				}
			}
			
		}
	 else if(strDrvJekins.equals("Chrome")) {
			
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			ChromeOptions options = disable();
			driver = new ChromeDriver(options);
			
		}
		else if(strDrvJekins.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			
		} 
	
		} catch (Exception e) {
			System.out.println(e +"Load Driver");
			e.printStackTrace();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		try {
			strDriverQry = "Select * from Configuration";
			Recordset rs = data_Connections.qry_DataFile(strDriverQry);
			while (rs.next()) {
				ArrayList<String> arrFiledNames = rs.getFieldNames();
				for (String i : arrFiledNames) {
					if (i.contains("URL")) {
						driver.navigate().to(rs.getField(i)); 
						break;
					}
				}
			}
		} catch (InvalidArgumentException e) {
	//		e.printStackTrace();
		}
		return driver;
	}

	public static void closeBrowser() {
	
		driver.quit();
	}
	
		public static ChromeOptions disable()
		
		
		{ 
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
		    options.addArguments("ignore-certificate-errors");
		    options.setAcceptInsecureCerts(true);
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("safebrowsing.enabled", "true"); //this is the needed configuration
			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); 
			options.setExperimentalOption("prefs", chromePrefs);
			return options;
		}
		
		
		
		

}
