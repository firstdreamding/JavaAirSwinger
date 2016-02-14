package game;


/**
 * A small utility <strong>class</strong> to validate user input.
 * 
 * @author Geoffrey
 *
 */
public class Validator {
	
	private static Input input = new Input();
	
	public static int stringToInt(String input, String output) {
		boolean success = tryStringToInt(input);
		while (!success) {
			Validator.input.print(output);
			input = Validator.input.getLine();
			success = tryStringToInt(input);
		}
		return Integer.parseInt(input);
	}
	
	public static boolean tryStringToInt(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}


}
