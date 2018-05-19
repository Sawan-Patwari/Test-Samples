package test.threads.taskManagerDemo.task;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class HouseCleaningTask extends Task {

	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		doActivity1();
		doActivity2();
		doActivity3();
		doActivity4();
		doActivity5();
	}

	private void doActivity1() {
		System.out.println("HouseCleaningTask.doActivity1() completed.");
	}

	private void doActivity2() {
		System.out.println("HouseCleaningTask.doActivity2() completed.");
	}

	private void doActivity3() {
		System.out.println("HouseCleaningTask.doActivity3() completed.");
	}

	private void doActivity4() {
		System.out.println("HouseCleaningTask.doActivity4() completed.");
	}

	private void doActivity5() {
		System.out.println("HouseCleaningTask.doActivity5() completed.");
	}
}
