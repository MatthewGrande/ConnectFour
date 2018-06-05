package connectfour;

import java.io.*;
/**
 * 
 * @author Matthew Grande
 * @version 2.0 Test suite has been added
 * bot now considers the best move out of all available options
 *  rather than only the leftmost column
 * This class allows the user to play the game
 *
 */
public class Play {
	public static void main(String[] args) {
		InputStreamReader input = new InputStreamReader(System.in);
		System.out.println(Game.play(input));
	}
}
