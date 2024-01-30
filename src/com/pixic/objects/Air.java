package com.pixic.objects;

import com.pixic.graphics.Screen;

public class Air extends PixicObject {
	
	public static final int COLOR = 0x00FFFFFF;
	
	public Air(Screen parentScreen, int pixel, int location) {
		super(parentScreen, pixel, PixicObjectType.AIR, location);
	}
	
	public Air(Screen parentScreen, int location) {
		super(parentScreen, COLOR, PixicObjectType.AIR, location);
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
