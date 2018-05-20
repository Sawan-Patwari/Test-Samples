package favouritePackage.taskManagerDemo.taskManager;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import test.threads.taskManagerDemo.task.Taskable;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class TaskManager<T, R> {
	protected Stream<T> tasks;

	public void doTasksInForeground() {
		Map<? super T, ? super R> x = tasks.parallel().collect(Collectors.toConcurrentMap(k -> k, v -> 1));

		x.keySet().parallelStream().map(i -> ((Taskable) i).performTask()).count();
	}

	public void doTasksInBackground() {
		ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

		Runnable backgroundTask = () -> {
			doTasksInForeground();
		};
		
		ShutDownService<ScheduledExecutorService> closeService = new 
				ShutDownScheduledService(scheduledService, 1l);
		
		Runnable shutDownTask = () -> {
			System.out.println("Kicked-off service shutdown.");
			closeService.doShutDownService();
		};
		
		scheduledService.schedule(backgroundTask, 20, TimeUnit.SECONDS);
		scheduledService.schedule(shutDownTask, 40, TimeUnit.SECONDS);

		System.out.println("Process scheduled to run in background after 20 seconds.");

		System.out.println("Task Manager will shut-down now.");

	}

}

