package main_app;
import game.GameHandler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import multiplayer.MultiplayerGameHandler;
import server.LampClientHandler;
import server.LampServerHandler;
import world_collision.WorldCollisionHandler;

public class LampPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 8567230218593415991L;
	public static int PWIDTH = 1024;
	public static int PHEIGHT = 576;
	public static final int PERIOD = 33333333;

	Thread animator;
	boolean running = false;
	boolean isPaused = false;
	boolean gameOver = false;

	Image dbImage;
	Graphics dbg;
	
	Handler currHandler;
	private int frames = 0;
	private long lastRead = 0;
	
	MainMenuHandler menuHandler;

	public LampPanel() {
		this.setDoubleBuffered(false);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		
		setFocusable(true);
		requestFocus();
		
		this.menuHandler = new MainMenuHandler(this);
		this.currHandler = menuHandler;
		
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (currHandler != null) {
					currHandler.keyPressed(e.getKeyCode());
				}
			}
			
			public void keyReleased(KeyEvent e) {
				if (currHandler != null) {
					currHandler.keyReleased(e.getKeyCode());
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (currHandler != null) {
						currHandler.mouseLeftClicked(e.getX(), e.getY());
					}
					//System.out.println("1");
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					if (currHandler != null) {
						currHandler.mouseMiddleClicked(e.getX(), e.getY());
					}
					//System.out.println("2");
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					if (currHandler != null) {
						currHandler.mouseRightClicked(e.getX(), e.getY());
					}
					//System.out.println("3");
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (currHandler != null) {
						currHandler.mouseLeftReleased(e.getX(), e.getY());
					}
					//System.out.println("1");
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					if (currHandler != null) {
						currHandler.mouseMiddleReleased(e.getX(), e.getY());
					}
					//System.out.println("2");
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					if (currHandler != null) {
						currHandler.mouseRightReleased(e.getX(), e.getY());
					}
					//System.out.println("3");
				}
			}
			
		});
		
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (currHandler != null) {
					currHandler.mouseWheelRotated(e.getWheelRotation());
				}
				
			}
			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (currHandler != null) {
					currHandler.mouseMoved(e.getX(), e.getY());
				}
			}
			
			public void mouseDragged(MouseEvent e) {
				if (currHandler != null) {
					currHandler.mouseMoved(e.getX(), e.getY());
				}
			}
		});
	}

	public void addNotify()
	// wait for the JPanel to be added to the JFrame before starting
	{
		super.addNotify(); // creates the peer
		startGame(); // start the thread
	}

	private void startGame()
	// initialize and start the thread
	{
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	} // end of startGame()

	// ------------- game life cycle methods ------------
	// called by the JFrame's window listener methods

	public void resumeGame()
	// called when the JFrame is activated / deiconified
	{
		isPaused = false;
	}

	public void pauseGame()
	// called when the JFrame is deactivated / iconified
	{
		isPaused = true;
	}

	public void stopGame()
	// called when the JFrame is closing
	{
		running = false;
	}

	// ----------------------------------------------

	public void run()
	/* The frames of the animation are drawn inside the while loop. */
	{
		long beforeTime, afterTime, timeDiff, sleepTime;
		//long overSleepTime = 0L;
		//int noDelays = 0;
		//long excess = 0L;

		beforeTime = System.nanoTime();

		running = true;

		while (running) {
			gameUpdate();
			gameRender();
			paintScreen();
			requestFocus();
			
			if (System.nanoTime() - lastRead > 1000000000) {
				lastRead = System.nanoTime();
				System.out.println(frames + " FPS");
				frames = 1;
			} else {
				frames++;
			}

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (PERIOD - timeDiff);

			if (sleepTime > 0) { // some time left in this cycle
				try {
					Thread.sleep(sleepTime / 1000000L); // nano -> ms
				} catch (InterruptedException ex) {
				}
				//overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else { // sleepTime <= 0; the frame took longer than the period
				//excess -= sleepTime; // store excess time value
				//overSleepTime = 0L;
				try {
					Thread.sleep(5); // nano -> ms
				} catch (InterruptedException ex) {
				}

			}

			beforeTime = System.nanoTime();

		}

		System.exit(0); // so window disappears
	} // end of run()

	private void gameUpdate() {
		if (!isPaused && !gameOver) {
			if (currHandler != null) {
				currHandler.update();
			}
		}
	} // end of gameUpdate()

	private void gameRender() {
		if (dbImage == null) {
			dbImage = createImage(PWIDTH, PHEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else
				dbg = dbImage.getGraphics();
		}

		
		dbg.setColor(new Color(0, 0, 0));
		((Graphics2D) dbg).setTransform(new AffineTransform(1, 0, 0, 1, 0, 0));
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
		
		// clear the background
				if (currHandler != null) {
					currHandler.draw(dbg);
				}
		
	}

	private void paintScreen()
	// use active rendering to put the buffered image on-screen
	{
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	} // end of paintScreen()
	
	public void startGameHandler() {
		this.currHandler = new GameHandler(this);
	}

	public void toMainMenu() {
		this.currHandler = menuHandler;
		
	}

	public void startWorldCollisionEditor() {
		this.currHandler = new WorldCollisionHandler(this);
	}

	public void resized(int width, int height) {
		// TODO Auto-generated method stub
		this.setPreferredSize(new Dimension(width, height));
		LampPanel.PHEIGHT = height;
		LampPanel.PWIDTH = width;
	}

	public void startClient() {
		this.currHandler = new LampClientHandler(this);
	}
	
	public void startServer() {
		this.currHandler = new LampServerHandler(this);
	}

	public void startMultiplayerGame() {
		this.currHandler = new MultiplayerGameHandler(this);
	}

}
