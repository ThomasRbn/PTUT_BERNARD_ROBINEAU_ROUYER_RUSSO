package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;

/**
 * Lance un algorithme de recherche  
 * sur un problème donné et affiche le résultat
 */
public class MainTestv2 {

    public static void main(String[] args){

        DonneesJeu donneesJeu = new DonneesJeu("niveaux/niveau4.txt");
        while (donneesJeu.getPlatDepose().isEmpty()) {
            Joueur j = donneesJeu.getJoueur(0);
            Action action = j.demanderAction(donneesJeu);
            System.out.println("end");
            System.out.println(action);
            faireAction(action, 0, donneesJeu);
            System.out.println(donneesJeu);
        }


    }
}
