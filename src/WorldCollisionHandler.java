import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;


public class WorldCollisionHandler extends Handler {
	
	WorldCollisionMenu menu;
	WorldCollisionWorld world;
	
	private double scale;
	private double centerX, centerY;
	private double lastMouseX, lastMouseY;
	private boolean overButton;
	private boolean secondClick;
	private double firstClickX;
	private double firstClickY;
	private boolean mouseMovedSinceClick;
	
	public WorldCollisionHandler(LampPanel panel) {
		super(panel);
		
		menu = new WorldCollisionMenu(this);
		world = new WorldCollisionWorld(this);
		
		scale = .99;
		centerX = LampPanel.PWIDTH / 2;
		centerY = LampPanel.PHEIGHT / 2;
		overButton = false;
		secondClick = false;
		mouseMovedSinceClick = false;
	}
	
	@Override
	public void draw(Graphics g) {
		
		if (secondClick) {
			WorldCollisionSegment temp = new WorldCollisionSegment(firstClickX, firstClickY, (world.dispImage.getWidth() / 2 + -(centerX - mouseX) * scale), (world.dispImage.getHeight() / 2 + -(centerY - mouseY) * scale), (Handler) this);
			world.segments.add(temp);
			g.setColor(Color.MAGENTA);
			((Graphics2D) g).setStroke(new BasicStroke(3,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND));
			world.draw(g, scale, scale, centerX, centerY);
			world.segments.remove(temp);
		} else {
			world.draw(g, scale, scale, centerX, centerY);
		}
		
		menu.draw(g);
		//g.setColor(Color.BLACK);
		//g.fillOval((int) centerX - 3, (int) centerY - 3, 6, 6);
	}
	
	@Override
	public void mouseClickedGO(Point p) {
		menu.mouseClicked(p);
		
		
		
	}
	
	@Override
	public void mouseReleasedGO(Point p) {
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		if (!overButton && !mouseMovedSinceClick) {
			if (!secondClick) {
				secondClick = true;
				firstClickX = (world.dispImage.getWidth() / 2 + -(centerX - p.getX()) * scale);
				firstClickY = (world.dispImage.getHeight() / 2 + -(centerY - p.getY()) * scale);
			} else {
				secondClick = false;
				world.addSegment(firstClickX, firstClickY, (world.dispImage.getWidth() / 2 + -(centerX - p.getX()) * scale), (world.dispImage.getHeight() / 2 + -(centerY - p.getY()) * scale));
			}
		}
		mouseMovedSinceClick = false;
	}
	
	@Override
	public void mouseMovedGO(Point p) {
		if (menu.mouseMoved(p)) {
			this.overButton = true;
		} else {
			this.overButton = false;
		}
		if (this.isMouseLeftDown()) {
			mouseMovedSinceClick = true;
			centerX += (p.getX() - lastMouseX) * 1.8;
			centerY += (p.getY() - lastMouseY) * 1.8;
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		} else {
			mouseMovedSinceClick = false;
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		lastMouseX = p.getX();
		lastMouseY = p.getY();
	}

	@Override
	public void updateGO() {
		if (this.keyDown(KeyEvent.VK_A)) {
			centerX -= 10;
		}
		if (this.keyDown(KeyEvent.VK_D)) {
			centerX += 10;
		}
		if (this.keyDown(KeyEvent.VK_W)) {
			centerY -= 10;
		}
		if (this.keyDown(KeyEvent.VK_S)) {
			centerY += 10;
		}
	}
	
	@Override
	public void mouseRotatedGO(int rotation) {
		scale *= Math.pow(1.2, rotation);
	}
	
	public void zoomIn() {
		scale *= 1 / 1.5;
	}
	
	public void zoomOut() {
		scale *= 1.5;
	}

}
