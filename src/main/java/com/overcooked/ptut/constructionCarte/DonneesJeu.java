package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.JoueurIA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DonneesJeu {

    public static final char MUR = 'X';
    public static final char JOUEUR = 'J';
    public static final char VIDE = '.';
    public static final char DEPOT = 'D';
    public static final char COUTEAU = 'C';
    public static final char POELE = 'P';
    public static final char GENERATEURTOMATE = 't';
    public static final char GENERATEURPAIN = 'p';
    public static final char GENERATEURSALADE = 's';
    public static final char GENERATEURVIANDE = 'v';

    /**
     * carte du jeu
     */
    private char[][] carte;
    private List<int[]> murs;
    private List<Joueur> joueurs;
    private Depot depot;
//    private List<Couteau> couteaux;
//    //private List<Poele> poeles;
//    private List<Generateur> generateurs;

    public DonneesJeu(String chemin) {
        try {
            // ouvrir fichier
            File fichier = new File(chemin);
            FileReader reader = new FileReader(fichier);
            BufferedReader bfRead = new BufferedReader(reader);

            this.carte = new char[getHauteurFichier(fichier)][getLongueurFichier(fichier)];
            murs = new ArrayList<>();
            joueurs = new ArrayList<>();
//            couteaux = new ArrayList<>();

            // lecture des cases
            String ligne = bfRead.readLine();

            // stocke les indices courants
            int indexLigne = 0;

            // parcours le fichier
            while (ligne != null) {
                for (int indexColonne = 0; indexColonne < ligne.length(); indexColonne++) {
                    char c = ligne.charAt(indexColonne);
                    carte[indexLigne][indexColonne] = c;
                    switch (c) {
                        case MUR:
                            murs.add(new int[]{indexLigne, indexColonne});
                            break;
                        case JOUEUR:
                            joueurs.add(new JoueurIA(indexLigne, indexColonne));
                            break;
                        case DEPOT:
                            depot = new Depot(indexLigne, indexColonne);
                            break;
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
                        case VIDE:
                            break;
                        default:
                            throw new Error("caractere inconnu " + c);
                    }
                }
                indexLigne++;
                ligne = bfRead.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * retourne la longueur du fichier
     *
     * @param f fichier
     * @return longueur
     * @throws IOException erreur
     */
    public int getLongueurFichier(File f) throws IOException {
        BufferedReader cloned = new BufferedReader(new FileReader(f));
        return cloned.readLine().length();
    }

    /**
     * retourne la hauteur du fichier
     *
     * @param f fichier
     * @return hauteur
     * @throws IOException erreur
     */
    public int getHauteurFichier(File f) throws IOException {
        BufferedReader cloned = new BufferedReader(new FileReader(f));
        int hauteur = 0;
        while (cloned.readLine() != null) {
            hauteur++;
        }
        return hauteur;
    }

    /**
     * public void deplacerPerso(String action, Personnage pj) {
     * int[] chemin = Carte.getSuivant(pj.getX(), pj.getY(), action);
     * if (carte[chemin[0]][chemin[1]] == VIDE) {
     * pj = new Personnage(chemin[0], chemin[1], getPj().getPv());
     * }
     * }
     **/

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (char[] ligne : carte) {
            for (char c : ligne) {
                s.append(c);
            }
            s.append("\n");
        }
        return s.toString();
    }
}