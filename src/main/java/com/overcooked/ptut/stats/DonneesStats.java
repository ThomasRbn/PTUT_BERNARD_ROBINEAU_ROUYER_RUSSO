package com.overcooked.ptut.stats;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.stats.strategieCollecte.StrategieCollecte;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import static com.overcooked.ptut.MainCollecteStats.*;

public class DonneesStats {

    protected int nbTours = 0;
    protected long[] tempsCalculs;
    private DonneesJeu donneesJeu;
    private Duo combinaison;
    private StrategieCollecte strategieCollecte;
    private Thread thread;

    public DonneesStats(DonneesJeu jeu, Class<? extends StrategieCollecte> strategieCollecte, Duo combinaison) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.donneesJeu = jeu;
        this.combinaison = combinaison;
        this.strategieCollecte = strategieCollecte.getDeclaredConstructor().newInstance();
        this.tempsCalculs = new long[2];
        jeu.getJoueurs().add(combinaison.j1().getDeclaredConstructor(int.class, int.class).newInstance(jeu.getCoordonneesJoueurs().getFirst()[0], jeu.getCoordonneesJoueurs().getFirst()[1]));
        jeu.getJoueurs().add(combinaison.j2().getDeclaredConstructor(int.class, int.class).newInstance(jeu.getCoordonneesJoueurs().getLast()[0], jeu.getCoordonneesJoueurs().getLast()[1]));
        jeu.getJoueur(0).setNumJoueur(0);
        jeu.getJoueur(1).setNumJoueur(1);
    }

    public void lancerPartie() {
        threadOccupe = true;
        System.out.println("Lancement de la partie avec les joueurs " + combinaison.j1().getSimpleName() + " et " + combinaison.j2().getSimpleName());
        List<Joueur> joueursIA = donneesJeu.getJoueurs().stream().filter(JoueurIA.class::isInstance).toList();
        thread = new Thread(() -> {
            strategieCollecte.initierTemps();
            Action actionIA;
            while (!strategieCollecte.getConditionArretSatisfaite()) {
                for (Joueur joueur : joueursIA) {
                    long start = System.nanoTime();
                    actionIA = joueur.demanderAction(donneesJeu);
//                    System.out.println(actionIA);
                    long end = System.nanoTime();
                    tempsCalculs[joueur.getNumJoueur()] += end - start;
                    donneesJeu.getActionsDuTour().ajouterAction(joueur, actionIA);
                }
                while (!donneesJeu.getActionsDuTour().isTourTermine()) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                donneesJeu.getDepot().viderDepot();
                nbTours++;
//                System.out.println(nbTours);
//                System.out.println(strategieCollecte.getConditionArretSatisfaite());
                if (strategieCollecte.getConditionArretSatisfaite()) {
                    System.out.println(donneesJeu.getDepot().getPoints() + " points pour " + combinaison);
                    System.out.println(nbTours + " tours pour " + combinaison);

                    Set<Integer> tours = mapTours.get(combinaison);
                    tours.add(nbTours);
                    mapTours.replace(combinaison, tours);
                    Set<Integer> points = mapPoints.get(combinaison);
                    points.add(donneesJeu.getDepot().getPoints());
                    mapPoints.replace(combinaison, points);
                    Thread.currentThread().interrupt();
                    threadOccupe = false;
                }
            }
            threadOccupe = false;
        });
        thread.start();
    }

    public DonneesJeu getDonneesJeu() {
        return donneesJeu;
    }
}
