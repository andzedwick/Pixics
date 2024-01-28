package com.pixics.main;

import com.pixics.graphics.Screen;
import com.pixics.graphics.Window;

public class Application {
	
	private static final String GAME_TITLE = "PIXICS";
	private static final String GAME_VERSION = "beta 1.0";
	private static final String FRAME_TITLE = GAME_TITLE + " - " + GAME_VERSION;
	
	public static void main(String[] args) {
		new Application();
	}
	
	public Application() {
		new Window(Screen.getMonitorWidth(), Screen.getMonitorHeight(), FRAME_TITLE, 10);
	}
}
