package com.xmudronc.renderer;

import org.jline.terminal.Size;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FxRenderer extends Application implements Renderer {
    private Size startupSize;
    private Size runSize;
    private Stage stage;

    public FxRenderer(Size startupSize, Size runSize, Stage stage) {
        this.startupSize = startupSize;
        this.runSize = runSize;
        this.stage = stage;
    }

    @Override
    public void init(RGB[][] buffer1, RGB[][] buffer2) {
        
    }

    @Override
    public void render(RGB[][] buffer1, RGB[][] buffer2) {
        
    }

    @Override
    public void end() {
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("FxRenderer init.");
        var label = new Label("Hello.");
        var scene = new Scene(new StackPane(label), 480, 480);
        stage.setScene(scene);
        System.out.println("FxRenderer stage.show().");
        stage.show();
        System.out.println("FxRenderer stage.show().");
    }
    
}
