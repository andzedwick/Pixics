package com.pixic.objects;

import com.pixic.graphics.Screen;

public class PixicObjectFactory {

	// Think about adding a class that can be extended to describe different types of
	// pixic objects
	
	public static PixicObject createPixic(Screen parentScreen, int location, PixicObjectType objectType) {
		switch(objectType) {
			case AIR:
				return new Air(parentScreen, location);
			case SAND:
				return new Sand(parentScreen, location);
			default:
				return new Air(parentScreen, location);
		}
	}
	
}
