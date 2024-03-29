package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Depot;
import com.overcooked.ptut.objet.Generateur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Plat;

public class GestionActions {

    /**
     * Retourne vrai si l'action a est légale dans l'état courant pour le joueur numJoueur
     */
    public static boolean isLegal(Action a, int numJoueur, DonneesJeu donneesJeu) {
        // Récupération des données
        Bloc[][] objetsFixes = donneesJeu.getObjetsFixes();
        Plat[][] objetsDeplacables = donneesJeu.getObjetsDeplacables();

        Joueur joueur = donneesJeu.getJoueur(numJoueur);
        int[] positionJoueur = joueur.getPosition();

        int[] caseDevant;
        caseDevant = joueur.getPositionCible();

        final Bloc objetFixe = objetsFixes[caseDevant[0]][caseDevant[1]];
        final Plat objetDeplacable = objetsDeplacables[caseDevant[0]][caseDevant[1]];
        return switch (a) {
            case HAUT, BAS, GAUCHE, DROITE -> objetFixe == null || joueur.getDirection() != a;
            case PRENDRE -> {
                //On vérifie que ses mains sont libres
                if (joueur.getInventaire() != null) {
                    if (objetFixe instanceof Generateur generateur) {
                        yield joueur.getInventaire().estFusionnable(generateur.getAliment());
                    }
                    if (objetFixe instanceof PlanDeTravail && objetFixe.getInventaire() != null) {
                        yield joueur.getInventaire().estFusionnable(objetFixe.getInventaire());
                    }
                    if (objetFixe instanceof Transformateur transformateur && transformateur.getInventaire() != null)
                        yield joueur.getInventaire().estFusionnable(transformateur.getInventaire());
                    yield false;
                } else {
                    // Calcul des coordonnés de la case devant le joueur

                    //Recherche dans objetDeplacable s'il y a un objet à prendre
                    yield objetDeplacable != null
                            || objetsDeplacables[positionJoueur[0]][positionJoueur[1]] != null
                            || objetFixe instanceof Generateur
                            || (objetFixe instanceof PlanDeTravail && objetFixe.getInventaire() != null)
                            || objetFixe instanceof Transformateur && objetFixe.getInventaire() != null;
                }
            }
            case POSER -> {
                // On vérifie si le joueur à quelque chose dans les mains
                if (joueur.getInventaire() == null) {
                    yield false;
                } else {
                    //On vérifie le cas où on a un transformateur
                    if (objetFixe instanceof Transformateur) {
                        yield joueur.getInventaire().getRecettesComposees().size() == 1
                                && objetFixe.getInventaire() == null;

                    } //Il faut vérifier que l'aliment est fusionable avec l'objet fixe
                    if (objetFixe instanceof Generateur generateur) {
                        yield joueur.getInventaire().estFusionnable(generateur.getAliment());
                    }
                    if (objetFixe instanceof PlanDeTravail && objetFixe.getInventaire() != null) {
                        yield joueur.getInventaire().estFusionnable(objetFixe.getInventaire());
                    }
                    if (objetFixe instanceof PlanDeTravail && objetFixe.getInventaire() == null) {
                        yield true;
                    }
                    //On vérifie que l'on ne peut pas poser par terre
                    yield objetFixe != null;
                }

            }

            case UTILISER -> objetFixe instanceof Transformateur transformateur
                    && !transformateur.isBloque() && transformateur.getInventaire() != null
                    && joueur.getInventaire() == null
                    && transformateur.getInventaire().getRecettesComposees().size() == 1
                    && !transformateur.estTransforme();
            case RIEN -> true;

            //Exception si l'action n'est pas reconnue
            default -> throw new IllegalArgumentException("DonneesJeu.isLegal, action invalide" + a);
        };
    }


    /**
     * Methode permettant de faire une action
     *
     * @param a         Action à faire
     * @param numJoueur Numéro du joueur
     */
    public static void faireAction(Action a, int numJoueur, DonneesJeu donneesJeu) {
        Bloc[][] objetsFixes = donneesJeu.getObjetsFixes();
        Plat[][] objetsDeplacables = donneesJeu.getObjetsDeplacables();
        Joueur joueur = donneesJeu.getJoueur(numJoueur);
        int[] positionJoueurCible = joueur.getPositionCible();
        switch (a) {
            // Deplacement du joueur si possible
            case DROITE, GAUCHE, HAUT, BAS -> {
                joueur.changeDirection(a);
                // On remet le joueur dans la bonne direction, car il change sa direction avant de se déplacer
                positionJoueurCible = joueur.getPositionCible();
                if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] == null) {
                    joueur.deplacer(a);
                }
            }
            //Prendre un objet
            case PRENDRE -> prendre(joueur, objetsFixes, objetsDeplacables);

            //Poser un objet (dans la case devant le joueur)
            case POSER -> poser(objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]], joueur);

            //Utiliser un transformateur
            case UTILISER -> utiliser((Transformateur) objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]]);

            case RIEN -> {
            }

            //Exception si l'action n'est pas reconnue
            default -> throw new IllegalArgumentException("DonneesJeu.faireAction, action invalide" + a);
        }
    }

    private static void utiliser(Transformateur transformateur) {
        if (transformateur.getInventaire() != null) {
//            transformateur.setBloque(true);
            transformateur.transform();
        }
    }

    private static void poser(Bloc objetFixe, Joueur joueur) {
        if (objetFixe instanceof Depot) {
            if (joueur.getInventaire() != null) {
                Depot depot = (Depot) objetFixe;
                depot.deposerPlat(joueur.poser());
                return;
            }
        }

        if (objetFixe instanceof Transformateur transformateur) {
            if (transformateur.getInventaire() != null) return;
            transformateur.ajouterElem(joueur.poser());
            return;
        }

        if (objetFixe instanceof PlanDeTravail planDeTravail) {
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
     * @param joueur Joueur qui prend l'objet
     */
    private static void prendre(Joueur joueur, Bloc[][] objetsFixes, Plat[][] objetsDeplacables) {
        // Position cible du joueur en fonction de sa direction
        int[] positionJoueurCible = joueur.getPositionCible();
//        System.out.println(Arrays.toString(joueur.getPosition()));
        int[] positionJoueur = joueur.getPosition();
        Plat objetsDeplacableCible = objetsDeplacables[positionJoueurCible[0]][positionJoueurCible[1]];
        Plat objetsDeplacable = objetsDeplacables[positionJoueur[0]][positionJoueur[1]];
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
        if (objetsDeplacable == null) return;
        //Prendre un objet déjà présent sur la carte
        joueur.prendre(objetsDeplacable);
        objetsDeplacables[positionJoueur[0]][positionJoueur[1]] = null;
    }

}
