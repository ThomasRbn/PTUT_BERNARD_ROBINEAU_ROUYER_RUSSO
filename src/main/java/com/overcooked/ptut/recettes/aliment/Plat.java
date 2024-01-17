package com.overcooked.ptut.recettes.aliment;

import java.util.*;

/**
 * Classe abstraite representant un plat, avec un nom et une liste de recettes qui le composent
 */
public class Plat extends Aliment {
    // Liste des recettes qui composent la recette en question
    Map<String, Aliment> recettesComposees;

    public Plat() {
        super();
        this.nom = "";
        this.recettesComposees = new TreeMap<>();
    }

    public String getNomPlat() {
        String nomPlat = "";
        for (String aliment : recettesComposees.keySet()) {
            nomPlat += aliment;
        }
        return nomPlat;
    }

    public Plat(Plat plat){
        super();
        this.nom = plat.nom;
        this.recettesComposees = new TreeMap<>(plat.getRecettesComposeesMap());
    }

    public Plat(Plat plat1, Plat plat2){
        super();
        this.recettesComposees = new TreeMap<>();
        this.recettesComposees.putAll(plat1.getRecettesComposeesMap());
        this.recettesComposees.putAll(plat2.getRecettesComposeesMap());
        this.nom = getNomPlat();
    }

    public Plat(Aliment aliment1, Aliment aliment2) {
        super();
        this.recettesComposees = new TreeMap<>();
        this.recettesComposees.put(aliment1.getNom(), aliment1.cloneAlim());
        this.recettesComposees.put(aliment2.getNom(), aliment2.cloneAlim());
        this.nom = getNomPlat();
    }

    public Plat(Aliment aliment){
        super();
        this.nom = "Plat";
        this.recettesComposees = new TreeMap<>();
        this.recettesComposees.put(aliment.getNom(), aliment.cloneAlim());
    }

    public void ajouterAliment(Aliment aliment) {
        String nomAlim = aliment.getSimplifiedNom();
        recettesComposees.put(nomAlim, aliment);
        this.nom = getNomPlat();
    }

    public void fusionerPlat(Plat plat){
        this.recettesComposees.putAll(plat.recettesComposees);
        this.nom = getNomPlat();
    }

    public boolean estFusionnable(Plat plat){
        //On vérifie qu'il n'y a aucun aliment en commun dans chaque plat
        for (Aliment aliment : plat.recettesComposees.values()){
            if (recettesComposees.containsValue(aliment)){
                return false;
            }
        }
        return true;
    }

    public List<Aliment> getRecettesComposees() {
        List<Aliment> recettesComposees = new ArrayList<>();
        for (Aliment aliment : this.recettesComposees.values()) {
            recettesComposees.add(aliment.cloneAlim());
        }
        return recettesComposees;
    }

    public Map<String, Aliment> getRecettesComposeesMap() {
        Map<String, Aliment> recettesComposees = new TreeMap<>();
        for (Aliment aliment : this.recettesComposees.values()) {
            recettesComposees.put(aliment.getNom(), aliment.cloneAlim());
        }
        return recettesComposees;
    }

    public void viderAliments(){
        recettesComposees.clear();
        this.nom = getNomPlat();
    }

    @Override
    public String toString() {
        return getNomPlat();
    }

    public boolean equals(Object o) {
        Plat plat = (Plat) o;
        System.out.println(plat);
        System.out.println(this);
        if (this == plat) return true;
        if (plat == null || getClass() != plat.getClass()) return false;
        List<Aliment> currAliments = new ArrayList<>(recettesComposees.values());
        for (Aliment aliment : plat.recettesComposees.values()) {
            int currIndex = currAliments.indexOf(aliment);
            if (currIndex != -1) {
                currAliments.remove(currIndex);
            } else {
                return false;
            }
        }
        return currAliments.isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), recettesComposees);
    }
}
