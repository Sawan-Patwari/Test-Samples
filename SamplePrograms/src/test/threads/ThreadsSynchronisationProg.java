package test.threads;

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
						
						if(data.isEmpty()) {
							System.out.println(successMessage);
						}
					}catch(ConcurrentModificationException e) {
						System.out.println(failureMessage+e);
					}
					System.out.println("Sub-Test-1: [Ended]");
					
					System.out.println("Sub-Test-2: [Started]");		
					try {
						data = new HashMap<String, Integer>();
						data.put("A", 1);
						data.put("B", 2);
						
						Map<String,Integer> synchronizedData = Collections.synchronizedMap(data);
						
						//Synchronized methods of Collections class doesn't work with iterators.
						for (String key : synchronizedData.keySet())
							synchronizedData.remove(key);
						
						if(synchronizedData.isEmpty()) {
							System.out.println(successMessage);
						}
					}catch(ConcurrentModificationException e) {
						System.out.println(failureMessage+e);
					}
					System.out.println("Sub-Test-2: [Ended]");	
					
				} else {
					
					data = new ConcurrentHashMap<String, Integer>();
					data.put("A", 1);
					data.put("B", 2);
					for (String key : data.keySet())
						data.remove(key);
					
					if(data.isEmpty()) {
						System.out.println(successMessage);
					}
				}
			} catch (Exception e) {
				System.out.println(failureMessage+e);
			}

		}
		
		private static void doTest() {

			System.out.println("General Synchronisation/Concurrency based API samples: [Started]");
			try {
				System.out.println("ConcurrentHashMap Sample: [Started]");
				Map<String,Integer> map = new ConcurrentHashMap<>();
				map.put("A", 1);
				map.put("B", 2);
				System.out.println(map.get("B"));
				System.out.println("ConcurrentHashMap Sample: [Ended]");
				
				System.out.println("ConcurrentLinkedQueue Sample: [Started]");
				Queue<Integer> queue = new ConcurrentLinkedQueue<>();
				queue.offer(12);
				System.out.println(queue.peek());
				System.out.println(queue.poll());
				System.out.println("ConcurrentLinkedQueue Sample: [Ended]");
				
				System.out.println("ConcurrentLinkedDeque Sample: [Started]");
				Deque<Integer> deque = new ConcurrentLinkedDeque<>();
				deque.offer(6);
				deque.push(23);
				System.out.println(deque.peek());
				System.out.println(deque.pop());
				System.out.println("ConcurrentLinkedDeque Sample: [Ended]");
				
				System.out.println("LinkedBlockingQueue Sample: [Started]");
				try {
					
					BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
					blockingQueue.offer(76);
					blockingQueue.offer(90, 4, TimeUnit.SECONDS);
					System.out.println(blockingQueue.poll());
					System.out.println(blockingQueue.poll(5, TimeUnit.SECONDS));
					
				} catch (InterruptedException e) {
					System.out.println(e);
				}
				System.out.println("LinkedBlockingQueue Sample: [Ended]");
				
				System.out.println("LinkedBlockingDeque Sample: [Started]");
				try {
					BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();
					blockingDeque.offer(34);
					blockingDeque.offerFirst(1, 1, TimeUnit.MINUTES);
					blockingDeque.offerLast(35, 5, TimeUnit.SECONDS);
					blockingDeque.offer(36, 4, TimeUnit.SECONDS);
					System.out.println(blockingDeque.poll());
					System.out.println(blockingDeque.poll(1_000, TimeUnit.MILLISECONDS));
					System.out.println(blockingDeque.pollFirst(800, TimeUnit.NANOSECONDS));
					System.out.println(blockingDeque.pollLast(1, TimeUnit.SECONDS));
				} catch (InterruptedException e) {
					System.out.println(e);
				}
				System.out.println("LinkedBlockingDeque Sample: [Ended]");
				
				System.out.println("CopyOnWriteArrayList Sample: [Started]");
				List<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
				int i = 4;
				for (Integer item : list) {
					System.out.println(item);
					list.add(i++);
				}
				System.out.println("Size: " + list.size());
				System.out.println("CopyOnWriteArrayList Sample: [Ended]");
				

			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("General Synchronisation/Concurrency based API samples: [Ended]");
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
		
		public static void executeTest4() throws InterruptedException {
			
			System.out.println("executeTest4(): [Started]");
			
			Synchronisation.doTest();
			
			System.out.println("executeTest4(): [Ended]");
		}
	}
}

/*
 * Note-1: Concurrent collection classes list:
 * ConcurrentHashMap - implements - ConcurrentMap - no ordering and no sorting.
 * ConcurrentLinkedDeque - implements - Deque - Ordering but no sorting.
 * ConcurrentLinkedQueue - implements - Queue - Ordering but no sorting.
 * ConcurrentSkipListMap - implements - ConcurrentMap, SortedMap, NavigableMap - Ordering and sorting.
 * ConcurrentSkipListSet - implements - SortedSet, NavigableSet - Ordering and sorting.
 * CopyOnWriteArrayList - implements - List - Ordering but no sorting.
 * CopyOnWriteArraySet - implements - Set - no ordering and no sorting.
 * LinkedBlockingDeque - implements - BlockingQueue, BlockingDeque - Ordering and blocking but no sorting.
 * LinkedBlockingQueue - implements - BlockingQueue - Ordering and blocking but no sorting.
 */

/*
 * Note-2: Synchronized methods in Collections:
 * synchronizedCollection(Collection<T> c) 
 * synchronizedList(List<T> list)
 * synchronizedMap(Map<K,V> m) 
 * synchronizedNavigableMap(NavigableMap<K,V> m)
 * synchronizedNavigableSet(NavigableSet<T> s) 
 * synchronizedSet(Set<T> s)
 * synchronizedSortedMap(SortedMap<K,V> m) 
 * synchronizedSortedSet(SortedSet<T> s)
 */
