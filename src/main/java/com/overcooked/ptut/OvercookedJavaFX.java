package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Pain;
import com.overcooked.ptut.recettes.aliment.Salade;
import com.overcooked.ptut.recettes.aliment.Tomate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OvercookedJavaFX extends Application {

    public String chemin = "niveaux/niveau1.txt";

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
            plateau.getColumnConstraints().add(new ColumnConstraints(50));
        }

        // création des lignes
        for (int i = 0; i < jeu.getHauteur(); i++) {
            plateau.getRowConstraints().add(new RowConstraints(50));
        }

        Scene scene = new Scene(plateau, jeu.getLongueur() * 50, jeu.getHauteur() * 50);

        afficherBlocs(plateau, jeu);
        afficherJoueurs(plateau, jeu);

        // Ajout des events clavier
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
                    jeu.faireAction(Action.PRENDRE, 0);
                    break;
                case ENTER:
                    jeu.faireAction(Action.POSER, 0);
                    break;
                case SHIFT:
                    jeu.faireAction(Action.COUPER, 0);
                    break;
            }

            // Mise à jour de l'affichage après chaque action
            plateau.getChildren().clear();
            afficherBlocs(plateau, jeu);
            afficherJoueurs(plateau, jeu);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void afficherBlocs(GridPane grille, DonneesJeu jeu) {
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
                        Rectangle rectangle = new Rectangle(50, 50);
                        Text text = new Text(generateur.getAliment().getNom().charAt(0) + "");
                        text.setFont(javafx.scene.text.Font.font(20));

                        switch (generateur.getAliment()) {
                            case Salade ignored:
                                rectangle.setFill(Color.GREEN);
                                break;
                            case Tomate ignored:
                                rectangle.setFill(Color.RED);
                                break;
                            case Pain ignored:
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

//                if (jeu.getObjetsFixes()[i][j] != null) {
//
//                    if (jeu.getObjetsFixes()[i][j] instanceof Depot) { // Si c'est dépot
//                        caseBloc.setStyle("-fx-background-color: #969696;");
//                    } else if (jeu.getObjetsFixes()[i][j] instanceof Generateur) {
//                        Aliment typeGenerateur = ((Generateur) jeu.getObjetsFixes()[i][j]).getAliment();
//
//                    } else {
//                        caseBloc.setStyle("-fx-background-color: #a66b3b;"); // Sinon c'est un plan de travail
//                    }
//
//                } else { // Si c'est un sol
//                    caseBloc.setStyle("-fx-background-color: #e39457;");
//                }
                grille.add(caseBloc, j, i);
            }
        }
    }

    private void afficherJoueurs(GridPane grille, DonneesJeu jeu) {
        for (Joueur joueur : jeu.getJoueurs()) {
            StackPane caseJoueur = new StackPane();
            caseJoueur.setStyle("-fx-background-color: #e39457;");

            Circle cercle = new Circle(20);
            cercle.setFill(Color.PURPLE);
            caseJoueur.getChildren().add(cercle);

            grille.add(caseJoueur, joueur.getPosition()[1], joueur.getPosition()[0]);
        }
    }
}
