import java.awt.Dimension;

import javax.swing.JFrame;

public class SuperDuperLamp extends JFrame{
	
	public SuperDuperLamp() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(LampPanel.PWIDTH, LampPanel.PHEIGHT);
		this.setTitle("Super Duper Lamp");
		LampPanel panel = new LampPanel();
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SuperDuperLamp app = new SuperDuperLamp();

	}
	

}
