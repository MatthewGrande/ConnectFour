package connectfour;

import java.io.*;
import java.util.Random;

/**
 * 
 * @author Matthew Grande
 * @version 1.3 bot seeks opportunities to guarantee a win 2 turns in advance
 * If it can't it seeks a connect 3
 * If it's confused, it places piece on top of player 2 until those are possible
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

		while (board.spaceLeft) { // While there is space on the board
			//	while (nbTurn < 42) { // maximum of turns allowed by the size of the grid

			player = nbTurn %2 + 1; //Determine which player gets to play.
			//TODO: this ^ will change once we find out who starts.
			if (player == 2){ 
				columnPlayed = getNextMove(keyboard, board, 2);
			}

			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, board);
			}

			System.out.println(columnPlayed); // GOING TO REMOVE THIS ONCE I GET A NICE BOARD
			board.addDisk(columnPlayed, player);
			board.print(); //Print out the board, GOING TO REMOVE THIS ONCE I GET A NICE BOARD.

			if (board.wonTheGame(columnPlayed, player)){
				board.print();
				System.out.println("Congrats to player " + player + " !");
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
	public static int getNextMove(BufferedReader keyboard, Configuration boardState, int player){

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
	public static int firstMovePlayer1 (){
		//return move.nextInt(7); // The AI starts at a random place on the board between 0 and 6
		return 3; //The AI starts by placing his piece in the middle of the board(it is the optimal position).
	}

	/**
	 * Checks which column the bot should place its token in
	 * @param lastColumnPlayed takes the last column played by the user
	 * @param board Checks the current board state to analyze its next move 
	 * @return the optimal move for the bot
	 */

	public static int movePlayer1 (int lastColumnPlayed, Configuration board){

		int i=1;
		int bot = 1;
		int human = 2;

		if (board.winningMoveAvailable(bot) != -1 ) //if the AI has a winning move available, it'll play it.
		{
			System.out.println("Bot just won the game btw! ");
			return board.winningMoveAvailable(bot);
		}

		else if (board.winGameNextTurn(human) != -1) // else if the human can win next turn, the AI will stop him.
		{
			System.out.println("Bot is trying to deny a winning move! ");
			return board.winGameNextTurn(human);
		}
		else if (board.winGameTwoTurns(human) != -1) // else if the human can win in two turns, the AI will stop him
			//TODO: THIS ISNT ALWAYS A  CORRECT WAY OF STOPPING THE PLAY!!!
			//IT OFTEN IS THOUGH!!
		{
			System.out.println("Bot is trying to deny a foreseen winning move! ");
			return board.winGameTwoTurns(human);
		}
		//The bot will then aggressively seek a winning position
		else if (board.getInWinningPosition(bot) != -1)
		{
			System.out.println("Bot is trying to move into a winning position! ");
			return board.getInWinningPosition(bot);
		}

		//The bot will then seek connect threes to set himself up
		else if (board.connectThreeNextTurn(bot) != -1)
		{
			System.out.println("Bot is getting a connect three!");
			return board.connectThreeNextTurn(bot);
		}
		
		else if (board.moveTowardsConnectThree(bot) != -1) {
			System.out.println("The bot's setting up a connect three!");
			return board.moveTowardsConnectThree(bot);
		}

		else if (board.hasSpace(lastColumnPlayed)) // otherwise, he'll put a piece on top of yours if there is space
		{
			System.out.println("Bot is just copying you! ");
			return lastColumnPlayed;
		}

		//Bad way of determining where he'll place a piece if the column is filled.
		else {
				System.out.println("Bot doesn't know what to do! ");
			while(true)
			{
				if ((lastColumnPlayed-i)>=0 && board.hasSpace(lastColumnPlayed-i))
					return (lastColumnPlayed-i);
				if ((lastColumnPlayed+i)<=6 && board.hasSpace(lastColumnPlayed+i))
					return (lastColumnPlayed+i);
				i++;
			}
		}
	}

}
