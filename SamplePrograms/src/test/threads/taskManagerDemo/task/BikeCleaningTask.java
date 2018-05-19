package test.threads.taskManagerDemo.task;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class BikeCleaningTask extends Task {

	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		doActivity1();
		doActivity2();
		doActivity3();
	}

	private void doActivity1() {
		System.out.println("BikeCleaningTask.doActivity1() completed.");
	}

	private void doActivity2() {
		System.out.println("BikeCleaningTask.doActivity2() completed.");
	}

	private void doActivity3() {
		System.out.println("BikeCleaningTask.doActivity3() completed.");
	}

}
