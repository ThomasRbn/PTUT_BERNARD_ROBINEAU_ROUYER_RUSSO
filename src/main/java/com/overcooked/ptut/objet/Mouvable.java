package com.overcooked.ptut.objet;

public class Mouvable {
    int[] coordonnees;

    public Mouvable(int[] coordonnees){
        this.coordonnees = coordonnees;
    }

    public Mouvable(Mouvable m){
        this.coordonnees = m.coordonnees;
    }

    public Mouvable(){
        this.coordonnees = new int[2];
    }

    public int[] getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(int[] coordonnees) {
        this.coordonnees = coordonnees;
    }
}
