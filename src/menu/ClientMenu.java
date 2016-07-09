package menu;

import java.awt.Color;

import main_app.Handler;
import main_app.LampPanel;
import main_app.MainMenuHandler;
import server.LampClientHandler;

public class ClientMenu extends Menu {
	
	public ClientMenu (Handler handler) {
		super(handler);
		
		this.buttons.add(new Button(0, LampPanel.PWIDTH - 150, 30, 120, 40, new Color(15, 150, 50, 200), "Ping", this));
		this.buttons.add(new Button(1, LampPanel.PWIDTH - 150, 85, 120, 40, new Color(15, 150, 50, 200), "Close", this));
		this.buttons.add(new Button(2, LampPanel.PWIDTH - 150, 140, 120, 40, new Color(15, 150, 50, 200), "Broadcast", this));
	}

	@Override
	public void execute(int id) {
		switch (id) {
		case 0:
			((LampClientHandler) handler).ping();
			break;
		case 1:
			((LampClientHandler) handler).close();
			break;
		case 2:
			((LampClientHandler) handler).broadcast("Hello");
			break;
		default:
			System.out.println("Invalid Button ID");
		}
		
	}
}
