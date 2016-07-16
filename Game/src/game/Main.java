package game;

import game.items.Item;
import graphics.Screen;
import graphics.Texture;
import graphics.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

	boolean running = false;
	Player player;
	Level level;
	Input input;
	StoryLine Storyline;
	private Window window;
	private Screen screen;
	private List<Tile> tiles = new ArrayList<Tile>();
	private int levelIndex;

	private static Main main;

	private int[] sizeChange = null;

	private void init() {
		boolean bypass = true;
		loadResources();
		input = new Input();
		Storyline = new StoryLine();
		if (!bypass) {
			String scOn = input.getLine(
					"Do you wish to open a save file?(Y/N): ").toLowerCase();
			if (scOn.startsWith("n")) {
				player = new Player(input);
				levelIndex = input.getInt("Choose a level (1-10)");
				input.getLine();
				this.level = Level.create(levelIndex, player);
				Storyline.StoryA();
			} else if (scOn.startsWith("y")) {
				String scName = input
						.getLine("What is the name of this save file?: ");
				Map<String, String> attributes = Save.load(scName);
				if (attributes == null) {
					init();
					return;
				}
				System.out.println("Name: " + attributes.get("name"));
				System.out.println("(" + attributes.get("x") + ", "
						+ attributes.get("y") + ")");
				player = new Player(attributes.get("name"), input);
				level = Level.create(Integer.parseInt(attributes.get("level")),
						player);
				player.setPosition(Integer.parseInt(attributes.get("x")),
						Integer.parseInt(attributes.get("y")));
				String[] pitems = attributes.get("pitems").split(",");
				for (String item : pitems) {
					player.addItem(Item.getByID(Integer.parseInt(item)));
				}
			}
		} else {
			player = new Player("Geoffrey", input);
			levelIndex = 1;
			this.level = Level.create(levelIndex, player);
			Storyline.StoryA();

		}
		sizeChange = new int[] { level.getWidth() * Tile.SIZE,
				level.getHeight() * Tile.SIZE };
		initGraphics();
		running = true;

	}

	private void loadResources() {
		TextureManager.add(new Texture("Player", "res/Player.png"));

		TextureManager.add(new Texture("ArrowIcon", "res/hud/arrow.png"));
		TextureManager.add(new Texture("BowIcon", "res/hud/BowWooden.png"));
		TextureManager.add(new Texture("SwordIcon", "res/hud/Sword.png"));

		TextureManager.add(new Texture("HPotIcon", "res/hud/Health.png"));

		TextureManager.add(new Texture("HeartFullIcon", "res/hud/heart.png"));
		TextureManager
				.add(new Texture("HeartHalfIcon", "res/hud/hearthalf.png"));
		TextureManager.add(new Texture("HeartEmptyIcon",
				"res/hud/heartempty.png"));
		TextureManager.add(new Texture("MainChar", "res/MainChar.png"));

		TextureManager.add(new Texture("Fire", "res/hud/Fire.png"));
	}

	private void initGraphics() {
		Tile.loadTextures();

		new Thread(new Runnable() {
			public void run() {
				graphicsThread();
			}
		}, "Graphics Thread").start();
	}

	public void graphicsThread() {
		window = new Window("Game", 800, 400);
		window.show();

		long lastTime = System.currentTimeMillis();
		double ms = 1000.0 / 60.0;
		double delta = 0.0;
		long timer = System.currentTimeMillis();

		int updates = 0, frames = 0;
		while (true) {
			long now = System.currentTimeMillis();
			delta += (now - lastTime) / ms;
			lastTime = now;
			if (delta >= 1) {
				level.tick();
				updates++;
				delta--;
			}
			if (sizeChange != null) {
				window.setWindowSize(sizeChange[0], sizeChange[1]);
				sizeChange = null;
			}
			screen = window.getScreen();
			screen.clear();
			level.render(screen);
			window.update();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}

	public void run() {
		while (running) {
			String in = input.getLine("Enter an action :> ").toLowerCase();

			String[] commands = in.split(" ");

			switch (commands[0]) {
			case "quit":
			case "stop":
				running = false;
				break;
			case "help":
				;
				Help.listHelp();
				break;
			case "view":
				player.view(commands);
				break;
			case "save":
				if (commands.length > 1) {
					String filename = "";
					for (int i = 1; i < commands.length; i++) {
						filename += commands[i];
						if (i < commands.length - 1)
							filename += " ";
					}
					player.save(filename);
					break;

				}
				player.savePrompt();
				break;
			case "m":
			case "move":
				player.move(commands);
				break;
			case "level":
				newLevel();
				break;
			case "select":
				player.select(commands);
				break;
			case "use":
				player.use();
				break;
			case "drop":
				player.drop();
				break;
			case "take":
				player.take();
				break;
			case "switch":
				player.switcharoo();
				break;
			case "info":
				level.infoOn();
				break;
			case "damage":
				player.damage();
				break;
			}
			level.update();
		}
		input.close();
		System.out.println("\nThanks for playing! :D");
	}

	public static void main(String[] args) {
		System.out.println("WELCOME TO AIR SWINGING 2015");
		main = new Main();
		main.init();
		main.run();
	}

	public void render(Screen screen) {

		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			screen.fillRect(tile.x * Tile.SIZE, tile.y * Tile.SIZE, Tile.SIZE,
					Tile.SIZE, tile.getColor());
		}
	}

	public void newLevel() {
		int level = input.getInt("Choose a level (1-10)");
		input.getLine();
		this.level = Level.create(level, player);
	}

	public void newLevel(int level) {
		this.level = Level.create(level, player);
	}

	public void nextLevel() {
		this.level = Level.create(++levelIndex, player);
		sizeChange = new int[] { level.getWidth() * Tile.SIZE,
				level.getHeight() * Tile.SIZE };
	}

	public static Main getInstance() {
		return main;
	}

}