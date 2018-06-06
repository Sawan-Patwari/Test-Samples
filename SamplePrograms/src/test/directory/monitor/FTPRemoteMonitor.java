package test.directory.monitor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sawan.Patwari
 */
public class FTPRemoteMonitor extends Monitor{

	@Override
	List<Path> createPaths(List<String> paths) {
		// TODO Auto-generated method stub
		List<Path> pathObj = new ArrayList<>();
		for(String path : paths) {
			Path newPath = getFileSystem().getPath(path);			
			pathObj.add(newPath);
		}
		
		return pathObj;
	}

}
