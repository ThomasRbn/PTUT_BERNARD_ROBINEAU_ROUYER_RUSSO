package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Plat;

public class GestionActions {
    /**
     * Methode permettant de faire une action
     *
     * @param a
     * @param numJoueur
     */
    public static void faireAction(Action a, int numJoueur, DonneesJeu donneesJeu) {
        Bloc[][] objetsFixes = donneesJeu.getObjetsFixes();
        Plat[][] objetsDeplacables = donneesJeu.getObjetsDeplacables();
        Joueur joueur = donneesJeu.getJoueur(numJoueur);
        joueur.changeDirection(a);
        int[] positionJoueurCible = joueur.getPositionCible();
        switch (a) {
            //Deplacement du joueur si possible
            case DROITE, GAUCHE, HAUT, BAS -> {
                if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] == null) {
                    joueur.deplacer(a);
                }
            }
            //Prendre un objet
            case PRENDRE -> prendre(joueur, objetsFixes, objetsDeplacables);
            //Poser un objet (dans la case devant le joueur)
            case POSER -> {
                if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] instanceof Depot) {
                    if (joueur.getInventaire() != null) {
                        Depot depot = (Depot) objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]];
                        depot.deposerPlat(joueur.poser());
                        return;
                    }
                }
                if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] instanceof Transformateur transformateur) {
                    transformateur.ajouterElem(joueur.getInventaire());
                    System.out.println(transformateur.getElemPose());
                    return;
                }
                objetsDeplacables[joueur.getPosition()[0]][joueur.getPosition()[1]] = joueur.poser();
            }
            //Exception si l'action n'est pas reconnue
            default -> throw new IllegalArgumentException("DonneesJeu.faireAction, action invalide" + a);
        }
    }


    /**
     * Methode permettant de prendre un objet
     *
     * @param joueur
     */
    private static void prendre(Joueur joueur, Bloc[][] objetsFixes, Plat[][] objetsDeplacables){
        // Position cible du joueur en fonction de sa direction
        int[] positionJoueurCible = joueur.getPositionCible();
//        System.out.println(Arrays.toString(joueur.getPosition()));
        Plat objetsDeplacableCible = objetsDeplacables[positionJoueurCible[0]][positionJoueurCible[1]];
        if (objetsDeplacableCible != null) {
            joueur.prendre(objetsDeplacableCible);
            objetsDeplacables[positionJoueurCible[0]][positionJoueurCible[1]] = null;
            return;
        }

        // Position du joueur (blocs mouvables)
        Bloc objetsFix = objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]];
        switch (objetsFix) {
            case null -> {
            }
            case Generateur generateur -> {
                joueur.prendre(generateur.getAliment());
                return;
            }
            case Transformateur transformateur -> {
                joueur.prendre(transformateur.retirerElem());
                System.out.println(joueur.getInventaire());
                return;
            }
            default -> throw new IllegalArgumentException("DonneesJeu.prendre, bloc invalide" + objetsFix);
        }
        //Prendre un objet déjà présent sur la carte
        int[] positionJoueur = joueur.getPosition();
        Plat objetsDeplacable = objetsDeplacables[positionJoueur[0]][positionJoueur[1]];
        if (objetsDeplacable != null) {
            joueur.prendre(objetsDeplacable);
            objetsDeplacables[positionJoueur[0]][positionJoueur[1]] = null;
        }
    }

}
