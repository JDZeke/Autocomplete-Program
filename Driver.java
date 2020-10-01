import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		AutocompleteProvider provider = new AutocompleteProvider();
		Scanner scanner = new Scanner(System.in);
		String input = "";
		
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println(" Welcome to Joseph Zietowski's implementation of the Asymmetrik \"Mobile Device\n Keyboard\" Challenge\n");
		System.out.println("   1. If you would like to train the algorithm, type \'train\' followed by a\n   string of text");
		System.out.println("   (example: train This is some sample text.)\n");
		System.out.println("   2. If you would like to get autocomplete suggestions based on a string\n   fragment, type \'input\' followed by a string fragment");
		System.out.println("   (example: input som)\n");
		System.out.println("   3. To exit the program, type \'exit\' at any time\n");
		System.out.println("--------------------------------------------------------------------------------\n");
		
		while (!input.equals("exit")) {
			input = scanner.nextLine().toLowerCase();
			
			// the exit command
			if (input.length() >= 4 && input.substring(0, 4).equals("exit")) {
				break;
			}
			// the train command
			else if (input.length() > 5 && input.substring(0, 6).equals("train ")) {
				provider.train(input.substring(6));
				System.out.println("The algorithm has been trained");
			}
			// the input command
			else if (input.length() > 5 && input.substring(0, 6).equals("input ")) {
				System.out.print("Suggestions: ");
				System.out.println(provider.getWords(input.substring(6)));
			} else {
				System.out.println("Please enter a valid command");
			}
		}
		scanner.close();
	}
	
}
