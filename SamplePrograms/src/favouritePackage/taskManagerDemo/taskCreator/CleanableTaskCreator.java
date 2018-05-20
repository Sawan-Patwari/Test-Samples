package favouritePackage.taskManagerDemo.taskCreator;

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
	 * Caller needs to make sure that the Stream is not empty before processing.
	 * 
	 * @param numberOfTasks
	 * @return
	 */
	public Stream<CleanableTask> getStreamOfBikeCleaningTasks(long numberOfTasks) {

		if (isNumberOfTasksInvalid(numberOfTasks))
			numberOfTasks = 1;

		return getStreamOfTasks(numberOfTasks, BikeCleaningTask.class);
	}

	/**
	 * Caller needs to make sure that the Stream is not empty before processing.
	 * 
	 * @param numberOfTasks
	 * @return
	 */
	public Stream<CleanableTask> getStreamOfCarCleaningTasks(long numberOfTasks) {

		if (isNumberOfTasksInvalid(numberOfTasks))
			numberOfTasks = 1;

		return getStreamOfTasks(numberOfTasks, CarCleaningTask.class);
	}

	/**
	 * Caller needs to make sure that the Stream is not empty before processing.
	 * 
	 * @param numberOfTasks
	 * @return
	 */
	public Stream<CleanableTask> getStreamOfHouseCleaningTasks(long numberOfTasks) {

		if (isNumberOfTasksInvalid(numberOfTasks))
			numberOfTasks = 1;

		return getStreamOfTasks(numberOfTasks, HouseCleaningTask.class);
	}
}
