package favouritePackage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class ComparisonProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println();
		System.out.println("Persons Data:[Start]");
		
		getPersons().forEach(System.out::println);
		
		System.out.println("Persons Data:[End]");
		System.out.println();
		
		System.out.println();
		System.out.println("Sorting Persons (All Fields): [Start]");
		
		sortAndDisplay(getPersons(), Person.sortPersons);
		
		System.out.println("Sorting Persons (All Fields): [End]");
		System.out.println();
		
		System.out.println();
		System.out.println("Sorting Persons (Ascending Birth Dates): [Start]");
		
		sortAndDisplay(getPersons(), Person.SpecificFieldSort.ascendingSortByDateOfBirth);
		
		System.out.println("Sorting Persons (Ascending Birth Dates): [End]");
		System.out.println();
		
		System.out.println();
		System.out.println("Sorting Persons (Descending Birth Dates): [Start]");
				
		sortAndDisplay(getPersons(), Person.SpecificFieldSort.descendingSortByDateOfBirth);

		System.out.println("Sorting Persons (Descending Birth Dates): [End]");
		System.out.println();
		
		System.out.println();
		System.out.println("Sorting Persons (Ascending Names): [Start]");
				
		sortAndDisplay(getPersons(), Person.SpecificFieldSort.ascendingSortByName);

		System.out.println("Sorting Persons (Ascending Names): [End]");
		System.out.println();
		
		System.out.println();
		System.out.println("Sorting Persons (Descending Names): [Start]");
				
		sortAndDisplay(getPersons(), Person.SpecificFieldSort.descendingSortByName);

		System.out.println("Sorting Persons (Descending Names): [End]");
		System.out.println();
		
	}
	
	private static void sortAndDisplay(List<Person> persons, Comparator<Person> comparator) {
		
		if(!(Objects.isNull(persons) || persons.isEmpty())) {
			persons.sort(comparator);
			persons.forEach(System.out::println);
		}else {
			System.out.println("List is empty.");
		}
	}
	
	private static List<Person> getPersons(){
		
		List<Person> persons = new ArrayList<>();
		
		Person person1 = new Person("Ram", LocalDate.of(1999, 11, 2));
		Person person2 = new Person("Hanuman", LocalDate.of(1901, 1, 10));
		Person person3 = new Person("Beeshma", LocalDate.of(1201, 12, 28));
		Person person4 = new Person("Arjun", LocalDate.of(1980, 4, 13));
		Person person5 = new Person("Sughreev", LocalDate.of(1950, 9, 9));
		Person person6 = new Person("Duryodhan", LocalDate.of(2000, 10, 19));
		Person person7 = new Person("Karan", LocalDate.of(2010, 1, 1));
		Person person8 = new Person("Krishna", LocalDate.of(2000, 1, 12));
		Person person9 = new Person("Vasudev", LocalDate.of(2110, 11, 2));
		Person person10 = new Person("Arjun", LocalDate.of(1979, 9, 13));
		Person person11 = new Person("Krishna", LocalDate.of(1981, 9, 19));
		Person person12 = new Person("Duryodhan", LocalDate.of(1999, 12, 19));
		Person person13 = new Person(null, null);
		
		persons.add(person1);
		persons.add(person2);
		persons.add(person3);
		persons.add(person4);
		persons.add(person5);
		persons.add(person6);
		persons.add(person7);
		persons.add(person8);
		persons.add(person9);
		persons.add(person10);
		persons.add(person11);
		persons.add(person12);
		persons.add(person13);
		
		return persons;
	}

}

/**
 * This class will serve as the standard template for sorting any future class.
 * 
 * @author Sawan.Patwari
 *
 */
class Person implements Comparable<Person>{
	private String name;
	private LocalDate birthday;
	
	public Person(String name, LocalDate birthday) {
		this.name = name;
		this.birthday = birthday;
	}
	
	/**
	 * Null values are handled.
	 * 
	 * @return
	 */
	public String getName() {
		
		if(Objects.isNull(name)) {
			name = "Not Mentioned";
		}
		
		return name;
	}
	
	/**
	 * Null values are handled.
	 * 
	 * @return
	 */
	public LocalDate getBirthday() {
		
		if(Objects.isNull(birthday)) {
			birthday = LocalDate.ofEpochDay(Integer.MAX_VALUE);
		}
		
		return birthday;
	}
	
	/**
	 * None of the getter methods of Person class will give null value. The method code doesn't have to handle null 
	 * values, making the code null-check free.
	 */
	@Override
	public int compareTo(Person otherPerson) {
		// TODO Auto-generated method stub
		
		Person me = this;
		
		if(me == otherPerson) {//both the references pointing to the same Person in memory.
			return 0;
		} else {
		
			int afterComparison = SpecificFieldSort.ascendingSortByName.compare(me, otherPerson);
			
			if(afterComparison != 0) {//me and otherPerson are not same w.r.t name.
				return afterComparison;
			} else {
				//me and otherPerson are same w.r.t name, thus, proceeding to sort by other remaining
				//field(s).
				return SpecificFieldSort.ascendingSortByDateOfBirth.compare(me, otherPerson);
			}
			
		}
				
	}
	
	/**
	 * None of the getter methods of Person class will give null value. The code of the methods of this class
	 * doesn't have to handle null values, making the code null-check free.
	 */
	static class SpecificFieldSort {

		static Comparator<Person> ascendingSortByDateOfBirth = (me, otherPerson) -> {

			if (me == otherPerson || me.getBirthday().equals(otherPerson.getBirthday())) {
				return 0;
			} else {

				if (me.getBirthday().isBefore(otherPerson.getBirthday())) {
					return -1;
				} else {
					return 1;
				}

			}

		};
		
		static Comparator<Person> descendingSortByDateOfBirth = (me, otherPerson) -> {

			if (me == otherPerson || me.getBirthday().equals(otherPerson.getBirthday())) {
				return 0;
			} else {

				if (me.getBirthday().isBefore(otherPerson.getBirthday())) {
					return 1;
				} else {
					return -1;
				}

			}

		};
		
		static Comparator<Person> ascendingSortByName = (me, otherPerson) -> {
			return me.getName().compareTo(otherPerson.getName());
		};
		
		static Comparator<Person> descendingSortByName = (me, otherPerson) -> {
			return otherPerson.getName().compareTo(me.getName());
		};

	}
	
	static Comparator<Person> sortPersons = (me, otherPerson) -> me.compareTo(otherPerson);
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Person Name:"+getName()+"\t"+"Person Birthday:"+getBirthday();
	}
}