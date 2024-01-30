package com.pixic.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import javax.swing.event.MouseInputListener;

import com.pixic.main.Engine;
import com.pixic.main.Log;
import com.pixic.objects.Air;
import com.pixic.objects.PixicObject;
import com.pixic.objects.PixicObjectFactory;
import com.pixic.objects.PixicObjectType;

public class Screen extends Canvas implements MouseInputListener, MouseMotionListener {
	
	public final Color BACKGROUND_COLOR = new Color(0xA6FFFF);
	
	private static final long serialVersionUID = 413692066054408408L;
	private int width, height, pixicWidth, pixicHeight, pixicScale;
	private int fpsCounter, fps;
	private int mouseX = 0, mouseY = 0;
	private long sigmaRenderTime = 0L;
	private boolean mousePressed = false;
	private PixicObject[] pixics;
	private Render render;
	
	public Screen(int width, int height, int pixicScale) {
		this.width = width;
		this.height = height;
		this.pixicScale = pixicScale <= 0 ? 1 : pixicScale;
		pixicWidth = this.width / this.pixicScale;
		pixicHeight = this.height / this.pixicScale;
		pixics = new PixicObject[this.pixicWidth * this.pixicHeight];
		init();
		Engine.getInstance().setScreen(this);
	}
	
	public void draw(int x, int y, PixicObjectType objType) {
		if (x >= 0 && x < pixicWidth && y >= 0 && y < pixicHeight) {
			pixics[x + y * pixicWidth] = PixicObjectFactory.createPixic(this, x + y * pixicWidth, objType);
		}
	}
	
	public void draw(int pixelLoc, PixicObjectType objType) {
		if (pixelLoc >= 0 && pixelLoc < pixics.length) {
			pixics[pixelLoc] = PixicObjectFactory.createPixic(this, pixelLoc, objType);
		}
	}
	
	public void draw(PixicObject[] pixics) {
		if (pixics.length != this.pixics.length) {
			Log.warning("Render > draw(PixicObject[]) - Given pixel array is not the same size as the Render pixel array.");
			return;
		}
		
		for (int i = 0; i < pixics.length; i++) {
			this.pixics[i] = pixics[i];
		}
	}
	
	public void drawIfType(int pixelLoc, PixicObjectType objTypeToDraw, PixicObjectType objTypeToReplace) {
		if (pixelLoc >= 0 && pixelLoc < pixics.length && pixics[pixelLoc].getObjectType() == objTypeToReplace) {
			pixics[pixelLoc] = PixicObjectFactory.createPixic(this, pixelLoc, objTypeToDraw);
		}
	}
	
	public void drawIfType(int x, int y, PixicObjectType objTypeToDraw, PixicObjectType objTypeToReplace) {
		int pixicLoc = x + y * pixicWidth;
		if (x >= 0 && x < pixicWidth && y >= 0 && y < pixicHeight  && pixics[pixicLoc].getObjectType() == objTypeToReplace) {
			pixics[x + y * pixicWidth] = PixicObjectFactory.createPixic(this, pixicLoc, objTypeToDraw);
		}
	}
	
	public void fillCircle(int x, int y, int r, PixicObjectType objectType) {
		int x1 = x - r;
		int x2 = x + r;
		int y1 = y - r;
		int y2 = y + r;
		
		if (x1 >= pixicWidth || x2 < 0) return;
		if (y1 >= pixicHeight || y2 < 0) return;
		
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (Math.pow((double)(i - x), 2.0) + Math.pow((double)(j - y), 2.0) <= Math.pow((double)r, 2.0)) {
					int pixicLocation = i + j * pixicWidth;
					if (pixicLocation >= 0 && pixicLocation < pixics.length) {
						pixics[pixicLocation] = PixicObjectFactory.createPixic(this, pixicLocation, objectType);
					}
				}
			}
		}
	}
	
	public void fillCircleIfType(int x, int y, int r, PixicObjectType objTypeToDraw, PixicObjectType objTypeToReplace) {
		int x1 = x - r;
		int x2 = x + r;
		int y1 = y - r;
		int y2 = y + r;
		
		if (x1 >= pixicWidth || x2 < 0) return;
		if (y1 >= pixicHeight || y2 < 0) return;
		
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (Math.pow((double)(i - x), 2.0) + Math.pow((double)(j - y), 2.0) <= Math.pow((double)r, 2.0)) {
					int pixicLocation = i + j * pixicWidth;
					if (pixicLocation >= 0 && pixicLocation < pixics.length && pixics[pixicLocation].getObjectType() == objTypeToReplace) {
						pixics[pixicLocation] = PixicObjectFactory.createPixic(this, pixicLocation, objTypeToDraw);
					}
				}
			}
		}
	}
	
	public PixicObject getRelativePixic(PixicObject referenceObj, int offX, int offY) {
		int loc = getRelativePixicLocation(referenceObj, offX, offY);
		if (loc >= 0 && loc < pixics.length) {
			return pixics[loc];
		}
		
		return null;
	}
	
	public PixicObject getRelativePixic(PixicObject referenceObj, int offset) {
		int loc = getRelativePixicLocation(referenceObj, offset);
		
		if (loc >= 0 && loc < pixics.length) {
			return pixics[loc];
		}
		
		return null;
	}
	
	/**
	 * Does not verify if the object at the location exists
	 * 
	 * @param referenceObj
	 * @param offset
	 * @return
	 */
	public int getRelativePixicLocation(PixicObject referenceObj, int offset) {
		return referenceObj.getLocation() + offset;
	}
	
	/**
	 * Does not verify if the object at the location exists
	 * 
	 * @param referenceObj
	 * @param locX
	 * @param locY
	 * @return
	 */
	public int getRelativePixicLocation(PixicObject referenceObj, int offX, int offY) {
		//System.out.println("Obj Loc: " + referenceObj.getLocation());
		//System.out.println("Total Offset: " + (offX + offY * pixicWidth));
		return referenceObj.getLocation() + (offX + offY * pixicWidth);
	}
	
	public boolean isPixicLocationValid(int location) {
		if (location < 0 || location >= pixics.length) return false;
		
		return true;
	}
	
	/**
	 * Swaps based off the location saved in the two objects
	 * 
	 * @param deltaTime
	 */
	public void swap(PixicObject obj1, PixicObject obj2) {
		swap(obj1.getLocation(), obj2.getLocation());
	}
	
	/**
	 * Swaps PixicObjects at given locations
	 * 
	 * @param locX1
	 * @param locY1
	 * @param locX2
	 * @param locY2
	 */
	public void swap(int loc1, int loc2) {
		if (loc1 >= 0 && loc1 < pixics.length && loc2 >= 0 && loc2 < pixics.length) {
			PixicObject obj1 = pixics[loc1];
			
			obj1.setLocation(loc2);
			pixics[loc2].setLocation(loc1);
			
			pixics[loc1] = pixics[loc2];
			pixics[loc2] = obj1;
		} else {
			Log.warning("Screen > swap() - One or both locations are invalid, swapping was not executed.");
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getPixicWidth() {
		return pixicWidth;
	}
	
	public int getPixicHeight() {
		return pixicHeight;
	}
	
	public int getPixicScale() {
		return pixicScale;
	}
	
	public int getFps() {
		return fps;
	}
	
	public boolean isRenderNull() {
		if (render == null) return true;
		
		return false;
	}
	
	public void render(long deltaTime) {
		sigmaRenderTime += deltaTime;
		fpsCounter++;
		
		if (sigmaRenderTime >= 1000L) {
			fps = fpsCounter;
			fpsCounter = 0;
			sigmaRenderTime -= 1000L;
		}
		
		BufferStrategy strat = this.getBufferStrategy();
		if (strat == null) {
			createBufferStrategy(2);
			return;
		}
		
		for (int x = 0; x < pixicWidth; x++) {
			for (int y = 0; y < pixicHeight; y++) {
				pixics[x + y * pixicWidth].onRender(deltaTime);
			}
		}
		
		render.draw(pixics); // TODO - maybe don't draw all pixels, performance would
		//                             be better if I only update necessary pixels
		
		Graphics g = strat.getDrawGraphics();
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, width, height);
		g.drawImage(render, 0, 0, width, height, null);
		g.dispose();
		strat.show();
	}
	
	public void tick(long deltaTime) {
		for (int x = 0; x < pixicWidth; x++) {
			for (int y = 0; y < pixicHeight; y++) {
				pixics[x + y * pixicWidth].onTick(deltaTime);
			}
		}
		
		if (mousePressed) {
			fillCircleIfType(mouseX / pixicScale, mouseY / pixicScale, 12, PixicObjectType.SAND, PixicObjectType.AIR);
		}
	}
	
	private void init() {
		render = new Render(pixicWidth, pixicHeight);
		
		for (int i = 0; i < pixics.length; i++) {
			pixics[i] = new Air(this, i);
		}
		
		render.draw(pixics);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
}
