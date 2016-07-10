package world_collision;

import game.GameHandler;

public class CollisionManager {
	
	GameHandler handler;
	
	public CollisionManager(GameHandler handler) {
		this.handler = handler;
	}
	
	public MoveVector testCollision(double x, double y, double xvel, double yvel) {
		MoveVector mv = new MoveVector(x, y + 27, x + xvel, y + 27 + yvel);
		
		MoveVector newMv = handler.getWorld().testCollision(mv);
		
		return newMv;
	}
	
}
