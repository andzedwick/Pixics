package com.pixics.graphics;

import java.awt.Color;

import com.pixics.main.Logger;

public class Pixel {

	public static final Color DEFAULT_COLOR = Color.BLACK;
	
	private Logger log = new Logger();
	private Color color = DEFAULT_COLOR;
	
	public Pixel(Color c) {
		setColor(c);
	}
	
	public Pixel(int r, int g, int b) {
		setColor(r, g, b);
	}
	
	public Pixel(int r, int g, int b, int a) {
		setColor(r, g, b, a);
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void setColor(int r, int g, int b) {
		setColor(r, g, b, 255);
	}
	
	public void setColor(int r, int g, int b, int a) {
		try {
			color = new Color(r, g, b, a);
		} catch (IllegalArgumentException e) {
			log.error("Pixel > setColor() - Input color given is out of bounds:\nr: " + r + ", g: " + g + ", b: " + b + ", a: " + a, e);
		}
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getColorR() {
		return color.getRed();
	}
	
	public int getColorG() {
		return color.getGreen();
	}
	
	public int getColorB() {
		return color.getBlue();
	}
	
	public int getColorA() {
		return color.getAlpha();
	}
}
