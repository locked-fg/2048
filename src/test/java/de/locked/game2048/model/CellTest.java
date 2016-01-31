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
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.internal.runners.statements.ExpectException;

/**
 *
 * @author Franz Graf <code@Locked.de>
 */
public class CellTest {

    List<Integer> x = Arrays.asList(new Integer[]{0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096});

    @org.junit.Test
    public void testValidateFalse() {
        Cell cell = new Cell(0);
        IntStream.range(-1, 4097)
                .filter(i -> !x.contains(i))
                .forEach(i -> {
                    try {
                        cell.validate(i);
                        fail(i + " should have failed");
                    } catch (IllegalArgumentException e) {
                    }
                });
    }

    @org.junit.Test
    public void testValidateTrue() {
        Cell cell = new Cell(0);
        x.stream()
                .forEach(i -> {
                    try {
                        cell.validate(i);
                    } catch (IllegalArgumentException e) {
                        fail(i + " should have succeeded");
                    }
                });
    }

}
