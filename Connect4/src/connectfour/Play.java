package connectfour;

import java.io.*;
/**
 * 
 * @author Matthew Grande
 * @version 1.4 bot seeks opportunities to guarantee a win 2 turns in advance
 * It denies win attempts
 * It denies attempts to get into a winning position
 * If it can't it seeks a connect 3
 * If it's confused, it places piece on top of player 2 until those are possible
 * This class allows the user to play the game
 *
 */
public class Play {
	public static void main(String[] args) {
		InputStreamReader input = new InputStreamReader(System.in);
		System.out.println(Game.play(input));
	}
}
