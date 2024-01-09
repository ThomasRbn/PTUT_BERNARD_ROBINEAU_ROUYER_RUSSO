package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.Scanner;

public class JoueurHumain extends Joueur {

    public JoueurHumain(int x, int y) {
        super(x, y);
    }

    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez une action (HAUT, BAS, GAUCHE, DROITE, PRENDRE, POSER, COUPER)");
        String choix = "";

        boolean estConforme = false;
        boolean estJouable = false;

        while (!estConforme && !estJouable) {
            choix = sc.nextLine();
            for (Action value : Action.values()) {
                System.out.println(value.getName());
                if (value.getName().equals(choix)) {
                    estConforme = true;
                    break;
                }
            }

            Action action = Action.valueOf(choix);
            System.out.println("Mon action est " + action);
            estJouable = donneesJeu.isLegal(action, this.numJoueur);

            if (!estJouable || !estConforme) {
                System.out.println(!estConforme ? "Pas conforme" : "Conforme");
                System.out.println(!estJouable ? "Pas jouable" : "Jouable");
                System.out.println("Entrée invalide : " + choix + " (HAUT, BAS, GAUCHE, DROITE, PRENDRE, POSER, COUPER)");
            }
        }

        return Action.valueOf(choix);
    }
}
