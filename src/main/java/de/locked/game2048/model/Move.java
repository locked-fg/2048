package de.locked.game2048.model;

public enum Move {
    RIGHT(1, 0), LEFT(-1, 0), UP(0, -1), DOWN(0, 1);
    final int x, y;

    private Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
