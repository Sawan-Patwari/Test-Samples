package test.threads;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class ThreadsSynchronisationProg {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		executeTest1();
		executeTest2();
		executeTest3();
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

	/**
	 * I should be able to test this method easily from TestQuickProg.java as well.
	 */
	public static void executeTest2() {
		try {
			Synchronisation.executeTest2();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	/**
	 * I should be able to test this method easily from TestQuickProg.java as well.
	 */
	public static void executeTest3() {
		try {
			Synchronisation.executeTest3();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	private static class Synchronisation {

		private int counter = 0;// without using Atomic int.

		private static long ELEVEN_SECONDS = 11_000;
		private static long THREE_SECONDS = 3_000;
		private static long ONE_SECOND = 1_000;

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
		
		enum BUG {
			WITH(true), WITHOUT(false);

			@SuppressWarnings("unused")
			private boolean option;

			BUG(boolean option) {
				this.option = option;
			}

		}
		
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

		private static void doTest(CONCURRENT_API option) {

			abstract class API {
				abstract void createData();

				abstract Integer getEachDataElement();

				abstract void reset();

				abstract Map<Integer, Integer> getWholeData();

				void runTest(ExecutorService service) {

					this.reset();

					for (int i = 0; i < 10; i++)
						service.submit(() -> this.createData());

					try {
						Thread.sleep(THREE_SECONDS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}

					System.out.println("Displaying data by using Multiple Threads:");
					for (int i = 0; i < 10; i++)
						service.submit(() -> 
									{
										/**
										 * Need a synchronized block since 'System.out' is also a resource
										 * being accessed by multiple threads at the same time. It will not
										 * be sufficient if the method 'getEachDataElement' is synchronized.
										 */
										synchronized(API.class) {
											System.out.println(this.getEachDataElement());
										}
									}
								);

					try {
						Thread.sleep(THREE_SECONDS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}

					System.out.println("Displaying data by using a Single Thread:");
					this.getWholeData().forEach((k, v) -> System.out.println(v));
				}
			}

			ExecutorService service = null;
			API sample = null;
			try {
				service = Executors.newFixedThreadPool(20);

				if (option == CONCURRENT_API.WITH) {

					class WithConcurrentAPI extends API {

						private AtomicInteger initialPutPointer = new AtomicInteger(0);
						private AtomicInteger initialGetPointer = new AtomicInteger(0);

						private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();

						private void put(Integer key, Integer value) {
							data.put(key, value);
						}

						private Integer get(Integer key) {
							return data.get(key);
						}

						public void createData() {
							initialPutPointer.incrementAndGet();
							put(initialPutPointer.get(), initialPutPointer.get());
						}

						public Integer getEachDataElement() {
							initialGetPointer.incrementAndGet();
							return get(initialGetPointer.get());
						}

						public void reset() {
							initialPutPointer.set(0);
							initialGetPointer.set(0);
							data.clear();
						}

						@Override
						Map<Integer, Integer> getWholeData() {
							// TODO Auto-generated method stub
							return data;
						}

					}

					sample = new WithConcurrentAPI();
					sample.runTest(service);

				} else {

					class WithoutConcurrentAPI extends API {
						private int initialPutPointer = 0;
						private int initialGetPointer = 0;

						private Map<Integer, Integer> data = new HashMap<Integer, Integer>();
						
						//'synchronized' keyword not needed as it is a private access. The method 
						//which accesses this method should be synchronized if it is a public method
						// and will be accessed by multiple threads else don't use the 
						//'synchronized' keyword to even that method as well.
						private void put(Integer key, Integer value) {
							data.put(key, value);
						}
						
						//'synchronized' keyword not needed as it is a private access. The method 
						//which accesses this method should be synchronized if it is a public method
						// and will be accessed by multiple threads else don't use the 
						//'synchronized' keyword to even that method as well.
						private Integer get(Integer key) {
							return data.get(key);
						}

						public synchronized void createData() {
							++initialPutPointer;
							put(initialPutPointer, initialPutPointer);
						}

						public synchronized Integer getEachDataElement() {
							++initialGetPointer;
							return get(initialGetPointer);
						}

						//This method doesn't need synchronization as it will be called
						//by a single thread.
						public void reset() {
							initialPutPointer = 0;
							initialGetPointer = 0;
							data.clear();
						}

						//This method doesn't need synchronization as it will be called
						//by a single thread.
						@Override
						Map<Integer, Integer> getWholeData() {
							// TODO Auto-generated method stub
							return data;
						}
					}

					sample = new WithoutConcurrentAPI();
					sample.runTest(service);

				}

			} finally {
				if (service != null)
					service.shutdown();
			}

		}
		
		private static void doTest(BUG option) {
			
			try {
				Map<String, Integer> data = null;
				if (option == BUG.WITH) {
					data = new HashMap<String, Integer>();
					data.put("A", 1);
					data.put("B", 2);
					for (String key : data.keySet())
						data.remove(key);
					
					if(data.isEmpty()) {
						System.out.println("All the data deleted.");
					}
				} else {
					data = new ConcurrentHashMap<String, Integer>();
					data.put("A", 1);
					data.put("B", 2);
					for (String key : data.keySet())
						data.remove(key);
					
					if(data.isEmpty()) {
						System.out.println("All the data deleted.");
					}
				}
			} catch (ConcurrentModificationException e) {
				System.out.println("Data is not deleted due to:"+e);
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

			System.out.println("Synchronisation with out Concurrent API Test: [Started]");
			Synchronisation.doTest(CONCURRENT_API.WITHOUT);
			Thread.sleep(THREE_SECONDS);
			System.out.println("Synchronisation with out Concurrent API Test: [Ended]");
			Thread.sleep(THREE_SECONDS);
			System.out.println("Synchronisation with Concurrent API Test: [Started]");
			Synchronisation.doTest(CONCURRENT_API.WITH);
			Thread.sleep(ELEVEN_SECONDS);
			System.out.println("Synchronisation with Concurrent API Test: [Ended]");

			System.out.println("executeTest2(): [Ended]");
		}

		public static void executeTest3() throws InterruptedException {

			System.out.println("executeTest3(): [Started]");

			System.out.println("Synchronisation.doTest(BUG.WITH): [Started]");
			Synchronisation.doTest(BUG.WITH);
			Thread.sleep(THREE_SECONDS);
			System.out.println("Synchronisation.doTest(BUG.WITH): [Ended]");
			Thread.sleep(THREE_SECONDS);
			System.out.println("Synchronisation.doTest(BUG.WITHOUT): [Started]");
			Synchronisation.doTest(BUG.WITHOUT);
			Thread.sleep(THREE_SECONDS);
			System.out.println("Synchronisation.doTest(BUG.WITHOUT): [Ended]");

			System.out.println("executeTest3(): [Ended]");
		}
	}
}
