package com.pixic.main;

import com.pixic.graphics.Screen;

public class Engine extends Thread {

	private static Engine instance;
	private Screen screen;
	
	private boolean running = false;
	
	private Engine() {}
	
	public static Engine getInstance() {
		if (instance == null) instance = new Engine();
		return instance;
	}
	
	public void start() {
		super.start();
	}
	
	public void stopEngine() {
		running = false;
	}
	
	public void run() {
		super.run();
		running = true;
		
		long currentTime = System.currentTimeMillis();
		long lastTime;
		
		while (running) {
			lastTime = currentTime;
			currentTime = System.currentTimeMillis();
			long deltaTime = currentTime - lastTime;
			
			tick(deltaTime);
			if (screen != null) {
				screen.render(deltaTime);
			}
		}
	}
	
	private void tick(long deltaMs) {
		
	}
	
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
	public Screen getScreen() {
		return screen;
	}
}
