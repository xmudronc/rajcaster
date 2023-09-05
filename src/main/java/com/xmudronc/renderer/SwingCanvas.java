package com.xmudronc.renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import org.jline.terminal.Size;

public class SwingCanvas extends Canvas {
    private RGB[][] buffer1;
    private RGB[][] buffer2;
    private Size runSize;

    public SwingCanvas(Size runSize) {
        this.runSize = runSize;
        setBackground(Color.BLACK);
    }

    public void paint(Graphics g) {
        for (int x = 0; x < buffer1.length; x+=4) {
            for (int y = 0; y < buffer1[x].length; y+=2) {
                //if (!buffer1[x][y].equals(buffer2[x][y]) || !buffer1[x][y+1].equals(buffer2[x][y+1])) {
                    g.setColor(buffer1[x][y].getColor());
                    g.fillRect(x * 5, y * 5, 20, 10);
                //}
            }
        }
    }

    public void setBuffers(RGB[][] buffer1, RGB[][] buffer2) {
        this.buffer1 = buffer1;
        this.buffer2 = buffer2;
    }
}
