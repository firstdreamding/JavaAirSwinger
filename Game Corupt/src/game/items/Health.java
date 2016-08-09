package game.items;

import game.Entity;
import game.Level;
import game.Player;
import game.TextureManager;

public class Health extends Item {
	public Health(int id) {
		super(id, "Health Pot", "A single bottle of magic that can heal you up to half of your health.");
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
		player.removeItem(Health.class, 1);
	}
}
