package com.overcooked.ptut.stats.strategieCollecte;

import com.overcooked.ptut.constructionCarte.DonneesJeu;

import java.util.Timer;
import java.util.TimerTask;

import static com.overcooked.ptut.MainCollecteStats.DUREE_PARTIE;

public abstract class StrategieCollecte {

    protected DonneesJeu donneesJeu;
    protected boolean collecteTerminee = false;

    public abstract boolean getConditionArretSatisfaite();

    public void initierTemps() {
        Timer timer = new Timer();
        long delai = DUREE_PARTIE * 1000L; // Changer ici le temps de la partie
        long delaiMAJ = 1000;

        TimerTask timerTask = new TimerTask() {
            int remainingTime = (int) (delai / 1000);

            @Override
            public void run() {
                if (remainingTime >= 0) {
                    remainingTime--;
//                    System.out.println("Temps restant : " + remainingTime + " secondes");
                } else {
                    collecteTerminee = true;
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, delaiMAJ);
    }
}
