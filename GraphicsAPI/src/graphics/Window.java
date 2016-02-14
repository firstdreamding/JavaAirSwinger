package graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends Canvas {
	
	private int width, height;
	
	private BufferStrategy bs;
	private JFrame frame;
	private Screen screen;
	
	public Window(String title, int width, int height){
		this.width = width;
		this.height = height;
		setSize(new Dimension(width, height));
		
		frame = new JFrame(title);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		init();
	}
	
	private void init(){
		createBufferStrategy(3);
		bs = getBufferStrategy();
		screen = new Screen(width, height, bs);
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public Screen getScreen() {
		return screen;
	}
	
	public void setWindowSize(int width, int height) {
		setSize(width, height);
		frame.pack();
		this.width = width;
		this.height = height;
		init();
	}
	private boolean isValidBuffer() {
		if (bs == null){ 			
			createBufferStrategy(3);
			return false;
		}
		return true;
	}
	
	public void update() {
		screen.flush();
		
	}
	
}
