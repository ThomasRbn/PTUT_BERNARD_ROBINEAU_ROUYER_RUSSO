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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainCollecteStats {

    public static final int DUREE_PARTIE = 10;

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<String> fichiers = List.of("niveaux/stats/niveau0.txt");

        for (String nomFichier : fichiers) {
            System.out.println(nomFichier.split("\\.")[0].charAt(nomFichier.split("\\.")[0].length() - 1));
            List<Class<? extends JoueurIA>> joueursIA = new ArrayList<>(){{
                add(JoueurIA.class);
                add(JoueurIADecentr.class);
                add(JoueurIADecentrV2.class);
                add(getAutomate(nomFichier));
            }};

            List<Duo> combinaisonsIA = new ArrayList<>();

            for (Class<? extends JoueurIA> joueurIA : joueursIA) {
                for (Class<? extends JoueurIA> joueurIA2 : joueursIA) {
                    combinaisonsIA.add(new Duo(joueurIA, joueurIA2));
                }
            }
            Duo combinaison = new Duo(JoueurIADecentrV2.class, JoueurIADecentrV2.class);
//            Duo combinaison = new Duo(JoueurAutoN0.class, JoueurAutoN0.class);
            DonneesJeu donneesJeu = new DonneesJeu(nomFichier);
            DonneesStats donneesStats = new DonneesStats(donneesJeu, new CollecteSelonTemps(), combinaison);
            donneesStats.lancerPartie();
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
