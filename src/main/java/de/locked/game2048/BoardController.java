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
package de.locked.game2048;

import de.locked.game2048.model.Move;
import de.locked.game2048.model.Coord;
import de.locked.game2048.model.Board;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardController {

    private final Board board;
    private int score = 0;

    public BoardController() {
        this(new int[16], 4, 4);
        board.addNewCell();
        board.addNewCell();
    }

    public BoardController(int[] cells, int w, int h) {
        board = new Board(cells, w, h);
    }

    public int[] getBoardState() {
        return board.getState();
    }

    public BoardController copy() {
        BoardController bc = new BoardController(board.getState(), board.getWidth(), board.getHeight());
        bc.score = this.score;
        return bc;
    }

    public boolean canContinue() {
        return board.canContinue();
    }

    public Integer getScore() {
        return score;
    }

    public Integer getMaxValue() {
        return Arrays.stream(getBoardState()).max().getAsInt();
    }

    public boolean move(Move m) {
        boolean didMove = processMove(m);
        if (didMove) {
            board.addNewCell();
        }
        return didMove;
    }

    private boolean processMove(Move move) {
        boolean[][] merged = new boolean[board.getWidth()][board.getHeight()];
        boolean moved = false;
        for (Coord src : buildMoves(move)) {
            Coord dst = findTargetCell(src, move, merged);
            if (!src.equals(dst)) {
                if (board.isEmpty(dst)) {
                    board.move(src, dst);
                } else { // merge
                    board.merge(src, dst);
                    score += board.getValue(dst);
                    merged[dst.x][dst.y] = true;
                }
                moved = true;
            }
        }
        return moved;
    }

    private Coord findTargetCell(final Coord src, Move m, boolean[][] merged) {
        Coord now, next = src;
        do {
            now = next;
            next = now.step(m);
        } while (board.inBounds(next) && board.isEmpty(next));

        // next is now out of bounds OR occupied (and maybe mergable)
        if (!board.inBounds(next)) {
            return now;
        } else if (!board.canMerge(src, next) || merged[next.x][next.y]) {
            // non mergable or merged already in this move
            return now;
        } else {
            return next;
        }
    }

    private List<Coord> buildMoves(Move move) {
        List<Coord> toMove = new ArrayList<>();
        for (int col = 0; col < board.getWidth(); col++) {
            for (int row = 0; row < board.getHeight(); row++) {
                Coord coord = new Coord(col, row);
                if (!board.isEmpty(coord)) {
                    toMove.add(coord);
                }
            }
        }
        //always start from the far side
        if (move == Move.DOWN) {
            Collections.sort(toMove, (c1, c2) -> -Integer.compare(c1.y, c2.y));
        } else if (move == Move.RIGHT) {
            Collections.sort(toMove, (c1, c2) -> -Integer.compare(c1.x, c2.x));
        }
        return Collections.unmodifiableList(toMove);
    }

    @Override
    public String toString() {
        final String separator = "+" + new String(new char[board.getWidth() * 5]).replace("\0", "-") + "+";
        String s = separator + " Score: " + getScore() + "\n";
        for (int row = 0; row < board.getHeight(); row++) {
            s += "|";
            for (int col = 0; col < board.getWidth(); col++) {
                Coord co = new Coord(col, row);
                if (board.isEmpty(co)) {
                    s += " " + String.format("%4d", 0);
                } else {
                    s += " " + String.format("%4d", board.getValue(co));
                }
            }
            s += "|\n";
        }
        s += separator;
        return s;
    }
}
