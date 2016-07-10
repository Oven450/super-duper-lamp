package world_collision;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class MoveVector {
	
	public double x1;
	public double x2;
	public double y1;
	public double y2;
	
	public MoveVector(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.y2 = y2;
		this.x2 = x2;
	}
	
	public Rectangle2D.Double getBoundingBox() {
		if (x1 < x2) {
			if (y1 < y2) {
				return new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1);
			} else {
				return new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2);
			}
		} else {
			if (y1 < y2) {
				return new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1);
			} else {
				return new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2);
			}
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}
	
	public double getSlope() {
		return (y2 - y1) / (x2 - x1);
	}
	
	public double getYInt() {
		return getSlope() * -x1 + y1;
	}
	
	public Point2D getPoint01fromPoint(double x, double y) {
		// Not sure about this
		/*if (Point.distance(x, y, x1, y1) < .01) {
			return new Point2D.Double(x1, y1);
		}*/
		double s = getSlope();
		double a = 1 / (100 * Math.sqrt(Math.pow(s, 2) + 1));
		double b = s * a;
		a *= (x1 - x2) / Math.abs(x1 - x2);
		b *= (y1 - y2) / Math.abs(y1 - y2);
		return new Point2D.Double(x + a, y + b);
	}

	public double getProgress(Point2D p) {
		if (x2 - x1 == 0 && y2 - y1 == 0) {
			return 1;
		} else if (x2 - x1 == 0) {
			return (p.getY() - y1) / (y2 - y1);
		}
		return (p.getX() - x1) / (x2 - x1);
		
	}
}
