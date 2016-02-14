import graphics.Screen;
import graphics.Texture;
import graphics.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
	
	private List<Tile> tiles = new ArrayList<Tile>();
	private Random random = new Random();
	private Texture texture;
	
	
	public Test() {
		tiles.add(new Tile(Tile.TYPE_GRASS, 0, 0));
		tiles.add(new Tile(Tile.TYPE_WATER, 2, 0));
		tiles.add(new Tile(Tile.TYPE_DIRT, 0, 1));
		tiles.add(new Tile(Tile.TYPE_STONE, 3, 3));
		
		for (int i = 0; i < 15; i++)
			tiles.add(new Tile(Tile.TYPE_WATER, i, 8));

		for (int i = 0; i < 15; i++)
			tiles.add(new Tile(random.nextInt(3) == 0 ? Tile.TYPE_DIRT : Tile.TYPE_STONE, i, 7));
		
		for (int i = 0; i < 15; i++)
			tiles.add(new Tile(Tile.TYPE_DIRT, i, 6));
		
		for (int i = 0; i < 15; i++)
			tiles.add(new Tile(random.nextInt(3) == 0 ? Tile.TYPE_DIRT : Tile.TYPE_GRASS, i, 5));
		
		for (int i = 0; i < 15; i++)
			tiles.add(new Tile(Tile.TYPE_GRASS, i, 4));
		
		texture = new Texture("C:\\Users\\geoffrey\\workspace\\Game\\res\\player.png");
		
	}
	
	private void render(Screen screen) {
		
		for (int i = 0; i < tiles.size(); i++){
			Tile tile = tiles.get(i);
			screen.fillRect(tile.x * Tile.SIZE, tile.y * Tile.SIZE, Tile.SIZE, Tile.SIZE, tile.getColor()); 	
		}
		screen.drawTexture(50, 50, texture);
	}
	
	public static void main(String[] args) throws Exception {
		Window window = new Window("Game", 960, 540);
		window.show();
		Test game = new Test();
		Screen screen = window.getScreen();
		Random random = new Random();
		while (true) {
			screen = window.getScreen();
			screen.clear(0);
			game.render(screen);
			window.update();
		
		}
		
	}

}
