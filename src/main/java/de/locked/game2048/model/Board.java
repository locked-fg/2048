/*
 * The MIT License
 *
 * Copyright 2016 Franz Graf <code@Locked.de>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.locked.game2048.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class Board {

    private final int w;
    private final int h;
    private final Cell[] board;

    public Board(int w, int h) {
        this(new int[w * h], w, h);
    }

    public Board(int[] cells, int w, int h) {
        if (w * h != cells.length) {
            throw new IllegalArgumentException("Cells length does not equal w*h");
        }
        this.w = w;
        this.h = h;
        this.board = new Cell[w * h];
        for (int i = 0; i < cells.length; i++) {
            board[i] = new Cell(cells[i]);
        }
    }

    public int size() {
        return board.length;
    }

    Cell get(int i) {
        return board[i];
    }

    public Cell get(Coord c) {
        return get(index(c));
    }

    void set(int i, Cell cell) {
        board[i] = cell;
    }

    private int index(Coord c) {
        return c.y * w + c.x;
    }

    public boolean canContinue() {
        return getFreeCellIndex().isPresent() || canMerge();
    }

    boolean canMerge() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Coord from = new Coord(x, y);
                if (canMerge(from, from.step(Move.RIGHT)) || canMerge(from, from.step(Move.DOWN))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean inBounds(Coord c) {
        return c.x >= 0 && c.x < w && c.y >= 0 && c.y < h;
    }

    public boolean isEmpty(Coord c) {
        return get(c).isEmpty();
    }

    public boolean canMerge(Coord a, Coord b) {
        return inBounds(b) && inBounds(b) && get(a).canMergeWith(get(b));
    }

    public void move(Coord src, Coord dst) {
        board[index(dst)] = board[index(src)];
        board[index(src)] = Cell.EMPTY;
    }

    public void merge(Coord src, Coord dst) {
        board[index(dst)] = get(dst).mergeWith(get(src));
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

    public void addNewCell() {
        getFreeCellIndex().ifPresent(i -> board[i] = new Cell(newValue()));
    }

    public boolean hasValue(int v) {
        return Arrays.stream(board).anyMatch(c -> c.getValue() == v);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public int getValue(Coord c) {
        return get(c).getValue();
    }

    public int[] getState() {
        int[] cells = new int[board.length];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = board[i].getValue();
        }
        return cells;
    }

}
