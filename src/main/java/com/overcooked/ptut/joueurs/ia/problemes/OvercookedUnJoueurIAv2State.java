package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.constructionCarte.GestionActions;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.HasHeuristic;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.List;
import java.util.Objects;

import static com.overcooked.ptut.constructionCarte.ComparateurDonneesJeu.ComparerDonneesJeu;

public class OvercookedUnJoueurIAv2State extends State implements HasHeuristic {

    //Données
    private DonneesJeu donnees;

    private int numJoueur;

    private int[] objectif;

    private Aliment aliment;

    /**
     * Constructeur
     *
     * @param donneesJeu Données du jeu
     * @param numJoueur  Numéro du joueur courant
     * @param aliment
     */
    public OvercookedUnJoueurIAv2State(DonneesJeu donneesJeu, int numJoueur, Aliment aliment, int[] objectif) {
        this.numJoueur = numJoueur;
        //Constructeur par copie
        this.donnees = new DonneesJeu(donneesJeu);
        this.objectif = objectif;
        this.aliment = aliment;
    }

    public DonneesJeu getDonnees() {
        return donnees;
    }

    /**
     * Retourne une copie de l'état
     */
    @Override
    protected State cloneState() {
        return new OvercookedUnJoueurIAv2State(donnees, numJoueur, aliment, objectif);
    }

    /**
     * Retourne vrai si l'état o est égal à l'état courant
     */
    @Override
    protected boolean equalsState(State o) {
        return ComparerDonneesJeu(donnees, ((OvercookedUnJoueurIAv2State) o).getDonnees());
    }

    @Override
    protected int hashState() {
        return 0;
    }

    /**
     * Retourne l'heuristique de l'état courant
     */
    @Override
    public double getHeuristic() {

        int[] positionJoueur = donnees.getJoueur(numJoueur).getPositionCible();

        int distance = Math.abs(positionJoueur[0] - objectif[0]) + Math.abs(positionJoueur[1] - objectif[1]);

        return distance;
    }

    public String toString() {
        return donnees.toString();
    }

    /**
     * Retourne vrai si l'action a est légale dans l'état courant
     */
    public boolean isLegal(Action a) {
        return GestionActions.isLegal(a, numJoueur, donnees);
    }

    /**
     * Applique l'action a dans l'état courant
     */
    public void faireAction(Action a) {
        GestionActions.faireAction(a, numJoueur, donnees);
    }


    public boolean isGoalState() {
        Joueur joueur = donnees.getJoueur(numJoueur);
        if (!(joueur.getPositionCible()[0] == objectif[0] && joueur.getPositionCible()[1] == objectif[1]) )return false;
        if(Objects.equals(aliment.getNom(), "Decoupe") || Objects.equals(aliment.getNom(), "Cuisson") ){
            //On vérifie si l'aliment à la position cible du joueur est découpé
            int[] coordonneesCible = joueur.getPositionCible();
            Bloc[][] carte = donnees.getObjetsFixes();
            Plat plat = carte[coordonneesCible[0]][coordonneesCible[1]].getInventaire();
            return plat != null;
        }
        if(aliment.getEtat() == 1 || aliment.getEtat() == 3){
            //On vérifie si l'aliment à la position cible du joueur est cuit
            int[] coordonneesCible = joueur.getPositionCible();
            Bloc[][] carte = donnees.getObjetsFixes();
            Plat plat = carte[coordonneesCible[0]][coordonneesCible[1]].getInventaire();
            if(donnees.getJoueur(numJoueur).getInventaire() == null)return false;
            return donnees.getJoueur(numJoueur).getInventaire().getRecettesComposees().getFirst().getEtat() == 1 || donnees.getJoueur(numJoueur).getInventaire().getRecettesComposees().getFirst().getEtat() == 3;
        }
        if(aliment.getEtat() == 2 || aliment.getEtat() == 3){
            //On vérifie si l'aliment à la position cible du joueur est cuit
            int[] coordonneesCible = joueur.getPositionCible();
            Bloc[][] carte = donnees.getObjetsFixes();
            Plat plat = carte[coordonneesCible[0]][coordonneesCible[1]].getInventaire();
            if(donnees.getJoueur(numJoueur).getInventaire() == null)return false;
            return donnees.getJoueur(numJoueur).getInventaire().getRecettesComposees().getFirst().getEtat() == 2 || donnees.getJoueur(numJoueur).getInventaire().getRecettesComposees().getFirst().getEtat() == 3;
        }
        if(Objects.equals(aliment.getNom(), "Depot")){
            return !donnees.getPlatDepose().isEmpty();
        }
        Plat inventaireJoueur = joueur.getInventaire();
        if(inventaireJoueur == null)return false;
        //Parcours de l'inventaire pour savoir s'il contient le plat
        List<Aliment> recetteComposee = inventaireJoueur.getRecettesComposees();
        for(Aliment aliment : recetteComposee){
            if(aliment.equalsType(this.aliment)){
                return true;
            }
        }
        return false;
    }
}
