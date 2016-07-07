import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Bow extends Weapon {
	
	
	
	public Bow(Player player, Handler handler, int weapon) {
		super(player, handler, weapon);
		this.image = handler.loadImage("/bow.png");
		this.name = "Bow";
	}
	
}
