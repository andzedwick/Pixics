package com.pixics.objects;

import java.awt.Color;

import com.pixics.graphics.PixicScreen;

public class Air extends PixicObject {
	// private Logger log = new Logger();
	
	public Air(PixicScreen parentScreen, int locX, int locY) {
		super(parentScreen, new Color(0, 0, 0, 0), PixicObjectType.AIR, locX, locY);
	}
	
	public void onFrameRender() {
		super.onFrameRender();
	}
}
