package server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class LampClient extends Thread {
	
	private static final int PORT = 12234;
	private static final String HOST = "68.9.35.86";
	
	private boolean connectionOpen;
	
	private Socket sock;
	private ArrayList<String> disp;
	private BufferedReader in;
	private PrintWriter out;
	
	public LampClient() {
		disp = new ArrayList<String>();
		disp.add(new Date() + ": Client initialized.");
		try {
			sock = new Socket(HOST, PORT);
			disp.add(new Date() + ": Connection initialized with " + sock.getInetAddress());
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream());
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
			System.out.println("Running");
			try {
				String s = in.readLine();
				if (s != null && s.equals("close")) {
					disp.add(new Date() + ": Closing connection");
					connectionOpen = false;
				}
				Date d = new Date();
				disp.add(d + ": " + (d.getTime() - Long.parseLong(s) + "ms"));
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

	public synchronized void draw(Graphics g) {
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
	
}
