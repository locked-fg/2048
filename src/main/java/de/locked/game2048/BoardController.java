package de.locked.game2048;

import de.locked.game2048.model.Move;
import de.locked.game2048.model.Coord;
import de.locked.game2048.model.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BoardController {

    public enum STATE {
        WON, LOOSE, PENDING;
    }

    private final Board board;
    private final int WINNING_VALUE = 2048;
    private STATE state = STATE.PENDING;
    private int score = 0;

    public BoardController() {
        board = new Board(4, 4);
        board.addNewCell();
        board.addNewCell();
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
            board.addNewCell();
            updateState();
        }
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
        final String separator = "+" + new String(new char[board.getWidth() * 4]).replace("\0", "-") + "+";
        String s = separator + " Score: " + getScore() + "\n";
        for (int row = 0; row < board.getHeight(); row++) {
            s += "|";
            for (int col = 0; col < board.getWidth(); col++) {
                Coord co = new Coord(col, row);
                if (board.isEmpty(co)) {
                    s += "    ";
                } else {
                    s += String.format("% 4d", board.getValue(co));
                }
            }
            s += "|\n";
        }
        s += separator;
        return s;
    }

    private void updateState() {
        if (board.hasValue(WINNING_VALUE)) {
            state = STATE.WON;

        } else if (!board.canContinue()) {
            state = STATE.LOOSE;
        }
    }
}
