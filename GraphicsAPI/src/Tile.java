import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Tile {
	
	public static final int TYPE_WATER = 0;
	public static final int TYPE_DIRT  = 1;
	public static final int TYPE_STONE = 2;
	public static final int TYPE_GRASS = 3;
	
	public static final int SIZE = 50;
	
	
	public final int type;
	public int x, y;
	private int color;

	public Tile(int type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
		
		if (type == TYPE_WATER)
			color = 0x0A6EF2;
		else if (type == TYPE_DIRT)
			color = 0x7A6306;
		else if (type == TYPE_GRASS)
			color = 0x05F55D;
		else if (type == TYPE_STONE)
			color = 0x9D9E98;
	}
	
	public int getColor() {
		return color;
		
	}
	

	
	public String toString() {
		String result = "";
		switch (type) {
		case TYPE_WATER:
			result += "Water ";
			break;
		case TYPE_GRASS:
			result += "Grass ";			
			break;
		case TYPE_DIRT:
			result += "Dirt ";			
			break;
		case TYPE_STONE:
			result += "Stone ";			
			break;
		}
		result += "tile at " + x + "," + y;
		return result;
	}
	
}
