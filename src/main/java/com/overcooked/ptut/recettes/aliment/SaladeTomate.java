package com.overcooked.ptut.recettes.aliment;

import com.overcooked.ptut.recettes.etat.Coupe;

public class SaladeTomate extends Recette {

    public SaladeTomate(){
        super();
        setNom("Salade de tomate");
        setDescription("Salade de tomate");
        recettesComposees.add(new Coupe(new Salade()));
        recettesComposees.add(new Coupe(new Tomate()));
    }
}
