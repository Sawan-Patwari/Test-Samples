package test.directory.monitor;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Factory pattern has been used in this case.
 * 
 * @author Sawan.Patwari
 */
public class DirectoryMonitorHelper {

	private String httpURL;
	
	private FTPDetails ftpDetails;
	
	private List<String> folderPaths;
	
	private Set<EventMonitorTypes> eventMonitorTypes;
	
	public String getHttpURL() {
		return httpURL;
	}

	public void setHttpURL(String httpURL) {
		this.httpURL = httpURL;
	}

	public FTPDetails getFtpDetails() {
		return ftpDetails;
	}

	public void setFtpDetails(FTPDetails ftpDetails) {
		this.ftpDetails = ftpDetails;
	}

	public Set<EventMonitorTypes> getEventMonitorTypes() {
		return eventMonitorTypes;
	}

	public void setEventMonitorTypes(Set<EventMonitorTypes> eventMonitorTypes) {
		this.eventMonitorTypes = eventMonitorTypes;
	}

	public List<String> getFolderPaths() {
		return folderPaths;
	}

	public void setFolderPaths(List<String> folderPaths) {
		this.folderPaths = folderPaths;
	}
	
	public Monitor newLocalMonitor() {
		
		Monitor monitor = new LocalMonitor();
		monitor.setMonitorType(MonitorType.LOCAL);
		monitor.setFileSystem(FileSystems.getDefault());
		doCommonSetupWork(monitor);
		
		return monitor;
	}
	
	private void doCommonSetupWork(Monitor monitor) {
		
		monitor.setPaths(getFolderPaths());
		
		Set<WatchEvent.Kind<?>> events = new HashSet<>();
		
		if(getEventMonitorTypes().contains(EventMonitorTypes.CREATE)) {
			events.add(StandardWatchEventKinds.ENTRY_CREATE);
		}
		
		if(getEventMonitorTypes().contains(EventMonitorTypes.DELETE)) {
			events.add(StandardWatchEventKinds.ENTRY_DELETE);
		}
		
		if(getEventMonitorTypes().contains(EventMonitorTypes.MODIFY)) {
			events.add(StandardWatchEventKinds.ENTRY_MODIFY);
		}
		
		monitor.setEvents(events);
	}
	
	public Monitor newFTPRemoteMonitor() {

		Monitor monitor = new FTPRemoteMonitor();
		monitor.setMonitorType(MonitorType.FTP_REMOTE);		

		FTPDetails ftpDetails = getFtpDetails();
		FileSystem fileSystem = null;
		try {
			fileSystem = FileSystems.getFileSystem(new URI("ftp://" + ftpDetails.getUserName() + ":"
					+ ftpDetails.getPassword() + "@" + "ftp." + ftpDetails.getFtpServer()));

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		monitor.setFileSystem(fileSystem);
		
		doCommonSetupWork(monitor);

		return monitor;
	}
	
	public Monitor newHTTPRemoteMonitor() {

		Monitor monitor = new HTTPRemoteMonitor();
		monitor.setMonitorType(MonitorType.HTTP_REMOTE);		
		
		FileSystem fileSystem = null;
		try {
			fileSystem = FileSystems.getFileSystem(new URI(getHttpURL()));

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		monitor.setFileSystem(fileSystem);
		
		doCommonSetupWork(monitor);

		return monitor;
	}

}
