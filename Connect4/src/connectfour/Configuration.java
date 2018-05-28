package connectfour;

/**
 * 
 * @author Matthew Grande
 * @version 1.2 bot places piece on top of player 2 until it sees a winning opportunity
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

		// Four in a row BELOW said piece:
		int inARowBelow = 0;
		int row = this.available[lastColumnPlayed] - 1;
		int column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			row--;
			inARowBelow++;
			if (inARowBelow == 4)
				return true;
			if (row<0)
				break;
		}
		//System.out.println("In a row BELOW: "+ inARowBelow);

		//Four in a row TOP LEFT of said piece:
		int inARowTopLeft=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column--;
			row++;
			inARowTopLeft++;
			if (inARowTopLeft == 4)
				return true;
			if (row>5 || column<0)
				break;
		}
		//System.out.println("In a row TOP LEFT: "+ inARowTopLeft);

		//Four in a row LEFT of said piece:
		int inARowLeft=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column--;
			inARowLeft++;
			if (inARowLeft == 4)
				return true;
			if (column<0)
				break;
		}
		//System.out.println("In a row LEFT: "+ inARowLeft);

		//Four in a row BOTTOM LEFT of said piece:
		int inARowBottomLeft=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column--;
			row--;
			inARowBottomLeft++;
			if (inARowBottomLeft == 4)
				return true;
			if (column<0 || row<0)
				break;
		}
		//System.out.println("In a row BOTTOM LEFT: "+ inARowBottomLeft);

		//Four in a row BOTTOM RIGHT of said piece:
		int inARowBottomRight=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column++;
			row--;
			inARowBottomRight++;
			if (inARowBottomRight == 4)
				return true;
			if (column>6 || row<0)
				break;
		}
		//System.out.println("In a row BOTTOM RIGHT: "+ inARowBottomRight);

		//Four in a row RIGHT of said piece:
		int inARowRight=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column++;
			inARowRight++;
			if (inARowRight == 4)
				return true;
			if (column>6)
				break;
		}
		//System.out.println("In a row RIGHT: "+ inARowRight);

		//Four in a row TOP RIGHT of said piece:
		int inARowTopRight=0;
		row=this.available[lastColumnPlayed] - 1;
		column = lastColumnPlayed;
		while ( this.board[column][row] == player) {
			column++;
			row++;
			inARowTopRight++;
			if (inARowTopRight == 4)
				return true;
			if (column>6 || row>5)
				break;
		}
		//System.out.println("In a row TOP RIGHT: "+ inARowTopRight);

		//Don't have to check in a row ABOVE because this is the last piece.

		//Check if the piece is within a connect four diagonally:
		//The piece has been considered twice, so subtract one

		if((inARowTopRight + inARowBottomLeft-1)>= 4)
			return true;
		if ((inARowTopLeft+inARowBottomRight-1)>=4)
			return true;

		//Check if the piece is within a connect four horizontally:
		//The piece has been considered twice, so subtract one
		if ((inARowLeft+inARowRight-1)>=4)
			return true;

		return false; // The player has not won.
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
	 * Returns the column that a player can play to guarantee a victory on his or her next move.
	 * If this is not possible, returns -1
	 * @param player Takes the player that it will analyze
	 * @return the move that will guarantee a win, or -1 if the player can't guarantee a victory with 
	 * his or her next move.
	 */

	public int winGameTwoTurns (int player){

		//Find out who the enemy player is.
		int otherPlayer = enemyPlayer(player);

		int potentialCounterplay=0;

		//Let's iterate through the columns and see if there's one place that allows us to win in two turns:
		for (int potentialMove=0;potentialMove<7;potentialMove++) {

			//Check if there's space available at column "potentialMove":
			if (hasSpace(potentialMove)) {
				this.addDisk(potentialMove, player);

				//Check if the opponent can stop our next connect four.
				for (potentialCounterplay = 0; potentialCounterplay<7; potentialCounterplay++) {

					/*If there's space in this column, the other player will place a token and 
					 * we'll see if they can deny the victory
					 */

					if (hasSpace(potentialCounterplay)) {
						this.addDisk(potentialCounterplay, otherPlayer);

						/*
						 * If the opponent can place a piece in column "potentialCounterplay"
						 * that makes it so that the Player doesn't win next turn,
						 * this move isn't optimal
						 */

						if (this.winGameNextTurn(player) == -1) {
							this.removeDisk(potentialCounterplay, otherPlayer);
							break;
						}

						//Let's remove the disk we just placed and try again.
						this.removeDisk(potentialCounterplay, otherPlayer);
					}
				}

				/*If we iterated through all 7 columns and the opponent can't stop 
				 * the next connect four, player wins in 2 turns.	
				 * If they can stop it, let's try the next column. */

				if (potentialCounterplay == 7) {
					this.removeDisk(potentialMove, player);
					return potentialMove;
				}
				this.removeDisk(potentialMove, player);
			}
		}
		return -1; 
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
}
