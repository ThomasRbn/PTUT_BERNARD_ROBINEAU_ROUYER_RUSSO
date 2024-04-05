package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.autonome.JoueurAutoN0;
import com.overcooked.ptut.joueurs.autonome.JoueurAutoN4;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.ia.JoueurIADecentr;
import com.overcooked.ptut.joueurs.ia.JoueurIADecentrV2;
import com.overcooked.ptut.stats.DonneesStats;
import com.overcooked.ptut.stats.Duo;
import com.overcooked.ptut.stats.strategieCollecte.CollecteSelonTemps;
import com.overcooked.ptut.stats.strategieCollecte.StrategieCollecte;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.overcooked.ptut.stats.export.ExportCSV.exportCSV;

public class MainCollecteStats {

    public static final int DUREE_PARTIE = 10;

    public static Map<Duo, Integer> mapTours = new HashMap<>();
    public static Map<Duo, Integer> mapPoints = new HashMap<>();

    public static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<String> fichiers = List.of("niveaux/stats/niveau0.txt", "niveaux/stats/niveau4.txt");
        List<StrategieCollecte> strategieCollectes = new ArrayList<>() {{
            add(new CollecteSelonTemps());
        }};

        for (String nomFichier : fichiers) {
            for (StrategieCollecte strat : strategieCollectes) {
                List<Class<? extends JoueurIA>> joueursIA = new ArrayList<>() {{
                    add(JoueurIA.class);
                    add(JoueurIADecentr.class);
                    add(JoueurIADecentrV2.class);
                    add(getAutomate(nomFichier));
                }};

                for (Class<? extends JoueurIA> joueurIA : joueursIA) {
                    for (Class<? extends JoueurIA> joueurIA2 : joueursIA) {
                        Duo combinaison = new Duo(joueurIA, joueurIA2);
                        mapTours.put(combinaison, 0);
                        mapPoints.put(combinaison, 0);
                        DonneesJeu donneesJeu = new DonneesJeu(nomFichier);
                        DonneesStats donneesStats = new DonneesStats(donneesJeu, strat, combinaison);
                        donneesStats.lancerPartie();
                    }
                }
                while (threads.stream().anyMatch(Thread::isAlive)) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

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
