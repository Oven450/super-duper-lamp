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
		getCollision(150, 150, 400, 200);
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
	
	public Collision getCollision(double startX, double startY, double endX, double endY) {
		long beforeTime = System.nanoTime();
		Color lastColor = null;
		double deltaX = endX - startX;
		double deltaY = endY - startY;
		//System.out.println(deltaX + " " + deltaY);
		int currPixelX = (int)startX;
		int currPixelY = (int)startY;
		while((currPixelX != (int)endX || currPixelY != (int)endY) && getColorAtPoint(currPixelX, currPixelY).equals(Color.BLACK)) {
			//System.out.println("Current pixel: " + currPixelX + " " + currPixelY + "; Current double: " + startX + " " + startY);
			lastColor = getColorAtPoint(currPixelX, currPixelY);
			double xDist = 0;
			double yDist = 0;
			int posX = 0;
			int posY = 0;
			if (deltaX > 0) {
				posX = 1;
			}
			xDist = Math.floor(startX) + posX - startX;
			if (deltaY > 0) {
				posY = 1;
			} 
			yDist = Math.floor(startY) + posY - startY;
			
			double xFrac = 1;
			double yFrac = 1;
			if (deltaX != 0) {
				xFrac = xDist / deltaX;
			}
			if (deltaY != 0) {
				yFrac = yDist / deltaY;
			}
			//System.out.println(xFrac + " " + yFrac);
			if (xFrac < yFrac) {
				currPixelX = (int)startX + posX;
				startY += deltaY * xFrac;
				startX = currPixelX;
			}
			else if (yFrac < xFrac) {
				currPixelY = (int)startY + posY;
				startX += deltaX * yFrac;
				startY = currPixelY;
			}
			if (xFrac == yFrac) {
				currPixelX = (int)startX + posX;
				currPixelY = (int)startY + posY;
				startX = currPixelX;
				startY = currPixelY;
			}
			
		}
		//System.out.println("Took " + (double) (System.nanoTime() - beforeTime) / 1000000000d + " seconds");
		//System.out.println(colors);
		
		if (lastColor.equals(WALL_COL)) {
			return new Collision(startX, startY, Collision.WALL);
		} else if (lastColor.equals(PLAT_COL)) {
			return new Collision(startX, startY, Collision.PLATFORM);
		} else {
			return null;
		}
		
	}
	
	
	
}
