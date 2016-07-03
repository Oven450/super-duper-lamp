import java.awt.Color;
import java.awt.Graphics;


public class WorldCollisionSegment {
	
	private double x1, y1, x2, y2;
	private boolean above;
	private boolean below;
	
	private Handler handler;
	
	public WorldCollisionSegment(double startX, double startY, double endX, double endY, Handler handler) {
		System.out.println("New segment created: (" + startX + ", " + startY + ") --> (" + endX + ", " + endY + ")");
		this.x1 = startX;
		this.y1 = startY;
		this.x2 = endX;
		this.y2 = endY;
		above = true;
		below = true;
		this.handler = handler;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}
	
}
