package com.xmudronc;

import java.io.IOException;

import org.jline.terminal.TerminalBuilder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
    private static Game game;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        var javaVersion = "SystemInfo.javaVersion()";
        var javafxVersion = "SystemInfo.javafxVersion()";

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();

        Platform.runLater(() -> run(stage));
    }

    private void run(Stage stage) {
        try {
            game = new Game(TerminalBuilder.builder().build(), stage);
            game.run();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
}
