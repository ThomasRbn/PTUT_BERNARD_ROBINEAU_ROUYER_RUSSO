package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Generateur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.*;

import java.util.List;

import static com.overcooked.ptut.constructionCarte.CaracteresCarte.*;

public class Creation {

    public static Aliment getCurrentPlatBut(List<String> recette) {
        Aliment currAliment = new Aliment();
        for (String s : recette) {
            switch (s) {
                case "S" -> currAliment = new Salade();
                case "T" -> currAliment = new Tomate();
                case "B" -> currAliment = new Pain();
                case "V" -> currAliment = new Viande();
                case "1" -> currAliment.cuire();
                case "2" -> currAliment.decouper();
                case "3" -> {
                    currAliment.cuire();
                    currAliment.decouper();
                }
                default -> throw new IllegalStateException("Unexpected value: " + s);
            }
        }
        return currAliment;
    }

    public static Bloc CreationBloc(char c, int indexColonne, int indexLigne) {
        return switch (c) {
            case GENERATEURSALADE -> new Generateur(indexLigne, indexColonne, new Plat(new Salade()), "Salade");
            case GENERATEURTOMATE -> new Generateur(indexLigne, indexColonne, new Plat(new Tomate()), "Tomate");
            case GENERATEURPAINBURGER -> new Generateur(indexLigne, indexColonne, new Plat(new Pain()), "Pain");
            case PLAN_DE_TRAVAIL -> new PlanDeTravail(indexLigne, indexColonne);
            case PLANCHE -> new Planche(indexLigne, indexColonne);
            case POELE -> new Poele(indexLigne, indexColonne);
            // Exception si le caractère lu est inconnu
            default -> throw new IllegalArgumentException("DonneesJeu.constructeur, caractère inconnu : " + c);
        };
    }
}
