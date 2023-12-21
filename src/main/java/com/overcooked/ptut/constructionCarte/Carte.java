package com.overcooked.ptut.constructionCarte;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Carte {

    public static final char MUR = 'X';
    public static final char JOUEUR = 'J';
    public static final char VIDE = '.';
    public static final char DEPOT = 'D';
    public static final char COUTEAU = 'C';
    public static final char POELE = 'P';
    public static final char GENERATEURTOMATE = 'T';
    public static final char GENERATEURPAIN = 'G';
    public static final char GENERATEURSALADE = 'S';
    public static final char GENERATEURSTEAK = 'K';


    /**
     * constantes actions possibles
     */
    public static final String HAUT = "Haut";
    public static final String BAS = "Bas";
    public static final String GAUCHE = "Gauche";
    public static final String DROITE = "Droite";
    public static final String[] DIRECTION_TAB = {HAUT, BAS, GAUCHE, DROITE};

    public static char[][] chargerCarte(String chemin) {
        File fichier = new File(chemin);
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichier));
            System.out.println(getLongueurFichier(fichier));
            System.out.println(getHauteurFichier(fichier));
            char[][] carte = new char[getHauteurFichier(fichier)][getLongueurFichier(fichier)];
            System.out.println("longueur : " + carte.length);
            System.out.println("hauteur : " + carte[0].length);
            String ligne;

            int x = 0;
            while ((ligne = br.readLine()) != null) {
                for (int y = 0; y < carte[x].length; y++) {
                    carte[x][y] = ligne.charAt(y);
                }
                x++;
            }

            br.close();
            return carte;
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier de chargement de la carte");
        }
        throw new Error("Erreur lors de la lecture du fichier de chargement de la carte");
    }

    /**
     * retourne la longueur du fichier
     * @param f fichier
     * @return longueur
     * @throws IOException erreur
     */
    public static int getLongueurFichier(File f) throws IOException {
        BufferedReader cloned = new BufferedReader(new FileReader(f));
        return cloned.readLine().length();
    }

    /**
     * retourne la hauteur du fichier
     * @param f fichier
     * @return hauteur
     * @throws IOException erreur
     */
    public static int getHauteurFichier(File f) throws IOException {
        BufferedReader cloned = new BufferedReader(new FileReader(f));
        int hauteur = 0;
        while (cloned.readLine() != null) {
            hauteur++;
        }
        return hauteur;
    }

    /**
     * retourne la case suivante selon une actions
     *
     * @param x      case depart
     * @param y      case depart
     * @param action action effectuee
     * @return case suivante
     */
    static int[] getSuivant(int x, int y, String action) {
        switch (action) {
            case HAUT:
                // on monte une ligne
                y--;
                break;
            case BAS:
                // on descend une ligne
                y++;
                break;
            case DROITE:
                // on augmente colonne
                x++;
                break;
            case GAUCHE:
                // on augmente colonne
                x--;
                break;
            default:
                throw new Error("action inconnue");
        }
        return new int[]{x, y};
    }
}
