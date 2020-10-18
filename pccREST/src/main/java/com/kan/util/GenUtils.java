package com.kan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.kan.exception.InvalidDataException;

public class GenUtils {

	public static int toInt(String intStr) {
		return Integer.parseInt(intStr);
	}
	
	public static double toDouble(String doubleStr) {
		return Double.parseDouble(doubleStr);
	}

	public static Date toDate(String dateStr) throws InvalidDataException {

		if (dateStr == null || dateStr.isBlank()) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return (Date) formatter.parse(dateStr);
		} catch (ParseException e) {
			throw new InvalidDataException(e.getMessage() + ", expected date format yyyy-MM-dd");
		}
	}
	
	public static List<String> toArrayList(String commaSepStr) {
		return Arrays.asList(commaSepStr.split(","));
	}
}
