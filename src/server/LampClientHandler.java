package server;

import java.awt.Graphics;

import main_app.Handler;
import main_app.LampPanel;

public class LampClientHandler extends Handler {
	
	LampClient client;
	
	public LampClientHandler(LampPanel panel) {
		super(panel);
		this.client = new LampClient();
	}
	
	@Override
	public void draw(Graphics g) {
		client.draw(g);
	}
	
	@Override
	public void updateGO() {
		client.update();
	}
	
}
