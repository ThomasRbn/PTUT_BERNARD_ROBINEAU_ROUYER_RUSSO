package com.overcooked.ptut.recettes.aliment;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite representant un plat, avec un nom et une liste de recettes qui le composent
 */
public class Plat extends Aliment {
    // Liste des recettes qui composent la recette en question
    List<Aliment> recettesComposees;

    public Plat() {
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
    }

    public Plat(Plat plat){
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.addAll(plat.getRecettesComposees());
    }

    public Plat(Plat plat1, Plat plat2){
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.addAll(plat1.getRecettesComposees());
        this.recettesComposees.addAll(plat2.getRecettesComposees());
    }

    public Plat(Aliment aliment1, Aliment aliment2) {
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.add(aliment1.cloneAlim());
        this.recettesComposees.add(aliment2.cloneAlim());
    }

    public Plat(Aliment aliment){
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.add(aliment.cloneAlim());
    }

    public boolean equals(Plat plat) {
        if (this == plat) return true;
        if (plat == null || getClass() != plat.getClass()) return false;
        List<Aliment> currAliments = new ArrayList<>(recettesComposees);
        for (Aliment aliment : plat.recettesComposees) {
            int currIndex = currAliments.indexOf(aliment);
            if (currIndex != -1) {
                currAliments.remove(currIndex);
            } else {
                return false;
            }
        }
        return currAliments.isEmpty();
    }

    public void ajouterAliment(Aliment aliment) {
        recettesComposees.add(aliment);
    }

    public void fusionerPlat(Plat plat){
        for (Aliment aliment : plat.recettesComposees){
            recettesComposees.add(aliment);
        }
    }

    public boolean estFusionnable(Plat plat){
        //On vérifie qu'il n'y a aucun aliment en commun dans chaque plat
        for (Aliment aliment : plat.recettesComposees){
            if (recettesComposees.contains(aliment)){
                return false;
            }
        }
        return true;
    }

    public List<Aliment> getRecettesComposees() {
        List<Aliment> recettesComposees = new ArrayList<>();
        for (Aliment aliment : this.recettesComposees) {
            recettesComposees.add(aliment.cloneAlim());
        }
        return recettesComposees;
    }

    public boolean retirerAliment(Aliment aliment) {
        return recettesComposees.remove(aliment);
    }

    public void viderAliments(){
        recettesComposees.clear();
    }

    @Override
    public String toString() {
        return "Plat{" +
                "recettesComposees=" + recettesComposees +
                '}';
    }
}
