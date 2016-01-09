package de.locked.game2048;

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
                bc.up();
            }
            if (line.equals("s")) {
                bc.down();
            }
            if (line.equals("a")) {
                bc.left();
            }
            if (line.equals("d")) {
                bc.right();
            }
            System.out.println(bc);
        } while (!line.equals("x"));
        scanner.close();
    }
}
