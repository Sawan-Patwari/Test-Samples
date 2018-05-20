package favouritePackage.taskManagerDemo.taskManager;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

abstract public class ShutDownService<T>{

	private T service;
	private Long waitTimeInMinutes;
	
	public ShutDownService(T service, Long waitTimeInMinutes) {
		this.service = service;
		this.waitTimeInMinutes = waitTimeInMinutes;
	}
	
	protected void doShutDownService() {
		if (service != null)
			((ExecutorService)service).shutdown();
		
		if (service != null) {
			System.out.println("Service shutdown awaiting termination for "+waitTimeInMinutes+" minute(s).");
			try {
				((ExecutorService) service).awaitTermination(waitTimeInMinutes, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}			
		}
	}

}
