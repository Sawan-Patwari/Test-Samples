package test.threads.taskManagerDemo.taskCreator;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class TaskCreator<T> {
	/*
	 Not sure why Eclipse is giving an issue with the below Generics code. 
	 https://stackoverflow.com/questions/27771488/the-target-type-of-this-expression-must-be-a-functional-interface#27776288
	 
	//Predicate<T> ignoreNull = k -> !Objects.isNull(k);
	
	//Predicate<T> isNull = k -> Objects.isNull(k);
	 
	//Moved the above code to ManageableTaskCreator.java because of the issue.
	
	*/
	protected boolean isNumberOfTasksInvalid(long numberOfTasks) {

		if (numberOfTasks < 1l)
			return true;
		else
			return false;
	}

	/**
	 * The Stream can have null objects. Caller needs to filter them.
	 * 
	 * @param numberOfTasks
	 * @param clazz
	 * @return
	 */
	protected Stream<T> getStreamOfTasks(long numberOfTasks, Class<? extends T> clazz) {

		if (isNumberOfTasksInvalid(numberOfTasks))
			numberOfTasks = 1;

		Stream<Long> taskStreamingHelper = Stream.iterate(1l, n -> n + 1).limit(numberOfTasks);

		Stream<T> streamOfTasks = taskStreamingHelper.parallel().map(k -> {
			try {
				return clazz.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				return null;
			}
		});

		return streamOfTasks;
	}

}
