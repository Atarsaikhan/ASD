package Framework;

import java.util.Scanner;

public class GameTest {
	public static void main(String[] args) {
		GameController game = new FourBullsGame();

		int move = 1;
		Scanner in = new Scanner(System.in);
		while (move >= 0) {
			move = in.nextInt();
			if (move > 9 && move < 55) {
				int from = move / 10 - 1;
//				System.out.println(from);
				int to = move % 10 - 1;
//				System.out.println(to);
				game.move(from, to);
			} else
				System.out.println("Wrong move!\r\n");
		}

	}
}
