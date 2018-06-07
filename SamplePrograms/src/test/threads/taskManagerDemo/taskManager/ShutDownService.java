package test.threads.taskManagerDemo.taskManager;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 
 * @author Sawan.Patwari
 *
 */
abstract public class ShutDownService<T>{

	private T service;
	private Long waitTimeInMinutes;
	private List<Future<?>> futureOfSubmittedTasks = new ArrayList<>();
	
	public ShutDownService(T service, Long waitTimeInMinutes) {
		this.service = service;
		this.waitTimeInMinutes = waitTimeInMinutes;
	}
	
	public ShutDownService(T service, List<Future<?>> futureOfSubmittedTasks) {
		this.service = service;
		if(!Objects.isNull(futureOfSubmittedTasks)) {
			this.futureOfSubmittedTasks = futureOfSubmittedTasks;
		}
	}
	
	protected void doShutDownService() {
		
		if(service instanceof ExecutorService) {
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
		} else {
			throw new UnsupportedOperationException("Service Shutdown operation not supported.");
		}
	}
	
	protected void doShutDownServiceIfTasksCompleted(ScheduledExecutorService serviceShutdowner) {
		
		boolean canShutdown = true;
		
		for(Future<?> x : getFutureOfSubmittedTasks()) {
			
			if(!x.isDone()) {
				
				canShutdown = false;
				break;
			}
		}

		if(canShutdown) {
			if(service instanceof ExecutorService) {
				if (service != null)
					((ExecutorService)service).shutdown();
			} else {
				throw new UnsupportedOperationException("Service Shutdown operation not supported.");
			}
			
			if(serviceShutdowner != null) {
				serviceShutdowner.shutdown();
			}
			
			System.out.println("Services have been shut-down.");
		}
	}

	private List<Future<?>> getFutureOfSubmittedTasks() {
		return futureOfSubmittedTasks;
	}

}
