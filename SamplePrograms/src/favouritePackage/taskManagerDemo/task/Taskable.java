package favouritePackage.taskManagerDemo.task;


/**
 * 
 * @author Sawan.Patwari
 *
 */
public interface Taskable {

	enum TaskStatus {
		IN_COMPLETE, IN_PROGRESS, COMPLETE;
	}

	void openTask();

	void doTask();

	void closeTask();

	TaskStatus getTaskCurrentStatus();
	
	Number performTask();
}
