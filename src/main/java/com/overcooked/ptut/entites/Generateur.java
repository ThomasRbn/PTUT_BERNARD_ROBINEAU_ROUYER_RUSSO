package com.overcooked.ptut.entites;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;

import java.util.List;

public class Generateur extends Bloc {

    private Aliment aliment;


    public Generateur(int x, int y, Aliment aliment) {
        super(x, y);
        this.aliment = aliment;
    }

    public Generateur(Generateur generateur) {
        super(generateur);
        this.aliment = generateur.aliment;
    }

    public Aliment getAliment() {
        return aliment;
    }
    
}
