package com.pixic.main;

import com.pixic.graphics.Window;

public class Application {
	
	public static final String TITLE = "Pixics Engine - v Beta.1";

	public static void main(String[] args) {
		new Application();
	}
	
	public Application() {
		new Window(800, 600, TITLE);
		Engine.getInstance().start();
	}
}
