package com.pixics.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pixics.main.Logger;

public class PixelArray {
	private Logger log = new Logger();
	private List<Pixel> array = null;
	
	public PixelArray(int size) {
		array = Collections.synchronizedList(new ArrayList<Pixel>(size));
		initialize(size);
	}
	
	public synchronized void setPixel(Pixel p, int loc) {
		if (loc >= 0 && loc <= array.size()) {
			array.set(loc, p);
		}
	}
	
	public synchronized void setPixels(Pixel[] pixelArray) {
		if (pixelArray == null) {
			fill(new Pixel(Pixel.DEFAULT_COLOR));
			return;
		}
		
		array = Collections.synchronizedList(new ArrayList<Pixel>(pixelArray.length));
		for (int i = 0; i < pixelArray.length; i++) {
			array.set(i, pixelArray[i]);
		}
	}
	
	public synchronized Pixel get(int loc) {
		try {
			return array.get(loc);
		} catch (IndexOutOfBoundsException e) {
			log.error("PixelArray > get() - Location out of bounds.", e);
			return null;
		}
	}
	
	public synchronized void append(Pixel p) {
		array.add(p);
	}
	
	/**
	 * Fills array with identical deep copies of the given Pixel p
	 * 
	 * @param p
	 */
	public synchronized void fill(Pixel p) {
		for (int i = 0; i < array.size(); i++) {
			array.set(i, new Pixel(p.getColor()));
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
				array.add(new Pixel(Pixel.DEFAULT_COLOR));
			}
		}
	}	
	
	public synchronized Pixel[] getArray() {
		return (Pixel[]) array.toArray();
	}
	
	private synchronized void initialize(int arraySize) {
		for (int i = 0; i < arraySize; i++) {
			array.add(new Pixel(Pixel.DEFAULT_COLOR));
		}
	}
}
