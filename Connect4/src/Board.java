import java.util.Random;

public class Board {

	private final int WIDTH = 7;
	private final int HEIGHT = 6;
	private final int NUM_IN_ROW = 4;
	private char[][] board;
	private final Character STARTING_PLAYER = 'X';
	// if INT: 1 for X, zero for empty, -1 for O
	private Character turn;
	
	private boolean vsCPU = true;
	
	// tracks most recent move (zero-based column)
	private int lastMoveCol;
	private int lastMoveRow;
	
	// holds height of available move, if [== HEIGHT] it's full column
	private int[] workingBoard; 
	
	public Board() {
		board = new char[HEIGHT][WIDTH];
		createEmptyBoard();
		//turn = STARTING_PLAYER;
		turn = STARTING_PLAYER;
		workingBoard = new int[WIDTH];
		
		lastMoveCol = -1; lastMoveRow = -1;
	}
	
	private void createEmptyBoard() {
		for(int i = HEIGHT - 1; i >= 0; i--) {
			for(int j = 0; j < WIDTH; j++) {
				board[i][j] = ' ';
			}
		}	
	}

	
	/*
	 * I don't want to check the whole board, but only the combinations
	 * that would be impacted by the most recent move.
	 */
	
	// Do i want to manually change whose turn it is in the driver, 
	// so that i can checkWin using the turn variable
	
	public boolean isOver() {
		if(lastMoveCol == -1) {
			return false;
		}
		
		lastMoveRow = workingBoard[lastMoveCol] - 1;
		
		return checkVertical() 
				|| checkHorizontal() 
				|| checkR_Diag() /*|| checkL_Diag() */
				|| boardIsFull();
	}
	
	private boolean boardIsFull() {
		for(int i = 0; i < WIDTH; i++) {
			if(workingBoard[i] < HEIGHT) {
				return false;
			}
		}
		turn = null; // what is this for? I think it's for printing that there has been a tie.
		return true;
	}

	/*
	 * Checks for a vertical win condition. Only checks column containing the
	 * most recent move.
	 * This is easy because you just check down the column for equality.
	 * Most recent piece is equal to [NUM_IN_ROW - 1] pieces below it
	 */
	private boolean checkVertical() {
		if(lastMoveRow < NUM_IN_ROW - 1) {
			// not enough pieces in column
			return false;
		}
		for(int i = 0; i < NUM_IN_ROW; i++) {
			if(board[lastMoveRow - i][lastMoveCol] != turn) {
				return false;
			}
		}
		return true;
	}

	private boolean checkHorizontal() {
		// Least amount of checks? -> check outward from most recent move

		// This is to not run off the side of the board
		int start = Math.max(0, lastMoveCol - (NUM_IN_ROW - 1));		
		int end = Math.min(WIDTH - 1, lastMoveCol + (NUM_IN_ROW - 1));
				
		if((end - start) < (NUM_IN_ROW - 1)) {
			return false;
			// board too narrow
		}
		
		// Start from lastMoveCol, move out. Winning combo must contain center
		int consecPieces = 1;
		
		// go left
		for(int i = lastMoveCol - 1; i >= start; i--) {
			if(board[lastMoveRow][i] == turn) {
				consecPieces++;
			} else {
				break;
			}
		}
		
		// a win occurred from the last move directly left
		if(consecPieces == NUM_IN_ROW) {
			return true;
		}
		
		// System.out.println("going left did not yield a win.");
		// go right
		for(int i = lastMoveCol + 1; i <= end; i++) {
			if(board[lastMoveRow][i] == turn) {
				consecPieces++;
			} else {
				// return here because resetting at this point cannot yield a win
				// System.out.println("going right also did not yield a horizontal win.");
				return false;
			}
			if(consecPieces == NUM_IN_ROW) {
				return true;
			}
		}
		
		// I think this return is unreachable, 
		// but deleting it results in compiler error
		return false;
	}

	/**
	 * A right diagonal is one that goes bottom-left to top-right "forward slash"
	 * @return Whether or not a win is satisfied along this direction
	 */
	private boolean checkR_Diag() {
		// best way would be to go down-left until there isn't a match, then
		// hold that count and proceed to check up-right until a win is
		// no longer possible

		// basically the same thing as checkHoriz but diagonally

		// This is to not run off the board. How many spaces available 
		int start = Math.min(lastMoveRow, lastMoveCol);
		start = Math.min(start, (NUM_IN_ROW - 1));

		int end = Math.min(HEIGHT - lastMoveCol, WIDTH - lastMoveRow);
		end = Math.min(end, (NUM_IN_ROW - 1));

		// Start from most recent move, move out. Winning combo must contain center
		int consecPieces = 1;

		// go down-left
		for (int i = 1; i <= start ;i++) {
			if (board[lastMoveRow - i][lastMoveCol - i] == turn) {
				consecPieces++;
			} else {
				break;
			}
		}

		// a win occurred from the last move directly down-left
		if (consecPieces == NUM_IN_ROW) {
			return true;
		}

		// go up-right
		for (int i = 1; i <= end; i++) {
			if (board[lastMoveRow + i][lastMoveCol + i] == turn) {
				consecPieces++;
			} else {
				// return here because resetting at this point cannot yield a win
				// this statement can be removed but exiting early saves some checks
				return false;
			}
			if (consecPieces == NUM_IN_ROW) {
				return true;
			}
		}
		
		return false;
	}

	private boolean checkL_Diag() {
		return false;
	}

	// changed return type
	public Character whoseTurn() {
		return turn;
	}

	/**
	 * Receives a row for a new piece to be put in to (input between 1, WIDTH)
	 * Immediately switches to 0 to [WIDTH-1] for processing
	 */
	public boolean makeMove(int move) {
		move--; // change move to zero-based indexing
		
		//	validate move, end the turn if invalid.
		if(!isValidMove(move)) {
			lastMoveCol = -1; 
			// for isOver(), don't check if game is over since there was no valid move.
			return false;
		}
		
		// update the board
		updateBoard(move);
		lastMoveCol = move;
		
		// check for winner, do I need this here?
		// No, that's the while loop in Driver...
		
		// update turn
		// swapTurn();
		
		// print board
		displayBoard();
		
		return true;
	}

	public void swapTurn() {
		turn = (turn == 'X') ? 'O' : 'X';		
	}

	private void displayBoard() {
		for(int i = HEIGHT - 1; i >= 0; i--) {
			for(int j = 0; j < WIDTH; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	// row/col semantics here might be backwards.
	private void updateBoard(int move) {
		// where will the piece end up
		int row = workingBoard[move];
		// mark board
		board[row][move] = turn;
		// update relevant column
		workingBoard[move]++;
	}

	private boolean isValidMove(int row) {
		
		if(row > WIDTH - 1 || row < 0) {
			return false; // out of bounds
		}
		return workingBoard[row] < HEIGHT;
	}

	// Used for single player games
	public void cpuMove() {
		Random r = new Random();
		int col = r.nextInt(6);
		//makeMove(col);
		
	}
	
}
