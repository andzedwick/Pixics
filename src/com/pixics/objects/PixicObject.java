package com.pixics.objects;

import java.awt.Color;
import java.awt.Dimension;

import com.pixics.graphics.Pixel;
import com.pixics.graphics.PixicScreen;
import com.pixics.main.Globals;

public class PixicObject extends Pixel {
	
	public static final PixicObjectType DEFAULT_OBJECT_TYPE = PixicObjectType.AIR;
	public static final Color DEFAULT_COLOR = Color.BLACK;
	
	protected int locX = -1, locY = -1;
	
	protected final long objectId = Globals.getNextId();
	
	protected PixicScreen parentScreen = null;
	protected PixicObjectType objectType;
	
	public PixicObject(PixicScreen parentScreen, Color c, PixicObjectType objectType, int locX, int locY) {
		super(c);
		this.objectType = objectType;
		setLoc(locX, locY);
		this.parentScreen = parentScreen;
	}
	
	public PixicObject(PixicScreen parentScreen, int r, int g, int b, PixicObjectType objectType, int locX, int locY) {
		super(r, g, b);
		this.objectType = objectType;
		setLoc(locX, locY);
		this.parentScreen = parentScreen;
	}
	
	public PixicObject(PixicScreen parentScreen, int r, int g, int b, int a, PixicObjectType objectType, int locX, int locY) {
		super(r, g, b, a);
		this.objectType = objectType;
		setLoc(locX, locY);
		this.parentScreen = parentScreen;
	}
	
	/**
	 * Note: This is meant to be overridden by child objects
	 */
	public void onFrameRender() {
		
	}
	
	/**
	 * Note: This is meant to be overridden by child objects
	 */
	public void onEngineCycle() {
		
	}
	
	public synchronized void setParentScreen(PixicScreen parentScreen) {
		this.parentScreen = parentScreen;
	}
	
	public synchronized PixicScreen getParentScreen() {
		return parentScreen;
	}
	
	public void setObjectType(PixicObjectType objectType) {
		this.objectType = objectType;
	}
	
	public PixicObjectType getObjectType() {
		return objectType;
	}
	
	/**
	 * This should generally only be set by the screen object that owns this object
	 * 
	 * @param locX
	 */
	public void setLocX(int locX) {
		this.locX = locX > 0 ? locX : 0;
	}
	
	public int getLocX() {
		return locX;
	}
	
	/**
	 * This should generally only be set by the screen object that owns this object
	 * 
	 * @param locY
	 */
	public void setLocY(int locY) {
		this.locY = locY > 0 ? locY : 0;
	}
	
	public int getLocY() {
		return locY;
	}
	
	/**
	 * This should generally only be set by the screen object that owns this object
	 * 
	 * @param locX
	 * @param locY
	 */
	public void setLoc(int locX, int locY) {
		setLocX(locX);
		setLocY(locY);
	}
	
	public Dimension getLoc() {
		return new Dimension(locX, locY);
	}
	
	public long getObjectId() {
		return objectId;
	}
}
