import java.awt.Color;
import java.awt.Font;


public class WorldCollisionMenu extends Menu {

	public WorldCollisionMenu(WorldCollisionHandler handler) {
		super(handler);
		
		this.buttons.add(new Button(0, LampPanel.PWIDTH - 150, 25, 120, 30, new Color(15, 150, 50), "Main Menu", this));
		this.buttons.add(new Button(1, LampPanel.PWIDTH - 150, 65, 55, 30, new Color(190, 20, 120), "Zoom +", this));
		this.buttons.add(new Button(2, LampPanel.PWIDTH - 85, 65, 55, 30, new Color(190, 20, 120), "Zoom -", this));
		this.buttons.add(new Button(3, LampPanel.PWIDTH - 150, 105, 120, 30, new Color(190, 20, 120), "New Line", this));
		this.buttons.add(new Button(4, LampPanel.PWIDTH - 150, 145, 120, 30, new Color(255, 20, 20), "Delete Last", this));
		for (int i = 0; i < this.buttons.size(); i++) {
			Button button = this.buttons.get(i);
			button.setFont(new Font("sans-serif", Font.BOLD, 12));
		}
	}

	@Override
	public void execute(int id) {
		switch (id) {
		case 0:
			handler.toMainMenu();
			break;
		case 1:
			((WorldCollisionHandler) handler).zoomIn();
			break;
		case 2:
			((WorldCollisionHandler) handler).zoomOut();
			break;
		case 3:
			break;
		case 4:
			((WorldCollisionHandler) handler).deleteLast();
			break;
		default:
			System.out.println("Invalid button");
		}
		
	}
	
	
	
}
