package world_collision;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import main_app.Handler;


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
	
	public double getSlope() {
		return (y2 - y1) / (x2 - x1);
	}
	
	public double getYInt() {
		return getSlope() * -x1 + y1;
	}
	
	public Point2D getPoint01FromPoint(double x, double y, double fromX, double fromY) {
		double c = Math.sqrt(.1 / (Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2)));
		double a = -c * (y2 - y1);
		double b = c * (x2 - x1);
		double yOnLine = y1 + ((fromX - x1) / (x2 - x1)) * (y2 - y1);
		double coef  = -1;
		if (yOnLine > fromY) {
			coef = -b / Math.abs(b);
		} else {
			coef = b / Math.abs(b);
		}
		a *= coef;
		b *= coef;
		return new Point2D.Double(x + a, y + b);
	}
	
	public Point2D getCollisionPoint(MoveVector mv) {
		double a, b, d, e, f, g, h, i;
		d = mv.x2 - mv.x1;
		e = -(x2 - x1);
		f = x1 - mv.x1;
		g = mv.y2 - mv.y1;
		h = -(y2 - y1);
		i = y1 - mv.y1;
		double det = (e* g - d * h);
		if (Double.isNaN(det)) {
			return null;
		}
		a = -h / det * f + e / det * i;
		b = g / det * f + -d / det * i;
		double newX, newY;
		if (a < 0 || a > 1 || b < 0 || b > 1) {
			//System.out.println("Returning null");
			return null;
		}
		newX = x1 + b * (x2 - x1);
		newY = y1 + b * (y2 - y1);
		if (Double.isNaN(newX) || Double.isNaN(newY)) {
			return null;
		}
		return (new Point2D.Double(newX, newY));
	}
	
}
