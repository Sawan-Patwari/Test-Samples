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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
		executeTest11();
		executeTest12();
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
	
	public static void executeTest11() {
		FromJDK_1Dot7.listFilesOfCurrentDirectory();
	}
	
	public static void executeTest12() {
		FromJDK_1Dot7.doDirectoryWalkSample();
	}
	
	public static void executeTest13() {
		FromJDK_1Dot7.displayLastOneDayModifiedJavaFiles();
	}
	
	public static void executeTest14() {
		FromJDK_1Dot7.doSampleReadWithStreamsTest();
	}
	
	public static void executeTest15() {
		FromJDK_1Dot7.doSampleFileProcessingUsingStreams();
	}
}

class SampleFiles {
	final static String CURRENT_DIRECTORY = ".";
	private final static String CURRENT_WORKING_DIRECTORY = CURRENT_DIRECTORY + File.separator + "src" + File.separator + "test"
			+ File.separator + "files" + File.separator;

	final static String filePath1 = CURRENT_WORKING_DIRECTORY + "sampleFile1.txt";
	final static String filePath2 = CURRENT_WORKING_DIRECTORY + "sampleFile2.txt";
	final static String filePath3 = CURRENT_WORKING_DIRECTORY + "sampleFile3.txt";
	final static String emptyFilePath = CURRENT_WORKING_DIRECTORY + "sampleEmptyFile.txt";
	final static String sampleLogFile = CURRENT_WORKING_DIRECTORY + "sampleFileToBeProcessed.txt";
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
	
	public static void readFileUsingStreams(String filePath) throws IOException{
		
		Path path = Paths.get(filePath);
		Files.lines(path).forEach(System.out::println);
	}
	
	private static void processFileUsingStreams(String filePath, String...searchKeyword) throws IOException{
				
		Predicate<String> searchFor = null;
		Function<String, String> mapDisplayContent = s -> "Found "+s;
		
		//if nothing to search then will display the file contents completely.
		if (searchKeyword.length == 0) {
			searchFor = s -> true;
			mapDisplayContent = s -> s;
		} else if (searchKeyword.length == 1) {
			searchFor = s -> s.startsWith(searchKeyword[0]);
		} else if (searchKeyword.length == 2) {
			searchFor = s -> s.startsWith(searchKeyword[0]) || s.startsWith(searchKeyword[1]);
		} else if (searchKeyword.length == 3) {
			searchFor = s -> s.startsWith(searchKeyword[0]) || s.startsWith(searchKeyword[1])
					|| s.startsWith(searchKeyword[2]);
		} else if (searchKeyword.length == 4) {
			searchFor = s -> s.startsWith(searchKeyword[0]) || s.startsWith(searchKeyword[1])
					|| s.startsWith(searchKeyword[2]) || s.startsWith(searchKeyword[3]);
		} else {
			throw new IllegalArgumentException("Can support only 4 search keywords");
		}
		
		Path path = Paths.get(filePath);
		
		Files.lines(path)
		.filter(searchFor)
		.map(mapDisplayContent).//{return "Found "+s;}
		forEach(System.out::println);		
		 
		/*
		 List<String> filteredData = Files.lines(path)
			.filter(s -> s.startsWith("WARN") || s.startsWith("ERROR"))
			.map(s -> "Found "+s).collect(Collectors.toList());
		 
		 */
	}
	
	public static void processSampleLogFile() {
		
		try {
			
			System.out.println("Nothing to search: [Started]");
			processFileUsingStreams(SampleFiles.sampleLogFile);
			System.out.println("Nothing to search: [Ended]");
			
			System.out.println("Searching for 'WARN' statements: [Started]");
			processFileUsingStreams(SampleFiles.sampleLogFile, "WARN");
			System.out.println("Searching for 'WARN' statements: [Ended]");
			
			System.out.println("Searching for 'ERROR' and 'INFO' statements: [Started]");
			processFileUsingStreams(SampleFiles.sampleLogFile, "ERROR", "INFO");			
			System.out.println("Searching for 'ERROR' and 'INFO' statements: [Ended]");
			
			System.out.println("Searching for 'INFO', 'DEBUG', and 'ERROR' statements: [Started]");
			processFileUsingStreams(SampleFiles.sampleLogFile, "INFO", "DEBUG", "ERROR");			
			System.out.println("Searching for 'INFO', 'DEBUG', and 'ERROR' statements: [Ended]");
			
			System.out.println("Searching for 'WARN', 'INFO', 'DEBUG', and 'ERROR' statements: [Started]");
			processFileUsingStreams(SampleFiles.sampleLogFile, "WARN", "INFO", "DEBUG", "ERROR");			
			System.out.println("Searching for 'WARN', 'INFO', 'DEBUG', and 'ERROR' statements: [Ended]");
			
			System.out.println("Searching for 'DEBUG' statements: [Started]");
			processFileUsingStreams(SampleFiles.sampleLogFile, "DEBUG");		
			System.out.println("Searching for 'DEBUG' statements: [Ended]");			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void doSampleFileProcessingUsingStreams() {
		processSampleLogFile();
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
	
	public static void doSampleReadWithStreamsTest() {
		try {
			readFileUsingStreams(SampleFiles.filePath3);
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
	
	public static void listFilesOfADirectory(Path path) {
		try {

			Files.list(path).filter(p -> 
				!Files.isDirectory(p)).
				map(p -> p.toAbsolutePath().
				normalize()).
				forEach(System.out::println);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void displayFiles(Path path, String fileType) {

		try {
			Files.walk(path).filter(p -> p.toString().endsWith(fileType))
			.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void displayJavaFiles(Path path) {
		
		displayFiles(path, ".java");
	}
	
	public static void listFilesOfCurrentDirectory() {		
		listFilesOfADirectory(Paths.get(SampleFiles.CURRENT_DIRECTORY));
	}
	
	public static void doDirectoryWalkSample() {
		displayJavaFiles(Paths.get(SampleFiles.CURRENT_DIRECTORY));
	}
	
	private static Stream<Path> findLastNDaysModifiedFiles(Path path, String fileType, int numberOfDays) {
		
		Stream<Path> stream = null;
		try {
			stream = Files.find(path, 12,

					(p, a) -> p.toString().endsWith(fileType)

							&& (a.lastModifiedTime().toMillis()) > 
									(Instant.now().minus(numberOfDays, ChronoUnit.DAYS)
									.toEpochMilli()));

		} catch (IOException e) {
			System.out.println(e);
		}
		return stream;
	}
	
	public static void displayLastOneDayModifiedJavaFiles() {
		
		Stream<Path> streamOfFiles = 
				findLastNDaysModifiedFiles(
						Paths.get(SampleFiles.CURRENT_DIRECTORY),".java", 1);
		
		if(Objects.isNull(streamOfFiles)) {
		/*Cannot think of count operation since the stream will be closed.
		  Need to think of cloning Stream instance, if at all it is possible;
		  it may not be possible, but need to try later (on the to-do list).
		  Alternate approach is to get the list out of the stream instance 
		  and then take a count and as well display the contents, 
		  if the count is > 0. 
		  Cloning stream might not be possible because of lambda expressions
		  (functional programming), and even if it is possible, 
		  it may be an expensive operation because the stream terminal
		  operation will be executed twice - one for count and one for 
		  the forEach operation.
		*/
		//if(Objects.isNull(streamOfFiles) || streamOfFiles.count()==0) {
			System.out.println("Cannot display the files.");
		}else {
			streamOfFiles.forEach(System.out::println);
		}
	}
}
