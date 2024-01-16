package com.overcooked.ptut.vue;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.etat.Etat;
import com.overcooked.ptut.vue.aliment.AlimentVue;
import com.overcooked.ptut.vue.aliment.PainVue;
import com.overcooked.ptut.vue.aliment.SaladeVue;
import com.overcooked.ptut.vue.aliment.TomateVue;
import javafx.scene.shape.Circle;

public class AfficheurCercle {

    public static Circle afficherEtatCercle(Aliment currAliment, double tailleCellule) {
        return switch (currAliment.getNom()) {
            case "Salade" -> {
                if (currAliment.getEtat() == Etat.COUPE) {
                    System.out.println("salt");
                    yield new SaladeVue(tailleCellule).couper();
                } else if (currAliment.getEtat() == Etat.CUIT) {
                    yield new SaladeVue(tailleCellule).cuire();
                } else if (currAliment.getEtat() == Etat.CUIT_ET_COUPE) {
                    yield new SaladeVue(tailleCellule).cuireEtCouper();
                }
                yield new SaladeVue(tailleCellule);
            }
            case "Tomate" -> {
                if (currAliment.getEtat() == Etat.COUPE) {
                    yield new TomateVue(tailleCellule).couper();
                } else if (currAliment.getEtat() == Etat.CUIT) {
                    yield new TomateVue(tailleCellule).cuire();
                }
                yield new TomateVue(tailleCellule);
            }
            case "Pain" -> {
                if (currAliment.getEtat() == Etat.COUPE) {
                    yield new PainVue(tailleCellule).couper();
                } else if (currAliment.getEtat() == Etat.CUIT) {
                    yield new PainVue(tailleCellule).cuire();
                } else if (currAliment.getEtat() == Etat.CUIT_ET_COUPE) {
                    yield new PainVue(tailleCellule).cuireEtCouper();
                }
                yield new PainVue(tailleCellule);
            }
            case null, default -> new AlimentVue(tailleCellule);
        };
    }

}
