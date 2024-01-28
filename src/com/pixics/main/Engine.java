package com.pixics.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.pixics.graphics.PixelScreen;
import com.pixics.graphics.PixicScreen;

public class Engine extends Thread {

	private static Engine engineInstance = null;
	
	public boolean running = false;
	
	// private Logger log = new Logger();
	
	private int frameCap = 120;
	
	private List<PixelScreen> pixelScreens = null;
	private List<PixicScreen> pixicScreens = null;
	
	public static Engine getInstance() {
		if (engineInstance == null) {
			engineInstance = new Engine();
		}
		return engineInstance;
	}
	
	public void run() {
		long msPerRender = 1000L / (long) frameCap;
		long currentTime = System.currentTimeMillis();
		long lastTime;
		long sigmaRender = 0L;
		
		while (running) {
			lastTime = currentTime;
			currentTime = System.currentTimeMillis();
			
			long deltaTime = currentTime - lastTime;
			sigmaRender += deltaTime;
			
			if (sigmaRender >= msPerRender) {
				frameRender();
				sigmaRender = 0L;
			}
			
			engineCycle();
		}
	}
	
	public void frameRender() {
		// Deal with per frame code for PixelScreens
		synchronized(pixelScreens) {
			Iterator<PixelScreen> i = pixelScreens.iterator();
			
			while(i.hasNext()) {
				PixelScreen ps = i.next();
				ps.onFrameRender();
				ps.repaint();
			}
		}
		
		// Deal with per frame code for PixicScreens
		synchronized(pixicScreens) {
			Iterator<PixicScreen> i = pixicScreens.iterator();
			
			while(i.hasNext()) {
				PixicScreen ps = i.next();
				ps.onFrameRender();
				ps.repaint();
			}
		}
	}
	
	public void engineCycle() {
		// Deal with per frame code for PixelScreens
		synchronized(pixelScreens) {
			Iterator<PixelScreen> i = pixelScreens.iterator();
			
			while(i.hasNext()) {
				i.next().onEngineCycle();
			}
		}
		
		// Deal with per frame code for PixicScreens
		synchronized(pixicScreens) {
			Iterator<PixicScreen> i = pixicScreens.iterator();
			
			while(i.hasNext()) {
				i.next().onEngineCycle();
			}
		}
	}
	
	public synchronized void start() {
		super.start();
		running = true;
	}
	
	public synchronized void stopEngine() {
		running = false;
	}
	
	public void setFrameCap(int cap) {
		frameCap = cap;
	}
	
	public int getFrameCap() {
		return frameCap;
	}
	
	/**
	 * Note, this is done automatically for PixelScreen objects
	 * 
	 * @param obj
	 */
	public synchronized void subscribe(PixelScreen obj) {
		pixelScreens.add(obj);
	}
	
	/**
	 * Note, this is done automatically for PixicScreen objects
	 * 
	 * @param obj
	 */
	public synchronized void subscribe(PixicScreen obj) {
		pixicScreens.add(obj);
	}
	
	private Engine() {
		init();
	}
	
	private void init() {
		pixelScreens = Collections.synchronizedList(new ArrayList<PixelScreen>());
		pixicScreens = Collections.synchronizedList(new ArrayList<PixicScreen>());
	}
}
