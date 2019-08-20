import java.util.Random;

public class Board {

	private final int WIDTH = 7;
	private final int HEIGHT = 6;
	private final int NUM_IN_ROW = 4;
	private char[][] board;
	private final char STARTING_PLAYER = 'X';
	// if INT: 1 for X, zero for empty, -1 for O
	private char turn;
	private boolean vsCPU;
	
	private int[] workingBoard; 
	// holds height of available move, 
	// == HEIGHT then full
	
	public Board() {
		board = new char[HEIGHT][WIDTH];
		makeBoardEmpty();
		turn = STARTING_PLAYER;
		vsCPU = false;
		workingBoard = new int[WIDTH];
	}
	
	private void makeBoardEmpty() {
		for(int i = HEIGHT - 1; i >= 0; i--) {
			for(int j = 0; j < WIDTH; j++) {
				board[i][j] = ' ';
			}
		}	
	}

	public boolean isOver() {
		return checkVertical() || checkHorizontal() 
				|| checkR_Diag() || checkL_Diag() 
				|| boardIsFull();
	}
	
	private boolean boardIsFull() {
		for(int i = 0; i < WIDTH; i++) {
			if(workingBoard[i] >= HEIGHT) {
				return true;
			}
		}
		return false;
	}

	private boolean checkVertical() {
		char check;
		// uhhhhhh
		for(int i = 0; i < WIDTH; i++) {
			check = board[0][i];
			
		}
		return false;
	}

	private boolean checkHorizontal() {
		return false;
	}

	private boolean checkR_Diag() {
		return false;
	}

	private boolean checkL_Diag() {
		return false;
	}

	public char whoseTurn() {
		return turn;
	}

	public boolean makeMove(int move) {
		move--; // move to zero-based indexing
		
		//	validate move
		if(!isValidMove(move)) {
			return false;
		}
		
		// update the board
		updateBoard(move);
		
		// check for winner, do I need this here?
		// No, that's the while loop in Driver...
		
		// update turn
		turn = (turn == 'X') ? 'O' : 'X';
		
		// print board
		displayBoard();
		
		return true;
	}

	private void displayBoard() {
		for(int i = HEIGHT - 1; i >= 0; i--) {
			for(int j = 0; j < WIDTH; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	private void updateBoard(int move) {
		// where will the piece end up
		int col = workingBoard[move];
		// mark board
		board[col][move] = turn;
		// update relevant column
		workingBoard[move]++;
	}

	private boolean isValidMove(int row) {
		
		if(row > WIDTH - 1) {
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
