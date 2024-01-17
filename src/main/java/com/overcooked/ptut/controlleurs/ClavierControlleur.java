package com.overcooked.ptut.controlleurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.vue.Plateau;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;
import static com.overcooked.ptut.constructionCarte.GestionActions.isLegal;

public class ClavierControlleur {

    private DonneesJeu jeu;

    public ClavierControlleur(DonneesJeu jeu) {
        this.jeu = jeu;
    }

    public void initEventClavier(Scene scene, Plateau plateau) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            for (Joueur joueur : jeu.getJoueurs()) {
                if (joueur instanceof JoueurHumain) {
                    handleHumainInput(key, joueur, plateau);
                } else if (joueur instanceof JoueurIA) {
                    handleIAInput(key, joueur);
                }
            }

            // Mise à jour de l'affichage après chaque action
            // TODO Refactor affichage
            plateau.getChildren().clear();
            jeu.getDepot().viderDepot();
            plateau.afficherBlocs(jeu);
            plateau.afficherJoueurs(jeu);
            plateau.afficherInventaireBloc(jeu);
            plateau.afficherPB(jeu);
        });
    }

    private void handleHumainInput(KeyEvent key, Joueur joueur, Plateau plateau) {
        switch (key.getCode()) {
            case Z:
                faireAction(Action.HAUT, joueur.getNumJoueur(), jeu);
                break;
            case S:
                faireAction(Action.BAS, joueur.getNumJoueur(), jeu);
                break;
            case Q:
                faireAction(Action.GAUCHE, joueur.getNumJoueur(), jeu);
                break;
            case D:
                faireAction(Action.DROITE, joueur.getNumJoueur(), jeu);
                break;
            case E:
                faireAction(Action.UTILISER, joueur.getNumJoueur(), jeu, plateau);
                break;
            case SPACE:
                if (isLegal(Action.PRENDRE, joueur.getNumJoueur(), jeu)) {
                    faireAction(Action.PRENDRE, joueur.getNumJoueur(), jeu);
                } else if (isLegal(Action.POSER, joueur.getNumJoueur(), jeu)) {
                    faireAction(Action.POSER, joueur.getNumJoueur(), jeu);
                }
                break;
            default:
                break;
        }
    }

    private void handleIAInput(KeyEvent key, Joueur joueur) {
        if (key.getCode() == javafx.scene.input.KeyCode.ENTER) {
            faireAction(joueur.demanderAction(jeu), joueur.getNumJoueur(), jeu);
        }
    }
}
