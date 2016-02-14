package game;

public class LevelTwo extends Level {

	boolean isPlaying = true;

	public LevelTwo(Player player){
		super(player, 2);
		level = load("Levels/two.lvl");
		generate();

	}

	protected void generate(){
		for (int i = 0; i < level.length(); i++) {
			char c = level.charAt(i);
			if (c == 'P') player.setPosition(i % width, i / width);
			else if (c == 'D') add(new Dragon(i % width, i / width));
		}
	}
}