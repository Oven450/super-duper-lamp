import java.awt.Color;


public class WorldCollisionMenu extends Menu {

	public WorldCollisionMenu(WorldCollisionHandler handler) {
		super(handler);
		
		this.buttons.add(new Button(0, LampPanel.PWIDTH - 200, 25, 150, 40, new Color(15, 150, 50), "Main Menu", this));
	}

	@Override
	public void execute(int id) {
		switch (id) {
		case 0:
			handler.toMainMenu();
			break;
		default:
			System.out.println("Invalid button");
		}
		
	}
	
	
	
}
