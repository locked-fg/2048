package de.locked.game2048;

import de.locked.game2048.model.Move;
import java.util.Scanner;

public class Console {

    public static void main(String[] args) {
        BoardController bc = new BoardController();
        System.out.println(bc.toString());

        Scanner scanner = new Scanner(System.in);
        String line = "";
        do {
            line = scanner.nextLine().trim();
            if (line.equals("w")) {
                bc.move(Move.UP);
            } else if (line.equals("s")) {
                bc.move(Move.DOWN);
            } else if (line.equals("a")) {
                bc.move(Move.LEFT);
            } else if (line.equals("d")) {
                bc.move(Move.RIGHT);
            }
            System.out.println(bc);
        } while (!line.equals("x"));
        scanner.close();
    }
}
