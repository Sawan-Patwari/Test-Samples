package favouritePackage;

import java.util.Objects;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class ThreadsProg extends Thread implements Runnable {

	/**
	 * I wanted to synchronize the custom thread creation activity between JVM call
	 * and my explicit 'main' call as a lambda expression as part of the static
	 * block.
	 * 
	 * @param args
	 */
	synchronized public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * Observation: Seems like the the 'main' method call is not synchronized all
		 * the time.
		 */
		if (Objects.isNull(args))
			System.out.println("I called main.");
		else
			System.out.println("JVM called main.");

		// one of the ways of running lambda code.
		Runnable x = () -> {
			new MyFirstThreadClass("MyFirstThreadClass").start();

		};
		x.run();

		// running lambda API.
		MyLambdaAPI.doSampleTest(() -> {
			new MySecondThreadClass().start();
		});

		// running lambda API.
		MyLambdaAPI.doSampleTest(x);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		main(null);// I am calling main.
	}

	static {
		Runnable x = () -> {
			new ThreadsProg().start();
		};

		MyLambdaAPI.doSampleTest(x);
	}

}

// Want thread details and use the class as part of my sample lambda API.
class MyFirstThreadClass extends Thread implements Runnable, Activity, Displayable {

	public MyFirstThreadClass() {
		super("MyFirstThreadClass");
	}

	public MyFirstThreadClass(String name) {
		super(name);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		commonThreadInfo(this);
		doActivity(this);
		customMessage(this);
	}

	@SuppressWarnings("static-access")
	@Override
	public void doActivity(Thread thread) {
		// TODO Auto-generated method stub
		try {
			// do some activity.
			thread.sleep(SLEEP_FOR_5_SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	@Override
	public void customMessage(Thread thread) {
		// TODO Auto-generated method stub
		StringBuilder message = new StringBuilder().append("My thread-id is:" + thread.getId()).append(blankSpace)
				.append("Terminating after 5 seconds of sleep.");

		System.out.println(message);
	}

}

// Want thread details and use the class as part of my sample lambda API.
class MySecondThreadClass extends Thread implements Runnable, Activity, Displayable {

	public MySecondThreadClass() {
		super("MySecondThreadClass");
	}

	public MySecondThreadClass(String name) {
		super(name);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		commonThreadInfo(this);
		doActivity(this);
		customMessage(this);
	}

	@SuppressWarnings("static-access")
	@Override
	public void doActivity(Thread thread) {
		// TODO Auto-generated method stub
		try {
			// do some activity.
			thread.sleep(SLEEP_FOR_10_SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

	}

	@Override
	public void customMessage(Thread thread) {
		// TODO Auto-generated method stub
		StringBuilder message = new StringBuilder().append("My thread-id is:" + thread.getId()).append(blankSpace)
				.append("Terminating after 10 seconds of sleep.");

		System.out.println(message);
	}

}

interface Displayable {

	String blankSpace = " ";

	default void commonThreadInfo(Thread thread) {

		StringBuilder threadDetails = new StringBuilder().append("Thread Name:" + thread.getName()).append(blankSpace)
				.append("Thread ID:" + thread.getId()).append(blankSpace)
				.append("Thread priority:" + thread.getPriority());

		System.out.println(threadDetails);
	}

	void customMessage(Thread thread);
}

interface Activity {
	long SLEEP_FOR_5_SECONDS = 5000;
	long SLEEP_FOR_10_SECONDS = 10_000;

	void doActivity(Thread thread);
}

class MyLambdaAPI {

	static void doSampleTest(Runnable lfunction) {
		lfunction.run();
	}
}
