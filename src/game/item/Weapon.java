package game.item;
import game.Player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main_app.Handler;

public class Weapon extends Item {
	
	Handler handler;
	public static ArrayList<Double> damages = new ArrayList<Double>();
	public static ArrayList<String> names = new ArrayList<String>();
	BufferedImage weaponSpritesheet; //filled with images of weapons
	public BufferedImage attackingSpritesheet; //Animations of different weapon type attacks (ie. long sword vs short sword etc.)
	/*attackingSpritesheet Layout
	 * WeaponType #1
	 * Left attacking images
	 * Right attacking images
	 * Jumping attacking images
	 * WeaponType #2
	 * Left attacking images
	 * Right attacking images
	 * Jumping attacking images
	 * etc...
	 */
	
	public double damage = 100; //some starting damage (not even needed)
	//public String name = "Basic Sword";
	
	public int weaponType;
	private final int SHORTSWORD = 0;
	private final int LONGSWORD = 1;
	private final int KATANA = 2;//more can be added
	
	public Weapon (Player player, Handler handler, int weapon){ //each weapon has a corresponding integer
		super(player, handler);
		//this.weaponSpritesheet = handler.loadImage("/weapons.png"); I COMMENTED THESE OUT FOR TESTING PURPOSES
		//this.attackingSpritesheet = handler.loadImage("/attacks.png");
		//this.damage = damages.get(weapon);
		//this.name = names.get(weapon);
		this.weaponType = (weapon/10); //10 weapons of each type
	}
}
