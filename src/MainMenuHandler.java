import java.awt.Graphics;
import java.awt.Point;


public class MainMenuHandler extends Handler {
	
	MainMenu menu;
	
	public MainMenuHandler(LampPanel panel) {
		super(panel);
		Handler handler = this;
		menu = new MainMenu(handler);
		
	}
	
	public void startGame() {
		this.panel.startGameHandler();
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
