package game;

import game.items.Item;

public class LevelOne extends Level {

	boolean isPlaying = true;

	public LevelOne(Player player){
		super(player, 1);
		level = load("Levels/one.lvl");
		//System.out.println("You appeared in a huge maze with all your memory gone. You see a sign ahead. \n'The lost hero, please come. Our world is destoryed. Help.....'. You deside to see what is up." );
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
				placeItem(item, x, y);
			}
				
		}
	}

	//protected void play(){
			//String playerInput = input.getLine("Where do you want to move?(Example: Up) (You are allowed to move up, down, left, or right only. With out any items that say so other wise, you can only move one space.)");

			//switch(playerInput){
				//case "Up":
				//	playerUp(1);
					//return null;
			//}
			//cpmMove();
			//play();
		//}

	//protected void playerUp(int move){

	//	int y = entity.getY();
	//	int x = entity.getX();
	//	y++;
//		entity.setPosition(x,y);

//	}

}