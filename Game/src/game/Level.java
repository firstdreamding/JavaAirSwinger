package game;
import game.items.Item;
import game.items.Weapon;
import graphics.Screen;
import graphics.Texture;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Level {

	protected List<Entity> entities = new ArrayList<Entity>();
	protected String level;
	protected Player player;
	protected int width, height;
    private BufferedImage image;
    private static boolean infoOn = true;
    int turns;
    
    
	public final int ID;
	
	public class LevelItem {
		Item item;
		int x, y;
		
		LevelItem(Item item, int x, int y) {
			this.item = item;
			this.x = x;
			this.y = y;
		}
	}
	
	private List<LevelItem> items = new ArrayList<LevelItem> ();
	
	public Level(Player player, int ID){
		this.player = player;
		this.ID = ID;
		add(player);
	}

	public void add(Entity entity){
		entity.init(this);
		entities.add(entity);
	}

	protected void generate(){
		
	}
	
	protected void placeItem(Item item, int x, int y) {
		items.add(new LevelItem(item, x, y));
	}

	public char getTile(int x, int y){
		if (x < 0 || x >= width || y < 0 || y >= height)
			return '\0';
		return level.charAt(x + y * width);
	}
	public String getMap() {
	 return level;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
	protected String load(String path){
		String level = "";
		width = height = 0;
	    try{
	      Scanner file = new Scanner(new File(path));
	          while (file.hasNextLine()) {
	              String line = file.nextLine();
	              width = line.length();
	              height++;
	              level += line;
	        }
	        file.close();
	      }catch(FileNotFoundException e){
	        System.out.println("File not found!");
	        return null;
	      }
	      return level;
	}
	
	public void render(Screen screen) {
		//Render Level
		for (int i = 0; i < level.length(); i++){
			int type = Tile.TYPE_FLOOR;
			char c = level.charAt(i);
			if (c == 'P' || c == 'D' || c == 'M') type = Tile.TYPE_FLOOR;
			else if (c == '#'){
				type = Tile.TYPE_WALL;
			}
			int xi = i % getWidth();
			int yi = i / getWidth();
			
			Texture texture = Tile.getTexture(type);
			if (texture == null)
				screen.fillRect(xi * Tile.SIZE, yi * Tile.SIZE, Tile.SIZE, Tile.SIZE, Tile.getColor(type)); 	
			else
				screen.drawTexture(xi * Tile.SIZE, yi * Tile.SIZE,Tile.getTexture(Tile.TYPE_WALL));
		}
		//Render The items
		boolean renderedItemPanel = false;
		for (int i = 0; i < items.size(); i++) {
			LevelItem item = items.get(i);
			int x = item.x * Tile.SIZE;
			int y = item.y * Tile.SIZE;
			screen.drawTexture(x, y, item.item.getIcon());
			if (distance(player, x, y) < 100 && !renderedItemPanel && infoOn) {
				renderItemPanel(screen, item.item, x + 40, y);
				renderedItemPanel = true;
			}
				
		}
		//Render The entities
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
	}
	
	private void renderItemPanel(Screen screen, Item item, int x, int y) {
		screen.fillRect(x, y, 100, 200, 0xff555555);
		screen.drawString(item.name, x + 5, y + 20, new Font("Consolas", Font.PLAIN, 18), Color.WHITE);
		screen.drawString("Wt.-> " + item.weight, x + 5, y + 40, new Font("Consolas", Font.PLAIN, 18), Color.WHITE);
		if (item instanceof Weapon) {
			Weapon w = (Weapon)item;
			Item selected = player.getSelectedItem();
			Color damageColor = Color.WHITE;
			Color rangeColor = Color.WHITE;		
			if (selected instanceof Weapon) {
				Weapon p = (Weapon)selected;
				if (w.getRange() > p.getRange())
					rangeColor = Color.GREEN;
				else if (p.getRange() > w.getRange())
					rangeColor = Color.RED;
				
				if (w.getDamage() > p.getDamage())
					damageColor = Color.GREEN;
				else if (p.getDamage() > w.getDamage())
					damageColor = Color.RED;
			}
			screen.drawString("Dmg.-> " + w.getDamage(), x + 5, y + 60, new Font("Consolas", Font.PLAIN, 18), damageColor);
			screen.drawString("Rng.-> " + w.getRange(), x + 5, y + 80, new Font("Consolas", Font.PLAIN, 18), rangeColor);
		}
	}
	
	
		

    public void ImagePanel() {
        try {                
           image = ImageIO.read(new File("image name and path"));

        } catch (IOException ex) {
             // handle exception...
        }
     }

	public static Level create(int level, Player player) {
			switch (level){
				case 1:
					return new LevelOne(player);
				case 2:
					return new LevelTwo(player);
			}
			return null;
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) {
				entities.remove(i--);
				continue;
			}
			entities.get(i).update();	
		}
	}

	public List<Entity> findEntities(){
		return entities;
	}
	
	public Player getPlayer() {
		return player;
	}

	public List<Entity> findEntities(Entity entity){
		List<Entity> results = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			if (entity.equals(entities.get(i))) continue;
			results.add(entities.get(i));
		}
		return results;

	}
	
	public <T> List<T> findEntities(Class<T> cls, int distance, Entity exclude){
		List<T> results = new ArrayList<T>();
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (cls.isInstance(e)){
				T result = (T)e;
				if (distance((Entity)result, exclude) < distance)
					results.add((T)e);
			}
		}
		return results;
	}
	
	private int distance(Entity a, Entity b) {
		int x = a.x - b.x;
		int y = a.y - b.y;
		return (int) Math.sqrt(x * x + y * y);
	}
	
	private int distance(Entity a, int xp, int yp) {
		int x = a.x * Tile.SIZE - xp;
		int y = a.y * Tile.SIZE - yp;
		return (int) Math.sqrt(x * x + y * y);
	}


	public <T> List<T> getEntities(Class<?> cls) {
		List<T> results = new ArrayList<T>();
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (cls.isInstance(e))
				results.add((T)e);
		}
		return results;

	}

	public void addItem(Item item, int x, int y) {
		items.add(new LevelItem(item, x, y));		
	}

	public Item getItem(int x, int y) {
		for (LevelItem item : items) {
			if (item.x == x && item.y == y)
				return item.item;
		}
		return null;
	}

	public void removeItem(int x, int y) {
		for (int i = 0; i < items.size(); i++) {
			LevelItem item = items.get(i);
			if (item.x == x && item.y == y) {
				items.remove(i);
				break;
			}
		}
		
	}

	public void infoOn() {
		String infoInfo = "Infomation boxes turned: ON";
		if(infoOn){
			infoOn = false;
			infoInfo = "Infomation boxes turned: OFF";
		}
		else if(!infoOn){
			infoOn = true;
			infoInfo = "Infomation boxes turned: ON";
		}
		System.out.println(infoInfo);
	}

}