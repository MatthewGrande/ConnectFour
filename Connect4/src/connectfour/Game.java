package connectfour;

import java.io.*;
import java.util.Random;

/**
 * 
 * @author Matthew Grande
 * @version 2.0 Test suite has been added
 * bot now considers the best move out of all available options
 *  rather than only the leftmost column
 * This class configures the game, and is where the bot is stored,
 *
 */

public class Game {

	static Random move = new Random();

	/**
	 * Contains a while loop that will iterate until there is no space left on the board,
	 * or until one player wins.
	 * @param input takes in an input,and allows the user to place a piece in the specified column
	 * @return returns the player that has won.
	 */

	public static int play (InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration board = new Configuration();
		board.print(); //Prints out the empty board

		int columnPlayed = 0; 
		int player;

		/*
		 *TODO: allow the player to decide who starts
		 *( OR: flip a coin to determine who starts)
		 *I want to see the coin tossing process! 
		 *e.g print out "Would you like heads or tails?"
		 * "the coin landed on heads! you decide who starts!,etc)
		 */

		//first move for player 1 (played by computer) : CURRENTLY: places it in the middle
		board.addDisk(firstMovePlayer1(), 1);
		board.print(); //Print out the board
		int nbTurn = 1;
		String yourMoves = "";

		while (board.spaceLeft) { // While there is space on the board
			//	while (nbTurn < 42) { // maximum of turns allowed by the size of the grid

			player = nbTurn %2 + 1; //Determine which player gets to play.
			//TODO: this ^ will change once we find out who starts.
			if (player == 2){ 
				columnPlayed = getNextMove(keyboard, board, 2);
//				columnPlayed = moveBot(2,columnPlayed,board); // MAKE TWO BOTS PLAY AGAINST EACH OTHER
				yourMoves+=columnPlayed;
			}

			if (player == 1){
				columnPlayed = moveBot(1, columnPlayed, board);
			}

			System.out.println(columnPlayed); // GOING TO REMOVE THIS ONCE I GET A NICE BOARD
			board.addDisk(columnPlayed, player);
			board.print(); //Print out the board, GOING TO REMOVE THIS ONCE I GET A NICE BOARD.

			if (board.wonTheGame(columnPlayed, player)){
				board.print();
				if (player == 1)
					System.out.println("Archon beat you :) ");
				else
					System.out.println("Looks like you won! Upgrades are coming.");
				//System.out.println("Congrats to player " + player + " !");
				System.out.println("Your moves this game were:" + yourMoves);
				return(player);
			}

			nbTurn++;
			if (nbTurn == 42) {
				board.spaceLeft = false;
				System.out.println("The game has ended in a draw!");
			}
		}
		System.out.println("We got here!!");
		return -1;
	}

	/**
	 * 
	 * @param keyboard reads the user's keyboard for their input.
	 * If the input is not an integer between 0 and 6, it will request one.
	 * @param boardState considers the current board's state when adding a piece
	 * @param player Checks to see which player will be playing
	 * @return returns the input if it is correct.
	 */
	private static int getNextMove(BufferedReader keyboard, Configuration boardState, int player){

		int userInput;
		while(true) {
			String error = "";
			try {
				try {
					userInput = Integer.parseInt((keyboard.readLine()));
					if (userInput<0 || userInput > 6)
						error+= "Please select a column between 0 and 6(inclusive!)";
					else if (boardState.available[userInput]>5)
						error+="There is no space in this column! Please select a different one!";
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

	/**
	 * Determines the first move of the bot
	 * @return returns a constant value 3, which is the column where the bot will place the first
	 * token of the game.
	 */
	private static int firstMovePlayer1 (){
		return 3; //The AI starts by placing his piece in the middle of the board(it is the optimal position).
	}

	/**
	 * Checks which column the bot should place its token in
	 * @param lastColumnPlayed takes the last column played by the user
	 * @param board Checks the current board state to analyze its next move 
	 * @return the optimal move for the bot
	 */

	private static int moveBot (int player, int lastColumnPlayed, Configuration board){

		int[] powerRanking = {0,1,2,3,2,1,0};

		//Add the power ranking of a winning move to the array.
		board.connectFour(player, powerRanking); 
		board.connect4TwoTurns(player, powerRanking);
		board.denyWinningMove(player, powerRanking);
		board.denyWinningPosition(player, powerRanking); 
		board.searchWin2Turns(player, powerRanking);
		board.connectThree(player, powerRanking);
		board.moveTowardsConnectThree(player, powerRanking);
		board.avoidLoss(player,powerRanking);
		board.emptyColumns(player, powerRanking);
		return maxPower(powerRanking);
	}

	/**
	 * Determines the maximum power available for the bot.
	 * @param power the array that considers the power of each move.
	 * @return the max power from the powerRanking array
	 */
	private static int maxPower(int[] power) {
		int max=0;
		for (int i=0;i<7;i++) {
			System.out.println("power of "+i+ " is: "+ power[i]);
			if (power[i]>=power[max])
				max=i;
		}
		return max;
	}
}

