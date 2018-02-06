package com.sp.utilities;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.sp.datainitialization.DataInt;

public class Util {



public static final Logger LOG = Logger.getLogger(Util.class);


public static LinkedList<Object[]> getLoginData(String sheetName1,Xls_Reader xls) {
		

	LinkedList<Object[]> logindata = new LinkedList<Object[]>();

	try {
		DataInt dataInitialization = null;
		int dataStartRowNum = 3;
		int totalRows = 0;

		while (!xls.getCellData(sheetName1, 0, dataStartRowNum + totalRows).equals("")) {
			
			totalRows++;
		}

		for (int i = dataStartRowNum; i <= (dataStartRowNum + totalRows); i++) {

			dataInitialization = new DataInt();
			Object obj[] = new Object[1];

			if (xls.getCellData(sheetName1, 0, i).equalsIgnoreCase("Login")) {

				dataInitialization.setUsername(xls.getCellData(sheetName1, 1, i));
				dataInitialization.setUserpassword(xls.getCellData(sheetName1, 2, i));
				
				
				obj[0] = dataInitialization;
				logindata.add(obj);
			}
		}

	} catch (Exception e) {
		LOG.info(e.toString());

	}
	return logindata;

    }

}


