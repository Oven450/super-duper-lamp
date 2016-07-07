package game.item;
import main_app.Handler;
import game.Player;





public class Sword extends Weapon {
	

	public Sword(Player player, Handler handler, int weapon) {
		super(player, handler, weapon);
		this.image = handler.loadImage("/sword.png");
		this.name = "Sword";
		
	}

}
