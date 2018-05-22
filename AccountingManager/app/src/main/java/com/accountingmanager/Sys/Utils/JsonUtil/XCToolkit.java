package com.accountingmanager.Sys.Utils.JsonUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class XCToolkit {
	public static int parseInt(String strValue) {
		String valueString = "";
		strValue = strValue.replaceAll(",", "");
		if ((strValue == null) || (strValue.isEmpty())) {
			return 0;
		}
		for (int i = 0; i < strValue.length(); i++) {
			if (!Character.isDigit(strValue.charAt(i))) {
				break;
			}
			valueString = valueString + strValue.charAt(i);
		}
		return Integer.parseInt(valueString);
	}

	public static String stringFromDate(Date date, String formate) {
		if ((date == null) || (formate == null)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formate, Locale.CHINESE);
		return sdf.format(date);
	}

}
