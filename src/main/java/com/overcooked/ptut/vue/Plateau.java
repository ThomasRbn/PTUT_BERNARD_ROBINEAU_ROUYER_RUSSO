package com.overcooked.ptut.vue;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.controlleurs.ClavierControlleur;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Depot;
import com.overcooked.ptut.objet.Generateur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.vue.bloc.*;
import com.overcooked.ptut.vue.joueur.JoueurVue;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;
import static com.overcooked.ptut.constructionCarte.GestionActions.isLegal;
import static com.overcooked.ptut.vue.AfficheurInfobulle.afficherEtatCercle;

public class Plateau extends GridPane {

    public double tailleCellule;
    private ClavierControlleur clavierController;

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

        clavierController = new ClavierControlleur(jeu);

        afficherBlocs(jeu);
        afficherJoueurs(jeu);
    }

    public void afficherBlocs(DonneesJeu jeu) {
        for (int i = 0; i < jeu.getHauteur(); i++) {
            for (int j = 0; j < jeu.getLongueur(); j++) {
                BlocVue caseBloc = switch (jeu.getObjetsFixes()[i][j]) {
                    case null -> new BlocVue();
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

    public void afficherJoueurs(DonneesJeu jeu) {
        for (Joueur joueur : jeu.getJoueurs()) {
            //TODO afficher différemment le joueur normal que le joueur IA (créer des classes qui hérietent de JoueurVue)
            JoueurVue caseJoueur = new JoueurVue(tailleCellule, joueur);
            this.add(caseJoueur, joueur.getPosition()[1], joueur.getPosition()[0]);
        }
    }

    public void afficherInventaireBloc(DonneesJeu jeu) {
        for (int i = 0; i < jeu.getHauteur(); i++) {
            for (int j = 0; j < jeu.getLongueur(); j++) {
                StackPane caseBloc = (StackPane) this.getChildren().get(i * jeu.getLongueur() + j);
                if (jeu.getObjetsFixes()[i][j] instanceof PlanDeTravail planDeTravail) {
                    if (planDeTravail.getInventaire() != null) {
                        Circle cercle = new Circle(tailleCellule / 10 * 3);

                        cercle = setCircle(planDeTravail, cercle);
                        caseBloc.getChildren().add(cercle);
                    }
                } else if (jeu.getObjetsFixes()[i][j] instanceof Transformateur transformateur) {
                    if (transformateur.getInventaire() != null) {
                        Circle cercle = new Circle(tailleCellule / 10 * 3);

                        cercle = setCircle(transformateur, cercle);
                        caseBloc.getChildren().add(cercle);
                    }
                }
            }
        }
    }

    private Circle setCircle(Bloc planDeTravail, Circle cercle) {

        for (Aliment aliment : planDeTravail.getInventaire().getRecettesComposees()) {
            cercle = afficherEtatCercle(aliment, tailleCellule);
        }
        return cercle;
    }

    public void afficherPB(DonneesJeu jeu) {
        for (int i = 0; i < jeu.getHauteur(); i++) {
            for (int j = 0; j < jeu.getLongueur(); j++) {
                StackPane caseBloc = (StackPane) this.getChildren().get(i * jeu.getLongueur() + j);
                if (jeu.getObjetsFixes()[i][j] instanceof Transformateur transformateur) {
                    if (transformateur.isBloque()) {
                        ProgressBar progressBar = new ProgressBar(0);
                        progressBar.progressProperty().bind(transformateur.getTransformation().progressProperty());
                        caseBloc.getChildren().add(progressBar);
                        transformateur.setProgressBar(progressBar);
                    } else {
                        if (transformateur.getTransformation() != null) {
                            caseBloc.getChildren().remove(transformateur.getProgressBar());
                        }
                    }
                }
            }
        }

    }

    public void genererTask(Transformateur transformateur, DonneesJeu jeu) {
        // Création d'une tâche pour simuler une opération prenant 3 secondes
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < 100; i++) {
                    updateProgress(i + 1, 100);
                    Thread.sleep(30); // Simule une opération prenant du temps
                }
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            transformateur.setBloque(false);
            transformateur.transform();
            afficherInventaireBloc(jeu);
            afficherPB(jeu);
        });

        new Thread(task).start();

        transformateur.setTransformation(task);
    }

    public ClavierControlleur getClavierController() {
        return clavierController;
    }
}
