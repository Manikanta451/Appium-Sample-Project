package com.sp.pagefactory;

import org.openqa.selenium.WebDriver;
import com.sp.datainitialization.DataInt;
import com.sp.pageobjects.LoginPageObjects;

public class Login extends LoginPageObjects{
	
	public Login(WebDriver driver) {
		super(driver);
	}
	
	public void accountlogin(DataInt dataInt) throws Exception {
			waitForSeconds(5);
			btn.sendKeys("Manikanta Selenium Appium");
			waitForSeconds(2);
			myacc.click();
	   }	
		
}
