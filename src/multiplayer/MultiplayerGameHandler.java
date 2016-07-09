package multiplayer;

import game.GameHandler;

import java.awt.Graphics;
import java.util.HashMap;

import main_app.LampPanel;

public class MultiplayerGameHandler extends GameHandler {
	
	MultiplayerClient client;
	HashMap<Integer, RemotePlayer> remPlayers;
	
	public MultiplayerGameHandler(LampPanel panel) {
		super(panel);
		client = new MultiplayerClient(this);
		remPlayers = new HashMap<Integer, RemotePlayer>();
	}
	
	public void updateGO() {
		super.updateGO();
		for (int i : remPlayers.keySet()) {
			remPlayers.get(i).update();
		}
		client.broadcast(player.getBroadcastString());
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		for (int i : remPlayers.keySet()) {
			remPlayers.get(i).draw(g);
		}
	}

	public void receiveBroadcast(String s, int id) {
		//System.out.println(s);
		if (s.split(" ")[0].equals("player")) {
			
			if (!remPlayers.containsKey(id)) {
				remPlayers.put(id, new RemotePlayer(this, id));
			}
			remPlayers.get(id).updateFromRemote(s.substring(s.indexOf(" ") + 1));
		}
		
	}
	
	
	
}
