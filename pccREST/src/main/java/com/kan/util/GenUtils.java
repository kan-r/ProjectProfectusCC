package com.kan.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class GenUtils {

	public static int toInt(String intStr) {
		return Integer.parseInt(intStr);
	}
	
	public static double toDouble(String doubleStr) {
		return Double.parseDouble(doubleStr);
	}

	// valid date format yyyy-MM-dd
	public static LocalDate toDate(String dateStr) {
		return LocalDate.parse(dateStr);
	}
	
	public static List<String> toArrayList(String commaSepStr) {
		return Arrays.asList(commaSepStr.split(","));
	}
}
