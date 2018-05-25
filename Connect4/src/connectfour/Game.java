package connectfour;

import java.io.*;
import java.util.Random;

public class Game {

	static Random move = new Random();

	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		c.print();
		int columnPlayed = 3; int player;

		/*
		 *TODO: allow the player to decide who starts
		 *( OR: flip a coin to determine who starts)
		 *I want to see the coin tossing process! 
		 *e.g print out "Would you like heads or tails?"
		 * "the coin landed on heads! you decide who starts!,etc)
		 */

		//first move for player 1 (played by computer) : CURRENTLY: randomly placed on the board
		c.addDisk(firstMovePlayer1(), 1);
		c.print(); //Print out the board
		int nbTurn = 1;

		//while (c.spaceLeft) // While there is space on the board
		
		while (nbTurn < 42) { // maximum of turns allowed by the size of the grid
		
			player = nbTurn %2 + 1; //Determine which player gets to play.
			//TODO: this ^ will change once we find out who starts.
			if (player == 2){ 
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}

			System.out.println(columnPlayed); // GOING TO REMOVE THIS ONCE I GET A NICE BOARD
			c.addDisk(columnPlayed, player);
			c.print(); //Print out the board, GOING TO REMOVE THIS ONCE I GET A NICE BOARD.
			
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			
			nbTurn++;
			
			//if (nbTurn == 42)
			//	c.spaceLeft = false;
			// System.out.println("The game has ended in a draw!");
		}
		return -1;
	}

	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){

		int userInput;
		while(true) {
			String error = "";
			try {
				try {
					userInput = Integer.parseInt((keyboard.readLine()));
					if (userInput<0 || userInput > 6)
						error+= "Please select a column between 0 and 6(inclusive!)";
					else if (c.available[userInput]>5)
						error+="There is no space in this column, please try again!";
					if(error.length() !=0)
						System.out.println(error);
					else
						return userInput;
				} catch (IOException e) { // Make sure the input was read correctly.
					e.printStackTrace();
				}
			}
			catch (NumberFormatException e) { // Make sure the user input a number.
				System.out.println("Please select a column between 0 and 6(inclusive!)");
			} 

		}
	}

	public static int firstMovePlayer1 (){
		//return move.nextInt(7); // The AI starts at a random place on the board
		return 3; //The AI starts by placing his piece in the middle of the board(it is the optimal position).
	}

	public static int movePlayer1 (int columnPlayed2, Configuration c){

		//TODO: add a "if total space isn't 42"
		int last = columnPlayed2;
		int i=1;
		if (c.canWinNextRound(1) != -1) // if the AI can win next turn, he will
			return c.canWinNextRound(1);
		
		else if (c.canWinNextRound(2) != -1) // else if the human can win next turn, the AI will stop him.
			return c.canWinNextRound(2);
		
		else if (c.canWinTwoTurns(1) != -1)
			return c.canWinTwoTurns(1); // if the AI can win in two turns, he will
		
		else if (c.canWinTwoTurns(2) != -1) // else if the human can win in two turns, the AI will stop him
			return c.canWinTwoTurns(2);
		
		else if (c.available[last]<=5) // otherwise, he'll put a piece on top of yours.
			return last;

		else {
			//If there's no space, he'll place his piece next to it if possible
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
