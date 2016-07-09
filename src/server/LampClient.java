package server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class LampClient extends Thread {
	
	private static final int PORT = 56276;
	private static final String HOST = "68.9.35.86";
	
	private boolean connectionOpen;
	
	private Socket sock;
	private ArrayList<String> disp;
	private BufferedReader in;
	private PrintStream out;
	
	public LampClient() {
		disp = new ArrayList<String>();
		disp.add(new Date() + ": Client initialized.");
		try {
			sock = new Socket(HOST, PORT);
			sock.setTcpNoDelay(true);
			disp.add(new Date() + ": Connection initialized with " + sock.getInetAddress());
			disp.add(System.getProperty("java.version"));
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintStream(sock.getOutputStream());
			connectionOpen = true;
			this.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		while(connectionOpen) {
			try {
				String s = in.readLine();
				if (s != null && s.equals("close")) {
					disp.add(new Date() + ": Closing connection");
					connectionOpen = false;
				}
				if (s.substring(20).substring(0, s.indexOf(" ") - 20).equals("ping")) {
					Date d = new Date();
					disp.add(d + ": Ping response: " + (System.nanoTime() - Long.parseLong(s.substring(s.indexOf(" ") + 1))) / 1000000 + "ms");
				} else {
					Date d = new Date();
					disp.add(d + ": " + s.substring(20) + " -- " + ((System.nanoTime() - Long.parseLong(s.substring(0, 20))) / 1000000) + "ms");
					messageReceived(s);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void messageReceived(String s) {
		
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		int start = disp.size() - 27;
		if (start < 0) {
			start = 0;
		}
		for (int i = start; i < disp.size(); i++) {
			g.drawString(disp.get(i), 20, 35 + 20 * (i - start));
		}
	}
	
	public void update() {
		
	}
	
	public void ping() {
		sendMessage("ping ");
		
	}
	
	private void sendMessage(String message) {
		Date d = new Date();
		Long l = System.nanoTime();
		DecimalFormat df = new DecimalFormat("00000000000000000000");
		out.println(df.format(l) + message);
		out.flush();
	}

	public void close() {
		sendMessage("close ");
		try {
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void broadcast(String s) {
		sendMessage("broadcast " + s);
	}
	
}
