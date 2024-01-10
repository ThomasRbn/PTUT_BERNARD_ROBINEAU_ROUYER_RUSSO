package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
import com.overcooked.ptut.entites.PlanDeTravail;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;
import javafx.application.Application;
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
import javafx.stage.Stage;

public class OvercookedJavaFX extends Application {

    public String chemin = "niveaux/niveau1.txt";
    public double tailleCellule = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DonneesJeu jeu = new DonneesJeu(chemin);

        GridPane plateau = new GridPane();
        plateau.setGridLinesVisible(true);

        // création des colonnes
        for (int i = 0; i < jeu.getLongueur(); i++) {
            plateau.getColumnConstraints().add(new ColumnConstraints(tailleCellule));
        }

        // création des lignes
        for (int i = 0; i < jeu.getHauteur(); i++) {
            plateau.getRowConstraints().add(new RowConstraints(tailleCellule));
        }

        Scene scene = new Scene(plateau, jeu.getLongueur() * tailleCellule, jeu.getHauteur() * tailleCellule);

        afficherBlocs(plateau, jeu);
        afficherJoueurs(plateau, jeu);
        initEventClavier(scene, jeu, plateau);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initEventClavier(Scene scene, DonneesJeu jeu, GridPane plateau) {
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
                    //TODO Implémenter la prise de l'aliment quand OK
                    Joueur joueur = jeu.getJoueurs().getFirst();
                    int[] positionFaceJoueur = joueur.getPositionCible();

                    switch (jeu.getObjetsFixes()[positionFaceJoueur[0]][positionFaceJoueur[1]]) {
                        case Generateur generateur:
                            if (joueur.getInventaire() == null) {
                                joueur.prendre(new Plat(generateur.getAliment().getNom(), generateur.getAliment()));
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
            plateau.getChildren().clear();
            afficherBlocs(plateau, jeu);
            afficherJoueurs(plateau, jeu);
            afficherInventaireBloc(jeu, plateau);
        });
    }

    private void afficherBlocs(GridPane grille, DonneesJeu jeu) {
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
                        Text text = new Text(String.valueOf(generateur.getAliment().getNom().charAt(0)).toUpperCase());
                        text.setFont(Font.font(tailleCellule / 10 * 4));

                        switch (generateur.getAliment().getNom()) {
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
                    default:
                        caseBloc.setStyle("-fx-background-color: #a66b3b;");
                        break;
                }
                grille.add(caseBloc, j, i);
            }
        }
    }

    private void afficherJoueurs(GridPane grille, DonneesJeu jeu) {
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
            grille.add(caseJoueur, joueur.getPosition()[1], joueur.getPosition()[0]);
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

    private void afficherInventaireBloc(DonneesJeu jeu, GridPane grille) {
        for (int i = 0; i < jeu.getHauteur(); i++) {
            for (int j = 0; j < jeu.getLongueur(); j++) {
                StackPane caseBloc = (StackPane) grille.getChildren().get(i * jeu.getLongueur() + j);
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
