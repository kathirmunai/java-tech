package com.kathirmunai.cms.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class CSVReaderWriter{
	private static CSVReader csvReader;
	private static String pathToCsv="/Users/sathiya/Documents/kathirmunai.csv";

	public static void main(String[] args) throws CsvValidationException, IOException {
		csvWriter();
		csvReader();
	}

	private static void csvWriter() throws IOException {
		FileWriter outputfile = null;
		Path tempFilePath=Paths.get(pathToCsv);
		outputfile = new FileWriter(tempFilePath.toFile());
		CSVWriter writer = new CSVWriter(outputfile); 

		List<String[]> data = new ArrayList<>();
		data.add(new String[] { "Name", "Percentage", "DOB" }); 
		data.add(new String[] { "Sathiya", "90", "1/1/82"});
		data.add(new String[] { "Vettri", "100", "1/1/92"});

		writer.writeAll(data); 
		writer.close(); 
		outputfile.close();
	}

	private static void csvReader() throws CsvValidationException, IOException {
		csvReader = new CSVReader(new BufferedReader(new FileReader(pathToCsv)));

		String[] line;
		int iteration = 0;
		while ((line = csvReader.readNext()) != null) {
			if(iteration == 0) {
				iteration++;  
				continue;
			}
			System.out.println("[Name= " + line[0] + ", Percentage= " + line[1] + " , DOB=" + line[2] +"]");
		}
	}

}  