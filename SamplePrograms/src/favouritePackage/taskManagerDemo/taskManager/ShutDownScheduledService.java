package favouritePackage.taskManagerDemo.taskManager;

import java.util.concurrent.ScheduledExecutorService;

public class ShutDownScheduledService extends ShutDownService<ScheduledExecutorService>{

	public ShutDownScheduledService(ScheduledExecutorService scheduledService, Long terminationWaitTimeInMinutes) {
		super(scheduledService, terminationWaitTimeInMinutes);
	}
}
