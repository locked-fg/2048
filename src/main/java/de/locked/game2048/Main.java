package de.locked.game2048;

public class Main {

    public static void main(String[] args) {
        BoardController board = new BoardController();
        print(board);
        board.right();
        print(board);
        board.right();
        print(board);
        board.right();
        print(board);
        board.right();
        print(board);
        board.right();
        print(board);
//        board.top();
//        print(board);
//        board.left();
//        print(board);
//        board.bottom();
//        print(board);
    }

    private static void print(BoardController board) {
        System.out.println(board.toString());
    }

}
