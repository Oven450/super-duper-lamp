import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Handler {
	
	LampPanel panel;

	private double baseX, baseY;
	private int mouseX, mouseY;

	private boolean mouseLeftDown;
	private boolean mouseMiddleDown;
	private boolean mouseRightDown;

	// Publicly accessible ArrayLists of the keys down in the current tick, the
	// points that the mouse has been pressed at since the last tick, and the
	// points where the mouse has been released at since the last tick

	public ArrayList<Integer> keysDown = new ArrayList<Integer>();
	public ArrayList<Point> clickPoints = new ArrayList<Point>();
	public ArrayList<Point> releasePoints = new ArrayList<Point>();

	public Handler(LampPanel panel) {
		this.panel = panel;
		
		// Instantiate all game objects here

	}

	public void keyPressed(int keyCode) {
		if (!keysDown.contains(keyCode)) {
			keysDown.add(keyCode);
		}
	}

	public void keyReleased(int keyCode) {
		if (keysDown.contains(keyCode)) {
			keysDown.remove((Integer) keyCode);
		}
	}

	public void mouseLeftClicked(int x, int y) {
		mouseLeftDown = true;
		clickPoints.add(new Point(x, y));
	}

	public void mouseRightClicked(int x, int y) {
		mouseRightDown = true;
	}

	public void mouseMiddleClicked(int x, int y) {
		mouseMiddleDown = true;
	}

	public void mouseLeftReleased(int x, int y) {
		mouseLeftDown = false;
		releasePoints.add(new Point(x, y));
	}

	public void mouseRightReleased(int x, int y) {
		mouseRightDown = false;
	}

	public void mouseMiddleReleased(int x, int y) {
		mouseMiddleDown = false;
	}

	public void update() {
		for (int i = 0; i < clickPoints.size(); i++) {
			Point click = clickPoints.get(i);
			// Call mouseClicked on game objects at these points
			
		}
		clickPoints = new ArrayList<Point>();
		for (int i = 0; i < releasePoints.size(); i++) {
			Point release = releasePoints.get(i);
			// Call mouseReleased on game objects at these points

		}
		releasePoints = new ArrayList<Point>();
		
		// Update all game objects here
		

	}
	
	public Point getMouseLoc() {
		return new Point(mouseX, mouseY);
	}

	public void mouseMoved(int x, int y) {
		this.mouseX = x;
		this.mouseY = y;
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
	
	public void draw(Graphics g) {
		// Draw all game objects here
		
		
	}
}
