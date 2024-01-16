package com.overcooked.ptut.vue.aliment;

import com.overcooked.ptut.objet.transformateur.PatternTransformateur;
import javafx.scene.shape.Circle;

public class AlimentVue extends Circle {
    public AlimentVue(double tailleCellule) {
        super(tailleCellule / 10 * 3);
    }

    public AlimentVue couper() {
        return PatternTransformateur.afficherCoupe(this);
    }

    public AlimentVue cuire() {
        return PatternTransformateur.afficherCuisson(this);
    }

    public AlimentVue cuireEtCouper() {
        return PatternTransformateur.afficherCoupe(PatternTransformateur.afficherCuisson(this));
    }
}
