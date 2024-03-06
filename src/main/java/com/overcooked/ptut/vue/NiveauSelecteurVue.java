package com.overcooked.ptut.vue;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;

public class NiveauSelecteurVue extends VBox {

    private final ComboBox<String> comboBox;
    private final Button valider;

    public NiveauSelecteurVue(Stage primaryStage) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);

        Text titre = new Text("Bienvenue sur AICooked");

        //Selection du niveau
        comboBox = new ComboBox<>();
        comboBox.setValue("Choisir un niveau");
        File folder = new File("niveaux");
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles);
        for (File file : listOfFiles) {
            if (file.isFile()) {
                comboBox.getItems().add(file.getName().substring(0, file.getName().length() - 4));
            }
        }

        //Bouton de validation
        valider = new Button("Valider");
        valider.setOnAction(e -> {
            String niveauSelectionne = comboBox.getValue();
            if (niveauSelectionne.equals("Choisir un niveau")) return;

            DonneesJeu jeu = new DonneesJeu("niveaux/" + niveauSelectionne + ".txt");

            OptionsSelecteurVue optionsSelecteurVue = new OptionsSelecteurVue(jeu, primaryStage);
            Scene sceneJoueur = new Scene(optionsSelecteurVue, 300, 300);
            primaryStage.setScene(sceneJoueur);
        });

        this.getChildren().addAll(titre, comboBox, valider);
    }
}
