package com.pixics.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.util.Random;

import javax.swing.JPanel;

import com.pixics.main.Logger;
import com.pixics.objects.PixicArray2D;
import com.pixics.objects.PixicObject;

public class PixicScreen extends JPanel {
	
	private static final long serialVersionUID = -5409316679593494707L;
	private static DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
	
	private final int MIN_PIXIC_SCALE = 1;
	
	private Logger log = new Logger();
	
	private int screenWidth = 0, screenHeight = 0;
	private int minWidth = 0, minHeight = 0;
	private int pixicScale = 1;
	
	private PixicArray2D screenPixics = null;
	
	public PixicScreen(int width, int height) {
		this(width, height, 1);
	}
	
	public PixicScreen(int width, int height, int pixicScale) {
		if (width < minWidth) {
			width = minWidth;
		}
		if (height < minHeight) {
			height = minHeight;
		}
		
		screenPixics = new PixicArray2D(width, height);
		setScreenSize(width, height);
		setPixelScale(pixicScale);
		this.setVisible(true);
		this.setSize(getActualPixelWidth(), getActualPixelHieght());
	}
	
	public synchronized void fill(PixicObject p) {
		screenPixics.fill(p);
	}
	
	public synchronized void setPixic(int locX, int locY, PixicObject p) {
		screenPixics.setPixic(locX, locY, p);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (screenPixics == null) {
			log.debug("Screen > paintComponent() - Returning because screenPixels is null");
			return;
		}
		
        for (int x = 0; x < screenPixics.sizeX(); x++) {
        	for (int y = 0; y < screenPixics.sizeY(); y++) {
        		// g.setColor(screenPixels.get(x, y).getColor());
        		Random r = new Random();
        		g.setColor(new Color(r.nextInt(0, 256), r.nextInt(0, 256), r.nextInt(0, 256)));
        		g.fillRect(x * pixicScale, y * pixicScale, pixicScale, pixicScale);
        	}
        }
    }
	
	public void setScreenWidth(int width) {
		if (width < minWidth) {
			width = minWidth;
		}
		
		if (screenPixics != null) {
			screenPixics.resizeX(width, true);
		} else {
			log.warning("Screen > setWidth() - Unable to resize screen pixel array becuase it is null.");
		}
		
		this.screenWidth = width;
	}
	
	/**
	 * Gets the width of the screen in virtual Pixics pixels
	 */
	public int getScreenWidth() {
		return screenWidth;
	}
	
	/**
	 * Gets the actual width of the screen in real pixels
	 * 
	 * @return
	 */
	public int getActualPixelWidth() {
		return screenWidth * pixicScale;
	}
	
	public void setScreenHeight(int height) {
		if (height < minHeight) {
			height = minHeight;
		}
		
		if (screenPixics != null) {
			screenPixics.resizeY(height, true);
		} else {
			log.warning("Screen > setHeight() - Unable to resize screen pixel array becuase it is null.");
		}
		
		this.screenHeight = height;
	}
	
	/**
	 * Gets the height of the screen in virtual Pixics pixels
	 */
	public int getScreenHeight() {
		return screenHeight;
	}
	
	/**
	 * Gets the actual height of the screen in real pixels
	 * 
	 * @return
	 */
	public int getActualPixelHieght() {
		return screenHeight * pixicScale;
	}
	
	public void setScreenSize(int width, int height) {
		setScreenWidth(width);
		setScreenHeight(height);
	}
	
	/**
	 * Gets the size of the screen in virtual Pixics pixels
	 */
	public Dimension getScreenSize() {
		return new Dimension(screenWidth, screenHeight);
	}
	
	/**
	 * Gets the actual size of the screen in real pixels
	 * 
	 * @return
	 */
	public Dimension getActualPixelSize() {
		return new Dimension(getActualPixelWidth(), getActualPixelHieght());
	}
	
	public void setPixelScale(int scale) {
		if (scale < MIN_PIXIC_SCALE) {
			scale = MIN_PIXIC_SCALE;
		}
		pixicScale = scale;
	}
	
	public int getPixicScale() {
		return pixicScale;
	}
	
	public static int getMonitorWidth() {
		return dm.getWidth();
	}
	
	public static int getMonitorHeight() {
		return dm.getHeight();
	}
	
	public static Dimension getMonitorSize() {
		return new Dimension(getMonitorWidth(), getMonitorHeight());
	}
}
