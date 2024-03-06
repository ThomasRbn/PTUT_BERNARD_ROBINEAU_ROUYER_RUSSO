package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Plat;

public class ComparateurDonneesJeu {
    /**
     * Retourne vrai si deux jeux de données sont strictement identiques
     *
     * @param donneesJeu1 Données du jeu 1
     * @param donneesJeu2 Données du jeu 2
     * @return Vrai si les deux jeux de données sont identiques
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
        //On parcourt l'ensemble des objets déplaçables
        for (int i = 0; i < objetsDeplacables1.length; i++) {
            for (int j = 0; j < objetsDeplacables1[i].length; j++) {
                if (objetsDeplacables1[i][j] == null) {
                    //Si l'objet est null, on vérifie que l'objet deplaçable de l'autre jeu de données est null
                    //Si ce n'est pas le cas, on retourne faux
                    if (objetsDeplacables2[i][j] != null) {
                        return false;
                    }

                    //On vérifie que les deux objets deplaçables sont identiques
                } else if (!objetsDeplacables1[i][j].equals(objetsDeplacables2[i][j])) {
                    return false;
                }
            }
        }

        //On parcourt l'ensemble des joueurs
        for (Joueur joueur : donneesJeu1.getJoueurs()) {
            int numJoueur = joueur.getNumJoueur();
            //Si joueur différent, on retourne faux
            if (!joueur.equals(donneesJeu2.getJoueur(numJoueur))) {
                return false;
            }
        }

        //On regarde que les transformateurs sont identiques
        for (int i = 0; i < donneesJeu1.getObjetsFixes().length; i++) {
            for (int j = 0; j < donneesJeu1.getObjetsFixes()[i].length; j++) {
                if (donneesJeu1.getObjetsFixes()[i][j] instanceof Transformateur || donneesJeu1.getObjetsFixes()[i][j] instanceof PlanDeTravail) {
                    if (!donneesJeu1.getObjetsFixes()[i][j].equals(donneesJeu2.getObjetsFixes()[i][j])) {
                        return false;
                    }
                }
            }
        }

        //On vérifie que les deux listes de plats déposés sont identiques
        return donneesJeu1.getPlatDepose().size() == donneesJeu2.getPlatDepose().size()
                && (donneesJeu1.getPlatDepose().isEmpty()
                || donneesJeu1.getPlatDepose().getFirst().equals(donneesJeu2.getPlatDepose().getFirst()));

    }
}
