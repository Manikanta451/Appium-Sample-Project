package com.sp.testscripts;

import java.util.Iterator;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.sp.pageobjects.ExcelSheetObjects;
import com.sp.testscripts.SetupEnvironment;
import com.sp.datainitialization.DataInt;
import com.sp.utilities.Util;
import com.sp.utilities.Xls_Reader;


public class AppiumSampleTestSuite extends Base {
	
	public static final Logger LOG = Logger.getLogger(AppiumSampleTestSuite.class);
	String testDataPath = System.getProperty("user.dir")+ "\\src\\main\\java\\com\\sp\\test\\data\\TestData.xlsx";			
	public Xls_Reader xls = new Xls_Reader(testDataPath);

	@Test(description = "Login", dataProvider = "getLogin", priority = 0)
	public void Login(DataInt dataInt) throws Exception {
		try {
			login= driverhome.getLogin();
			login.accountlogin(dataInt);	
			SetupEnvironment.createXLSReport(ExcelSheetObjects.KEYWORD_PASS, ExcelSheetObjects.LoginWithValidCredentials, "TestCases");
		} catch (Exception e) {
			SetupEnvironment.createXLSReport(ExcelSheetObjects.KEYWORD_FAIL,ExcelSheetObjects.LoginWithValidCredentials, "TestCases");
			e.printStackTrace();
		}
					
	}
	
    
			@DataProvider
			public Iterator<Object[]> getLogin() {
				return Util.getLoginData("Login", xls).iterator();

			}	
}


