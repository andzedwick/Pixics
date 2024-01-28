package com.pixics.graphics;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class PixicWindow {
	
	private JFrame frame = null;
	private PixicScreen screen = null;
	
	private int frameWidth, frameHeight, screenWidth, screenHeight, pixicScale;
	private String frameTitle = "";
	
	public PixicWindow(int frameWidth, int frameHeight, String frameTitle, int screenWidth, int screenHeight, int pixelScale, boolean fullScreen) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameTitle = frameTitle;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.pixicScale = pixelScale;
		
		setupFrame(fullScreen);
		setupScreen();
	}
	
	public PixicWindow(int frameWidth, int frameHeight, String frameTitle, int pixelScale, boolean fullScreen) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameTitle = frameTitle;
		this.pixicScale = pixelScale;
		
		screenWidth = (frameWidth / pixelScale);
		screenHeight = (frameHeight / pixelScale);
		
		setupFrame(fullScreen);
		setupScreen();
	}
	
	public void setPixicScreen(PixicScreen screen) {
		this.screen = screen;
	}
	
	public PixicScreen getPixicScreen() {
		return screen;
	}
	
	private void setupFrame(boolean fullScreen) {
		Image applicationIcon = Toolkit.getDefaultToolkit().getImage("src/assets/Pixics_Icon.png");
		frame = new JFrame(frameTitle);
		frame.setSize(frameWidth, frameHeight);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(applicationIcon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if (fullScreen) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
	private void setupScreen() {
		if (frame == null) return;
		
		screen = new PixicScreen(screenWidth, screenHeight, pixicScale);
		screen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		frame.add(screen);
		screen.repaint();
	}
}
