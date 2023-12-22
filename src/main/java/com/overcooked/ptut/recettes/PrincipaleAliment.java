package com.overcooked.ptut.recettes;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Pain;
import com.overcooked.ptut.recettes.aliment.Viande;
import com.overcooked.ptut.recettes.etat.Cuisson;
import com.overcooked.ptut.recettes.verificateur.VerificationAliment;

import java.util.ArrayList;
import java.util.List;

public class PrincipaleAliment {
    public static void main(String[] args) {
        VerificationAliment vf = new VerificationAliment();
        List<Aliment> alimentsATraiter = new ArrayList<>();
        Aliment viande = new Viande();
        alimentsATraiter.add(viande);
        vf.verifiercompatibilite(alimentsATraiter);
        Aliment pain = new Pain();
        Aliment viandeCuite = new Cuisson(viande);
        alimentsATraiter.remove(viande);
        alimentsATraiter.add(viandeCuite);
        alimentsATraiter.add(pain);
        vf.verifiercompatibilite(alimentsATraiter);
    }
}