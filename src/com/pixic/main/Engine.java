package com.pixic.main;

import com.pixic.graphics.Screen;

public class Engine extends Thread {

	private static Engine instance;
	private Screen screen;
	
	private boolean running = false;
	
	private long fpsCap = 120;
	private long fpsCapMsPerRender = 1000L / fpsCap;
	private long sigmaTime = 0L;
	
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
			sigmaTime += deltaTime;
			
			tick(deltaTime);
			
			if (screen != null) {
				if (sigmaTime >= fpsCapMsPerRender) {
					screen.render(sigmaTime);
					
					sigmaTime = 0L;
				}
			}
		}
	}
	
	private void tick(long deltaMs) {
		screen.tick(deltaMs);
	}
	
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
	public Screen getScreen() {
		return screen;
	}
}
