package com.overcooked.ptut.recettes.assiette;

import com.overcooked.ptut.recettes.aliment.Aliment;

/**
 * Classe correspondant a une assiette. Une assiette fait partie du plat final dans certains cas.
 */
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
