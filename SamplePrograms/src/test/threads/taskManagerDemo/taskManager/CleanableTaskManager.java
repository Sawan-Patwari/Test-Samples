package test.threads.taskManagerDemo.taskManager;

import java.util.Objects;

import java.util.stream.Stream;

import test.threads.taskManagerDemo.task.CleanableTask;
import test.threads.taskManagerDemo.taskCreator.CleanableTaskCreator;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class CleanableTaskManager extends TaskManager<CleanableTask, Long> {

	public CleanableTaskManager() {
		tasks = new CleanableTaskCreator().getStreamOfBikeCleaningTasks(10);
	}

	public CleanableTaskManager(Stream<CleanableTask> tasks) {

		if (!Objects.isNull(tasks))
			this.tasks = tasks;
		else
			new CleanableTaskManager();
	}

}
