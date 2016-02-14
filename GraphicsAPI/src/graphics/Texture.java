package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	
	private String name;
	private int width, height;
	public int[] pixels;
	
	public Texture(String name, String path) {
		this.name = name;
		load(path);
	}
	
	public Texture(String path) {
		this.name = path;
		load(path);
	}
	
	public Texture(int[] pixels, int width, int height) {
		this.pixels = new int[width * height];
		this.width = width;
		this.height = height;
		
		System.arraycopy(pixels, 0, this.pixels, 0, width * height);
	}
	
	private void load(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
