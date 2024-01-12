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
                StackPane caseBloc = new StackPane();

                switch (jeu.getObjetsFixes()[i][j]) {
                    case null:
                        caseBloc.setStyle("-fx-background-color: #e39457;");
                        break;
                    case Depot ignored:
                        caseBloc.setStyle("-fx-background-color: #969696;");
                        break;
                    case Generateur generateur:
                        Rectangle rectangle = new Rectangle(tailleCellule, tailleCellule);
                        Text text = new Text(String.valueOf(generateur.getType().charAt(0)).toUpperCase());
                        text.setFont(Font.font(tailleCellule / 10 * 4));

                        switch (generateur.getType()) {
                            case "Salade":
                                rectangle.setFill(Color.GREEN);
                                break;
                            case "Tomate":
                                rectangle.setFill(Color.RED);
                                break;
                            case "Pain":
                                rectangle.setFill(Color.BROWN);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + generateur.getAliment());
                        }

                        caseBloc.getChildren().addAll(rectangle, text);
                        break;
                    case Planche ignored:
                        caseBloc.setStyle("-fx-background-color: #ffffff;");
                        caseBloc.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        break;
                    case Poele ignored:
                        caseBloc.setStyle("-fx-background-color: #ffe728;");
                        caseBloc.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        break;
                    default:
                        caseBloc.setStyle("-fx-background-color: #a66b3b;");
                        break;
                }
                this.add(caseBloc, j, i);
            }
        }
    }

    private void afficherJoueurs(DonneesJeu jeu) {
        for (Joueur joueur : jeu.getJoueurs()) {
            Pane caseJoueur = new Pane();
            caseJoueur.setStyle("-fx-background-color: #e39457;");

            StackPane visuelJoueur = new StackPane();
            visuelJoueur.setLayoutX(tailleCellule / 10);
            visuelJoueur.setLayoutY(tailleCellule / 10);

            Circle cercle = new Circle(tailleCellule / 2 - tailleCellule / 10);
            cercle.setFill(Color.PURPLE);

            Arc arc = new Arc(tailleCellule / 2, tailleCellule / 2, tailleCellule / 2 - 5, tailleCellule / 2 - 5, 45 + getAngleDirection(joueur), 90);
            arc.setFill(Color.BLACK);
            arc.setType(ArcType.ROUND);


            visuelJoueur.getChildren().addAll(cercle);
            caseJoueur.getChildren().addAll(arc, visuelJoueur);
            afficherInventaireJoueur(joueur, visuelJoueur);
            this.add(caseJoueur, joueur.getPosition()[1], joueur.getPosition()[0]);
        }
    }

    private void afficherInventaireJoueur(Joueur joueur, Pane pane) {
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
