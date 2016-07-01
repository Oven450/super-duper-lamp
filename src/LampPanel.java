import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class LampPanel extends JPanel implements Runnable {

	public static final int PWIDTH = 1024;
	public static final int PHEIGHT = 576;
	public static final int PERIOD = 0;

	Thread animator;
	boolean running = false;
	boolean isPaused = false;
	boolean gameOver = false;

	Image dbImage;
	Graphics dbg;
	
	Handler handler;
	private int frames = 0;
	private long lastRead = 0;

	public LampPanel() {
		this.setDoubleBuffered(false);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		
		setFocusable(true);
		requestFocus();
		
		this.handler = new Handler(this);
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				handler.keyPressed(e.getKeyCode());
			}
			
			public void keyReleased(KeyEvent e) {
				handler.keyReleased(e.getKeyCode());
			}
		});
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					handler.mouseLeftClicked(e.getX(), e.getY());
					//System.out.println("1");
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					handler.mouseMiddleClicked(e.getX(), e.getY());
					//System.out.println("2");
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					handler.mouseRightClicked(e.getX(), e.getY());
					//System.out.println("3");
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					handler.mouseLeftReleased(e.getX(), e.getY());
					//System.out.println("1");
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					handler.mouseMiddleReleased(e.getX(), e.getY());
					//System.out.println("2");
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					handler.mouseRightReleased(e.getX(), e.getY());
					//System.out.println("3");
				}
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				handler.mouseMoved(e.getX(), e.getY());
			}
			
			public void mouseDragged(MouseEvent e) {
				handler.mouseMoved(e.getX(), e.getY());
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
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

		beforeTime = System.nanoTime();

		running = true;

		while (running) {
			gameUpdate();
			gameRender();
			paintScreen();
			
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
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else { // sleepTime <= 0; the frame took longer than the period
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;
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
			handler.update();
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

		// clear the background
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
		
		handler.draw(dbg);
		
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
	

}
