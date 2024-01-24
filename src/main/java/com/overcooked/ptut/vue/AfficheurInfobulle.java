package com.overcooked.ptut.vue;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.etat.Etat;
import com.overcooked.ptut.vue.aliment.AlimentVue;
import com.overcooked.ptut.vue.aliment.PainVue;
import com.overcooked.ptut.vue.aliment.SaladeVue;
import com.overcooked.ptut.vue.aliment.TomateVue;

public class AfficheurInfobulle {

    public static AlimentVue afficherEtatCercle(Aliment currAliment, double tailleCellule, int i) {
        tailleCellule = i == 1 ? tailleCellule : tailleCellule / (i + 1) * 2;
        return switchAlimentVue(currAliment, (int) tailleCellule);
    }

    public static AlimentVue afficherEtatCercle(Aliment currAliment, int tailleCercle) {
        AlimentVue alimentVue = switchAlimentVue(currAliment, tailleCercle);
        alimentVue.setRadius(tailleCercle);
        return alimentVue;
    }

    public static AlimentVue switchAlimentVue(Aliment currAliment, int taille) {
        return switch (currAliment.getNom()) {
            case "Salade" -> {
                if (currAliment.getEtat() == Etat.COUPE) {
                    yield new SaladeVue(taille).couper();
                } else if (currAliment.getEtat() == Etat.CUIT) {
                    yield new SaladeVue(taille).cuire();
                } else if (currAliment.getEtat() == Etat.CUIT_ET_COUPE) {
                    yield new SaladeVue(taille).cuireEtCouper();
                }
                yield new SaladeVue(taille);
            }
            case "Tomate" -> {
                if (currAliment.getEtat() == Etat.COUPE) {
                    yield new TomateVue(taille).couper();
                } else if (currAliment.getEtat() == Etat.CUIT) {
                    yield new TomateVue(taille).cuire();
                } else if (currAliment.getEtat() == Etat.CUIT_ET_COUPE) {
                    yield new TomateVue(taille).cuireEtCouper();
                }
                yield new TomateVue(taille);
            }
            case "Pain" -> {
                if (currAliment.getEtat() == Etat.COUPE) {
                    yield new PainVue(taille).couper();
                } else if (currAliment.getEtat() == Etat.CUIT) {
                    yield new PainVue(taille).cuire();
                } else if (currAliment.getEtat() == Etat.CUIT_ET_COUPE) {
                    yield new PainVue(taille).cuireEtCouper();
                }
                yield new PainVue(taille);
            }
            case null, default -> new AlimentVue(taille);
        };
    }
}
