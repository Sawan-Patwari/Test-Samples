package test.threads.taskManagerDemo.task;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class CarCleaningTask extends Task {

	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		doActivity1();
		doActivity2();
		doActivity3();
		doActivity4();
	}

	private void doActivity1() {
		System.out.println("CarCleaningTask.doActivity1() completed.");
	}

	private void doActivity2() {
		System.out.println("CarCleaningTask.doActivity2() completed.");
	}

	private void doActivity3() {
		System.out.println("CarCleaningTask.doActivity3() completed.");
	}

	private void doActivity4() {
		System.out.println("CarCleaningTask.doActivity4() completed.");
	}

}
