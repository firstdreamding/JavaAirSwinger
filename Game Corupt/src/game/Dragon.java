package game;

import graphics.Screen;
import graphics.SpriteSheet;
import graphics.Texture;

import java.awt.Color;
import java.awt.Font;
public class Dragon extends Entity {
	
	private static SpriteSheet dragonTextures = new SpriteSheet(new Texture("res/Dragon.png"),50, 50);
	private int tx, ty;
	private int damage = 0;
	float modX, modY;
	int damageTimer;
	float opacity = 1;
	float red = 0.9f;
	float colors;
	
	public Dragon(int x, int y){
		this.x = x;
		this.y = y;
		
		health = 10;
	}
	
	public void tick() {
		if(showDamage()){	
			modY -= 0.6f;
			opacity -= 0.75f / 60f;
			colors += 0.3f / 60f;
			modX += random.nextInt(3) - 1;
		}
		damageTimer--;
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
		if (opacity < 0.0f)
			opacity = 0.0f;
		
		if (opacity > 1.0f)
			opacity = 1.0f;
		
		if(showDamage()){
			screen.drawString("-" + damage, (int)(x * Tile.SIZE + modX), (int)(y * Tile.SIZE + modY), new Font("Consolas", Font.BOLD, 18), new Color(red, colors, colors, opacity));
		}
		
	}
	
	public boolean showDamage() {
		return damageTimer > 0;
	}
	
	public void damage(int amount) {
		health -= amount;
		modX = 15;
		damage = amount;
		opacity = 1.0f;
		red = 0.9f;
		damageTimer = 60;
		modY = 0;
		colors = 0.1f;
		
		if (health <= 0)
			remove();
		
	}
	
}


