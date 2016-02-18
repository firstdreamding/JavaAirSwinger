package game;

import java.awt.Color;
import java.awt.Font;

import graphics.Screen;
import graphics.SpriteSheet;
import graphics.Texture;
public class Dragon extends Entity {
	
	private static SpriteSheet dragonTextures = new SpriteSheet(new Texture("res/Dragon.png"),50, 50);
	private int tx, ty;
	private int damage = 0;
	public boolean doneDamage;
	int modX, modY;
	
	public Dragon(int x, int y){
		this.x = x;
		this.y = y;
		
		health = 10;
	}
	
	public void doneDamage(boolean yes) {
		doneDamage = yes;
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
		renderDamage(screen);
		super.render(screen);
	}
	
	public void renderDamage (Screen screen) {
		if(doneDamage){
			screen.drawString("-" + damage, x * Tile.SIZE + modX, y * Tile.SIZE + modY, new Font("Consolas", Font.BOLD, 18), Color.RED);
		}
		
	}
	
	public void damage(int amount) {
		health -= amount;
		damage = amount;
		doneDamage = true;
		if (health <= 0)
			remove();
		
	}
	
}


