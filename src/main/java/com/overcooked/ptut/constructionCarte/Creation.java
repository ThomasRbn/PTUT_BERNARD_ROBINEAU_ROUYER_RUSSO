package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
import com.overcooked.ptut.entites.PlanDeTravail;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.Pain;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Salade;
import com.overcooked.ptut.recettes.aliment.Tomate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.overcooked.ptut.constructionCarte.CaracteresCarte.*;

public class Creation {
    static Joueur CreationJoueur(int indexLigne, int indexColonne){
        // Création du joueur
        return demanderTypeJoueur().equalsIgnoreCase("H")?
            new JoueurHumain(indexLigne, indexColonne)
            : new JoueurIA(indexLigne, indexColonne);
    }

    private static String demanderTypeJoueur() {
        // Demande le type de joueur
        String choix = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le type de joueur (H, IA)");
        boolean estConforme = false;
        while (!estConforme) {
            choix = sc.nextLine();
            if (choix.equalsIgnoreCase("H") || choix.equalsIgnoreCase("IA")) {
                estConforme = true;
            } else {
                System.out.println("Entrée invalide : " + choix + " (H, IA)");
            }
        }
        return choix;
    }

    public static Bloc CreationBloc(char c, int indexColonne, int indexLigne) {
        return switch (c) {
            case GENERATEURSALADE -> new Generateur(indexLigne, indexColonne, new Plat(new Salade()), "Salade");
            case GENERATEURTOMATE -> new Generateur(indexLigne, indexColonne, new Plat(new Tomate()), "Tomate");
            case GENERATEURPAINBURGER -> new Generateur(indexLigne, indexColonne, new Plat(new Pain()), "Pain");
            case PLAN_DE_TRAVAIL -> new PlanDeTravail(indexLigne, indexColonne);
            case PLANCHE -> new Planche(indexLigne, indexColonne);
            case POELE ->  new Poele(indexLigne, indexColonne);
            // Exception si le caractère lu est inconnu
            default -> throw new IllegalArgumentException("DonneesJeu.constructeur, caractère inconnu : " + c);
        };
    }
}
