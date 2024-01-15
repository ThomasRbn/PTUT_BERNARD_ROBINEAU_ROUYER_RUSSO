package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.vue.Plateau;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OvercookedJavaFX extends Application {

    public String chemin = "niveaux/niveau0_2J.txt";
    public double tailleCellule = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DonneesJeu jeu = new DonneesJeu(chemin);

        Plateau plateau = new Plateau(jeu, tailleCellule);
        Scene scene = new Scene(plateau, jeu.getLongueur() * tailleCellule, jeu.getHauteur() * tailleCellule);
        plateau.initEventClavier(scene, jeu);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
