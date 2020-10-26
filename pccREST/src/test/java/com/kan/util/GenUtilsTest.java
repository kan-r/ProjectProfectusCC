package com.kan.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

class GenUtilsTest {

	@Test
	void testToInt() {
		// valid int
		assertThat(GenUtils.toInt("10")).isEqualTo(10);
		
		// valid int
		assertThat(GenUtils.toInt("+10")).isEqualTo(10);
				
		// valid int
		assertThat(GenUtils.toInt("-10")).isEqualTo(-10);
		
		// invalid int
		assertThrows(NumberFormatException.class, () -> {
			GenUtils.toInt("");
	    });
		
		// invalid int
		assertThrows(NumberFormatException.class, () -> {
			GenUtils.toInt("10.1");
	    });
		
		// invalid int
		assertThrows(NumberFormatException.class, () -> {
			GenUtils.toInt("a10");
	    });
	}

	@Test
	void testToDouble() {
		// valid double
		assertThat(GenUtils.toDouble("10")).isEqualTo(10);
		
		// valid double
		assertThat(GenUtils.toDouble("10.1")).isEqualTo(10.1);
		
		// valid double
		assertThat(GenUtils.toDouble("+10.1")).isEqualTo(10.1);
		
		// valid double
		assertThat(GenUtils.toDouble("-10.1")).isEqualTo(-10.1);
		
		// invalid double
		assertThrows(NumberFormatException.class, () -> {
			GenUtils.toDouble("");
	    });
		
		// invalid double
		assertThrows(NumberFormatException.class, () -> {
			GenUtils.toDouble("a10");
	    });
				
	}

	@Test
	void testToDate() {
		// valid date format yyyy-MM-dd
		// valid date
		LocalDate date = GenUtils.toDate("2020-10-25");
		assertThat(date.getYear()).isEqualTo(2020);
		assertThat(date.getMonthValue()).isEqualTo(10);
		assertThat(date.getDayOfMonth()).isEqualTo(25);
		
		// invalid date
		assertThrows(DateTimeParseException.class, () -> {
			GenUtils.toDate("");
	    });
		
		// invalid date
		assertThrows(DateTimeParseException.class, () -> {
			GenUtils.toDate("25-10-2020");
	    });
		
		// invalid date
		assertThrows(DateTimeParseException.class, () -> {
			GenUtils.toDate("2020-10-25 13:30");
	    });
	}

	@Test
	void testToArrayList() {
		// valid str
		assertThat(GenUtils.toArrayList("a1, a2")).hasSize(2);
		
		// valid str
		assertThat(GenUtils.toArrayList("a1; a2")).hasSize(1);
		
		// valid str
		assertThat(GenUtils.toArrayList("")).hasSize(1);
		
		// invalid str
		assertThrows(NullPointerException.class, () -> {
			GenUtils.toArrayList(null);
	    });
	}

}
