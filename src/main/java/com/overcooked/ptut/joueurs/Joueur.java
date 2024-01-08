package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Movable;

public abstract class Joueur {

    /**
     * Position du joueur sur la carte
     */
    protected int[] position;

    protected Movable inventaire;

    public Joueur(int x, int y) {
        position = new int[]{x, y};
    }

    protected void prendre(Movable objet) {
        inventaire = objet;
    }

    protected Movable deposer() {
        Movable objet = inventaire;
        inventaire = null;
        return objet;
    }

    public abstract Action demanderAction();
}
