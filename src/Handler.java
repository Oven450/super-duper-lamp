import java.awt.Graphics;
import java.awt.Point;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Handler {

	LampPanel panel;

	int mouseX, mouseY;
	
	boolean mouseMovedSinceLast = false;
	

	boolean mouseLeftDown;
	boolean mouseMiddleDown;
	boolean mouseRightDown;

	// ArrayLists of the keys down in the current tick, the
	// points that the mouse has been pressed at since the last tick, and the
	// points where the mouse has been released at since the last tick

	ArrayList<Integer> keysDown = new ArrayList<Integer>();
	ArrayList<Point> clickPoints = new ArrayList<Point>();
	ArrayList<Point> releasePoints = new ArrayList<Point>();
	
	Handler currHandler;


	BufferedImageLoader imageLoader;
	
	MainMenu menu;

	public Handler(LampPanel panel) {
		this.panel = panel;
		this.imageLoader = new BufferedImageLoader();
		// Instantiate all game objects here

		menu = new MainMenu (this);
	}

	public void keyPressed(int keyCode) {
		if (currHandler != null) {
			currHandler.keyPressed(keyCode);
		}
		if (!keysDown.contains(keyCode)) {
			keysDown.add(keyCode);
		}
	}

	public void keyReleased(int keyCode) {
		if (currHandler != null) {
			currHandler.keyReleased(keyCode);
		}
		if (keysDown.contains(keyCode)) {
			keysDown.remove((Integer) keyCode);
		}
	}

	public void mouseLeftClicked(int x, int y) {
		if (currHandler != null) {
			currHandler.mouseLeftClicked(x, y);
		}
		mouseLeftDown = true;
		clickPoints.add(new Point(x, y));
	}

	public void mouseRightClicked(int x, int y) {
		if (currHandler != null) {
			currHandler.mouseRightClicked(x, y);
		}
		mouseRightDown = true;
	}

	public void mouseMiddleClicked(int x, int y) {
		if (currHandler != null) {
			currHandler.mouseMiddleClicked(x, y);
		}
		mouseMiddleDown = true;
	}

	public void mouseLeftReleased(int x, int y) {
		if (currHandler != null) {
			currHandler.mouseLeftReleased(x, y);
		}
		mouseLeftDown = false;
		releasePoints.add(new Point(x, y));

	}

	public void mouseRightReleased(int x, int y) {
		if (currHandler != null) {
			currHandler.mouseRightReleased(x, y);
		}
		mouseRightDown = false;
	}

	public void mouseMiddleReleased(int x, int y) {
		if (currHandler != null) {
			currHandler.mouseMiddleReleased(x, y);
		}
		mouseMiddleDown = false;
	}

	public void update() {
		for (int i = 0; i < clickPoints.size(); i++) {
			Point click = clickPoints.get(i);
			// Call mouseClicked on game objects at these points
			if (currHandler != null) {
				currHandler.mouseLeftClicked((int) click.getX(), (int) click.getY());
			} else {
				menu.mouseClicked(click);
			}
			System.out.println(click);
		}
		clickPoints = new ArrayList<Point>();
		for (int i = 0; i < releasePoints.size(); i++) {
			Point release = releasePoints.get(i);
			// Call mouseReleased on game objects at these points

		}
		releasePoints = new ArrayList<Point>();

		// Update all game objects here	
		
		if (currHandler != null) {
			currHandler.update();
		} else {
			if (mouseMovedSinceLast) {
				menu.mouseMoved(new Point(this.mouseX, this.mouseY));
			}
		}
		
		mouseMovedSinceLast = false;

	}

	public Point getMouseLoc() {
		return new Point(mouseX, mouseY);
	}

	public void mouseMoved(int x, int y) {
		if (currHandler != null) {
			currHandler.mouseMoved(x, y);
		}
		
		this.mouseX = x;
		this.mouseY = y;
		mouseMovedSinceLast = true;
	}

	public boolean isMouseLeftDown() {
		return mouseLeftDown;
	}

	public boolean isMouseMiddleDown() {
		return mouseMiddleDown;
	}

	public boolean isMouseRightDown() {
		return mouseRightDown;
	}
	
	public boolean keyDown(int keycode) {
		// Called with KeyEvent.VK_[keyname]
		
		if (keysDown.contains(keycode)) {
			return true;
		}
		
		return false;
	}

	public void draw(Graphics g) {
		// Draw all game objects here

		
		if (currHandler != null) {
			currHandler.draw(g);
		} else {
			menu.draw(g);
		}
	}
	
	public BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try {
			image = imageLoader.loadImage(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;

	}
	
	public void startGame() {
		this.currHandler = new GameHandler(panel);
	}
	
	public LampPanel getPanel() {
		return panel;
	}
	

}
