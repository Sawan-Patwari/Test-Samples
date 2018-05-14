package test.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class ThreadPollingSampleProg {

	private static boolean wait;
	
	private static AtomicBoolean atomicWait = new AtomicBoolean(false);

	private static int counter = 0;
	
	private static AtomicInteger atomicCounter = new AtomicInteger(0);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		doBooleanWaitTestWithTweak();
		doBooleanWaitTestWithGenuineFix();
		doWaitTestWithExecutors();
		doCounterWaitTestWithTweak();
		doCounterWaitTestWithGenuineFix();
	}

	/**
	 * This function hangs. Do not call this method.
	 */
	@Deprecated
	public static void doBooleanWaitTest() {
		
		Object lock = new Object();
		try {
			System.out.println("Thread Polling Sample Test: [Starts]");
			new Thread(() -> {

				synchronized (lock) {
					wait = true;
				}

				try {
					System.out.println("[Child]: Parent, I will sleep. Wait for me.");
					Thread.sleep(10_000);

					synchronized (lock) {
						wait = false;
					}

					System.out.println("[Child]: Parent, I got up from sleep.");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			}).start();

			try {
				// The below sleep from Parent was needed to wait till the 'wait' shared
				// variable was set to 'true' by the Child.
				Thread.sleep(2_000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}

			System.out.println("[Parent]: I will wait Child.");
			synchronized (lock) {
				while (wait) {
					// System.out.println("[Parent]: Child, did you get up?");

				}
			}
			System.out.println("[Parent]: Finally, you got up!");

			System.out.println("Thread Polling Sample Test: [Ends]");
		} catch (Error | Exception e) {
			System.out.println(e);
		}
	}
	
	public static void doBooleanWaitTestWithGenuineFix() {
		try {
			System.out.println("Thread Polling Sample Test: [Starts]");
			new Thread(() -> {

				atomicWait.set(true);

				try {
					System.out.println("[Child]: Parent, I will sleep. Wait for me.");
					Thread.sleep(10_000);

					atomicWait.set(false);

					System.out.println("[Child]: Parent, I got up from sleep.");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			}).start();

			try {
				// The below sleep from Parent was needed to wait till the 'wait' shared
				// variable was set to 'true' by the Child.
				Thread.sleep(2_000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}

			System.out.println("[Parent]: I will wait Child.");
			
			while (atomicWait.get()) {
					//do nothing
			}
			
			System.out.println("[Parent]: Finally, you got up!");

			System.out.println("Thread Polling Sample Test: [Ends]");
		} catch (Error | Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 */	
	public static void doBooleanWaitTestWithTweak() {
		try {
			System.out.println("Thread Polling Sample Test: [Starts]");
			new Thread(() -> {

				wait = true;

				try {
					System.out.println("[Child]: Parent, I will sleep. Wait for me.");
					Thread.sleep(10_000);

					wait = false;

					System.out.println("[Child]: Parent, I got up from sleep.");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			}).start();

			try {
				// The below sleep from Parent was needed to wait till the 'wait' shared
				// variable was set to 'true' by the Child.
				Thread.sleep(2_000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}

			System.out.println("[Parent]: I will wait Child.");

			while (wait) {
				// System.out.println("[Parent]: Child, did you get up?");
				try {// unnecessary code and this is the fix with a tweak.
					Thread.sleep(1_000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			System.out.println("[Parent]: Finally, you got up!");

			System.out.println("Thread Polling Sample Test: [Ends]");
		} catch (Error | Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Below function shouldn't be called as it will hang.
	 */
	@Deprecated
	public static void doCounterWaitTest() {
		Object lock = new Object();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				synchronized (lock) {
					counter++;
				}

				try {
					Thread.sleep(5_000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				synchronized (lock) {
					System.out.println(counter);
				}
			}
		}).start();

		synchronized (lock) {
			while (counter <= 5) {

			}
		}
		System.out.println("Reached!");
	}

	public static void doCounterWaitTestWithTweak() {

		new Thread(() -> {
			for (int i = 0; i < 10; i++) {

				counter++;

				try {
					Thread.sleep(5_000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println(counter);

			}
		}).start();

		while (counter <= 5) {

			try {// a tweak to fix the hanging issue.
				Thread.sleep(1_000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("5 has Reached!");
	}
	
	public static void doCounterWaitTestWithGenuineFix() {
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {

				atomicCounter.getAndIncrement();

				try {
					Thread.sleep(5_000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println(atomicCounter.get());

			}
		}).start();

		// while (counter <= 5) {
		while (atomicCounter.get() <= 5) {

			// do nothing.
		}

		System.out.println("5 has Reached! I (main) will exit now.");
	}
	
	public static void doWaitTestWithExecutors() {

		System.out.println("Thread Polling Sample Test (With Executors): [Starts]");

		ExecutorService service = null;
		Future<Boolean> childMessage = null;
		try {
			service = Executors.newSingleThreadExecutor();
			
			childMessage = service.submit(() -> {
				System.out.println("[Child]: Parent, I will sleep. Wait for me.");
				return true;
			});
			
			//Also, can wait for specific duration - childMessage.get(10, TimeUnit.SECONDS);
			if (childMessage.get()) {
				System.out.println("[Parent]: I will wait Child.");
			}

			childMessage = service.submit(() -> {

				Thread.sleep(10_000);
				System.out.println("[Child]: Parent, I got up from sleep.");
				return true;
			});

			if (childMessage.get()) {
				System.out.println("[Parent]: Finally, you got up!");
			}

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			System.out.println("Communication between Parent and Child cannot proceed due to the following issue.");
			System.out.println(e);
		} finally {
			if (service != null)
				service.shutdown();
		}
		
		System.out.println("Thread Polling Sample Test (With Executors): [Ends]");
	}
}
