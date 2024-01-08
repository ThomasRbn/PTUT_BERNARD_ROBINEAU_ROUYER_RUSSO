package com.overcooked.ptut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PoelePictogramme extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(200, 200);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        dessinerPoele(gc);

        Scene scene = new Scene(root, 200, 200);
        primaryStage.setTitle("Pictogramme de poêle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void dessinerPoele(GraphicsContext gc) {
        gc.fillOval(50, 50, 100, 100);
        gc.setFill(Color.GRAY);
        gc.fillOval(60, 60, 80, 80);
        gc.setFill(Color.BLACK);
        gc.rotate(45);
        gc.fillRect(135, 40, 20, 60);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
