import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class World {
	
	Handler handler;
	BufferedImage dispImage;
	BufferedImage collisionMap;
	
	public static final Color WALL_COL = new Color(255, 19, 4);
	public static final Color PLAT_COL = new Color(0, 255, 0);
	
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
	
	private Color getColorAtPoint(int x, int y) {
		int pix = collisionMap.getRGB(x, y);
		int r = (pix >> 16) & 0xFF;
		int g = (pix >> 8) & 0xFF;
		int b = pix & 0xFF;
		
		return new Color(r, g, b);
	}
	
	
	
}
