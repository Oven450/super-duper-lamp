package multiplayer;

import server.LampClient;

public class MultiplayerClient extends LampClient {
	
	MultiplayerGameHandler handler;
	
	public MultiplayerClient(MultiplayerGameHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void messageReceived(String s) {
		if (s.substring(20).substring(0, s.indexOf(" ") - 20).equals("broadcast")) {
			String newS = s.substring(20).substring(s.indexOf(" ") - 20 + 1);
			int id = Integer.parseInt(newS.substring(0, newS.indexOf(" ")));
			handler.receiveBroadcast(newS.substring(newS.indexOf(" ") + 1), id);
		}
	}
	
}
