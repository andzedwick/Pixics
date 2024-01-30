package com.pixic.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.pixic.graphics.Window;

public class Application {
	
	public static final String TITLE = "Pixics Engine - v Beta.1";

	public static void main(String[] args) {
		new Application();
	}
	
	public Application() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		new Window(screenSize.width, screenSize.height, TITLE);
		Engine.getInstance().start();
	}
}
