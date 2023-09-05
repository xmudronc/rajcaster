package com.xmudronc.renderer;
 
import java.awt.Graphics;

import javax.swing.JFrame;

import org.jline.terminal.Size;

public class SwingRenderer implements Renderer {
    private Size startupSize;
    private Size runSize;
    private JFrame frame;
    private SwingCanvas canvas;

    public SwingRenderer(Size startupSize, Size runSize) {
        this.startupSize = startupSize;
        this.runSize = runSize;
    }

    @Override
    public void init(RGB[][] buffer1, RGB[][] buffer2) {
        for (int x = 1; x < buffer1.length; x++) {
            for (int y = 1; y < buffer1[x].length; y++) {
                buffer1[x][y] = new RGB(41, 0, 0);
                buffer2[x][y] = new RGB(0, 0, 0);
            }
        }

        frame = new JFrame();//creating instance of JFrame
        canvas = new SwingCanvas(runSize);
        canvas.setBuffers(buffer1, buffer2);
        
        frame.add(canvas);
                
        frame.setSize(runSize.getColumns() * 5, runSize.getRows() * 11);
        frame.setVisible(true);//making the frame visible
    }

    @Override
    public void render(RGB[][] buffer1, RGB[][] buffer2) {
        canvas.setBuffers(buffer1, buffer2);
        canvas.repaint();
    }

    @Override
    public void end() {
        System.exit(0);
    }
    
}
