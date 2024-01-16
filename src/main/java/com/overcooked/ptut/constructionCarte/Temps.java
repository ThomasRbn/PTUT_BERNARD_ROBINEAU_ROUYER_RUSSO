package com.overcooked.ptut.constructionCarte;

import java.util.List;
import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;

public class Temps {

    List<Chronometre> timers;

    public Temps() {
        this.timers = new ArrayList<>();
    }

    public void addTimer(Chronometre timer) {
        this.timers.add(timer);
    }
}
