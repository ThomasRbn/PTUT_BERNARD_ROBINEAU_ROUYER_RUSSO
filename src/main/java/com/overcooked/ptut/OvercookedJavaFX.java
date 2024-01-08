package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import javafx.application.Application;
import javafx.stage.Stage;

public class OvercookedJavaFX extends Application {

    public String chemin = "niveaux/niveau0.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DonneesJeu carte = new DonneesJeu(chemin);
        System.out.println(carte);
    }
}
