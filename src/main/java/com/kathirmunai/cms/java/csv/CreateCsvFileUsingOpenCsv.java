package com.kathirmunai.cms.java.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class CreateCsvFileUsingOpenCsv {

	public static void main(String[] args) throws IOException {
		System.out.println("CreateCsvFileUsingOpenCsv starts");
		createFile();
		System.out.println("CreateCsvFileUsingOpenCsv ends");
	}

	private static void createFile() throws  IOException {
		try {
			List<Student> studentList = new ArrayList<>();
			Student student1 = new Student(Long.valueOf(1),"kathir",Integer.valueOf(90));
			Student student2 = new Student(Long.valueOf(2),"munai",Integer.valueOf(99));
			studentList.add(student1);
			studentList.add(student2);

			String fileName = System.getProperty("user.home") + File.separator + "km-demo"+ File.separator +"OpenCsv.csv";
			File file=new File(fileName);

			if (!file.exists()) {
				file.getParentFile().mkdirs();

				file.createNewFile();
			}

			Path filePath = Paths.get(fileName);

			MappingStrategy<Student> mappingStrategy = new CustomBeanToCSVMappingStrategy<>();
			mappingStrategy.setType(Student.class);

			BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8);
			StatefulBeanToCsv<Student> beanToCsv = new StatefulBeanToCsvBuilder<Student>(bufferedWriter)
					.withMappingStrategy(mappingStrategy)
					.withOrderedResults(true)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
					.build();

			beanToCsv.write(studentList);
			bufferedWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
}
