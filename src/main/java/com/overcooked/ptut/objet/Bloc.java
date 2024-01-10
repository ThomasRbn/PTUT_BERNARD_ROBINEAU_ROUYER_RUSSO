package com.overcooked.ptut.objet;

public class Bloc {
    private int x;
    private int y;

    public Bloc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Bloc(Bloc bloc) {
        this.x = bloc.x;
        this.y = bloc.y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
