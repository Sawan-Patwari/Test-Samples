package test.threads.taskManagerDemo.task;

/**
 * 
 * @author Sawan.Patwari
 *
 */
abstract public class Task implements CleanableTask {

	private TaskStatus currentStatus = TaskStatus.IN_COMPLETE;

	@Override
	public void openTask() {
		// TODO Auto-generated method stub
		currentStatus = TaskStatus.IN_PROGRESS;
	}

	abstract public void doTask();

	@Override
	public void closeTask() {
		// TODO Auto-generated method stub
		currentStatus = TaskStatus.COMPLETE;
	}

	public TaskStatus getTaskCurrentStatus() {
		return currentStatus;
	}

}
