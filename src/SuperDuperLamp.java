import java.awt.Dimension;

import javax.swing.JFrame;

public class SuperDuperLamp extends JFrame{
	
	public SuperDuperLamp() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1024, 576);
		this.setTitle("Super Duper Lamp");
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SuperDuperLamp app = new SuperDuperLamp();

	}
	

}
