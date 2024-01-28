package com.pixics.objects;

import java.awt.Color;

import com.pixics.graphics.Pixel;

public class PixicObject extends Pixel {
	
	public static final PixicObjectType DEFAULT_OBJECT_TYPE = PixicObjectType.SAND;
	
	private PixicObjectType objectType;
	
	public PixicObject(Color c, PixicObjectType objectType) {
		super(c);
		this.objectType = objectType;
	}
	
	public PixicObject(int r, int g, int b, PixicObjectType objectType) {
		super(r, g, b);
		this.objectType = objectType;
	}
	
	public PixicObject(int r, int g, int b, int a, PixicObjectType objectType) {
		super(r, g, b, a);
		this.objectType = objectType;
	}
	
	public void setObjectType(PixicObjectType objectType) {
		this.objectType = objectType;
	}
	
	public PixicObjectType getObjectType() {
		return objectType;
	}
}
