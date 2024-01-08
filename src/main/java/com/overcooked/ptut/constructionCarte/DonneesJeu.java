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
    public static final char SOL = '.';
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
    private int longueur, hauteur;
    private List<int[]> plansDeTravail;
    private List<int[]> sols;
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

            plansDeTravail = new ArrayList<>();
            joueurs = new ArrayList<>();
            sols = new ArrayList<>();
//            couteaux = new ArrayList<>();

            // lecture des cases
            String ligne = bfRead.readLine();
            longueur = ligne.length();

            // stocke les indices courants
            int indexLigne = 0;

            // parcours le fichier
            while (ligne != null) {
                for (int indexColonne = 0; indexColonne < ligne.length(); indexColonne++) {
                    char c = ligne.charAt(indexColonne);
                    switch (c) {
                        case MUR:
                            plansDeTravail.add(new int[]{indexLigne, indexColonne});
                            break;
                        case JOUEUR:
                            joueurs.add(new JoueurIA(indexLigne, indexColonne));
                            sols.add(new int[]{indexLigne, indexColonne});
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
                        case SOL:
                            sols.add(new int[]{indexLigne, indexColonne});
                            break;
                        default:
                            throw new Error("caractere inconnu " + c);
                    }
                }
                indexLigne++;
                ligne = bfRead.readLine();
            }
            hauteur = indexLigne;
            System.out.println(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * public void deplacerPerso(String action, Personnage pj) {
     * int[] chemin = Carte.getSuivant(pj.getX(), pj.getY(), action);
     * if (carte[chemin[0]][chemin[1]] == VIDE) {
     * pj = new Personnage(chemin[0], chemin[1], getPj().getPv());
     * }
     * }
     **/

    public List<int[]> getPlansDeTravail() {
        return plansDeTravail;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public List<int[]> getSols() {
        return sols;
    }

    public Depot getDepot() {
        return depot;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int[] coordonnees = new int[]{0, 0};
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < longueur; j++) {
                coordonnees[0] = i;
                coordonnees[1] = j;
                if (plansDeTravail.contains(coordonnees)) {
                    s.append(MUR);
                } else if (joueurs.contains(coordonnees)) {
                    s.append(JOUEUR);
                } else if (sols.contains(coordonnees)) {
                    s.append(SOL);
                } else if (coordonnees[0] == depot.getX() && coordonnees[1] == depot.getY()) {
                    s.append(DEPOT);
                } else {
                    s.append(" ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
}
