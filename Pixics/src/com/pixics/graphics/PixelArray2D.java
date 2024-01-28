package com.pixics.graphics;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pixics.main.Logger;

public class PixelArray2D {
	private Logger log = new Logger();
	private List<PixelArray> array2d = null;
	private int sizeY = 0;
	
	public PixelArray2D(int sizeX, int sizeY) {
		if (sizeX < 0) sizeX = 0;
		if (sizeY < 0) sizeY = 0;
		array2d = Collections.synchronizedList(new ArrayList<PixelArray>(sizeX));
		initializeArrays(sizeY);
		this.sizeY = sizeY;
	}
	
	/**
	 * Fills array with identical deep copies of the given Pixel p
	 * 
	 * @param p
	 */
	public synchronized void fill(Pixel p) {
		for (int x = 0; x < array2d.size(); x++) {
			array2d.get(x).fill(p);
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
	
	public synchronized void setPixel(int locX, int locY, Pixel p) {
		if (locX >= 0 && locX < array2d.size() && locY < sizeY) {
			array2d.get(locX).setPixel(p, locY);
		}
	}
	
	public synchronized Pixel get(int locX, int locY) {
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
				array2d.add(new PixelArray(sizeY));
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
			array2d.get(i).resize(sizeY, true);
		}
	}
	
	public synchronized void resize(int sizeX, int sizeY, boolean suppressWarning) {
		resizeX(sizeX, suppressWarning);
		resizeY(sizeY, suppressWarning);
	}
	
	public synchronized Pixel[][] getArray() {
		Pixel[][] a = new Pixel[array2d.size()][sizeY];
		for (int x = 0; x < array2d.size(); x++) {
			a[x] = array2d.get(x).getArray();
		}
		return a;
	}
	
	/**
	 * This will reset the entire pixel array, wiping all previous data
	 * 
	 * @param pixelArray
	 */
	public synchronized void setPixels(Pixel[][] pixelArray) {
		if (pixelArray == null) {
			fill(new Pixel(Pixel.DEFAULT_COLOR));
			return;
		}
		
		int sizeY = pixelArray[0] == null ? 0 : pixelArray[0].length;
		array2d = Collections.synchronizedList(new ArrayList<PixelArray>(pixelArray.length));
		initializeArrays(sizeY);
		this.sizeY = sizeY;
	}
	
	private synchronized void initializeArrays(int sizeY) {
		for (int i = 0; i < array2d.size(); i++) {
			array2d.set(i, new PixelArray(sizeY));
		}
	}
}
