package game;

import graphics.Screen;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class StoryRender {
	
	private Level level;
	private Queue<Dialogue> storyQueue = new LinkedList<Dialogue>();
	private Font font = new Font("Consolas", Font.BOLD, 14);
	
	private int time = 0;
	private String currentMessage;
	private int index = 0;
	private boolean done = false;
	private int lineWidth = 80;
	
	public StoryRender(Level level) {
		this.level = level;
	}
	
	public void add(Dialogue dialogue) {
		storyQueue.add(dialogue);
	}
	
	public void add(List<Dialogue> dialogue) {
		for (Dialogue d : dialogue)
			add(d);
	}
	
	public void tick() {
		time++;
		
		if (storyQueue.isEmpty())
			return;
		String message = storyQueue.peek().text;
		if (index < message.length()) {
			currentMessage = message.substring(0, index);
			if (time % 2 == 0)
				index++;
		}
		else{
			done = true;
			done = false;
			storyQueue.remove();
			index = 0;
		}
	}
	
	private void renderStory(Screen screen, int px, int py) {
		if (currentMessage == null)
			return;
		int lines = currentMessage.length()/ lineWidth + 1;
		for (int i = 0; i < lines; i++) {
			String m = "";
			if (i < lines - 1)
				m = currentMessage.substring(i * lineWidth, (i + 1) * lineWidth);
			else
				m = currentMessage.substring(i * lineWidth);
			screen.drawString(m, px, py + i * 16, font, Color.WHITE);
		}
	}
	
	public void render(Screen screen) {
		screen.fillRect(0, 300, 800, 100, 0x445566);
		renderStory(screen, 110, 320);
		
		screen.drawTexture(0, 300, TextureManager.get("MainChar"));
		//screen.fillRect(0, 0, 800, 400, 0x444444);
		
		if (done) {
			if (time % 40 > 20)
				screen.drawString("->", 760, 380);
		}
	}
	
}
