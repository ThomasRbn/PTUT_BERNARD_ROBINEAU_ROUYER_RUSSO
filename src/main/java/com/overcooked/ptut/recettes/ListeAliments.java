package com.overcooked.ptut.recettes;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Burger;
import com.overcooked.ptut.recettes.aliment.Recette;

import java.util.ArrayList;
import java.util.List;

public class ListeAliments {
    List<Recette> recettesPossibles;

    public ListeAliments() {
        recettesPossibles = new ArrayList<>();
        recettesPossibles.add(new Burger());
    }

    public List<Recette> getRecettesPossibles() {
        return recettesPossibles;
    }
}
