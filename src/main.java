import java.util.InputMismatchException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		String username;
		Scanner keyb = new Scanner(System.in);
		
		System.out.print("Name of user: ");
		username = keyb.nextLine();
		
		Board game = new Board();
		
		while(!game.isOver()) {
			
			if(game.whoseTurn() == 'X') { // user's turn
				System.out.print("Enter the column of your move: ");
				int move = -1;
				try {
					move = keyb.nextInt();
					game.makeMove(move);
					
				} catch (InputMismatchException e) {
					System.out.println("Enter an integer between 1 and 7");
					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("column " + move + " is full.");
				}
				
			} else {
				game.makeMove();
				
			}
				
			
		}
		
	}

}
