package com.pixic.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.pixic.main.Log;
import com.pixic.objects.PixicObject;

public class Render extends BufferedImage {
	
	private int[] pixels;
	
	public Render(int width, int height) {
		super(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)getRaster().getDataBuffer()).getData();
	}

	public void draw(int[] pixels) {
		if (pixels.length != this.pixels.length) {
			Log.warning("Render > draw(int[]) - Given pixel array is not the same size as the Render pixel array.");
			return;
		}
		
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}
	
	public void draw(PixicObject[] pixics) {
		if (pixics.length != pixels.length) {
			Log.warning("Render > draw(PixicObject[]) - Given pixel array is not the same size as the Render pixel array.");
			return;
		}
		
		for (int i = 0; i < pixics.length; i++) {
			pixels[i] = pixics[i].getPixel();
		}
	}
	
	public void draw(int x, int y, int pixel) {
		if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
			pixels[x + y * getWidth()] = pixel;
		}
	}
	
	public void draw(int pixelLoc, int pixel) {
		if (pixelLoc >= 0 && pixelLoc < pixels.length) {
			pixels[pixelLoc] = pixel;
		}
	}
	
	public int[] getPixels() {
		return pixels;
	}
}
