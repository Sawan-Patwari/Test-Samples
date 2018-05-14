package test.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		doSampleExecuteTest();
		doSampleSubmitTest();
	}

	static void doSampleExecuteTest() {
		ExecutorService service = null;
		try {
			// SingleThreadExecutor: no guarantee in the order of execution of tasks when
			// more threads are created. Order of execution is preserved for small number
			// of threads/tasks.
			service = Executors.newSingleThreadExecutor();
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
	
	static void doSampleSubmitTest() {
		ExecutorService service = null;
		try {
			//'Executors' - A Factory pattern.
			service = Executors.newSingleThreadExecutor();
			
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

}
