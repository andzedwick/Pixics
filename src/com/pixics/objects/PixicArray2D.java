package com.pixics.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pixics.main.Logger;
import com.pixics.graphics.Pixel;
import com.pixics.graphics.PixicScreen;

public class PixicArray2D {
	private Logger log = new Logger();
	private List<PixicArray> array2d = null;
	private int sizeY = 0;
	private PixicScreen parentScreen = null;
	
	public PixicArray2D(PixicScreen parentScreen, int sizeX, int sizeY) {
		this.parentScreen = parentScreen;
		if (sizeX < 0) sizeX = 0;
		if (sizeY < 0) sizeY = 0;
		array2d = Collections.synchronizedList(new ArrayList<PixicArray>(sizeX));
		initializeArrays(sizeY);
		this.sizeY = sizeY;
	}
	
	/**
	 * Fills array with identical deep copies of the given Pixel p
	 * 
	 * @param p
	 */
	public synchronized void fill(Color c, PixicObjectType objectType) {
		for (int x = 0; x < array2d.size(); x++) {
			array2d.get(x).fill(c, objectType, x);
		}
	}
	
	public synchronized Dimension size() {
		return new Dimension(array2d.size(), sizeY);
	}
	
	public synchronized int sizeX() {
		return array2d.size();
	}
	
	public synchronized int sizeY() {
		return sizeY;
	}
	
	public synchronized void setPixic(int locX, int locY, Color c, PixicObjectType objectType) {
		if (locX >= 0 && locX < array2d.size() && locY < sizeY) {
			array2d.get(locX).setPixic(locY, PixicObjectFactory.createGenericPixicObject(parentScreen, c, objectType, locX, locY));
		}
	}
	
	/**
	 * Sets the PixicObject at the given location and updates the PixicObject's location
	 * as well.
	 * 
	 * @param locX
	 * @param locY
	 * @param p
	 */
	public synchronized void setPixic(int locX, int locY, PixicObject p) {
		if (locX >= 0 && locX < array2d.size() && locY < sizeY) {
			array2d.get(locX).setPixic(locY, p);
			p.setLoc(locX, locY);
		}
	}
	
	public synchronized PixicObject get(int locX, int locY) {
		if (locX >= 0 && locX < array2d.size() && locY < sizeY) {
			return array2d.get(locX).get(locY);
		}
		
		log.error("PixelArray2D > get() - LocationX given is out of bounds");
		return null;
	}
	
	public synchronized void resizeX(int sizeX, boolean suppressWarning) {
		if (sizeX < 0) sizeX = 0;
		if (sizeX > array2d.size()) {
			for (int i = array2d.size(); i < sizeX; i++) {
				array2d.add(new PixicArray(parentScreen, sizeY, i));
			}
		} else if (sizeX < array2d.size()) {
			if (!suppressWarning) {
				log.warning("PixelArray2D > resizeX() - Resizing to a smaller array will permanently delete data.");
			}
			for (int i = sizeX; i < array2d.size(); i++) {
				array2d.remove(i);
			}
		}
	}
	
	public synchronized void resizeY(int sizeY, boolean suppressWarning) {
		if (sizeY == this.sizeY) return;
		if (sizeY < 0) sizeY = 0;
		this.sizeY = sizeY;
		
		if (sizeY < this.sizeY && !suppressWarning) {
			log.warning("PixelArray2D > resizeY() - Resizing to a smaller array will permanently delete data.");
		}
		
		for (int i = 0; i < array2d.size(); i++) {
			array2d.get(i).resize(sizeY, i, true);
		}
	}
	
	public synchronized void resize(int sizeX, int sizeY, boolean suppressWarning) {
		resizeX(sizeX, suppressWarning);
		resizeY(sizeY, suppressWarning);
	}
	
	public synchronized PixicObject[][] getArray() {
		PixicObject[][] a = new PixicObject[array2d.size()][sizeY];
		for (int x = 0; x < array2d.size(); x++) {
			a[x] = array2d.get(x).getArray();
		}
		return a;
	}
	
	public synchronized void setParentScreen(PixicScreen parentScreen) {
		this.parentScreen = parentScreen;
	}
	
	public synchronized PixicScreen getParentScreen() {
		return parentScreen;
	}
	
	/**
	 * This will reset the entire pixel array, wiping all previous data
	 * 
	 * @param pixelArray
	 */
	public synchronized void setPixics(PixicObject[][] pixicArray) {
		if (pixicArray == null) {
			fill(Pixel.DEFAULT_COLOR, PixicObject.DEFAULT_OBJECT_TYPE);
			return;
		}
		
		int sizeY = pixicArray[0] == null ? 0 : pixicArray[0].length;
		array2d = Collections.synchronizedList(new ArrayList<PixicArray>(pixicArray.length));
		initializeArrays(sizeY);
		this.sizeY = sizeY;
	}
	
	private synchronized void initializeArrays(int sizeY) {
		for (int i = 0; i < array2d.size(); i++) {
			array2d.set(i, new PixicArray(parentScreen, sizeY, i));
		}
	}
}
