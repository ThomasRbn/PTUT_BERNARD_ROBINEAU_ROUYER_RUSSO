package com.overcooked.ptut.entites;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Plat;

public class Generateur extends Bloc {

    private Plat aliment;
    private String type;


    /**
     * Constructeur de la classe Generateur
     * @param x
     * @param y
     * @param aliment
     * @param type
     */
    public Generateur(int x, int y, Plat aliment, String type) {
        super(x, y);
        this.aliment = aliment;
        this.type = type;
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
        return new Plat(aliment);
    }

    public String getType() {
        return type;
    }

    @Override
    public Plat getInventaire() {
        return aliment;
    }
}
