package favouritePackage.taskManagerDemo.task;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public interface CleanableTask extends Taskable {

	
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
