package com.overcooked.ptut.controlleurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.vue.Plateau;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;
import static com.overcooked.ptut.constructionCarte.GestionActions.isLegal;

public class ClavierControlleur {

    private DonneesJeu jeu;

    public ClavierControlleur(DonneesJeu jeu) {
        this.jeu = jeu;
    }

    /**
     * Initialise les évènements clavier et met à jour l'affichage à chaque entrée
     *
     * @param scene   Scene
     * @param plateau Plateau
     */
    public void initEventClavier(Scene scene, Plateau plateau) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            for (Joueur joueur : jeu.getJoueurs()) {
                if (joueur instanceof JoueurHumain) {
                    handleHumainInput(key, joueur, plateau);
                }
            }

            // Mise à jour de l'affichage après chaque action
            // TODO Refactor affichage
            plateau.getChildren().clear();
            jeu.getDepot().viderDepot();
            plateau.afficherBlocs(jeu);
            plateau.afficherJoueurs(jeu);
            plateau.afficherInventaireBloc(jeu);
//            plateau.affichageProgressBar(jeu);
        });
    }

    /**
     * Gère les entrées clavier pour un joueur humain
     *
     * @param key     KeyEvent
     * @param joueur  Joueur
     * @param plateau Plateau
     */
    private void handleHumainInput(KeyEvent key, Joueur joueur, Plateau plateau) {
        switch (key.getCode()) {
            case Z:
                if (isLegal(Action.HAUT, joueur.getNumJoueur(), jeu))
                    faireAction(Action.HAUT, joueur.getNumJoueur(), jeu);
                break;
            case S:
                if (isLegal(Action.BAS, joueur.getNumJoueur(), jeu))
                    faireAction(Action.BAS, joueur.getNumJoueur(), jeu);
                break;
            case Q:
                if (isLegal(Action.GAUCHE, joueur.getNumJoueur(), jeu))
                    faireAction(Action.GAUCHE, joueur.getNumJoueur(), jeu);
                break;
            case D:
                if (isLegal(Action.DROITE, joueur.getNumJoueur(), jeu))
                    faireAction(Action.DROITE, joueur.getNumJoueur(), jeu);
                break;
            case E:
                if (isLegal(Action.UTILISER, joueur.getNumJoueur(), jeu)) {
                    faireAction(Action.UTILISER, joueur.getNumJoueur(), jeu);
//                    int[] cible = joueur.getPositionCible();
//                    Transformateur transformateur = (Transformateur) jeu.getObjetsFixes()[cible[0]][cible[1]];
//                    plateau.genererTask(transformateur, jeu);
                }
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

    public void lancerThreadIA(DonneesJeu jeu, Plateau plateau) {
        for (Joueur joueur : jeu.getJoueurs()) {
            if (joueur instanceof JoueurIA) {
                new Thread(() -> {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            faireAction(joueur.demanderAction(jeu), joueur.getNumJoueur(), jeu);
                            System.out.println(jeu);
                            AnimationTimer animationTimer = new AnimationTimer() {
                                @Override
                                public void handle(long now) {
                                    plateau.getChildren().clear();
                                    jeu.getDepot().viderDepot();
                                    plateau.afficherBlocs(jeu);
                                    plateau.afficherJoueurs(jeu);
                                    plateau.afficherInventaireBloc(jeu);
                                }
                            };
                            animationTimer.start();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
//                Platform.runLater(() -> {
//                    while (true) {
//                        try {
//                            Thread.sleep(1000);
//                            faireAction(joueur.demanderAction(jeu), joueur.getNumJoueur(), jeu);
//                            System.out.println(jeu);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
            }
        }
    }
}
