package com.sp.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DriverHome extends CommonBase {
	
	public DriverHome(WebDriver driver) {
		super(driver);

	}

	public DriverHome(String deviceName, String platformName) {
		super(deviceName, platformName); 
	}

	public Login getLogin(){
		return PageFactory.initElements(driver, Login.class);
	}
	
	
}

