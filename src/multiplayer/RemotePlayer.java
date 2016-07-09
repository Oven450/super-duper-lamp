package multiplayer;

import game.GameHandler;
import game.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import main_app.LampPanel;

public class RemotePlayer {
	
	MultiplayerGameHandler handler;
	int id;
	double x, y, xvel, yvel;
	BufferedImage spritesheet1;
	BufferedImage spritesheet2;
	BufferedImage currImage;
	int ticksTillNextFrame;
	int facing;
	
	public RemotePlayer(MultiplayerGameHandler handler, int  id) {
		this.handler = handler;
		this.id = id;
		this.spritesheet1 = handler.loadImage("/player.png");
		this.spritesheet2 = handler.loadImage("/player2.png");
		ticksTillNextFrame = 9;
		currImage = spritesheet1;
	}
	
	public void updateFromRemote(String s) {
		String[] arr = s.split(" ");
		//System.out.println(Arrays.toString(arr));
		double[] darr = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			darr[i] = Double.parseDouble(arr[i]);
		}
		x = darr[0];
		y = darr[1];
		xvel = darr[2];
		yvel = darr[3];
		facing = (int) darr[4];
	}
	
	public void update() {
		if (ticksTillNextFrame == 0) {
			if (currImage.equals(spritesheet1)) {
				currImage = spritesheet2;
			} else {
				currImage = spritesheet1;
			}
			ticksTillNextFrame = 9;
		} else {
			ticksTillNextFrame--;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0, 255, 255, 50));
		if (facing == Player.RIGHT) {
			((Graphics2D) g).drawImage(currImage, LampPanel.PWIDTH / 2 - (int) (((GameHandler) handler).player.getX()  - this.x) - 16, LampPanel.PHEIGHT / 2 - (int) (((GameHandler) handler).player.getY() - this.y) - 27, LampPanel.PWIDTH / 2 - (int) (((GameHandler) handler).player.getX()  - this.x) + 16, LampPanel.PHEIGHT / 2 - (int) (((GameHandler) handler).player.getY() - this.y) + 27, 0, 0, 14, 27, null);
		} else {
			((Graphics2D) g).drawImage(currImage, LampPanel.PWIDTH / 2 - (int) (((GameHandler) handler).player.getX()  - this.x) - 16, LampPanel.PHEIGHT / 2 - (int) (((GameHandler) handler).player.getY() - this.y) - 27, LampPanel.PWIDTH / 2 - (int) (((GameHandler) handler).player.getX()  - this.x) + 16, LampPanel.PHEIGHT / 2 - (int) (((GameHandler) handler).player.getY() - this.y) + 27, 14, 0, 0, 27, null);
		}
	}
	
	
}
