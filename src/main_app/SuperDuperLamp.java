package main_app;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class SuperDuperLamp extends JFrame{
	
	LampPanel panel;
	
	public SuperDuperLamp() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(LampPanel.PWIDTH, LampPanel.PHEIGHT);
		this.setTitle("Super Duper Lamp");
		this.panel = new LampPanel();
		this.add(panel);
		this.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
		        panel.resized(e.getComponent().getWidth() - 16, e.getComponent().getHeight() - 38);
		    }

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		this.pack();
		this.setVisible(true);
		
	}

	public static void main(String[] args) {
		SuperDuperLamp app = new SuperDuperLamp();

	}
	

}
