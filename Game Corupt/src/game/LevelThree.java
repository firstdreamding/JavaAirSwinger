package game;

import game.items.Item;

public class LevelThree extends Level{
	public LevelThree(Player player){
		super(player, 1);
		level = load("Levels/three.lvl");
		generate();

	}
	
	protected void generate(){
		for (int i = 0; i < level.length(); i++) {
			char c = level.charAt(i);
			int x = i % width;
			int y = i / width;
			if (c == 'P') 
				player.setPosition(x, y);
			else if (c == 'D') 
				add(new Dragon(i % width, i / width));
			else if (c >= '0' && c <= '9'){
				int id = Integer.parseInt("" + c);
				Item item = Item.getByID(id);
				addItem(item, x, y, 4);
			}
				
		}
	}
}
