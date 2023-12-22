package com.overcooked.ptut.objet;

public abstract class Movable {
    int[][] coordonnees;

    public Movable(int[][] coordonnees){
        this.coordonnees = coordonnees;
    }

    public Movable(){
        this.coordonnees = new int[10][10];
    }

    public int[][] getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(int[][] coordonnees) {
        this.coordonnees = coordonnees;
    }
}
