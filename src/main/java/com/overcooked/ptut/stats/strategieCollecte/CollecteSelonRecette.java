package com.overcooked.ptut.stats.strategieCollecte;

public class CollecteSelonRecette extends StrategieCollecte {
    @Override
    public boolean getConditionArretSatisfaite() {
        return !donneesJeu.getDepot().getPlatsDeposes().isEmpty();
    }
}
