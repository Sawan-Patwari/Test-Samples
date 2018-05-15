package test.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class ThreadsSynchronisationProg {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		executeTest1();
	}

	/**
	 * I should be able to test this method easily from TestQuickProg.java as well.
	 */
	public static void executeTest1() {
		try {
			Synchronisation.executeTest1();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	private static class Synchronisation {

		private int counter = 0;// without using Atomic int.

		private static long ELEVEN_SECONDS = 11_000;
		private static long THREE_SECONDS = 3_000;

		enum SYNCHRONISATION {
			WITH(true), WITHOUT(false);

			@SuppressWarnings("unused")
			private boolean option;

			SYNCHRONISATION(boolean option) {
				this.option = option;
			}

		}

		enum CONCURRENT_API {
			WITH(true), WITHOUT(false);

			@SuppressWarnings("unused")
			private boolean option;

			CONCURRENT_API(boolean option) {
				this.option = option;
			}

		}

		private static long ONE_SECOND = 1_000;

		// private void doDisplay(boolean isSynchronisationRequired) {
		private void doDisplay(SYNCHRONISATION option) {

			if (option == SYNCHRONISATION.WITH) {
				// synchronized (this) {//one of the ways.
				synchronized (Synchronisation.class) {// another way.
					System.out.println(provideContent());
				}
			} else {
				System.out.println(provideContent());
			}
		}

		@SuppressWarnings("static-access")
		private int provideContent() {// didn't synchronize the method, per my requirement.

			try {
				Thread.currentThread().sleep(ONE_SECOND);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return ++counter;
		}

		private static void doTest(SYNCHRONISATION option) {
			ExecutorService service = null;
			try {
				service = Executors.newFixedThreadPool(20);
				Synchronisation syncTester = new Synchronisation();

				for (int i = 0; i < 10; i++)
					service.submit(() -> syncTester.doDisplay(option));

			} finally {
				if (service != null)
					service.shutdown();
			}
		}

		public static void executeTest1() throws InterruptedException {

			System.out.println("executeTest1(): [Started]");

			System.out.println("With out Synchronisation Test: [Started]");
			Synchronisation.doTest(SYNCHRONISATION.WITHOUT);
			Thread.sleep(THREE_SECONDS);
			System.out.println("With out Synchronisation Test: [Ended]");
			Thread.sleep(THREE_SECONDS);
			System.out.println("With Synchronisation Test: [Started]");
			Synchronisation.doTest(SYNCHRONISATION.WITH);
			Thread.sleep(ELEVEN_SECONDS);
			System.out.println("With out Synchronisation Test: [Ended]");

			System.out.println("executeTest1(): [Ended]");
		}

		public static void executeTest2() throws InterruptedException {

			System.out.println("executeTest2(): [Started]");

			System.out.println("executeTest2(): [Ended]");
		}

	}
}
