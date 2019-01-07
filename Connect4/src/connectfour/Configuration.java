package connectfour;


/**
 * 
 * @author Matthew Grande
 * @version 3.0 GUI has been added
 *  bot's configuration vastly cleaned up
 *This class configures the board, and is where the where all the "move" methods are stored.
 *
 */



public class Configuration {
	public static final int WIDTH = 7;
	public static final int HEIGHT = 6;
	public int[][] board; 
	public int[] available;
	boolean spaceLeft;
	//		C4Board visualBoard = new C4Board(); 


	public Configuration(){
		board = new int[WIDTH][HEIGHT];
		available = new int[HEIGHT+1]; 
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
		board[column][available[column]] = player;
		(available[column])++;
	}

	/**
	 * Remove a disk from the board, to be used in some methods.
	 * @param column the column that we're removing the piece from
	 * @param player the player whose piece we're removing
	 */
	public void removeDisk(int column,int player) {
		board[column][available[column]-1] = 0;
		(available[column])--;
	}

	/**
	 * Checks if a player has a connect four horizontally
	 * @param player the player that is being analyzed
	 * @return true if the player has a horizontal c4, false otherwise
	 */
	private boolean hasHorizontalC4(int player) {

		for (int row=0;row<HEIGHT;row++) {
			for (int column=0;column<WIDTH-3;column++) {
				if (board[column][row] == player
						&& board[column+1][row] == player
						&& board[column+2][row] == player
						&& board[column+3][row] == player)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a player has a connect four vertically
	 * @param player the player that is being analyzed
	 * @return true if the player has a vertical c4, false otherwise
	 */
	private boolean hasVerticalC4(int player) {
		for (int row=0;row<HEIGHT-3;row++) {
			for (int column=0;column<WIDTH;column++) {
				if (board[column][row] == player
						&& board[column][row+1] == player
						&& board[column][row+2] == player
						&& board[column][row+3] == player)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a player has a connect four in the \ direction
	 * @param player the player that is being analyzed
	 * @return true if the player has a \ c4, false otherwise
	 */
	private boolean hasDiagonalBotRightC4(int player) {

		for (int row=0;row<HEIGHT-3;row++) {
			for (int column=3;column<WIDTH;column++) {
				if (board[column][row] == player
						&& board[column-1][row+1] == player
						&& board[column-2][row+2] == player
						&& board[column-3][row+3] == player)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the player has a connect four in the / direction
	 * @param player the player that is being analyzed
	 * @return true if the player has a / c4, false otherwise
	 */
	private boolean hasDiagonalTopRightC4(int player) {

		for (int row=0;row<HEIGHT-3;row++) {
			for (int column=0;column<WIDTH-3;column++) {
				if (board[column][row] == player
						&& board[column+1][row+1] == player
						&& board[column+2][row+2] == player
						&& board[column+3][row+3] == player)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there is a connect 4 on the board.
	 * A player has a connect 4 if they have four consecutive pieces in any direction:
	 * Vertically, Horizontally, \, and /
	 * @param player the player that might have a connect 4
	 * @return true if the player has connect 4, false otherwise.
	 */
	private boolean hasConnect4(int player) {

		if (hasHorizontalC4(player)
				|| hasVerticalC4(player)
				|| hasDiagonalBotRightC4(player)
				|| hasDiagonalTopRightC4(player))
			return true;
		return false;
	}


	/**
	 * Determine if a player's move has won them the game.
	 * A player has won if he or she has four consecutive pieces in any direction.
	 * @param lastColumnPlayed Takes the last column played
	 * @param player takes the player that played last
	 * @return true if won, false if lost
	 */
	public boolean wonTheGame (int lastColumnPlayed, int player){
		return hasConnect4(player);
	}

	/**
	 * Determines if dropping a token into specific column yields a connect 4
	 * @param player player who is dropping the token
	 * @param column where the player is placing the token
	 * @return true if a token in this column yields connect 4, false if not.
	 */
	public boolean columnYieldsConnect4(int column,int player) {

		if (hasSpace(column)) {
			addDisk(column,player);
			if (wonTheGame(column,player)) {
				removeDisk(column,player);
				return true;
			}
			else {
				removeDisk(column,player);
				return false;
			}
		}

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
	 * I.e: If the other player doesn't have a connect 4 available after the player places his piece,
	 * then there is counterplay.
	 * This method will ONLY be called in "columnYieldsGuaranteedConnect4NextTurn"
	 * Will return true if counterplay exists, and false if not.
	 * @param player the player that might have a guaranteed connect 4 available next turn.
	 * @param otherPlayer the player who is attempting to stop the guaranteed connect 4.
	 * @return true if counterplay exists(i.e. the other player can stop the guaranteed connect 4) and false if not.
	 */
	public boolean possibleCounterplay(int player, int otherPlayer) {

		for (int possibleMove=0;possibleMove<WIDTH;possibleMove++) {
			if (hasSpace(possibleMove)) {
				addDisk(possibleMove,otherPlayer);
				if (!connect4Available(player)) {
					removeDisk(possibleMove,otherPlayer);
					return true;
				}
				removeDisk(possibleMove, otherPlayer);
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

		if (hasSpace(column)) {
			addDisk(column,player);
			if (!possibleCounterplay(player,otherPlayer) &&
					!connect4Available(otherPlayer)) {
				removeDisk(column,player);
				return true;
			}

			else {
				removeDisk(column,player);
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
			if (columnYieldsGuaranteedConnect4NextTurn(column,player)) {
				//System.out.println(column + " lead to c4twoturns");
				return true;
			}
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
	 * Checks to see if placing a token in this column yields connect 3
	 * without putting the player in a losing position
	 * @param player The player who wishes to place a token
	 * @param column the column where the token is being placed
	 * @return true if connect 3 is obtained, false if not
	 */
	public boolean columnYieldsConnect3(int column, int player) {
		int otherPlayer = enemyPlayer(player);
		if (hasSpace(column)) {
			addDisk(column, player);

			if (hasUsefulConnectThree(column, player) 
					&& !canWinNextTurn(otherPlayer)) {
				removeDisk(column,player);
				return true;
			}
			else
				removeDisk(column,player);
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
			addDisk(column, player);

			if (	canWinNextTurn(player) &&
					!canWinNextTurn(otherPlayer)) {
				removeDisk(column,player);
				return true;
			}
			
			else {
				removeDisk(column, player);
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
			addDisk(column, player);
			if (!canWinNextTurn(otherPlayer)) {
				removeDisk(column, player);
				return true;
			}
			else
				removeDisk(column, player);
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
			addDisk(column, player);
			if (!canWinNextTurn(otherPlayer)
					&& !possibleWin2Turns(otherPlayer)) {
				removeDisk(column, player);
				return true;
			}
			else {
				removeDisk(column, player);
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
		if (canWinNextTurn(otherPlayer)) {
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

	/**
	 * If placing a token in a column results in an available connect 3 without
	 * putting the player in a losing position, this column leads to connect three
	 * @param column the column where the piece is being placed
	 * @param player the player that is placing the piece
	 * @return true or false if the column leads towards connect 3
	 */
	public boolean leadsToConnectThree(int column, int player) {
		int otherPlayer = enemyPlayer(player);
		if (hasSpace(column)) {
			addDisk(column, player);
			if ( !canWinNextTurn(otherPlayer)
					&& connect3Available(player)) {
				removeDisk(column, player);
				return true;
			}
			else
				removeDisk(column, player);
		}
		return false;
	}

	private boolean canWinNextTurn(int player) {
		return connect4Available(player) ||
				connect4TwoTurnsAvailable(player);
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
		if (hasSpace(column)) {
			addDisk(column,player);
			if (!wonTheGame(column,player) && canWinNextTurn(otherPlayer)) {
				removeDisk(column,player);
				return true;
			}
			else {
				removeDisk(column,player);
				return false;
			}
		}
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
			//If the column doesn't win the game, and its a losing position, make the power -1 
			// So that the player will only play this column if he has no other choice.
			if (losingPosition(column,player))
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
			//If the column has no space, make the power -2 
			//So that the player will NEVER play this column.
			if (!hasSpace(column))
				power[column] = -2;
		}
	}

	/**
	 * Check to see if the player has a useful connect three directly below the piece. 
	 * This means:
	 * There is a space available above the topmost piece so the player can eventually
	 * get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three below, false otherwise
	 */
	public boolean hasUsefulConnectThreeBelow(int column, int player) {
		int inARowBelow = 0;
		int row = available[column] - 1;
		if (row < 5) {
			while (board[column][row] == player) {
				inARowBelow++;
				row--;
				if (inARowBelow == 3)
					return true;
				//Make sure we don't go past the board.
				if (row<0)
					break;
			}
		}
		return false;
	}
	/**
	 * Check to see if the player has a useful connect three top left of the piece. 
	 * This means:
	 * There is a space available top left of the last piece, or bottom right of the first piece,
	 * so the player can eventually get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three to the top left, false otherwise
	 */
	public boolean hasUsefulConnectThreeTopLeft(int column,int player) {
		int inARowTopLeft=0;
		int row = available[column] - 1;
		int otherPlayer = enemyPlayer(player);
		int initialRow = row;
		int initialColumn = column;

		while ( board[column][row] == player) {
			column--;
			row++;
			//Make sure the spot to the top left of the piece is within the board.
			inARowTopLeft++;
			if (inARowTopLeft == 3) {
				//Make sure there is a spot available top left of this piece.
				//OR a space available bottom right of the first piece.
				if ((row <= 5 && column >= 0 && board[column][row] != otherPlayer) 
						|| (initialColumn+1<=6 && initialRow-1>=0 &&
						board[initialColumn+1][initialRow-1] != otherPlayer))
					return true;
			}
			if (row > 5 || column < 0)
				break;
		}
		return false;
	}

	/**
	 * Check to see if the player has a useful connect three left of the piece. 
	 * This means:
	 * There is a space available left of the last piece, or right of the first, so the player can eventually
	 * get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three to the left, false otherwise
	 */
	public boolean hasUsefulConnectThreeLeft(int column, int player) {
		int inARowLeft = 0;
		int row = available[column] - 1;
		int otherPlayer = enemyPlayer(player);
		int initialColumn = column;

		while ( board[column][row] == player) {
			column--;
			//If the column is now beyond the board, we'll return false.
			inARowLeft++;
			//Make sure the spot to the left of the piece is within the board.
			if (inARowLeft == 3) {
				//Make sure the other player isn't blocking us off.
				if ((column>=0 && board[column][row] != otherPlayer)
						||(initialColumn+1<=6 && board[initialColumn+1][row] != otherPlayer))
					return true;
			}
			if (column<0)
				break;
		}
		return false;
	}

	/**
	 * Check to see if the player has a useful connect three bottom left of the piece. 
	 * This means:
	 * There is a space available bottom left of the last piece, or top right of the first piece,
	 * so the player can eventually get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three to the bottom left, false otherwise
	 */
	public boolean hasUsefulConnectThreeBottomLeft(int column, int player) {
		int inARowBottomLeft=0;
		int row=available[column] - 1;
		int otherPlayer = enemyPlayer(player);

		while ( board[column][row] == player) {
			column--;
			row--;
			inARowBottomLeft++;
			//If the column or row is now beyond the board, we'll return false.
			if (inARowBottomLeft == 3) {
				if (row >= 0 && column>=0 && board[column][row] != otherPlayer) {
					return true;
				}
			}
			if (row < 0 || column < 0)
				break;

		}
		return false;
	}

	/**
	 * Check to see if the player has a useful connect three bottom right of the piece. 
	 * This means:
	 * There is a space available bottom right of the last piece, or top left of the first piece,
	 * so the player can eventually get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three to the bottom right, false otherwise
	 */
	public boolean hasUsefulConnectThreeBottomRight(int column, int player) {
		int inARowBottomRight=0;
		int row=available[column] - 1;
		int otherPlayer = enemyPlayer(player);
		while ( board[column][row] == player) {

			column++;
			row--;
			inARowBottomRight++;
			//If the column or row is now beyond the board, we'll return false.
			if (inARowBottomRight == 3) {
				if (column <=6 && row>=0 && board[column][row] != otherPlayer) {
					return true;
				}
			}

			if (column>6 || row<0) 
				break;
		}
		return false;
	}

	/**
	 * Check to see if the player has a useful connect three right of the piece. 
	 * This means:
	 * There is a space available right of the last piece, or left of the first, so the player can eventually
	 * get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three to the right, false otherwise
	 */
	public boolean hasUsefulConnectThreeRight (int column, int player) {
		int inARowRight=0;
		int row=available[column] - 1;
		int otherPlayer = enemyPlayer(player);
		int initialColumn = column; 

		while ( board[column][row] == player) {
			column++;
			inARowRight++;
			//If the column is now beyond the board, we'll return false.
			if (inARowRight == 3) {
				if ((column <= 6 && board[column][row] != otherPlayer) 
						|| (initialColumn-1 >= 0 && 
						board[initialColumn-1][row] != otherPlayer))
					return true;
			}
			if (column>6)
				break;
		}

		return false;
	}

	/**
	 * Check to see if the player has a useful connect three right of the piece. 
	 * This means:
	 * There is a space available right of the last piece, or bottom left of the first piece,
	 * so the player can eventually get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three to the right, false otherwise
	 */
	public boolean hasUsefulConnectThreeTopRight (int column, int player) {
		int inARowTopRight=0;
		int row=available[column] - 1;
		int otherPlayer = enemyPlayer(player);
		int initialRow = row;
		int initialColumn = column;

		while ( board[column][row] == player) {
			column++;
			row++;
			inARowTopRight++;
			if (inARowTopRight == 3) {
				if ((column<=6 && row <=5 && board[column][row] != otherPlayer)
						||(initialColumn-1>=0 && initialRow-1>=0 &&
						board[initialColumn-1][initialRow-1] != otherPlayer))
					return true;
			}
			if (column>6 || row>5)
				break;
		}
		return false;
	}
	/**
	 * Check to see if the player has a useful connect three with the piece in the middle
	 * This means:
	 * There is a space available either two tokens right of the last piece, or two tokens left
	 * so the player can eventually get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three horizontally, false otherwise
	 */
	public boolean hasUsefulConnectThreeHorizontal(int column, int player) {
		int row=available[column] - 1;
		int otherPlayer = enemyPlayer(player);
		//Have to make sure column + 1 and column - 1 are not out of bounds!
		if (column >0 && column < 6 )
			//The player has a connect 3 with this piece in the middle.
			if (board[column+1][row] == player && board[column-1][row] == player)
				//There is an available spot 2 pieces to the right or 2 pieces to the left
				if (((column+2)<=6 && board[column+2][row] != otherPlayer) ||
						((column - 2 >=0) && board[column-2][row] != otherPlayer))
					return true;
		return false;
	}

	/**
	 * Check to see if the player has a useful diagonal connect three with the piece in the middle
	 * This means:
	 * There is a space available either two tokens top right of the last piece, or two tokens bottom left
	 * so the player can eventually get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect three diagonally to the top right, false otherwise
	 */
	public boolean hasUsefulConnectThreeBotLeftTopRight(int column, int player) {
		int row=available[column] - 1;
		int otherPlayer = enemyPlayer(player);
		//Have to make sure column + 1,row+1 and column - 1,row-1 are not out of bounds!
		if (column > 0 && column < 6 && row > 0 && row < 5 )
			//The player has a connect 3 with this piece in the middle.
			if (board[column+1][row+1] == player && board[column-1][row-1] == player)
				//There is an available spot 2 pieces to the top right 
				// OR 2 pieces to the bot left
				//HAVE TO MAKE SURE ITS NOT OUT OF BOUNDS!!
				if (((column+2)<=6 && (row+2)<=5 && board[column+2][row+2] != otherPlayer) ||
						((column - 2 >=0) && (row - 2 >= 0) && board[column-2][row-2] != otherPlayer))
					return true;
		return false;
	}
	/**
	 * Check to see if the player has a useful diagonal connect three with the piece in the middle
	 * This means:
	 * There is a space available either two tokens top right of the last piece, or two tokens bottom left
	 * so the player can eventually get a connect 4 off of this connect 3
	 * @param column the column where the piece was placed
	 * @param player the player that is placing the piece.
	 * @return true if the player has a useful connect diagonally to the top left, false otherwise
	 */
	public boolean hasUsefulConnectThreeBotRightTopLeft(int column, int player) {
		int row=available[column] - 1;
		int otherPlayer = enemyPlayer(player);
		//Have to make sure column + 1,row-1 and column - 1,row+1 are not out of bounds!
		if (column > 0 && column < 6 && row > 0 && row < 5 )
			//The player has a connect 3 with this piece in the middle.
			if (board[column+1][row-1] == player && board[column-1][row+1] == player)
				//There is an available spot 2 pieces to the top left 
				// OR 2 pieces to the bot right
				//HAVE TO MAKE SURE ITS NOT OUT OF BOUNDS!!
				if (((column+2)<=6 && (row-2)>=0 && board[column+2][row-2] != otherPlayer) ||
						((column - 2 >=0) && (row + 2 <= 5) && board[column-2][row+2] != otherPlayer))
					return true;
		return false;
	}

	/**
	 * Checks to see if the player placed a piece in a column that yields a useful connect three
	 * This means:
	 * There is a space available such that this connect 3 can eventually result in a connect 4
	 * @param lastColumnPlayed the last column played by the player
	 * @param player the player that is placing the piece
	 * @return true if a connect 4 is possible and false otherwise
	 */
	public boolean hasUsefulConnectThree (int lastColumnPlayed, int player){

		int row=available[lastColumnPlayed] - 1;
		//If there are no pieces in the 0'th row at this column , you don't have connect 3
		if (row < 0)
			return false;

		//If ANY of the useful connect 3 methods return true, there is a useful connect 3 available

		if (hasUsefulConnectThreeBelow(lastColumnPlayed,player)
				|| hasUsefulConnectThreeTopLeft(lastColumnPlayed,player)
				|| hasUsefulConnectThreeLeft(lastColumnPlayed,player)
				|| hasUsefulConnectThreeBottomLeft(lastColumnPlayed,player)
				|| hasUsefulConnectThreeBottomRight(lastColumnPlayed,player)
				|| hasUsefulConnectThreeRight(lastColumnPlayed,player)
				|| hasUsefulConnectThreeTopRight(lastColumnPlayed,player)
				|| hasUsefulConnectThreeBotRightTopLeft(lastColumnPlayed,player)
				|| hasUsefulConnectThreeBotLeftTopRight(lastColumnPlayed,player)
				|| hasUsefulConnectThreeHorizontal(lastColumnPlayed,player))
			return true;

		return false; // The player does  not have a useful connect "3".
	}

	/**
	 * Determines who the opposing player is
	 * @param player the current player
	 * @return the integer corresponding to the enemy player
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
		return available[column]<=5;
	}


}
