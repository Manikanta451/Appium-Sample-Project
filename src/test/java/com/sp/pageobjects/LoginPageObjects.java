package com.sp.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.sp.pagefactory.CommonBase;

public class LoginPageObjects extends CommonBase {

	protected LoginPageObjects(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(how = How.XPATH, using = "//android.widget.EditText[@content-desc='my_text_fieldCD']")
	public WebElement btn;
	
	@FindBy(how = How.ID, using = "io.selendroid.testapp:id/startUserRegistration")
	public WebElement myacc	;
	
	

}
