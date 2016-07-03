import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;


public class WorldCollisionSegment {
	
	private double x1, y1, x2, y2;
	

	private boolean above;
	private boolean below;
	
	private Handler handler;
	
	public WorldCollisionSegment(double startX, double startY, double endX, double endY, Handler handler) {
		//System.out.println("New segment created: (" + startX + ", " + startY + ") --> (" + endX + ", " + endY + ")");
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
	
	public double getX1() {
		return x1;
	}

	public double getY1() {
		return y1;
	}

	public double getX2() {
		return x2;
	}

	public double getY2() {
		return y2;
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.000");
		return df.format(x1) + " " + df.format(y1) + " " + df.format(x2) + " " + df.format(y2);
	}
	
}
