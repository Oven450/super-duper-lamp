package multiplayer;

import server.LampClient;

public class MultiplayerClient extends LampClient {
	
	MultiplayerGameHandler handler;
	
	public MultiplayerClient(MultiplayerGameHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void messageReceived(String s) {
		
	}
	
}
