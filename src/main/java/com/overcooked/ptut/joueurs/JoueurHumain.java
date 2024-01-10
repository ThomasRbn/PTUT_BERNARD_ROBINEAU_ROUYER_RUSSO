package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;

public class JoueurHumain extends Joueur {

    public JoueurHumain(int x, int y) {
        super(x, y);
    }

    public JoueurHumain(int x, int y, Plat inventaire, Action direction, int numJoueur) {
        super(x, y, inventaire, direction, numJoueur);
    }

    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {
        return null;
    }
}
