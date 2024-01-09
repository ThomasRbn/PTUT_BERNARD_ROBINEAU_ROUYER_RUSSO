package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.joueurs.Joueur;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class OvercookedJavaFX extends Application {

    public String chemin = "niveaux/niveau0.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DonneesJeu jeu = new DonneesJeu(chemin);
        Overcooked overcooked = new Overcooked();

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

        afficherBlocs(plateau, jeu);
        afficherJoueurs(plateau, jeu);

        primaryStage.setScene(new Scene(plateau, jeu.getLongueur() * 50, jeu.getHauteur() * 50));
        primaryStage.show();

        // Set up the game loop using Timeline
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            System.out.println("Boucle");
            overcooked.jeu(jeu);

            System.out.println("Before initSol");
            initSol(plateau, jeu);
            System.out.println("After initSol");

            System.out.println("Before afficherJoueurs");
            afficherJoueurs(plateau, jeu);
            System.out.println("After afficherJoueurs");
        }));


        // Set the cycle count to INDEFINITE for continuous looping
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        // Start the game loop
        gameLoop.play();
    }

    private void initSol(GridPane grille, DonneesJeu jeu) {
        for (int i = 0; i < jeu.getHauteur(); i++) {
            for (int j = 0; j < jeu.getLongueur(); j++) {
                if (jeu.getObjetsFixes()[i][j] == null) {
                    Pane caseBloc = new Pane();
                    caseBloc.setStyle("-fx-background-color: #e39457;");
                    grille.add(caseBloc, j, i);
                }
            }
        }
    }

    private void afficherBlocs(GridPane grille, DonneesJeu jeu) {
        for (int i = 0; i < jeu.getHauteur(); i++) {
            for (int j = 0; j < jeu.getLongueur(); j++) {
                Pane caseBloc = new Pane();
                if (jeu.getObjetsFixes()[i][j] != null) {
                    if (jeu.getObjetsFixes()[i][j] instanceof Depot) {
                        caseBloc.setStyle("-fx-background-color: #969696;");
                    } else {
                        caseBloc.setStyle("-fx-background-color: #a66b3b;");
                    }
                } else {
                    caseBloc.setStyle("-fx-background-color: #e39457;");
                }
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
