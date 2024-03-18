package com.overcooked.ptut.stats.strategieCollecte;

public class CollecteSelonTemps extends StrategieCollecte {


    @Override
    public boolean getConditionArretSatisfaite() {
        return this.collecteTerminee;
    }
}
