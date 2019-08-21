import java.util.Scanner;


public class Driver {
	
	public static void main(String[] args) {
		Scanner keyb = new Scanner(System.in);
		Board game = new Board();
		// stuff
		
		int move;
		do {
		
			System.out.print("Enter a row: ");
			move = keyb.nextInt();
			if(!game.makeMove(move)) {
				System.out.println("Invalid move.");
			}
			
		} while(!game.isOver());
		
		System.out.println("The game is over!");
		
		keyb.close();
	}
	
}