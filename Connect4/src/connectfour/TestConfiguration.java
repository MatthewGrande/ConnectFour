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
		tester.addDisk(0, otherPlayer);
		assertEquals(true,tester.connect4TwoTurnsAvailable(player),
				"there is no guaranteed connect 4 available");
		tester.addDisk(1, otherPlayer);
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
			assertEquals(false,tester.hasConnectThree(column, player),"Player should not have connect 3");
		}

		tester.addDisk(2, player);
		tester.addDisk(3, player);

		for (int column = 0;column<7;column++) {
			assertEquals(false,tester.hasConnectThree(column, player),"Player should not have connect 3");
		}

		tester.addDisk(4, player);
		assertEquals(true,tester.hasConnectThree(4, player),"Player should have connect 3");
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
		
		for (int column=0;column<7;column++) {
			assertEquals(false,tester.leadsToConnectThree(column, player),
					"No columns should lead to connect 3");
		}
		
		tester.addDisk(2, player);
		
		assertEquals(true,tester.leadsToConnectThree(0, player),"Column 0 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(1, player),"Column 1 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(2, player),"Column 2 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(3, player),"Column 3 should lead to connect 3");
		assertEquals(true,tester.leadsToConnectThree(4, player),"Column 4 should lead to connect 3");
		assertEquals(false,tester.leadsToConnectThree(5, player),"Column 5 should not lead to connect 3");
		assertEquals(false,tester.leadsToConnectThree(6, player),"Column 6 should not lead to connect 3");	
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
	
}

