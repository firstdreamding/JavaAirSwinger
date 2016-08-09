package game;

import java.io.File;

public class Delete{

	public static void main(String[] args) {
		if (args.length < 1){
			System.out.println("Please specify at least one file to delete.");
			System.out.println("Usage: java Delete [file]");
			return;
		}
		
		for(int i = 0; i < args.length; i++){
			File file = new File(args[i]);
			System.out.print("Deleting: " + file.getAbsolutePath() + "... ");
			if(file.exists()){
				if(file.delete()){
					System.out.println("File deleted successfully!");
				}else{
					System.out.println(":( NOT DELELTED! :(");
				}
			}else{ 
				System.out.println("The File Is Not Exist :(");
			}
		}
	}
}