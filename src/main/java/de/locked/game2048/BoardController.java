package de.locked.game2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BoardController {

    public enum STATE {
        WON, LOOSE, PENDING;
    }

    private final Cell[] board;
    private final int w = 4;
    private final int h = 4;
    private final int WINNING_VALUE = 2048;
    private STATE state = STATE.PENDING;
    private int score = 0;

    public BoardController() {
        board = new Cell[w * h];
        for (int i = 0; i < board.length; i++) {
            board[i] = Cell.EMPTY;
        }
        addNewCell();
        addNewCell();
    }

    public int getScore() {
        return score;
    }

    void right() {
        boolean[] processed = new boolean[board.length];
        for (int row = 0; row < h; row++) {
            for (int col = w - 2; col >= 0; col--) {
                if (!board[index(col, row)].isEmpty()) {
                    int fromCol = col;
                    boolean proceed = true;
                    while (proceed && !processed[index(col + 1, row)]) {
                        int src = index(fromCol, row);
                        int dst = index(fromCol + 1, row);

                        if (board[dst].isEmpty()) { // move to right
                            board[dst] = board[src];
                            board[src] = Cell.EMPTY;
                        } else {
                            if (board[dst].canMergeWith(board[src])) {
                                board[dst] = board[dst].mergeWith(board[src]);
                                board[src] = Cell.EMPTY;
                                score += board[dst].getValue();
                            }
                            proceed = false;
                        }
                        fromCol++;
                    }
                    processed[index(fromCol - 1, row)] = true;
                }
            }
        }

        addNewCell();
        updateState();
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

    private int newValue() {
        return Math.random() < 0.9 ? 2 : 4;
    }

    private void addNewCell() {
        getFreeCellIndex().ifPresent(i -> board[i] = new Cell(newValue()));
    }

    private Optional<Integer> getFreeCellIndex() {
        int[] candidates = IntStream.range(0, board.length)
                .filter(i -> board[i].isEmpty())
                .toArray();
        final int size = candidates.length;
        return size == 0 ? Optional.empty() : Optional.of(candidates[new Random().nextInt(size)]);
    }

    private int index(int col, int row) {
        return row * w + col;
    }

    private boolean canContinue() {
        return getFreeCellIndex().isPresent() || canMerge();
    }

    private boolean canMergeIndex(int a, int b) {
        if (a < 0 || a >= board.length || b < 0 || b >= board.length) {
            return false;
        } else {
            return board[a].canMergeWith(board[b]);
        }
    }

    private boolean canMerge() {
        // just check against right & bottom as we're symmetric
        for (int i = 0; i < board.length; i++) {
            if (canMergeIndex(i, i + 1) || canMergeIndex(i, i + w)) {
                return true;
            }
        }
        return false;
    }

    private void updateState() {
        if (Arrays.stream(board).anyMatch(c -> c.getValue() == WINNING_VALUE)) {
            state = STATE.WON;
        } else if (!canContinue()) {
            state = STATE.LOOSE;
        }
    }
}
