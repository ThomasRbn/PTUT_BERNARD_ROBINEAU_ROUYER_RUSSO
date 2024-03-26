package com.overcooked.ptut.stats;

import com.overcooked.ptut.joueurs.ia.JoueurIA;

import java.util.Objects;

public record Duo(Class<? extends JoueurIA> j1, Class<? extends JoueurIA> j2) {
    public Duo {
        if (j1 == null || j2 == null) {
            throw new IllegalArgumentException("Les deux joueurs doivent être non nuls");
        }
    }

    @Override
    public String toString() {
        return j1().getSimpleName() + " vs " + j2().getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duo duo = (Duo) o;
        return Objects.equals(j1, duo.j1) && Objects.equals(j2, duo.j2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(j1, j2);
    }
}
