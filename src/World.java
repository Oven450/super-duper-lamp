import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class World {
	
	Handler handler;
	BufferedImage dispImage;
	BufferedImage collisionMap;
	
	public World(Handler handler) {
		this.handler = handler;
		
		dispImage = handler.loadImage("level.png");
		collisionMap = handler.loadImage("level.png");
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(dispImage, 0, 0, null);
	}
	
}
