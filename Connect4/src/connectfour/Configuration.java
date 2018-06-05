package connectfour;

/**
 * 
 * @author Matthew Grande
 * @version 2.0 Test suite has been added
 * bot now considers the best move out of all available options
 *  rather than only the leftmost column
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
		//System.out.println("Trying to add a piece to column:"+column);
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
		if (row < 0)
			return false;
		//If there are no pieces in the 0'th row at this column , return false
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
	 * Determines if dropping a token into specific column yields a connect 4
	 * @param player player who is dropping the token
	 * @param column where the player is placing the token
	 * @return true if a token in this column yields connect 4, false if not.
	 */
	public boolean columnYieldsConnect4(int column,int player) {
		//Check if there's space in the column

		if (hasSpace(column)) {
			this.addDisk(column,player);

			//Check to see if the player has a connect 4
			if (wonTheGame(column,player)) {
				this.removeDisk(column,player);
				return true;
			}
			//If we haven't won the game, return false
			else {
				this.removeDisk(column,player);
				return false;
			}
		}

		//If there's no space, it obviously returns false
		else
			return false;
	}

	/**
	 * Check to see if the player has a connect 4 available. This means:
	 * There is a column where the player can drop a token to win the game.
	 * @param player the player who is being analyzed 
	 * @return true if there is a connect 4 available, false otherwise
	 */
	public boolean connect4Available(int player) {
		for (int column=0;column<WIDTH;column++) {
			if (columnYieldsConnect4(column,player))
				return true;
		}
		return false;
	}

	/**
	 * Adds significant power to a column if it is capable of yielding connect 4, so that the bot is
	 * encouraged to play in this column.
	 * All columns that yield true are WINNING MOVES.
	 * @param player the turn player
	 * @param power the turn player's power ranking
	 */
	public void connectFour(int player,int[] power) {
		for (int column=0;column<WIDTH;column++) {
			if (columnYieldsConnect4(column,player))
				power[column]+=100000;
		}
	}

	/**
	 * Checks to see if the opponent has counterplay.
	 * Counterplay means the player can play a move that prevents the other player from winning.
	 * This method will ONLY be called in "columnYieldsGuaranteedConnect4NextTurn"
	 * Will return true if counterplay exists, and false if not.
	 * @param player the player that might have a guaranteed connect 4 available next turn.
	 * @param otherPlayer the player who is attempting to stop the guaranteed connect 4.
	 * @return true if counterplay exists(i.e. the other player can stop the guaranteed connect 4) and false if not.
	 */
	public boolean possibleCounterplay(int player, int otherPlayer) {

		for (int possibleMove=0;possibleMove<WIDTH;possibleMove++) {
			if (hasSpace(possibleMove)) {
				this.addDisk(possibleMove,otherPlayer);
				// If the player doesn't have a connect 4 available for this column, 
				// then counterplay exists!
				if (!connect4Available(player)) {
					this.removeDisk(possibleMove,otherPlayer);
					return true;
				}
				this.removeDisk(possibleMove, otherPlayer);
			}
		}
		return false;
	}

	/**
	 * Check to see if dropping a token into this specific column yields a guaranteed connect 4 next turn.
	 * This means: If player drops a token into this column, there is no possible column where the enemy player
	 * can deny a connect 4 from player.
	 * @param player the player who is being analyzed
	 * @param column the column that is being checked for a guaranteed connect 4 next turn.
	 * @return true if the column yields a guaranteed connect 4 next turn, false otherwise
	 */
	public boolean columnYieldsGuaranteedConnect4NextTurn(int column,int player) {
		int otherPlayer = enemyPlayer(player);

		//Make sure there's space in the column
		if (hasSpace(column)) {
			this.addDisk(column,player);
			/*Check to see if the player has a guaranteed connect 4 available.
			 * This means: no matter where the opponent places his piece, I have a connect 4 available.
			 */

			//If there is no counterplay to this piece and the opponent can't win
			//i.e there is no connect4Available(otherplayer)
			//return true!

			if (!possibleCounterplay(player,otherPlayer) &&
					!connect4Available(otherPlayer)) {
				this.removeDisk(column,player);
				return true;
			}

			else {
				this.removeDisk(column,player);
				return false;
			}
		}

		return false;
	}

	/**
	 * Determine if there is a way the player has a guaranteed win in 2 turns 
	 * @param player that is looking to place a piece
	 * @return true if the player can win in 2 turns, false otherwise
	 */
	public boolean connect4TwoTurnsAvailable(int player) {
		for (int column=0;column<WIDTH;column++) {
			if (columnYieldsGuaranteedConnect4NextTurn(column,player))
				return true;
		}
		return false;
	}

	/**
	 * Adds significant power to a column if there is a guaranteed connect 4 available in 2 turns
	 * All columns that yield true are WINNING MOVE
	 * @param player
	 * @param power
	 */
	public void connect4TwoTurns(int player,int[] power) {
		for (int column=0;column<WIDTH;column++) {
			if (columnYieldsGuaranteedConnect4NextTurn(column,player))
				power[column]+=80000;
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
	 * Checks to see if placing a token in this column yields connect 3
	 * @param player The player who wishes to place a token
	 * @param column the column where the token is being placed
	 * @return true if connect 3 is obtained, false if not
	 */
	public boolean columnYieldsConnect3(int column, int player) {
		int otherPlayer = enemyPlayer(player);
		if (hasSpace(column)) {
			//drop a disk, check if they have 3 consecutive tokens, then remove it.
			this.addDisk(column, player);

			/*If this disk is the 3rd consecutive token and doesn't make the other player win
			 *remove it and add slight power to the column
			 */
			if (this.hasConnectThree(column, player) 
					&& !connect4Available(otherPlayer) &&
					!connect4TwoTurnsAvailable(otherPlayer)) {
				this.removeDisk(column,player);
				return true;
			}
			else
				this.removeDisk(column,player);
		}
		return false;
	}

	/**
	 * Check to see if the player has a connect 3 available. This means:
	 * There is a column where the player can drop a token to win the game.
	 * @param player the player who is being analyzed 
	 * @return true if there is a connect 3 available, false otherwise
	 */
	public boolean connect3Available(int player) {
		for (int column=0;column<WIDTH;column++) {
			if (columnYieldsConnect3(column,player))
				return true;
		}
		return false;
	}

	/**
	 * This method will attempt to get a connect 3 for the bot.
	 *  IT IS VERY LOW PRIORITY, will add very slight amounts of power to a play.
	 * @param player the player that is playing his move
	 * @param power the array of power ranking for each column 
	 */
	public void connectThree(int player, int[] power) {
		for (int column=0;column<WIDTH;column++) {
			if (columnYieldsConnect3(column,player))
				power[column]+=100;
		}
	}


	/**
	 * This method will check if placing a piece in this column is a potential Winning position
	 * i.e, you might be able to win next turn if you put a token in this column.
	 * @param player the player that is being analyzed
	 * @param column the column that the piece is being placed in
	 * @return true if it is a potentially winning position
	 */
	public boolean potentialWinningPosition(int column,int player) {
		int otherPlayer=enemyPlayer(player);

		if (hasSpace(column)) {
			this.addDisk(column, player);
			/*Check if this move doesn't put the other player in a winning position
			 * and that the player now has a winning move available
			 */
			if (	(connect4Available(player) || connect4TwoTurnsAvailable(player)) &&
					!connect4Available(otherPlayer) &&
					!connect4TwoTurnsAvailable(otherPlayer)) {

				this.removeDisk(column,player);
				return true;
			}
			else {
				this.removeDisk(column, player);
				return false;
			}
		}
		return false;
	}

	/**
	 * This method will be called by denyWinningMove. If the opponent has a winning move available, this
	 * method will check if the current player can stop it or not by placing a token in a specific column.
	 *  If it can stop it by placing it in this column, this will return true.
	 * @param player the player who is making the move
	 * @param column the column where the piece is placed
	 * @return true if the denial Attempt is a success, false otherwise
	 */
	public boolean denialAttempt(int column, int player) {
		int otherPlayer = enemyPlayer(player);

		if (hasSpace(column)) {
			this.addDisk(column, player);
			//If the opponent no longer has a winning move available, 
			// add a decent chunk of power to this column
			if (!connect4Available(otherPlayer) &&
					!connect4TwoTurnsAvailable(otherPlayer)) {
				this.removeDisk(column, player);
				return true;
			}
			else
				this.removeDisk(column, player);
		}
		return false;
	}

	/**
	 * This method will add small power to the columns where the 
	 *player can place a token and have a winning move available on his or her next turn.
	 * @param player The player that is being analyzed
	 * @param power The array of power rankings for the columns
	 */
	public void searchWin2Turns(int player,int[] power) {
		for (int potentialMove=0;potentialMove<WIDTH;potentialMove++) {
			if (potentialWinningPosition(potentialMove,player))
				power[potentialMove]+=1500;
		}
	}

	/**
	 * Boolean method of the searchWin2Turns, suggesting there is a column the player can place a token where
	 * he or she has a possible win in two turns.
	 * @param player the player who wishes to place a token
	 * @return true if there is a possible(preventable) win in two turns, false otherwise.
	 */
	public boolean possibleWin2Turns(int player) {

		for (int potentialMove=0;potentialMove<WIDTH;potentialMove++) {
			if (potentialWinningPosition(potentialMove,player))
				return true;
		}
		return false;
	}

	/**
	 * This method will be called by denyWinningPosition. If the opponent has a winning position available, this
	 * method will check if the current player can stop it or not. If it can stop it, this will return true
	 * @param player the player who is making the move
	 * @param column the column where the piece is placed
	 * @return true if the denial Attempt is a success, false otherwise
	 */
	public boolean denialPotentialWinPosition(int column, int player) {
		int otherPlayer = enemyPlayer(player);
		if (hasSpace(column)) {
			this.addDisk(column, player);
			if (!connect4Available(otherPlayer) &&
					!connect4TwoTurnsAvailable(otherPlayer)
					&& !possibleWin2Turns(otherPlayer)) {
				this.removeDisk(column, player);
				return true;
			}
			else {
				this.removeDisk(column, player);
				return false;
			}
		}

		else
			return false;
	}

	/**
	 * If the other player has a winning move available, deny it.
	 * This method will 	add a decent chunk of power to columns that can deny wins.
	 * @param player the player who is making the move
	 * @param power the array of power for each column
	 */
	public void denyWinningMove(int player,int[] power) {
		int otherPlayer = enemyPlayer(player);
		//We have observed that: winningMoveAvailable(otherPlayer)
		//Let's try to stop it!
		if (connect4Available(otherPlayer) &&
				connect4TwoTurnsAvailable(otherPlayer)) {
			for (int column=0;column<WIDTH;column++) {
				if (denialAttempt(column,player))
					power[column]+=5000;
			}
		}
	}


	/**
	 * If the other play can move into a winning position next turn, deny it.
	 * This method will add a decent chunk of power to columns that can deny wins.
	 * @param player the player that is trying to make the defense.
	 * @param power the array of power for each column  
	 */
	public void denyWinningPosition(int player,int[] power) {
		int otherPlayer = enemyPlayer(player);
		//Let's see if the other player has a potential winning position
		if (possibleWin2Turns(otherPlayer)) {
			//Ok he does, so let's stop him.
			for (int column=0;column<WIDTH;column++) {
				if (denialPotentialWinPosition(column,player))
					power[column]+=3000;
			}
		}
	}


	public boolean leadsToConnectThree(int column, int player) {
		int otherPlayer = enemyPlayer(player);
		if (hasSpace(column)) {
			addDisk(column, player);
			// Make sure the move we did didn't push the opponent into a winning position
			// Make sure there's a connect 3 available next turn
			if ( !connect4Available(otherPlayer) &&
					!connect4TwoTurnsAvailable(otherPlayer)
					&& connect3Available(player)) {
				this.removeDisk(column, player);
				return true;
			}
			else
				this.removeDisk(column, player);
		}
		return false;
	}

	/**
	 * The bot will add a very tiny amount of power to the column where it can move towards a connect three.
	 * @param player the player that is playing his or her move
	 * @param power the array of power for each column 
	 */
	public void moveTowardsConnectThree(int player,int[] power) {
		for (int column=0;column<WIDTH;column++) {
			if (leadsToConnectThree(column,player)) {
				power[column]+=50;
			}
		}
	}


	/**
	 * Returns true if the column puts the player in a losing position
	 * A losing position is defined when a player drops a token in this column and then:
	 * - the player has NOT won the game AND:
	 * -> the other player has a connect 4 available OR 
	 * -> the other player has a connect4TwoTurnsAvailable
	 * @param column the column where the player is putting his token
	 * @param player the player that is placing a token
	 * @return true if the player puts the token and is now in a losing position, false otherwise
	 */
	public boolean losingPosition(int column, int player) {

		int otherPlayer = enemyPlayer(player);
		//If there is space in the column, check if placing a piece leads to a losing position
		//Make sure there's no connect four available.
//		if (!connect4Available(player)) {
			if (hasSpace(column)) {
				addDisk(column,player);
				if (!wonTheGame(player,column) && (connect4Available(otherPlayer) ||
						(connect4TwoTurnsAvailable(otherPlayer)))) {
					removeDisk(column,player);
					return true;
				}
				else {
					removeDisk(column,player);
					return false;
				}
//			}
		}
		//If there is no space, then return false since this column is not a losing position.
		return false;	
	}

	/**
	 * If putting a piece in a column makes the other player win,
	 * the bot will not place his piece there
	 * @param player the player that is playing his or her move
	 * @param power the array of power for each column
	 */
	public void avoidLoss(int player, int[] power) {
		for (int column=0;column<WIDTH;column++) {
			//If the column doesn't win the game, and its a losing position, make it -1
			if (!columnYieldsConnect4(column,player) && losingPosition(column,player))
				power[column]= -1;
		}
	}

	/**
	 * The bot will make the power of any column that does not have space equal to -2, 
	 * so that it does not place pieces in this column
	 * @param player the player that is playing his or her move
	 * @param power the array of power for each column
	 */
	public void emptyColumns(int player, int[] power) {
		for (int column=0;column<WIDTH;column++) {
			if (!hasSpace(column))
				power[column] = -2;
		}
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
	 * Checks if there is space in a column for a token to be placed
	 * @param column takes the column that is being played
	 * @return true if there is space in the column, false otherwise
	 */
	private boolean hasSpace(int column) {
		return this.available[column]<=5;
	}

	//TODO: implement a USEFUL connect three method, so the bot doesn't attempt to connect three
	//OUTSIDE of the board!!

}
