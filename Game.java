package assignment4Game;

import java.io.*;
import java.util.Random;

public class Game {

	static Random move = new Random();
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		c.print();
		int columnPlayed = 3; int player;

		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		c.print();
		int nbTurn = 1;

		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}

			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			c.print();
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}

	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){

		int number;
		while(true) {
			String error = "";
			try {
				number = Integer.parseInt((keyboard.readLine()));
				if (number<0 || number > 6)
					error+= "Please select a column between 0 and 6(inclusive!)";
				else if (c.available[number]>5)
					error+="There is no space in this column, please try again!";
				if(error.length() !=0)
					System.out.println(error);
				else
					return number;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int firstMovePlayer1 (){
		return move.nextInt(7); // The AI starts at a random place on the board
	}

	public static int movePlayer1 (int columnPlayed2, Configuration c){

		int last = columnPlayed2;
		int i=1;
		if (c.canWinNextRound(1) != -1) // if the AI can win next turn, he will
			return c.canWinNextRound(1);

		if (c.canWinTwoTurns(1) != -1)
			return c.canWinTwoTurns(1); // if the AI can win in two turns, he will

		if (c.available[last]<=5) // otherwise, he'll put a piece on top of yours.
			return last;

		//If there's no space, he'll place his piece next to it if possible
		else {
			while(true)
			{
				if ((last-i)>=0 && c.available[last-i]<=5)
					return (last-i);
				if ((last+i)<=6 && c.available[last+i]<=5 )
					return (last+i);
				i++;
			}
		}
	}

}
