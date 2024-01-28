package com.pixics.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pixics.main.Logger;
import com.pixics.graphics.Pixel;
import com.pixics.graphics.PixicScreen;

public class PixicArray {
	private Logger log = new Logger();
	private List<PixicObject> array = null;
	private PixicScreen parentScreen = null;
	
	public PixicArray(PixicScreen parentScreen, int size, int locX) {
		this.parentScreen = parentScreen;
		array = Collections.synchronizedList(new ArrayList<PixicObject>(size));
		initialize(size, locX);
	}
	
	public synchronized void setPixic(int loc, PixicObject p) {
		if (loc >= 0 && loc <= array.size()) {
			array.set(loc, p);
		}
	}
	
	public synchronized void setPixics(PixicObject[] pixelArray, int locX) {
		if (pixelArray == null) {
			fill(Pixel.DEFAULT_COLOR, PixicObject.DEFAULT_OBJECT_TYPE, locX);
			return;
		}
		
		array = Collections.synchronizedList(new ArrayList<PixicObject>(pixelArray.length));
		for (int i = 0; i < pixelArray.length; i++) {
			array.set(i, pixelArray[i]);
		}
	}
	
	public synchronized PixicObject get(int loc) {
		try {
			return array.get(loc);
		} catch (IndexOutOfBoundsException e) {
			log.error("PixelArray > get() - Location out of bounds.", e);
			return null;
		}
	}
	
	public synchronized void append(PixicObject p) {
		array.add(p);
	}
	
	/**
	 * Fills array with identical deep copies of the given Pixel p
	 * 
	 * @param p
	 */
	public synchronized void fill(Color c, PixicObjectType objectType, int locX) {
		for (int i = 0; i < array.size(); i++) {
			array.set(i, PixicObjectFactory.createGenericPixicObject(parentScreen, c, objectType, locX, i));
		}
	}
	
	public synchronized int size() {
		return array.size();
	}
	
	public synchronized void resize(int newSize, int xLoc, boolean suppressWarning) {
		if (newSize == array.size()) return;
		if (newSize < array.size()) {
			if (!suppressWarning) {
				log.warning("PixelArray > resize() - Resizing to a smaller array will permanently delete data.");
			}
			for (int i = newSize; i < array.size(); i++) {
				array.remove(i);
			}
		} else {
			for (int i = array.size(); i < newSize; i++) {
				array.add(PixicObjectFactory.createGenericPixicObject(parentScreen, PixicObject.DEFAULT_COLOR, PixicObject.DEFAULT_OBJECT_TYPE, xLoc, i));
			}
		}
	}	
	
	public synchronized void setParentScreen(PixicScreen parentScreen) {
		this.parentScreen = parentScreen;
	}
	
	public synchronized PixicScreen getParentScreen() {
		return parentScreen;
	}
	
	public synchronized PixicObject[] getArray() {
		PixicObject[] pixicArray = new PixicObject[array.size()];
		return array.toArray(pixicArray);
	}
	
	private synchronized void initialize(int arraySize, int locX) {
		for (int i = 0; i < arraySize; i++) {
			array.add(PixicObjectFactory.createGenericPixicObject(parentScreen, PixicObject.DEFAULT_COLOR, PixicObject.DEFAULT_OBJECT_TYPE, locX, i));
		}
	}
}
