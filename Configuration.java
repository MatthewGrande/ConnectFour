package assignment4Game;

public class Configuration {

	public int[][] board; 
	public int[] available;
	boolean spaceLeft;

	public Configuration(){
		// Create a 7 by 6 board, with 42 total spaces.
		board = new int[7][6];

		// Initialize an array of integers that will hold the available spots in a column.
		available = new int[7]; 

		// Initialize a boolean variable that will be false if no further pieces can be placed on the board.
		spaceLeft = true; 
	}

	//Print out the board(Very basic for now!)
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

	//Allow a player to add a disk to the board at a specific column.
	public void addDisk (int index, int player){

		this.board[index][this.available[index]] = player;
		(this.available[index])++;

		for (int i=0; i<7;i++) {
			if (this.available[i]<=5)
			{
				this.spaceLeft = true;
				break;
			}
			else
				this.spaceLeft = false;
		}
	}

	//Remove a disk from the board, to be used in some methods.
	public void removeDisk(int index,int player) {
		this.board[index][this.available[index]-1] = 0;
		(this.available[index])--;

		for (int i=0; i<7;i++) {
			if (this.available[i]<=5)
			{
				this.spaceLeft = true;
				break;
			}
			else
				this.spaceLeft = false;
		}

	}

	//Determine if a player's move wins them the game.
	//A player has won if he or she has four consecutive pieces in any direction.
	public boolean isWinning (int lastColumnPlayed, int player){

		//TODO: CAN I EVALUATE THESE USING THREADS? find out!!

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

	//Checks if a player can win next round.
	//Returns the first column if they can, else returns -1.
	public int canWinNextRound (int player){

		for (int i=0;i<7;i++) {
			if (this.available[i]<=5) {
				this.addDisk(i, player);//drop a disk, check if they won, then remove it.
				if (this.isWinning(i, player)) {
					this.removeDisk(i,player);
					return i;
				}
				this.removeDisk(i,player);
			}
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}

	//Checks if a player can have a GUARANTEED victory in two rounds.
	//Returns the first column if they do, else returns -1
	public int canWinTwoTurns (int player){

		//Find out who the enemy player is.
		int otherPlayer;
		int j=0;
		if (player == 1)
			otherPlayer = 2;
		else
			otherPlayer = 1;

		//Let's iterate through the columns and see if there's one place that allows us to win in two turns:
		for (int i=0;i<7;i++) {

			//Check if there's space available at column "i":
			if (this.available[i]<=5) {
				this.addDisk(i, player);

				//If there's no space left let's just return -1
				if (!this.spaceLeft) {
					this.removeDisk(i, player);
					return -1;
				}

				//Make sure other player can't win!!
				if (this.canWinNextRound(otherPlayer) == -1) {

					//Let's see if the opponent can stop our next connect four.
					for (j = 0;j<7;j++) {

						//If there's space in this column, let's try it.
						if (this.available[j]<=5) {
							this.addDisk(j, otherPlayer);

							//If there's no space left let's just return -1
							if (!this.spaceLeft) {
								this.removeDisk(j, otherPlayer);
								this.removeDisk(i, player);
								return -1;
							}

							/* If the opponent can place a piece in column j
			 that makes it so that Player doesn't win next turn, this move wasn't good. */ 

							if (this.canWinNextRound(player) == -1) {
								this.removeDisk(j, otherPlayer);
								break;
							}
							//Let's remove the disk we just placed and try again.
							this.removeDisk(j, otherPlayer);
						}
					}

					/*If we scrolled through all 7 columns and can't stop the next connect four, 
					 player wins in 2 turns.	 */
					if (j == 7) {
						this.removeDisk(i, player);
						return i;
					}
				}

				this.removeDisk(i, player);
			}
		}
		return -1; 
	}

}
