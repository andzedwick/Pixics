package com.pixics.graphics;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import com.pixics.main.Logger;
import com.pixics.objects.PixicObjectType;

public class PixicWindow implements MouseInputListener {
	
	private JFrame frame = null;
	private PixicScreen screen = null;
	private Logger log = new Logger();
	
	private int frameWidth, frameHeight;
	private int pixicWidth, pixicHeight, pixicScale;
	private String frameTitle = "";
	
	public PixicWindow(int pixicWidth, int pixicHeight, int pixicScale, String frameTitle, boolean fullScreen) {
		this.frameTitle = frameTitle;
		this.pixicScale = pixicScale < 0 ? 0 : pixicScale;
		this.pixicWidth = pixicWidth < 0 ? 0 : pixicWidth;
		this.pixicHeight = pixicHeight < 0 ? 0 : pixicHeight;
		frameWidth = this.pixicWidth * this.pixicScale;
		frameHeight = this.pixicHeight * this.pixicScale;
		
		// Make sure if the numbers cause a pixel to be half in and out of the edge
		// of the screen, that this pixel is added to be drawn.
		// TODO - in the future, I should set some draw offsets so this calculation
		//        can allow the screen to draw in the very middle of the frame.
		// TODO - I could also look into resizing somehow?
//		if (this.pixicsWidth * pixicsScale < this.frameWidth) {
//			pixicsWidth += 1;
//		}
//		
//		if (this.pixicsHeight * pixicsScale < this.frameHeight) {
//			pixicsHeight += 1;
//		}
		
		log.info("PixicsScale: " + this.pixicScale);
		log.info("PixicsWidth: " + this.pixicWidth);
		log.info("PixicsHeight: " + this.pixicHeight);
		log.info("FrameWidth: " + this.frameWidth);
		log.info("FrameHeight: " + this.frameHeight);
		
		setupFrame(fullScreen);
		setupScreen();
	}
	
	public void setPixicScreen(PixicScreen screen) {
		this.screen = screen;
	}
	
	public PixicScreen getPixicScreen() {
		return screen;
	}
	
	private void setupFrame(boolean fullScreen) {
		Image applicationIcon = Toolkit.getDefaultToolkit().getImage("src/assets/Pixics_Icon.png");
		frame = new JFrame(frameTitle);
		frame.setSize(frameWidth, frameHeight);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(applicationIcon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if (fullScreen) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		
		frame.addMouseListener(this);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
	private void setupScreen() {
		if (frame == null) return;
		
		screen = new PixicScreen(pixicWidth, pixicHeight, pixicScale);
		screen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		frame.add(screen);
		screen.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (screen == null) return;
		
		int locX = e.getX() / screen.getPixicScale();
		int locY = e.getY() / screen.getPixicScale();
		
		if (locX >= 0 && locX <= screen.getScreenWidth()
				&& locY >= 0 && locY <= screen.getScreenHeight()) {
			screen.setPixic(locX, locY, new Color(204, 179, 148), PixicObjectType.SAND);
			log.info("Clicked!");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
