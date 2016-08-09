package game.items;

import game.Entity;
import game.Level;
import game.Player;
import game.TextureManager;

import java.util.List;

public class Knife extends Weapon{
	
		public Knife(int id){
		super(id,"Knife", "A light, Pocket Knife");
		weight = 2;
		icon = TextureManager.get("BowIcon");
	}
		public void use(Entity entity, Level level) {
			
	        if (!(entity instanceof Player)) return;
	        Player player = (Player) entity;
	        List<Entity> entities = level.findEntities(player);
	        System.out.println("You back stabbed   " + entities.get(0));
	        }
}


