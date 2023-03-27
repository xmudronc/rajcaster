package com.xmudronc;

import java.io.IOException;

import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.utils.NonBlockingReader;

public class Menu {
    private Size startupSize;
    private Size runSize;
    private Terminal terminal;
    private NonBlockingReader reader;
    private Boolean running = false;
    private Thread input = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                if (reader != null) {
                    while (running) {
                        Integer value = reader.read();
                        if (value >= 65 && value <= 90) {
                            value += 32;
                        }
                        moveCursor(value);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    public Menu(Terminal terminal, NonBlockingReader reader, Size startupSize, Size runSize) {
        this.terminal = terminal;
        this.reader = reader;
        this.startupSize = startupSize;
        this.runSize = runSize;
    }
    
    private void moveCursor(Integer key) throws IOException {
        switch (key) {
            case 115: // s
                break;
            case 119: // w
                break;
            case 13:
                break;
            default:
                break;
        }
    }

    public void clearGameArea() {
        for (Integer y = 1; y < runSize.getRows(); y++) {
            for (Integer x = 1; x < runSize.getColumns(); x+=2) {
                System.out.print(String.format("%c[%d;%df", 0x1B, y, x));
                System.out.print("\u001B[0m" + Symbol.EMPTY.value);
            }
        } 
    }

    public void clearPlayArea() {
        for (Integer y = 2; y < runSize.getRows()-1; y++) {
            for (Integer x = 3; x < runSize.getRows()*2-3; x+=2) {
                System.out.print(String.format("%c[%d;%df", 0x1B, y, x));
                System.out.print("\u001B[0m" + Symbol.EMPTY.value);
            }
        } 
    }

    public void exitSelected() throws IOException {
        resizeTerminal(startupSize.getColumns(), startupSize.getRows());
        running = false;
        terminal.close();
        reader.close();
        System.exit(0);
    }

    private void drawArea(Integer fromY, Integer toY, Integer fromX, Integer toX) {
        for (Integer y = fromY; y < toY; y++) {
            for (Integer x = fromX; x < toX; x+=2) {
                System.out.print(String.format("%c[%d;%df", 0x1B, y, x));
                if (y == fromY || y == toY-1) {
                    System.out.print("\u001B[41m" + Symbol.EMPTY.value);
                } else {
                    if (x == fromX || x == toX-1) {
                        System.out.print("\u001B[41m" + Symbol.EMPTY.value);
                    } else {
                        System.out.print("\u001B[0m" + Symbol.EMPTY.value);
                    }
                }
            }
            if (y != fromY) {
                System.out.print(String.format("%c[%d;%df", 0x1B, y, toX+1));
                System.out.print("\u001B[30m\u001B[40m" + Symbol.EMPTY.value);
            } else {
                System.out.print(String.format("%c[%d;%df", 0x1B, y, toX+1));
                System.out.print("\u001B[0m" + Symbol.EMPTY.value);
            }
            System.out.println();
        }    
        for (Integer x = fromX; x < toX+2; x+=2) {
            System.out.print(String.format("%c[%d;%df", 0x1B, toY, x));
            if (x < fromX+2) {
                System.out.print("\u001B[0m" + Symbol.EMPTY.value);
            } else {
                System.out.print("\u001B[30m\u001B[40m" + Symbol.EMPTY.value);
            }
        } 
        System.out.print("\u001B[0m"); 
    }

    public void drawGameArea() {
        Integer fromY = 1;
        Integer toY = runSize.getRows();
        Integer fromX = 1;
        Integer toX = runSize.getRows()*2;
        drawArea(fromY, toY, fromX, toX);
    }

    public void init() {
        clearPlayArea();
        drawGameArea();
        drawMenu();
    }

    private void resizeTerminal(Integer columns, Integer rows) {
        System.out.print("\u001B[8;" + rows + ";" + columns + "t"); 
    }

    public void drawMenu() {
        running = true;
        input.start();
    }
}
