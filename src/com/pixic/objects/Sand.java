package com.pixic.objects;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import com.pixic.graphics.Screen;

public class Sand extends PixicObject {
	
	public static final int COLOR = 0xFFFBC02D;
	public static final int ALLOWED_RGB_VARIATION = 40;
	public static final long FALL_RATE_MS = 1000L / 70L; // Represents how many MS before each fall update
	
	private static int lastColor = 0xFFBBCCDD;
	
	private long sigmaTick = 0L;
	
	public Sand(Screen parentScreen, int pixel, int location) {
		super(parentScreen, pixel, PixicObjectType.SAND, location);
	}
	
	public Sand(Screen parentScreen, int location) {
		super(parentScreen, getRainbowSandColor(), PixicObjectType.SAND, location);
	}
	
	public static int getVariedSandColor() {
		int r = ThreadLocalRandom.current().nextInt(ALLOWED_RGB_VARIATION);
		int g = ThreadLocalRandom.current().nextInt(ALLOWED_RGB_VARIATION);
		int b = ThreadLocalRandom.current().nextInt(ALLOWED_RGB_VARIATION);
		int rgbVariationAmount = (255 << 24) | (r << 16) | (g << 8) | b;
		
		return COLOR | rgbVariationAmount;
	}
	
	public synchronized static int getRainbowSandColor() {
		if (ThreadLocalRandom.current().nextInt(31) == 0) {
			int r = ((lastColor >> 16) & 0xFF);
			int g = ((lastColor >> 8) & 0xFF);
			int b = (lastColor & 0xFF);
			float[] hsl = rgbToHsl(r, g, b);
			
			// Adjust the hue
	        float hueIncrement = ThreadLocalRandom.current().nextFloat(0.003f);
	        hsl[0] = (hsl[0] + hueIncrement);
	
	        // Convert back to RGB
	        lastColor = hslToRgb(hsl[0], hsl[1], hsl[2]);
		}
        
        return lastColor;
	}
	
	/**
	 * Meant to be overridden by child classes
	 * 
	 * @param msSinceLastRender
	 */
	public void onRender(long msSinceLastRender) {
		
	}
	
	// TODO - perhaps have a trigger for pixics to let the above pixics know if they move
	//        or change in some way so that onTick doesn't have to check this all the time.
	
	/**
	 * Meant to be overridden by child classes
	 * 
	 * @param msSinceLastRender
	 */
	public void onTick(long msSinceLastRender) {
		sigmaTick += msSinceLastRender;
		
		if (sigmaTick >= FALL_RATE_MS) {
			sigmaTick -= FALL_RATE_MS;
			
			PixicObject objBelow = parentScreen.getRelativePixic(this, 0, 1);
			if (objBelow != null && objBelow.objectType == PixicObjectType.AIR) {
				parentScreen.swap(this, objBelow);
				return;
			}
			
			PixicObject objLeft = parentScreen.getRelativePixic(this, -1, 1);
			PixicObject objRight = parentScreen.getRelativePixic(this, 1, 1);
			
			if (objLeft != null && objLeft.objectType == PixicObjectType.AIR) {
				if (objRight != null && objRight.objectType == PixicObjectType.AIR) {
					if (ThreadLocalRandom.current().nextBoolean()) {
						parentScreen.swap(this, objRight);
					} else {
						parentScreen.swap(this, objLeft);
					}
				} else {
					parentScreen.swap(this, objLeft);
				}
				return;
			}
			
			if (objRight != null && objRight.objectType == PixicObjectType.AIR) {
				parentScreen.swap(this, objRight);
			}
		}
	}
	
	private static float[] rgbToHsl(int r, int g, int b) {
        float[] hsl = new float[3];
        Color.RGBtoHSB(r, g, b, hsl);
        return hsl;
    }

    private static int hslToRgb(float h, float s, float l) {
        return Color.HSBtoRGB(h, s, l);
    }
}
