package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
class Save{
	
  private Save() {}

  public static Map<String, String> load(String name) {

    List<String> lines = new ArrayList<String>();
    try{
      Scanner file = new Scanner(new File("saves/" + name + ".txt"));
          while (file.hasNextLine()) {
              String line = file.nextLine();
              lines.add(line);
        }
        file.close();
      }catch(FileNotFoundException e){
        System.out.println("File not found!");
        return null;
      }

      Map<String, String> attributes = new HashMap<String,String>();
      for (int i = 0; i < lines.size(); i++) {
        String line = lines.get(i);
        if (line.contains("{")) {
            i++;
            String pitems = "";
            while (!(line = lines.get(i++)).contains("}")){
              	pitems += line.split("=")[1] + ",";
            }
            attributes.put("pitems", pitems.substring(0, pitems.length() - 1));	
            if (i >= lines.size()) break;
            line = lines.get(i);
        }
        String[] kv = line.split("=");
        attributes.put(kv[0], kv[1]);
      }
      return attributes;

  } 

}