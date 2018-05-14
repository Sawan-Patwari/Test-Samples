package test.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsSynchronisationProg {

	private static long ELEVEN_SECONDS = 11_000;
	private static long THREE_SECONDS = 3_000;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("With out Synchronisation Test: [Started]");
		SynchronisationTest.doTest(SynchronisationTest.WITHOUT_SYNCHRONISATION);
		Thread.sleep(THREE_SECONDS);
		System.out.println("With out Synchronisation Test: [Ended]");
		Thread.sleep(THREE_SECONDS);
		System.out.println("With Synchronisation Test: [Started]");
		SynchronisationTest.doTest(SynchronisationTest.WITH_SYNCHRONISATION);
		Thread.sleep(ELEVEN_SECONDS);
		System.out.println("With out Synchronisation Test: [Ended]");
	}

}

class SynchronisationTest {
	private int counter = 0;

	public static boolean WITH_SYNCHRONISATION = true;
	public static boolean WITHOUT_SYNCHRONISATION = false;

	private static long ONE_SECOND = 1_000;

	private void doDisplay(boolean isSynchronisationRequired) {

		if (isSynchronisationRequired) {
			synchronized (this) {
				System.out.println(provideContent());
			}
		} else {
			System.out.println(provideContent());
		}
	}

	@SuppressWarnings("static-access")
	private int provideContent() {

		try {
			Thread.currentThread().sleep(ONE_SECOND);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ++counter;
	}

	public static void doTest(boolean isSynchronisationRequired) {
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(20);
			SynchronisationTest syncTester = new SynchronisationTest();

			for (int i = 0; i < 10; i++)
				service.submit(() -> syncTester.doDisplay(isSynchronisationRequired));

		} finally {
			if (service != null)
				service.shutdown();
		}
	}

}