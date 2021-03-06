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

import static java.lang.Math.log;

public class Cell {

    private final int value;
    static final Cell EMPTY = new Cell(0);

    Cell(int value) {
        validate(value);
        this.value = value;
    }

    final void validate(int v) {
        if (v != 0) { // allow 0
            Double test = log(v) / log(2);
            if (v < 0 || v == 1 || test.isNaN() || Math.abs(test - test.intValue()) > 0.00001) {
                throw new IllegalArgumentException("value " + v + " is not a valid input");
            }
        }
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

    @Override
    public String toString() {
        return "Cell{" + value + '}';
    }
    
}
