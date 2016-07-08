package server;

import java.awt.Graphics;

import main_app.Handler;
import main_app.LampPanel;

public class LampServerHandler extends Handler {
	
	LampServer server;
	
	public LampServerHandler(LampPanel panel) {
		super(panel);
		server = new LampServer();
	}
	
	@Override
	public void updateGO() {
		server.update();
	}
	
	@Override
	public void draw(Graphics g) {
		server.draw(g);
	}
	
}
