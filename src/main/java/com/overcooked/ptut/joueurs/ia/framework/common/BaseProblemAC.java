package com.overcooked.ptut.joueurs.ia.framework.common;

import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;

import java.util.ArrayList;

/**
 * Représente une abstraction pour un problème ou un jeu
 *
 */

public abstract class BaseProblemAC {

    /**
     *  <p>La liste de toutes les actions
     *  les classes concrètes doivent le renseigner </p>
     *   Voir {@link Partie3.ia.problemes.Vacuum} ou {@link Partie3.ia.problemes.EightPuzzle}
     */
    
    protected static AlimentCoordonnees[] ALIMENTCO = null;
       

    /**
     * Retourner les actions possibles un état
     * @param s Un état 
     * @return Les actions possibles (pas forcément toutes) depuis s
     */
    public abstract ArrayList<AlimentCoordonnees> getAlimentCoordonnees(State s);

    /**
     * Exécuter une action dans un état 
     * @param s Un état 
     * @param a Une action
     * @return L'état résultat de faire l'action a dans s
     */
    public abstract State doAlimentCoordonnees(State s, AlimentCoordonnees a);

}
