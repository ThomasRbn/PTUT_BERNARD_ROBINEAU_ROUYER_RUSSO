package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.io.*;

public class DonneesJeu {

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
    public static final Action HAUT = new Action("Haut");
    public static final Action BAS = new Action("Bas");
    public static final Action GAUCHE = new Action("Gauche");
    public static final Action DROITE = new Action("Droite");
    public static final Action PRENDRE = new Action("Prendre");
    public static final Action POSER = new Action("Poser");
    public static final Action COUPER = new Action("Couper");

    public static final Action[] DIRECTION_TAB = {HAUT, BAS, GAUCHE, DROITE};

    /**
     * carte du jeu
     */
    private int longueur;
    private int hauteur;
//    private List<Mur> murs;
//    private Depot depot;
//    private List<Couteau> couteaux;
//    //private List<Poele> poeles;
//    private List<Personnage> personnages;
//    private List<Generateur> generateurs;

    public DonneesJeu(String chemin) {
        try {
            // ouvrir fichier
            FileReader fichier = new FileReader(chemin);
            BufferedReader bfRead = new BufferedReader(fichier);


            // lecture nblignes
            hauteur = Integer.parseInt(bfRead.readLine());
            // lecture nbcolonnes
            longueur = Integer.parseInt(bfRead.readLine());


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
     * @return longueur
     */
    public int getLongueurFichier(){
        return longueur;
    }

    /**
     * retourne la hauteur du fichier
     * @return hauteur
     */
    public int getHauteurFichier(){
        return hauteur;
    }

    /**public void deplacerPerso(String action, Personnage pj) {
        int[] chemin = Carte.getSuivant(pj.getX(), pj.getY(), action);
        if (carte[chemin[0]][chemin[1]] == VIDE) {
            pj = new Personnage(chemin[0], chemin[1], getPj().getPv());
        }
    }**/


}
