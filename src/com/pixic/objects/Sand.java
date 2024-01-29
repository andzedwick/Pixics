package com.pixic.objects;

import java.util.Random;

import com.pixic.graphics.Screen;

public class Sand extends PixicObject {
	
	public static final int COLOR = 0xFFFFD076;
	public static final int ALLOWED_RGB_VARIATION = 25;
	
	public Sand(Screen parentScreen, int pixel, PixicObjectType objectType) {
		super(parentScreen, pixel, objectType);
	}
	
	public static int getVariedSandColor() {
		Random rand = new Random();
		int r = rand.nextInt(ALLOWED_RGB_VARIATION);
		int g = rand.nextInt(ALLOWED_RGB_VARIATION);
		int b = rand.nextInt(ALLOWED_RGB_VARIATION);
		int rgbVariationAmount = (255 << 24) | (r << 16) | (g << 8) | b;
		
		return COLOR | rgbVariationAmount;
	}
	
	/**
	 * Meant to be overridden by child classes
	 * 
	 * @param msSinceLastRender
	 */
	public void onRender(long msSinceLastRender) {
		System.out.println("Rendering Sand");
	}
	
	/**
	 * Meant to be overridden by child classes
	 * 
	 * @param msSinceLastRender
	 */
	public void onEngineCycle(long msSinceLastRender) {
		
	}
}
