package game.items;

import game.Entity;
import game.Level;
import game.Player;
import game.TextureManager;

import java.util.List;

public class Bow extends Weapon {

	public Bow(int id) {
		super(id, "Bow", "A bow, with no arrows.");
		weight = 5;
		damage = 5;
		range = 5;
		icon = TextureManager.get("BowIcon");
	}

	public void use(Entity entity, Level level) {
		if (!(entity instanceof Player))
			return;
		Player player = (Player) entity;
		boolean found = false;
		Arrow arrow = null;
		for (int i = 0; i < player.items.length; i++) {
			if (player.items[i] == null)
				continue;
			if (player.items[i].item instanceof Arrow) {
				found = true;
				arrow = (Arrow) player.items[i].item;
				player.removeItem(player.items[i].item, 1);
				break;
			}
		}
		if (found) {
			List<Entity> entities = level.findEntities(player);
			if (entities.size() > 0) {
				System.out.println("You fired an arrow at " + entities.get(0));
				entities.get(0).damage(this.damage + arrow.getDamage());
			}

		} else {

			System.out.println("You have no arrows!");

		}

	}
}