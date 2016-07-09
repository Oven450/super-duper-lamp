package server;

import java.awt.Graphics;
import java.awt.Point;

import main_app.Handler;
import main_app.LampPanel;
import menu.ClientMenu;

public class LampClientHandler extends Handler {
	
	LampClient client;
	ClientMenu menu;
	
	public LampClientHandler(LampPanel panel) {
		super(panel);
		this.client = new LampClient();
		menu = new ClientMenu(this);
	}
	
	@Override
	public void draw(Graphics g) {
		client.draw(g);
		menu.draw(g);
	}
	
	@Override
	public void updateGO() {
		client.update();
	}
	
	@Override
	public void mouseClickedGO(Point p) {
		menu.mouseClicked(p);
	}

	public void ping() {
		client.ping();
	}

	public void close() {
		client.close();
		this.panel.stopGame();
	}

	public void broadcast(String s) {
		client.broadcast(s);
		
	}
	
}
