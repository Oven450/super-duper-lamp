import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import java.awt.Cursor;


public abstract class Menu {
	
	ArrayList<Button> buttons;
	Handler handler;
	
	public Menu(Handler handler) {
		this.handler = handler;
		this.buttons = new ArrayList<Button>();
	}
	
	public abstract void execute(int id);
	
	public void draw(Graphics g) {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(g);
		}
	}
	
	public void mouseClicked(Point p) {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).mouseClicked(p);
		}
	}
	
	public void mouseMoved(Point p) {
		boolean overButton = false;
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).mouseMoved(p)) {
				overButton = true;
			}
		}
		if (!overButton) {
			handler.getPanel().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
}
