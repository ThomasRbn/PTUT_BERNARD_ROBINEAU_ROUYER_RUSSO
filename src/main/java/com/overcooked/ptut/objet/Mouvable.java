package com.overcooked.ptut.objet;

public abstract class Mouvable {
    int[][] coordonnees;

    public Mouvable(int[][] coordonnees){
        this.coordonnees = coordonnees;
    }

    public Mouvable(){
        this.coordonnees = new int[10][10];
    }

    public int[][] getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(int[][] coordonnees) {
        this.coordonnees = coordonnees;
    }
}
