package com.overcooked.ptut.stats;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.stats.export.ExportCSV;
import com.overcooked.ptut.stats.strategieCollecte.StrategieCollecte;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DonneesStats {

    private DonneesJeu donneesJeu;
    private Duo combinaison;
    private StrategieCollecte strategieCollecte;
    protected int nbTours = 0;
    protected long[] tempsCalculs;

    public DonneesStats(DonneesJeu jeu, StrategieCollecte strategieCollecte, Duo combinaison) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.donneesJeu = jeu;
        this.combinaison = combinaison;
        this.strategieCollecte = strategieCollecte;
        this.tempsCalculs = new long[2];
        jeu.getJoueurs().add(combinaison.j1().getDeclaredConstructor(int.class, int.class).newInstance(jeu.getCoordonneesJoueurs().getFirst()[0], jeu.getCoordonneesJoueurs().getFirst()[1]));
        jeu.getJoueurs().add(combinaison.j2().getDeclaredConstructor(int.class, int.class).newInstance(jeu.getCoordonneesJoueurs().getLast()[0], jeu.getCoordonneesJoueurs().getLast()[1]));
        jeu.getJoueur(0).setNumJoueur(0);
        jeu.getJoueur(1).setNumJoueur(1);
    }

    public void lancerPartie() {
        System.out.println("Lancement de la partie avec les joueurs " + combinaison.j1().getSimpleName() + " et " + combinaison.j2().getSimpleName());
        strategieCollecte.initierTemps();
        List<Joueur> joueursIA = donneesJeu.getJoueurs().stream().filter(joueur -> joueur instanceof JoueurIA).toList();
        Thread thJ1 = new Thread(() -> {
            Action actionIA;
            while (!strategieCollecte.getConditionArretSatisfaite()) {
                for (Joueur joueur : joueursIA) {
                    long start = System.nanoTime();
                    actionIA = joueur.demanderAction(donneesJeu);
                    long end = System.nanoTime();
                    tempsCalculs[joueur.getNumJoueur()] += end - start;

                    donneesJeu.getActionsDuTour().ajouterAction(joueur, actionIA);
                }
                while (!donneesJeu.getActionsDuTour().isTourTermine()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                donneesJeu.getDepot().viderDepot();
                nbTours++;
                if (strategieCollecte.getConditionArretSatisfaite()) {
                    System.out.println("Fin de la partie " + nbTours + " tours réalisés");
                    System.out.println("Nombre de pts réalisés : " + donneesJeu.getDepot().getPoints() + " points");
                    try {
                        ExportCSV.exportCSV(this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thJ1.start();
    }

    public DonneesJeu getDonneesJeu() {
        return donneesJeu;
    }

    public Duo getCombinaison() {
        return combinaison;
    }

    public StrategieCollecte getStrategieCollecte() {
        return strategieCollecte;
    }

    public int getNbTours() {
        return nbTours;
    }

    public long[] getTempsCalculs() {
        return tempsCalculs;
    }
}
