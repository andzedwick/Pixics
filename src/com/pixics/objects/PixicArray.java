package com.pixics.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pixics.main.Logger;
import com.pixics.graphics.Pixel;

public class PixicArray {
	private Logger log = new Logger();
	private List<PixicObject> array = null;
	
	public PixicArray(int size) {
		array = Collections.synchronizedList(new ArrayList<PixicObject>(size));
		initialize(size);
	}
	
	public synchronized void setPixic(PixicObject p, int loc) {
		if (loc >= 0 && loc <= array.size()) {
			array.set(loc, p);
		}
	}
	
	public synchronized void setPixics(PixicObject[] pixelArray) {
		if (pixelArray == null) {
			fill(new PixicObject(Pixel.DEFAULT_COLOR, PixicObject.DEFAULT_OBJECT_TYPE));
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
	public synchronized void fill(PixicObject p) {
		for (int i = 0; i < array.size(); i++) {
			array.set(i, new PixicObject(p.getColor(), p.getObjectType()));
		}
	}
	
	public synchronized int size() {
		return array.size();
	}
	
	public synchronized void resize(int newSize, boolean suppressWarning) {
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
				array.add(new PixicObject(Pixel.DEFAULT_COLOR, PixicObject.DEFAULT_OBJECT_TYPE));
			}
		}
	}	
	
	public synchronized PixicObject[] getArray() {
		return (PixicObject[]) array.toArray();
	}
	
	private synchronized void initialize(int arraySize) {
		for (int i = 0; i < arraySize; i++) {
			array.add(new PixicObject(Pixel.DEFAULT_COLOR, PixicObject.DEFAULT_OBJECT_TYPE));
		}
	}
}
