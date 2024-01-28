package com.pixics.main;

import java.awt.Color;

import com.pixics.graphics.PixicScreen;
import com.pixics.graphics.PixicWindow;
import com.pixics.objects.PixicObjectType;
import com.pixics.objects.Sand;

public class Application {
	
	private static final String GAME_TITLE = "PIXICS";
	private static final String GAME_VERSION = "beta 1.0";
	private static final String FRAME_TITLE = GAME_TITLE + " - " + GAME_VERSION;
	
	public static void main(String[] args) {
		new Application();
	}
	
	// TODO Need to add logic to delete PixicObjects/Pixels from the engine subscribe
	// TODO - better yet, have objects updated by the screen they are associated with
	//        using a screen onRender/onEngineCycle method and if the screen no longer has
	//        the object in storage, then it will be automatically not updated
	
	// TODO For efficiency, update the getArray function in the PixicArray2D, PixicArray, etc
	//      so that the array is stored in memory after being calculated. Then, if an update
	//      occurs, then either the update is directly stored in that memory, or a recalculation
	//      of the entire array is done. This will add a lot of performance, as this method
	//      is called every engine cycle and every frame render
	
	// TODO Add a PixicObjectDataSelector class that is used to send data to the PixicObjectFactory
	//      so that adding new selector info is easier and just means I have to update that object
	//      and references to it.
	public Application() {
		//PixicWindow window = new PixicWindow(PixicScreen.getMonitorWidth(), PixicScreen.getMonitorHeight(), FRAME_TITLE, 10);
		PixicWindow window = new PixicWindow(PixicScreen.getMonitorWidth(), PixicScreen.getMonitorHeight(), FRAME_TITLE, 10, false);
		PixicScreen screen = window.getPixicScreen();
		screen.setPixic(10, 30, new Color(204, 179, 148), PixicObjectType.SAND);
		screen.repaint();
		
		Engine engine = Engine.getInstance();
		engine.start();
	}
}
