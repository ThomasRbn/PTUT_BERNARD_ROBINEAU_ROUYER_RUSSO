package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.vue.CommandeVue;
import com.overcooked.ptut.vue.HeaderVue;
import com.overcooked.ptut.vue.PlateauVue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OvercookedJavaFX extends Application {

    public String chemin = "niveaux/niveau4.txt";
    public double tailleCellule = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DonneesJeu jeu = new DonneesJeu(chemin);

        BorderPane root = new BorderPane();

        PlateauVue plateau = new PlateauVue(jeu, tailleCellule);
        root.setCenter(plateau);

        Scene scene = new Scene(root, (jeu.getLongueur() * tailleCellule) * 2, jeu.getHauteur() * tailleCellule + tailleCellule);

        CommandeVue commandeVue = new CommandeVue(jeu.getPlatsBut());
        commandeVue.setPrefSize(jeu.getLongueur() * tailleCellule, jeu.getHauteur() * tailleCellule);
        commandeVue.setBorder(Border.EMPTY);
        root.setRight(commandeVue);

        HeaderVue headerVue = new HeaderVue(jeu);
        headerVue.setPrefSize(jeu.getLongueur() * tailleCellule * 2, tailleCellule);
        root.setTop(headerVue);

        plateau.getClavierController().initEventClavier(scene, plateau, headerVue);
        plateau.getClavierController().lancerThreadIA(jeu, plateau, headerVue);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
