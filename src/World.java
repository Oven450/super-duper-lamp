import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class World {
	
	Handler handler;
	BufferedImage dispImage;
	ArrayList<WorldCollisionSegment> segments;
	
	public World(Handler handler) {
		this.handler = handler;
		
		dispImage = handler.loadImage("level.png");
		segments = new ArrayList<WorldCollisionSegment>();
		readCollisionData();
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(dispImage, 0, 0, null);
		((Graphics2D) g).setStroke(new BasicStroke(3, 0, 0));
		for (int i = 0; i < segments.size(); i++) {
			segments.get(i).draw(g);
		}
	}
	
	public void readCollisionData() {
		ReadFile fileReader = new ReadFile("/world-collisions.txt", true);
		String[] lines = null;
		try {
			lines = fileReader.openFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lines != null && lines.length > 1) {
			for (int i = 1; i < Integer.parseInt(lines[0]) + 1; i++) {
				String[] line = lines[i].split(" ");
				addSegment(Double.parseDouble(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2]), Double.parseDouble(line[3]));
			}
		}
		
	}
	
	public void addSegment(double x1, double y1, double x2, double y2) {
		segments.add(new WorldCollisionSegment(x1, y1, x2, y2, (Handler) handler));
	}
	
	public MoveVector testCollision(MoveVector mv) {
		for (WorldCollisionSegment seg : segments) {
			Rectangle2D segRect = seg.getBoundingBox();
			Rectangle2D mvRect = mv.getBoundingBox();
			//if (segRect.intersects(mvRect)) {
			//System.out.println("(" + mv.x1 + ", " + mv.x2 + ", " + mv.y1 + ", " + mv.y2 + ") (" + seg.getX1() + ", " + seg.getX2() + ", " + seg.getY1() + ", " + seg.getY2() + ")");	
			Point2D p1 = seg.getCollisionPoint(mv);
				if (p1 == null) {
					continue;
				}
				Point2D p = seg.getPoint01FromPoint(p1.getX(), p1.getY(), mv.x1, mv.y1);
				double x = p.getX();
				double y = p.getY();
				
				try {
					//System.out.println("Sleeping : (" + x + ", " + y + ")");
					//handler.panel.animator.sleep(500);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new MoveVector(mv.x1, mv.y1, x, y);
				
			//}
			//if ((new Line2D.Double(seg.getX1(), seg.getY1(), seg.getX2(), seg.getY2())).ptLineDist(new Point2D.Double(mv.x1, mv.y1)) < .00000001) {
			//	return new MoveVector(mv.x1, mv.y1, mv.x1, mv.y1);
			//}
		}
		return null;
	}
	
	
}
