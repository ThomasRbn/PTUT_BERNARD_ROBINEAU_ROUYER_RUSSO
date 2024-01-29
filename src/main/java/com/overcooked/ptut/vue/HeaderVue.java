package com.overcooked.ptut.vue;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class HeaderVue extends HBox {

    private DonneesJeu jeu;
    private boolean actionJ0;
    private boolean actionJ1;
    private long tempsRestant;
    private int dureeDuJeu;


    public HeaderVue(DonneesJeu jeu, int duree) {
        this.jeu = jeu;
        this.actionJ0 = false;
        this.actionJ1 = false;
        this.dureeDuJeu = duree;
        lancerTimer();
    }

    public void afficher() {
        if (jeu.isJeuTermine()) {
            return;
        }
        this.getChildren().clear();

        int points = jeu.getDepot().getPoints();

        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.web("#e39457"), null, null)));

        Circle cercleJ0 = new Circle(10);
        cercleJ0.setFill(actionJ0 ? Color.GREEN : Color.RED);

        this.actionJ0 = jeu.getActionsDuTour().getActions().get(jeu.getJoueur(0)) != null;
        Text joueur0 = new Text("Joueur 0");
        joueur0.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cercleJ0, joueur0);
        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setSpacing(5);

        Text pts = new Text("Points : " + points);
        pts.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");

        Text timerText = new Text("Temps restant : " + (tempsRestant) + " s");
        timerText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: red;");

        this.getChildren().addAll(hBox, pts, timerText);

        if (jeu.getJoueurs().size() == 2) {
            this.actionJ1 = jeu.getActionsDuTour().getActions().get(jeu.getJoueur(1)) != null;
            Circle cercleJ1 = new Circle(10);
            cercleJ1.setFill(actionJ1 ? Color.GREEN : Color.RED);
            Text joueur1 = new Text("Joueur 1");
            joueur1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
            HBox hBox2 = new HBox();
            hBox2.getChildren().addAll(cercleJ1, joueur1);
            hBox2.setAlignment(Pos.CENTER_LEFT);
            hBox2.setSpacing(5);
            this.getChildren().add(hBox2);
        }

        this.setSpacing(10);
    }

    public void lancerTimer() {
        Timer timer = new Timer();
        long delai = dureeDuJeu * 1000L; // Changer ici le temps de la partie
        long delaiMAJ = 1000;

        TimerTask timerTask = new TimerTask() {
            int remainingTime = (int) (delai / 1000);

            @Override
            public void run() {
                if (remainingTime >= 0) {
                    tempsRestant = remainingTime;
                    new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            afficher();
                        }
                    }.start();
                    remainingTime--;
                } else {
                    jeu.setJeuTermine(true);
                    new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            afficherEcranFin();
                        }
                    }.start();
                    timer.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, delaiMAJ);
    }

    public void afficherEcranFin() {
        this.getChildren().clear();
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.web("#e39457"), null, null)));
        Text fin = new Text("Fin de la partie, votre score est de : " + jeu.getDepot().getPoints() + " points");
        fin.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
        this.getChildren().add(fin);
    }

}
