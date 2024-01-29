package com.overcooked.ptut.vue.joueur;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.autonome.JoueurAutoN4;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.vue.aliment.AlimentVue;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

import static com.overcooked.ptut.vue.AfficheurInfobulle.afficherEtatCercle;

public class JoueurVue extends Pane {
    public JoueurVue(double tailleCellule, Joueur joueur) {
        StackPane visuelJoueur = new StackPane();
        visuelJoueur.setLayoutX(tailleCellule / 10);
        visuelJoueur.setLayoutY(tailleCellule / 10);

        Circle cercle = new Circle(tailleCellule / 2 - tailleCellule / 10);
        cercle.setFill(switch (joueur){
            case JoueurHumain ignored -> Color.PURPLE;
            case JoueurAutoN4 ignored -> Color.YELLOW;
            case JoueurIA ignored -> Color.PINK;
            default -> Color.BLACK;
        });

        Arc arc = new Arc(tailleCellule / 2, tailleCellule / 2, tailleCellule / 2 - 5, tailleCellule / 2 - 5, 45 + getAngleDirection(joueur), 90);
        arc.setFill(Color.BLACK);
        arc.setType(ArcType.ROUND);

        visuelJoueur.getChildren().addAll(cercle);
        this.getChildren().addAll(arc, visuelJoueur);
        afficherInventaireJoueur(joueur, visuelJoueur, tailleCellule);
    }

    private void afficherInventaireJoueur(Joueur joueur, Pane pane, double tailleCellule) {
        if (joueur.getInventaire() == null) return;
        List<AlimentVue> aliments = getVueAliments(joueur, tailleCellule);
        pane.getChildren().addAll(aliments);
    }

    private List<AlimentVue> getVueAliments(Joueur joueur, double tailleCellule) {
        List<AlimentVue> aliments = new ArrayList<>();
        List<Aliment> recettesComposees = joueur.getInventaire().getRecettesComposees();
        for (int i = 0; i < recettesComposees.size(); i++) {
            AlimentVue alimentVue = afficherEtatCercle(recettesComposees.get(i), tailleCellule  / 10 * 3, i + 1);
            aliments.add(alimentVue);
        }
        return aliments;
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
