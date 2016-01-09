package de.locked.game2048;

public class Coord {

    final int x, y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Coord step(Move m) {
        return new Coord(x + m.x, y + m.y);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.x;
        hash = 71 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coord other = (Coord) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

}
