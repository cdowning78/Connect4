import java.util.Scanner;

 
public class Driver {
	
	public static void main(String[] args) {
		Scanner keyb = new Scanner(System.in);
		Board game = new Board();
		// stuff
		
		int move; // holds input for a move
		
		welcome();
		
		do {
			game.swapTurn();
		
			System.out.print("\nIt is " + game.whoseTurn() + "\'s turn. Enter a row: ");
			move = keyb.nextInt();
			if(!game.makeMove(move)) {
				System.out.println("Invalid move.");
				game.swapTurn(); // this swaps turn back if an invalid move is made
			}
			
		} while(!game.isOver());
		
		System.out.println("\nThe game is over!");
		
		// accounts for full board with ties
		if(game.whoseTurn() != null) {
			//game.swapTurn(); // FIXME because makeMove changes turn before isOver is called
			System.out.println(game.whoseTurn() + " has won!");
		} else {
			System.out.println("The board has been filled. Tie game!");
		}
		
		keyb.close();
	}

	private static void welcome() {
		System.out.println("Yooooo, it's Connect 4.");		
	}
	
}