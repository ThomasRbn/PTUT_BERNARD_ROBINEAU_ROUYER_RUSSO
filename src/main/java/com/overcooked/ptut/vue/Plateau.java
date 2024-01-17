package com.overcooked.ptut.vue;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.controlleurs.ClavierControlleur;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Depot;
import com.overcooked.ptut.objet.Generateur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.vue.bloc.*;
import com.overcooked.ptut.vue.joueur.JoueurVue;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

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

    /**
     * Affiche les blocs de la carte avec leurs couleurs respectives
     *
     * @param jeu Données du jeu
     */
    public void afficherBlocs(DonneesJeu jeu) {
        for (int i = 0; i < jeu.getHauteur(); i++) { //Double boucle pour parcourir tous les blocs
            for (int j = 0; j < jeu.getLongueur(); j++) {
                BlocVue caseBloc = switch (jeu.getObjetsFixes()[i][j]) { //Création de la case en fonction du bloc
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

    /**
     * Affiche les joueurs sur la carte ainsi que leur inventaire et la direction de leur regard
     *
     * @param jeu Données du jeu
     */
    public void afficherJoueurs(DonneesJeu jeu) {
        for (Joueur joueur : jeu.getJoueurs()) {
            JoueurVue caseJoueur = new JoueurVue(tailleCellule, joueur);
            this.add(caseJoueur, joueur.getPosition()[1], joueur.getPosition()[0]);
        }
    }

    /**
     * Affiche l'inventaire des blocs qui contiennent un aliment ou un plat
     *
     * @param jeu Données du jeu
     */
    public void afficherInventaireBloc(DonneesJeu jeu) {
        for (int i = 0; i < jeu.getHauteur(); i++) { //Double boucle pour parcourir tous les blocs
            for (int j = 0; j < jeu.getLongueur(); j++) {
                StackPane caseBloc = (StackPane) this.getChildren().get(i * jeu.getLongueur() + j); // Récupération de la case

                // Les PDT et les Transfo sont les seuls blocs qui doivent afficher leur inventaire
                if (jeu.getObjetsFixes()[i][j] instanceof PlanDeTravail || jeu.getObjetsFixes()[i][j] instanceof Transformateur) {
                    Bloc bloc = jeu.getObjetsFixes()[i][j];
                    if (bloc.getInventaire() != null) {
                        caseBloc.getChildren().add(empilagePlat(bloc));
                    }
                }
            }
        }
    }

    /**
     * Affiche l'inventaire des blocs qui contiennent un aliment ou un plat
     *
     * @param bloc Données du jeu
     * @return StackPane contenant les cercles représentant les plats
     */
    private StackPane empilagePlat(Bloc bloc) {
        StackPane caseBloc = new StackPane();
        for (int i = 0; i < bloc.getInventaire().getRecettesComposees().size(); i++) {
            caseBloc.getChildren().add(afficherEtatCercle(bloc.getInventaire().getRecettesComposees().get(i), tailleCellule, i + 1));
        }
        return caseBloc;
    }

//    public void affichageProgressBar(DonneesJeu jeu) {
//        for (int i = 0; i < jeu.getHauteur(); i++) {
//            for (int j = 0; j < jeu.getLongueur(); j++) {
//                StackPane caseBloc = (StackPane) this.getChildren().get(i * jeu.getLongueur() + j);
//                if (jeu.getObjetsFixes()[i][j] instanceof Transformateur transformateur) {
//                    if (transformateur.isBloque()) {
//                        ProgressBar progressBar = new ProgressBar(0);
//                        progressBar.progressProperty().bind(transformateur.getTransformation().progressProperty());
//                        caseBloc.getChildren().add(progressBar);
//                        transformateur.setProgressBar(progressBar);
//                    } else {
//                        if (transformateur.getTransformation() != null) {
//                            caseBloc.getChildren().remove(transformateur.getProgressBar());
//                        }
//                    }
//                }
//            }
//        }
//    }

//    public void genererTask(Transformateur transformateur, DonneesJeu jeu) {
//        // Création d'une tâche pour simuler une opération prenant 3 secondes
//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                for (int i = 0; i < 100; i++) {
//                    updateProgress(i + 1, 100);
//                    Thread.sleep(30); // Simule une opération prenant du temps
//                }
//                return null;
//            }
//        };
//
//        task.setOnSucceeded(e -> {
//        transformateur.setBloque(false);
//        transformateur.transform();
//        afficherInventaireBloc(jeu);
//        affichageProgressBar(jeu);
//        });
//
//        new Thread(task).start();
//
//        transformateur.setTransformation(task);
//    }

    public ClavierControlleur getClavierController() {
        return clavierController;
    }
}
