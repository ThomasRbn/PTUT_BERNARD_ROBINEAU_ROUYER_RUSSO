package com.overcooked.ptut.vue;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class HeaderVue extends HBox {

    private DonneesJeu jeu;
    private boolean actionJ0;
    private boolean actionJ1;

    public HeaderVue(DonneesJeu jeu) {
        this.jeu = jeu;
        this.actionJ0 = false;
        this.actionJ1 = false;
        afficher(jeu.getDepot().getPoints());
    }

    public void afficher(int points) {
        this.actionJ0 = jeu.getActionsDuTour().getActions().get(jeu.getJoueur(0)) != null;
        this.actionJ1 = jeu.getActionsDuTour().getActions().get(jeu.getJoueur(1)) != null;
        this.getChildren().clear();

        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.web("#e39457"), null, null)));

        Circle cercleJ0 = new Circle(10);
        cercleJ0.setFill(actionJ0 ? Color.GREEN : Color.RED);
        Circle cercleJ1 = new Circle(10);
        cercleJ1.setFill(actionJ1 ? Color.GREEN : Color.RED);

        Text joueur0 = new Text(actionJ0 ? "Joueur 0 prêt" : "Le joueur 0 choisit son action");
        joueur0.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
        Text joueur1 = new Text(actionJ1 ? "Joueur 1 prêt" : "Le joueur 1 choisit son action");
        joueur1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(cercleJ0, joueur0);
        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setSpacing(5);

        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(cercleJ1, joueur1);
        hBox2.setAlignment(Pos.CENTER_LEFT);
        hBox2.setSpacing(5);

        Text pts = new Text("Points : " + points);
        pts.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
        this.getChildren().addAll(hBox, pts, hBox2);
        this.setSpacing(10);
    }

}
