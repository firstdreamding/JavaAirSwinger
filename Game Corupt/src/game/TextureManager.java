package game;

import graphics.Texture;

import java.util.HashMap;
import java.util.Map;


public class TextureManager {
	
	private static Map<String, Texture> textures = new HashMap<String,Texture>();
	
	private TextureManager(){
	}
	
	public static void add(Texture texture) {
		textures.put(texture.getName(), texture);
	}
	
	public static Texture get(String name) {
		if (!textures.containsKey(name)) {
			System.err.println("Texture '" + name + "' doesn't exist!");
			return null;
		}
		return textures.get(name);
	}
	
}
