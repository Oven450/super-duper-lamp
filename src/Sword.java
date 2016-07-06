import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Sword extends Weapon {
	
	BufferedImage swordImage;

	public Sword(Player player, Handler handler, int weapon) {
		super(player, handler, weapon);
		swordImage = handler.loadImage("/sword.png");
		
	}
	
	@Override
	public void inventoryDraw(int x, int y, Graphics g, boolean curr) {
		super.inventoryDraw(x, y, g, curr);
		g.drawImage(swordImage, x, y, null);
	}

}
