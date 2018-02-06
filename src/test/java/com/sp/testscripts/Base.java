package com.sp.testscripts;

import java.net.MalformedURLException;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.sp.pagefactory.DriverHome;
import com.sp.pagefactory.Login;
import com.sp.utilities.Xls_Reader;

/**
 * 
 * 
 * This is the base class for all the test suites,It executes before executing
 * the TestSuite Classes
 * 
 */

public class Base {
	
	public static final Logger LOG = Logger.getLogger(Base.class);
	public DriverHome driverhome;
	public Login login;
	public AppiumDriverLocalService appiumService;
    String appiumServiceUrl;
	public static String passMessage = null;
	public static String finalMessage = null;
	public static String skipMessage = null;
	public Xls_Reader xls;

	@BeforeTest
	@Parameters({"deviceName" , "platformName" })
	public void setup(String deviceName , String platformName) throws MalformedURLException, InterruptedException{
		try {
			  appiumService = AppiumDriverLocalService.buildDefaultService();
			  appiumService.start();
			  LOG.info("Device Name:"+deviceName);
			  LOG.info("PlatFormName:"+platformName);
		      appiumServiceUrl = appiumService.getUrl().toString();
			  LOG.info("Appium Service Address :"+appiumServiceUrl);
			  Thread.sleep(5000);
			  driverhome = new DriverHome(deviceName,platformName);
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void closesetup() throws Exception {
		try {
			Thread.sleep(5000);
			//driverhome.quitDriver();
			appiumService.stop();
		} catch (WebDriverException e) {
			e.printStackTrace();
			}
		}
}