import java.util.Random;

public class Board {

	private final int WIDTH = 7;
	private final int HEIGHT = 6;
	private int[][] board;
	// 1 for X
	// 0 for empty
	// -1 for Oh
	
	private char turn;
	
	
	public Board() {
		board = new int[HEIGHT][WIDTH];
	}
	
	public boolean isOver() {
		return true;
	}
	
	public char whoseTurn() {
		return turn;
	}

	/*
	 * User move
	 * input is 1-7 column indexing, array is columned at 0-6
	 */
	public void makeMove(int move) {
		move--;
		int i = 0;
		
		while (board[i][move] != 0) {
			i++;
		}
		
		try {
			board[i][move] = markBoard();
		} catch(ArrayIndexOutOfBoundsException e) {
			// throw exception to main.java
			throw e;
		}
		
	}

	/*
	 * CPU move
	 */
	public void makeMove() {
		Random r = new Random();
		int col = r.nextInt(6);
		makeMove(col);
		
	}
	
	private int markBoard() {
		if(turn == 'X')
			return 1;
		
		return 0;
	}
	
	
}
