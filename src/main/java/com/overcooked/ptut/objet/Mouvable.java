package com.overcooked.ptut.objet;

public abstract class Mouvable implements Cloneable {
    int[] coordonnees;

    public Mouvable(int[] coordonnees){
        this.coordonnees = coordonnees;
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

    @Override
    public Mouvable clone() {
        try {
            return (Mouvable) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
