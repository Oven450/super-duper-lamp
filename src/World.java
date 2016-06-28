import java.awt.Color;
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
		g.fillRect(10, 10, 28, 48);
	}
	
	private Color getColorAtPoint(int x, int y) {
		int pix = collisionMap.getRGB(x, y);
		int r = (pix >> 16) & 0xFF;
		int g = (pix >> 8) & 0xFF;
		int b = pix & 0xFF;
		
		return new Color(r, g, b);
	}
	
	
	
}
