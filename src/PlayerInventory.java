import java.awt.Color;
import java.awt.Graphics;


public class PlayerInventory {
	
	Player player;
	Handler handler;
	Item[] equipped;
	Item[] inventory;
	int currEquipped;
	
	
	public PlayerInventory(Player player, Handler handler, int inventorySize, int equipSlots) {
		this.player = player;
		this.handler = handler;
		
		this.equipped = new Item[equipSlots];
		this.inventory = new Item[inventorySize];
		currEquipped = 0;
	}
	
	public boolean addToInventory(Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				inventory[i] = item;
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
	
	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255, 140));
		g.fillRect(LampPanel.PWIDTH - 24 - 60 * equipped.length, LampPanel.PHEIGHT - 84, equipped.length * 60 + 12, 72);
		
		for (int i = 0; i < equipped.length; i++) {
			g.setColor(new Color(90, 90, 90, 110));
			g.fillRect(LampPanel.PWIDTH - 72 - 60 * i, LampPanel.PHEIGHT - 72, 48, 48);
			if (equipped[i] != null) {
				equipped[i].inventoryDraw(LampPanel.PWIDTH - 72 - 60 * i, LampPanel.PHEIGHT - 72, g, currEquipped == i);
			}
			
		}
	}
	
}
