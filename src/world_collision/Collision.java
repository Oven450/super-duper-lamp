package world_collision;

public class Collision {
	
	double x, y, progress;
	WorldCollisionSegment seg;
	
	public Collision(double x, double y, WorldCollisionSegment seg, double progress) {
		this.x = x;
		this.y = y;
		this.seg = seg;
		this.progress = progress;
	}
	
}
