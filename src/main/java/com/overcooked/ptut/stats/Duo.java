package com.overcooked.ptut.stats;

import com.overcooked.ptut.joueurs.ia.JoueurIA;

public record Duo(Class<? extends JoueurIA> j1, Class<? extends JoueurIA> j2) {
    public Duo {
        if (j1 == null || j2 == null) {
            throw new IllegalArgumentException("Les deux joueurs doivent être non nuls");
        }
    }
}
