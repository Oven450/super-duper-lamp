
public class Collision {
	
	private double x, y;
	private int type;
	
	public static final int WALL = 0;
	public static final int PLATFORM = 1;
	
	public Collision(double x, double y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
}
