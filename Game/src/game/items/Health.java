package game.items;

import game.Entity;
import game.Level;
import game.Player;
import game.TextureManager;

public class Health extends Item{
	public Health(int id){
	super(id,"Arrow", "A single, silver arrow");
	weight = 1;
	icon = TextureManager.get("HPotIcon");
	stackable = true;
}
public void use(Entity entity, Level level) {
	Player player = (Player) entity;
	player.recover(5);
	if (player.getHealth() > 10) {
		player.setHealth(10);
	}
}
}
