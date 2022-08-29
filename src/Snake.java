import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Includes all properties and movement of the snake (player)
 */
public class Snake {
	
	private final int STARTING_SIZE = 3; //starting body parts for snake (This should be no bigger than SCREEN_WIDTH/2 - 1)
	private ArrayList<Rectangle> body; //contains all snake body parts
	private char direction; //the current direction of the snake
	//u, h, w used for ease of writing code
	private int u = GamePanel.UNIT_SIZE; //game unit size (size of each grid piece)
	private int h = GamePanel.SCREEN_HEIGHT;
	private int w = GamePanel.SCREEN_WIDTH;
	
	/**
	 * Creates a new snake by populating the body with STARTING_SIZE body parts
	 * The snake will start in the middle of the screen facing right
	 */
	public Snake() {
		//creates a list where each snake body part will be stored
		body = new ArrayList<>();
		
		//creates 3 body parts with the head in the middle of screen
		//and the tail 2 squares to the left of the head
		//the snake will start off by default with STARTING_SIZE body parts
		Rectangle bodyPart;
		for (int i = 0; i < STARTING_SIZE; i++) {
			bodyPart = new Rectangle(u, u);
			bodyPart.setLocation((w / 2) - (u * i), h / 2);
			body.add(bodyPart);
		}
		direction = 'R';
	}
	
	/**
	 * Moves the snake while ensuring it doesn't turn around and eat itself
	 * Pre-Condition: whenever you call move you should always check if the game is in RUNNING state
	 */
	public void move() {
		Rectangle head = body.get(0);
		Rectangle newHead = new Rectangle(u, u);
		
		switch (direction) {
		case 'U':
			newHead.setLocation(head.x, head.y - u);
			break;
		case 'D':
			newHead.setLocation(head.x, head.y + u);
			break;
		case 'L':
			newHead.setLocation(head.x - u, head.y);
			break;
		case 'R':
			newHead.setLocation(head.x + u, head.y);
			break;
		}
		
		body.add(0, newHead);
		body.remove(body.size() - 1);
	}
	
	/**
	 * Similar to move method but instead does not remove the last body part in body list
	 * since the snake has eaten an apple and has to grow, meaning its body part count needs to 
	 * increase by 1
	 */
	public void moveAndGrow() {
		Rectangle head = body.get(0);
		Rectangle newHead = new Rectangle(u, u);
		
		switch (direction) {
		case 'U':
			newHead.setLocation(head.x, head.y - u);
			break;
		case 'D':
			newHead.setLocation(head.x, head.y + u);
			break;
		case 'L':
			newHead.setLocation(head.x - u, head.y);
			break;
		case 'R':
			newHead.setLocation(head.x + u, head.y);
			break;
		}
		
		body.add(0, newHead);
	}
	
	public ArrayList<Rectangle> getBody() {
		return body;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}
	
}
