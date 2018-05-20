package favouritePackage.taskManagerDemo.taskManager;

import test.threads.taskManagerDemo.task.CleanableTask;
import test.threads.taskManagerDemo.taskCreator.CleanableTaskCreator;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class TaskManagerTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CleanableTaskCreator tasks = new CleanableTaskCreator();

		TaskManager<CleanableTask, Long> taskManager = 
				new CleanableTaskManager(tasks.getStreamOfBikeCleaningTasks(10));
		
		System.out.println("Foreground process will start.");
		taskManager.doTasksInForeground();
		System.out.println("Foreground process ended.");
				
		taskManager = 
				new CleanableTaskManager(tasks.getStreamOfBikeCleaningTasks(2));
		taskManager.doTasksInBackground();
		
	}

}
