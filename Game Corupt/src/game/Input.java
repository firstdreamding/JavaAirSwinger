package game;

import java.util.Scanner;

public class Input{

	Scanner scanner;

	public Input() {
		scanner = new Scanner(System.in);
	}

	int getInt(){
		return scanner.nextInt();
	}

	int getInt(String prompt) {
		print(prompt);
		return scanner.nextInt();
	}

	double getDouble() {
		return scanner.nextDouble();
	}

	double getDouble(String prompt) {
		print(prompt);
		return scanner.nextDouble();
	}

	String getLine(){
		return scanner.nextLine();
	}	

	String getLine(String prompt){
		print(prompt);
		return scanner.nextLine();
	}

	void print(String prompt){
		System.out.print(prompt);
	}

	void println(String prompt){
		System.out.println(prompt);
	}

	void close() {
		scanner.close();
	}
}