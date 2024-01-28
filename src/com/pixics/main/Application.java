package com.pixics.main;

import com.pixics.graphics.PixicScreen;
import com.pixics.graphics.PixicWindow;

public class Application {
	
	private static final String GAME_TITLE = "PIXICS";
	private static final String GAME_VERSION = "beta 1.0";
	private static final String FRAME_TITLE = GAME_TITLE + " - " + GAME_VERSION;
	
	public static void main(String[] args) {
		new Application();
	}
	
	public Application() {
		PixicWindow window = new PixicWindow(PixicScreen.getMonitorWidth(), PixicScreen.getMonitorHeight(), FRAME_TITLE, 10);
		
	}
}
