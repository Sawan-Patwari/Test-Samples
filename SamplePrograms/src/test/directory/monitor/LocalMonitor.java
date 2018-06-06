package test.directory.monitor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sawan.Patwari
 */
public class LocalMonitor extends Monitor{

	@Override
	List<Path> createPaths(List<String> paths) {
		// TODO Auto-generated method stub
		List<Path> pathObj = new ArrayList<>();
		for(String path : paths) {
			Path newPath = Paths.get(path);			
			pathObj.add(newPath);
		}
		
		return pathObj;
	}

}
