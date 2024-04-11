package com.overcooked.ptut.stats.strategieCollecte;

import java.util.Timer;
import java.util.TimerTask;

import static com.overcooked.ptut.MainCollecteStats.DUREE_PARTIE;

public abstract class StrategieCollecte {

    protected boolean collecteTerminee;

    public abstract boolean getConditionArretSatisfaite();

    public void initierTemps() {
        this.collecteTerminee = false;
        Timer timer = new Timer();
        long delai = DUREE_PARTIE * 1000L; // Changer ici le temps de la partie
        long delaiMAJ = 1000;

        TimerTask timerTask = new TimerTask() {
            int remainingTime = (int) (delai / 1000);

            @Override
            public void run() {
                if (remainingTime >= 0) {
                    remainingTime--;
                    System.out.print(remainingTime + 1 + "s \r");
                } else {
                    collecteTerminee = true;
//                    threadOccupe = false;
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, delaiMAJ);
    }
}
