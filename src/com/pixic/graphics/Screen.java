package com.pixic.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import com.pixic.main.Engine;
import com.pixic.objects.PixicObject;

public class Screen extends Canvas {
	
	private static final long serialVersionUID = 413692066054408408L;
	private int width, height, pixicWidth, pixicHeight, pixicScale;
	private int[] pixels;
	private PixicObject[] pixics;
	private BufferedImage img;
	
	public Screen(int width, int height, int pixicScale) {
		this.width = width;
		this.height = height;
		this.pixicScale = pixicScale < 0 ? 0 : pixicScale;
		pixicWidth = this.width / this.pixicScale;
		pixicHeight = this.height / this.pixicScale;
		pixics = new PixicObject[this.pixicWidth * this.pixicHeight];
		init();
		Engine.getInstance().setScreen(this);
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
	
	public void render(long deltaTime) {
		BufferStrategy strat = this.getBufferStrategy();
		if (strat == null) {
			createBufferStrategy(2);
			return;
		}
		
		for (int i = 0; i < pixics.length; i++) {
			pixics[i].onRender(0L);
		}
		
		Graphics g = strat.getDrawGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		strat.show();
		
		System.out.println("Rendering");
	}
	
	private void init() {
		img = new BufferedImage(pixicWidth, pixicHeight, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		
		Random r = new Random();
		
		for (int i = 0; i < pixics.length; i++) {
			pixics[i] = new PixicObject(this, pixels[i], PixicObject.DEFAULT_OBJECT_TYPE);
			pixels[i] = r.nextInt(); // TODO - temp
		}
	}
}
