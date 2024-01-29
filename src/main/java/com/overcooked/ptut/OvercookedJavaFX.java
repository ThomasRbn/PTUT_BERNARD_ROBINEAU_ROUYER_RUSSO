package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.vue.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
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
