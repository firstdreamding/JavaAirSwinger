package game;

import graphics.Texture;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
public class Tile {
	
	public static final int TYPE_FLOOR = 0;
	public static final int TYPE_DIRT  = 1;
	public static final int TYPE_WALL = 2;
	public static final int TYPE_GRASS= 3;
	private BufferedImage image;
	
	private static Map<Integer, Texture> textures = new HashMap<Integer, Texture> ();
	
	public static final int SIZE = 50;
	
	
	public final int type;
	public int x, y;
	private int color;
	
	public Tile(int type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
		
		if (type == TYPE_FLOOR)
			color = 0xFFFFF2;
		else if (type == TYPE_DIRT)
			color = 0x7A6306;
		else if (type == TYPE_GRASS)
			color = 0x05F55D;
		else if (type == TYPE_WALL)
			color = 0x9D9E98;
	}
	

    


	public int getColor() {
		return color;
		
	}
	
	public static void loadTextures(){
		textures.put(TYPE_WALL, new Texture("res/Wall.png"));
	}
	public static int getColor(int type) {
		if (type == TYPE_FLOOR)
			return 0x4A3336;
		else if (type == TYPE_DIRT)
			return 0x7A6306;
		else if (type == TYPE_GRASS)
			return 0x05F55D;
		else if (type == TYPE_WALL)
			return 0x555555;
		
		return 0xff00ff;
	}
	
	public String toString() {
		String result = "";
		switch (type) {
		case TYPE_FLOOR:
			result += "Water ";
			break;
		case TYPE_GRASS:
			result += "Grass ";			
			break;
		case TYPE_DIRT:
			result += "Dirt ";			
			break;
		case TYPE_WALL:
			result += "Stone ";			
			break;
		}
		result += "tile at " + x + "," + y;
		return result;
	}





	public static Texture getTexture(int type) {
		Texture result = textures.get(type);
		return result;
	}
	
}
