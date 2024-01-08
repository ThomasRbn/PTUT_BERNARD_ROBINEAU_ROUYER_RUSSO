package com.overcooked.ptut.joueurs.ia;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;

public class JoueurIA extends Joueur {

    public JoueurIA(int x, int y) {
        super(x, y);
    }

    // Utilisera Problème
    @Override
    public Action demanderAction() {
        return null;
    }
}
