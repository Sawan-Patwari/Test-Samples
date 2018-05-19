package test.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class ExecutorServiceProg {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		int FIVE_THREADS = 5;
		int THREE_THREADS = 3;
		long FIVE_SECONDS = 5_000;
		
		//It would be better if we make the below calls through separate programs.
		ExecutorService service = Executors.newSingleThreadExecutor();
		doSampleExecuteTest(service);//we are shutting down the service. 
		//Need new service from the factory.
		Thread.sleep(FIVE_SECONDS);
		service = Executors.newSingleThreadExecutor();
		doSampleSubmitTest(service);
		
		Thread.sleep(FIVE_SECONDS);
		
		//It would be better if we make the below calls through separate programs.
		service = Executors.newCachedThreadPool();
		doSampleExecuteTest(service);		
		Thread.sleep(FIVE_SECONDS);
		service = Executors.newCachedThreadPool();
		doSampleSubmitTest(service);
		
		Thread.sleep(FIVE_SECONDS);
		
		//It would be better if we make the below calls through separate programs.
		service = Executors.newFixedThreadPool(FIVE_THREADS);
		doSampleExecuteTest(service);
		Thread.sleep(FIVE_SECONDS);
		service = Executors.newFixedThreadPool(FIVE_THREADS);
		doSampleSubmitTest(service);
		
		Thread.sleep(FIVE_SECONDS);
		
		//It would be better if we make the below calls through separate programs.
		ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
		doSampleScheduleTest(scheduledService);
		Thread.sleep(FIVE_SECONDS);
		scheduledService = Executors.newScheduledThreadPool(THREE_THREADS);
		doSampleScheduleTest(scheduledService);
		
		/* Basic Test Pattern:
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		doSampleExecuteTest(service);
		doSampleSubmitTest(service);
		doSampleScheduleTest(service);*/
	}

	static void doSampleExecuteTest(ExecutorService service) {
		
		try {
			// SingleThreadExecutor: no guarantee in the order of execution of tasks when
			// more threads are created. Order of execution is preserved for small number
			// of threads/tasks.
			
			System.out.println("[Test Begin]");
			service.execute(() -> System.out.println("Executing Task-1"));
			service.execute(() -> {
				System.out.println("Executing Task-2");
			});
			service.execute(() -> System.out.println("Executing Task-3"));
			System.out.println("[Test End]");
		} finally {//ExecutorService interface doesn't implement AutoCloseable.
			if (service != null)
				service.shutdown();
		}
		
		/*
		 * Output: Asynchronous
			[Test Begin]
			[Test End]
			Executing Task-1
			Executing Task-2
			Executing Task-3
		 */
		
		/*
		 * If the output was as follows, then it would be Synchronous, 
		 * as in the case of invokeAll() - the main waits till the results are returned by
		 * the invokeAll() via the Future object and the control is returned by 
		 * invokeAll() to the main.
		 	
		 	[Test Begin]			
			Executing Task-1
			Executing Task-2
			Executing Task-3
			[Test End]		 
		 */
	}
	
	static void doSampleSubmitTest(ExecutorService service) {
		
		try {
			
			//This is a Runnable Task submission, as there exists no return.
			Future<?> futureOfTask1 = service.submit(() -> System.out.println("Task-1"));
			
			//This is a Callable Task submission, as there exists a return with an Object.
			Future<String> futureOfTask2 = service.submit(() ->

					 {	
						System.out.println("Task-2");
						return "Task-2 Submitted and Executed";
					 }
													);
			
			//futureOfTask1.get() returns 'null', obviously, because Runnable task has been
			//submitted.
			System.out.println("Task-1 Completed?:"+futureOfTask1.isDone());
			
			System.out.println("Task-2 Completed?:"+futureOfTask2.isDone());
			try {
				//Got to be careful with the get(), if the task is too time consuming,
				//since the 'Future' instance is derived from Executors.submit, unlike
				//Executors.invokeAll() and Executors.invokeAny().
				System.out.println("Task-2 Status?:"+futureOfTask2.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}finally { 
			if (service != null)
				service.shutdown();
		}
		if (service != null) {
			try {
				service.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Check whether all tasks are finished
			if (service.isTerminated())
				System.out.println("All tasks finished");
			else
				System.out.println("At least one task is still running");
		}
	}
	
	@SuppressWarnings("unused")
	static void doSampleScheduleTest(ScheduledExecutorService service) {

		System.out.println("[doSampleScheduleTest()]: I started!");

		Runnable runnableTask1 = () -> System.out.println("Runnable Task-1 Done.");
		Runnable runnableTask2 = () -> {
			String taskNumber = "Task-2." + TaskNumberIncrementer.getTask2DecimalIncrementedValue();
			System.out.println("Recurring Runnable " + taskNumber + " Done.");
		};
		Runnable runnableTask3 = () -> {
			String taskNumber = "Task-3." + TaskNumberIncrementer.getTask3DecimalIncrementedValue();
			System.out.println("Recurring Runnable " + taskNumber + " Done.");
		};
		Callable<String> callableTask = () -> "Callable Task Done.";

		Future<?> runnableResult1 = service.schedule(runnableTask1, 10, TimeUnit.SECONDS);
		Future<String> callableResult = service.schedule(callableTask, 1, TimeUnit.SECONDS);

		Future<?> runnableResult2 = service.scheduleAtFixedRate(runnableTask2, 1, 1, TimeUnit.MINUTES);

		Future<?> runnableResult3 = service.scheduleWithFixedDelay(runnableTask3, 1, 2, TimeUnit.MINUTES);

		System.out.println("[doSampleScheduleTest()]: I am done with Runnable tasks!");

		try {
			System.out.println("[doSampleScheduleTest()]: I have time to wait for Callable " + "task output. "
					+ "The output is:" + callableResult.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[doSampleScheduleTest()]: I am exiting now.");
	}

}

/**
 * This class acts as a state maintainer for the lambda Runnable/Callable code.
 * This class sample is to know the previous value before getting the next task version number
 * inside the lambda Runnable code.
 * 
 * @author Sawan.Patwari
 *
 */
class TaskNumberIncrementer {
	private static int task2Incrementer = -1;
	private static int task3Incrementer = -1;

	public static int getTask2DecimalIncrementedValue() {
		return ++task2Incrementer;
	}

	public static int getTask3DecimalIncrementedValue() {
		return ++task3Incrementer;
	}
}
