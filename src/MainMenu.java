import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


public class MainMenu extends Menu {
	
	private static final Color BACKGROUND = new Color(15, 15, 25);
	
	public MainMenu (Handler handler) {
		super(handler);
		
		this.buttons.add(new Button(0, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 - 50, 300, 60, new Color(15, 150, 50), "Start Game", this));
		this.buttons.add(new Button(1, LampPanel.PWIDTH / 2 - 150, LampPanel.PHEIGHT / 2 + 25, 300, 60, new Color(15, 150, 50), "Quit", this));
	}

	@Override
	public void execute(int id) {
		switch (id) {
		case 0:
			((MainMenuHandler) handler).startGame();
			break;
		case 1:
			handler.getPanel().stopGame();
			break;
		default:
			System.out.println("Invalid Button ID");
		}
		
	}
	
	public void draw(Graphics g) {
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, LampPanel.PWIDTH, LampPanel.PHEIGHT);
		
		g.setFont(new Font("Impact", Font.PLAIN, 80));
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.WHITE);
		
		g.drawString("SUPER DUPER LAMP", (int)(LampPanel.PWIDTH / 2 - fm.stringWidth("SUPER DUPER LAMP") / 2), (int)(LampPanel.PHEIGHT / 2 - 150 + (fm.getAscent() / 3)));
		super.draw(g);
		
		
	}
	
}
