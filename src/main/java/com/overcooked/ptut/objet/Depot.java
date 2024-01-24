package com.overcooked.ptut.objet;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class Depot extends Bloc {

    private List<Plat> platsButs;
    private List<Plat> platsDeposes;
    private int points;

    /**
     * Constructeur de la classe Depot
     * @param x
     * @param y
     */
    public Depot(int x, int y, List<Plat> platsButs) {
        super(x, y);
        type = "Depot";
        platsDeposes = new ArrayList<>();
        points = 0;
        this.platsButs = platsButs;
    }

    @Override
    public Plat getInventaire() {
        return null;
    }

    /**
     * Constructeur par copie de la classe Depot
     * @param depot
     */
    public Depot(Depot depot) {
        super(depot);
        this.type = depot.type;
        this.platsDeposes = new ArrayList<>(depot.platsDeposes);
        this.points = depot.points;
        this.platsButs = new ArrayList<>(depot.platsButs);
    }

    /**
     * Méthode permettant de déposer un plat dans le dépôt
     * @param plat
     */
    public void deposerPlat(Plat plat) {
        platsDeposes.add(plat);
        if (platsButs.contains(plat)) {
            for (Aliment aliment : plat.getRecettesComposees()) {
                this.points += aliment.getEtat() + 1;
            }
            System.out.println("Points : " + points);
        }
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
