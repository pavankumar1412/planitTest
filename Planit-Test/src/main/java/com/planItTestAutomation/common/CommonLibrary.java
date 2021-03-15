package com.planItTestAutomation.common;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.steadystate.css.parser.ParseException;


/**
 * The CommonLibrary class contains methods to perform common operations on JupiterToys.
 * @author Ram Pavan Kumar
 * @since 14-Mar 2021
 * @version 1.0
 */
public class CommonLibrary {
	
	public static ExtentReports csvExtent;
	public static ExtentTest csvTest;
	
	public static WebDriver driver = null;
	public static EventFiringWebDriver objdriver = null;
	static WebDriverWait objWait = null;	
	public static String appURL = "https://jupiter.cloud.planittesting.com/#/";
	
	@SuppressWarnings("unchecked")
	public static WebDriver initiateDriverInstance(String strDriver) {
		FirefoxProfile fxProfile = new FirefoxProfile();
		fxProfile.setPreference("browser.download.folderList", 2);
		fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		fxProfile.setPreference("browser.download.dir", System.getProperty("user.home"));
		fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip");
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(fxProfile);
		String downloadFilepath = System.getProperty("user.dir") + "/resources";
		ChromeOptions options1 = new ChromeOptions();
		@SuppressWarnings("rawtypes")
		HashMap chromePrefs = new HashMap();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		options1.setExperimentalOption("prefs", chromePrefs);
		options1.addArguments("--test-type");
		options1.addArguments("start-maximized", "disable-popup-blocking");
		options1.addArguments("--no-sandbox");

		String platform = System.getProperty("os.name");
		if (platform.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/resources/chromedriver.exe");
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/resources/geckodriver.exe");
		} else if (platform.contains("Linux")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/resources/geckodriver");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/resources/chromedriver");
		} else if (platform.contains("Mac")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/resources/geckodriver_mac");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/resources/chromedriver_mac");
		}
		if (driver == null) {
			if (strDriver.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver(options1);
				try{
				driver.manage().window().maximize();}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			} else {
				driver = new FirefoxDriver(options);
				try{
					driver.manage().window().maximize();}
					catch(Exception e)
					{
						e.printStackTrace();
					}
			}
			driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		}
		objdriver = new EventFiringWebDriver(driver);
		/*EventHandler handler = new EventHandler();
		objdriver.register(handler);*/
		return objdriver;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void startReport()throws Exception
	{
		csvExtent = new ExtentReports(System.getProperty("user.home") + "/AutomationReport/AutomationReport.html",
				true);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void InitiateReport(String testCaseName) throws IOException, InterruptedException, ParseException {
		System.out.println("Capturing extent Report of the test run");
		FileUtils.deleteDirectory(new File(System.getProperty("user.home").replace("\\", "/") + "/AutomationReport/"));		
		csvTest = csvExtent.startTest(testCaseName);
	}
	
	public static void logReport(boolean blnTestResult,String message, String fileName)  {
		try {
			takeScreenShot(fileName + ".jpeg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (blnTestResult) {
			csvTest.log(LogStatus.PASS, "<br/><b>Actual Result Screen Shot:</b>" + message + csvTest.addScreenCapture("./" + fileName + ".jpeg"));
		} else {
			csvTest.log(LogStatus.FAIL, "<br/><b>Actual Result Screen Shot:</b>" + message + csvTest.addScreenCapture("./" + fileName + ".jpeg"));
		}
	}
	
	public static void takeScreenShot(String fname) throws Exception {
		File scrFile = ((TakesScreenshot) objdriver).getScreenshotAs(OutputType.FILE);
		String imageFileDir = System.getProperty("selenium.screenshot.dir");
		String ExtentReportPath = System.getProperty("user.home") + "/AutomationReport/";
		if (imageFileDir == null)
			imageFileDir = System.getProperty("java.io.tmpdir");
		FileUtils.copyFile(scrFile, new File(imageFileDir, fname));
		FileUtils.copyFile(scrFile, new File(ExtentReportPath, fname));
	}	
	
	public static void flushExtentReport(){
		csvExtent.endTest(csvTest);
		csvExtent.flush();
		//csvExtent.close();
	}

	public static boolean waitForElementToBeVisible(String strXPath) {
		boolean blnElementVisible = true;
		objWait = new WebDriverWait(driver, 180);
		try {
			objWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXPath)));
		} catch (Exception e) {
			blnElementVisible = false;
		}
		return blnElementVisible;
	}
	public static void waitForOperationInSeconds(int i) throws InterruptedException
	{
		int milliSeconds = i*1000;
		Thread.sleep(milliSeconds);
	}
	
	
}
