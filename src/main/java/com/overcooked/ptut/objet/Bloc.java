package com.overcooked.ptut.objet;

import com.overcooked.ptut.recettes.aliment.Plat;

public abstract class Bloc {
    private int x;
    private int y;

    protected String type;

    public Bloc(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = "Bloc";
    }

    public Bloc(Bloc bloc) {
        this.x = bloc.x;
        this.y = bloc.y;
        this.type = bloc.type;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public String getType() { return type; }

    public abstract Plat getInventaire();
}
