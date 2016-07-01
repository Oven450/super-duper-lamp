import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import java.awt.Cursor;


public class Button {
	
	Menu menu;
	
	int x;
	int y;
	int width;
	int height;
	Color color;
	Color activeColor;
	BufferedImage image = null;
	int id;
	String text;
	
	boolean active = false;
	
	public Button(int id, int x, int y, int width, int height, Color color, String text, Menu menu) {
		System.out.println("Button created");
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		int r = color.getRed() + 15;
		int g = color.getGreen() + 15;
		int b = color.getBlue() + 15;
		if (r > 255) {
			r = 255;
		}
		if (g > 255) {
			g = 255;
		}
		if (b > 255) {
			b = 255;
		}
		this.activeColor = new Color(r, g, b);
		
		this.menu = menu;
		this.id = id;
		this.text = text;
	}
	
	public Button(int id, int x, int y, BufferedImage image, Menu menu) {
		this.x = x;
		this.y = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.image = image;
		this.menu = menu;
		this.id = id;
	}
	
	public void mouseClicked(Point p) {
		if (p.getX() > x && p.getY() > y && p.getX() < x + width && p.getY() < y + height) {
			menu.execute(id);
			menu.handler.getPanel().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public boolean mouseMoved(Point p) {
		if (p.getX() > x && p.getY() > y && p.getX() < x + width && p.getY() < y + height) {
			active = true;
			
			menu.handler.getPanel().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			return true;
		}
		else {
			
			active = false;
			return false;
		}
	}
	
	public void draw(Graphics g) {
		System.out.println("Button draw called");
		if (image != null) {
			g.drawImage(image, x, y, null);
		} else {
			if (active) {
				g.setColor(activeColor);
			} else {
				g.setColor(color);
			}
			g.fillRoundRect(x, y, width, height, 10, 10);
			g.setFont(new Font("Impact", Font.PLAIN, 28));
			FontMetrics fm = g.getFontMetrics();
			g.setColor(Color.WHITE);
			
			g.drawString(this.text, (int)(this.x + this.width / 2 - fm.stringWidth(text) / 2), (int)(this.y + this.height / 2 + (fm.getAscent() / 3)));
		}
		
	}
	
}
