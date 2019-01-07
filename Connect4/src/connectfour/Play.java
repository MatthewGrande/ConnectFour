package connectfour;
import acm.program.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 * 
 * @author Matthew Grande
 * @version 3.0 GUI has been added
 * bot's configuration vastly cleaned up
 * This class runs the board and allows the game to be played.
 *
 */
public class Play extends GraphicsProgram {

	private static final long serialVersionUID = 1L;
	private C4Board c4board;	
	private int player;
	private Configuration board;
	private int columnPlayed;

	public void run() {
		c4board = new C4Board();
		board = new Configuration();

		add(c4board, 0, 0);
		setUpButtons();

		String yourMoves = "";

		for (int turn=0;turn<42;turn++) {

			player = turn % 2+1;
			if (player == 2) { 
				canPlay=true;
				while (canPlay)
					addActionListeners();
				yourMoves+= columnPlayed;

				//				columnPlayed = moveBot(2, board);
				//				board.addDisk(columnPlayed, player);
				//				c4board.placeToken(columnPlayed,player);
				//				yourMoves+=columnPlayed;

			}

			if (player == 1){
				columnPlayed = moveBot(1, board);
				board.addDisk(columnPlayed, player);
				c4board.placeToken(columnPlayed,player);
			}


			if (board.wonTheGame(columnPlayed, player)){
				canPlay=false;
				board.print();

				if (player == 1) {
					System.out.println("Archon beat you :) ");
					System.out.println("Your moves this game were:" + yourMoves);
					infoBox("You lost!");
				}

				else {
					System.out.println("Looks like you won! Upgrades are coming.");
					System.out.println("Your moves this game were:" + yourMoves);
					infoBox("You won!");
				}
				break;
			}
		}
	}

	/**
	 * Allows the user to play a token when they press the button.
	 */
	public void actionPerformed(ActionEvent e) {
		this.columnPlayed = Integer.valueOf(e.getActionCommand());
		if (canPlay) {
			this.c4board.placeToken(columnPlayed, player);
			this.board.addDisk(columnPlayed, player);
			canPlay=false;
		}
	}

	/**
	 * Places the buttons that will allow us to drop the tokens.
	 */
	private void setUpButtons() {
		//TODO: make nicer buttons
		add(new JButton("0"), NORTH);
		add(new JButton("1"), NORTH);
		add(new JButton("2"), NORTH);
		add(new JButton("3"), NORTH);
		add(new JButton("4"), NORTH);
		add(new JButton("5"), NORTH);
		add(new JButton("6"), NORTH);
		addActionListeners();
	}

	/**
	 * Checks which column the bot should place its token in
	 * @param lastColumnPlayed takes the last column played by the user
	 * @param board Checks the current board state to analyze its next move 
	 * @return the optimal move for the bot
	 */
	private int moveBot (int player, Configuration board){

		//TODO: Make difficulty levels
		//TODO: find out a system to prune bad potential moves using a decision tree and minimax
		
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
		int playColumn = maxPower(powerRanking);
		
//		for(int i=0;i<7;i++)
//			System.out.println(powerRanking[i]);
		return playColumn;
	}

	/**
	 * Determines the maximum power available for the bot.
	 * @param power the array that considers the power of each move.
	 * @return the max power from the powerRanking array
	 */
	private static int maxPower(int[] power) {
		int max=0;
		for (int i=0;i<7;i++) {
			//	System.out.println("power of "+i+ " is: "+ power[i]);
			if (power[i]>=power[max])
				max=i;
		}
		return max;
	}
	/**
	 * Brings up a box when the game ends
	 * @param infoMessage the message that is written in the box
	 */
	public static void infoBox(String infoMessage)
	{
		JOptionPane.showMessageDialog(null, infoMessage, "The game is over!", JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean canPlay = false;
}
