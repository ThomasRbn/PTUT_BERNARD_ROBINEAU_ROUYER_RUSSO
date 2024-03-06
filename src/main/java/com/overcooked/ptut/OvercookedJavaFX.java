package com.overcooked.ptut;

import com.overcooked.ptut.vue.NiveauSelecteurVue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OvercookedJavaFX extends Application {

    public static double TAILLE_CELLULE = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        NiveauSelecteurVue niveauSelect = new NiveauSelecteurVue(primaryStage);
        Scene sceneMenu = new Scene(niveauSelect, 300, 300);

        primaryStage.setScene(sceneMenu);
        primaryStage.show();
    }

}
