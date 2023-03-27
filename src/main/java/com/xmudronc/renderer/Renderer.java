package com.xmudronc.renderer;

import java.util.ArrayList;

public interface Renderer {
    public void init(ArrayList<ArrayList<Integer>> buffer1, ArrayList<ArrayList<Integer>> buffer2, boolean mainBuffer);
    public void render(ArrayList<ArrayList<Integer>> buffer1, ArrayList<ArrayList<Integer>> buffer2, boolean mainBuffer);
    public void end();
}
