package com.overcooked.ptut.recettes.etat;

/**
 * Classe correspondant a un etat d'un aliment. Un aliment peut avoir plusieurs etats
 */
public class Etat {
    public static final int CRU = 0;
    public static final int CUIT = 1;
    public static final int COUPE = 2;
    public static final int CUIT_ET_COUPE = 3;

    public static int transformEtat(int etat, int etat1) {
        if(etat == CRU && etat1 == CUIT)
            return CUIT;
        else if(etat == CUIT && etat1 == COUPE)
            return CUIT_ET_COUPE;
        else if(etat == CRU && etat1 == COUPE)
            return COUPE;
        else if(etat == COUPE && etat1 == CUIT)
            return CUIT_ET_COUPE;
        else if(etat == CUIT_ET_COUPE && etat1 == CUIT)
            return CUIT_ET_COUPE;
        else if(etat == CUIT_ET_COUPE && etat1 == COUPE)
            return CUIT_ET_COUPE;
        else
            return etat;
    }
}
