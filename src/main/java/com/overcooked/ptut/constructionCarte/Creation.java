package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.ia.JoueurIADecentr;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Generateur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.*;

import java.util.List;
import java.util.Scanner;

import static com.overcooked.ptut.constructionCarte.CaracteresCarte.*;

public class Creation {
    static Joueur CreationJoueur(int indexLigne, int indexColonne) {
        // Création du joueur
        return switch (demanderTypeJoueur().toUpperCase()){
            case "H" -> new JoueurHumain(indexLigne, indexColonne);
            case "IA" -> new JoueurIA(indexLigne, indexColonne);
            case "IAD" -> new JoueurIADecentr(indexLigne, indexColonne);
            default -> throw new IllegalStateException("Unexpected value: " + demanderTypeJoueur());
        };
    }

    private static String demanderTypeJoueur() {
        // Demande le type de joueur
        String choix = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le type de joueur (H, IA, IAD)");
        boolean estConforme = false;
        while (!estConforme) {
            choix = sc.nextLine();
            if (choix.equalsIgnoreCase("H") || choix.equalsIgnoreCase("IA") || choix.equalsIgnoreCase("IAD")) {
                estConforme = true;
            } else {
                System.out.println("Entrée invalide : " + choix + " (H, IA, IAD)");
            }
        }
        return choix;
    }

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
