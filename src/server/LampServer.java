package server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import main_app.Handler;

public class LampServer extends Thread {

	public static final int PORT = 56276;
	BufferedReader in;
	PrintStream out;
	Socket clientSock;
	int frames = 0;
	ArrayList<String> disp;
	boolean connectionOpen;
	Handler handler;
	int currClientID = 0;
	HashMap<Integer, ServerThread> clients;

	public LampServer(Handler handler) {
		this.handler = handler;
		this.clients = new HashMap<Integer, ServerThread>();
		this.start();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				ServerSocket serverSock = new ServerSocket(PORT);
				

				System.out.println("Waiting for client...");
				clientSock = serverSock.accept();
				clientSock.setTcpNoDelay(true);

				in = new BufferedReader(new InputStreamReader(
						clientSock.getInputStream()));
				out = new PrintStream(clientSock.getOutputStream());

				System.out.println("Client connection from "
						+ clientSock.getInetAddress().getHostAddress());
				
				disp = new ArrayList<String>();
				disp.add("Connection initialized with: " + clientSock.getInetAddress().toString());
				clients.put(currClientID, new ServerThread(handler, this, clientSock, currClientID));
				currClientID++;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void update() {
		if (clientSock != null) {
			if (frames > 30) {
				frames = 0;
				Date d = new Date();
				DecimalFormat df = new DecimalFormat("00000000000000000000");
				//out.println(df.format(d.getTime()) + "hello");
				//disp.add(d.toString());
			} else {
				frames++;
			}
		}
		
	}
	
	public void draw(Graphics g) {
		if (clientSock != null) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			int start = disp.size() - 27;
			if (start < 0) {
				start = 0;
			}
			for (int i = start; i < disp.size(); i++) {
				g.drawString(disp.get(i), 20, 35 + 20 * (i - start));
			}
		} else {
			g.setColor(Color.WHITE);
			g.drawString("Waiting for client...", 20, 40);
		}
	}
	
	public void broadcast(String s, int id) {
		for (int i : clients.keySet()) {
			if (i != id) {
				clients.get(i).sendMessage("broadcast " + id + " " + s);
			}
		}
	}

}
