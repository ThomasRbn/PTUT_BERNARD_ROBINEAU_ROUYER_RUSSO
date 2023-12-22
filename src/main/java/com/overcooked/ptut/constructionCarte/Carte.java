package com.overcooked.ptut.constructionCarte;

import java.io.*;
import java.util.ArrayList;

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

    /**
     * carte du jeu
     */
    private char[][] carte;
//    private List<Mur> murs;
//    private Depot depot;
//    private List<Couteau> couteaux;
//    //private List<Poele> poeles;
//    private List<Personnage> personnages;
//    private List<Generateur> generateurs;

    public Carte (String chemin) {
        try {
            // ouvrir fichier
            FileReader fichier = new FileReader(chemin);
            BufferedReader bfRead = new BufferedReader(fichier);

            int nbLignes, nbColonnes;
            // lecture nblignes
            nbLignes = Integer.parseInt(bfRead.readLine());
            // lecture nbcolonnes
            nbColonnes = Integer.parseInt(bfRead.readLine());

            this.carte = new char[getHauteurFichier(fichier)][getLongueurFichier(fichier)];
//            murs = new ArrayList<>();
//            couteaux = new ArrayList<>();
//            personnages = new ArrayList<>();

            // lecture des cases
            String ligne = bfRead.readLine();

            // stocke les indices courants
            int numeroLigne = 0;

            // parcours le fichier
            while (ligne != null) {
                for (int i = 0; i < ligne.length(); i++) {
                    char c = ligne.charAt(i);
//                    switch (c) {
//                        case MUR:
//                            murs.add(new Mur(i, numeroLigne));
//                            break;
//                        case JOUEUR:
//                            personnages.add(new Personnage(i, numeroLigne));
//                            break;
//                        case DEPOT:
//                            depot = new Depot(i, numeroLigne);
//                            break;
//                        case COUTEAU:
//                            couteaux.add(new Couteau(i, numeroLigne));
//                            break;
//                        case POELE:
//                            //poeles.add(new Poele(i, numeroLigne));
//                            break;
//                        case GENERATEURTOMATE:
//                            generateurs.add(new Generateur(i, numeroLigne, "tomate"));
//                            break;
//                        case GENERATEURPAIN:
//                            generateurs.add(new Generateur(i, numeroLigne, "pain"));
//                            break;
//                        case GENERATEURSALADE:
//                            generateurs.add(new Generateur(i, numeroLigne, "salade"));
//                            break;
//                        case GENERATEURSTEAK:
//                            generateurs.add(new Generateur(i, numeroLigne, "steak"));
//                            break;
//                        case VIDE:
//                            break;
//                        default:
//                            throw new Error("caractere inconnu");
//                    }
                }
                numeroLigne++;
                ligne = bfRead.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        /**
     * retourne la longueur du fichier
     * @param f fichier
     * @return longueur
     * @throws IOException erreur
     */
    public int getLongueurFichier(FileReader f) throws IOException {
        BufferedReader cloned = new BufferedReader(f);
        return cloned.readLine().length();
    }

    /**
     * retourne la hauteur du fichier
     * @param f fichier
     * @return hauteur
     * @throws IOException erreur
     */
    public int getHauteurFichier(FileReader f) throws IOException {
        BufferedReader cloned = new BufferedReader(f);
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

    /**public void deplacerPerso(String action, Personnage pj) {
        int[] chemin = Carte.getSuivant(pj.getX(), pj.getY(), action);
        if (carte[chemin[0]][chemin[1]] == VIDE) {
            pj = new Personnage(chemin[0], chemin[1], getPj().getPv());
        }
    }**/


}
