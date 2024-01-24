package com.overcooked.ptut.vue;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HeaderVue extends HBox {

    DonneesJeu jeu;

    public HeaderVue(DonneesJeu jeu) {
        this.jeu = jeu;
        afficher(jeu.getDepot().getPoints());
    }

    public void afficher(int points) {
        this.getChildren().clear();

        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.web("#e39457"), null, null)));
        Text pts = new Text("Points : " + points);
        pts.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
        this.getChildren().add(pts);
    }

}
