package com.sp.pagefactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sp.utilities.PropertiesFileReader;

/**
 * 
 * 
 * This Class is the base class for the entire script the driver and platform are initialized here
 * 
 * 
 */

public class CommonBase {
	
	public String browserName;
	public String MobileCapabilityType;
	public String str;
	public String excep;
	public static WebElement webelement = null;
	public WebDriverWait dwait;
	public  AppiumDriver<MobileElement> driver;
	WebDriverWait	wait;
	String 			os;
	Process p;
	DesiredCapabilities capabilities = new DesiredCapabilities();
	public final int elementTimeOut = Integer.parseInt(PropertiesFileReader.getproperty("element.time.out"));
	public final String  applicationpackage= String.valueOf(PropertiesFileReader.getproperty("APP_PACKAGE"));
	public final String  applicationactivity= String.valueOf(PropertiesFileReader.getproperty("APP_ACTIVITY"));
	public final String  nameofdevice= String.valueOf(PropertiesFileReader.getproperty("DEVICE_NAME"));
	public final String  nameofplatform= String.valueOf(PropertiesFileReader.getproperty("PLATFORM_NAME"));
	public final String  version=(PropertiesFileReader.getproperty("PLATFORM_VERSION"));
	public final String  androidpath= String.valueOf(PropertiesFileReader.getproperty("ANDROID_APP_PATH"));
	public final String  emulatorandroid= String.valueOf(PropertiesFileReader.getproperty("EMULATOR_NAME"));
	public final String  versionemulator=(PropertiesFileReader.getproperty("PLATFORM_VERSION_EMULATOR"));
	public final String  nameofdevice1= String.valueOf(PropertiesFileReader.getproperty("DEVICE_NAME1"));
	public final String  iphoneversion=(PropertiesFileReader.getproperty("PLATFORM_VERSION_IPHONE"));
	public final String  nameofplatform1= String.valueOf(PropertiesFileReader.getproperty("PLATFORM_NAME1"));
	public final String  nodePath= String.valueOf(PropertiesFileReader.getproperty("NODE_PATH"));
	public final String  appiumJSPath= String.valueOf(PropertiesFileReader.getproperty("JS_PATH"));
	public final String  iospath= String.valueOf(PropertiesFileReader.getproperty("IOS_APP_PATH"));
	public final String  browsertype= String.valueOf(PropertiesFileReader.getproperty("BROWSER"));
	public static String reportpath1 = System.getProperty("user.dir")+ System.getProperty("file.separator")+ "test-output"+ System.getProperty("file.separator")+ "TestReport.xlsx";
	public static String reportpath2 = System.getProperty("user.dir")+ System.getProperty("file.separator")+ "test-output"+ System.getProperty("file.separator")+ "Extent.html";
	public static String reportpath3 = System.getProperty("user.dir")+ System.getProperty("file.separator")+ "src/main/java/com/sp/test/data"+ System.getProperty("file.separator")+ "TestCases.xlsx";
	String cmd = nodePath + " " + appiumJSPath;

	public static final Logger LOG = Logger.getLogger(CommonBase.class);

	@SuppressWarnings({"unchecked" })
	protected CommonBase(WebDriver driver) {
		if (this.driver == null) {
			this.driver = (AppiumDriver<MobileElement>) driver;
		}
		dwait = new WebDriverWait(driver, 20);
	}
	
	public CommonBase(String deviceName, String platformName) {
		createWebDriver(deviceName, platformName);

	}
	
	public void createWebDriver(String deviceName, String platformName) {
			this.os = platformName;
			try{
				if(os.equalsIgnoreCase("android")){
					//this.appiumStart();
					//Thread.sleep(5000);
					this.androidSetup();
					driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			}else if (deviceName.equalsIgnoreCase("androidemulator")){
					//this.appiumStart();
					//Thread.sleep(5000);
					this.emulatorandroid();
					driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
				}else if (os.equalsIgnoreCase("ios")){
					/*this.appiumStart();
					Thread.sleep(5000);*/
					this.iosSetup();
				    driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
				}
				wait = new WebDriverWait(driver, 20);
				driver.manage().timeouts().implicitlyWait(15 , TimeUnit.SECONDS);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	public void androidSetup() throws MalformedURLException {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/Apps");
		File app = new File(appDir, androidpath);
		capabilities.setCapability("deviceName", nameofdevice);
		capabilities.setCapability("platformVersion", version);
		capabilities.setCapability("platformName", nameofplatform);
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("appPackage", applicationpackage);
		capabilities.setCapability("appActivity", applicationactivity);
		capabilities.setCapability("autoAcceptAlerts",true); 
	}
	
	public void emulatorandroid() throws MalformedURLException {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/Apps");
		File app = new File(appDir,androidpath );
		capabilities.setCapability("deviceName", emulatorandroid);
		capabilities.setCapability("platformVersion", versionemulator);
		capabilities.setCapability("platformName", nameofplatform);
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("appPackage", applicationpackage);
		capabilities.setCapability("appActivity", applicationactivity);
		capabilities.setCapability("autoAcceptAlerts",true); 
	}

	public void browserselection() throws MalformedURLException{
		   //capabilities.setCapability("browserName",BrowserType.CHROME);
	       capabilities.setCapability("browseraName", browsertype);
		   capabilities.setCapability("platformName", nameofplatform);
		   capabilities.setCapability("deviceName", nameofdevice);
		   capabilities.setCapability("platformVersion", version);
	}
	
	public void iosSetup() throws MalformedURLException {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/Apps");
		File app = new File(appDir, iospath);
		capabilities.setCapability("deviceName", nameofdevice1);
		capabilities.setCapability("platformVersion", iphoneversion);
		capabilities.setCapability("platformName", nameofplatform1);
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("autoAcceptAlerts",true); 
	}
	
	public void safarisetup() throws MalformedURLException {
	        capabilities.setCapability("deviceName", "iPhone 5");
	        capabilities.setCapability("platformName", "iOS");
	        capabilities.setCapability("platformVersion", "9.3.2");
	        capabilities.setCapability("browserName", "safari");
	        capabilities.setCapability("autoAcceptAlerts",true); 
	    }

	public void appiumStart() throws IOException, InterruptedException {
	  p = Runtime.getRuntime().exec(cmd);
	  TimeUnit.MINUTES.sleep(10);
	  if (p != null) {
	   System.out.println("Appium server Is started now.");
	  }
	 }

	public void appiumStop() throws IOException {
	  if (p != null) {
	   p.destroy();
	  }
	  System.out.println("Appium server Is stopped now.");
	 }
	 
	public void appiumStart1() throws IOException, InterruptedException {
	  CommandLine command = new CommandLine("cmd");
	  command.addArgument("c:/");
	  command.addArgument(nodePath);
	  command.addArgument(appiumJSPath);
	  command.addArgument("--address");
	  command.addArgument("127.0.0.1");
	  command.addArgument("--port");
	  command.addArgument("4723");
	  command.addArgument("--no-reset");
	  command.addArgument("--log");
	  command.addArgument("D://appiumLogs.txt");
	  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
	  DefaultExecutor executor = new DefaultExecutor();
	  executor.setExitValue(1);
	  executor.execute(command, resultHandler);
	  TimeUnit.MINUTES.sleep(15);
	 }

	public static void appiumStop1() throws IOException {
	  CommandLine command = new CommandLine("cmd");
	  command.addArgument("c:/");
	  command.addArgument("taskkill");
	  command.addArgument("/F");
	  command.addArgument("/IM");
	  command.addArgument("node.exe");
	  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
	  DefaultExecutor executor = new DefaultExecutor();
	  executor.setExitValue(1);
	  executor.execute(command, resultHandler);
	 }
	 
	public void quitDriver() {
		driver.close();
	}
	
	public void waitForPageToLoad(MobileElement id) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(id));
	}

	public void waitForElementToDisAppear(String id) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
	}

	public WebElement waitForElement(MobileElement arg) {
		waitForPageToLoad(arg);
		WebElement el = arg;
		return el;
	}

	public void swipeRightUntilTextExists(String expected) {
		do {
			swipeRight();
		} while (!driver.getPageSource().contains(expected));
	}

	public void swipeLeftUntilTextExists(String expected) {
		do {
			swipeLeft();
		} while (!driver.getPageSource().contains(expected));
	}

	public void swipeRight() {
		Dimension size = driver.manage().window().getSize();
		int startx = (int) (size.width * 0.9);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		driver.swipe(startx, starty, endx, starty, 1000);
	}

	public void swipeLeft() {
		Dimension size = driver.manage().window().getSize();
		int startx = (int) (size.width * 0.8);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		driver.swipe(startx, starty, endx, starty, 1000);
	}

	public void scrollDirection(String Id, SwipeElementDirection arg) {
		MobileElement e = (MobileElement) driver.findElementById(Id);
		e.swipe(arg, 1000);
	}

	public void setContext(String context) {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		}
		driver.context((String) contextNames.toArray()[1]); // set context to WEBVIEW_1

		LOG.info("Current context" + driver.getContext());
	}
	
	public void clickBackButton(){
		driver.navigate().back();
	}
	
	public String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
	
	public void waitForVisibilityOf(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
	
    public void waitForClickabilityOf(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void scrollPageUp() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        swipeObject.put("startX", 0.50);
        swipeObject.put("startY", 0.95);
        swipeObject.put("endX", 0.50);
        swipeObject.put("endY", 0.01);
        swipeObject.put("duration", 3.0);
        js.executeScript("mobile: swipe", swipeObject);
    }

    public void swipeLeftToRight() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        swipeObject.put("startX", 0.01);
        swipeObject.put("startY", 0.5);
        swipeObject.put("endX", 0.9);
        swipeObject.put("endY", 0.6);
        swipeObject.put("duration", 3.0);
        js.executeScript("mobile: swipe", swipeObject);
    }

    public void swipeRightToLeft() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        swipeObject.put("startX", 0.9);
        swipeObject.put("startY", 0.5);
        swipeObject.put("endX", 0.01);
        swipeObject.put("endY", 0.5);
        swipeObject.put("duration", 3.0);
        js.executeScript("mobile: swipe", swipeObject);
    }

    public void swipeFirstCarouselFromRightToLeft() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        swipeObject.put("startX", 0.9);
        swipeObject.put("startY", 0.2);
        swipeObject.put("endX", 0.01);
        swipeObject.put("endY", 0.2);
        swipeObject.put("duration", 3.0);
        js.executeScript("mobile: swipe", swipeObject);
    }

    public void performTapAction(WebElement elementToTap) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("x", (double) 360); // in pixels from left
        tapObject.put("y", (double) 170); // in pixels from top
        tapObject.put("element", Double.valueOf(((RemoteWebElement) elementToTap).getId()));
        js.executeScript("mobile: tap", tapObject);
    }
   
	public WebElement findElement(By by) throws Exception {
		WebElement element = null;
		for (int i = 1; i <= elementTimeOut; i++) {
			try {
				element = this.driver.findElement(by);
			} catch (WebDriverException e) {
				if (i == elementTimeOut) {
					throw e;
				}

				try {
					Thread.sleep(1000L);
				} catch (InterruptedException ie) {

					throw new RuntimeException(
							"Exception occured in sleep method");
				}
			}
		}
		if (element != null) {
			return element;
		} else {
			throw new WebDriverException("Cannot find element with "
					+ by.getClass());
		}

	}
	
	public boolean isElementExist(WebElement elemetName) {

		boolean present = true;
		try {
			elemetName.isDisplayed();
			present = true;

		} catch (NoSuchElementException e) {
			present = false;
		}

		return present;
	}
	
	public static void emailreport() throws Exception{		 
		 sendPDFReportByGMail("seleniumautomatonreports@gmail.com", "1111111!", "mani6747@gmail.com", "Amazon App Automation Report", "");
	 }	 
	  
	private static void sendPDFReportByGMail(String from, String pass, String to, String subject, String body) throws Exception {
		 System.out.println("Waiting for generating Testreports....");
		 System.setProperty("javax.net.ssl.trustStore", "C://Program Files//Java//jre1.8.0_101//lib//security//cacerts");
		 System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		 Properties props = System.getProperties();		 
		 String host = "smtp.gmail.com";
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtp.host", host);
	     props.put("mail.smtp.user", from);
	     props.put("mail.smtp.password", pass);
	     props.put("mail.smtp.auth", "true");	  
	     props.put("mail.smtp.socketFactory.port", "465");  
	     props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
	     props.put("mail.smtp.auth", "true");  
	     props.put("mail.smtp.port", "465");  
		 Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
			 
				protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("from","pass");

				}
			});			 
		 MimeMessage message = new MimeMessage(session);		 
		 try {
			 //Set from address
			 message.setFrom(new InternetAddress(from));
			 message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			 //Set subject
			 message.setSubject(subject);
			 message.setText(body);
			 BodyPart objMessageBodyPart = new MimeBodyPart();
			 objMessageBodyPart.setText("Hi,"
			 		+ "\n"
			 		+ "\n"
			 		+ "      Please Find The Attached Automation Report Files,please download and then open it!"
			 		+ "\n"
			 		+ "\n");
			 Multipart multipart = new MimeMultipart();
			 multipart.addBodyPart(objMessageBodyPart);
			 objMessageBodyPart = new MimeBodyPart();
			 addAttachment(multipart, reportpath1);
			 addAttachment(multipart, reportpath2);
			 addAttachment(multipart, reportpath3);
			 message.setContent(multipart);
			 Transport transport = session.getTransport("smtp");
			 transport.connect(host, from, pass);
			 transport.sendMessage(message, message.getAllRecipients());
			 transport.close();		 
		}
		catch (AddressException ae) {		 
			ae.printStackTrace();		 
		} 
		catch (MessagingException me) {		 
			me.printStackTrace();		 
		}
		System.out.println("TestReports has been Sent Successfully....!!!");
}

	private static void addAttachment(Multipart multipart, String filename)throws Exception{
		    DataSource source = new FileDataSource(filename);
		    BodyPart messageBodyPart = new MimeBodyPart();        
		    messageBodyPart.setDataHandler(new DataHandler(source));
		    messageBodyPart.setFileName(filename);
		    multipart.addBodyPart(messageBodyPart);
		}
	
	public static void waitForSeconds(int Sec){
		long start = System.currentTimeMillis();//returns the current time in milliseconds
		long stop = start+Sec*1000;
		while(System.currentTimeMillis()<stop){
		}		
	}
}