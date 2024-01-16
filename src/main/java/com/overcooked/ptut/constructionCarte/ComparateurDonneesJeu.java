package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.recettes.aliment.Plat;

public class ComparateurDonneesJeu {
    /**
     * Retourne vrai si deux jeux de données sont strictement identiques
     *
     * @param donneesJeu1
     * @param donneesJeu2
     * @return
     */
    public static boolean ComparerDonneesJeu(DonneesJeu donneesJeu1, DonneesJeu donneesJeu2){
        //Lève une exception si le jeu de données est null
        if (donneesJeu1 == null || donneesJeu2 == null) {
            throw new IllegalArgumentException("DonneesJeu.equals, donneesJeu est null");
        }

        //Retourne vrai si les deux jeux de données sont la même instance
        if (donneesJeu1 == donneesJeu2) {
            return true;
        }

        // Lève une exception si les deux jeux de données n'ont pas la même classe
        if (donneesJeu1.getClass() != donneesJeu2.getClass()) {
            throw new IllegalArgumentException("DonneesJeu.equals, donneesJeu n'est pas de la même classe");
        }

        Plat[][] objetsDeplacables1 = donneesJeu1.getObjetsDeplacables();
        Plat[][] objetsDeplacables2 = donneesJeu2.getObjetsDeplacables();
        //On parcours l'ensemble des objets deplacables
        for (int i = 0; i < objetsDeplacables1.length; i++) {
            for (int j = 0; j < objetsDeplacables1[i].length; j++) {
                if (objetsDeplacables1[i][j] == null) {
                    //Si l'objet est null, on vérifie que l'objet deplacable de l'autre jeu de données est null
                    //Si ce n'est pas le cas, on retourne faux
                    if (objetsDeplacables2[i][j] != null) {
                        return false;
                    }

                    //On verifie que les deux objets deplacables sont identiques
                } else if (!objetsDeplacables1[i][j].equals(objetsDeplacables2[i][j])) {
                    return false;
                }
            }
        }

        //On parcours l'ensemble des joueurs
        for (Joueur joueur : donneesJeu1.getJoueurs()) {
            int numJoueur = joueur.getNumJoueur();
            //Si joueur différent, on retourne faux
            if (!joueur.equals(donneesJeu2.getJoueur(numJoueur))) {
                return false;
            }
        }
        return donneesJeu1.getPlatDepose().size() == donneesJeu2.getPlatDepose().size()
                && (donneesJeu1.getPlatDepose().isEmpty()
                || donneesJeu1.getPlatDepose().getFirst().equals(donneesJeu2.getPlatDepose().getFirst()));

    }
}
