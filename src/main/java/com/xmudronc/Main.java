package com.xmudronc;

import java.io.IOException;

import org.jline.terminal.TerminalBuilder;

public class Main {
    private static Game game;

    public static void main(String[] args) {
        try {
            game = new Game(TerminalBuilder.builder().build());
            game.run();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
