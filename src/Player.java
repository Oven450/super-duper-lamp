import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
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
	
	private double x = 200;
	private double y = 200;
	
	private double yVel = 10;
	private double initialYVel = yVel;
	
	public Player (Handler handler) {
		this.handler = handler;
		//this.spritesheet = handler.loadImage("/player.png");
		
		gameState = STANDING;
		facing = RIGHT;
	}
	
	public void update() {
		if (handler.keyDown(KeyEvent.VK_A)){
			facing = LEFT;
			x--;
		} else if (handler.keyDown(KeyEvent.VK_D)){
			facing = RIGHT;
			x++;
		} 
		if(yVel == 0 && gameState != JUMPING){
			gameState = WALKING;
		}
		if(handler.keyDown(KeyEvent.VK_W)&& gameState != JUMPING){
			gameState = JUMPING;
			
		}
		if(gameState == JUMPING){
			y -= yVel;
			yVel-=1;
		}
		if(yVel < ((-1)*initialYVel)){
			yVel = initialYVel;
			gameState = STANDING;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect((int)x, (int)y, 20, 40);
		
	}

}
