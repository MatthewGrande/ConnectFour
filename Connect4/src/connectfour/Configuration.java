package connectfour;

/**
 * 
 * @author Matthew Grande
 * @version 1.4 bot seeks opportunities to guarantee a win 2 turns in advance
 * It denies win attempts
 * It denies attempts to get into a winning position
 * If it can't it seeks a connect 3
 * If it's confused, it places piece on top of player 2 until those are possible
 * This class configures the board, and is where the where all the "move" methods are stored.
 *
 */
public class Configuration {
	public static final int WIDTH = 7;
	public static final int HEIGHT = 6;
	public int[][] board; 
	public int[] available;
	boolean spaceLeft;

	public Configuration(){
		// Create a 7 by 6 board, with 42 total spaces.
		board = new int[WIDTH][HEIGHT];

		// Initialize an array of integers that will hold the available spots in a column.
		available = new int[HEIGHT+1]; 

		// Initialize a boolean variable that will be false if no further pieces can be placed on the board.
		spaceLeft = true; 
	}

	/**
	 * Prints out the board(Very basic for now!)
	 */

	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Allow a player to add a disk to the board at a specific column.
	 * @param column the column that the turn player is placing their piece in
	 * @param player the turn player
	 */

	public void addDisk (int column, int player){
		this.board[column][this.available[column]] = player;
		(this.available[column])++;
	}

	/**
	 * Remove a disk from the board, to be used in some methods.
	 * @param column the column that we're removing the piece from
	 * @param player the player whose piece we're removing
	 */

	public void removeDisk(int column,int player) {
		this.board[column][this.available[column]-1] = 0;
		(this.available[column])--;
	}

	/**
	 * Determine if a player's move has won them the game.
	 * A player has won if he or she has four consecutive pieces in any direction.
	 * @param lastColumnPlayed Takes the last column played
	 * @param player takes the player that played last
	 * @return true if won, false if lost
	 */

	public boolean wonTheGame (int lastColumnPlayed, int player){
		return hasConnectX(lastColumnPlayed,player,4);
	}

	/**
	 * Returns the column that a player can play to win the game.
	 * @param player Takes the player that it will analyze
	 * @return the move that will win the next round, or -1 if player can't win next round.
	 */

	public int winGameNextTurn (int player){

		for (int column=0;column<7;column++) {
			if (hasSpace(column)) {
				this.addDisk(column, player);//drop a disk, check if they won, then remove it.

				//If this disk wins them the game, remove it and return the column number
				if (this.wonTheGame(column, player)) {
					this.removeDisk(column,player);
					return column;
				}
				this.removeDisk(column,player);
			}
		}
		return -1;
	}

	/**
	 * A recursive function that makes the bot think X turns into the future for a super play.
	 * The bot will look for guaranteed wins X turns ahead.
	 * @param player that is looking for super play
	 * @param x the number of moves that the bot is thinking ahead
	 * @return the column that allows for a guaranteed connect 4 in x turns.
	 */
	public int winGameXTurns(int player, int x) {

		int otherPlayer = enemyPlayer(player);
		int potentialCounterplay=0;

		//Base case
		if (x == 1) {
			return winGameNextTurn(player);
		}

		else {
			//Check if the enemy player can win next turn, if he can this play won't be optimal.
			if (possibleMove(this.winGameNextTurn(otherPlayer)))
				return -1;

			for (int potentialMove=0;potentialMove<7;potentialMove++) {

				//Check if there's space available at column "potentialMove":
				if (hasSpace(potentialMove)) {
					this.addDisk(potentialMove, player);

					//Check if the opponent can stop our next connect four.
					for (potentialCounterplay = 0; potentialCounterplay<7;potentialCounterplay++) {

						/*If there's space in this column, the other player will place a token and 
						 * we'll see if they can deny the victory
						 */

						if (hasSpace(potentialCounterplay)) {
							this.addDisk(potentialCounterplay, otherPlayer);

							/*
							 * If the opponent can place a piece in column "potentialCounterplay"
							 * that makes it so that the Player doesn't win in "X-1" turns,
							 * this move wasn't a game winning play.
							 */

							if (!possibleMove(winGameXTurns(player,x-1))) {
								this.removeDisk(potentialCounterplay, otherPlayer);
								break;
							}
							this.removeDisk(potentialCounterplay, otherPlayer);
						}
					}
					//Let's remove the disk we just placed and try again.
					this.removeDisk(potentialMove, player);

					/*If we iterated through all 7 columns and the opponent can't stop 
					 * the next connect four, player wins in X turns.	
					 * If they can stop it, let's try the next column. */

					if (potentialCounterplay == 7)
						return potentialMove;
				}
			}
			return -1;
		}
	}

	/**
	 * Checks if there is space in a column for a token to be placed
	 * @param column takes the column that is being played
	 * @return true if there is space in the column, false otherwise
	 */
	public boolean hasSpace(int column) {
		return this.available[column]<=5;
	}

	/**
	 * Checks to see if the player has a guaranteed win available, in either 1,2,3, or 4 turns.
	 * Returns the column where this is possible, or -1 if there is no win potential.
	 * @param player the player that is being analyzed
	 * @return the column that will ensure victory
	 */

	public int winningMoveAvailable (int player) {

		for (int turns=1;turns<3;turns++) {
			int 	winningMove = winGameXTurns(player,turns);
			if (winningMove != -1)
				return winningMove;
		}
		return -1;

	}

	/**
	 * Allow the bot to seek out winning positions TWO TURNS in advance and aggressively make plays.
	 * @param player 
	 * @return returns the column that will allow a winning position, or -1 if none are available
	 */
	
	public int searchForWinningPosition(int player) {
		int otherPlayer = enemyPlayer(player);
	
		for (int potentialMove=0;potentialMove<7;potentialMove++) {
			//Check if there's space available at column "potentialMove":
			if (hasSpace(potentialMove)) {
				this.addDisk(potentialMove, player);
				/*Check if this move doesn't put the other player in a winning position
				 * and that the player now has a winning move available
				 */
				if (possibleMove(winningMoveAvailable(player)) 
						&& !possibleMove(winningMoveAvailable(otherPlayer))) {
					removeDisk(potentialMove,player);
					return potentialMove;
				}
				removeDisk(potentialMove,player);
			}
		}
		return -1;
	}

	/**
	 * Allow the bot to seek out winning positions and aggressively make plays.
	 * @param player 
	 * @return returns the column that will allow a winning position, or -1 if none are available
	 */

	//THIS METHOD WILL MOVE MY PIECE INTO A SPOT WHERE I CAN POTENTIALLY WIN NEXT TURN
	public int searchWin2Turns(int player) {
		int otherPlayer = enemyPlayer(player);

		for (int potentialMove=0;potentialMove<7;potentialMove++) {
			//Check if there's space available at column "potentialMove":
			if (hasSpace(potentialMove)) {
				this.addDisk(potentialMove, player);
				/*Check if this move doesn't put the other player in a winning position
				 * and that the player now has a winning move available
				 */
				if (possibleMove(winningMoveAvailable(player)) && !possibleMove(winningMoveAvailable(otherPlayer))) {
					removeDisk(potentialMove,player);
					return potentialMove;
				}
				removeDisk(potentialMove,player);
			}
		}
		return -1;
	}
	/**
	 * Allow the bot to seek out winning positions TWO TURNS in advance and aggressively make plays.
	 * @param player The player that is making the move
	 * @param x The number of turns that the bot will look ahead for a win.
	 * @return returns the column that will allow a winning position, or -1 if none are available
	 */

	public int searchWinXTurns(int player,int x) {
		int otherPlayer = enemyPlayer(player);

		if (x == 2) {
			return searchWin2Turns(player);
		}
		
		else {
			for (int potentialMove=0;potentialMove<7;potentialMove++) {
				//Check if there's space available at column "potentialMove":
				if (hasSpace(potentialMove)) {
					this.addDisk(potentialMove, player);
					/*Check if this move doesn't put the other player in a winning position
					 * and that the player now has a winning move available
					 */
					if (possibleMove(searchWinXTurns(player,x-1)) && !possibleMove(winningMoveAvailable(otherPlayer))) {
						removeDisk(potentialMove,player);
						return potentialMove;
					}
					removeDisk(potentialMove,player);
				}
			}
			return -1;
		}
	}
	/**
	 * Check if there is a connect three on the board
	 * @param lastColumnPlayed takes the location of the last token that was placed
	 * @param player Checks to see which player placed the last token
	 * @return returns true if the player has three consecutive tokens, and false otherwise
	 */
	public boolean hasConnectThree (int lastColumnPlayed, int player){
		return hasConnectX(lastColumnPlayed,player,3);
	}

	/**
	 * 
	 * @param the player that is playing his move
	 * @return the column that will provide the player with a connect three
	 */
	public int connectThreeNextTurn(int player) {

		int otherPlayer = enemyPlayer(player);
		for (int column=0;column<7;column++) {
			if (hasSpace(column)) {
				//drop a disk, check if they have 3 consecutive tokens, then remove it.
				this.addDisk(column, player);

				/*If this disk is the 3rd consecutive token and doesn't make the other player win
				 *remove it and return the column number
				 */
				if (this.hasConnectThree(column, player) 
						&& !possibleMove(this.winningMoveAvailable(otherPlayer))) {
					this.removeDisk(column,player);
					return column;
				}
				this.removeDisk(column,player);
			}
		}
		return -1;
	}

	/**
	 * If the other player has a winning move available, deny it.
	 * @param player the player who is making the move
	 * @return the column that denies the winning play, or -1 if impossible(game's done).
	 */

	public int denyWinningMove(int player) {
		//We have observed that: winningMoveAvailable(otherPlayer != -1)
		int otherPlayer = enemyPlayer(player);
		for (int column=0;column<7;column++) {
			if (hasSpace(column)) {
				this.addDisk(column, player);
				//If the opponent no longer has a winning move available, this is the piece to place.
				if (!possibleMove(this.winningMoveAvailable(otherPlayer))) {
					this.removeDisk(column, player);
					System.out.println("We're going to deny that!");
					return column;
				}
				this.removeDisk(column, player);
			}
		}
		System.out.println("That's not stoppable, sorry");
		return -1;
	}

	/**
	 * If the other play can move into a winning position next turn, deny it.
	 * @param player the player that is trying to make the defense.
	 * @return the column that makes it possible, or -1 otherwise.
	 */
	public int denyWinningPosition(int player) {
		int otherPlayer = enemyPlayer(player);
		for (int potentialMove=0;potentialMove<7;potentialMove++) {
			if (hasSpace(potentialMove))
				this.addDisk(potentialMove, player);
			//If the opponent can no longer search for a winning position
			//AND if the opponent can't win next turn.
			if (!possibleMove(this.winningMoveAvailable(otherPlayer)) 
					&& !possibleMove(searchForWinningPosition(otherPlayer))) {
				this.removeDisk(potentialMove, player);
				return potentialMove;
			}
			this.removeDisk(potentialMove, player);
		}
		return -1;
	}


	/**
	 * The bot will attempt to set up a connect three
	 * @param player the player that is playing his or her move
	 * @return returns the column that will set up a connect 3
	 */

	public int moveTowardsConnectThree(int player) {
		int otherPlayer = enemyPlayer(player);

		//Make sure we have space in the columns
		for (int column=0;column<7;column++) {
			if (hasSpace(column)) {
				this.addDisk(column, player);

				// Make sure the move we did didn't push the opponent into a winning position
				if ( possibleMove(connectThreeNextTurn(player)) 
						&& winningMoveAvailable(otherPlayer) == -1) {
					this.removeDisk(column, player);
					return column;
				}
				this.removeDisk(column, player);
			}
		}
		return -1;
	}

	/**
	 * Determines who the opposing player is
	 * @param player takes the current player
	 * @return the enemy player
	 */

	private int enemyPlayer(int player) {
		int otherPlayer;
		if (player == 1)
			otherPlayer = 2;
		else
			otherPlayer = 1;
		return otherPlayer;
	}

	/**
	 * This method will return true if there is a possible move from the method used in the 
	 * parameter.
	 * @param result takes in the int result produced by other methods, -1 if failed, or an integer 
	 * between 0 and 6 inclusive if successful
	 * @return a boolean value true if successful, and false if failed.
	 */
	private static boolean possibleMove(int result) {
		if (result != -1) return true;
		else return false;
	}

	/**
	 * Checks if there is a connect "X" on the board. This method will 
	 * be called by the "wonTheGame" method, which will check for connect 4,
	 * and the "hasConnectThree method", which will check for connect 3.
	 * @param lastColumnPlayed takes the location of the last token that was placed
	 * @param player Checks to see which player placed the last token
	 * @param x Number of tokens in a row
	 * @return true if there are "X" tokens in a row, and false if not
	 */
	private boolean hasConnectX (int lastColumnPlayed, int player, int x){

		//TODO: make methods for each connectX in a row(one for below, one for top left,etc)
		//X in a row BELOW said piece:
		int inARowBelow = 0;
		int row = this.available[lastColumnPlayed] - 1;
		int column = lastColumnPlayed;
		while (this.board[column][row] == player) {
			row--;
			inARowBelow++;
			if (inARowBelow == x)
				return true;
			if (row<0)
				break;
		}
		//System.out.println("In a row BELOW: "+ inARowBelow);

		//X in a row TOP LEFT of said piece:
		int inARowTopLeft=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column--;
			row++;
			inARowTopLeft++;
			if (inARowTopLeft == x)
				return true;
			if (row>5 || column<0)
				break;
		}
		//System.out.println("In a row TOP LEFT: "+ inARowTopLeft);

		//X in a row LEFT of said piece:
		int inARowLeft=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column--;
			inARowLeft++;
			if (inARowLeft == x)
				return true;
			if (column<0)
				break;
		}
		//System.out.println("In a row LEFT: "+ inARowLeft);

		//X in a row BOTTOM LEFT of said piece:
		int inARowBottomLeft=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column--;
			row--;
			inARowBottomLeft++;
			if (inARowBottomLeft == x)
				return true;
			if (column<0 || row<0)
				break;
		}
		//System.out.println("In a row BOTTOM LEFT: "+ inARowBottomLeft);

		//X in a row BOTTOM RIGHT of said piece:
		int inARowBottomRight=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column++;
			row--;
			inARowBottomRight++;
			if (inARowBottomRight == x)
				return true;
			if (column>6 || row<0)
				break;
		}
		//System.out.println("In a row BOTTOM RIGHT: "+ inARowBottomRight);

		//X in a row RIGHT of said piece:
		int inARowRight=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column++;
			inARowRight++;
			if (inARowRight == x)
				return true;
			if (column>6)
				break;
		}
		//System.out.println("In a row RIGHT: "+ inARowRight);

		//X in a row TOP RIGHT of said piece:
		int inARowTopRight=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column++;
			row++;
			inARowTopRight++;
			if (inARowTopRight == x)
				return true;
			if (column>6 || row>5)
				break;
		}
		//System.out.println("In a row TOP RIGHT: "+ inARowTopRight);

		//Don't have to check in a row ABOVE because this is the last piece.

		//Check if the piece is within a connect "X" diagonally:
		//The piece has been considered twice, so subtract one

		if((inARowTopRight + inARowBottomLeft-1)>= x)
			return true;
		if ((inARowTopLeft+inARowBottomRight-1)>=x)
			return true;

		//Check if the piece is within a connect "X" horizontally:
		//The piece has been considered twice, so subtract one
		if ((inARowLeft+inARowRight-1)>=x)
			return true;

		return false; // The player does  not have connect "X".
	}

	//TODO: implement a USEFUL connect three method, so the bot doesn't attempt to connect three
	//OUTSIDE of the board!!

}
