package de.locked.game2048.model;

public class Cell {

    private final int value;
    static final Cell EMPTY = new Cell();

    private Cell() {
        this(0);
    }

    Cell(int value) {
        this.value = value;
    }

    boolean isEmpty() {
        return value == 0;
    }

    int getValue() {
        return value;
    }

    boolean canMergeWith(Cell cell) {
        return !cell.isEmpty() && cell.value == this.value;
    }

    Cell mergeWith(Cell cell) {
        if (!canMergeWith(cell)) {
            throw new UnsupportedOperationException("Cells cannot be merged.");
        }
        return new Cell(value * 2);
    }
}
