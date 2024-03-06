package com.overcooked.ptut.joueurs.ia.problemes.calculChemin;

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

public class AlgoCalculCheminState extends State implements HasHeuristic {

    //Données
    private final DonneesJeu donnees;

    private final int numJoueur;

    private final int[] objectif;

    private final Aliment aliment;

    /**
     * Constructeur
     *
     * @param donneesJeu Données du jeu
     * @param numJoueur  Numéro du joueur courant
     * @param aliment   Aliment à récupérer
     */
    public AlgoCalculCheminState(DonneesJeu donneesJeu, int numJoueur, Aliment aliment, int[] objectif) {
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
        return new AlgoCalculCheminState(donnees, numJoueur, aliment, objectif);
    }

    /**
     * Retourne vrai si l'état o est égal à l'état courant
     */
    @Override
    protected boolean equalsState(State o) {
        return ComparerDonneesJeu(donnees, ((AlgoCalculCheminState) o).getDonnees());
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
        int[] positionJoueur;
        if(Objects.equals(aliment.getNom(), "j")) {
           positionJoueur = donnees.getJoueur(numJoueur).getPosition();

        }else {

            positionJoueur = donnees.getJoueur(numJoueur).getPositionCible();
        }

        return Math.abs(positionJoueur[0] - objectif[0]) + Math.abs(positionJoueur[1] - objectif[1]);
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
     * Applique l'action à dans l'état courant
     */
    public void faireAction(Action a) {
        GestionActions.faireAction(a, numJoueur, donnees);
    }


    public boolean isGoalState() {
        Joueur joueur = donnees.getJoueur(numJoueur);
        if(Objects.equals(aliment.getNom(), "j")){
            return joueur.getPosition()[0] == objectif[0] && joueur.getPosition()[1] == objectif[1];
        }
        if (!(joueur.getPositionCible()[0] == objectif[0] && joueur.getPositionCible()[1] == objectif[1]) )return false;
        if(Objects.equals(aliment.getNom(), "Decoupe") || Objects.equals(aliment.getNom(), "Cuisson") ){
            //On vérifie si l'aliment à la position cible du joueur est découpé
            int[] coordonneesCible = joueur.getPositionCible();
            Bloc[][] carte = donnees.getObjetsFixes();
            Plat plat = carte[coordonneesCible[0]][coordonneesCible[1]].getInventaire();
            if(plat == null)return false;
            Aliment aliment2 = plat.getRecettesComposees().getFirst();
            return Objects.equals(aliment.getNom(), "Decoupe")? aliment2.getEtat() == 2 || aliment2.getEtat() == 3 : aliment2.getEtat() == 1 || aliment2.getEtat() == 3;
        }
        if(Objects.equals(aliment.getNom(), "Depot")){
            return !donnees.getPlatDepose().isEmpty();
        }
        if(Objects.equals(aliment.getNom(), "pdt")){
            return joueur.getInventaire()== null;
        }


        Plat inventaireJoueur = joueur.getInventaire();
        if(inventaireJoueur == null)return false;
        //Parcours de l'inventaire pour savoir s'il contient le plat
        List<Aliment> recetteComposee = inventaireJoueur.getRecettesComposees();
        for(Aliment aliment : recetteComposee){
            if(aliment.equals(this.aliment)){
                return true; // TODO: false?
            }
        }
        return false;
    }
}
