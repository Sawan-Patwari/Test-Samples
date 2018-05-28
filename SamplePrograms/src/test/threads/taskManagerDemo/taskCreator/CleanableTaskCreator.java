package test.threads.taskManagerDemo.taskCreator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import test.threads.taskManagerDemo.task.BikeCleaningTask;
import test.threads.taskManagerDemo.task.CarCleaningTask;
import test.threads.taskManagerDemo.task.CleanableTask;
import test.threads.taskManagerDemo.task.HouseCleaningTask;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class CleanableTaskCreator extends TaskCreator<CleanableTask> {

	Predicate<CleanableTask> isNull = k -> Objects.isNull(k);

	/**
	 * 
	 * 
	 * @param numberOfTasks
	 * @return
	 */
	public Stream<CleanableTask> getStreamOfBikeCleaningTasks(long numberOfTasks) {

		return getStreamOfTasks(numberOfTasks, BikeCleaningTask.class);
	}

	/**
	 * 
	 * 
	 * @param numberOfTasks
	 * @return
	 */
	public Stream<CleanableTask> getStreamOfCarCleaningTasks(long numberOfTasks) {

		return getStreamOfTasks(numberOfTasks, CarCleaningTask.class);
	}

	/**
	 * 
	 * 
	 * @param numberOfTasks
	 * @return
	 */
	public Stream<CleanableTask> getStreamOfHouseCleaningTasks(long numberOfTasks) {

		return getStreamOfTasks(numberOfTasks, HouseCleaningTask.class);
	}
}
