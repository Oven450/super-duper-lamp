package game;
import game.item.Item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import main_app.Handler;
import main_app.LampPanel;


public class PlayerInventory {
	
	Player player;
	Handler handler;
	Item[] equipped;
	Item[] inventory;
	int currEquipped;
	boolean open;
	BufferedImage scrollImage;
	int scrollPos;
	
	
	public PlayerInventory(Player player, Handler handler, int inventorySize, int equipSlots) {
		this.player = player;
		this.handler = handler;
		
		this.equipped = new Item[equipSlots];
		this.inventory = new Item[inventorySize];
		currEquipped = 0;
		open = false;
		createScrollImage();
	}
	
	public boolean addToInventory(Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				inventory[i] = item;
				createScrollImage();
				return true;
			}
		}
		return false;
	}
	
	public Item getInventoryItem(int slot) {
		return inventory[slot];
	}
	
	public boolean equip(int slot, Item item) {
		if (equipped[slot] == null) {
			equipped[slot] = item;
			createScrollImage();
			return true;
		}
		return false;
	}
	
	public void rotateEquipped() {
		int start = currEquipped;
		do  {
			currEquipped = (currEquipped + 1) % equipped.length;
		} while(equipped[currEquipped] == null && currEquipped != start);
	}
	
	public void mouseRotated(int rotation) {
		if (open) {
			if (scrollPos + rotation * 25 < 0) {
				scrollPos = 0;
			} else if (scrollPos + rotation * 25 > scrollImage.getHeight() - 330) {
				scrollPos = scrollImage.getHeight() - 330;
			} else {
				scrollPos += rotation * 25;
			}
			
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255, 140));
		g.fillRect(LampPanel.PWIDTH - 24 - 60 * equipped.length, LampPanel.PHEIGHT - 84, equipped.length * 60 + 12, 72);
		
		for (int i = 0; i < equipped.length; i++) {
			g.setColor(new Color(90, 90, 90, 110));
			g.fillRect(LampPanel.PWIDTH - 72 - 60 * i, LampPanel.PHEIGHT - 72, 48, 48);
			if (equipped[i] != null) {
				equipped[i].equipDraw(LampPanel.PWIDTH - 72 - 60 * i, LampPanel.PHEIGHT - 72, g, currEquipped == i);
			}
			
		}
		if (open) {
			g.setColor(new Color(255, 255, 255, 140));
			g.fillRect(LampPanel.PWIDTH / 2 - 250, 80, 500, 400);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Impact", Font.PLAIN, 30));
			g.drawString("Inventory", LampPanel.PWIDTH / 2 - 235, 120);
			g.drawImage(scrollImage, LampPanel.PWIDTH / 2 - 250, 130, LampPanel.PWIDTH / 2 + 250, 460, 0, scrollPos, 500, scrollPos + 330, null);
		}
		
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void openInventory() {
		scrollPos = 0;
		open = true;
	}
	
	public void closeInventory() {
		open = false;
	}
	
	private void createScrollImage() {
		scrollImage = new BufferedImage(500, 70 + inventory.length * 120 + equipped.length * 120, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = scrollImage.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Impact", Font.PLAIN, 24));
		g.drawString("Equipped:" , 20, 25);
		for (int i = 0; i < equipped.length; i++) {
			g.setColor(new Color(90, 90, 90, 110));
			g.fillRect(30, 40 + 120 * i, 440, 108);
			if (equipped[i] != null) {
				equipped[i].inventoryDraw(30, 40 + 120 * i, g);
			}
			
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Impact", Font.PLAIN, 24));
		g.drawString("Not Equipped:" , 20, 45 + equipped.length * 120 + 15);
		for (int i = 0; i < inventory.length; i++) {
			g.setColor(new Color(90, 90, 90, 110));
			g.fillRect(30, 75 + 120 * equipped.length + 120 * i, 440, 108);
			if (inventory[i] != null) {
				inventory[i].inventoryDraw(50, 75 + 120 * equipped.length + 120 * i, g);
			} else {
				g.setColor(new Color(0, 0, 0, 140));
				g.setFont(new Font("Impact", Font.PLAIN, 24));
				g.drawString("Empty Slot", 250 - 50, 70 + 120 * equipped.length + 120 * i + 60);
			}
		}
	}
}
