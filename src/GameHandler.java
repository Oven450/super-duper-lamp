import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;


public class GameHandler extends Handler {

	Player player;
	World world;
	boolean paused = false;
	
	PauseMenu pauseMenu;
	boolean escapeable;
	
	public GameHandler(LampPanel panel) {
		super(panel);
		
		world = new World(this);
		player = new Player(this);
		pauseMenu = new PauseMenu(this);
	}
	
	@Override
	public void mouseClickedGO(Point p) {
		if (!paused) {
			
		} else {
			pauseMenu.mouseClicked(p);
		}
		System.out.println(p);
	}
	
	@Override
	public void mouseMovedGO(Point p) {
		if (paused) {
			pauseMenu.mouseMoved(new Point(mouseX, mouseY));
		}
	}
	
		
	@Override
	public void updateGO() {
		if (!paused) {
			world.update();
			player.update();
		}
		
		if (this.keyDown(KeyEvent.VK_ESCAPE) && escapeable) {
			escapeable = false;
			if (!paused) {
				pause();
			} else {
				resume();
			}
		} else if (!this.keyDown(KeyEvent.VK_ESCAPE) && !escapeable) {
			escapeable = true;
		}
	}
	
	public void draw(Graphics g) {
		if (!paused) {
			world.draw(g);
			player.draw(g);
		} else {
			world.draw(g);
			pauseMenu.draw(g);
		}
		
	}
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	

}
