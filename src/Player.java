import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Player {
	
	Handler handler;
	BufferedImage spritesheet;
	
	// Current game states for the player
	
	private int gameState;
	public static final int STANDING = 0;
	public static final int WALKING = 1;
	public static final int JUMPING = 2;
	
	// The current direction the player is facing
	
	private int facing;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	public Player (Handler handler) {
		this.handler = handler;
		this.spritesheet = handler.loadImage("/player.png");
		
		gameState = STANDING;
		facing = RIGHT;
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics g) {
		
	}

}
