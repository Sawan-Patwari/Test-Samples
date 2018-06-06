package test.files.serialisation;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class SerializeDeserializeProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SerializeDeserializeProg tester = new SerializeDeserializeProg();
				
		tester.serializeDeserializeCatOnlyData();
		
		tester.serializeDeserializeAllAnimalData();
		
		tester.serializeDeserializeAllAnimalDataList();
		
	}
	
	public void serializeDeserializeCatOnlyData() {
		Serialization serializationTester = new Serialization();
		serializationTester.seraliseOnlyCatDataToFile();
		
		Deserialization deserializationTester = new Deserialization();
		deserializationTester.deseraliseOnlyCatDataFromFile();
		
	}
	
	public void serializeDeserializeAllAnimalData() {
		Serialization serializationTester = new Serialization();
		serializationTester.seraliseAllAnimalDataAtOnceToFile();
		
		Deserialization deserializationTester = new Deserialization();
		deserializationTester.deseraliseAllAnimalDataFromFile(SampleFiles.allAnimalDataWrittenAtOnceFilePath);
	}
	
	public void serializeDeserializeAllAnimalDataList() {
		Serialization serializationTester = new Serialization();
		serializationTester.serialiseListData();
		
		Deserialization deserializationTester = new Deserialization();
		deserializationTester.deserialiseListDataApproach1();
		deserializationTester.deserialiseListDataApproach2();
		deserializationTester.deserialiseListDataApproach3();
		deserializationTester.deserialiseListDataApproach4();
		deserializationTester.deserialiseListDataApproach5();
	}
		
	public class Serialization {
		
		private void test1(List<Animal> animals, Class<? extends Animal> clazz, Path path)
				throws FileNotFoundException, IOException, ClassCastException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}

			/*
			 * StandardOpenOption.APPEND will cause below issue when reading the file,
			 * if the file has been written multiple times with APPEND option. Thus,
			 * changing it to StandardOpenOption.WRITE.
			 * Multiple call gives an exception - java.io.StreamCorruptedException: invalid type code: AC
			 * 
			 * https://stackoverflow.com/questions/2393179/streamcorruptedexception-invalid-type-code-ac#2395269
			 * 
			 * The issue can be simulated with the method-call to serializeDeserializeCatOnlyData() when 
			 * the below FileChannel.open is done with StandardOpenOption.APPEND option
			 */
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE)) {
				
				//FileLock lock = channel.lock();
				
				try (ObjectOutputStream writer = new ObjectOutputStream(Channels.newOutputStream(channel))) {
					for (Animal animal : animals)
						writer.writeObject((clazz.cast(animal)));
				} /*finally {
					
					lock.release();				
				} */
			} 
			
			/*
			try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path.toFile())))) {
				for (Animal animal : animals)
					writer.writeObject((clazz.cast(animal)));
			}*/
		}

		private void test2(List<? extends Serializable> objects, Path path) throws FileNotFoundException, IOException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}

			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE)) {

				//FileLock lock = channel.lock();

				try (ObjectOutputStream writer = new ObjectOutputStream(
						new BufferedOutputStream(Channels.newOutputStream(channel)))) {
					for (Object obj : objects)
						writer.writeObject(obj);
				}/* finally {
					lock.release();
				}*/
			}

		}
		
		private void test3(List<Animal> animals, Class<? extends Animal> clazz, Path path)
				throws FileNotFoundException, IOException, ClassCastException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}

			/*
			 * StandardOpenOption.APPEND will cause below issue when reading the file,
			 * if the file has been written multiple times with APPEND option. Thus,
			 * changing it to StandardOpenOption.WRITE.
			 * Multiple call gives an exception - java.io.StreamCorruptedException: invalid type code: AC
			 * 
			 * https://stackoverflow.com/questions/2393179/streamcorruptedexception-invalid-type-code-ac#2395269
			 * 
			 * The issue can be simulated with the method-call to serializeDeserializeCatOnlyData() when 
			 * the below FileChannel.open is done with StandardOpenOption.APPEND option
			 */
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.APPEND)) {
				
				//FileLock lock = channel.lock();
				
				try (ObjectOutputStream writer = new ObjectOutputStream(Channels.newOutputStream(channel))) {
					for (Animal animal : animals)
						writer.writeObject((clazz.cast(animal)));
				} /*finally {
					
					lock.release();				
				} */
			} 
			
			/*
			try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path.toFile())))) {
				for (Animal animal : animals)
					writer.writeObject((clazz.cast(animal)));
			}*/
		}
		
		private void test4(List<? extends Serializable> objects, Path path) throws FileNotFoundException, IOException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}

			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE)) {

				//FileLock lock = channel.lock();

				try (ObjectOutputStream writer = new ObjectOutputStream(
						new BufferedOutputStream(Channels.newOutputStream(channel)))) {					
						writer.writeObject(objects);
				}/* finally {
					lock.release();
				}*/
			}

		}
		
		public void serialiseListData() {
			
			List<Animal> animals = getAllAnimalDataSample();
			
			try {
				test4(animals, Paths.get(SampleFiles.allAnimalListDataFilePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * This method will corrupt the file as per the testing results. The file
		 * cannot be deserialized later though the serialization (this method call) 
		 * will not cause any issue. 
		 * 
		 * https://stackoverflow.com/questions/2393179/streamcorruptedexception-invalid-type-code-ac#2395269
		 */
		public void doTest3() {
			Torso torsoOfCats = new Torso();
			torsoOfCats.setTorsoType(TorsoType.SMALL);
			
			Torso torsoOfDogs = new Torso();
			torsoOfDogs.setTorsoType(TorsoType.MEDIUM);
			
			Torso torsoOfElephants = new Torso();
			torsoOfElephants.setTorsoType(TorsoType.LARGE);
			
			Tail tailPresent = new Tail();
			tailPresent.setTailPresent(true);
			
			Tail tailAbsent = new Tail();
			tailAbsent.setTailPresent(false);
			
			Animal cat1 = new Cat(torsoOfCats, tailPresent);
			cat1.setUniqueName("First Cat");
			
			Animal cat2 = new Cat(torsoOfCats, tailAbsent);
			cat2.setUniqueName("Second Cat");
			
			Animal dog1 = new Dog(torsoOfDogs, tailPresent);
			dog1.setUniqueName("First Dog");
			
			Animal dog2 = new Dog(torsoOfDogs, tailAbsent);
			dog2.setUniqueName("Second Dog");
			
			Animal elephant1 = new Elephant(torsoOfElephants, tailPresent);
			elephant1.setUniqueName("First Elephant");
			
			Animal elephant2 = new Elephant(torsoOfElephants, tailAbsent);
			elephant2.setUniqueName("Second Elephant");
			
			List<Animal> cats = new ArrayList<>();
			cats.add(cat1);
			cats.add(cat2);
			
			try {
				test3(cats, Cat.class, Paths.get(SampleFiles.allAnimalDataWrittenMultipleTimesFilePath));
			} catch (IOException | ClassCastException e) {
				// TODO Auto-generated catch block
				System.out.println("Couldn't write Cats data to the file due to " + e);		
			}
			
			List<Animal> dogs = new ArrayList<>();
			dogs.add(dog1);
			dogs.add(dog2);
			
			try {
				test3(dogs, Dog.class, Paths.get(SampleFiles.allAnimalDataWrittenMultipleTimesFilePath));
			} catch (IOException | ClassCastException e) {
				// TODO Auto-generated catch block
				System.out.println("Couldn't write Dogs data to the file due to " + e);
			}
			
			List<Animal> elephants = new ArrayList<>();
			elephants.add(elephant1);
			elephants.add(elephant2);
			
			try {
				test3(elephants, Elephant.class, Paths.get(SampleFiles.allAnimalDataWrittenMultipleTimesFilePath));
			} catch (IOException | ClassCastException e) {
				// TODO Auto-generated catch block
				System.out.println("Couldn't write Elephants data to the file due to " + e);
			}
					
		}
		
		public void seraliseAllAnimalDataAtOnceToFile() {
			
			List<Animal> animals = getAllAnimalDataSample();
						
			try {
				test2(animals, Paths.get(SampleFiles.allAnimalDataWrittenAtOnceFilePath));
			} catch (IOException | ClassCastException e) {
				// TODO Auto-generated catch block
				System.out.println("Couldn't write Elephants data to the file due to " + e);
			}
					
		}
		
		private List<Animal> getAllAnimalDataSample() {
			Torso torsoOfCats = new Torso();
			torsoOfCats.setTorsoType(TorsoType.SMALL);
			
			Torso torsoOfDogs = new Torso();
			torsoOfDogs.setTorsoType(TorsoType.MEDIUM);
			
			Torso torsoOfElephants = new Torso();
			torsoOfElephants.setTorsoType(TorsoType.LARGE);
			
			Tail tailPresent = new Tail();
			tailPresent.setTailPresent(true);
			
			Tail tailAbsent = new Tail();
			tailAbsent.setTailPresent(false);
			
			Animal cat1 = new Cat(torsoOfCats, tailPresent);
			cat1.setUniqueName("First Cat");
			
			Animal cat2 = new Cat(torsoOfCats, tailAbsent);
			cat2.setUniqueName("Second Cat");
			
			Animal dog1 = new Dog(torsoOfDogs, tailPresent);
			dog1.setUniqueName("First Dog");
			
			Animal dog2 = new Dog(torsoOfDogs, tailAbsent);
			dog2.setUniqueName("Second Dog");
			
			Animal elephant1 = new Elephant(torsoOfElephants, tailPresent);
			elephant1.setUniqueName("First Elephant");
			
			Animal elephant2 = new Elephant(torsoOfElephants, tailAbsent);
			elephant2.setUniqueName("Second Elephant");
			
			List<Animal> animals = new ArrayList<>();
			
			animals.add(cat1);
			animals.add(cat2);
		
			animals.add(dog1);
			animals.add(dog2);

			animals.add(elephant1);
			animals.add(elephant2);
			
			return animals;
		}
		
		public void seraliseOnlyCatDataToFile() {
			Torso torsoOfCats = new Torso();
			torsoOfCats.setTorsoType(TorsoType.SMALL);		
			
			Tail tailPresent = new Tail();
			tailPresent.setTailPresent(true);
			
			Tail tailAbsent = new Tail();
			tailAbsent.setTailPresent(false);
			
			Animal cat1 = new Cat(torsoOfCats, tailPresent);
			cat1.setUniqueName("First Cat");
			
			Animal cat2 = new Cat(torsoOfCats, tailAbsent);
			cat2.setUniqueName("Second Cat");
			
			List<Animal> cats = new ArrayList<>();
			cats.add(cat1);
			cats.add(cat2);
			
			try {
				test1(cats, Cat.class, Paths.get(SampleFiles.catOnlyDataFilePath));
			} catch (IOException | ClassCastException e) {
				// TODO Auto-generated catch block
				System.out.println("Couldn't write Cats data to the file due to " + e);		
			}
		}
		
		public void seraliseOnlyDogData() {
		
			Torso torsoOfDogs = new Torso();
			torsoOfDogs.setTorsoType(TorsoType.MEDIUM);
			
			Tail tailPresent = new Tail();
			tailPresent.setTailPresent(true);
			
			Tail tailAbsent = new Tail();
			tailAbsent.setTailPresent(false);
			
			Animal dog1 = new Dog(torsoOfDogs, tailPresent);
			dog1.setUniqueName("First Dog");
			
			Animal dog2 = new Dog(torsoOfDogs, tailAbsent);
			dog2.setUniqueName("Second Dog");

			List<Animal> dogs = new ArrayList<>();
			dogs.add(dog1);
			dogs.add(dog2);
			
			try {
				test1(dogs, Dog.class, Paths.get(SampleFiles.dogOnlyDataFilePath));
			} catch (IOException | ClassCastException e) {
				// TODO Auto-generated catch block
				System.out.println("Couldn't write Dogs data to the file due to " + e);
			}
		}
		
		public void seraliseOnlyElephantData() {
			
			Torso torsoOfElephants = new Torso();
			torsoOfElephants.setTorsoType(TorsoType.LARGE);
			
			Tail tailPresent = new Tail();
			tailPresent.setTailPresent(true);
			
			Tail tailAbsent = new Tail();
			tailAbsent.setTailPresent(false);
			
			Animal elephant1 = new Elephant(torsoOfElephants, tailPresent);
			elephant1.setUniqueName("First Elephant");
			
			Animal elephant2 = new Elephant(torsoOfElephants, tailAbsent);
			elephant2.setUniqueName("Second Elephant");
			
			List<Animal> elephants = new ArrayList<>();
			elephants.add(elephant1);
			elephants.add(elephant2);
			
			try {
				test1(elephants, Elephant.class, Paths.get(SampleFiles.elephantOnlyDataFilePath));
			} catch (IOException | ClassCastException e) {
				// TODO Auto-generated catch block
				System.out.println("Couldn't write Elephants data to the file due to " + e);
			}
		}
	}

	public class Deserialization {
		
		private List<? extends Animal> test1(Class<? extends Animal> clazz, Path path)
				throws FileNotFoundException, IOException, ClassCastException, ClassNotFoundException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}
			
			List<Animal> animals = new ArrayList<>();
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
				
				//FileLock lock = channel.lock();
				
				try (ObjectInputStream reader = new ObjectInputStream(Channels.newInputStream(channel))) {
					
					while (true) {
						Object object = reader.readObject();
						if (object instanceof Animal)
							animals.add(clazz.cast(object));
					}
				}catch (EOFException e) {
					// This is the way to determine if the file has been completely read.				
				} 
				/*finally {
					
					lock.release();				
				} */
			}
			
			return animals;
		}
		
		@SuppressWarnings("unchecked")
		public void deseraliseOnlyCatDataFromFile() {
			
			try {
				List<Cat> onlyCatsData = (List<Cat>) test1(Cat.class, Paths.get(SampleFiles.catOnlyDataFilePath));
				
				onlyCatsData.forEach(System.out::println);
				
			} catch (ClassCastException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		private List<? extends Animal> test2(Path path)
				throws FileNotFoundException, IOException, ClassCastException, ClassNotFoundException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}
			
			List<Animal> animals = new ArrayList<>();
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
				
				//FileLock lock = channel.lock();
				
				try (ObjectInputStream reader = new ObjectInputStream(Channels.newInputStream(channel))) {
					
					while (true) {
						Object object = reader.readObject();
						if (object instanceof Animal)
							animals.add((Animal)object);
					}
				}catch (EOFException e) {
					// This is the way to determine if the file has been completely read.				
				} 
				/*finally {
					
					lock.release();				
				} */
			}
			
			return animals;
		}
		
		private List<Animal> test3(Path path, Class<List<Animal>> clazz)		
				throws FileNotFoundException, IOException, ClassCastException, ClassNotFoundException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}
			
			List<Animal> listData = new ArrayList<>();
			Object object;
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
				
				//FileLock lock = channel.lock();
				
				try (ObjectInputStream reader = new ObjectInputStream(Channels.newInputStream(channel))) {
					
						object = reader.readObject();
						if(object != null) {
							listData = clazz.cast(object);
						}
					
				}
				/*finally {
					
					lock.release();				
				} */
			}
			
			return listData;
		}
		
		private List<? extends Animal> test4(Path path, Class<List<? extends Animal>> clazz)		
				throws FileNotFoundException, IOException, ClassCastException, ClassNotFoundException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}
			
			List<? extends Animal> listData = new ArrayList<>();
			Object object;
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
				
				//FileLock lock = channel.lock();
				
				try (ObjectInputStream reader = new ObjectInputStream(Channels.newInputStream(channel))) {
					
						object = reader.readObject();
						if(object != null) {
							listData = clazz.cast(object);
						}
					
				}
				/*finally {
					
					lock.release();				
				} */
			}
			
			return listData;
		}
		
		private List<Serializable> test5(Path path, Class<List<Serializable>> clazz)		
				throws FileNotFoundException, IOException, ClassCastException, ClassNotFoundException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}
			
			List<Serializable> listData = new ArrayList<>();
			Object object;
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
				
				//FileLock lock = channel.lock();
				
				try (ObjectInputStream reader = new ObjectInputStream(Channels.newInputStream(channel))) {
					
						object = reader.readObject();
						if(object != null) {
							listData = clazz.cast(object);
						}
					
				}
				/*finally {
					
					lock.release();				
				} */
			}
			
			return listData;
		}
				
		private List<? extends Serializable> test6(Path path, Class<List<? extends Serializable>> clazz)		
				throws FileNotFoundException, IOException, ClassCastException, ClassNotFoundException {

			if (path == null || path.toFile().isDirectory() || !path.toFile().exists()) {
				throw new IllegalArgumentException("Cannot Serialize due to issue with the file.");
			}
			
			List<? extends Serializable> listData = new ArrayList<>();
			Object object;
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
				
				//FileLock lock = channel.lock();
				
				try (ObjectInputStream reader = new ObjectInputStream(Channels.newInputStream(channel))) {
					
						object = reader.readObject();
						if(object != null) {
							listData = clazz.cast(object);
						}
					
				}
				/*finally {
					
					lock.release();				
				} */
			}
			
			return listData;
		}
					
		@SuppressWarnings("unchecked")
		public void deserialiseListDataApproach1() {
					
			List<? extends Serializable> animalsData = new ArrayList<>();
						
			try {
				animalsData = (List<? extends Serializable>)test6(Paths.get(SampleFiles.allAnimalListDataFilePath), (Class<List<? extends Serializable>>) animalsData.getClass());
				animalsData.forEach(System.out::println);
				
				/*
				  We can code below lines if we are sure that 'animalsData' is castable
				  to (List<Animal>). In our case, the file (SampleFiles.allAnimalListDataFilePath) 
				  has List<Animal> data only.
				 */
				/*
				for(Animal animal : (List<Animal>) animalsData) {
					System.out.println(animal.getUniqueName());
				}
				*/		
			} catch (ClassCastException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
		
		@SuppressWarnings("unchecked")
		public void deserialiseListDataApproach2() {
			
			List<Animal> animalsData = new ArrayList<>();
			
			try {
				animalsData = (List<Animal>)test6(Paths.get(SampleFiles.allAnimalListDataFilePath), (Class<List<? extends Serializable>>) animalsData.getClass());
				animalsData.forEach(System.out::println);
				
				for(Animal animal : (List<Animal>) animalsData) {
					System.out.println(animal.getUniqueName());
				}
								
			} catch (ClassCastException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		@SuppressWarnings("unchecked")
		public void deserialiseListDataApproach3() {
			
			List<Animal> animalsData = new ArrayList<>();
			
			try {
				animalsData = (List<Animal>)test3(Paths.get(SampleFiles.allAnimalListDataFilePath), (Class<List<Animal>>) animalsData.getClass());
				animalsData.forEach(System.out::println);
				
				for(Animal animal : animalsData) {
					System.out.println(animal.getUniqueName());
				}
								
			} catch (ClassCastException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		@SuppressWarnings("unchecked")
		public void deserialiseListDataApproach4() {
			
			List<? extends Animal> animalsData = new ArrayList<>();
			
			try {
				animalsData = (List<? extends Animal>)test4(Paths.get(SampleFiles.allAnimalListDataFilePath), (Class<List<? extends Animal>>) animalsData.getClass());
				animalsData.forEach(System.out::println);
				
				for(Animal animal : animalsData) {
					System.out.println(animal.getUniqueName());
				}
								
			} catch (ClassCastException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		@SuppressWarnings("unchecked")
		public void deserialiseListDataApproach5() {
			
			List<Serializable> animalsData = new ArrayList<>();
			
			try {
				animalsData = (List<Serializable>)test5(Paths.get(SampleFiles.allAnimalListDataFilePath), (Class<List<Serializable>>) animalsData.getClass());
				animalsData.forEach(System.out::println);
				
				for(Serializable animal : animalsData) {
					/*
					 * Below casting can be done if the file has only Animal 
					 * serialized data.
					 */
					System.out.println(((Animal)animal).getUniqueName());
				}
								
			} catch (ClassCastException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
				
		@SuppressWarnings("unchecked")
		public void deseraliseAllAnimalDataFromFile(String filePath) {
			
			try {
				List<Animal> allAnimalsData = (List<Animal>) test2(Paths.get(filePath));
				
				allAnimalsData.forEach(System.out::println);
				
			} catch (ClassCastException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * The call to this method will generate below issue:
		 * java.io.StreamCorruptedException: invalid type code: AC
		 * 
		 * This is because the file was written multiple times with StandardOpenOption.APPEND.
		 * https://stackoverflow.com/questions/2393179/streamcorruptedexception-invalid-type-code-ac#2395269
		 */
		public void deseraliseAllAnimalDataFromFileTest1() {
			deseraliseAllAnimalDataFromFile(SampleFiles.allAnimalDataWrittenMultipleTimesFilePath);
		}
		
		public void deseraliseAllAnimalDataFromFileTest2() {
			deseraliseAllAnimalDataFromFile(SampleFiles.allAnimalDataWrittenAtOnceFilePath);
		}
	}

}

class SampleFiles {
	final static String CURRENT_DIRECTORY = ".";
	private final static String CURRENT_WORKING_DIRECTORY = CURRENT_DIRECTORY + File.separator + "src" + File.separator + "test"
			+ File.separator + "files" + File.separator + "serialisation" + File.separator;

	final static String allAnimalDataWrittenMultipleTimesFilePath = CURRENT_WORKING_DIRECTORY + "AllAnimalDataWrittenMultipleTimes.data";
	final static String allAnimalDataWrittenAtOnceFilePath = CURRENT_WORKING_DIRECTORY + "AllAnimalDataWrittenAtOnce.data";
	final static String catOnlyDataFilePath = CURRENT_WORKING_DIRECTORY + "catOnlyFile.data";
	final static String dogOnlyDataFilePath = CURRENT_WORKING_DIRECTORY + "dogOnlyFile.data";
	final static String elephantOnlyDataFilePath = CURRENT_WORKING_DIRECTORY + "elephantOnlyFile.data";
	final static String allAnimalListDataFilePath = CURRENT_WORKING_DIRECTORY + "AllAnimalListDataFile.data";
}

class Animal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String animalType;
	String uniqueName;
	
	public String getAnimalType() {
		return animalType;
	}
	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
}

class Cat extends Animal {
	
	String catProperty = " I am a Cat ";
	
	Torso torso;
	
	Tail tail;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cat() {
		animalType = "cat";
	}
	
	public Cat(Torso torso, Tail tail) {
		this();
		setTorso(torso);
		setTail(tail);			
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return catProperty + torso + tail;
	}

	public Torso getTorso() {
		return torso;
	}

	public void setTorso(Torso torso) {
		this.torso = torso;
	}

	public Tail getTail() {
		return tail;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}
	
}

class Dog extends Animal{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String dogProperty = " I am a Dog ";
	
	Torso torso;
	
	Tail tail;

	public Dog() {
		animalType = "dog";
	}
	
	public Dog(Torso torso, Tail tail) {
		this();
		setTorso(torso);
		setTail(tail);			
	}
	
	@Override
	public String toString() {
		return dogProperty + torso + tail;
	}

	public Torso getTorso() {
		return torso;
	}

	public void setTorso(Torso torso) {
		this.torso = torso;
	}

	public Tail getTail() {
		return tail;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}
}

class Elephant extends Animal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String elephantProperty = " I am an Elephant ";
	
	Torso torso;
	
	Tail tail;

	public Elephant() {
		animalType = "elephant";
	}
	
	public Elephant(Torso torso, Tail tail) {
		this();
		setTorso(torso);
		setTail(tail);			
	}
	
	@Override
	public String toString() {
		return elephantProperty + torso + tail;
	}

	public Torso getTorso() {
		return torso;
	}

	public void setTorso(Torso torso) {
		this.torso = torso;
	}

	public Tail getTail() {
		return tail;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}
}

enum TorsoType implements Serializable {
	SMALL, MEDIUM, LARGE;
}

class Torso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String classType = "Torso";
	TorsoType torsoType;
	static int thisStaticInfoWillNotBeStoredToFile = 100;
	transient int thisTransientInfoWillNotBeStoredToFile = 200;

	public TorsoType getTorsoType() {
		return torsoType;
	}

	public void setTorsoType(TorsoType torsoType) {
		this.torsoType = torsoType;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "classType: " + classType + " " + "torsoType:" + torsoType + " ";
	}
}

class Tail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String classType = "Tail";
	boolean isTailPresent;

	public boolean isTailPresent() {
		return isTailPresent;
	}

	public void setTailPresent(boolean isTailPresent) {
		this.isTailPresent = isTailPresent;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "classType: " + classType + " " + "isTailPresent:" + isTailPresent;
	}
}