import java.awt.Graphics;
import java.awt.Point;


public class WorldCollisionHandler extends Handler {
	
	WorldCollisionMenu menu;
	
	public WorldCollisionHandler(LampPanel panel) {
		super(panel);
		
		menu = new WorldCollisionMenu(this);
		
	}
	
	@Override
	public void draw(Graphics g) {
		menu.draw(g);
	}
	
	@Override
	public void mouseClickedGO(Point p) {
		menu.mouseClicked(p);
	}
	
	@Override
	public void mouseMovedGO(Point p) {
		menu.mouseMoved(p);
	}

}
