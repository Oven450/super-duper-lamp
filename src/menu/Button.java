package menu;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


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
	Font font;
	
	boolean active = false;
	
	public Button(int id, int x, int y, int width, int height, Color color, String text, Menu menu) {
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
		this.font = new Font("Impact", Font.PLAIN, 28);
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
			menu.handler.getPanel().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			menu.execute(id);
			
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
		if (image != null) {
			g.drawImage(image, x, y, null);
		} else {
			if (active) {
				g.setColor(activeColor);
			} else {
				g.setColor(color);
			}
			g.fillRoundRect(x, y, width, height, 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(x, y, width, height, 10, 10);
			g.setFont(font);
			FontMetrics fm = g.getFontMetrics();
			g.setColor(Color.WHITE);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(
			        RenderingHints.KEY_TEXT_ANTIALIASING,
			        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.drawString(this.text, (int)(this.x + this.width / 2 - fm.stringWidth(text) / 2), (int)(this.y + this.height / 2 + (fm.getAscent() / 2.7)));
		}
		
	}
	
	
	public void setFont(Font font) {
		this.font = font;
	}
	
}
