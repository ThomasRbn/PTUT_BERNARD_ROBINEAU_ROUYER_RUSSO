package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
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
                        case null, default -> new Bloc(objetsFixes[i][j]);
                    };
                }
            }
        }
        return objetsFixesCopie;
    }

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