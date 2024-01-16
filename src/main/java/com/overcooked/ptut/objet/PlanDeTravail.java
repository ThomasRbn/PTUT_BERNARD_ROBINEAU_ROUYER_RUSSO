package com.overcooked.ptut.objet;

import com.overcooked.ptut.recettes.aliment.Plat;

public class PlanDeTravail extends Bloc {

    Plat inventaire;

    /**
     * Constructeur de la classe PlanDeTravail
     * @param x
     * @param y
     */
    public PlanDeTravail(int x, int y) {
        super(x, y);
    }

    /**
     * Constructeur par copie de la classe PlanDeTravail
     * @param planDeTravail
     */
    public PlanDeTravail(PlanDeTravail planDeTravail) {
        super(planDeTravail);
        if(planDeTravail.inventaire!= null) {
            this.inventaire = new Plat(planDeTravail.inventaire);
        }
    }

    /**
     * Méthode permettant de poser un plat sur le plan de travail
     * @param plat
     */
    public void poserDessus(Plat plat) {
        inventaire = plat;
    }

    /**
     * Méthode permettant de prendre un plat sur le plan de travail
     * @return
     */
    public Plat prendre() {
        Plat plat = inventaire;
        inventaire = null;
        return plat;
    }

    /**
     * Retourne le plat présent sur le plan de travail
     * @return
     */
    public Plat getInventaire() {
        return inventaire;
    }
}
