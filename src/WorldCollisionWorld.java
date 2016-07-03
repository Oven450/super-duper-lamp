import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class WorldCollisionWorld {
	
	WorldCollisionHandler handler;
	BufferedImage dispImage;
	ArrayList<WorldCollisionSegment> segments;
	
	public WorldCollisionWorld(WorldCollisionHandler handler) {
		this.handler = handler;
		
		dispImage = handler.loadImage("level.png");
		segments = new ArrayList<WorldCollisionSegment>();
		
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics g, double scaleX, double scaleY, double centerX, double centerY) {
		Graphics2D g2d = (Graphics2D) g;
		//System.out.println(((centerX - dispImage.getWidth() * scaleX / 2)) + ", " + ((centerY - dispImage.getHeight() * scaleY / 2)));
		int sx1 = (int) ((scaleX * - (centerX - dispImage.getWidth() / scaleX / 2)));
		int sy1 = (int) ((scaleY * - (centerY - dispImage.getHeight() / scaleY / 2)));
		int sx2 = (int) (sx1 + LampPanel.PWIDTH * scaleX);
		int sy2 = (int) (sy1 + LampPanel.PHEIGHT * scaleY);
		g2d.drawImage(dispImage, 0, 0, LampPanel.PWIDTH, LampPanel.PHEIGHT, sx1, sy1, sx2, sy2, null);
		AffineTransform transform = g2d.getTransform();
		g2d.scale(1 / scaleX, 1 / scaleY);
		g2d.translate(scaleX * (-dispImage.getWidth() / 2 + centerX) + dispImage.getWidth() * scaleX / 2 - dispImage.getWidth() / 2, scaleY * (-dispImage.getHeight() / 2 + centerY) + dispImage.getHeight() * scaleY / 2 - dispImage.getHeight() / 2);
		for (int i = 0; i < segments.size(); i++) {
			segments.get(i).draw(g);
		}
		g2d.setTransform(transform);
	}
	
	public void tempDraw(WorldCollisionSegment temp, Graphics g) {
		
	}
	
	public void addSegment(double x1, double y1, double x2, double y2) {
		segments.add(new WorldCollisionSegment(x1, y1, x2, y2, (Handler) handler));
	}
	
}
