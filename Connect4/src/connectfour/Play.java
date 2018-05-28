package connectfour;

import java.io.*;
/**
 * 
 * @author Matthew Grande
 * @version 1.2 bot places piece on top of player 2 until it sees a winning opportunity
 * This class allows the user to play the game
 *
 */
public class Play {
	public static void main(String[] args) {
		InputStreamReader input = new InputStreamReader(System.in);
		System.out.println(Game.play(input));
	}
}
