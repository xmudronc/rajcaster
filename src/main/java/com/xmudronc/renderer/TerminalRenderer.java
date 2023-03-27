package com.xmudronc.renderer;

import java.util.ArrayList;

public class TerminalRenderer implements Renderer {
    @Override
    public void init(ArrayList<ArrayList<Integer>> buffer1, ArrayList<ArrayList<Integer>> buffer2, boolean mainBuffer) {
        for (int x = 1; x < buffer1.size(); x++) {
            for (int y = 1; y < buffer1.get(0).size(); y++) {
                buffer1.get(x).set(y, 41);
                buffer2.get(x).set(y, 40);
            }
        }
    }

    @Override
    public void render(ArrayList<ArrayList<Integer>> buffer1, ArrayList<ArrayList<Integer>> buffer2, boolean mainBuffer) {
        for (int x = 1; x < buffer1.size(); x++) {
            for (int y = 1; y < buffer1.get(0).size(); y++) {
                if (buffer1.get(x).get(y)!=buffer2.get(x).get(y)) {
                    if (mainBuffer) {
                        System.out.print("\u001B[" + y + ";" + x + "f\u001B[" + buffer2.get(x).get(y) + "m \u001B[0m");
                    } else {
                        System.out.print("\u001B[" + y + ";" + x + "f\u001B[" + buffer1.get(x).get(y) + "m \u001B[0m");
                    }
                }
            }
        }
    }

    @Override
    public void end() {}
}
