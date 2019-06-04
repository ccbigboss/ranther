package org.ranther.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogFilter {

	public String[] filterInfo(String sourceLog, int duration) {
		String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
		if (!methodname.equals("filterWarning") && !methodname.equals("filterError")) {
			methodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		}
		switch (methodname) {
		case "filterInfo":
			methodname = "Info";
			break;
		case "filterWarning":
			methodname = "Warn";
			break;
		case "filterError":
			methodname = "Error";
			break;
		default:
			methodname = "Other";
		}
		String[] sourceLogArr = sourceLog.split("\r\n");
		List<String> list = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - duration);
		Date filterdate = c.getTime();
		String regDate = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(regDate);
		for (String s : sourceLogArr) {
			String strlogdate = s.substring(0, regDate.length());
			try {
				Date logdate = sdf.parse(strlogdate);
				boolean flag1 = s.matches(".+" + methodname + ".+");
				boolean flag2 = logdate.after(filterdate);
				if (flag1 && flag2) {
					list.add(s);
				}
			} catch (ParseException e) {
				System.out.println("日期格式错误无法转换");
			}
		}
		sourceLogArr = new String[list.size()];
		list.toArray(sourceLogArr);
		return sourceLogArr;
	}

	public String[] filterWarning(String sourceLog, int duration) {
		return filterInfo(sourceLog, duration);
	}

	public String[] filterError(String sourceLog, int duration) {
		return filterInfo(sourceLog, duration);
	}
	
}
