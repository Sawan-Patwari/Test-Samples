package general;

//********
// Imports because of Approach-2 from Level1.displayValue() method.
import general.Level1.Level2.Level3;
import general.Level1.Level2.Level3.Level4;

//*******

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class NestedClassProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OuterClass.showOuterClassMessage();
		OuterClass.showXFromOuterClass();
		Level1.displayValue();
		AClassWithAnInterfaceAndAClass.displayX();
		LocalInnerClassSample.doSample();
		AnonymousClass.doSample();
	}

}

class OuterClass {
	public String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public OuterClass() {
		
	}
	
	public OuterClass(String message) {
		setMessage(message);
	}
	
	class InnerClass {
		public String message = "This is Inner Class Message.";
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public InnerClass(String message) {
			
			OuterClass.this.setMessage(message);
			//Also:
			//OuterClass.this.message = message;
		}
		
		public void displayMessage() {
			
			System.out.println("Inner Class Message: "+getMessage());
			System.out.println("Outer Class Message: "+OuterClass.this.getMessage());
			//Also:
			//System.out.println("Outer Class Message: "+OuterClass.this.message);
		}
	}
	
	public void showInnerClassMessage(String message) {
		
		InnerClass innerClass = new InnerClass(message);
		innerClass.displayMessage();
	}
	
	public void showInnerClassMessage() {
		showInnerClassMessage(message);		
	}
	
	public static void showOuterClassMessage() {
		OuterClass outerClass1 = new OuterClass("Sample-1: This is Outer Class Message.");
		outerClass1.showInnerClassMessage();
		
		OuterClass outerClass2 = new OuterClass();
		outerClass2.showInnerClassMessage("Sample-2: This is Outer Class Message.");
	}
	
	public static void showXFromOuterClass() {
		System.out.print("The value of 'x' is:");
		InnerStaticClass.displayX();
	}
	
	static class InnerStaticClass{
		private static int x = 10;
		
		public static void displayX() {
			System.out.println(x);
		}
	}
}

class Level1{
	private int commonVariable = 10;
	
	class Level2{
		private int commonVariable = 20;
		
		class Level3{
			private int commonVariable = 30;
			
			class Level4{
				private int commonVariable = 40;
				
				public void displayValue() {
					System.out.println("Value from Level-4: "+commonVariable);
					System.out.println("Value from Level-3: "+Level3.this.commonVariable);
					System.out.println("Value from Level-2: "+Level2.this.commonVariable);
					System.out.println("Value from Level-1: "+Level1.this.commonVariable);
				}
			}
		}
	}
	
	public static void displayValue() {
		
		//Approach-1:
		Level1 l1 = new Level1();
		Level2 l2 = l1.new Level2();
		Level2.Level3 l3 = l2.new Level3();
		Level2.Level3.Level4 l4 = l3.new Level4();
		
		l4.displayValue();
		
		//Approach-2:
		Level1 l11 = new Level1();
		Level2 l12 = l11.new Level2();
		Level3 l13 = l12.new Level3();
		Level4 l14 = l13.new Level4();
		
		l14.displayValue();
		
		//Approach-3:
		Level1 l21 = new Level1();
		Level1.Level2 l22 = l21.new Level2();
		Level1.Level2.Level3 l23 = l22.new Level3();
		Level1.Level2.Level3.Level4 l24 = l23.new Level4();
		
		l24.displayValue();
	}
}

class AClassWithAnInterfaceAndAClass {
	
	private interface InnerInterface {
		int x = 10;
		void showX();
	}
	
	class InnerClass implements InnerInterface{

		@Override
		public void showX() {
			// TODO Auto-generated method stub
			System.out.println(x);
		}
		
	}
	
	static class StaticInnerClass implements InnerInterface {
		@Override
		public void showX() {
			// TODO Auto-generated method stub
			System.out.println(x);
		}
	}
	
	public static void displayX() {
		AClassWithAnInterfaceAndAClass classInstance = new AClassWithAnInterfaceAndAClass();
		InnerInterface instance = classInstance.new InnerClass();
		System.out.print("The value of x is:");
		instance.showX();
		
		classInstance.nonStaticDisplayX();
		
		System.out.print("The value of x is:");
		instance = new StaticInnerClass();
		instance.showX();
	}
	
	public void nonStaticDisplayX() {
		
		InnerInterface instance = new InnerClass();
		System.out.print("The value of x is:");
		instance.showX();
	}
}

class LocalInnerClassSample {
	
	interface InnerLocalInterface {
		void showX();
	}
	
	public static void doSample() {
		
		/*
		//This is not possible.
		interface InnerLocalInterface {
			
		}
		*/
		class LocalInnerClass implements InnerLocalInterface{
			private int x = 10;

			@Override
			public void showX() {
				// TODO Auto-generated method stub
				System.out.println(x);
			}
		}
		
		InnerLocalInterface instance = new LocalInnerClass();
		instance.showX();
	}
}

class AnonymousClass {
	
	abstract class InnerAbstractClass implements InnerInterface{
		private int x = 10;
		
		public int getX() {
			return x;
		}

		abstract public void showX();
		
		public void showDefaultMessage() {
			System.out.println("I am done!");
		}
		
		public int getMoreX() {
			return getX();
		}
	}
	
	interface InnerInterface{
		void showMessage();
	}
	
	public void getSample1() {
		
		InnerAbstractClass instance = new InnerAbstractClass() {
			
			{
				System.out.println("Executing from InnerAbstractClass anonymous instance:");
				
			}
			
			@Override
			public int getX() {
				return 20;
			}
			
			public void showX() {
				System.out.print("The value of x is:");
				System.out.println(getX());
			}					
			
			public void showMessage() {
				System.out.println("Anonymous Message: I am almost done!");
			}
			
			/**
			 * Cannot access this method. Perhaps, a JDK bug.
			 */
			@SuppressWarnings({ "unused" })
			public void showMessage1() {
				System.out.println("Anonymous Message: I am done!");
			}
			
			@Override
			public void showDefaultMessage() {
				System.out.println("Anonymous Message: I am done!");
			}
		};
		
		instance.showX();
		instance.showMessage();
		//instance.showMessage1();// Compilation issue: Cannot Access this method - perhaps a JDK bug.
		instance.showDefaultMessage();
	}
	
	public void getSample2() {
		
		InnerInterface instance = new InnerInterface() {

			@Override
			public void showMessage() {
				// TODO Auto-generated method stub
				System.out.println("Calling from the instance of an Interface.");
			}
			
		};
		
		instance.showMessage();
	}
	
	public void getSample3() {
		
		// An instance of an Abstract class via anonymous class.
		InnerInterface instance = new InnerAbstractClass() {

			@Override
			public void showMessage() {
				// TODO Auto-generated method stub
				System.out.println("The value of x is:");
				showX();
				showDefaultMessage();
			}

			@Override
			public void showX() {
				// TODO Auto-generated method stub
				System.out.println(getX());
			}
			
		};
		instance.showMessage();
	}
	
	public void getSample4() {
		
		InnerInterface instance = new InnerAbstractClass() {

			@Override
			public void showMessage() {
				// TODO Auto-generated method stub
				System.out.println("The value of 'X' is:");
				showX();
				showDefaultMessage();
			}

			@Override
			public void showX() {
				// TODO Auto-generated method stub
				System.out.println(getMoreX());
			}
			
			@Override
			public int getMoreX() {
				// TODO Auto-generated method stub
				return 10*getX();
			}
			
		};
		
		instance.showMessage();
		InnerAbstractClass castedInstance = (InnerAbstractClass)instance;
		System.out.println("The value of x is:"+ castedInstance.getMoreX());
		
		System.out.println("The value of x is:"+ ((InnerAbstractClass)instance).getMoreX());
	}
	
	public static void doSample() {
		
		AnonymousClass instance = new AnonymousClass() {
			{
				System.out.println("Creating Anonymous Instance.");
			}
		};
		instance.getSample1();
		instance.getSample2();
		instance.getSample3();
		instance.getSample4();
	}
	
}

