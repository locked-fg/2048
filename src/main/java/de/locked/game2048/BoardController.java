package de.locked.game2048;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BoardController {

    public enum STATE {
        WON, LOOSE, PENDING;
    }

    private final Cell[] board;
    private final int w = 4;
    private final int h = 4;
    private final Board boardM;
    private final int WINNING_VALUE = 2048;
    private STATE state = STATE.PENDING;
    private int score = 0;

    public BoardController() {
        board = new Cell[w * h];
        for (int i = 0; i < board.length; i++) {
            board[i] = Cell.EMPTY;
        }
        boardM = new Board(w, h, board);
        boardM.addNewCell();
        boardM.addNewCell();
    }

    public int getScore() {
        return score;
    }

    public void right() {
        move(Move.RIGHT);
    }

    public void up() {
        move(Move.UP);
    }

    public void left() {
        move(Move.LEFT);
    }

    public void down() {
        move(Move.DOWN);
    }

    private void move(Move m) {
        if (processMove(m)) {
            boardM.addNewCell();
            updateState();
        }
    }

    private boolean processMove(Move move) {
        boolean[][] merged = new boolean[w][h];
        boolean moved = false;
        for (Coord src : buildMoves(move)) {
            Coord dst = findTargetCell(src, move, merged);
            if (!src.equals(dst)) {
                if (boardM.isEmpty(dst)) {
                    boardM.move(src, dst);
                } else { // merge
                    boardM.merge(src, dst);
                    score += boardM.get(dst).getValue();
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
        } while (boardM.inBounds(next) && boardM.isEmpty(next));

        // next is now out of bounds OR occupied (and maybe mergable)
        if (!boardM.inBounds(next)) {
            return now;
        } else if (!boardM.canMerge(src, next) || merged[next.x][next.y]) {
            // non mergable or merged already in this move
            return now;
        } else {
            return next;
        }
    }

    private List<Coord> buildMoves(Move move) {
        List<Coord> toMove = new ArrayList<>();
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                toMove.add(new Coord(col, row));
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
        final String separator = "+" + new String(new char[w * 4]).replace("\0", "-") + "+";
        String s = separator + " Score: " + getScore() + "\n";
        for (int row = 0; row < h; row++) {
            s += "|";
            for (int col = 0; col < w; col++) {
                Cell c = boardM.get(new Coord(col, row));
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

    private void updateState() {
        if (boardM.hasValue(WINNING_VALUE)) {
            state = STATE.WON;
        } else if (!boardM.canContinue()) {
            state = STATE.LOOSE;
        }
    }
}
