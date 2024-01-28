package com.pixics.graphics;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class PixelWindow {
	
	private JFrame frame = null;
	private PixelScreen screen = null;
	
	private int frameWidth, frameHeight, screenWidth, screenHeight, pixelScale;
	private String frameTitle = "";
	
	public PixelWindow(int frameWidth, int frameHeight, String frameTitle, int screenWidth, int screenHeight, int pixelScale) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameTitle = frameTitle;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.pixelScale = pixelScale;
		
		setupFrame();
		setupScreen();
	}
	
	public PixelWindow(int frameWidth, int frameHeight, String frameTitle, int pixelScale) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameTitle = frameTitle;
		this.pixelScale = pixelScale;
		
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
		
		screen = new PixelScreen(screenWidth, screenHeight, pixelScale);
		frame.add(screen);
		screen.repaint();
	}
}
