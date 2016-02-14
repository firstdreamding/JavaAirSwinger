package game.items;

import game.Entity;
import game.Level;
import game.TextureManager;

public class Arrow extends Weapon{
	
		public Arrow(int id){
		super(id,"Arrow", "A single, silver arrow");
		weight = 1;
		damage = 2;
		range = 5;
		icon = TextureManager.get("ArrowIcon");
	}
	public void use(Entity entity, Level level) {
		System.out.println("Here the crazy guy, Swing away at air.");
	}
}
