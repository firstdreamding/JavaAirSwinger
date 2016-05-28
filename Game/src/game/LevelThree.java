package game;

public class LevelThree extends Level{
	public LevelThree(Player player){
		super(player, 1);
		level = load("Levels/three.lvl");
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
