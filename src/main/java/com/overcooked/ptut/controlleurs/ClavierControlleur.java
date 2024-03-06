package com.overcooked.ptut.controlleurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.vue.HeaderVue;
import com.overcooked.ptut.vue.PlateauVue;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.List;

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
    public void initEventClavier(Scene scene, PlateauVue plateau, HeaderVue header) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if (jeu.isJeuTermine()) return;
            for (Joueur joueur : jeu.getJoueurs()) {
                if (joueur instanceof JoueurHumain) {
                    handleHumainInput(key, joueur, plateau, jeu);
                }
            }

            // Mise à jour de l'affichage après chaque action
            // TODO Refactor affichage
            plateau.getChildren().clear();
            jeu.getDepot().viderDepot();
            plateau.afficherBlocs(jeu);
            plateau.afficherJoueurs(jeu);
            plateau.afficherInventaireBloc(jeu);
            header.afficher();
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
    private Action handleHumainInput(KeyEvent key, Joueur joueur, PlateauVue plateau, DonneesJeu jeu) {
        Action action = null;
        switch (key.getCode()) {
            case Z:
                action = Action.HAUT;
                break;
            case S:
                action = Action.BAS;
                break;
            case Q:
                action = Action.GAUCHE;
                break;
            case D:
                action = Action.DROITE;
                break;
            case R:
                action = Action.RIEN;
            case E:
                action = Action.UTILISER;
//                    int[] cible = joueur.getPositionCible();
//                    Transformateur transformateur = (Transformateur) jeu.getObjetsFixes()[cible[0]][cible[1]];
//                    plateau.genererTask(transformateur, jeu);
                break;
            case SPACE:
                action = Action.PRENDRE;
                break;
            default:
                break;
        }
        if (action == Action.PRENDRE) {
            if (isLegal(Action.PRENDRE, joueur.getNumJoueur(), jeu)) {
                jeu.getActionsDuTour().ajouterAction(joueur, Action.PRENDRE);
            } else if (isLegal(Action.POSER, joueur.getNumJoueur(), jeu)) {
                jeu.getActionsDuTour().ajouterAction(joueur, Action.POSER);
            }
        }
        if (action != null && isLegal(action, joueur.getNumJoueur(), jeu)) {
            jeu.getActionsDuTour().ajouterAction(joueur, action);
        }
        return action;
    }

    public void lancerThreadIA(DonneesJeu jeu, PlateauVue plateau, HeaderVue header) {
        List<Joueur> joueursIA = jeu.getJoueurs().stream().filter(joueur -> joueur instanceof JoueurIA).toList();
        new Thread(() -> {
            Action actionIA = null;
            while (!jeu.isJeuTermine()) {
                for (Joueur joueur : joueursIA) {
                    long now = System.nanoTime();
                    actionIA = joueur.demanderAction(jeu);
                    System.out.println("Temps IA : " + (System.nanoTime() - now) / 1_000 + "µs");
                    System.out.println("Action IA : " + actionIA);
                    Action finalActionIA = actionIA;
                    AnimationTimer an =  new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            plateau.ajouterPoint(joueur, finalActionIA);
                        }
                    };
                    an.start();
                    jeu.getActionsDuTour().ajouterAction(joueur, actionIA);
                }
                while (!jeu.getActionsDuTour().isTourTermine()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                AnimationTimer animationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        plateau.getChildren().clear();
                        jeu.getDepot().viderDepot();
                        plateau.afficherBlocs(jeu);
                        plateau.afficherJoueurs(jeu);
                        plateau.afficherInventaireBloc(jeu);
                        header.afficher();
                    }
                };
                animationTimer.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
