package connectfour;

import java.io.*;

public class Play {
	public static void main(String[] args) {
		InputStreamReader input = new InputStreamReader(System.in);
		System.out.println(Game.play(input));
	}

}
