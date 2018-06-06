package test.directory.monitor;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Sawan.Patwari
 */
abstract public class Monitor {
	
	private FileSystem fileSystem;

	private List<String> paths;
	
	private MonitorType monitorType;

	private Set<WatchEvent.Kind<?>> events;

	abstract List<Path> createPaths(List<String> paths);

	void registerEvents(WatchService service, List<Path> paths) throws IOException {
		WatchEvent.Kind<?>[] eventTypes = new WatchEvent.Kind<?>[3];
		
		if (!Objects.isNull(getEvents())) {			
			int i = 0;
			for(WatchEvent.Kind<?> x : getEvents()) {
				eventTypes[i++] = x;
			}
			
		}

		for (Path path : paths) {
			path.register(service, eventTypes);
			/*
			path.register(service, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);*/
			
		}
	}
	
	boolean processEvents(WatchKey key) {
		
		boolean shouldWeBreakPolling = false;
	
		for (WatchEvent<?> event : key.pollEvents()) {
			Kind<?> kind = event.kind();
			if (kind == StandardWatchEventKinds.OVERFLOW) {
				continue;
			}
			
			@SuppressWarnings("unchecked")
			WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
			Path path = watchEvent.context();
			System.out.println("[Monitor Type:]"+ monitorType +"[Event Type=" + kind + ", File Name=" + path.getFileName() + "]");
		}
		
		if (!key.reset()) {
			shouldWeBreakPolling = true;
		}
		
		return shouldWeBreakPolling;
	}
	
	void monitor() throws IOException {
		try (WatchService service = getFileSystem().newWatchService()) {

			List<Path> paths = createPaths(getPaths());

			registerEvents(service, paths);

			// Polling for events
			while(true) {
				WatchKey key;
				try {
					
					key = service.poll(30, TimeUnit.SECONDS);
				} catch (InterruptedException x) {
					System.out.println("Polling got interrupted.");
					break;
				}
				
				if(!Objects.isNull(key)) {
					if (processEvents(key)) {
						System.out.println("Cannot proceed with polling anymore.");
						break;
					}
				}
			}
		}
	}

	public List<String> getPaths() {
		return paths;
	}

	public void setPaths(List<String> paths) {
		this.paths = paths;
	}

	public MonitorType getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(MonitorType monitorType) {
		this.monitorType = monitorType;
	}

	public Set<WatchEvent.Kind<?>> getEvents() {
		return events;
	}

	public void setEvents(Set<WatchEvent.Kind<?>> events) {
		this.events = events;
	}
	
	public void start() {
		try {
			monitor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FileSystem getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}
}
