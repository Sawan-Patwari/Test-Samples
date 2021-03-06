package favouritePackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
		executeTest4();
		executeTest5();
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

	/**
	 * I should be able to test this method easily from TestQuickProg.java as well.
	 */
	public static void executeTest4() {
		try {
			Synchronisation.executeTest4();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	/**
	 * I should be able to test this method easily from TestQuickProg.java as well.
	 */
	public static void executeTest5() {
		try {
			Synchronisation.executeTest5();
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
		
		enum RE_ENTRANT_RW_LOCK{
			WITH(true), WITHOUT(false);

			@SuppressWarnings("unused")
			private boolean option;

			RE_ENTRANT_RW_LOCK(boolean option) {
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

		private static void doTest(SYNCHRONISATION option) throws InterruptedException {
			ExecutorService service = null;
			try {
				service = Executors.newFixedThreadPool(20);

				System.out.println("Sub-Test-1: [Started]");
				Synchronisation syncTester = new Synchronisation();

				for (int i = 0; i < 10; i++)
					service.submit(() -> syncTester.doDisplay(option));

				if (option == SYNCHRONISATION.WITHOUT) {
					Thread.sleep(THREE_SECONDS);
				} else {
					Thread.sleep(ELEVEN_SECONDS);
				}

				System.out.println("Sub-Test-1: [Ended]");
				Thread.sleep(3 * THREE_SECONDS);
				System.out.println("Sub-Test-2: [Started]");
				Thread.sleep(THREE_SECONDS);

				/*
				 * This class is not completely thread-safe as only one of the two methods is
				 * thread-safe.
				 */
				class CollectionsSynchronizedMethodsTest {
					private List<Integer> list = Collections
							.synchronizedList(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

					/*
					 * Synchronized methods of Collections class will not synchronize iterator based
					 * code, but only get and set based operations. Need explicit synchronized block
					 * if the code is accessed by multiple threads. Thus, this method shouldn't be
					 * executed in a multi-thread environment.
					 */
					public void doUnsynchronizedIteratorTest() {// not a thread-safe method.
						System.out.println();
						for (int element : list)
							System.out.print(element + " ");
						System.out.println();
					}

					/*
					 * Synchronized methods of Collections class will not synchronize iterator based
					 * code, but only get and set based operations. Need explicit synchronized block
					 * if the code is accessed by multiple threads. Thus, the below method can be
					 * run in a multi-threaded environment.
					 */
					public void doSynchronizedIteratorTest() {// a thread-safe method.
						/*
						 * Method could have been marked as synchronized in which case below
						 * synchronized block would become redundant.
						 */

						/*
						 * {
						 * 
						 * Common code/activity block which can be done by multiple threads
						 * concurrently/simultaneously, in which case, using synchronized blocks would
						 * increase the system performance instead of making the methods as
						 * synchronized.
						 * 
						 * }
						 */

						/*
						 * The below two lines will be incorrect as new Object instance is used for
						 * every thread call on this method.
						 * 
						 * Object obj = new Object(); synchronized (obj) {
						 * 
						 */

						/*
						 * Below line can also be used.
						 * 
						 * synchronized (CollectionsSynchronizedMethodsTest.class) {
						 */
						synchronized (list) {
							/* synchronized (this) { //This will also work. */
							/*
							 * 
							 * This block of code needs to be executed in a single-threaded fashion as a
							 * shared resource is being accessed/used, in order to maintain data
							 * integrity/consistency/synchronization of the shared resource/data.
							 * 
							 */
							System.out.println();
							for (int element : list)
								System.out.print(element + " ");
							System.out.println();
						}
					}

				}

				CollectionsSynchronizedMethodsTest tester = new CollectionsSynchronizedMethodsTest();

				if (option == SYNCHRONISATION.WITHOUT) {
					System.out.println("tester.doUnsynchronizedIteratorTest(): [Started]");
					Thread.sleep(2 * THREE_SECONDS);
					for (int i = 0; i < 50; i++)
						service.submit(() -> tester.doUnsynchronizedIteratorTest());
					Thread.sleep(THREE_SECONDS);
					System.out.println();
					System.out.println("tester.doUnsynchronizedIteratorTest(): [Ended]");
				} else {
					System.out.println("tester.doSynchronizedIteratorTest(): [Started]");
					Thread.sleep(2 * THREE_SECONDS);
					for (int i = 0; i < 50; i++)
						service.submit(() -> tester.doSynchronizedIteratorTest());
					Thread.sleep(THREE_SECONDS);
					System.out.println();
					System.out.println("tester.doSynchronizedIteratorTest(): [Ended]");
				}
				System.out.println("Sub-Test-2: [Ended]");

			} finally {
				if (service != null)
					service.shutdown();

				/*
				 * Below call is not needed, quite frankly, because the wait times for the main
				 * thread are already in place inside this method.
				 */
				// service.awaitTermination(ONE_MINUTE, TimeUnit.MINUTES);
			}
		}

		private static void doTest(CONCURRENT_API option) throws InterruptedException {

			abstract class API {
				abstract void createData();

				abstract Integer getEachDataElement();

				abstract void reset();

				abstract Map<Integer, Integer> getWholeData();

				void runTest(ExecutorService service) throws InterruptedException {

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
						service.submit(() -> {
							/**
							 * Need a synchronized block since 'System.out' is also a resource being
							 * accessed by multiple threads at the same time. It will not be sufficient if
							 * the method 'getEachDataElement' is synchronized.
							 */
							synchronized (API.class) {
								System.out.println(this.getEachDataElement());
							}
						});

					Thread.sleep(THREE_SECONDS);

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

						/*
						 * 'synchronized' keyword not needed as it is a private access. The method which
						 * accesses this method should be synchronized if it is a public method and will
						 * be accessed by multiple threads else don't use the 'synchronized' keyword to
						 * even that method as well.
						 */
						private void put(Integer key, Integer value) {
							data.put(key, value);
						}

						/*
						 * 'synchronized' keyword not needed as it is a private access. The method which
						 * accesses this method should be synchronized if it is a public method and will
						 * be accessed by multiple threads else don't use the 'synchronized' keyword to
						 * even that method as well.
						 */
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

						/*
						 * This method doesn't need synchronization as it will be called by a single
						 * thread.
						 */
						public void reset() {
							initialPutPointer = 0;
							initialGetPointer = 0;
							data.clear();
						}

						/*
						 * This method doesn't need synchronization as it will be called by a single
						 * thread.
						 */
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

			final String successMessage = "All the data is deleted.";
			final String failureMessage = "Data is not deleted due to:";

			try {
				Map<String, Integer> data = null;
				if (option == BUG.WITH) {

					System.out.println("Sub-Test-1: [Started]");
					try {

						data = new HashMap<String, Integer>();
						data.put("A", 1);
						data.put("B", 2);
						for (String key : data.keySet())
							data.remove(key);

						if (data.isEmpty()) {
							System.out.println(successMessage);
						}
					} catch (ConcurrentModificationException e) {
						System.out.println(failureMessage + e);
					}
					System.out.println("Sub-Test-1: [Ended]");

					System.out.println("Sub-Test-2: [Started]");
					try {
						data = new HashMap<String, Integer>();
						data.put("A", 1);
						data.put("B", 2);

						Map<String, Integer> synchronizedData = Collections.synchronizedMap(data);

						// Synchronized methods of Collections class doesn't work with iterators.
						for (String key : synchronizedData.keySet())
							synchronizedData.remove(key);

						if (synchronizedData.isEmpty()) {
							System.out.println(successMessage);
						}
					} catch (ConcurrentModificationException e) {
						System.out.println(failureMessage + e);
					}
					System.out.println("Sub-Test-2: [Ended]");

				} else {
					System.out.println("{ConcurrentHashMap Sub-Test-1}:[Started]");
					data = new ConcurrentHashMap<String, Integer>();
					data.put("A", 1);
					data.put("B", 2);
					for (String key : data.keySet())
						data.remove(key);

					if (data.isEmpty()) {
						System.out.println(successMessage);
					}
					System.out.println("{ConcurrentHashMap Sub-Test-1}:[Ended]");

				}
			} catch (Exception e) {
				System.out.println(failureMessage + e);
			}

		}

		private static void doTest() {

			System.out.println("General Synchronisation/Concurrency based API samples: [Started]");
			try {
				System.out.println("ConcurrentHashMap Sample: [Started]");
				Map<String, Integer> map = new ConcurrentHashMap<>();
				map.put("A", 1);
				map.put("B", 2);
				System.out.println(map.get("B"));
				System.out.println("ConcurrentHashMap Sample: [Ended]");

				System.out.println("ConcurrentLinkedQueue Sample: [Started]");
				Queue<Integer> queue = new ConcurrentLinkedQueue<>();
				/*
				 * true if inserts (at front -head) else false but no exception thrown if it
				 * cannot insert due to capacity constraints unlike in add.
				 */
				queue.offer(12);
				System.out.println(queue.peek());// only selects the head
				System.out.println(queue.poll());// selects and removes the head
				System.out.println("ConcurrentLinkedQueue Sample: [Ended]");

				System.out.println("ConcurrentLinkedDeque Sample: [Started]");
				/*
				 * generally, meant for stack operations. true if inserts (at the tail end -
				 * last) else false but no exception thrown if it cannot insert due to capacity
				 * constraints unlike in add.
				 */
				Deque<Integer> deque = new ConcurrentLinkedDeque<>();
				deque.offer(6);// Also, same as offerLast().
				deque.push(23);// Also, same as addFirst(). Exception thrown if no space.
				System.out.println(deque.peek());// Same as peekFirst() - only selects first element.
				System.out.println(deque.pop());// Removes first element. Same as removeFirst().
				System.out.println("ConcurrentLinkedDeque Sample: [Ended]");

				System.out.println("LinkedBlockingQueue Sample: [Started]");
				try {

					BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
					blockingQueue.offer(76);// inserts at the head-end.
					blockingQueue.offer(90, 4, TimeUnit.SECONDS);// blocking call.
					System.out.println(blockingQueue.poll());
					System.out.println(blockingQueue.poll(5, TimeUnit.SECONDS));

				} catch (InterruptedException e) {
					System.out.println(e);
				}
				System.out.println("LinkedBlockingQueue Sample: [Ended]");

				System.out.println("LinkedBlockingDeque Sample: [Started]");
				try {
					BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();
					blockingDeque.offer(34);// inserts at the tail-end.
					blockingDeque.offerFirst(1, 1, TimeUnit.MINUTES);
					blockingDeque.offerLast(35, 5, TimeUnit.SECONDS);
					blockingDeque.offer(36, 4, TimeUnit.SECONDS);// blocking call.
					System.out.println(blockingDeque.poll());// removes the head (front end) without blocking.
					System.out.println(blockingDeque.poll(1_000, TimeUnit.MILLISECONDS));
					System.out.println(blockingDeque.pollFirst(800, TimeUnit.NANOSECONDS));
					System.out.println(blockingDeque.pollLast(1, TimeUnit.SECONDS));
				} catch (InterruptedException e) {
					System.out.println(e);
				}
				System.out.println("LinkedBlockingDeque Sample: [Ended]");

				System.out.println("CopyOnWriteArrayList Sample: [Started]");
				/*
				 * Copies all of the elements to a new underlying structure any time an element
				 * is added, modified, or removed from the collection. In case of modified
				 * scenario, copy will happen if the reference in the collection is changed
				 * (add(index, element)) but not if the content of the reference is changed as
				 * in the case of set(index, element) operation.
				 */
				List<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
				int i = 4;
				for (Integer item : list) {
					System.out.println(item);
					list.add(i++);// throws an exception if ArrayList is used instead of CopyOnWriteArrayList.
				}
				System.out.println(list);
				System.out.println("Size: " + list.size());

				for (Integer item : list) {
					System.out.println(item);
					list.add(2, 0);// This causes copy on write because the reference in the collection is changed.
				}
				System.out.println(list);
				System.out.println("Size: " + list.size());

				for (Integer item : list) {
					System.out.println(item);
					/*
					 * This operation will not cause copy on write because the content referenced in
					 * the collection is changed and not the reference itself.
					 */
					list.set(2, 100);
				}
				System.out.println(list);
				System.out.println("Size: " + list.size());

				System.out.println("CopyOnWriteArrayList Sample: [Ended]");

			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("General Synchronisation/Concurrency based API samples: [Ended]");
		}
		
		private static void doTest(RE_ENTRANT_RW_LOCK option) {
			
			abstract class BaseTester {
				
				protected List<Integer> numbers = new ArrayList<>();
				
				protected void createInitialData() {
					numbers.add(1);
					numbers.add(2);
					numbers.add(3);
					numbers.add(4);
					numbers.add(5);
				}
				
				abstract protected int readNumber(int index);
				abstract protected void writeNumber(int number, int index);
				
				public void runTest(ExecutorService service) {
					
					for (int i = 0; i < 40; i++) {
						final int temp = i;
						service.submit(() -> writeNumber(99999, temp));
						service.submit(() -> readNumber(temp));
					}
					
				}
				
			}
			
			ExecutorService service = null;
			BaseTester tester = null;
			try {
				
				service = Executors.newFixedThreadPool(60);
				if (option == RE_ENTRANT_RW_LOCK.WITHOUT) {
					
					class WithoutReEntrantFeature extends BaseTester {
						
						public WithoutReEntrantFeature() {
							createInitialData();
						}

						@Override
						protected int readNumber(int index) {
							// TODO Auto-generated method stub
							System.out.print("Reading:");
							int num = numbers.get(index % numbers.size());
							System.out.println(num);
							return num;
						}

						@Override
						protected void writeNumber(int number, int index) {
							// TODO Auto-generated method stub
							int existingNum = numbers.get(index%numbers.size());
							numbers.set(index, ++existingNum);
							System.out.print("Writing:" + number +" " + numbers.get(index%numbers.size()));							
							numbers.add(number);
						}
						
					}
					
					tester = new WithoutReEntrantFeature();
					tester.runTest(service);
					
				} else {
					
					/**
					 * Reentrant Read-Write Lock: Read lock can be with many threads at a time, 
					 * but write lock will always be with one of the many threads, and 
					 * when a thread has a write lock, no other thread can read or write. 
					 * This is a better solution compared to synchronized blocks or 
					 * synchronized methods.
					 * 					
					 * @author Sawan.Patwari
					 *
					 */
					class WithReEntrantFeature extends BaseTester {
						
						private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
						
						public WithReEntrantFeature() {
							createInitialData();
						}
						
						@Override
						protected int readNumber(int index) {
							// TODO Auto-generated method stub

							Lock lock = readWriteLock.readLock();
							try {
								lock.lock();
								int num = numbers.get(index % numbers.size());
								System.out.print("Reading: Got Lock -" +num);								
								return num;
							} finally {
								System.out.println("Read Lock Released.");
								lock.unlock();
							}
						}

						@Override
						protected void writeNumber(int number, int index) {
							// TODO Auto-generated method stub
							Lock lock = readWriteLock.writeLock();

							try {
								lock.lock();
								System.out.println("Writing: Got Lock.");
								Thread.sleep(10);
								int existingNum = numbers.get(index%numbers.size());
								numbers.set(index, ++existingNum);
								System.out.print("Writing:" + number +" " + numbers.get(index%numbers.size()));
								numbers.add(number);
								System.out.println(numbers);
							} catch (InterruptedException e) {
								System.out.println(e);
							} finally {
								System.out.println("Write Lock Released.");
								lock.unlock();
							}

						}
						
					}
					
					tester = new WithReEntrantFeature();
					tester.runTest(service);
				}
				
			}catch(Exception e) {
				System.out.println(e);
			} finally {
				if (service != null)
					service.shutdown();
				if (service != null) {
					
					try {
						((ExecutorService) service).awaitTermination(1l, TimeUnit.MINUTES);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}			
				}
			}
		}

		public static void executeTest1() throws InterruptedException {

			System.out.println("executeTest1(): [Started]");

			System.out.println("Without Synchronisation Test: [Started]");
			Synchronisation.doTest(SYNCHRONISATION.WITHOUT);
			Thread.sleep(THREE_SECONDS);
			System.out.println("Without Synchronisation Test: [Ended]");
			Thread.sleep(THREE_SECONDS);
			System.out.println("With Synchronisation Test: [Started]");
			Synchronisation.doTest(SYNCHRONISATION.WITH);
			System.out.println("With Synchronisation Test: [Ended]");

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

		public static void executeTest4() throws InterruptedException {

			System.out.println("executeTest4(): [Started]");

			Synchronisation.doTest();

			System.out.println("executeTest4(): [Ended]");
		}
		
		public static void executeTest5() throws InterruptedException {

			System.out.println("executeTest5(): [Started]");

			System.out.println("Without Re-entrant Read-Write Lock Test: [Started]");
			Synchronisation.doTest(RE_ENTRANT_RW_LOCK.WITHOUT);
			Thread.sleep(THREE_SECONDS);
			System.out.println("Without Re-entrant Read-Write Lock Test: [Ended]");
			Thread.sleep(THREE_SECONDS);
			System.out.println("With Re-entrant Read-Write Lock Test: [Started]");
			Synchronisation.doTest(RE_ENTRANT_RW_LOCK.WITH);
			System.out.println("With Re-entrant Read-Write Lock Test: [Ended]");

			System.out.println("executeTest5(): [Ended]");
		}
	}
}

/*
 * Note-1: Concurrent collection classes list: ConcurrentHashMap - implements -
 * ConcurrentMap - no ordering and no sorting. ConcurrentLinkedDeque -
 * implements - Deque - Ordering but no sorting. ConcurrentLinkedQueue -
 * implements - Queue - Ordering but no sorting. ConcurrentSkipListMap -
 * implements - ConcurrentMap, SortedMap, NavigableMap - Ordering and sorting.
 * (ConcurrentSkipListMap equivalent to TreeMap with concurrency)
 * ConcurrentSkipListSet - implements - SortedSet, NavigableSet - Ordering and
 * sorting. (ConcurrentSkipListMap equivalent to TreeSet with concurrency)
 * CopyOnWriteArrayList - implements - List - Ordering but no sorting.
 * CopyOnWriteArraySet - implements - Set - no ordering and no sorting.
 * LinkedBlockingDeque - implements - BlockingQueue, BlockingDeque - Ordering
 * and blocking but no sorting. LinkedBlockingQueue - implements - BlockingQueue
 * - Ordering and blocking but no sorting.
 */

/*
 * Note-2: Synchronized methods in Collections:
 * synchronizedCollection(Collection<T> c) synchronizedList(List<T> list)
 * synchronizedMap(Map<K,V> m) synchronizedNavigableMap(NavigableMap<K,V> m)
 * synchronizedNavigableSet(NavigableSet<T> s) synchronizedSet(Set<T> s)
 * synchronizedSortedMap(SortedMap<K,V> m) synchronizedSortedSet(SortedSet<T> s)
 */
