package de.locked.game2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BoardController {

    private final Cell[] board;
    private final int w = 4;
    private final int h = 4;
    private int score = 0;

    public BoardController() {
        board = new Cell[w * h];
        for (int i = 0; i < board.length; i++) {
            board[i] = Cell.EMPTY;
        }
        addNewCell(2);
    }

    public int getScore() {
        return score;
    }

    private int index(int col, int row) {
        return row * w + col;
    }

    void right() {
        int moves = 0;
        for (int row = 0; row < h; row++) {
            int columnProcessed = w;
            for (int col = w - 2; col >= 0; col--) {
                if (!board[index(col, row)].isEmpty()) {
                    int from = col;
                    boolean proceed = true;
                    while (proceed && from + 1 < columnProcessed) {
                        int src = index(from, row);
                        int dst = index(from + 1, row);

                        if (board[dst].isEmpty()) {
                            board[dst] = board[src];
                            board[src] = Cell.EMPTY;
                            moves++;
                        } else {
                            if (board[dst].canMergeWith(board[src])) {
                                board[dst] = board[dst].mergeWith(board[src]);
                                board[src] = Cell.EMPTY;
                                moves++;
                            }
                            proceed = false;
                        }
                        from++;
                    }
                }
            }
        }

        if (moves > 0) {
            addNewCell();
        }
    }

    void top() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void left() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void bottom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        final String separator = "+" + new String(new char[w * 4]).replace("\0", "-") + "+";
        String s = separator + "\n";
        for (int row = 0; row < h; row++) {
            s += "|";
            for (int col = 0; col < w; col++) {
                Cell c = board[index(col, row)];
                if (c.isEmpty()) {
                    s += "    ";
                } else {
                    s += String.format("% 4d", c.getValue());
                }
            }
            s += "|\n";
        }
        s += separator;
        return s;
    }

    private void addNewCell() {
        int value = Math.random() < 0.9 ? 2 : 4;
        addNewCell(value);
    }

    private int getFreeCellIndex() {
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i].isEmpty()) {
                candidates.add(i);
            }
        }
        return new Random().nextInt(candidates.size());
    }

    private void addNewCell(int value) {
        board[getFreeCellIndex()] = new Cell(value);
    }

}
