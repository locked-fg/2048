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
import java.util.Scanner;

public class Console {

    public static void main(String[] args) {
        BoardController bc = new BoardController();
        System.out.println(bc.toString());
//        for (int i = 0; i < 6; i++) {
//            System.out.println(bc);
//            bc.move(Move.DOWN);
//        }
        try (Scanner scanner = new Scanner(System.in)) {
            String line = "";
            do {
                line = scanner.nextLine().trim();
                if (line.equals("w")) {
                    bc.move(Move.UP);
                } else if (line.equals("s")) {
                    bc.move(Move.DOWN);
                } else if (line.equals("a")) {
                    bc.move(Move.LEFT);
                } else if (line.equals("d")) {
                    bc.move(Move.RIGHT);
                }
                System.out.println(bc);
            } while (!line.equals("x"));
        }
    }
}
