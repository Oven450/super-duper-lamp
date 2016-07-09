package multiplayer;

import game.GameHandler;
import main_app.LampPanel;
import server.LampClient;

public class MultiplayerGameHandler extends GameHandler {
	
	MultiplayerClient client;
	
	public MultiplayerGameHandler(LampPanel panel) {
		super(panel);
		client = new MultiplayerClient(this);
	}
	
	public void updateGO() {
		super.updateGO();
		client.broadcast(player.getBroadcastString());
	}
	
	
	
}
