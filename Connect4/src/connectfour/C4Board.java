package connectfour;
import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.*;

/**
 * 
 * @author Matthew Grande
 * @version 3.0 GUI has been added
 *  bot's configuration vastly cleaned up
 * This class sets up the GUI of the board
 *
 */
public class C4Board extends GCompound {

	/** Creates a new C4Board object, which is initially empty. */

	public C4Board() {
		displayFrame();		
		addCirclesToFrame();
	}


	/**
	 * Displays the frame of the connect 4 board.
	 */
	private void displayFrame() {
		GRect frame = new GRect(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setFilled(true);
		frame.setFillColor(Color.BLUE);
		add(frame, 5, 8);
	}
/**
 * Displays empty token slots on the frame.
 * Creates an Array List of slots and adds 42 of them to the list.
 */
	private void addCirclesToFrame() {
		for (int row = 5;row>=0;row--){
			for (int columns=0;columns<7;columns++) {
				//Create White Circles using the board as a reference now!
				//First one is the x axis. Second is the y axis. Third is the radius of the circle. 
				slot = createWhiteCircle(FRAME_WIDTH/7*columns+60,FRAME_HEIGHT/6*row+50,LAMP_RADIUS);
				tokenArray.add(slot);
				add(slot);
			}
		}
	}
	
	/**
	 *Displays specific tokens that are placed to the board.
	 * @param column the column where the token is being played.
	 * @param player the player who is placing a token.
	 */
	public void placeToken(int column, int player) {
		
		Color color;
		
		if (player == 1 ) 
			color = Color.RED;
		else 
			color = Color.YELLOW;
		
		while (!this.tokenArray.get(column).getFillColor().equals(Color.white)) {
			column+=7;
			if (column>41)
				break;
		}
		
		if (column<42)
		this.tokenArray.get(column).setFillColor(color);
	}


	/** Creates a white circle centered at (x,y) with radius r */

	private GOval createWhiteCircle (double x, double y, double r) {
		GOval circle = new GOval(x-r, y-r, 2*r, 2*r);
		circle.setFillColor(Color.WHITE);
		circle.setFilled(true);
		return circle;
	}

	/* Private constants */

	private static final double FRAME_WIDTH = 750;
	private static final double FRAME_HEIGHT = 500;
	private static final double LAMP_RADIUS = 40;

	/* Private instance variables */
	private ArrayList<GOval> tokenArray = new ArrayList<GOval>();
	private GOval slot;


}
