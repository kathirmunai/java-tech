package com.kathirmunai.cms.java.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

@Data 
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class Student {
	private String name;
	private int percentage;
	private Date dob;
} 

public class AESEncryptExcelFile{
	static List<Student> students= null;
	public static String DATE_FORMAT="yyyy-MM-dd";
	private static String finalZipFile =  "kathimunai.zip";
	private static String finalExcelFile =  "kathimunai.xlsx";

	public static void main(String[] args) throws IOException, ParseException {
		students= Lists.newArrayList(
				new Student("Sathiya", 90, new SimpleDateFormat(DATE_FORMAT).parse("1982-07-26")), 
				new Student("Vettri", 99, new SimpleDateFormat(DATE_FORMAT).parse("1992-07-26"))
				);
		createFile();
	}

	private static void createFile() throws IOException {
		System.out.println("createFile starts");
		try {
			List<String> headerList = Lists.newArrayList("Name","Percentage","DOB");
			Workbook workbook = new XSSFWorkbook();
			int rowCount = 0;

			Sheet sheet = workbook.createSheet("Students List");
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 4000);

			Row header = sheet.createRow(rowCount++);
			CellStyle headerStyle = workbook.createCellStyle();

			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 16);
			font.setBold(true);
			font.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
			headerStyle.setFont(font);

			Cell headerCell = null;
			int headerCount=0;
			for (String headerStr : headerList) {
				headerCell = header.createCell(headerCount++);
				headerCell.setCellValue(headerStr);
				headerCell.setCellStyle(headerStyle);
			}

			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);

			Row row = null;
			Cell cell = null;

			if (!students.isEmpty()) {
				for (Student student : students) {

					System.out.println("createFile "
							+ "name : " + student.getName()+ ",Percentage : " + student.getPercentage()
							+ ",Dob :" + student.getDob()
							);

					row = sheet.createRow(rowCount++);

					int colNum = 0;
					cell = row.createCell(colNum++);
					cell.setCellValue(student.getName());

					cell = row.createCell(colNum++);
					cell.setCellValue(student.getPercentage());

					cell = row.createCell(colNum++);
					if (student.getDob() instanceof Date) {
						cell.setCellValue(new SimpleDateFormat(DATE_FORMAT).format(student.getDob()));
					}

					cell.setCellStyle(style);
				}
			}

			Path tempFilePath = Files.createTempFile("createFile", ".xlsx");
			System.out.println("createFile tempFilePath:"+tempFilePath);

			FileOutputStream fileOutputStream = new FileOutputStream(tempFilePath.toFile());
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bos.writeTo(fileOutputStream);
			workbook.write(fileOutputStream);
			workbook.close();

			aesEncrypt(tempFilePath,"password123");
		}catch(Exception e) {
			System.out.println("createFile ex: "+e);
		}
		System.out.println("createFile ends");
	}

	/**
	 * 
	 * @param excelFilePath
	 * @return
	 * @throws IOException 
	 */
	private static void aesEncrypt(Path excelFilePath, String excelPassword) throws IOException {
		String tempFilePath = FilenameUtils.getFullPath(excelFilePath.toString());
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setEncryptFiles(true);
		zipParameters.setEncryptionMethod(EncryptionMethod.AES);
		zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256); 
		zipParameters.setFileNameInZip(finalExcelFile);

		File renamedExcelFile = new File(tempFilePath+finalExcelFile);
		if(excelFilePath.toFile().renameTo(renamedExcelFile)) {
			List<File> filesToAddList = Arrays.asList(renamedExcelFile);
			ZipFile zipFile = new ZipFile(tempFilePath+finalZipFile, excelPassword.toCharArray());
			zipFile.addFiles(filesToAddList, zipParameters);
		}
		Path excelPath = Paths.get(tempFilePath+finalExcelFile);
		Files.deleteIfExists(excelPath);
	}
}  