package com.pixic.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import com.pixic.main.Engine;
import com.pixic.objects.PixicObject;
import com.pixic.objects.PixicObjectType;
import com.pixic.objects.Sand;

public class Screen extends Canvas {
	
	private static final long serialVersionUID = 413692066054408408L;
	private int width, height, pixicWidth, pixicHeight, pixicScale;
	private int fpsCounter, fps;
	private long sigmaTime = 0L;
	private PixicObject[] pixics;
	private Render render;
	
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
	
	public int getFps() {
		return fps;
	}
	
	public Render getRender() {
		return render;
	}
	
	public void render(long deltaTime) {
		sigmaTime += deltaTime;
		fpsCounter++;
		
		if (sigmaTime >= 1000L) {
			fps = fpsCounter;
			fpsCounter = 0;
			sigmaTime = 0L;
		}
		
		BufferStrategy strat = this.getBufferStrategy();
		if (strat == null) {
			createBufferStrategy(2);
			return;
		}
		
		
		
		for (int i = 0; i < pixics.length; i++) {
			pixics[i].onRender(0L);
		}
		
		Graphics g = strat.getDrawGraphics();
		g.drawImage(render, 0, 0, width, height, null);
		g.dispose();
		strat.show();
	}
	
	private void init() {
		render = new Render(pixicWidth, pixicHeight);
		Random r = new Random();
		
		for (int i = 0; i < pixics.length; i++) {
			pixics[i] = new PixicObject(this, Sand.getVariedSandColor(), PixicObjectType.SAND);
		}
		
		render.draw(pixics);
	}
}
