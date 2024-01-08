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

            for (int i = 0; i < joueurs.size(); i++) {
                joueurs.get(i).setNumJoueur(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DonneesJeu(DonneesJeu donneesJeu){
        //TODO: Constructeur par copie, attention a n'avoir aucun effet de bord, réaliser des test unitaires.
    }

    /**
     * public void deplacerPerso(String action, int numJoueur) {
     * Joueur pj = joueurs.get(numJoueur);
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

    public int getLongueur() {
        return longueur;
    }

    public int getHauteur() {
        return hauteur;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int[] coordonneesAComparer = new int[]{0, 0};
        List<int[]> coordonneesJoueurs = new ArrayList<>() {{
            for (Joueur joueur : joueurs) {
                add(joueur.getPosition());
            }
        }};

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < longueur; j++) {
                coordonneesAComparer[0] = i;
                coordonneesAComparer[1] = j;
                System.out.println(coordonneesAComparer[0] + " " + coordonneesAComparer[1]);
                boolean estJoueur = false;

                for (int[] coordonnees : coordonneesJoueurs) {
                    if (coordonnees[0] == coordonneesAComparer[0] && coordonnees[1] == coordonneesAComparer[1]) {
                        s.append(JOUEUR);
                        estJoueur = true;
                    }
                }

                for (int[] coordonnees : plansDeTravail) {
                    if (coordonnees[0] == coordonneesAComparer[0] && coordonnees[1] == coordonneesAComparer[1]) {
                        s.append(MUR);
                    }
                }

                for (int[] coordonnees : sols) {
                    if (!estJoueur && coordonnees[0] == coordonneesAComparer[0] && coordonnees[1] == coordonneesAComparer[1]) {
                        s.append(SOL);
                    }
                }

                if (depot.getX() == coordonneesAComparer[0] && depot.getY() == coordonneesAComparer[1]) {
                    s.append(DEPOT);
                }

                s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
