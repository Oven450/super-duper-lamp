package game;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import main_app.Handler;
import utility.ReadFile;
import world_collision.Collision;
import world_collision.MoveVector;
import world_collision.WorldCollisionSegment;


public class World {
	
	Handler handler;
	BufferedImage dispImage;
	ArrayList<WorldCollisionSegment> segments;
	BufferedImage lightmap;
	
	public World(Handler handler) {
		this.handler = handler;
		
		dispImage = handler.loadImage("level2.png");
		lightmap = handler.loadImage("lightmap.png");
		segments = new ArrayList<WorldCollisionSegment>();
		readCollisionData();
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(dispImage, (int) (512 - ((GameHandler) handler).player.getX()), (int) (288 - ((GameHandler) handler).player.getY()), null);
		//g.drawImage(lightmap, 0, 0, null);
		((Graphics2D) g).setStroke(new BasicStroke(3, 0, 0));
		for (int i = 0; i < segments.size(); i++) {
			//segments.get(i).draw(g);
		}
	}
	
	public void readCollisionData() {
		ReadFile fileReader = new ReadFile("/world-collisions2.txt", true);
		String[] lines = null;
		try {
			lines = fileReader.openFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lines != null && lines.length > 1) {
			for (int i = 1; i < Integer.parseInt(lines[0]) + 1; i++) {
				String[] line = lines[i].split(" ");
				addSegment(Double.parseDouble(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2]), Double.parseDouble(line[3]), Boolean.parseBoolean(line[4]), Boolean.parseBoolean(line[5]));
			}
		}
		
	}
	
	public void addSegment(double x1, double y1, double x2, double y2, boolean above, boolean below) {
		segments.add(new WorldCollisionSegment(x1, y1, x2, y2, above, below, (Handler) handler));
	}
	
	public Collision testCollision(MoveVector mv) {
		Point2D currentBest = null;
		WorldCollisionSegment bestSeg = null;
		double progress = 1;
		for (WorldCollisionSegment seg : segments) {
			Rectangle2D segRect = seg.getBoundingBox();
			Rectangle2D mvRect = mv.getBoundingBox();
			//if (segRect.intersects(mvRect)) {
			//System.out.println("(" + mv.x1 + ", " + mv.x2 + ", " + mv.y1 + ", " + mv.y2 + ") (" + seg.getX1() + ", " + seg.getX2() + ", " + seg.getY1() + ", " + seg.getY2() + ")");	
			Point2D p1 = seg.getCollisionPoint(mv);
				if (p1 == null) {
					continue;
				}
				double thisProgress = mv.getProgress(p1);
				if (thisProgress < progress) {
					currentBest = p1;
					progress = thisProgress;
					bestSeg = seg;
				}
		}
		if (currentBest == null) {
			return null;
		}
		Point2D p = bestSeg.getPoint01FromPoint(currentBest.getX(), currentBest.getY(), mv.x1, mv.y1);
		return new Collision(p.getX(), p.getY(), bestSeg, progress);
	}
	
	
}
