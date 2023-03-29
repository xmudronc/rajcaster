package com.xmudronc.renderer;

public interface Renderer {
    public void init(RGB[][] buffer1, RGB[][] buffer2);
    public void render(RGB[][] buffer1, RGB[][] buffer2);
    public void end();
}
