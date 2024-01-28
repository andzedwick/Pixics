package com.pixics.main;

import javax.swing.JFrame;

import com.pixics.graphics.Screen;

public class Application {
	
	private static final String GAME_TITLE = "PIXICS";
	private static final String GAME_VERSION = "beta 1.0";
	private static final String FRAME_TITLE = GAME_TITLE + " - " + GAME_VERSION;
	
	private Logger log = new Logger();
	private JFrame frame = null;
	private Screen screen = null;
	
	public static void main(String[] args) {
		new Application();
	}
	
	public Application() {
		setupFrame();
		setupScreen();
	}
	
	private void setupFrame() {
		frame = new JFrame(FRAME_TITLE);
		frame.setSize(Screen.getMonitorWidth(), Screen.getMonitorHeight());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
	
	private void setupScreen() {
		if (frame == null) return;
		
		screen = new Screen(Screen.getMonitorWidth() / 10, Screen.getMonitorHeight() / 10, 10);
		frame.add(screen);
		screen.repaint();
	}
}
