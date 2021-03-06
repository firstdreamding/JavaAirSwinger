package game;

import game.items.Item;
import graphics.Screen;
import graphics.SpriteSheet;
import graphics.Texture;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Entity {

	final int MAX_ITEMS = 10;

	Input input;
	String name;
	public PlayerItem[] items = new PlayerItem[MAX_ITEMS];
	int selected = 0;
	int index = 0;
	private Font itemQtyFont = new Font("Helvetica", Font.PLAIN, 18);
	char didyouslam;
	boolean isOnFire = false;
	int fireOn = 5;

	private SpriteSheet playerTextures;
	private int tx, ty;

	private Map<String, String> commandHistory = new HashMap<String, String>();

	private Texture[] heartTextures = new Texture[3];

	public class PlayerItem {
		public Item item;
		public int quantity;

		PlayerItem() {

		}

		PlayerItem(Item item, int qty) {
			this.item = item;
			this.quantity = qty;
		}

	}

	public Player(Input input) {
		this.input = input;
		name = input.getLine("Please enter your explore's name: ");
		init();
	}

	public Player(String name, Input input) {
		this.name = name;
		this.input = input;
		init();
	}

	private void init() {

		playerTextures = new SpriteSheet(TextureManager.get("Player"), 50, 50);

		heartTextures[0] = TextureManager.get("HeartEmptyIcon");
		heartTextures[1] = TextureManager.get("HeartHalfIcon");
		heartTextures[2] = TextureManager.get("HeartFullIcon");

		health = 10;
		addItem(Item.sword, 0);
		addItem(Item.bow, 1);
		addItem(Item.arrow, 2, 97);
		addItem(Item.arrow, 3, 98);

	}

	public void addItem(Item item) {

		for (int i = 0; i < MAX_ITEMS; i++) {
			if (items[i] == null) {
				addItem(item, i);
				return;
			}
		}

	}

	public void addItem(Item item, int slot) {
		addItem(item, slot, 1);
	}

	public void addItem(Item item, int slot, int quantity) {
		if (item == null) {
			items[slot] = null;
			return;
		}

		else if (items[slot] == null && item.isStackable()) {
			if (quantity > item.getStackLimit()) {
				int extra = quantity - item.getStackLimit();
				items[slot] = new PlayerItem();
				items[slot].item = item;
				items[slot].quantity = 99;
				addItem(item, slot, extra);
			} else {
				items[slot] = new PlayerItem();
				items[slot].item = item;
				items[slot].quantity = quantity;
			}
		}

		else if (!item.isStackable()) {
			if (items[slot] == null) {
				items[slot] = new PlayerItem();
				items[slot].item = item;
				items[slot].quantity = 1;
				quantity--;
				if (quantity == 0)
					return;
			}
			slot = findEmptySlot();
			if (slot == -1) {
				System.out.println("Full Inventory");
				return;
			}
			items[slot] = new PlayerItem();
			items[slot].item = item;
			items[slot].quantity = 1;
			quantity--;
			if (quantity == 0)
				return;
			int extra = quantity - 1;
			addItem(item, slot, extra);
		} else if (items[slot] != null && item.isStackable()) {
			slot = findStackSlot(item);
			if (slot == -1) {
				slot = findEmptySlot();
				if (slot == -1) {
					System.out.println("Full Inventory");
					return;
				}
				addItem(item, slot, quantity);
			}
			if (quantity + items[slot].quantity > item.getStackLimit()) {
				int extra = quantity + items[slot].quantity
						- item.getStackLimit();
				items[slot].quantity = 99;
				addItem(item, slot + 1, extra);
			} else {
				items[slot].quantity += quantity;
			}
		}
	}

	public void removeItem(Item item, int qty) {
		for (int i = 0; i < MAX_ITEMS; i++) {
			if (items[i] == null)
				continue;

			if (items[i].item == Item.arrow) {
				int xtra = items[i].quantity - qty;
				if (xtra < 0) {
					qty = -xtra;
					items[i] = null;
				} else {
					items[i].quantity = xtra;
					return;
				}
			}
		}
	}
	
	public boolean removeItem(Class<? extends Item> cls, int qty) {
		for (int i = 0; i < MAX_ITEMS; i++) {
			if (items[i] == null)
				continue;
			if (cls.isInstance(items[i].item)) {
				int remainder = items[i].quantity - qty;
				if (remainder <= 0) {
					items[i] = null;
				} else {
					items[i].quantity = remainder;
				}
				return true;
			}
		}
		return false;
	}

	private int findEmptySlot() {
		for (int i = 0; i < MAX_ITEMS; i++) {
			if (items[i] == null)
				return i;
		}
		return -1;
	}

	private int findStackSlot(Item item) {
		for (int i = 0; i < MAX_ITEMS; i++) {
			if (items[i] != null && items[i].item == item
					&& items[i].quantity < item.getStackLimit())
				return i;
		}
		return -1;
	}

	public Item getSelectedItem() {

		return items[selected] == null ? null : items[selected].item;
	}

	public void save() {
		save("save");
	}

	public void save(String littlefile) {
		if (littlefile.contains("/") || littlefile.contains("\\")) {
			System.out
					.println("NOOOO!!!! ILLEGAL FILE NAME! ERROR ERROR ERROR!");
			return;
		}
		System.out.println("Saving Game!");
		File dir = new File("saves");
		if (!dir.exists())
			dir.mkdir();
		File file = new File("saves/" + littlefile + ".txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String overwrite = input.getLine(
					littlefile + " Exists! Overwrite? (Y/N) ").toLowerCase();
			input.print(overwrite);
			if (!overwrite.startsWith("y"))
				return;
		}
		System.out.println("Saved! Your saved file is " + littlefile);

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write("name=" + name);
			out.newLine();

			out.write("level=" + level.ID);
			out.newLine();

			out.write("x=" + x);
			out.newLine();

			out.write("y=" + y);
			out.newLine();

			out.write("player {");
			out.newLine();

			for (int i = 0; i < MAX_ITEMS; i++) {
				if (items[i] == null)
					continue;
				out.write("\titem=" + items[i].item.getID());
				out.newLine();
			}
			out.write("}");

			out.newLine();

			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("File is not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void select(String[] commands) {
		if (commands.length > 1) {

			select(Validator.stringToInt(commands[1],
					"Enter a number you little word typer!!"));
		} else {
			select(input.getInt("Slot? :>"));
		}
	}

	public void select(int index) {
		if (index < 0 || index >= MAX_ITEMS) {
			System.out.println("Your bag doesn't hold up to " + index);
			return;
		}
		selected = index;
		System.out.println("You have selected: " + items[selected]);
	}

	public void use() {
		if (items[selected] != null)
			items[selected].item.use(this, level);
		else
			System.out
					.println("Didn't your mother teach you not to use nothing!?");

	}

	public void view(String[] commands) {
		if (commands.length < 2) {
			System.out.println("What would you like to view.");
			System.out
					.println("View self, View me, View Radar or View everything?");
			System.out.println("View self and View me does the samething.");
			return;
		}
		if (commands[1].equals("everything")) {
			List<Entity> entities = level.findEntities(this);
			for (Entity e : entities) {
				System.out.println(e + ": " + e.getX() + ", " + e.getY());
			}
		} else if (commands[1].equals("radar")) {

			int cycles = input.getInt("How many cycles? ");
			for (int i = 0; i < cycles; i++) {
				List<Entity> entities = level.findEntities(this);
				for (Entity e : entities) {
					System.out.println(e + ": " + e.getX() + ", " + e.getY());
				}
				System.out.println("/n------/n");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		} else if (commands[1].equals("me") || commands[1].equals("self")) {
			System.out.println(this);
		} else if (commands[1].equals("map")) {
			String map = level.getMap();
			System.out.println("Loading map... Please wait... ");
			String line = "";
			System.out.println("Length: " + map.length());
			System.out.println("Width: " + level.getWidth());
			System.out.println("Height: " + level.getHeight());
			for (int i = 0; i < map.length(); i++) {
				char c = map.charAt(i);
				if (c == 'P' || c == 'D' || c == 'M')
					c = ' ';
				int xi = i % level.getWidth();
				int yi = i / level.getWidth();
				if (x == xi && y == yi)
					c = 'P';
				line += c;
				if ((i + 1) % level.getWidth() == 0) {
					System.out.println(line);
					line = "";
				}
			}
		}
	}

	public void move(String[] commands) {
		if (commands.length < 2) {
			if (commandHistory.containsKey("move")) {
				commands = new String[] { commands[0],
						commandHistory.get("move") };
			} else {
				System.out
						.println("Please enter a direction (up, down, left, right)");
				return;
			}
		}
		int xa = 0;
		int ya = 0;
		switch (commands[1]) {
		case "up":
			ya--;
			tx = 1;
			ty = 1;
			break;
		case "down":
			ya++;
			tx = 0;
			ty = 1;
			break;
		case "left":
			xa--;
			tx = 0;
			ty = 0;
			break;
		case "right":
			xa++;
			tx = 1;
			ty = 0;
			break;
		}
		commandHistory.put("move", commands[1]);
		if (level.getTile(x + xa, y + ya) == '#') {
			level.message("You slammed into the wall. You can't move there!");
		}
		if (level.getTile(x + xa, y + ya) == '|')
			System.out.print("Its a sign, but it is too scratched up to be read.\n");
		move(xa, ya);

	}

	public void take() {
		Level.LevelItem item = level.getLevelItem(x, y);
		if (item == null)
			return;

		level.removeItem(x, y);
		addItem(item.item, selected, item.quantity);
	}

	public void drop() {
		Item item = items[selected].item;
		if (item == null)
			return;
		addItem(null, selected);
		level.addItem(item, x, y);
	}

	public void switcharoo() {
		Item itemNew = level.getItem(x, y);
		if (itemNew == null)
			return;
		level.removeItem(x, y);
		Item itemOld = items[selected].item;
		if (itemOld == null)
			return;
		addItem(null, selected);
		level.addItem(itemOld, x, y);
		addItem(itemNew, selected);
	}

	public void savePrompt() {
		save(input.getLine("Enter save name :"));
		System.out.println("Done.");
	}

	protected void onTileEntered(char fromTile, char toTile, int x, int y) {
		switch (toTile) {
		case 'L':
			System.out.println("I'm on fire! Better get to water!");
			isOnFire = true;
			break;
		case 'F':
			System.out.println("You did it!");
			Main.getInstance().nextLevel();
			break;
		case 'W':
			System.out.println("Extinguished!");
			isOnFire = false;
		}
	}

	public void update() {
		if (isOnFire) {
			damage(1);
			fireOn--;
			if (fireOn == 0) {
				isOnFire = false;
			}
		} else if (!(fireOn == 5))
			fireOn = 5;
	}

	private void renderInventory(Screen screen) {
		int width = level.getWidth() * Tile.SIZE;
		int height = level.getHeight() * Tile.SIZE;
		int x = width / 2 - (40 * 10) / 2;
		int y = height - 45;

		screen.fillRect(x, y, 400, 40, 0xbababa);

		for (int i = 0; i < 10; i++) {
			screen.fillRect(x + i * 40 + 2, y + 2, 36, 36, 0x333333);
			if (items[i] != null) {
				screen.drawTexture(x + i * 40 + 2, y + 2,
						items[i].item.getIcon());
			}

		}

		for (int j = 0; j < MAX_ITEMS; j++) {
			if (items[j] == null)
				continue;
			if (items[j].quantity > 1) {
				int offset = items[j].quantity < 10 ? 28 : 18;
				screen.drawString(items[j].quantity + "", x + j * 40 + offset,
						y + 38, itemQtyFont, Color.WHITE);
			}
		}

		x = x + selected * 40;

		screen.drawRect(x + 0, y + 0, 40, 40, 0xffffff);
		screen.drawRect(x + 1, y + 1, 38, 38, 0x0);
		screen.drawRect(x + 2, y + 2, 36, 36, 0xffffff);

	}

	private void renderHUD(Screen screen) {

		renderInventory(screen);
		int h = health / 2;
		boolean half = health % 2 != 0;

		int width = level.getWidth() * Tile.SIZE;
		int x = width - 5 * 38 - 5;
		int y = 5;
		for (int i = 0; i < 5; i++) {
			int tex = 0;
			if (i < h)
				tex = 2;
			else if (i == h && half)
				tex = 1;
			screen.drawTexture(x + i * 38, y, heartTextures[tex]);
		}
	}

	private void renderFire(Screen screen) {
		if (isOnFire) {
			screen.drawTexture(x * Tile.SIZE + 14, y * Tile.SIZE + 20,
					TextureManager.get("Fire"));
			if (Level.infoOn)
				screen.drawString("FIRE", x * Tile.SIZE + 5,
						y * Tile.SIZE + 65, itemQtyFont, Color.RED);
		}
	}

	public void render(Screen screen) {
		screen.drawTexture(x * Tile.SIZE, y * Tile.SIZE,
				playerTextures.getTexture(tx, ty));
		screen.drawString(name, x * Tile.SIZE - 25, y * Tile.SIZE);
		renderHUD(screen);
		renderFire(screen);
		// super.render(screen);
	}

	public String toString() {

		String result = name + " has:\n";

		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				result += "  ";
				int qty = items[i].quantity;
				result += qty + " " + items[i].item.name;
				if (qty > 1)
					result += "s";
				result += "\n";
			}
		}
		return result;
	}

	public void damage() {
		// TODO Auto-generated method stub

	}

}