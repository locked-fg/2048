package de.locked.game2048;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class Board {

    private final int w;
    private final int h;
    private final Cell[] board;

    Board(int w, int h, Cell[] board) {
        this.w = w;
        this.h = h;
        this.board = board;
    }

    int size() {
        return board.length;
    }

    Cell get(int i) {
        return board[i];
    }

    Cell get(Coord c) {
        return get(index(c));
    }

    void set(int i, Cell cell) {
        board[i] = cell;
    }

    private int index(Coord c) {
        return c.y * w + c.x;
    }

    boolean canContinue() {
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

    boolean inBounds(Coord c) {
        return c.x >= 0 && c.x < w && c.y >= 0 && c.y < h;
    }

    boolean isEmpty(Coord c) {
        return get(c).isEmpty();
    }

    boolean canMerge(Coord a, Coord b) {
        return canMergeIndex(index(a), index(b));
    }

    void move(Coord src, Coord dst) {
        board[index(dst)] = board[index(src)];
        board[index(src)] = Cell.EMPTY;
    }

    void merge(Coord src, Coord dst) {
        board[index(dst)] = get(dst).mergeWith(get(src));;
        board[index(src)] = Cell.EMPTY;
    }

    private Optional<Integer> getFreeCellIndex() {
        int[] candidates = IntStream.range(0, board.length)
                .filter(i -> get(i).isEmpty())
                .toArray();
        final int size = candidates.length;
        return size == 0 ? Optional.empty() : Optional.of(candidates[new Random().nextInt(size)]);
    }

    private int newValue() {
        return Math.random() < 0.9 ? 2 : 4;
    }

    void addNewCell() {
        getFreeCellIndex().ifPresent(i -> board[i] = new Cell(newValue()));
    }

    boolean hasValue(int v) {
        return Arrays.stream(board).anyMatch(c -> c.getValue() == v);
    }

}
