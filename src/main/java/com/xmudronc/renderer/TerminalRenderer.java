package com.xmudronc.renderer;

import org.jline.terminal.Size;

import com.xmudronc.Symbol;

public class TerminalRenderer implements Renderer {
    private Size startupSize;
    private Size runSize;

    public TerminalRenderer(Size startupSize, Size runSize) {
        this.startupSize = startupSize;
        this.runSize = runSize;
    }

    private static void resizeTerminal(Integer columns, Integer rows) {
        System.out.print("\u001B[8;" + rows + ";" + columns + "t"); 
    }

    @Override
    public void init(int[][] buffer1, int[][] buffer2) {
        resizeTerminal(runSize.getColumns(), runSize.getRows());
        for (int x = 1; x < buffer1.length; x++) {
            for (int y = 1; y < buffer1[x].length; y++) {
                buffer1[x][y] = 41;
                buffer2[x][y] = 40;
            }
        }
    }

    @Override
    public void render(int[][] buffer1, int[][] buffer2) {
        for (int x = 0; x < buffer1.length; x+=4) {
            for (int y = 0; y < buffer1[x].length; y+=2) {
                if (buffer1[x][y]!=buffer2[x][y] || buffer1[x][y+1]!=buffer2[x][y+1]) {
                    System.out.print("\u001B[" + ((y+1)-(y/2)) + ";" + (x+1) + "f");
                    if (buffer1[x][y]==buffer1[x][y+1]) {
                        System.out.print("\u001B[" + buffer1[x][y] + "m\u001B[" + (buffer1[x][y]-10) + "m" + Symbol.BLOCK.value + "\u001B[0m");
                    } else {
                        System.out.print("\u001B[" + buffer1[x][y] + "m\u001B[" + (buffer1[x][y+1]-10) + "m" + Symbol.B_BLOCK.value + "\u001B[0m");
                    }
                }
            }
        }
    }

    @Override
    public void end() {
        resizeTerminal(startupSize.getColumns(), startupSize.getRows());
    }
}
