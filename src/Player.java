import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public class Player {
	
	Handler handler;
	BufferedImage spritesheet1;
	BufferedImage spritesheet2;
	BufferedImage currImage;
	Weapon weapon;
	
	// Current game states for the player
	
	private int gameState;
	public static final int STANDING = 0;
	public static final int WALKING = 1;
	public static final int JUMPING = 2;
	public static final int ATTACKING =3;
	// The current direction the player is facing
	private int ticksTillNextFrame;
	
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
		this.spritesheet1 = handler.loadImage("/player.png");
		this.spritesheet2 = handler.loadImage("/player2.png");
		this.weapon = new Weapon(handler, 0);
		gameState = STANDING;
		facing = RIGHT;
		this.xvel = 0;
		this.yvel = 0;
		drawMV = new MoveVector(0, 0, 1, 1);
		currImage = spritesheet1;
		ticksTillNextFrame = 9;
	}
	
	public void update() {
		if (ticksTillNextFrame == 0) {
			if (currImage.equals(spritesheet1)) {
				currImage = spritesheet2;
			} else {
				currImage = spritesheet1;
			}
			ticksTillNextFrame = 9;
		} else {
			ticksTillNextFrame--;
		}
		if(handler.keyDown(KeyEvent.VK_SPACE) && gameState != ATTACKING){
			attack();
		}
		if (handler.keyDown(KeyEvent.VK_A)){
			facing = LEFT;
			xvel = -15;
		} else if (handler.keyDown(KeyEvent.VK_D)){
			facing = RIGHT;
			xvel = 15;
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
			yvel += 1.3;
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
		g.setColor(new Color(0, 255, 255, 50));
		if (facing == RIGHT) {
			((Graphics2D) g).drawImage(currImage, (int) 512 - 16, (int) 288 - 27, (int) 512 + 16, (int) 288 + 27, 0, 0, 14, 27, null);
		} else {
			((Graphics2D) g).drawImage(currImage, (int) 512 - 16, (int) 288 - 27, (int) 512 + 16, (int) 288 + 27, 14, 0, 0, 27, null);
		}
		//g.fillRect((int)x, (int)y, 20, 40);
		//this.drawMV.draw(g);
		//(new MoveVector (this.x + 10, this.y + 40, this.x + 10.0001 + xvel, this.y + 40 + yvel)).draw(g);
	}
	public void attack(){
		BufferedImage attackImages;
		if(gameState != JUMPING){
			if(facing == LEFT){
				attackImages = weapon.attackingSpritesheet.getSubimage(0, weapon.weaponType*3, 64*5, 64);//64 pixel images as test
			} else{
				attackImages = weapon.attackingSpritesheet.getSubimage(0, weapon.weaponType*3+1, 64*5, 64);
			}
		} else{
			attackImages = weapon.attackingSpritesheet.getSubimage(0, weapon.weaponType*3 + 2, 64*5, 64);
		}
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}


