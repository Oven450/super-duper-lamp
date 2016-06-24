import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class LampPanel extends JPanel implements Runnable {

	public static final int PWIDTH = 1024;
	public static final int PHEIGHT = 576;
	public static final int PERIOD = 33;

	Thread animator;
	boolean running = false;
	boolean isPaused = false;
	boolean gameOver = false;

	Image dbImage;
	Graphics dbg;
	int ballX = 0;
	int ballY = 0;
	int ballVelX = 0;
	int ballVelY = 0;

	public LampPanel() {
		super();
		this.setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		
		ballX = (int) (Math.random() * (PWIDTH - 20) + 10);
		ballY = (int) (Math.random() * (PHEIGHT - 20) + 10);
		
		ballVelX = (int) (Math.random() * 10 + 10);
		ballVelY = (int) (Math.random() * 10 + 10);
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

			/*
			 * If frame animation is taking too long, update the game state
			 * without rendering it, to get the updates/sec nearer to the
			 * required FPS.
			 */

		}

		System.exit(0); // so window disappears
	} // end of run()

	private void gameUpdate() {
		if (!isPaused && !gameOver) {
			if (ballX < 10) {
				ballVelX *= -1;
				ballX = 10;
			}
			if (ballY < 10) {
				ballVelY *= -1;
				ballY = 10;
			}
			if (ballX > PWIDTH - 10) {
				ballVelX *= -1;
				ballX = PWIDTH - 10;
			}
			if (ballY > PHEIGHT - 10) {
				ballVelY *= -1;
				ballY = PHEIGHT - 10;
			}
			
			ballX += ballVelX;
			ballY += ballVelY;
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
		
		dbg.setColor(new Color((int) ((double)Math.random() * 256), (int) ((double)Math.random() * 256), (int) ((double)Math.random() * 256)));
		//dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
		
		dbg.fillOval(ballX - 10, ballY - 10, 20, 20);
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
