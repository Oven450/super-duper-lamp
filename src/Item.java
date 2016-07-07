import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Item {
	
	boolean inWorld;
	
	BufferedImage frame;
	BufferedImage image;
	String name;
	Player player;
	World world;
	Handler handler;
	double x;
	double y;
	double yvel;
	
	public Item(double x, double y, World world, Handler handler) {
		inWorld = true;
		this.x = x;
		this.y = y;
		this.world = world;
		this.handler = handler;
		frame = handler.loadImage("/item_frame.png");
	}
	
	public Item(Player player, Handler handler) {
		inWorld = false;
		this.player = player;
		this.handler = handler;
		frame = handler.loadImage("/item_frame.png");
	}
	
	public void toWorld(World world) {
		inWorld = true;
		this.world = world;
	}
	
	public void toPlayer(Player player) {
		inWorld = false;
		this.player = player;
	}
	
	public void update() {
		if (inWorld) {
			yvel += 1.3;
			MoveVector mv = new MoveVector (this.x + 12, this.y + 24, this.x + 12, this.y + 24 + yvel);
			MoveVector rmv = world.testCollision(mv);
			if (rmv == null) {
				y += yvel;
			} else {
				if (rmv.y2 - rmv.y1 != yvel) {
					yvel = 0;
				}
				x += rmv.x2 - rmv.x1;
				y += rmv.y2 - rmv.y1;
			}
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawImage(frame, LampPanel.PWIDTH / 2 - (int) (((GameHandler) handler).player.getX()  - this.x), LampPanel.PHEIGHT / 2 - (int) (((GameHandler) handler).player.getY() - this.y), null);
		
	}
	
	public void equipDraw(int x, int y, Graphics g, boolean curr) {
		if (curr) {
			g.setColor(Color.YELLOW);
			g.drawRect(x, y, 48, 48);
		}
		g.drawImage(this.image, x, y, null);
		
	}
	
	public void inventoryDraw(int x, int y, Graphics g) {
		((Graphics2D) g).drawImage(this.image, x + 10, y + 6, x + 106, y + 98, 0, 0, 48, 48, null);
		System.out.println(this.getClass() + " " + this.name);
		g.setColor(Color.WHITE);
		g.drawString(this.name, x + 116, y + 30);
	}
	
}
