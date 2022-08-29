import java.awt.Rectangle;
import java.util.Random;

/**
 * Deals with the creation and location of the food for the game
 */
public class Apple {

	private int x; //initialize apple coordinates
	private int y;
	private Random random;
	
	/**
	 * Creates a new apple generated at a random location
	 * @param player : Needed to ensure the apple generated does not collide with player on spawn
	 */
	public Apple(Snake player) {
		random = new Random();
		this.generateFood(player);
	}
	
	/**
	 * Generates a new apple at a random location and ensures that the apples location
	 * does not collide with the snake player immediately (the spawn location cannot be inside of the 
	 * snake body)
	 * @param player : Needed to ensure the apple generated does not collide with player on spawn
	 */
	public void generateFood(Snake player) {	
		//make sure the food coordinates are not on top of snake
		//check to make sure and if they are generate new coordinates for food
		boolean onSnake = true;
		while (onSnake) {
			onSnake = false;
			x = random.nextInt((int)(GamePanel.SCREEN_WIDTH/GamePanel.UNIT_SIZE)) * GamePanel.UNIT_SIZE;
			y = random.nextInt((int)(GamePanel.SCREEN_HEIGHT/GamePanel.UNIT_SIZE)) * GamePanel.UNIT_SIZE;
			for (Rectangle r : player.getBody()) {
				if (((int) r.getX()) == x && ((int) r.getY()) == y) {
					onSnake = true;
				}
			}
		}
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
