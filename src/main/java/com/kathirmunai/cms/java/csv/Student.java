package com.kathirmunai.cms.java.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	@CsvBindByName(column = "Student Id")
	@CsvBindByPosition(position = 0)
	private Long studentId;

	@CsvBindByName(column = "Student Name")
	@CsvBindByPosition(position = 1)
	private String studentName;

	@CsvBindByName(column = "Student Percentage")
	@CsvBindByPosition(position = 2)
	private Integer studentPercentage;

}
