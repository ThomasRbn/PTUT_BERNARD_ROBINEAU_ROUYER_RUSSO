package com.overcooked.ptut.vue;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.autonome.JoueurAutoN4;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.ia.JoueurIADecentr;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.overcooked.ptut.OvercookedJavaFX.TAILLE_CELLULE;

public class OptionsSelecteurVue extends VBox {

    private final List<ComboBox<String>> comboList;
    private final TextField dureeJeu;

    public OptionsSelecteurVue(DonneesJeu jeu, Stage primaryStage) {
        this.setSpacing(15);

        //Création des champs des joueurs
        comboList = new ArrayList<>();
        for (int i = 0; i < jeu.getCoordonneesJoueurs().size(); i++) {
            Text titre = new Text("Joueur " + i);
            this.getChildren().add(titre);

            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.setValue("Choisir un type de joueur");
            comboBox.getItems().add("Humain");
            comboBox.getItems().add("IA");
            comboBox.getItems().add("AutoN4");
            comboBox.getItems().add("IADecentralisee");
            this.getChildren().add(comboBox);
            comboList.add(comboBox);
            this.setAlignment(Pos.CENTER);
        }

        //Champ de selection du temps
        dureeJeu = new TextField();
        dureeJeu.setPromptText("Entrez la durée de la partie en secondes");
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        });
        dureeJeu.setTextFormatter(formatter);
        dureeJeu.setMinSize(250, 25);
        dureeJeu.setPrefSize(250, 25);
        dureeJeu.setMaxSize(250, 25);
        dureeJeu.setAlignment(Pos.CENTER);
        this.getChildren().add(dureeJeu);

        //Bouton de validation
        Button valider = new Button("Valider");
        this.getChildren().add(valider);

        //Action du bouton de validation
        valider.setOnAction(e2 -> {
            //Si il n'y a pas de durée ou si le joueur n'a pas choisi de type de joueur
            if (dureeJeu.getText().isEmpty() || comboList.stream().anyMatch(comboBox -> comboBox.getValue().equals("Choisir un type de joueur"))) {
                return;
            }

            for (int i = 0; i < comboList.size(); i++) {
                System.out.println(jeu.getCoordonneesJoueurs().get(i)[0] + " " + jeu.getCoordonneesJoueurs().get(i)[1]);
                switch (comboList.get(i).getValue()) {
                    case "Humain" ->
                            jeu.getJoueurs().add(new JoueurHumain(jeu.getCoordonneesJoueurs().get(i)[0], jeu.getCoordonneesJoueurs().get(i)[1]));
                    case "IA" ->
                            jeu.getJoueurs().add(new JoueurIA(jeu.getCoordonneesJoueurs().get(i)[0], jeu.getCoordonneesJoueurs().get(i)[1]));
                    case "AutoN4" ->
                            jeu.getJoueurs().add(new JoueurAutoN4(jeu.getCoordonneesJoueurs().get(i)[0], jeu.getCoordonneesJoueurs().get(i)[1]));
                    case "IADecentralisee" ->
                            jeu.getJoueurs().add(new JoueurIADecentr(jeu.getCoordonneesJoueurs().get(i)[0], jeu.getCoordonneesJoueurs().get(i)[1]));
                }
                jeu.getJoueur(i).setNumJoueur(i);
            }

            int duree = Integer.parseInt(dureeJeu.getText());

            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, (jeu.getLongueur() * TAILLE_CELLULE) * 2, jeu.getHauteur() * TAILLE_CELLULE + TAILLE_CELLULE);

            PlateauVue plateau = new PlateauVue(jeu, TAILLE_CELLULE);
            root.setCenter(plateau);

            CommandeVue commandeVue = new CommandeVue(jeu.getPlatsBut());
            commandeVue.setPrefSize(jeu.getLongueur() * TAILLE_CELLULE, jeu.getHauteur() * TAILLE_CELLULE);
            commandeVue.setBorder(Border.EMPTY);
            root.setRight(commandeVue);

            HeaderVue headerVue = new HeaderVue(jeu, duree);
            headerVue.setPrefSize(jeu.getLongueur() * TAILLE_CELLULE * 2, TAILLE_CELLULE);
            root.setTop(headerVue);

            plateau.getClavierController().initEventClavier(scene, plateau, headerVue);
            plateau.getClavierController().lancerThreadIA(jeu, plateau, headerVue);
            primaryStage.setScene(scene);
            primaryStage.setHeight(jeu.getHauteur() * TAILLE_CELLULE + TAILLE_CELLULE);
            primaryStage.setWidth((jeu.getLongueur() * TAILLE_CELLULE) * 2);
        });
    }
}
