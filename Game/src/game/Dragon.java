package game;

import graphics.Screen;
import graphics.SpriteSheet;
import graphics.Texture;
public class Dragon extends Entity {
	
	private static SpriteSheet dragonTextures = new SpriteSheet(new Texture("res/Dragon.png"),50, 50);
	private int tx, ty;
	
	public Dragon(int x, int y){
		this.x = x;
		this.y = y;
		
		health = 10;
	}

	public void update(){
		int xa = random.nextInt(3) - 1;
		int ya = random.nextInt(3) - 1;
		move(xa,ya);
		if(xa < 0){
			tx = 1;
			ty = 0;	
		}else if(xa > 0){
			tx = 1;
			ty = 1;
		}
		
		if (ya < 0){
			tx = 0;
			ty = 1;
		}else if(ya > 0){
			tx = 0;
			ty = 0;
		}
		
		Player player = level.getPlayer();
		if (x == player.getX() && y == player.getY()) {
			player.damage(2);
		}

	}
	
	public void render(Screen screen) {
		screen.drawTexture(x * Tile.SIZE, y * Tile.SIZE, dragonTextures.getTexture(tx, ty));
		super.render(screen);
	}
	
}


