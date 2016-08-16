package world_collision;

import game.GameHandler;

import java.awt.Point;
import java.util.ArrayList;

public class CollisionManager {
	
	GameHandler handler;
	
	// The offsets from the object's center coordinates for each collision point
	ArrayList<Point> offsets = new ArrayList<Point>();
	
	public CollisionManager(GameHandler handler) {
		this.handler = handler;
		this.offsets = new ArrayList<Point>();
	}
	
	public void addOffsetPoint(Point p) {
		offsets.add(p);
	}
	
	public MoveVector testCollision(double x, double y, double xvel, double yvel) {
		Collision[] cols = new Collision[offsets.size()];
		for (int i = 0; i < cols.length; i++) {
			Point p = offsets.get(i);
			// Create MoveVector from offset point
			MoveVector mv = new MoveVector(x + p.getX(), y + p.getY(), x + p.getX() + xvel, y + p.getY() + yvel);
			cols[i] = handler.getWorld().testCollision(mv);
		}
		
		// Find collision with lowest progress
		int index = 0;
		double currMin = 3;

		for (int i = 0; i < cols.length; i++) {
			if (cols[i] != null && cols[i].progress < currMin) {
				currMin = cols[i].progress;
				index = i;
			}
		}
		if (cols[index] == null) {
			return null;
		}
		
		Point p = offsets.get(index);
		MoveVector mv = new MoveVector(x + p.getX(), y + p.getY(), x + p.getX() + xvel, y + p.getY() + yvel);
		
		Collision col = cols[index];
		
		
		double segAngle = col.seg.getAngle();
		double angMult = 0;
		
		double attackAngle1 = Math.abs(mv.getAngle() - col.seg.getAngle());
		double attackAngle2 = Math.abs(mv.getAngle() - (col.seg.getAngle() + 180));
		//System.out.println(segAngle + " " + mv.getAngle() + " : " + attackAngle1 + " " + attackAngle2);
		double newMvAngle = Double.NaN;
		if (attackAngle1 < 55) {
			newMvAngle = segAngle;
			angMult = (90 - attackAngle1) / 90;
		} else if (attackAngle2 < 55) {
			newMvAngle = segAngle + 180;
			angMult = (90 - attackAngle2) / 90;
		} else if (segAngle > 55) {
			newMvAngle = segAngle;
			angMult = (90 - Math.min(attackAngle1, attackAngle2)) / 90;
		} else if (segAngle < -55) {
			newMvAngle = segAngle + 180;
			angMult = (90 - Math.min(attackAngle1, attackAngle2)) / 90;
		}
		
		angMult += .15;
		if (angMult > 1) {
			angMult = 1;
		}
		
		if (Double.isNaN(newMvAngle)) {
			return new MoveVector(x, y, col.x - p.getX(), col.y - p.getY());
		}
		
		double origMag = mv.getMagnitude();
		double newMag = origMag * (1 - col.progress) * angMult;
		double newMvX = Math.cos(Math.toRadians(newMvAngle)) * newMag;
		double newMvY = Math.sin(Math.toRadians(newMvAngle)) * newMag;
		
		
		MoveVector testedMv = testCollision(col.x - p.getX(), col.y - p.getY(), newMvX, newMvY);
		
		if (testedMv == null) {
			return new MoveVector(x, y, col.x + newMvX - p.getX(), col.y + newMvY - p.getY());
		} else {
			return new MoveVector(x, y, testedMv.x2, testedMv.y2);
		}
		
		
	}
	
}
