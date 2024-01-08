package com.overcooked.ptut.recettes;

import com.overcooked.ptut.recettes.aliment.Burger;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.SaladeTomate;

import java.util.ArrayList;
import java.util.List;

public class ListeAliments {
    List<Plat> recettesPossibles;

    public ListeAliments() {
        recettesPossibles = new ArrayList<>();
        recettesPossibles.add(new Burger());
        recettesPossibles.add(new SaladeTomate());
    }

    public List<Plat> getRecettesPossibles() {
        return recettesPossibles;
    }
}
