package com.kathirmunai.cms.java.timezone;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class ConvertStringToTimeZoneDate {

	public static void main(String[] args) {
		DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("hh:mm a, dd-MMMM-yyyy");
		String text = "07:00 PM, 12-August-2020";

		LocalDateTime localDateTime = LocalDateTime.parse(text, formatter);
		Instant instantDefault = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

		SimpleDateFormat sdfOut = new SimpleDateFormat("hh:mm a, dd-MMMM");
		sdfOut.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Calcutta")));

		String outDate = sdfOut.format(instantDefault.toEpochMilli());
		System.out.println(outDate);
	}
}
