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
import javafx.scene.shape.*;
import javafx.scene.text.Font;
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

            }

            // Mise à jour de l'affichage après chaque action
            //TODO Refactor affichage
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
                        text.setFont(Font.font(20));

                        switch (generateur.getAliment().getNom()) {
                            case "salade":
                                rectangle.setFill(Color.GREEN);
                                break;
                            case "tomate":
                                rectangle.setFill(Color.RED);
                                break;
                            case "pain":
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
            joueur.prendre(new Tomate());
            Pane caseJoueur = new Pane();
            caseJoueur.setStyle("-fx-background-color: #e39457;");

            StackPane visuelJoueur = new StackPane();
            visuelJoueur.setLayoutX(5);
            visuelJoueur.setLayoutY(5);

            Circle cercle = new Circle(20);
            cercle.setFill(Color.PURPLE);

            Arc arc = new Arc(25, 25, 22, 22, 45 + getAngle(joueur), 90);
            arc.setFill(Color.BLACK);
            arc.setType(ArcType.ROUND);


            visuelJoueur.getChildren().addAll(cercle);
            caseJoueur.getChildren().addAll(arc, visuelJoueur);
            grille.add(caseJoueur, joueur.getPosition()[1], joueur.getPosition()[0]);
        }
    }

    private void afficherInventaire(Joueur joueur, Pane pane) {
        //TODO faire l'affichage si aliment coupé ou pas
        Circle cercle = new Circle(20);
        switch (joueur.getInventaire()) {
            case Tomate ignored:
                cercle.setFill(Color.RED);
                pane.getChildren().add(cercle);
                break;

            case Salade ignored:
                cercle.setFill(Color.GREEN);
                pane.getChildren().add(cercle);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + joueur.getInventaire());
        }
    }

    private int getAngle(Joueur joueur){
        return switch (joueur.getDirection()) {
            case HAUT -> 0;
            case BAS -> 180;
            case GAUCHE -> 90;
            case DROITE -> 270;
            default -> throw new IllegalStateException("Unexpected value: " + joueur.getDirection());
        };
    }
}
