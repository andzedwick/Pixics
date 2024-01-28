package com.pixics.objects;

import java.awt.Color;

public class Sand extends PixicObject {

	public Sand(Color c) {
		super(c, PixicObjectType.SAND);
	}

	public Sand(int r, int g, int b) {
		super(r, g, b, PixicObjectType.SAND);
	}
	
	public Sand(int r, int g, int b, int a) {
		super(r, g, b, a, PixicObjectType.SAND);
	}
}
