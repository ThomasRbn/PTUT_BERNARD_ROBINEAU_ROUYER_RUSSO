package com.overcooked.ptut;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ProgressBarExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX ProgressBar Example");

        Circle circle = new Circle(40, 40, 30);

        // Création de la mise en page
        StackPane root = new StackPane();
        root.getChildren().addAll(circle);

        // Création de la scène
        Scene scene = new Scene(root, 300, 150);

        // Configuration de la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.show();

        // Ajout d'un écouteur d'événements sur la scène pour détecter la touche E
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                // Création de la ProgressBar
                ProgressBar progressBar = new ProgressBar(0);
                // Affiche la ProgressBar uniquement lorsque la touche E est enfoncée
                root.getChildren().add(progressBar);

                // Création d'une tâche pour simuler une opération prenant 3 secondes
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        for (int i = 0; i < 100; i++) {
                            updateProgress(i + 1, 100);
                            Thread.sleep(30); // Simule une opération prenant du temps
                        }
                        return null;
                    }
                };

                // Liaison de la ProgressBar à la propriété progress de la tâche
                progressBar.progressProperty().bind(task.progressProperty());

                // Ajout d'un écouteur d'événements sur la propriété state de la tâche
                task.setOnSucceeded(taskEvent -> {
                    // Action à effectuer lorsque la tâche est terminée (état SUCCEEDED)
                    root.getChildren().remove(progressBar);
                });

                // Démarrage de la tâche dans un nouveau thread
                new Thread(task).start();
            }
        });
    }
}
