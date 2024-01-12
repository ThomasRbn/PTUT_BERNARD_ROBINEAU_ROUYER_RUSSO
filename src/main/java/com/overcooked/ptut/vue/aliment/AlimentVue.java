package com.overcooked.ptut.vue.aliment;

import javafx.scene.shape.Circle;

public class AlimentVue extends Circle {
    public AlimentVue(double tailleCellule) {
        super(tailleCellule / 10 * 3);
    }
}
