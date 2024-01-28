package com.pixics.graphics;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class PixicWindow {
	
	private JFrame frame = null;
	private PixicScreen screen = null;
	
	private int frameWidth, frameHeight, screenWidth, screenHeight, pixicScale;
	private String frameTitle = "";
	
	public PixicWindow(int frameWidth, int frameHeight, String frameTitle, int screenWidth, int screenHeight, int pixelScale) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameTitle = frameTitle;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.pixicScale = pixelScale;
		
		setupFrame();
		setupScreen();
	}
	
	public PixicWindow(int frameWidth, int frameHeight, String frameTitle, int pixelScale) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameTitle = frameTitle;
		this.pixicScale = pixelScale;
		
		screenWidth = (frameWidth / pixelScale) + 1;
		screenHeight = (frameHeight / pixelScale) + 1;
		
		setupFrame();
		setupScreen();
	}
	
	private void setupFrame() {
		Image applicationIcon = Toolkit.getDefaultToolkit().getImage("src/assets/Pixics_Icon.png");
		frame = new JFrame(frameTitle);
		frame.setSize(frameWidth, frameHeight);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(applicationIcon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
	
	private void setupScreen() {
		if (frame == null) return;
		
		screen = new PixicScreen(screenWidth, screenHeight, pixicScale);
		frame.add(screen);
		screen.repaint();
	}
}
