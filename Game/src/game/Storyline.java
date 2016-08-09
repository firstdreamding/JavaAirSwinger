package game;

import java.util.ArrayList;
import java.util.List;


public class Storyline {
	private List<Dialogue> dialogue = new ArrayList<Dialogue>();
	public Storyline(String file){
		List<String> lines = FileUtils.readLines(file);
		for (String line : lines) {
			int quote = line.indexOf('"');
			Dialogue d = new Dialogue();
			d.name = line.substring(0, line.indexOf('"')).trim();
			d.text = line.substring(quote);
			dialogue.add(d);
		}
		
	}
	
	public List<Dialogue> getDialogue() {
		return dialogue;
	}
}
