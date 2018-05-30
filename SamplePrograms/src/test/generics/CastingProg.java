package test.generics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This sample class is useful for casting that will be needed in case of
 * Serialization and Deserialization.
 * 
 * @author Sawan.Patwari
 *
 */
public class CastingProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CastingProg tester = new CastingProg();

		tester.doTest1();
		tester.doTest2();
		tester.doTest3();
		tester.doTest4();
	}

	private void test1(Object animal, Class<? extends Animal> clazz) {
		System.out.println((clazz.cast(animal)));
	}

	public void doTest1() {
		Animal cat = new Cat();
		test1(cat, Cat.class);

		Animal dog = new Dog();
		test1(dog, Dog.class);
	}

	private void test2(List<Animal> animals, Class<? extends Animal> clazz) {
		for (Animal animal : animals) {
			System.out.println((clazz.cast(animal)));
		}
	}

	public void doTest2() {
		List<Animal> animals = new ArrayList<>();
		animals.add(new Cat());
		test2(animals, Cat.class);

		animals = new ArrayList<>();
		animals.add(new Dog());

		test2(animals, Dog.class);
	}

	private List<? extends Animal> test3(Object animal, Class<? extends Animal> clazz) {
		List<Animal> ls = new ArrayList<>();
		ls.add(clazz.cast(animal));

		return ls;
	}

	@SuppressWarnings("unchecked")
	public void doTest3() {

		List<Cat> cats = (List<Cat>) test3(new Cat(), Cat.class);
		System.out.println(cats);

		List<Dog> dogs = (List<Dog>) test3(new Dog(), Dog.class);
		System.out.println(dogs);
	}

	private List<? extends Animal> test4(List<?> animals, Class<? extends Animal> clazz) {
		// below code will not work.
		// return (List<? extends Animal>) clazz.cast(animals);

		List<Animal> ls = new ArrayList<>();
		for (Object animal : animals) {

			ls.add(clazz.cast(animal));
		}

		return ls;
	}

	@SuppressWarnings("unchecked")
	public void doTest4() {
		List<Object> animals = new ArrayList<>();
		animals.add(new Cat());

		List<Cat> cats = (List<Cat>) test4(animals, Cat.class);
		System.out.println(cats);

		animals = new ArrayList<>();
		animals.add(new Dog());

		List<Dog> dogs = (List<Dog>) test4(animals, Dog.class);
		System.out.println(dogs);

	}

}

class Animal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String animalType;
}

class Cat extends Animal {

	String catProperty = "I am a Cat";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cat() {
		animalType = "cat";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return catProperty;
	}

}

class Dog extends Animal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String dogProperty = "I am a Dog";

	public Dog() {
		animalType = "dog";
	}

	@Override
	public String toString() {
		return dogProperty;
	}
}
