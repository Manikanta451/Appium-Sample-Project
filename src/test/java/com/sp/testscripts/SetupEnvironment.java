package com.sp.testscripts;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import com.sp.pageobjects.ExcelSheetObjects;
import com.sp.utilities.Xls_Reader;


public class SetupEnvironment 
{
	//Constant to read excel suite.xlsx file during execution process 
	public static Xls_Reader Wb;
	public static int currentSuiteID;
	public static int currentTestCaseID;
	public static String RunModeM;
	public String RunModeTC;
	public static String currentTestSuite;
	public static String currentTestCaseName;
	public static Object sheetName;
	public String Testdata;

	// Constant to read excel current_test_suite.xlsx file during execution process 
	public static Xls_Reader currentTestdataXLS;
	public static Xls_Reader currentTestSuiteXLS;
	public static int currentTestRowID;
	public static int currentTestColumnID=1;
	public static int currentTestDataSetID=2;
	public static String keyword_execution_result;
	public static ArrayList<String> resultSetYes;
	public static ArrayList<String> resultSetNo;
	public static ArrayList<String> resultSet;
	public static String TEST_VALUE;
	
	public ArrayList<String> start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Xls_Reader Wb = new Xls_Reader("src/main/java/com/sp/test/data/TestCases.xlsx"); 
		resultSetYes = new ArrayList<String>();
		resultSetNo= new ArrayList<String>();
		  
		for(currentSuiteID=2;currentSuiteID<=Wb.getRowCount(ExcelSheetObjects.TEST_SUITE_SHEET);currentSuiteID++)
	 	{
			// test suite name = test suite .xls file having test cases
            currentTestSuite=Wb.getCellData(ExcelSheetObjects.TEST_SUITE_SHEET, ExcelSheetObjects.Test_Suite_ID, currentSuiteID);
            RunModeM=Wb.getCellData(ExcelSheetObjects.TEST_SUITE_SHEET, ExcelSheetObjects.RUNMODE, currentSuiteID);
           
            if(Wb.getCellData(ExcelSheetObjects.TEST_SUITE_SHEET, ExcelSheetObjects.RUNMODE, currentSuiteID).equals(ExcelSheetObjects.RUNMODE_YES))
	 		{
            	currentTestSuiteXLS=new Xls_Reader("src/main/java/com/sp/test/data/"+currentTestSuite+".xlsx");
            	for(currentTestCaseID=2;currentTestCaseID<=currentTestSuiteXLS.getRowCount("Test Case");currentTestCaseID++)
            	{
                    currentTestCaseName=currentTestSuiteXLS.getCellData(ExcelSheetObjects.TEST_CASES_SHEET, ExcelSheetObjects.Test_Case_ID, currentTestCaseID);
                    RunModeTC=currentTestSuiteXLS.getCellData(ExcelSheetObjects.TEST_CASES_SHEET, ExcelSheetObjects.RUNMODE, currentTestCaseID);
                    
                    if(currentTestSuiteXLS.getCellData(ExcelSheetObjects.TEST_CASES_SHEET, ExcelSheetObjects.RUNMODE, currentTestCaseID).equals(ExcelSheetObjects.RUNMODE_YES))
                    {
                       	// RUN as many times as number of test data sets with runmode Y
						resultSetYes.add(currentTestCaseName); // multiple sets of data
					}
					else
					{
					    // iterating through all keywords
					    resultSetNo.add(currentTestCaseName);// no data with the test
					}
            	}
	 		}
        } 
		return resultSetYes;// no data with the test
    }	

	public static void SetdataSheet() throws Exception
	{
		Xls_Reader Wb_SET_SKIP = new Xls_Reader("src/main/java/com/sp/test/data/TestCases.xlsx"); 
		System.out.println("Workbook datasheet Name:"+Wb_SET_SKIP);
		
		for(currentSuiteID=2; currentSuiteID <= Wb_SET_SKIP.getRowCount(ExcelSheetObjects.TEST_SUITE_SHEET); currentSuiteID++)
	 	{
			currentTestSuite = Wb_SET_SKIP.getCellData(ExcelSheetObjects.TEST_SUITE_SHEET, ExcelSheetObjects.Test_Suite_ID, currentSuiteID);
            SetupEnvironment.SetValue_Excel_SKIP(currentTestSuite);
		}
	}
	
	public static int Get_RowNum(String Test_case_Name) throws Exception
	{
		int RowNum = currentTestSuiteXLS.getCellRowNum(ExcelSheetObjects.TEST_CASES_SHEET, ExcelSheetObjects.SUITE_ID, Test_case_Name);
			System.out.print(RowNum+" is the row number to write data in sheet");
		return RowNum;
	}
	
	public static void SetValue_Excel_SKIP(String SheetName)
	{

		if(!SheetName.equals("TestCases"))
		{
			System.out.println("Skipped excel sheet :"+SheetName);
			Xls_Reader currentXLS = new Xls_Reader("src/main/java/com/sp/test/data/"+SheetName+".xlsx");
			
			for(int RN=2; RN<=currentXLS.getRowCount(ExcelSheetObjects.TEST_CASES_SHEET); RN++)
			{
				currentXLS.setCellData(ExcelSheetObjects.TEST_CASES_SHEET, ExcelSheetObjects.RESULT, RN, ExcelSheetObjects.KEYWORD_SKIP);
			}
		}
	}
	
	public  static void createXLSReport(String RESULT, String Test_case_Name, String SheetName) throws Exception
	{		
		Xls_Reader currentTestSuiteXLS = new Xls_Reader("src/main/java/com/sp/test/data/"+SheetName+".xlsx");		
		int Cell_value = SetupEnvironment.Get_RowNum(Test_case_Name);
		
		if(RESULT.equals(ExcelSheetObjects.KEYWORD_PASS))
		{
			currentTestSuiteXLS.setCellData(ExcelSheetObjects.TEST_CASES_SHEET, ExcelSheetObjects.RESULT, Cell_value, ExcelSheetObjects.KEYWORD_PASS);
		}
		else
		{
			currentTestSuiteXLS.setCellData(ExcelSheetObjects.TEST_CASES_SHEET, ExcelSheetObjects.RESULT, Cell_value, ExcelSheetObjects.KEYWORD_FAIL);
		}
	}
	
}