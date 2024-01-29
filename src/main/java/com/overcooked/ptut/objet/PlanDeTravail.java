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
        type = "PlanDeTravail";
    }

    /**
     * Constructeur par copie de la classe PlanDeTravail
     * @param planDeTravail
     */
    public PlanDeTravail(PlanDeTravail planDeTravail) {
        super(planDeTravail);
        this.type = planDeTravail.type;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof PlanDeTravail) {
            PlanDeTravail planDeTravail = (PlanDeTravail) o;
            return planDeTravail.getX() == this.getX() && planDeTravail.getY() == this.getY() && planDeTravail.inventaire == this.inventaire;
        }
        return false;
    }

    public String getNomPlat() {
        return inventaire != null ? inventaire.getNomPlat() : "";
    }

    /**
     * Retourne le plat présent sur le plan de travail
     * @return
     */
    public Plat getInventaire() {
        return inventaire;
    }
}
