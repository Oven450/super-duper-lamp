package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;

import main_app.Handler;

public class ServerThread extends Thread {
	
	Handler handler;
	LampServer server;
	Socket sock;
	BufferedReader in;
	PrintStream out;
	int clientID;
	boolean connectionOpen;
	
	public ServerThread(Handler handler, LampServer server, Socket sock, int clientID) {
		this.handler = handler;
		this.server = server;
		this.sock = sock;
		this.clientID = clientID;
		this.connectionOpen = true;
		this.start();
	}
	
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintStream(sock.getOutputStream());
			while (true) {
				String s = in.readLine();
				Date d = new Date();
				server.disp.add(d + ": " + s.substring(20) + " -- " + ((System.nanoTime() - Long.parseLong(s.substring(0, 20))) / 1000000 + "ms") + " -- " + sock.getInetAddress() + ": " + clientID);
				if (s.substring(20).substring(0, s.indexOf(" ") - 20).equals("ping")) {
					pingBack(s.substring(0, 20));
				} else if (s.substring(20).substring(0, s.indexOf(" ") - 20).equals("close")) {
					connectionOpen = false;
					sock.close();
				} else if (s.substring(20).substring(0, s.indexOf(" ") - 20).equals("broadcast")) {
					server.broadcast(s.substring(s.indexOf(" ") + 1), clientID);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(String message) {
		Date d = new Date();
		Long l = System.nanoTime();
		DecimalFormat df = new DecimalFormat("00000000000000000000");
		out.println(df.format(l) + message);
		out.flush();
	}
	
	public void pingBack(String sendTime) {
		sendMessage("ping " + sendTime);
	}
	
}
