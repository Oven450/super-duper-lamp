import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Lamp extends Item {
	
	BufferedImage lamp;
	
	public Lamp(Player player, Handler handler) {
		super(player, handler);
		this.lamp = handler.loadImage("/lamp.png");
	}
	
	@Override
	public void inventoryDraw(int x, int y, Graphics g, boolean curr) {
		super.inventoryDraw(x, y, g, curr);
		((Graphics2D) g).drawImage(lamp, x, y, x + 48, y + 48, 0, 0, 36, 36, null);
	}
	
}
