package game.items;

import game.Entity;
import game.Level;
import graphics.Texture;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Item{

	public int id, weight;
	public String name, description;
	
	protected Texture icon;
	
	protected final Random random = new Random();
	
	private static Map<Integer, Item> items = new HashMap<Integer, Item>();
	public static Item sword = new Sword(0);
	public static Item bow = new Bow(1);
	public static Item arrow = new Arrow(2);
	public static Item knife = new Knife(3);
	public static Item battleaxe = new Battleaxe(4);


	public Item(int id, String name, String description){
		this.id = id;
		this.name = name;
		this.description = description;
		
		items.put(id, this);
	}

	public static final Item getByID(int id) {
		if (!items.containsKey(id))
			return null;
		
		return items.get(id);
	}

	public void use(Entity entity, Level level) {
		System.out.println("This item isn't usable");
	}
	
	public Texture getIcon() {
		return icon;
	}

	public String toString(){
		return name + ": " + description;
	}

	public int getID(){
		return id;
	}

}