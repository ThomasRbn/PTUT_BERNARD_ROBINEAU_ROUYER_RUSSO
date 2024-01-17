package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Depot;
import com.overcooked.ptut.objet.Generateur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.etat.Etat;
import com.overcooked.ptut.vue.Plateau;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;

public class GestionActions {

    /**
     * Retourne vrai si l'action a est légale dans l'état courant pour le joueur numJoueur
     */
    public static boolean isLegal(Action a, int numJoueur, DonneesJeu donneesJeu) {
        // Récupération des données
        int hauteur = donneesJeu.getHauteur();
        int longueur = donneesJeu.getLongueur();
        Bloc[][] objetsFixes = donneesJeu.getObjetsFixes();
        Plat[][] objetsDeplacables = donneesJeu.getObjetsDeplacables();

        Joueur joueur = donneesJeu.getJoueur(numJoueur);
        int[] positionJoueur = joueur.getPosition();
        Action direction = joueur.getDirection();
        return switch (a) {
            case HAUT -> positionJoueur[0] != 0 || direction != HAUT;
            case GAUCHE -> positionJoueur[1] != 0 || direction != GAUCHE;
            case BAS -> positionJoueur[0] != hauteur - 1 || direction != BAS;
            case DROITE -> positionJoueur[1] != longueur - 1 || direction != DROITE;
            case PRENDRE -> {
                int[] caseDevant = new int[2];
                caseDevant = joueur.getPositionCible();
                //On vérifie que ses mains sont libres
                if (joueur.getInventaire() != null) {
                    if (objetsFixes[caseDevant[0]][caseDevant[1]] instanceof Generateur generateur) {
                        yield joueur.getInventaire().estFusionnable(generateur.getAliment());
                    }
                    if (objetsFixes[caseDevant[0]][caseDevant[1]] instanceof PlanDeTravail && objetsFixes[caseDevant[0]][caseDevant[1]].getInventaire() != null) {
                        yield joueur.getInventaire().estFusionnable(objetsFixes[caseDevant[0]][caseDevant[1]].getInventaire());
                    }
                    yield false;
                } else {
                    // Calcul des coordonnes de la case devant le joueur

                    //Recherche dans objetDeplacable s'il y a un objet à prendre
                    yield objetsDeplacables[caseDevant[0]][caseDevant[1]] != null
                            || objetsDeplacables[positionJoueur[0]][positionJoueur[1]] != null
                            || objetsFixes[caseDevant[0]][caseDevant[1]] instanceof Generateur
                            || (objetsFixes[caseDevant[0]][caseDevant[1]] instanceof PlanDeTravail && ((PlanDeTravail) objetsFixes[caseDevant[0]][caseDevant[1]]).getInventaire() != null)
                            || objetsFixes[caseDevant[0]][caseDevant[1]] instanceof Transformateur && ((Transformateur) objetsFixes[caseDevant[0]][caseDevant[1]]).getInventaire() != null;
                }
            }

            case POSER -> {
                int[] caseDevant = new int[2];
                caseDevant = joueur.getPositionCible();
                // On vérifie si le joueur à quelque chose dans les mains
                if (joueur.getInventaire() == null) {
                    yield false;
                } else {
                    if (objetsDeplacables[caseDevant[0]][caseDevant[1]] != null) {
                        yield true;
                    }
                }
                // Calcul des coordonnés de la case devant le joueur
                //Recherche dans objetDeplacable s'il y a un objet devant le joueur
                yield objetsDeplacables[caseDevant[0]][caseDevant[1]] == null;
                //TODO: Vérifier que la case devant soit compatible avec l'objet à déplacer (ex: pas d'aliment sur le feu sans poele)
            }
            //Exception si l'action n'est pas reconnue
            default -> throw new IllegalArgumentException("DonneesJeu.isLegal, action invalide" + a);
        };
    }


    /**
     * Methode permettant de faire une action
     *
     * @param a
     * @param numJoueur
     */
    public static void faireAction(Action a, int numJoueur, DonneesJeu donneesJeu, Plateau... plateau) {
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
            case PRENDRE -> {
                prendre(joueur, objetsFixes, objetsDeplacables);
//                System.out.println("Prendre : " + joueur.getInventaire().getRecettesComposees());
            }

            //Poser un objet (dans la case devant le joueur)
            case POSER -> {
                poser(objetsFixes, positionJoueurCible, joueur);
            }
            //Utiliser un transformateur
            case UTILISER -> {
                if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] instanceof Transformateur transformateur) {
                    if (joueur.getInventaire() != null || transformateur.isBloque()) return;
                    if (transformateur instanceof Planche
                            && transformateur.getInventaire().getRecettesComposees().getFirst().getEtat() == Etat.COUPE
                            || transformateur.getInventaire().getRecettesComposees().getFirst().getEtat() == Etat.CUIT_ET_COUPE)
                        return;
                    if (transformateur instanceof Poele
                            && transformateur.getInventaire().getRecettesComposees().getFirst().getEtat() == Etat.CUIT
                            || transformateur.getInventaire().getRecettesComposees().getFirst().getEtat() == Etat.CUIT_ET_COUPE)
                        return;

                    if (transformateur.getInventaire() != null) {
                        transformateur.setBloque(true);
                        plateau[0].genererTask(transformateur, donneesJeu);
                    }
                }
            }
            //Exception si l'action n'est pas reconnue
            default -> throw new IllegalArgumentException("DonneesJeu.faireAction, action invalide" + a);
        }
    }

    private static void poser(Bloc[][] objetsFixes, int[] positionJoueurCible, Joueur joueur) {
        if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] instanceof Depot) {
            if (joueur.getInventaire() != null) {
                Depot depot = (Depot) objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]];
                depot.deposerPlat(joueur.poser());
                return;
            }
        }

        if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] instanceof Transformateur transformateur) {
            if (transformateur.getInventaire() != null) return;
            transformateur.ajouterElem(joueur.poser());
            System.out.println("Poser : " + transformateur.getInventaire().getRecettesComposees().getFirst().getEtat());
            return;
        }

        if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] instanceof PlanDeTravail planDeTravail) {
            if (planDeTravail.getInventaire() == null) {
                planDeTravail.poserDessus(joueur.poser());
                return;
            }
            if (planDeTravail.getInventaire() instanceof Plat plat) {
                if (joueur.getInventaire() != null) {
                    plat.fusionerPlat(joueur.poser());
                }
            }
        }
    }


    /**
     * Methode permettant de prendre un objet
     *
     * @param joueur
     */
    private static void prendre(Joueur joueur, Bloc[][] objetsFixes, Plat[][] objetsDeplacables) {
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
                if (transformateur.isBloque()) return;
                joueur.prendre(transformateur.retirerElem());
//                System.out.println("Prendre : " + joueur.getInventaire().getRecettesComposees().getFirst().getEtat());
                return;
            }
            case PlanDeTravail planDeTravail -> {
                if (planDeTravail.getInventaire() != null) {
                    joueur.prendre(planDeTravail.getInventaire());
                    planDeTravail.poserDessus(null);
                    return;
                }
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
