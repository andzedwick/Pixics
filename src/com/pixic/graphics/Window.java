package com.pixic.graphics;

import javax.swing.JFrame;

public class Window {
	
	private int width, height;
	private String title;
	private JFrame frame;
	private Screen screen;
	
	private static final int PIXIC_SCALE = 2;
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		initFrame();
		initScreen();
	}
	
	public Screen getScreen() {
		return screen;
	}
	
	private void initFrame() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initScreen() {
		screen = new Screen(width, height, PIXIC_SCALE);
		frame.add(screen);
		frame.setVisible(true);
	}
}
