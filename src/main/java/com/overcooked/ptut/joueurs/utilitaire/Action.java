package com.overcooked.ptut.joueurs.utilitaire;

/**
 * Représente une action
 *
 */

public enum Action {
    HAUT("Haut"),
    BAS("Bas"),
    GAUCHE("Gauche"),
    DROITE("Droite"),
    PRENDRE("Prendre"),
    POSER("Poser"),
    COUPER("Couper");
    /**
     * Une action est juste une instance qui a un nom
     * pour mieux les lire :)
     */

    private final String name;

    /**
     * @param n le nom de l'action
     */
    Action(String n) {
        this.name = n;
    }

    /**
     * @return le nom de l'action
     */
    public String getName(){
        return this.name;
    }

}
