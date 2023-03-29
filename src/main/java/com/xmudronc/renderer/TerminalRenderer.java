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
    public void init(RGB[][] buffer1, RGB[][] buffer2) {
        resizeTerminal(runSize.getColumns(), runSize.getRows());
        for (int x = 1; x < buffer1.length; x++) {
            for (int y = 1; y < buffer1[x].length; y++) {
                buffer1[x][y] = new RGB(41, 0, 0);
                buffer2[x][y] = new RGB(0, 0, 0);
            }
        }
    }

    @Override
    public void render(RGB[][] buffer1, RGB[][] buffer2) {
        for (int x = 0; x < buffer1.length; x+=4) {
            for (int y = 0; y < buffer1[x].length; y+=2) {
                if (!buffer1[x][y].equals(buffer2[x][y]) || !buffer1[x][y+1].equals(buffer2[x][y+1])) {
                    System.out.print("\u001B[" + ((y+1)-(y/2)) + ";" + (x+1) + "f");
                    if (buffer1[x][y].equals(buffer1[x][y+1])) {
                        System.out.print(buffer1[x][y].getRGB() + Symbol.BLOCK.value + "\u001B[0m");
                    } else {
                        buffer1[x][y].setBgRGB(buffer1[x][y+1]);
                        System.out.print(buffer1[x][y].getRGB() + Symbol.B_BLOCK.value + "\u001B[0m");
                        buffer1[x][y].setBgRGB(buffer1[x][y]);
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
