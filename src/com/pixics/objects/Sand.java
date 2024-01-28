package com.pixics.objects;

import java.awt.Color;

import com.pixics.graphics.PixicScreen;
import com.pixics.main.Engine;

public class Sand extends PixicObject {

	// Speed is in units of updates per second
	private int speed = 5;
	
	public Sand(PixicScreen parentScreen, Color c, int locX, int locY) {
		super(parentScreen, c, PixicObjectType.SAND, locX, locY);
	}

	public Sand(PixicScreen parentScreen, int r, int g, int b, int locX, int locY) {
		super(parentScreen, r, g, b, PixicObjectType.SAND, locX, locY);
	}
	
	public Sand(PixicScreen parentScreen, int r, int g, int b, int a, int locX, int locY) {
		super(parentScreen, r, g, b, a, PixicObjectType.SAND, locX, locY);
	}
	
	public void onFrameRender() {
		super.onFrameRender();
		if (Engine.getCurrentFrame() % speed == 0) {
			PixicObject objBelow = parentScreen.getPixicObjectRelativeTo(this, 0, 1);
			if (objBelow != null && objBelow.getObjectType() == PixicObjectType.AIR) {
				parentScreen.swapPixic(this, objBelow);
			}
		}
	}
}
