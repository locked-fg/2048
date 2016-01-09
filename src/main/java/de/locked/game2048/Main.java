package de.locked.game2048;

import de.locked.game2048.model.Move;

public class Main {

    public static void main(String[] args) {
        BoardController board = new BoardController();
        for (int i = 0; i < 6; i++) {
            print(board);
            board.move(Move.UP);
        }
    }

    private static void print(BoardController board) {
        System.out.println(board.toString());
    }

}
