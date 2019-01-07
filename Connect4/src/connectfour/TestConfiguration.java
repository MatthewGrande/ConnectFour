package connectfour;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TestConfiguration {

	//Test the "addDisk" method
	@Test
	public void addingDisksShouldAddToBoard() {
		Configuration tester = new Configuration(); 
		tester.addDisk(3,2);
		tester.addDisk(2,2);
		tester.addDisk(3,1);
		assertEquals(2, tester.board[3][0], "Board should have a 2 in column 3 row 0");
		assertEquals(2, tester.board[2][0], "Board should have a 2 in column 2 row 0");
		assertEquals(1, tester.board[3][1], "Board should have a 1 in column 3 row 1");
		assertEquals(2, tester.available[3], "Column 3 should have 4 available");
		assertEquals(1, tester.available[2], "Column 2 should have 5 available");
	}

	//Test the  "removeDisk" method
	@Test
	public void removingDisksShouldRemoveFromBoard() {
		Configuration tester = new Configuration(); 
		tester.addDisk(3, 2);
		assertEquals(2, tester.board[3][0], "Board should have a 2 in column 3 row 0");
		assertEquals(1, tester.available[3], "Column 3 should have 5 available");
		tester.removeDisk(3, 2);
		assertEquals(0, tester.board[3][0], "Board should have a 2 in column 3 row 0");
		assertEquals(0, tester.available[3], "Column 3 should have 6 available");
	}

	//Test the "wonTheGame" method for empty columns
	@Test
	public void wonTheGameEmptyColumnShouldBeFalse() {
		Configuration tester = new Configuration(); 
		for (int column=0;column<7;column++) {
			assertEquals(false,tester.wonTheGame(column, 1),"Empty columns should not yield true");
			assertEquals(false,tester.wonTheGame(column, 2),"Empty columns should not yield true");
		}
	}

	//Test the "wonTheGame" method
	@Test
	public void testWonTheGame() {
		Configuration tester = new Configuration(); 

		for (int column=0;column<7;column++) {
			assertEquals(false,tester.wonTheGame(column, 2),"Should not have won the game yet");
		}

		tester.addDisk(3, 2);
		tester.addDisk(3, 2);
		tester.addDisk(3, 2);

		for (int column=0;column<7;column++) {
			assertEquals(false,tester.wonTheGame(column, 2),"Should not have won the game yet");
		}

		tester.addDisk(3, 2);
		assertEquals(true,tester.wonTheGame(3, 2),"Should have won the game");
	}

	//Test the "columnYieldsConnect4" method
	@Test
	public void testColumnYieldsConnect4() {
		Configuration tester = new Configuration(); 
		tester.addDisk(3, 2);

		for (int column=0;column<7;column++) {
			assertEquals(false,tester.columnYieldsConnect4(column, 2),"Should not have a connect 4 available in any column");
		}

		tester.addDisk(3, 2);
		tester.addDisk(3, 2);

		assertEquals(true,tester.columnYieldsConnect4(3, 2),"Should have a connect 4 available in the 3rd column");
		assertEquals(false,tester.columnYieldsConnect4(3, 1),"Player 1 should not have a connect 4 available in the 3rd column");
		tester.addDisk(3, 1);

		assertEquals(false,tester.columnYieldsConnect4(3, 2),"Should no longer have a connect 4 available in the 3rd column");
		assertEquals(false,tester.columnYieldsConnect4(3, 1),"Player 1 should not have a connect 4 available in the 3rd column");
	}

	//Test the "connect4Available" method
	@Test
	public void testConnect4Available() {
		Configuration tester = new Configuration();
		tester.addDisk(3, 2);

		for (int i=0;i<7;i++) {
			assertEquals(false,tester.columnYieldsConnect4(i, 2),"Should not have a connect 4 available in the i'th column");
			assertEquals(false,tester.columnYieldsConnect4(i, 1),"Should not have a connect 4 available in the i'th column");
		}

		assertEquals(false,tester.connect4Available(1),"Should not have a connect 4 available");
		assertEquals(false,tester.connect4Available(2),"Should not have a connect 4 available yet");

		tester.addDisk(3, 2);
		tester.addDisk(3, 2);

		assertEquals(false,tester.connect4Available(1),"Should not have a connect 4 available");
		assertEquals(true,tester.connect4Available(2),"Should now have a connect 4 available");
	}

	//Test the "connect4" Method:
	@Test
	public void testConnect4() {
		Configuration tester = new Configuration();
		int[] powerRanking = {0,1,2,3,2,1,0};
		tester.connectFour(2, powerRanking);
		//None of the indexes should change!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should not have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");

		tester.addDisk(3, 2);
		tester.addDisk(3, 2);
		tester.addDisk(3, 2);
		tester.connectFour(2, powerRanking);

		//powerRanking[3] should have changed since connect 4 is available!!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(100003,powerRanking[3],"Power rankings should have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");
	}


	//Test the "possibleCounterplay" method when there is counterplay available:
	@Test
	public void testPossibleCounterplayAvailable() {
		Configuration tester = new Configuration();
		int player = 2;
		int otherPlayer = 1;
		assertEquals(true,tester.possibleCounterplay(player, otherPlayer),"Counterplay exists!");
		tester.addDisk(3, player);
		tester.addDisk(3, player);
		tester.addDisk(3, player);
		assertEquals(true,tester.possibleCounterplay(player, otherPlayer),"Counterplay exists!");

	}

	//Test the "possibleCounterplay" method when there is no counterplay available:
	@Test
	public void testPossibleCounterplayNotAvailable() {
		Configuration tester = new Configuration();
		int player = 2;
		int otherPlayer = 1;
		assertEquals(true,tester.possibleCounterplay(player, otherPlayer),"Counterplay exists!");
		tester.addDisk(2, player);
		tester.addDisk(3, player);
		assertEquals(true,tester.possibleCounterplay(player, otherPlayer),"Counterplay exists!");
		tester.addDisk(4, player);
		assertEquals(false,tester.possibleCounterplay(player, otherPlayer),"Counterplay does not exist!");

	}

	//Test the "columnYieldsGuaranteedConnect4NextTurn" method:
	@Test
	public void testColumnYieldsGuaranteedConnect4NextTurn() {
		int player=2;
		Configuration tester = new Configuration();
		for (int column=0;column<7;column++) {
			assertEquals(false,tester.columnYieldsConnect4(column, 2), 
					"No columns should yield a guaranteed connect 4");
		}
		tester.addDisk(1, player);
		tester.addDisk(3, player);
		assertEquals(true,tester.columnYieldsGuaranteedConnect4NextTurn(2, player),"Column 2 should yield a guaranteed connect 4");
	}

	//Test the "connect4TwoTurnsAvailable" method:
	@Test
	public void testConnect4TwoTurnsAvailable() {
		int player=2;
		Configuration tester = new Configuration();
		assertEquals(false,tester.connect4TwoTurnsAvailable(player),"There is no guaranteed connect 4 avaiable");
		tester.addDisk(1, player);
		tester.addDisk(3, player);
		assertEquals(true,tester.connect4TwoTurnsAvailable(player),"There is a guaranteed connect 4 avaiable");
	}

	//Test the "connect4TwoTurnsAvailable" method when the opponent can win:
	@Test
	public void testConnect4TwoTurnsAvailableShouldBeFalseIfOpponentCanWin() {
		int player=2;
		int otherPlayer=1;
		Configuration tester = new Configuration();

		/* Set up the board such that player has a connect 4 two turns available, BUT
		 * if he goes for it, then otherPlayer will win!!!
		 * SO it should be false!
		 */
		assertEquals(false,tester.connect4TwoTurnsAvailable(player),
				"There is no guaranteed connect 4 avaiable");
		tester.addDisk(1, otherPlayer);
		tester.addDisk(3, otherPlayer);
		assertEquals(true,tester.connect4TwoTurnsAvailable(otherPlayer),
				"There is a guaranteed connect 4 avaiable");
		tester.addDisk(1, player);
		tester.addDisk(3, player);
		tester.addDisk(0, player);
		tester.addDisk(3, player);
		tester.addDisk(0, player);
		tester.addDisk(3, player);
		tester.addDisk(0, player);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(0, otherPlayer);
		tester.print();
		assertEquals(true,tester.connect4TwoTurnsAvailable(player),
				"there is as guaranteed connect 4 available");
		tester.addDisk(1, otherPlayer);
		
		tester.print();
		
		assertEquals(false,tester.connect4TwoTurnsAvailable(player),
				"there is no guaranteed connect 4 available");

	}

	//Test the "connect4TwoTurns" method:
	@Test
	public void testConnect4TwoTurns() {
		Configuration tester = new Configuration();
		int[] powerRanking = {0,1,2,3,2,1,0};
		int player = 2;
		tester.connect4TwoTurns(2, powerRanking);
		//None of the indexes should change!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should not have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");

		tester.addDisk(2, player);
		tester.addDisk(4, player);
		tester.connect4TwoTurns(2, powerRanking);

		//powerRanking[3] should have changed since connect 4 is available!!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(80003,powerRanking[3],"Power rankings should have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");
	}

	//Test the "hasConnect3" method:
	@Test
	public void testHasConnect3() {
		Configuration tester = new Configuration();
		int player = 2;

		for (int column = 0;column<7;column++) {
			assertEquals(false,tester.hasUsefulConnectThree(column, player),"Player should not have connect 3");
		}

		tester.addDisk(2, player);
		tester.addDisk(3, player);

		for (int column = 0;column<7;column++) {
			assertEquals(false,tester.hasUsefulConnectThree(column, player),"Player should not have connect 3");
		}

		tester.addDisk(4, player);

		assertEquals(true,tester.hasUsefulConnectThree(4, player),"Player should have connect 3");
	}

	// Test the "columnYieldsConnect3" method:
	@Test
	public void testColumnYieldsConnect3() {
		Configuration tester = new Configuration();
		int player=2;

		for (int column=0;column<7;column++){
			assertEquals(false,tester.columnYieldsConnect3(column, player),
					"There should be no columns that yield connect three yet.");
		}

		tester.addDisk(2, player);
		tester.addDisk(3, player);

		assertEquals(false,tester.columnYieldsConnect3(0, player),"Column 0 does not yield connect 3");
		assertEquals(true,tester.columnYieldsConnect3(1, player),"Column 1 yields connect 3");
		assertEquals(false,tester.columnYieldsConnect3(2, player),"Column 2 does not yield connect 3");
		assertEquals(false,tester.columnYieldsConnect3(3, player),"Column 3 does not yield connect 3");
		assertEquals(true,tester.columnYieldsConnect3(4, player),"Column 4 yields connect 3");
		assertEquals(false,tester.columnYieldsConnect3(5, player),"Column 5 does not yield connect 3");
		assertEquals(false,tester.columnYieldsConnect3(6, player),"Column 6 does not yield connect 3");

		tester.addDisk(4, player);
		tester.addDisk(4, player);
		tester.addDisk(3, player);
		assertEquals(true,tester.columnYieldsConnect3(4, player),"Column 4 yields connect 3");
	}

	// Test the "Connect3Avaiable" method
	@Test
	public void testConnect3Available() {
		Configuration tester = new Configuration();
		int player = 2;
		assertEquals(false,tester.connect3Available(player),"Connect 3 is not available");
		tester.addDisk(2, player);
		assertEquals(false,tester.connect3Available(player),"Connect 3 is not available");
		tester.addDisk(3,player);
		assertEquals(true,tester.connect3Available(player),"Connect 3 is available");
	}

	//Test the "ConnectThree" method
	@Test
	public void testConnectThree() {
		Configuration tester = new Configuration();
		int[] powerRanking = {0,1,2,3,2,1,0};
		int player = 2;
		tester.connectThree(2, powerRanking);
		//None of the indexes should change!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should not have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");

		tester.addDisk(2, player);
		tester.addDisk(3, player);
		tester.addDisk(4, player);
		tester.addDisk(4, player);
		tester.addDisk(3, player);
		tester.connectThree(2, powerRanking);

		//powerRanking[1] should have changed since connect 3 is available!!
		//powerRanking[2] should have changed since connect 3 is available!!
		//powerRanking[3] should have changed since connect 3 is available!!
		//powerRanking[4] should have changed since connect 3 is available!!
		//powerRanking[5] should have changed since connect 3 is available!!

		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(101,powerRanking[1],"Power rankings should not have changed");
		assertEquals(102,powerRanking[2],"Power rankings should not have changed");
		assertEquals(103,powerRanking[3],"Power rankings should have changed");
		assertEquals(102,powerRanking[4],"Power rankings should not have changed");
		assertEquals(101,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");
	}

	//Test the "potentialWinningPosition" method:
	@Test
	public void testPotentialWinningPosition() {
		Configuration tester = new Configuration();
		int player = 2;
		for (int column = 0 ; column<7; column++) {
			assertEquals(false,tester.potentialWinningPosition(column, player),
					"There should be no potential winning positions");
		}
		tester.addDisk(2, player);

		//Should be three potentially winning positions!
		assertEquals(true,tester.potentialWinningPosition(1, player),
				"There should be a potential winning position at column 1");
		assertEquals(true,tester.potentialWinningPosition(3, player),
				"There should be a potential winning position at column 3");
		assertEquals(true,tester.potentialWinningPosition(4, player),
				"There should be a potential winning position at column 4");

		//The rest should be false.
		assertEquals(false,tester.potentialWinningPosition(0, player),
				"There should be no potential winning position at column 0");
		assertEquals(false,tester.potentialWinningPosition(2, player),
				"There should be no potential winning position at column 2");
		assertEquals(false,tester.potentialWinningPosition(5, player),
				"There should be no potential winning position at column 5");
		assertEquals(false,tester.potentialWinningPosition(6, player),
				"There should be no potential winning position at column 6");
	}

	//Test the "possibleWin2Turns" method:
	@Test
	public void testPossibleWin2Turns() {
		Configuration tester = new Configuration();
		int player=2;
		tester.addDisk(2, player);
		assertEquals(true,tester.possibleWin2Turns(player),"There is a possible win available in 2 turns");
	}

	//Test the "searchWin2Turns" method:
	@Test
	public void testSearchWin2Turns() {
		Configuration tester = new Configuration();
		int[] powerRanking = {0,1,2,3,2,1,0};
		int player = 2;
		tester.searchWin2Turns(player, powerRanking);
		//None of the indexes should change!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should not have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");

		tester.addDisk(2, player);
		tester.searchWin2Turns(player, powerRanking);

		//powerRanking[1] should have changed since a potential winning position is available!!
		//powerRanking[3] should have changed since a potential winning position is available!!
		//powerRanking[4] should have changed since a potential winning position is available!!


		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1501,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(1503,powerRanking[3],"Power rankings should have changed");
		assertEquals(1502,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");
	}

	//Test the "denialattempt" method:
	@Test
	public void testDenialAttempt() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;
		tester.addDisk(2, player);
		tester.addDisk(3, player);
		tester.addDisk(4,player);
		assertEquals(false,tester.denialAttempt(1, otherPlayer),
				"The connect 4 cannot be denied");
		tester.addDisk(1, otherPlayer);
		assertEquals(true,tester.denialAttempt(5, otherPlayer),
				"The connect 4 can be denied by a token in 5");
	}

	//Test the "denyWinningMove" method:
	@Test
	public void testDenyWinningMove() {
		Configuration tester = new Configuration();
		int[] powerRanking = {0,1,2,3,2,1,0};
		int player = 2;
		int otherPlayer = 1;
		tester.denyWinningMove(player, powerRanking);
		//None of the indexes should change!

		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should not have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");

		tester.addDisk(2, otherPlayer);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(2, otherPlayer);
		tester.addDisk(2, otherPlayer);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(1, player);
		tester.addDisk(1, otherPlayer);
		tester.addDisk(4, player);
		tester.addDisk(4, player);
		tester.addDisk(0, otherPlayer);
		tester.denyWinningMove(player, powerRanking);

		//None of the indexes should change!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should not have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");

		tester.addDisk(2, player);
		tester.denyWinningMove(player, powerRanking);

		//Index 3 should have changed to deny the win!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(5003,powerRanking[3],"Power rankings should have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");
	}

	//Test the method "denialPotentialWinPosition":
	@Test
	public void testDenialPotentialWinPosition() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		tester.addDisk(2, otherPlayer);
		assertEquals(true,tester.denialPotentialWinPosition(1, player),"Should deny the win position");
		assertEquals(true,tester.denialPotentialWinPosition(3, player),"Should deny the win position");
		assertEquals(true,tester.denialPotentialWinPosition(4, player),"Should deny the win position");
	}

	//Test the method "losingPosition" when setting up an enemy's connect four:
	@Test
	public void testLosingPositionSettingUpEnemyConnectFourShouldBeTrue() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;
		//Check for any losing positions. Should all be false!
		for (int column=0;column<7;column++)
			assertEquals(false,tester.losingPosition(column, player),"There are no losing positions");
		tester.addDisk(0, player);
		tester.addDisk(1, otherPlayer);
		tester.addDisk(2, otherPlayer);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(2, player);
		tester.addDisk(2, player);
		tester.addDisk(3, player);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(4, player);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(4, player);
		assertEquals(true,tester.losingPosition(1, otherPlayer),"Column 1 is a losing position!");
	}

	//Test the method "losingPosition" when setting up an enemy's connect4twoturns
	@Test
	public void testLosingPositionSettingUpEnemyConnect4TwoTurnsShouldBeTrue() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;
		//Check for any losing positions. Should all be false!
		for (int column=0;column<7;column++)
			assertEquals(false,tester.losingPosition(column, player),"There are no losing positions");


		tester.addDisk(2, otherPlayer);
		tester.addDisk(2, player);
		tester.addDisk(2, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(4, player);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(5, otherPlayer);
		tester.addDisk(5, player);
		tester.addDisk(5, player);
		tester.addDisk(5, otherPlayer); //this should still be a losing position!!!

		// Losing position because:
		// The connect4TwoTurnsAvialable(player) denies the connect 4 of otherPlayer
		assertEquals(true,tester.losingPosition(2, otherPlayer),"Column 2 is a losing position!");
	}

	//Test the method "avoidLoss":
	@Test
	public void testAvoidLoss() {
		Configuration tester = new Configuration();
		int player = 2;
		int otherPlayer=1;
		int[] powerRanking = {0,1,2,3,2,1,0};

		tester.avoidLoss(otherPlayer, powerRanking);
		//None of the indexes should change!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should not have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");

		tester.addDisk(2, otherPlayer);
		tester.addDisk(2, player);
		tester.addDisk(2, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(4, player);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(5, otherPlayer);
		tester.addDisk(5, player);
		tester.addDisk(5, player);
		tester.addDisk(5, otherPlayer); //this should still be a losing position!!!

		tester.avoidLoss(otherPlayer, powerRanking);

		//powerRanking[2] should have changed since placing a token in that column leads to a losing position!!

		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(-1,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");	
	}

	//Test the method "leadsToConnectThree":
	@Test
	public void testLeadsToConnectThree() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		for (int column=0;column<7;column++) {
			assertEquals(false,tester.leadsToConnectThree(column, player),
					"No columns should lead to connect 3");
		}

		tester.addDisk(3, player);

		assertEquals(false,tester.leadsToConnectThree(0, player),"Column 0 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(1, player),"Column 1 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(2, player),"Column 2 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(3, player),"Column 3 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(4, player),"Column 4 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(5, player),"Column 5 should not lead to connect 3");
		assertEquals(false,tester.leadsToConnectThree(6, player),"Column 6 should not lead to connect 3");	


		tester.addDisk(4,otherPlayer);
		tester.addDisk(2, player);
		tester.addDisk(1, player);
		assertEquals(true,tester.hasUsefulConnectThree(1, player));
		assertEquals(true,tester.connect3Available(player));

		//	assertEquals(true,tester.leadsToConnectThree(2, player));
	}
	//Test the method "moveTowardsConnectThree":
	@Test
	public void testMoveTowardsConnectThree() {
		Configuration tester = new Configuration();
		int player=2;
		int[] powerRanking = {0,1,2,3,2,1,0};

		tester.moveTowardsConnectThree(player, powerRanking);
		//None of the indexes should change!
		assertEquals(0,powerRanking[0],"Power rankings should not have changed");
		assertEquals(1,powerRanking[1],"Power rankings should not have changed");
		assertEquals(2,powerRanking[2],"Power rankings should not have changed");
		assertEquals(3,powerRanking[3],"Power rankings should not have changed");
		assertEquals(2,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");

		tester.addDisk(2, player);
		tester.moveTowardsConnectThree(player, powerRanking);
		//Indexes 0,1,2,3,4 should change!
		assertEquals(50,powerRanking[0],"Power rankings should not have changed");
		assertEquals(51,powerRanking[1],"Power rankings should not have changed");
		assertEquals(52,powerRanking[2],"Power rankings should not have changed");
		assertEquals(53,powerRanking[3],"Power rankings should not have changed");
		assertEquals(52,powerRanking[4],"Power rankings should not have changed");
		assertEquals(1,powerRanking[5],"Power rankings should not have changed");
		assertEquals(0,powerRanking[6],"Power rankings should not have changed");
	}

	//Test the method "hasUsefulConnectThreeBelow".
	@Test
	public void testHasUsefulConnectThreeBelow() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set it up such that the connect 3 has many spaces above it.
		tester.addDisk(3, player);
		tester.addDisk(3, player);
		tester.addDisk(3, player);
		assertEquals(true,tester.hasUsefulConnectThreeBelow(3, player),"Should be true");

		//Set it up such that the connect 3 does not have a space available above it
		tester.removeDisk(3, player);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(3, player);
		tester.addDisk(3, player);

		assertEquals(false,tester.hasUsefulConnectThreeBelow(3, player),"Should be false");

		//Set it up such that the connect 3 only has one space available above it.
		tester.removeDisk(3, player);
		tester.removeDisk(3, player);
		tester.removeDisk(3, player);
		tester.removeDisk(3, otherPlayer);
		tester.removeDisk(3, player);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(3, player);
		tester.addDisk(3, player);

		assertEquals(true,tester.hasUsefulConnectThreeBelow(3, player),"Should be true");
	}

	//Test the method "hasUsefulConnectThreeTopLeft".
	@Test
	public void testhasUsefulConnectThreeTopLeft() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set the player up for a simple connect 3 to the top left.
		tester.addDisk(3, player);
		tester.addDisk(2, otherPlayer);
		tester.addDisk(2, player);
		tester.addDisk(1, otherPlayer);
		tester.addDisk(1, otherPlayer);
		tester.addDisk(1,player);

		//Set up otherPlayer for a connect 3 that wont have space to the top left of the last piece.
		tester.addDisk(0, otherPlayer);
		tester.addDisk(0, player);
		tester.addDisk(0, otherPlayer);

		assertEquals(true,tester.hasUsefulConnectThreeTopLeft(3, player),"Should be true");
		assertEquals(false,tester.hasUsefulConnectThreeTopLeft(2, otherPlayer),"Should be false");
		tester.addDisk(0, otherPlayer);
		assertEquals(false,tester.hasUsefulConnectThreeTopLeft(3, player),"Should be false");
	}

	//Test the method "hasUsefulConnectThreeLeft".
	@Test
	public void testhasUsefulConnectThreeLeft() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set it up for a simple connect 3 to the left.
		tester.addDisk(3, player);
		tester.addDisk(2,player);
		tester.addDisk(1, player);
		assertEquals(true,tester.hasUsefulConnectThree(3, player),"Should be true");

		//Block off the initial connect 3.
		tester.addDisk(4, otherPlayer);
		assertEquals(true,tester.hasUsefulConnectThree(3, player),"Should be true, left spot still open");

		tester.addDisk(0, otherPlayer);
		assertEquals(false,tester.hasUsefulConnectThree(3, player),"Should be false");
		tester.removeDisk(4, otherPlayer);
		assertEquals(true,tester.hasUsefulConnectThree(3, player),"Should be true, right spot still open");






		tester.addDisk(2, player);
		tester.addDisk(1, player);
		tester.addDisk(0, player);
		tester.addDisk(3, otherPlayer);
		assertEquals(false,tester.hasUsefulConnectThree(2,player),"Should be false");
	}

	//Test the method "hasUsefulConnectThreeBottomLeft".
	@Test
	public void testhasUsefulConnectThreeBottomLeft() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set it up for a simple connect 3 to the left.
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(2, otherPlayer);
		tester.addDisk(2, player);
		tester.addDisk(2, player);
		tester.addDisk(1,player);
		tester.addDisk(1, player);

		//tester.print();
		assertEquals(false,tester.hasUsefulConnectThreeBottomLeft(3, player),
				"Should be false, there is no space(out of bounds) for a potential c4 bottom left");

		tester.addDisk(3, player);
		//		tester.print();
		assertEquals(true,tester.hasUsefulConnectThreeBottomLeft(3, player),
				"Should be true,there is space for a potential c4 bottom left");
	}

	//Test the method "hasUsefulConnectThreeBottomRight":
	@Test
	public void testhasUsefulConnectThreeBottomRight() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set it up for a simple connect 3 to the left.
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, otherPlayer);
		tester.addDisk(3, player);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(4, player);
		tester.addDisk(4, player);
		tester.addDisk(5,player);
		tester.addDisk(5, player);

				//tester.print();
		assertEquals(false,tester.hasUsefulConnectThreeBottomRight(3, player),
				"Should be false, there is no space(out of bounds) for a potential c4 bottom right");

		tester.addDisk(3, player);
		//		tester.print();
		assertEquals(true,tester.hasUsefulConnectThreeBottomRight(3, player),
				"Should be true,there is space for a potential c4 bottom right");

		tester.addDisk(6, otherPlayer);
	}

	//Test the method "hasUsefulConnectThreeRight":
	@Test
	public void testhasUsefulConnectThreeRight() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set it up for a simple connect 3 to the right.
		tester.addDisk(3,player);
		tester.addDisk(4,player);
		tester.addDisk(5,player);
		assertEquals(true,tester.hasUsefulConnectThree(3, player),
				"Should be true, there's an available spot for connect 4");

		//Block off the initial connect 3.
		tester.addDisk(6, otherPlayer);
		assertEquals(true,tester.hasUsefulConnectThree(3, player),
				"Should be true, there's an available spot for connect 4");
		tester.addDisk(2, otherPlayer);
		//tester.print();
		assertEquals(false,tester.hasUsefulConnectThree(3, player),
				"Should be false, there is no c4 available on the right since otherplayer is blokcing it");

		tester.addDisk(4, player);
		tester.addDisk(5, player);
		tester.addDisk(6, player);
		//tester.print();
		assertEquals(true,tester.hasUsefulConnectThree(4, player),
				"Should be true, there's space on board for a c4");
	}

	//Test the method "hasUsefulConnectThreeTopRight":
	@Test
	public void testhasUsefulConnectThreeTopRight() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set the player up for a simple connect 3 to the top left.
		tester.addDisk(3, player);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(4, player);
		tester.addDisk(5, otherPlayer);
		tester.addDisk(5, otherPlayer);
		tester.addDisk(5,player);

		//Set up otherPlayer for a connect 3 that wont have space to the top left of the last piece.
		tester.addDisk(6, otherPlayer);
		tester.addDisk(6, player);
		tester.addDisk(6, otherPlayer);

		assertEquals(true,tester.hasUsefulConnectThreeTopRight(3, player),"Should be true");
		assertEquals(false,tester.hasUsefulConnectThreeTopRight(4, otherPlayer),"Should be false");

		//Block the player off from a connect 4 from column 3
		tester.addDisk(6, otherPlayer);

		assertEquals(false,tester.hasUsefulConnectThreeTopRight(3, player),"Should be false");
	}

	//Test the method "hasUsefulConnectThreeHorizontal":
	@Test
	public void testHasUsefulConnectThreeHorizontal() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		tester.addDisk(0, player);
		tester.addDisk(1, player);
		tester.addDisk(2, player);
		assertEquals(true,tester.hasUsefulConnectThreeHorizontal(1, player),"Should be true");

		tester.addDisk(4, player);
		tester.addDisk(5, player);
		tester.addDisk(6, player);

		assertEquals(true,tester.hasUsefulConnectThreeHorizontal(5, player),"Should be true");
		//Block off the other side of the c4
		tester.addDisk(3,otherPlayer);
		assertEquals(false,tester.hasUsefulConnectThreeHorizontal(1, player),"Should be true");
		assertEquals(false,tester.hasUsefulConnectThreeHorizontal(5, player),"Should be true");
	}

	//Test the method "hasUsefulConnectThreeBotLeftTopRight":
	@Test
	public void testHasUsefulConnectThreeBotLeftTopRight() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set the player up for a simple connect 3 to the top left.
		tester.addDisk(3, player);
		tester.addDisk(4, otherPlayer);
		tester.addDisk(4, player);
		tester.addDisk(5, otherPlayer);
		tester.addDisk(5, otherPlayer);
		tester.addDisk(5,player);

		//Set up otherPlayer for a connect 3 that wont have space to the top left of the last piece.
		tester.addDisk(6, otherPlayer);
		tester.addDisk(6, player);
		tester.addDisk(6, otherPlayer);

		assertEquals(true,tester.hasUsefulConnectThreeBotLeftTopRight(4, player),"Should be true");
		assertEquals(false,tester.hasUsefulConnectThreeBotLeftTopRight(5, otherPlayer),"Should be false");

		//Block the player off from a connect 4 from column 4
		tester.addDisk(6,otherPlayer);
		assertEquals(false,tester.hasUsefulConnectThreeBotLeftTopRight(4, player),"Should be false");

		tester.removeDisk(6, otherPlayer);
		tester.removeDisk(3, player);
		tester.addDisk(6, player);
		assertEquals(true,tester.hasUsefulConnectThreeBotLeftTopRight(5, player),"Should be true");

		tester.addDisk(3, otherPlayer);

		assertEquals(false,tester.hasUsefulConnectThreeBotLeftTopRight(5, player),"Should be false");
	}

	//Test the method "hasUsefulConnectThreeBotRightTopLeft":
	@Test
	public void testHasUsefulConnectThreeBotRightTopLeft() {
		Configuration tester = new Configuration();
		int player=2;
		int otherPlayer=1;

		//Set the player up for a simple connect 3 to the top left.
		tester.addDisk(3, player);
		tester.addDisk(2, otherPlayer);
		tester.addDisk(2, player);
		tester.addDisk(1, otherPlayer);
		tester.addDisk(1, otherPlayer);
		tester.addDisk(1,player);

		//Set up otherPlayer for a connect 3 that wont have space to the top left of the last piece.
		tester.addDisk(0, otherPlayer);
		tester.addDisk(0, player);
		tester.addDisk(0, otherPlayer);

		assertEquals(true,tester.hasUsefulConnectThreeBotRightTopLeft(2, player),"Should be true");
		assertEquals(false,tester.hasUsefulConnectThreeBotRightTopLeft(1, otherPlayer),"Should be false");

		//Block the player off from a connect 4 from column 4
		tester.addDisk(0,otherPlayer);
		assertEquals(false,tester.hasUsefulConnectThreeBotRightTopLeft(2, player),"Should be false");

		tester.removeDisk(0, otherPlayer);
		tester.removeDisk(3, player);
		tester.addDisk(0, player);
		assertEquals(true,tester.hasUsefulConnectThreeBotRightTopLeft(1, player),"Should be true");

		tester.addDisk(3, otherPlayer);

		assertEquals(false,tester.hasUsefulConnectThreeBotRightTopLeft(1, player),"Should be false");
	}
}

