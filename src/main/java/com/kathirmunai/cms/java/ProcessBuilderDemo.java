package com.kathirmunai.cms.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

public class ProcessBuilderDemo{
	public static void main(String[] args) throws IOException, InterruptedException {  

		ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath("hello.py"));
		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();
		List<String> results = readProcessOutput(process.getInputStream());
		System.out.println(results);

		int exitCode = process.waitFor();
		System.out.println(exitCode);
		System.out.println("-------------------------------------------------");

        ScriptEngineManager manager = new ScriptEngineManager();
		List<ScriptEngineFactory> engines = manager.getEngineFactories();

		for (ScriptEngineFactory engine : engines) {
			System.out.println("Engine name:"+ engine.getEngineName());
			System.out.println("Version:"+ engine.getEngineVersion());
			System.out.println("Language:"+ engine.getLanguageName());

			System.out.println("\nShort Names:");
			for (String names : engine.getNames()) {
				System.out.println(names);
			}
		}
	}

	private static List<String> readProcessOutput(InputStream inputStream) throws IOException {
		return IOUtils.readLines(inputStream, "UTF-8");
	}

	private static String resolvePythonScriptPath(String filename) {
		File file = new File("src/test/resources/" + filename);
		return file.getAbsolutePath();
	}
}
