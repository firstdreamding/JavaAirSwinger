package game;

import graphics.Screen;


public class StoryRender {
	Level level;
	
	public void render(Screen screen) {
		screen.fillRect(0, 0, 800, 400, 0x444444);
		screen.drawTexture(0, 300, TextureManager.get("MainChar"));
	}
}
