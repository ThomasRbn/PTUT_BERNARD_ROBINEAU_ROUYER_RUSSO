package com.overcooked.ptut.entites;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.List;

public class Generateur extends Bloc {

    private Plat aliment;


    /**
     * Constructeur de la classe Generateur
     * @param x
     * @param y
     * @param aliment
     */
    public Generateur(int x, int y, Plat aliment) {
        super(x, y);
        this.aliment = aliment;
    }

    /**
     * Constructeur par copie de la classe Generateur
     * @param generateur
     */
    public Generateur(Generateur generateur) {
        super(generateur);
        this.aliment = generateur.aliment;
    }

    /**
     * Retourne l'aliment généré par le générateur
     * @return
     */
    public Plat getAliment() {
        return aliment;
    }
    
}
