import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Handler {

	LampPanel panel;
	World world;

	private int mouseX, mouseY;

	private boolean mouseLeftDown;
	private boolean mouseMiddleDown;
	private boolean mouseRightDown;

	// ArrayLists of the keys down in the current tick, the
	// points that the mouse has been pressed at since the last tick, and the
	// points where the mouse has been released at since the last tick

	private ArrayList<Integer> keysDown = new ArrayList<Integer>();
	private ArrayList<Point> clickPoints = new ArrayList<Point>();
	private ArrayList<Point> releasePoints = new ArrayList<Point>();
	
	BufferedImageLoader imageLoader;

	public Handler(LampPanel panel) {
		this.panel = panel;
		imageLoader = new BufferedImageLoader();

		// Instantiate all game objects here
		world = new World(this);

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
			System.out.println(click);
		}
		clickPoints = new ArrayList<Point>();
		for (int i = 0; i < releasePoints.size(); i++) {
			Point release = releasePoints.get(i);
			// Call mouseReleased on game objects at these points

		}
		releasePoints = new ArrayList<Point>();

		// Update all game objects here
		
		world.update();

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
	
	public boolean keyDown(int keycode) {
		// Called with KeyEvent.VK_[keyname]
		
		if (keysDown.contains(keycode)) {
			return true;
		}
		
		return false;
	}

	public void draw(Graphics g) {
		// Draw all game objects here
		
		world.draw(g);

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
}
