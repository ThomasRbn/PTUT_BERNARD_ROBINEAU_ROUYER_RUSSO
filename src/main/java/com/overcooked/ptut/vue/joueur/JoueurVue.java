package com.overcooked.ptut.vue.joueur;

import com.overcooked.ptut.joueurs.Joueur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

public class JoueurVue extends Pane {
    public JoueurVue(double tailleCellule, Joueur joueur) {
        StackPane visuelJoueur = new StackPane();
        visuelJoueur.setLayoutX(tailleCellule / 10);
        visuelJoueur.setLayoutY(tailleCellule / 10);

        Circle cercle = new Circle(tailleCellule / 2 - tailleCellule / 10);
        cercle.setFill(Color.PURPLE);

        Arc arc = new Arc(tailleCellule / 2, tailleCellule / 2, tailleCellule / 2 - 5, tailleCellule / 2 - 5, 45 + getAngleDirection(joueur), 90);
        arc.setFill(Color.BLACK);
        arc.setType(ArcType.ROUND);

        visuelJoueur.getChildren().addAll(cercle);
        this.getChildren().addAll(arc, visuelJoueur);
        afficherInventaireJoueur(joueur, visuelJoueur, tailleCellule);
    }

    private void afficherInventaireJoueur(Joueur joueur, Pane pane, double tailleCellule) {
        //TODO faire l'affichage si aliment coupé ou pas
        Circle cercle = new Circle(tailleCellule / 10 * 3);

        if (joueur.getInventaire() == null) return;
        joueur.getInventaire().getRecettesComposees().forEach(aliment -> {
            switch (aliment.getNom()) {
                case "Salade":
                    cercle.setFill(Color.GREEN);
                    break;
                case "Tomate":
                    cercle.setFill(Color.RED);
                    break;
                case "Pain":
                    cercle.setFill(Color.BROWN);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + aliment.getNom());
            }
        });

        pane.getChildren().add(cercle);
    }

    private int getAngleDirection(Joueur joueur) {
        return switch (joueur.getDirection()) {
            case HAUT -> 0;
            case BAS -> 180;
            case GAUCHE -> 90;
            case DROITE -> 270;
            default -> throw new IllegalStateException("Unexpected value: " + joueur.getDirection());
        };
    }
}
