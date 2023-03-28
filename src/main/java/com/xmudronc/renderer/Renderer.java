package com.xmudronc.renderer;

public interface Renderer {
    public void init(int[][] buffer1, int[][] buffer2);
    public void render(int[][] buffer1, int[][] buffer2);
    public void end();
}
