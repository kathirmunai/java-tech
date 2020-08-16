package com.kathirmunai.cms.java.rename;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class RenameMoveFile {

	private static final String SOURCE_FILE = "src/test/resources/first.txt";
	private static final String TARGET_FILE = "src/test/resources/second.txt";

	public static void main(String[] args) throws IOException {
		System.out.println("RenameMoveFile starts");
		fileRenameMoveDemo();
		nioRenameMoveDemo();
		commonsIORenameMoveDemo();
		System.out.println("RenameMoveFile ends");
	}

	private static void fileRenameMoveDemo() throws FileSystemException {
		File fileToMove = new File(SOURCE_FILE);
		boolean isMoved = fileToMove.renameTo(new File(TARGET_FILE));
		if (!isMoved) {
			throw new FileSystemException(TARGET_FILE);
		}
	}

	private static void nioRenameMoveDemo() throws IOException {
		Path fileToMovePath = Paths.get(TARGET_FILE);
		Path targetPath = Paths.get(SOURCE_FILE);
		Files.move(fileToMovePath, targetPath);
	}

	private static void commonsIORenameMoveDemo() throws IOException {
		FileUtils.moveFile(
				FileUtils.getFile(System.getProperty("user.home") + File.separator + "first.txt"), 
				FileUtils.getFile(System.getProperty("user.home") + File.separator + "second.txt"));
	}

}
