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
	
	private double xvel;
	private double yvel;
	private double jumpYVel = -20;
	private MoveVector drawMV;
	
	public Player (Handler handler) {
		this.handler = handler;
		//this.spritesheet = handler.loadImage("/player.png");
		
		gameState = STANDING;
		facing = RIGHT;
		this.xvel = 0;
		this.yvel = 0;
		drawMV = new MoveVector(0, 0, 1, 1);
		
	}
	
	public void update() {
		if (handler.keyDown(KeyEvent.VK_A)){
			facing = LEFT;
			xvel = -5;
		} else if (handler.keyDown(KeyEvent.VK_D)){
			facing = RIGHT;
			xvel = 5;
		} else {
			xvel = 0;
		}
		if(yvel == 0 && gameState != JUMPING){
			gameState = WALKING;
		}
		if(handler.keyDown(KeyEvent.VK_W)&& gameState != JUMPING){
			yvel = jumpYVel;
			gameState = JUMPING;
			
		}
		//if(gameState == JUMPING){
			yvel += 1;
		//}
		MoveVector mv = new MoveVector (this.x + 10, this.y + 40, this.x + 10 + xvel, this.y + 40 + yvel);
		MoveVector rmv = ((GameHandler) handler).getWorld().testCollision(mv);
		if (rmv == null) {
			drawMV = mv;
			y += yvel;
			x += xvel;
		} else {
			if (rmv.y2 - rmv.y1 != yvel) {
				if (yvel > 0) {
					gameState = STANDING;
				}
				yvel = 0;
			}
			if (rmv.x2 - rmv.x1 != xvel) {
				xvel = 0;
			}
			drawMV = rmv;
			x += rmv.x2 - rmv.x1;
			y += rmv.y2 - rmv.y1;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect((int)x, (int)y, 20, 40);
		//this.drawMV.draw(g);
		(new MoveVector (this.x + 10, this.y + 40, this.x + 10.0001 + xvel, this.y + 40 + yvel)).draw(g);
	}

}
