import java.util.Random;

public class Board {

	private final int WIDTH = 7;
	private final int HEIGHT = 6;
	private final int NUM_IN_ROW = 4;
	private char[][] board;
	private final Character STARTING_PLAYER = 'X';
	// if INT: 1 for X, zero for empty, -1 for O
	private Character turn;
	
	
	private boolean vsCPU = false;
	
	// tracks most recent move (zero-based column)
	private int lastMove;
	private int lastMoveRow;
	
	// holds height of available move, if [== HEIGHT] it's full column
	private int[] workingBoard; 
	
	public Board() {
		board = new char[HEIGHT][WIDTH];
		createEmptyBoard();
		//turn = STARTING_PLAYER;
		turn = STARTING_PLAYER;
		workingBoard = new int[WIDTH];
		
		lastMove = -1; lastMoveRow = -1;
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
		if(lastMove == -1) {
			return false;
		}
		
		lastMoveRow = workingBoard[lastMove] - 1;
		System.out.println("inside isOver()\n"
				+ "col=" + (lastMove) + "row=" + lastMoveRow);
		
		return checkVertical() 
				/*|| checkHorizontal() */
				/*|| checkR_Diag() || checkL_Diag() */
				|| boardIsFull();
	}
	
	private boolean boardIsFull() {
		for(int i = 0; i < WIDTH; i++) {
			if(workingBoard[i] < HEIGHT) {
				return false;
			}
		}
		turn = null; // what is this for?
		return true;
	}

	// FIXME
	/*
	 * Most recent piece is equal to [NUM_IN_ROW - 1] pieces below it
	 */
	private boolean checkVertical() {
		// when i enter this function, lastMove is the column just modified (1-based index)
		// swapTurn() has been called since the last move
		
		// I'm going to move this into field data
		//int lastMoveRow = workingBoard[lastMove] - 1;
		
		if(lastMoveRow < NUM_IN_ROW - 1) {
			// not enough pieces in column
			System.out.println("Not enough pieces to win vertically.");
			return false;
		}
		for(int i = 0; i < NUM_IN_ROW; i++) {
			if(board[lastMoveRow - i][lastMove] == turn) {
				System.out.println("space on board: " + board[lastMoveRow - i][lastMove]);
				return false;
			}
		}
		System.out.println("vert win.");
		return true;
	}

	private boolean checkHorizontal() {
		// Least amount of checks? -> shifting.
		// check NUM_IN_ROW from lastCol - (NUM_IN_ROW - 1) to 
								//		 lastCol + (NUM_IN_ROW - 1)
		// This is to not run off the side of the board
		int start = Math.max(0, lastMove - (NUM_IN_ROW - 1));
		int end = Math.min(WIDTH - 1, lastMove + (NUM_IN_ROW - 1));
		
		for(int i = start; i <= end; i++) {
			if(board[lastMoveRow][i] == turn || board[lastMoveRow][i] == ' ') {
				// keep going
			}
		}
		
		
		
		return true;
	}

	private boolean checkR_Diag() {
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
		
		//	validate move, end the turn.
		if(!isValidMove(move)) {
			return false;
		}
		
		// update the board
		updateBoard(move);
		lastMove = move;
		
		// check for winner, do I need this here?
		// No, that's the while loop in Driver...
		
		// update turn
		swapTurn();
		
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
