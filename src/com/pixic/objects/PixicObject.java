package com.pixic.objects;

import com.pixic.graphics.Screen;

public class PixicObject {

	// TODO - add ids
	
	public static final PixicObjectType DEFAULT_OBJECT_TYPE = PixicObjectType.AIR;
	public static final int DEFAULT_COLOR = 0xFF000000;
	
	// The base of a PixicObject is the pixel
	protected int pixel = 0;
	protected Screen parentScreen;
	protected PixicObjectType objectType;
	
	private int location;
	
	public PixicObject(Screen parentScreen, int pixel, PixicObjectType objectType, int location) {
		this.parentScreen = parentScreen;
		this.pixel = pixel;
		this.objectType = objectType;
		this.location = location;
	}
	
	public void setPixel(int pixel) {
		this.pixel = pixel;
	}
	
	public int getPixel() {
		return pixel;
	}
	
	/**
	 * Generally should only be set by the parent screen
	 * 
	 * @param location
	 */
	public void setLocation(int location) {
		this.location = location;
	}
	
	public int getLocation() {
		return location;
	}
	
	public void setObjectType(PixicObjectType objectType) {
		this.objectType = objectType;
	}
	
	public PixicObjectType getObjectType() {
		return objectType;
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
	public void onTick(long msSinceLastRender) {
		
	}
}
