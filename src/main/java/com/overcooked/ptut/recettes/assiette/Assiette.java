package com.overcooked.ptut.recettes.assiette;

import com.overcooked.ptut.objet.Movable;
import com.overcooked.ptut.recettes.aliment.Aliment;

import java.security.AlgorithmConstraints;

public class Assiette extends Aliment {
    private Aliment aliment;

    public Assiette(Aliment aliment){
        this.aliment = aliment;
    }

    public Aliment getAliment() {
        return aliment;
    }

    public void setAliment(Aliment aliment) {
        this.aliment = aliment;
    }
}
