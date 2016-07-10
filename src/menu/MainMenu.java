package menu;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import main_app.Handler;
import main_app.LampPanel;
import main_app.MainMenuHandler;


public class MainMenu extends Menu {
	
	private static final Color BACKGROUND = new Color(15, 15, 25);
	
	public MainMenu (Handler handler) {
		super(handler);
		
		this.buttons.add(new Button(0, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 - 100, 300, 60, new Color(15, 150, 50, 30), "Single Player", this));
		this.buttons.add(new Button(5, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 - 25, 300, 60, new Color(15, 150, 50, 30), "Multiplayer", this));
		this.buttons.add(new Button(1, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 + 50, 300, 60, new Color(15, 150, 50, 30), "Edit World Collisions", this));
		this.buttons.add(new Button(3, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 + 125, 142, 60, new Color(15, 150, 50, 30), "Client", this));
		this.buttons.add(new Button(4, LampPanel.PWIDTH / 2 + 8, LampPanel.PHEIGHT / 2 + 125, 142, 60, new Color(15, 150, 50, 30), "Server", this));
		this.buttons.add(new Button(2, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 + 200, 300, 60, new Color(15, 150, 50, 30), "Quit", this));
	}

	@Override
	public void execute(int id) {
		switch (id) {
		case 0:
			((MainMenuHandler) handler).startGame();
			break;
		case 1:
			((MainMenuHandler) handler).startWorldCollisionEditor();
			break;
		case 2:
			handler.getPanel().stopGame();
			break;
		case 3:
			((MainMenuHandler) handler).startClient();
			break;
		case 4:
			((MainMenuHandler) handler).startServer();
			break;
		case 5:
			((MainMenuHandler) handler).startMultiplayerGame();
			break;
		default:
			System.out.println("Invalid Button ID");
		}
		
	}
	
	public void draw(Graphics g) {
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, LampPanel.PWIDTH, LampPanel.PHEIGHT);
		
		g.setFont(new Font("Impact", Font.PLAIN, 80));
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.WHITE);
		
		g.drawString("SUPER DUPER LAMP", (int)(LampPanel.PWIDTH / 2 - fm.stringWidth("SUPER DUPER LAMP") / 2), (int)(LampPanel.PHEIGHT / 2 - 160 + (fm.getAscent() / 3)));
		super.draw(g);
		
		
	}
	
}
