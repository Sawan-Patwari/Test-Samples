package test.threads.taskManagerDemo.taskManager;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class ShutDownScheduledService extends ShutDownService<ScheduledExecutorService>{

	public ShutDownScheduledService(ScheduledExecutorService scheduledService, Long terminationWaitTimeInMinutes) {
		super(scheduledService, terminationWaitTimeInMinutes);
	}
	
	public ShutDownScheduledService(ScheduledExecutorService scheduledService, List<Future<?>> futureOfSubmittedTasks) {
		super(scheduledService, futureOfSubmittedTasks);
	}
}
