package world_collision;

import game.GameHandler;

public class CollisionManager {
	
	GameHandler handler;
	
	public CollisionManager(GameHandler handler) {
		this.handler = handler;
	}
	
	public MoveVector testCollision(double x, double y, double xvel, double yvel) {
		MoveVector mv = new MoveVector(x, y, x + xvel, y + yvel);
		
		Collision col = handler.getWorld().testCollision(mv);
		if (col == null) {
			return null;
		}
		
		double segAngle = col.seg.getAngle();
		double angMult = 0;
		
		double attackAngle1 = Math.abs(mv.getAngle() - col.seg.getAngle());
		double attackAngle2 = Math.abs(mv.getAngle() - (col.seg.getAngle() + 180));
		System.out.println(segAngle + " " + mv.getAngle() + " : " + attackAngle1 + " " + attackAngle2);
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
			return new MoveVector(x, y, col.x, col.y);
		}
		
		double origMag = mv.getMagnitude();
		double newMag = origMag * (1 - col.progress) * angMult;
		double newMvX = Math.cos(Math.toRadians(newMvAngle)) * newMag;
		double newMvY = Math.sin(Math.toRadians(newMvAngle)) * newMag;
		MoveVector testedMv = testCollision(col.x, col.y, newMvX, newMvY);
		if (testedMv == null) {
			return new MoveVector(x, y, col.x + newMvX, col.y + newMvY);
		} else {
			return new MoveVector(x, y, testedMv.x2, testedMv.y2);
		}
		
		
	}
	
}
