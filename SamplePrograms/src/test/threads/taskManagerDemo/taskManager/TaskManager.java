package test.threads.taskManagerDemo.taskManager;

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

	public void doTasksForeground() {
		Map<? super T, ? super R> x = tasks.parallel().collect(Collectors.toConcurrentMap(k -> k, v -> 1l));// unnecessary

		x.keySet().parallelStream().map(i -> ((Taskable) i).performTask()).count();
	}

	public void doTasksBackground() {
		ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

		try {
			Runnable task = () -> {
	
				doTasksForeground();
			};
	
			scheduledService.schedule(task, 20, TimeUnit.SECONDS);
			System.out.println("Process scheduled to run in background after 20 seconds.");
			
		}finally { 
			if (scheduledService != null)
				scheduledService.shutdown();
		}
		if (scheduledService != null) {
			try {
				scheduledService.awaitTermination(2l, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		System.out.println("Task Manager will shut-down now.");

	}

}
