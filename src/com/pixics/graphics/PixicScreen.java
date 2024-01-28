package com.pixics.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JPanel;

import com.pixics.main.Engine;
import com.pixics.main.Logger;
import com.pixics.objects.PixicArray2D;
import com.pixics.objects.PixicObject;
import com.pixics.objects.PixicObjectType;

public class PixicScreen extends JPanel {
	
	private static final long serialVersionUID = -5409316679593494707L;
	private static DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
	private static Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
	
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
		
		screenPixics = new PixicArray2D(this, width, height);
		setScreenSize(width, height);
		setPixelScale(pixicScale);
		this.setVisible(true);
		this.setSize(getActualPixelWidth(), getActualPixelHieght());
		
		Engine.getInstance().subscribe(this);
	}
	
	public PixicObject getPixicObjectRelativeTo(int locX, int locY, int offsetX, int offsetY) {
		int newLocX = locX + offsetX;
		int newLocY = locY + offsetY;
		
		if (newLocX < 0 || newLocX >= screenPixics.sizeX()) {
			return null;
		} else if (newLocY < 0 || newLocY >= screenPixics.sizeY()) {
			return null;
		}
		
		return screenPixics.get(newLocX, newLocY);
	}
	
	/**
	 * Takes the location stored in the PixicObject given and uses that on this PixicScreen
	 * object to find the object being searched for.
	 * 
	 * @param obj
	 * @param offsetX
	 * @param offsetY
	 * @return
	 */
	public PixicObject getPixicObjectRelativeTo(PixicObject obj, int offsetX, int offsetY) {
		int newLocX = obj.getLocX() + offsetX;
		int newLocY = obj.getLocY() + offsetY;
		
		if (newLocX < 0 || newLocX >= screenPixics.sizeX()) {
			return null;
		} else if (newLocY < 0 || newLocY >= screenPixics.sizeY()) {
			return null;
		}
		
		return screenPixics.get(newLocX, newLocY);
	}
	
	/**
	 * Replaces the PixicObject at the new location with the one at the old.
	 * The PixicObject that was at the old location is now replaced with PixicObject.DEFAULT_OBJECT_TYPE
	 * 
	 * @param oldX
	 * @param oldY
	 * @param newX
	 * @param newY
	 */
	public synchronized void replacePixic(int oldX, int oldY, int newX, int newY) {
		screenPixics.setPixic(newX, newY, screenPixics.get(oldX, oldY));
		screenPixics.setPixic(oldX, oldY, PixicObject.DEFAULT_COLOR, PixicObject.DEFAULT_OBJECT_TYPE);
	}
	
	/**
	 * Replaces the PixicObject at the new location with the one at the old.
	 * The PixicObject that was at the old location is now replaced with PixicObject.DEFAULT_OBJECT_TYPE
	 * 
	 * Takes the location stored in the PixicObject given and replaces the PixicObject on
	 * this screen at that location.
	 * 
	 * @param obj
	 * @param offsetX
	 * @param offsetY
	 */
	public synchronized void replacePixic(PixicObject obj, int offsetX, int offsetY) {
		replacePixic(obj.getLocX(), obj.getLocY(), obj.getLocX() + offsetX, obj.getLocY() + offsetY);
	}
	
	/**
	 * Swaps PixicObjects at loc1 and loc2
	 * 
	 * @param locX1
	 * @param locY1
	 * @param locX2
	 * @param locY2
	 */
	public synchronized void swapPixic(int locX1, int locY1, int locX2, int locY2) {
		PixicObject obj = screenPixics.get(locX1, locY1);
		
		screenPixics.setPixic(locX1, locY1, screenPixics.get(locX2, locY2));
		screenPixics.setPixic(locX2, locY2, obj);
	}
	
	/**
	 * Swaps PixicObjects at loc1 and loc2
	 * 
	 * Takes the location stored in the PixicObject given and swaps the PixicObject on
	 * this screen at that location.
	 * 
	 * @param obj
	 * @param locX2
	 * @param locY2
	 */
	public synchronized void swapPixic(PixicObject obj, int offsetX, int offsetY) {
		swapPixic(obj.getLocX(), obj.getLocY(), obj.getLocX() + offsetX, obj.getLocY() + offsetY);
	}
	
	/**
	 * Swaps PixicObjects at loc1 and loc2
	 * 
	 * Takes the locations stored in the PixicObjects given and swaps the PixicObjects on
	 * this screen at those locations.
	 * 
	 * @param obj
	 * @param obj2
	 */
	public synchronized void swapPixic(PixicObject obj, PixicObject obj2) {
		swapPixic(obj.getLocX(), obj.getLocY(), obj2.getLocX(), obj2.getLocY());
	}
	
	/**
	 * Generally should only be called by the Engine class
	 */
	public void onFrameRender() {
		PixicObject[][] pixics = screenPixics.getArray();
		for (int x = 0; x < pixics.length; x++) {
			for (int y = 0; y < pixics[0].length; y++) {
				pixics[x][y].onFrameRender();
			}
		}
	}
	
	/**
	 * Generally should only be called by the Engine class
	 */
	public void onEngineCycle() {
		PixicObject[][] pixics = screenPixics.getArray();
		for (int x = 0; x < pixics.length; x++) {
			for (int y = 0; y < pixics[0].length; y++) {
				pixics[x][y].onEngineCycle();
			}
		}
	}
	
	public synchronized void fill(Color c, PixicObjectType objectType) {
		screenPixics.fill(c, objectType);
	}
	
	public synchronized void setPixic(int locX, int locY, Color c, PixicObjectType objectType) {
		screenPixics.setPixic(locX, locY, c, objectType);
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
        		g.setColor(screenPixics.get(x, y).getColor());
        		g.fillRect(x * pixicScale, y * pixicScale, pixicScale, pixicScale);
        		
        		g.setColor(Color.BLUE);
        		g.drawString("FPS: " + Engine.getFps(), 20, 20);
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
	
	public static int getTrueMonitorWidth() {
		return dm.getWidth();
	}
	
	public static int getTrueMonitorHeight() {
		return dm.getHeight();
	}
	
	public static Dimension getTrueMonitorSize() {
		return new Dimension(getTrueMonitorWidth(), getTrueMonitorHeight());
	}
	
	public static int getMonitorWidth() {
		return monitorSize.width;
	}
	
	public static int getMonitorHeight() {
		return monitorSize.height;
	}
	
	public static Dimension getMonitorSize() {
		return monitorSize;
	}
	
	public static double getMonitorSizeToTrueSizeMultiplier() {
		return (double) getTrueMonitorWidth() / (double) getMonitorHeight();
	}
}
