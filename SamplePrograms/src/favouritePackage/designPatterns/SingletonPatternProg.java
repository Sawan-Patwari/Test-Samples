package favouritePackage.designPatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class demonstrates usage of Singleton Pattern, Reentrant Lock, and Executor Service.
 * 
 * @author Sawan.Patwari
 *
 */
public class SingletonPatternProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StorageAreaManager storageAreaManager = new StorageAreaManager();
		storageAreaManager.setWorkingSeconds(30);//working time in seconds.
		storageAreaManager.setNumberOfWorkers(5);//number of subordinates.
		storageAreaManager.doManage();
		
		System.out.println("Main will shutdown now.");
	}

}

/**
 * Singleton class.
 * 
 * @author Sawan.Patwari
 *
 */
class StorageArea {
	
	//********************Singleton Related Work Started*********************
	private StorageArea() {
		
	}
	
	//Below line is also fine, in which case, getInstance() is not needed.
	//public static final StorageArea singletonInstance = new StorageArea();
	
	private static final StorageArea singletonInstance = new StorageArea();
	
	public static StorageArea getInstance() {
		return singletonInstance;
	}
	
	//********************Singleton Related Work Completed*********************
	
	
	//**************** Business Related Code Started **************************
	private final Item riceItem = new Item("RICE");
	private final Item saltItem = new Item("SALT");
	private final Item sugarItem = new Item("SUGAR");

	public Item getRiceItem() {
		return riceItem;
	}
	
	public Item getSaltItem() {
		return saltItem;
	}
	
	public Item getSugarItem() {
		return sugarItem;
	}
	//**************** Business Related Code Ended **************************
}

class Item {
	
	private String itemType;
	
	public String getItemType() {
		return itemType;
	}

	public Item(String itemType) {
		this.itemType = itemType;
	}

	private int quantity;
	
	private ReadWriteLock locker = new ReentrantReadWriteLock();
	
	public void increaseQuantity(int quantity) {
		
		if(quantity < 0) {
			throw new IllegalArgumentException("Inappropriate quantity.");
		}
		
		Lock lock = locker.writeLock();
		lock.lock();
		this.quantity += quantity;
		lock.unlock();
		
	}
	
	public void decreaseQuantity(int quantity) {
		
		if(quantity < 0) {
			throw new IllegalArgumentException("Inappropriate quantity.");
		}
		
		Lock lock = locker.writeLock();
		lock.lock();
		this.quantity -= quantity;
		if(this.quantity < 0) {
			this.quantity = 0;
		}
		lock.unlock();
	}
	
	public int getQuantity() {
		
		Lock lock = locker.readLock();
		lock.lock();
		int readQuantity = quantity;		
		lock.unlock();
		
		return readQuantity;
	}
	
	@Override
	public synchronized String toString() {
		// TODO Auto-generated method stub
		return "Item Type:"+getItemType()+" "+"Quantity:"+getQuantity();
	}
}

class StorageAreaManager {
	
	private int workingSeconds;
	private int numberOfWorkers;

	public int getNumberOfWorkers() {
		return numberOfWorkers;
	}

	public void setNumberOfWorkers(int numberOfWorkers) {
		
		if(numberOfWorkers < 1) {
			throw new IllegalArgumentException("Number of workers cannot be less than 1.");
		}
		
		this.numberOfWorkers = numberOfWorkers;
	}

	public int getWorkingSeconds() {
		return workingSeconds;
	}

	public void setWorkingSeconds(int workingSeconds) {
		
		if(workingSeconds < 30) {
			throw new IllegalArgumentException("Amount of time for the Manager to work cannot be less than 30 seconds.");
		}
		
		this.workingSeconds = workingSeconds;
	}
	
	private List<Future<?>> futuresOfSubmittedCoreTasks = new ArrayList<>();

	private List<Future<?>> getFuturesOfSubmittedCoreTasks() {
		return futuresOfSubmittedCoreTasks;
	}
	
	private ReadWriteLock locker = new ReentrantReadWriteLock();

	public void doManage() {

		ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
		ExecutorService coreManagementService = Executors.newFixedThreadPool(getNumberOfWorkers());

		Runnable coreManagementTask = () -> {
			doCoreManagement(coreManagementService);
		};

		ScheduledFuture<?> scheduledServiceFuture = scheduledService.scheduleAtFixedRate(coreManagementTask, 0, 1,
				TimeUnit.SECONDS);

		Runnable shutdownScheduledServiceTask = () -> {
			shutDownScheduledService(scheduledServiceFuture, scheduledService);
		};

		scheduledService.scheduleAtFixedRate(shutdownScheduledServiceTask, getWorkingSeconds(), 1, TimeUnit.SECONDS);

		ScheduledExecutorService coreManagementServiceShutdowner = Executors.newSingleThreadScheduledExecutor();

		Runnable shutDownCoreManagementServiceTask = () -> {
			shutDownCoreManagementService(coreManagementServiceShutdowner, coreManagementService);
		};

		coreManagementServiceShutdowner.scheduleAtFixedRate(shutDownCoreManagementServiceTask, getWorkingSeconds(), 1, TimeUnit.SECONDS);

	}
	
	private void doCoreManagement(ExecutorService coreManagementService) {
		
		Runnable task1 = () -> {
			StorageArea.getInstance().getRiceItem().increaseQuantity(1000);
		};
		
		Runnable task2 = () -> {
			StorageArea.getInstance().getRiceItem().decreaseQuantity(100);
		};
		
		Runnable task3 = () -> {
			System.out.println(StorageArea.getInstance().getRiceItem());
			
		};
		
		Future<?> futureOfTask1 = coreManagementService.submit(task1);
		Future<?> futureOfTask2 = coreManagementService.submit(task2);
		Future<?> futureOfTask3 = coreManagementService.submit(task3);
		
		Lock lock = locker.writeLock();
		lock.lock();
		getFuturesOfSubmittedCoreTasks().add(futureOfTask1);
		getFuturesOfSubmittedCoreTasks().add(futureOfTask2);
		getFuturesOfSubmittedCoreTasks().add(futureOfTask3);
		lock.unlock();
	}
	
	private void shutDownScheduledService(ScheduledFuture<?> scheduledServiceFuture, ScheduledExecutorService scheduledService) {
		
		if(scheduledServiceFuture.isDone()) {
			scheduledService.shutdown();
		}
	}
	
	private void shutDownCoreManagementService(ScheduledExecutorService coreManagementServiceShutdowner, ExecutorService coreManagementService) {
				
		boolean shutdown = true;
		Lock lock = locker.readLock();
		lock.lock();
		
		for(Future<?> x : getFuturesOfSubmittedCoreTasks()) {
			
			if(!x.isDone()) {
				
				shutdown = false;
				break;
			}
		}

		lock.unlock();
		
		if(shutdown) {
			coreManagementService.shutdown();
			try {
				coreManagementService.awaitTermination(2, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to Terminate Core Management Service");
			}
			
			coreManagementServiceShutdowner.shutdown();
		}
	}
	
}
