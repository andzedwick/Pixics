package com.pixics.objects;

import java.awt.Color;

import com.pixics.graphics.PixicScreen;
import com.pixics.main.Logger;


public class PixicObjectFactory {
	
	private static Logger log = new Logger();
	
	private PixicObjectFactory() {}
	
	public static PixicObject createGenericPixicObject(PixicScreen parentScreen, int r, int g, int b, PixicObjectType objectType, int locX, int locY) {
		return createGenericPixicObject(parentScreen, new Color(r, g, b), objectType, locX, locY);
	}
	
	public static PixicObject createGenericPixicObject(PixicScreen parentScreen, int r, int g, int b, int a, PixicObjectType objectType, int locX, int locY) {
		return createGenericPixicObject(parentScreen, new Color(r, g, b, a), objectType, locX, locY);
	}
	
	public static PixicObject createGenericPixicObject(PixicScreen parentScreen, Color c, PixicObjectType objectType, int locX, int locY) {
		switch(objectType) {
			case AIR:
				return new Air(parentScreen, locX, locY);
			case SAND:
				return new Sand(parentScreen, c, locX, locY);
			default:
				break;
		}
		
		log.warning("PixicObjectFactory > createGenericPixicObject() - Unable to find object type given");
		return null;
	}
}
