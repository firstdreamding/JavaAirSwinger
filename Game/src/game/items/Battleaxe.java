package game.items;

import game.Entity;
import game.Level;
import game.Player;

import java.util.List;

public class Battleaxe extends Weapon {

	public Battleaxe(int id){
		super(id,"Battle Axe ","A Beatifully carved Battle Axe");
		weight = 8;
		
	}
	public void use(Entity entity, Level level) {
		
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        List<Entity> entities = level.findEntities(player);
        System.out.println("You slashed your Battle Axe at  " + entities.get(0));
        }
	}