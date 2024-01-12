package com.overcooked.ptut.joueurs.utilitaire;

import com.overcooked.ptut.recettes.aliment.Aliment;

public class AlimentCoordonnees implements Comparable<AlimentCoordonnees> {
    private Aliment aliment;
    private int[] coordonnees;

    public AlimentCoordonnees(Aliment aliment, int[] coordonnees) {
        this.aliment = aliment;
        this.coordonnees = coordonnees;
    }

    public Aliment getAliment() {
        return aliment;
    }

    public int[] getCoordonnees() {
        return coordonnees;
    }

    @Override
    public int compareTo(AlimentCoordonnees o) {
        return aliment.getNom() == o.getAliment().getNom() && coordonnees[0] == o.getCoordonnees()[0] && coordonnees[1] == o.getCoordonnees()[1] ? 0 : -1;
    }
}
