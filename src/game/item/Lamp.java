package game.item;
import game.Player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main_app.Handler;


public class Lamp extends Item {
	
	
	
	public Lamp(Player player, Handler handler) {
		super(player, handler);
		this.image = handler.loadImage("/lamp.png");
		this.name = "Lamp";
	}
	
}
