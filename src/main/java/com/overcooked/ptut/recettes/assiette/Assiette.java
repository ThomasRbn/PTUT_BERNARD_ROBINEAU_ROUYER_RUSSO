package com.overcooked.ptut.recettes.assiette;

import com.overcooked.ptut.recettes.aliment.Aliment;

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
