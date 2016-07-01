import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


public class PauseMenu extends Menu {

	public PauseMenu(Handler handler) {
		super(handler);
		
		this.buttons.add(new Button(0, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 - 50, 300, 60, new Color(15, 150, 50), "Main Menu", this));
		this.buttons.add(new Button(1, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 + 25, 300, 60, new Color(15, 150, 50), "Resume", this));
	}

	@Override
	public void execute(int id) {
		switch (id) {
		case 0:
			((GameHandler) handler).setCurrentHandlerAsNull();
			break;
		case 1:
			((GameHandler) handler).resume();
		default:
			System.out.println("Invalid button");
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(15, 15, 25, 180));
		g.fillRect(0, 0, LampPanel.PWIDTH, LampPanel.PHEIGHT);
		
		g.setFont(new Font("Impact", Font.PLAIN, 40));
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.WHITE);
		
		g.drawString("Game Paused", (int)(LampPanel.PWIDTH / 2 - fm.stringWidth("Game Paused") / 2), (int)(LampPanel.PHEIGHT / 2 - 150 + (fm.getAscent() / 3)));
		super.draw(g);
		
		
	}
	
}
