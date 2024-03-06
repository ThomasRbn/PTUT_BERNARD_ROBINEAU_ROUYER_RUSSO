package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.objet.Depot;
import com.overcooked.ptut.objet.Generateur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class Copie {

    /**
     * Retourne une copie des objets fixes du jeu de données
     *
     * @param hauteur Hauteur de la carte
     * @param longueur Longueur de la carte
     * @param objetsFixes Ensemble d'objets fixes à copier
     * @return Une copie des objets fixes
     */
    static Bloc[][] CopieObjetFixe(int hauteur, int longueur, Bloc[][] objetsFixes) {
        Bloc[][] objetsFixesCopie = new Bloc[hauteur][longueur];
        for (int i = 0; i < objetsFixes.length; i++) {
            for (int j = 0; j < objetsFixes[i].length; j++) {
                if (objetsFixes[i][j] != null) {
                    objetsFixesCopie[i][j] = switch (objetsFixes[i][j]) {
                        case Depot depot -> new Depot(depot);
                        case Generateur generateur -> new Generateur(generateur);
                        case Planche planche -> new Planche(planche);
                        case Poele poele -> new Poele(poele);
                        case PlanDeTravail planDeTravail -> new PlanDeTravail(planDeTravail);
                        default -> throw new IllegalArgumentException("DonneesJeu.constructeur, caractère inconnu : " + objetsFixes[i][j]);
                    };
                }
            }
        }
        return objetsFixesCopie;
    }

    /**
     * Retourne une copie des objets déplaçables du jeu de données
     *
     * @param hauteur Hauteur de la carte
     * @param longueur Longueur de la carte
     * @param objetsDeplacables Ensemble d'objets déplaçables à copier
     * @return Une copie des objets déplaçables
     */
    static Plat[][] CopieObjetDeplacables(int hauteur, int longueur, Plat[][] objetsDeplacables) {
        Plat[][] objetsDeplacablesCopie = new Plat[hauteur][longueur];
        for (int i = 0; i < objetsDeplacables.length; i++) {
            for (int j = 0; j < objetsDeplacables[i].length; j++) {
                if (objetsDeplacables[i][j] != null) {
                    objetsDeplacablesCopie[i][j] = objetsDeplacables[i][j];
                }
            }
        }
        return objetsDeplacablesCopie;
    }

    /**
     * Retourne une copie des joueurs du jeu de données
     *
     * @param joueurs Ensemble de joueurs à copier
     * @return Une copie des joueurs
     */
    static List<Joueur> CopieJoueurs(List<Joueur> joueurs) {
        ArrayList<Joueur> joueursCopie = new ArrayList<>();
        for (Joueur joueur : joueurs) {
            joueursCopie.add( joueur instanceof JoueurIA
                ? joueur.getInventaire() != null
                    ? new JoueurIA(joueur.getPosition()[0], joueur.getPosition()[1], joueur.getCloneInventaire(), joueur.getDirection(), joueur.getNumJoueur())
                    : new JoueurIA(joueur.getPosition()[0], joueur.getPosition()[1], null, joueur.getDirection(), joueur.getNumJoueur())
                : joueur.getInventaire() != null
                    ? new JoueurHumain(joueur.getPosition()[0], joueur.getPosition()[1], joueur.getCloneInventaire(), joueur.getDirection(), joueur.getNumJoueur())
                    : new JoueurHumain(joueur.getPosition()[0], joueur.getPosition()[1], null, joueur.getDirection(), joueur.getNumJoueur()));
            }
        return joueursCopie;
    }
}
