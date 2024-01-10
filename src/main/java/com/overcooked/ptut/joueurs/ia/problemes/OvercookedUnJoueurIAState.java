package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.HasHeuristic;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.List;

public class OvercookedUnJoueurIAState extends State implements HasHeuristic {

    //Données
    private DonneesJeu donnees;

    private int numJoueur;

    /**
     * Constructeur
     *
     * @param donneesJeu Données du jeu
     * @param numJoueur  Numéro du joueur courant
     */
    public OvercookedUnJoueurIAState(DonneesJeu donneesJeu, int numJoueur) {
        this.numJoueur = numJoueur;
        //Constructeur par copie
        this.donnees = new DonneesJeu(donneesJeu);
    }

    public DonneesJeu getDonnees() {
        return donnees;
    }

    /**
     * Retourne une copie de l'état
     */
    @Override
    protected State cloneState() {
        return new OvercookedUnJoueurIAState(donnees, numJoueur);
    }

    /**
     * Retourne vrai si l'état o est égal à l'état courant
     */
    @Override
    protected boolean equalsState(State o) {
        // TODO: redéfinir methode equals dans donnees jeu
        return donnees.equals(((OvercookedUnJoueurIAState) o).getDonnees());
    }

    @Override
    protected int hashState() {
        return 0;
    }

//    @Override
//    public double getHeuristic() {
//        Plat platBut = donnees.getPlatsBut().get(0);
//        // On récupère les aliments nécessaires pour le plat
//        List<Aliment> aliments = platBut.getRecettesComposees();
//        int[] coordonneesDepot = donnees.getCoordonneesDepot();
//        int[] coordonneesJoueur = donnees.getJoueur(numJoueur).getPosition();
//        int heuristie = 0;
//        int[] dernieresCoordonnees = coordonneesJoueur;
//        //TODO: vérifier l'inventaire
//        Plat inventaire = donnees.getJoueur(numJoueur).getInventaire();
//
//        for (Aliment aliment : aliments) {
//            switch (aliment.getNom()) {
//                case "Tomate":
//                    if(inventaire == null || !(inventaire.getRecettesComposees().get(0) instanceof Tomate)) {
//                        List<int[]> coordonneesTomates = donnees.getCoordonneesTomates();
//                        int[] meilleursCo = dernieresCoordonnees;
//                        int distanceMin = Integer.MAX_VALUE;
//                        for (int[] coordonneesTomate : coordonneesTomates) {
//                            int distance = Math.abs(coordonneesTomate[0] - dernieresCoordonnees[0]) + Math.abs(coordonneesTomate[1] - dernieresCoordonnees[1]);
//                            if (distance < distanceMin) {
//                                distanceMin = distance;
//                                meilleursCo = coordonneesTomate;
//                            }
//                        }
//                        dernieresCoordonnees = meilleursCo;
//                        heuristie += distanceMin;
//                    }
//            }
//        }
//
//        // Calcul de la distance entre les dernieres coordonnees du joueur et le depot
//        System.out.println("Heuristie avant dernier calcul: "+heuristie);
//        heuristie += Math.abs(dernieresCoordonnees[0] - coordonneesDepot[0]) + Math.abs(dernieresCoordonnees[1] - coordonneesDepot[1]);
////        System.out.println(this.donnees);
//        System.out.println("Heuristique : " + heuristie);
//        return heuristie; // TODO: calcul de l'heuristie selon un objectif
//    }

    @Override
    public double getHeuristic() {
        double distanceMin = Integer.MAX_VALUE;
        int[] coordonneesDepot = donnees.getCoordonneesDepot();
        int[] coordonneesJoueur = donnees.getJoueur(numJoueur).getPosition();
        if (donnees.getJoueur(numJoueur).getInventaire() != null && donnees.getJoueur(numJoueur).getInventaire().getRecettesComposees().getFirst().getNom().equals("Tomate")) {
            return Math.abs(coordonneesDepot[0] - coordonneesJoueur[0]) + Math.abs(coordonneesDepot[1] - coordonneesJoueur[1]);
        }
        List<int[]> coordonneesTomates = donnees.getCoordonneesTomates();
        for (int[] coordonneesTomate : coordonneesTomates) {
            double distance = Math.abs(coordonneesTomate[0] - coordonneesJoueur[0]) + Math.abs(coordonneesTomate[1] - coordonneesJoueur[1]) + Math.abs(coordonneesDepot[0] - coordonneesJoueur[0]) + Math.abs(coordonneesDepot[1] - coordonneesJoueur[1]);
            if (distance < distanceMin) {
                distanceMin = distance;
            }
        }
        return distanceMin;
    }

    public String toString() {
        return donnees.toString();
    }

    /**
     * Retourne vrai si l'action a est légale dans l'état courant
     */
    public boolean isLegal(Action a) {
        return donnees.isLegal(a, numJoueur);
    }

    /**
     * Applique l'action a dans l'état courant
     */
    public void faireAction(Action a) {
        donnees.faireAction(a, numJoueur);
    }

    public boolean isGoalState() {
        //On vérifie que le premier plat déposé correspond au premier plat de la liste but
        return !donnees.getPlatDepose().isEmpty() && donnees.getPlatDepose().get(0).equals(donnees.getPlatsBut().get(0));
    }
}
