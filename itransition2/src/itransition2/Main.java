package itransition2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static private ArrayList<String> moves;
	static private int PlayerMove;
	static private int CompMove;
	
	public static void main(String[] argv) {
		CheckValid(argv);
		moves = new ArrayList<>(Arrays.asList(argv));
		byte[] key = Hmac.generate_key();
		ComputerMove();
		Hmac.printHmacSha256(key, moves.get(CompMove));
		PlayerMove();
		System.out.println("Your move: " + moves.get(PlayerMove));
		System.out.println("Computer move: " + moves.get(CompMove));
		CompareMoves();
		PrintKey(key);
	}
	
	
	static private void CheckValid(String[] argv) {
		int argc = argv.length;
		if (argc < 3 || argc % 2 == 0 ||
				Arrays.stream(argv).distinct().count() < argc) {
			System.out.println("Invalid arguments.\n"
					+ "When you start a command line, an odd number of >=3 unique lines is conveyed.");
			System.exit(0);
		}
	}
	
	
	static private void ComputerMove() {
		CompMove = (int)(Math.random() * moves.size());
	}
	
	
	static private void PrintMenu() {
		System.out.println("Available moves:");
		for (int i = 0; i < moves.size(); i++)
			System.out.println((i + 1) + " - " + moves.get(i));
		System.out.println(0 + " - exit");
	}
	
	
	private static void PlayerMove() {
		Scanner in = new Scanner(System.in);
		while (true) {
			PrintMenu();
			System.out.print("Enter your move: ");
			if (in.hasNextInt())
				PlayerMove = (in.nextInt());
			if (CheckPlayerMove()) {
				in.close();
				return;
			}
		}
	}
	
	
	static private boolean CheckPlayerMove() {
		if (PlayerMove < 0 || PlayerMove > moves.size()) {
			System.out.println("Invalid input. Try again.");
			return false;
		}
		if (PlayerMove == 0)
			System.exit(0);
		return true;
	}
	
	
	static private void CompareMoves() {
		if (CompMove == PlayerMove) {
			System.out.println("Draw!");
		} else {
			int mid = moves.size() / 2;
			if (CompMove > PlayerMove && CompMove > PlayerMove + mid ||
					CompMove < PlayerMove && PlayerMove <= CompMove + mid)
				System.out.println("You win!");
			else
				System.out.println("You lose!");
		}
	}
	
	static private void PrintKey(byte[] key) {
		StringBuilder sb = new StringBuilder();
	    for (byte b: key)
	        sb.append(String.format("%02x", b));
	    System.out.println("HMAC key: " + sb.toString());
	}
}