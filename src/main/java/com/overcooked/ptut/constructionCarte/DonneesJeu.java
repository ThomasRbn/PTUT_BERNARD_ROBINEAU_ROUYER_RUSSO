package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Mouvable;
import com.overcooked.ptut.recettes.aliment.Pain;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Salade;
import com.overcooked.ptut.recettes.etat.Coupe;

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
    public static final char PLANCHE = 'C';
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

    private List<Mouvable> objetsDeplacables;
    private List<Plat> platsBut;

    public DonneesJeu(String chemin) {
        objetsDeplacables = new ArrayList<>();
        platsBut = new ArrayList<>();
        Plat saladePain = new Plat("saladePain", new Coupe(new Salade()), new Pain());
        platsBut.add(saladePain);
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
//                        case PLANCHE:
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


    public DonneesJeu(DonneesJeu donneesJeu) {
        //TODO: Constructeur par copie, attention a n'avoir aucun effet de bord, réaliser des test unitaires.
        platsBut = new ArrayList<>();
        Plat saladePain = new Plat();
        saladePain.setNom("saladePain");
        saladePain.ajouterAliment(new Coupe(new Salade()));
        saladePain.ajouterAliment(new Pain());
        platsBut.add(saladePain);
        this.longueur = donneesJeu.longueur;
        this.hauteur = donneesJeu.hauteur;
        this.plansDeTravail = donneesJeu.plansDeTravail;
        this.sols = donneesJeu.sols;
        this.depot = donneesJeu.depot;
        this.joueurs = new ArrayList<>();
        System.out.println("Test");

        List<int[]> coordonneesJoueurs = new ArrayList<>() {{
            for (Joueur joueur : donneesJeu.joueurs) {
                add(joueur.getPosition());
            }
        }};

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < longueur; j++) {
                for (int[] coordonnees : coordonneesJoueurs) {
                    if (coordonnees[0] == i && coordonnees[1] == j) {
                        joueurs.add(new JoueurIA(i, j));
                    }
                }
            }
        }
    }


    public void faireAction(Action a, int numJoueur) {
        Joueur joueur = joueurs.get(numJoueur);
        switch (a) {
            case DROITE, HAUT, BAS, GAUCHE -> joueur.deplacer(a);
            case PRENDRE -> {
                // Premier cas avec la position du joueur (sous le joueur, car il est possible de chevaucher un objet)
                //joueur.prendre();
            }
            case POSER -> objetsDeplacables.add(joueur.poser());
            default -> throw new IllegalArgumentException("Invalid" + a);
        }
    }


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
