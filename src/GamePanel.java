import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 600; 
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; //size per thing on screen
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE); //total units available in game
	static final int DELAY = 75; //time before something happens
	boolean running = false; //the game state
	int applesEaten; //apple score
	
	private Snake player;
	private Apple apple;
	private Timer timer;
	
	/**
	 * Initializes additional game settings and starts the game
	 */
	GamePanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	/** 
	 * starts the game and sets up necessary variables
	 */
	public void startGame() {
		player = new Snake();
		apple = new Apple(player);
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	/**
	 * paint graphics
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	/**
	 * Draws all components of the game
	 * @param g : Used to draw items on the window
	 */
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (running) { //person is playing and have not gotten a game over yet
			//draw a grid with given game sizes
			for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g2d.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //draw vertical lines
				g2d.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); //draw horizontal lines
			}
			
			//draw the apple
			g2d.setColor(Color.red); //this sets the color that will be used to draw
			g2d.fillRect(apple.getX(), apple.getY(), UNIT_SIZE, UNIT_SIZE); //this fills the apple so that it matches the size of a grid section
			
			//draw the moving snake
			for (Rectangle r : player.getBody()) {
				g2d.setColor(Color.green);
				g2d.fill(r);
			}
		}
		else { //game over has been reached
			gameOver(g);
		}
	}
	
	
	/**
	 * Checks if the snake head has collided/eaten with an apple
	 * and increments the body parts of the snake, the score, and 
	 * creates a new apple at a random location
	 * 
	 * @return True if a collision occurred, false otherwise
	 */
	public boolean appleCollision() {
		Rectangle head = player.getBody().get(0);
		if ((head.x == apple.getX()) && (head.y == apple.getY())) {
			applesEaten++;
			apple.generateFood(player);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the snake collides with its body or border of screen
	 */
	public void checkCollisions() {
		//head to body collision
		ArrayList<Rectangle> body = player.getBody();
		Rectangle head = body.get(0);
		for (int i = 1; i < body.size(); i++) {
			if ((body.get(i).x == head.x) && (body.get(i).y == head.y)) {
				running = false;
			}
		}
		//head to border collision
		if (head.x < 0) { //left border
			running = false;
		}
		if (head.x > SCREEN_WIDTH - UNIT_SIZE) { //right border
			running = false;
		}
		if (head.y < 0) { //top border
			running = false;
		}
		if (head.y > SCREEN_HEIGHT - UNIT_SIZE) { //bottom border
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	/**
	 * display the game over screen using text
	 * @param g
	 */
	public void gameOver(Graphics g) {
		//game over score
		g.setColor(Color.white);
		g.setFont(new Font("Calibre", Font.PLAIN, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		//game over text
		g.setFont(new Font("Calibre", Font.PLAIN, 75));
		metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		g.setFont(g.getFont().deriveFont(31.0f));
		g.drawString("Press SPACEBAR to Restart", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2 + 75);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			if (appleCollision()) {
				player.moveAndGrow();
			} else {
				player.move();
			}
			//player.move();
			//checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	//inner class for keys
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		/**
		 * helps to control the snake and game based on input
		 * @param e : the key pressed
		 */
		public void keyPressed(KeyEvent e) {
			char direction = player.getDirection();
			//switches for all 4 arrow keys
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					player.setDirection('U');
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					player.setDirection('D');
				}
				break;
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					player.setDirection('L');
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					player.setDirection('R');
				}
				break;
			case KeyEvent.VK_SPACE: //reset game only when it is not running
				if (!running) {
					//reset game and repaint the canvas to the default settings
					player = new Snake();
					apple = new Apple(player);
					applesEaten = 0; 
					running = true;
					timer.start();
					repaint();
				}
			}
		}
	}

}
