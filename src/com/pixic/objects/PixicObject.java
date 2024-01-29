package com.pixic.objects;

import com.pixic.graphics.Screen;

public class PixicObject {

	// TODO - add ids
	
	public static final PixicObjectType DEFAULT_OBJECT_TYPE = PixicObjectType.AIR;
	public static final int DEFAULT_COLOR = 0xFF000000;
	
	// The base of a PixicObject is the pixel
	protected int pixel = 0;
	
	protected PixicObjectType objectType;
	protected Screen parentScreen;
	
	public PixicObject(Screen parentScreen, int pixel, PixicObjectType objectType) {
		this.parentScreen = parentScreen;
		this.pixel = pixel;
		this.objectType = objectType;
	}
	
	public void setPixel(int pixel) {
		this.pixel = pixel;
	}
	
	public int getPixel() {
		return pixel;
	}
	
	/**
	 * Meant to be overridden by child classes
	 * 
	 * @param msSinceLastRender
	 */
	public void onRender(long msSinceLastRender) {
		
	}
	
	/**
	 * Meant to be overridden by child classes
	 * 
	 * @param msSinceLastRender
	 */
	public void onEngineCycle(long msSinceLastRender) {
		
	}
}
