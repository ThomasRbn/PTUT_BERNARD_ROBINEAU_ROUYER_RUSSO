package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.autonome.JoueurAutoN0;
import com.overcooked.ptut.joueurs.autonome.JoueurAutoN4;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.ia.JoueurIADecentr;
import com.overcooked.ptut.joueurs.ia.JoueurIADecentrV2;
import com.overcooked.ptut.joueurs.ia.JoueurIADecentrV3;
import com.overcooked.ptut.stats.DonneesStats;
import com.overcooked.ptut.stats.Duo;
import com.overcooked.ptut.stats.strategieCollecte.CollecteSelonTemps;
import com.overcooked.ptut.stats.strategieCollecte.StrategieCollecte;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.overcooked.ptut.stats.export.ExportCSV.exportCSV;

public class MainCollecteStats {

    public static final int DUREE_PARTIE = 5;

    public static Map<Duo, Set<Integer>> mapTours = new HashMap<>();
    public static Map<Duo, Set<Integer>> mapPoints = new HashMap<>();

    //    public static List<Thread> threads = new ArrayList<>();
    public static boolean threadOccupe = false;


    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<String> fichiers = List.of("niveaux/stats/niveau0.txt", "niveaux/stats/niveau4.txt");
        List<Class<? extends StrategieCollecte>> strategieCollectes = new ArrayList<>() {{
            add(CollecteSelonTemps.class);
        }};

        for (String nomFichier : fichiers) {
            for (Class<? extends StrategieCollecte> strat : strategieCollectes) {
                List<Class<? extends JoueurIA>> joueursIA = new ArrayList<>() {{
                    add(JoueurIA.class);
//                    add(JoueurIADecentr.class);
                    add(JoueurIADecentrV2.class);
                    add(JoueurIADecentrV3.class);
                    add(getAutomate(nomFichier));
                }};

                for (int i = 0; i < 3; i++) {
                    for (Class<? extends JoueurIA> joueurIA : joueursIA) {
                        for (Class<? extends JoueurIA> joueurIA2 : joueursIA) {
                            Duo combinaison = new Duo(joueurIA, joueurIA2);
//                Duo combinaison = new Duo(JoueurIA.class, JoueurIA.class);


                            mapTours.put(combinaison, new HashSet<>());
                            mapPoints.put(combinaison, new HashSet<>());
                            if (combinaison.j1().equals(JoueurIA.class) && combinaison.j2().equals(JoueurIADecentr.class)
                                    || combinaison.j1().equals(JoueurIADecentr.class) && combinaison.j2().equals(JoueurIA.class)
                                    || combinaison.j1().equals(JoueurIA.class) && combinaison.j2().equals(JoueurIADecentrV2.class)
                                    || combinaison.j1().equals(JoueurAutoN0.class) && combinaison.j2().equals(JoueurAutoN0.class)
                                    || combinaison.j1().equals(JoueurAutoN4.class) && combinaison.j2().equals(JoueurAutoN4.class)) {
                                mapTours.get(combinaison).add(-1);
                                mapPoints.get(combinaison).add(-1);
                                continue;
                            }
                            DonneesJeu donneesJeu = new DonneesJeu(nomFichier);
                            DonneesStats donneesStats = new DonneesStats(donneesJeu, strat, combinaison);
                            while (threadOccupe) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            donneesStats.lancerPartie();
                        }
                    }
                    while (threadOccupe) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //affichages des données des maps pour le tour courant
                    mapTours.forEach((duo, nbTours) -> System.out.println(duo + " : " + nbTours + " tours"));
                    mapPoints.forEach((duo, nbPoints) -> System.out.println(duo + " : " + nbPoints + " points"));
                }
                while (threadOccupe) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                while (threads.stream().anyMatch(Thread::isAlive)) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

                //affichage des données des maps
                mapTours.forEach((duo, nbTours) -> System.out.println(duo + " : " + nbTours + " tours"));
                mapPoints.forEach((duo, nbPoints) -> System.out.println(duo + " : " + nbPoints + " points"));
                try {
                    exportCSV(nomFichier, strat, "Tours", mapTours);
                    exportCSV(nomFichier, strat, "Points", mapPoints);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Class<? extends JoueurIA> getAutomate(String nomFichier) {
        return switch (nomFichier.split("\\.")[0].charAt(nomFichier.split("\\.")[0].length() - 1)) {
            case '0' -> JoueurAutoN0.class;
            case '4' -> JoueurAutoN4.class;
            default -> null;
        };
    }
}
