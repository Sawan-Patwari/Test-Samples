package test.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class ReadWriteProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		executeTest1();
		executeTest2();
		executeTest3();
		executeTest4();
		executeTest5();
		executeTest6();
		executeTest7();
		executeTest8();
		executeTest9();
		executeTest10();
	}

	public static void executeTest1() {
		BeforeJDK_1Dot7.writeToFile(BeforeJDK_1Dot7.FileInstanceOption.WITH);
	}

	public static void executeTest2() {
		BeforeJDK_1Dot7.writeToFile(BeforeJDK_1Dot7.FileInstanceOption.WITHOUT);
	}

	public static void executeTest3() {
		new BeforeJDK_1Dot7().displayFileContents();
	}

	public static void executeTest4() {
		new FromJDK_1Dot7().doSampleWriteTest();
	}

	public static void executeTest5() {
		new FromJDK_1Dot7().doSampleReadTest();
	}

	public static void executeTest6() {
		new FromJDK_1Dot7().displayFileContents(SampleFiles.filePath3);
	}
	
	public static void executeTest7() {
		try {
			new TestClassWithUnsuportedFileOperations().readFile("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void executeTest8() {
		try {
			new TestClassWithUnsuportedFileOperations().writeFile("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void executeTest9() {
		new FromJDK_1Dot7().doSampleEmptyFileCreationTest();
	}

	public static void executeTest10() {
		new FromJDK_1Dot7().doSampleAppendToFileTest();
	}
}

class SampleFiles {
	private final static String CURRENT_WORKING_DIRECTORY = "." + File.separator + "src" + File.separator + "test"
			+ File.separator + "files" + File.separator;

	final static String filePath1 = CURRENT_WORKING_DIRECTORY + "sampleFile1.txt";
	final static String filePath2 = CURRENT_WORKING_DIRECTORY + "sampleFile2.txt";
	final static String filePath3 = CURRENT_WORKING_DIRECTORY + "sampleFile3.txt";
	final static String emptyFilePath = CURRENT_WORKING_DIRECTORY + "sampleEmptyFile.txt";
}

class BeforeJDK_1Dot7 implements FileOperations {

	static enum FileInstanceOption {
		WITH(true), WITHOUT(false);

		@SuppressWarnings("unused")
		private boolean option;

		FileInstanceOption(boolean option) {
			this.option = option;
		}
	}

	public static void writeToFile(FileInstanceOption option) {

		BeforeJDK_1Dot7 tester = new BeforeJDK_1Dot7();

		if (option == FileInstanceOption.WITH) {
			File sourceFileInstance = new File(SampleFiles.filePath1);

			try {
				tester.writeWithFileInstance(sourceFileInstance);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File couldn't be written. Reason:" + e);
			}

		} else {
			try {
				tester.writeWithoutFileInstance(SampleFiles.filePath2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File couldn't be written. Reason:" + e);
			}
		}

	}

	private void writeWithFileInstance(File fileInstance) throws IOException {

		try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileInstance)))) {

			printWriter.println("Writing to the file using old approach.");
			printWriter.print("Using PrintWriter with File instance.");
			printWriter.format("Wrote this content using format method.");
			printWriter.println();
			printWriter.println(this);
		}
	}

	private void writeWithoutFileInstance(String filePath) throws IOException {

		writeFile(filePath);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + " " + "This object is being written to the file.";
	}

	public void readFile(String filePath) throws IOException {

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String lineContent;
			while ((lineContent = reader.readLine()) != null) {
				System.out.println(lineContent);
			}
		}
	}

	public void writeFile(String filePath) throws IOException {
		try (PrintWriter printWriter = new PrintWriter(filePath)) {

			printWriter.println("Writing to the file using old approach.");
			printWriter.print("Using PrintWriter without File instance.");
			printWriter.format("Wrote this content using format method.");
			printWriter.println();
			printWriter.println(this);
		}
	}

}

class TestClassWithUnsuportedFileOperations implements FileOperations{
	
}

interface FileOperations {
	default void writeFile(String filePath) throws IOException {
		throw new UnsupportedOperationException("Write Operation not supported.");
	}

	default void readFile(String filePath) throws IOException {
		throw new UnsupportedOperationException("Read Operation not supported.");
	}
	
	/**
	 * 1.7 approach used.
	 * @param filePath
	 * @throws IOException
	 */
	default void createAnEmptyFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		Files.createFile(path);		
	}
	
	/**
	 * 1.7 approach used. 
	 * 
	 * <p>If the file is opened for write access by other programs,
	 * then it is file system specific if writing to the end of the file is atomic.
	 * 
	 * <p>Source: {@link StandardOpenOption}
	 * 
	 * <p>Need to make use of third-party jars such as Log4j-SLF4j for application
	 * logging.
	 * 
	 * @param filePath
	 * @param content
	 * @throws IOException
	 */
	default void appendToFile(String filePath, String content) throws IOException {
		Path path = Paths.get(filePath);

		try (BufferedWriter writer = 
				Files.newBufferedWriter(path, 
						StandardOpenOption.APPEND)) {
			writer.newLine();
			writer.append(content);
		}
		
		/*
		//Alternative:
		try (BufferedWriter bWriter = 
				Files.newBufferedWriter(path, 
						StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				PrintWriter pWriter = new PrintWriter(bWriter)
				) {
			pWriter.println();//to have println() style instead of newLine();	
			pWriter.append(content);//Also, bWriter.append(content);
		}*/
	}

	default void displayFileContents(String... multipleFilePaths) {

		if (Objects.isNull(multipleFilePaths) || multipleFilePaths.length == 0) {
			String[] defaultSampleFilePath = { SampleFiles.filePath1 };
			multipleFilePaths = defaultSampleFilePath;
		}

		int fileNumber = 0;
		for (String filePath : multipleFilePaths) {
			System.out.println("Reading file:" + ++fileNumber);

			try {
				readFile(filePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to read file:" + fileNumber + " due to " + e);
			}

			System.out.println();
		}

	}
}

class FromJDK_1Dot7 implements FileOperations {

	public void readFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		try (BufferedReader reader = Files.newBufferedReader(path)) {

			String currentLine = null;
			while ((currentLine = reader.readLine()) != null)
				System.out.println(currentLine);
		}
	}

	public void writeFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);

		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write("Writing to the file as per FromJDK_1Dot7 approach.");
			writer.newLine();
			writer.write("Writing to the file completed.");
		}
	}

	public void doSampleWriteTest() {
		try {
			writeFile(SampleFiles.filePath3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to write to the file due to " + e);
		}
	}

	public void doSampleReadTest() {
		try {
			readFile(SampleFiles.filePath3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to read to the file due to " + e);
		}
	}
	
	public void doSampleEmptyFileCreationTest() {
		
		try {
			createAnEmptyFile(SampleFiles.emptyFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to create the file due to " + e);
		}
	}
	
	public void doSampleAppendToFileTest() {
		String appendedContent = "Doing AppendToFileTest.";
		
		try {
			appendToFile(SampleFiles.filePath3, appendedContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to append the content to the file due to " + e);
		}
	}
}
