package com.overcooked.ptut.vue;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
import com.overcooked.ptut.entites.PlanDeTravail;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.vue.bloc.*;
import com.overcooked.ptut.vue.joueur.JoueurVue;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Plateau extends GridPane {

    public double tailleCellule;

    public Plateau(DonneesJeu jeu, double tailleCellule) {
        this.tailleCellule = tailleCellule;
        this.setGridLinesVisible(true);

        // création des colonnes
        for (int i = 0; i < jeu.getLongueur(); i++) {
            this.getColumnConstraints().add(new ColumnConstraints(tailleCellule));
        }

        // création des lignes
        for (int i = 0; i < jeu.getHauteur(); i++) {
            this.getRowConstraints().add(new RowConstraints(tailleCellule));
        }


        afficherBlocs(jeu);
        afficherJoueurs(jeu);
    }

    public void initEventClavier(Scene scene, DonneesJeu jeu) {
        //TODO faire le changement quand il y aura plusieurs joueurs (SOON)
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case Z:
                    jeu.faireAction(Action.HAUT, 0);
                    break;
                case S:
                    jeu.faireAction(Action.BAS, 0);
                    break;
                case Q:
                    jeu.faireAction(Action.GAUCHE, 0);
                    break;
                case D:
                    jeu.faireAction(Action.DROITE, 0);
                    break;
                case SPACE:
                    Joueur joueur = jeu.getJoueurs().getFirst();
                    int[] positionFaceJoueur = joueur.getPositionCible();

                    switch (jeu.getObjetsFixes()[positionFaceJoueur[0]][positionFaceJoueur[1]]) {
                        case Generateur generateur:
                            if (joueur.getInventaire() == null) {
                                joueur.prendre(new Plat(generateur.getType(), generateur.getAliment().getRecettesComposees().getFirst()));
                            }
                            break;
                        case Depot ignored:
                            if (joueur.getInventaire() != null) {
                                joueur.poser();
                            }
                            break;
                        case PlanDeTravail planDeTravail:
                            if (joueur.getInventaire() == null && planDeTravail.getInventaire() == null) return;
                            if (joueur.getInventaire() != null && planDeTravail.getInventaire() == null) {
                                planDeTravail.poserDessus(joueur.poser());
                            } else if (joueur.getInventaire() == null && planDeTravail.getInventaire() != null) {
                                joueur.prendre(planDeTravail.prendre());
                            }
                            break;
                        case null:
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + jeu.getObjetsFixes()[positionFaceJoueur[0]][positionFaceJoueur[1]]);
                    }
                    break;
                case ENTER:
                    Action action = jeu.getJoueurs().getFirst().demanderAction(jeu);
                    jeu.faireAction(action, 0);
                    break;
            }

            // Mise à jour de l'affichage après chaque action
            //TODO Refactor affichage
            this.getChildren().clear();
            jeu.getDepot().viderDepot();
            afficherBlocs(jeu);
            afficherJoueurs(jeu);
            afficherInventaireBloc(jeu);
        });
    }

    private void afficherBlocs(DonneesJeu jeu) {
        //TODO AFFICHAGE POELE / PLANCHE
        for (int i = 0; i < jeu.getHauteur(); i++) {
            for (int j = 0; j < jeu.getLongueur(); j++) {
                Bloc caseBloc = switch (jeu.getObjetsFixes()[i][j]) {
                    case null -> new Bloc();
                    case Depot ignored -> new DepotBloc();
                    case Generateur generateur -> new GenerateurBloc(generateur, tailleCellule);
                    case Planche ignored -> new PlancheBloc();
                    case Poele ignored -> new PoeleBloc();
                    default -> new Mur();
                };
                this.add(caseBloc, j, i);
            }
        }
    }

    private void afficherJoueurs(DonneesJeu jeu) {
        for (Joueur joueur : jeu.getJoueurs()) {
            JoueurVue caseJoueur = new JoueurVue(tailleCellule, joueur);
            //TODO afficher différemment le joueur normal que le joueur IA
            this.add(caseJoueur, joueur.getPosition()[1], joueur.getPosition()[0]);
        }
    }



    private void afficherInventaireBloc(DonneesJeu jeu) {
        for (int i = 0; i < jeu.getHauteur(); i++) {
            for (int j = 0; j < jeu.getLongueur(); j++) {
                StackPane caseBloc = (StackPane) this.getChildren().get(i * jeu.getLongueur() + j);
                if (jeu.getObjetsFixes()[i][j] instanceof PlanDeTravail planDeTravail) {
                    if (planDeTravail.getInventaire() != null) {
                        Circle cercle = new Circle(tailleCellule / 10 * 3);

                        planDeTravail.getInventaire().getRecettesComposees().forEach(aliment -> {
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
                        caseBloc.getChildren().add(cercle);
                    }
                }
            }
        }
    }
}
