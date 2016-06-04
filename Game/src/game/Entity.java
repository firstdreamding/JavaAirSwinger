package game;

import graphics.Screen;

import java.util.Random;

public class Entity {

	protected int x, y;
	protected Level level;
	protected int health;
	protected Random random = new Random();
	private boolean removed = false;

	public void init(Level level) {
		this.level = level;
	}

	public int getX() {
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getHealth(){
		return health;
	}

	public void damage(int amount) {
		health -= amount;
		if (health <= 0)
			remove();
	}
	
	public void recover(int amount) {
		health += amount;
	}
	
	public void setHealth(int amount) {
		health = amount;
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	protected void move(int xa, int ya) {
		if (collision(xa, ya) == "wall") {
			return;
		}
		char fromTile = level.getTile(x,y);
		x += xa;
		y += ya;
		char toTile = level.getTile(x,y);
		onTileEntered(fromTile, toTile, x, y);
	}

	protected String collision(int xa, int ya) {
		char walls = level.getTile(x + xa, y + ya);
		if (walls == '#') return "wall";
		if (walls == 'F') return "finish";
		return "";		

	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}

	protected void onTileEntered(char fromTile, char toTile, int x, int y) {
		
	}
	
	public void update() {

	}
	
	public void render(Screen screen) {
		screen.fillRect(x * Tile.SIZE - 5, y * Tile.SIZE - 10, (int)((Tile.SIZE + 10) * health / 10.0), 5, 0xaa);
	}
}