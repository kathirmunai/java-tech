package com.kathirmunai.cms.java.zip;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class PasswordProtecteZipFolderWithFileWithFile {

	private static final String SOURCE_FILE = "src/test/resources/hello.py";
	private static final String TARGET_FILE = "src/test/source.zip";

	public static void main(String[] args) {
		System.out.println("PasswordProtecteZipFolderWithFileWithFile starts");
		getPasswordProtectedZipFile();
		System.out.println("PasswordProtecteZipFolderWithFileWithFile ends");
	}

	private static void getPasswordProtectedZipFile()  {
		String filePath = FilenameUtils.getFullPath(SOURCE_FILE);

		try {
			ZipParameters zipParameters = new ZipParameters();
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(EncryptionMethod.AES);
			zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256); 

			ZipFile zipFile = new ZipFile(TARGET_FILE, "test".toCharArray());
			zipFile.addFolder(new File(filePath), zipParameters);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
