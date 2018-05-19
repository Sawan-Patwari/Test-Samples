package test.threads.taskManagerDemo.task;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public interface CleanableTask extends Taskable {

	enum TaskStatus {
		IN_COMPLETE, IN_PROGRESS, COMPLETE;
	}

	void openTask();

	void doTask();

	void closeTask();

	TaskStatus getTaskCurrentStatus();

	/**
	 * This method will be called using Parallel Streams which has built-in
	 * concurrency feature. Thus, no need to synchronize this method.
	 */
	default Long performTask() {
		if (getTaskCurrentStatus() == TaskStatus.IN_COMPLETE) {
			openTask();
			doTask();
			closeTask();
		}

		return 1l;
	}

}
