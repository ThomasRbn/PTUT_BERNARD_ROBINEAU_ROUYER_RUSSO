package com.overcooked.ptut.entites;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Mouvable;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class Depot extends Bloc {

    private List<Plat> platsDeposes;

    /**
     * Constructeur de la classe Depot
     * @param x
     * @param y
     */
    public Depot(int x, int y) {
        super(x, y);
        platsDeposes = new ArrayList<>();
    }

    /**
     * Constructeur par copie de la classe Depot
     * @param depot
     */
    public Depot(Depot depot) {
        super(depot);
        this.platsDeposes = new ArrayList<>(depot.platsDeposes);
    }

    /**
     * Méthode permettant de déposer un plat dans le dépôt
     * @param plat
     */
    public void deposerPlat(Plat plat) {
        platsDeposes.add(plat);
    }

    /**
     * Retourne les plats présent dans le dépôt
     * @return
     */
    public List<Plat> getPlatsDeposes() {
        return platsDeposes;
    }

    /**
     * Vide le dépôt
     */
    public void viderDepot() {
        platsDeposes.clear();
    }

}
