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
        this.w = w;
        this.h = h;
        this.board = new Cell[w * h];
        Arrays.fill(board, Cell.EMPTY);
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

    public boolean inBounds(Coord c) {
        return c.x >= 0 && c.x < w && c.y >= 0 && c.y < h;
    }

    public boolean isEmpty(Coord c) {
        return get(c).isEmpty();
    }

    public boolean canMerge(Coord a, Coord b) {
        return canMergeIndex(index(a), index(b));
    }

    public void move(Coord src, Coord dst) {
        board[index(dst)] = board[index(src)];
        board[index(src)] = Cell.EMPTY;
    }

    public void merge(Coord src, Coord dst) {
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

}
