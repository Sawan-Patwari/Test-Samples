package test.directory.monitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class uses Factory, Immutable, and Builder patterns based classes.
 * 
 * @author Sawan.Patwari
 */
public class DirectoryMonitor {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		doLocalDirectoryMonitor();
	}

	public static void doLocalDirectoryMonitor() {
		List<String> folderPaths = new ArrayList<>();
		folderPaths.add(".");

		Set<EventMonitorTypes> eventMonitorTypes = EnumSet.of(EventMonitorTypes.CREATE, EventMonitorTypes.DELETE,
				EventMonitorTypes.MODIFY);

		DirectoryMonitorHelper monitorHelper = new DirectoryMonitorHelper();
		monitorHelper.setFolderPaths(folderPaths);
		monitorHelper.setEventMonitorTypes(eventMonitorTypes);
		// monitorHelper.newLocalMonitor();
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.submit(() -> {

			monitorHelper.newLocalMonitor().start();

		});
		service.shutdown();
		System.out.println("Main method will shutdown now.");
	}

	/**
	 * Haven't tested this method.
	 */
	@SuppressWarnings("unused")
	private void doRemoteDirectoryMonitorViaFTP() {
		
		List<String> folderPaths = new ArrayList<>();
		folderPaths.add(System.getProperty("usr.dir"));

		Set<EventMonitorTypes> eventMonitorTypes = EnumSet.of(EventMonitorTypes.CREATE, EventMonitorTypes.DELETE,
				EventMonitorTypes.MODIFY);

		DirectoryMonitorHelper monitorHelper = new DirectoryMonitorHelper();
		monitorHelper.setFolderPaths(folderPaths);
		monitorHelper.setEventMonitorTypes(eventMonitorTypes);

		// some dummy values.
		FTPDetailsBuilder ftpBuilder = new FTPDetailsBuilder()
		.setUserName("someUserName")
		.setPassword("somePassword")
		.setFtpServer("ftpServer");
		
		FTPDetails ftpDetails = ftpBuilder.build();
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.submit(() -> {

			monitorHelper.newFTPRemoteMonitor().start();

		});
		service.shutdown();
		System.out.println("Main method will shutdown now.");
	}

	/**
	 * Haven't tested this method.
	 */
	@SuppressWarnings("unused")
	private void doRemoteDirectoryMonitorViaHTTP() {
		
		List<String> folderPaths = new ArrayList<>();
		folderPaths.add(System.getProperty("usr.dir"));

		Set<EventMonitorTypes> eventMonitorTypes = EnumSet.of(EventMonitorTypes.CREATE, EventMonitorTypes.DELETE,
				EventMonitorTypes.MODIFY);

		DirectoryMonitorHelper monitorHelper = new DirectoryMonitorHelper();
		monitorHelper.setFolderPaths(folderPaths);
		monitorHelper.setEventMonitorTypes(eventMonitorTypes);
		monitorHelper.setHttpURL("http://www.%some%Server%Name%.com");
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.submit(() -> {

			monitorHelper.newHTTPRemoteMonitor().start();

		});
		service.shutdown();
		System.out.println("Main method will shutdown now.");
	}

}
