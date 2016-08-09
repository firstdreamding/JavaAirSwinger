package game;

import graphics.Screen;


public class StoryRender {
	
	Level level;

	public StoryRender (Level level){
	this.level = level;
	}
	
	public void render(Screen screen) {
		screen.fillRect(0, 0, 800, 300, 0x000000);
		screen.fillRect(0, 300, 800, 300, 0xffffff);
	}
}
