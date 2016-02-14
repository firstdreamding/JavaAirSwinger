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

	public void damage(int amount) {
		health -= amount;
		if (health <= 0)
			remove();
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
		if (this instanceof Player && collision(xa, ya) == "finish"){
			System.out.println("You did it!");
		    Main.getInstance().nextLevel();
		}
		x += xa;
		y += ya;
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

	public void update() {

	}
	
	public void render(Screen screen) {
		screen.fillRect(x * Tile.SIZE - 5, y * Tile.SIZE - 10, (int)((Tile.SIZE + 10) * health / 10.0), 5, 0xaa);
	}
}