package general;

import java.util.Objects;

public class EnumProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EnumSamples._do();
	}

}

class EnumSamples {
	private enum Color {
		WHITE {
			public void showComplusoryMessage() {
				System.out.println("This is White.");
			}

			public void showNonCompulsoryMessage() {
				System.out.print("Showing Color Type with code - WH:");
			}

			/*
			 * Cannot call this method - perhaps, a JDK bug.
			 */
			@SuppressWarnings("unused")
			public void anySpecificMethod() {
				System.out.println("Some Specific Method.");
			}
		},
		BLUE {
			public void showComplusoryMessage() {
				System.out.println("This is Blue.");
			}
		},
		RED {
			public void showComplusoryMessage() {
				System.out.println("This is Red.");
			}
		},
		SAFFRON {
			public void showComplusoryMessage() {
				System.out.println("This is Saffron.");
			}
		},
		GREEN {
			public void showComplusoryMessage() {
				System.out.println("This is Green.");
			}
		},
		BROWN {
			public void showComplusoryMessage() {
				System.out.println("This is Brown.");
			}

			public void showNonCompulsoryMessage() {
				System.out.print("Showing Color Type with Code - BR:");
			}
		},
		NO_COLOR {
			public void showComplusoryMessage() {
				System.out.println("This is Colorless.");
			}
		};

		public abstract void showComplusoryMessage();

		// default method for Color types.
		public void showNonCompulsoryMessage() {
			System.out.print("Showing Color Type:");
		}
	}

	/*
	 * Inheritance Is not supported. enum SpecificColorSet extends Color{
	 * 
	 * }
	 */

	enum SYNCHRONISATION {
		WITH(true), WITHOUT(false);

		@SuppressWarnings("unused")
		private boolean option;

		SYNCHRONISATION(boolean option) {
			this.option = option;
		}

	}

	private static void sychronisationType(SYNCHRONISATION type) {
		System.out.println("sychronisationType: " + type);
	}

	public static void _do() {

		Color color = Color.WHITE;
		System.out.println(color);
		boolean colorTest1 = color == Color.BLUE;
		System.out.println("Is color == Colors.Blue?" + colorTest1);
		boolean colorTest2 = color == Color.WHITE;
		System.out.println("Is color == Colors.White?" + colorTest2);

		System.out.println("Displaying all colors:");

		for (Color eachColor : Color.values()) {
			System.out.println("Name:" + eachColor.name() + " " + "Value:" + eachColor.ordinal());
		}

		displayColorType(color);
		displayColorType(null);
		displayColorType(Color.SAFFRON);

		sychronisationType(SYNCHRONISATION.WITH);
		sychronisationType(SYNCHRONISATION.WITHOUT);
	}

	private static void displayColorType(Color color) {

		if (!Objects.isNull(color)) {
			switch (color) {

			case WHITE: {
				Color.WHITE.showNonCompulsoryMessage();
				Color.WHITE.showComplusoryMessage();
				// Color.WHITE.anySpecificMethod(); //compilation issue which is strange,
				// perhaps a bug in JDK.
			}
				break;

			case BLUE: {
				Color.BLUE.showNonCompulsoryMessage();
				Color.BLUE.showComplusoryMessage();
			}
				break;

			case SAFFRON: {
				Color.SAFFRON.showNonCompulsoryMessage();
				Color.SAFFRON.showComplusoryMessage();
			}
				break;
			default: {
				Color noColor = Color.NO_COLOR;
				noColor.showNonCompulsoryMessage();
				noColor.showComplusoryMessage();
			}
			}
		}

	}
}
