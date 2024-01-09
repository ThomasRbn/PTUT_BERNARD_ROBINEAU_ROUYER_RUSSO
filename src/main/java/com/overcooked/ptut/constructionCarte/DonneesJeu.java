package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
import com.overcooked.ptut.joueurs.Joueur;
//import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Mouvable;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.*;
import com.overcooked.ptut.recettes.etat.Coupe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;

public class DonneesJeu {

    public static final char PLAN_DE_TRAVAIL = 'X';
    public static final char JOUEUR = 'J';
    public static final char SOL = '.';
    public static final char DEPOT = 'D';
    public static final char PLANCHE = 'C';
    public static final char POELE = 'P';
    public static final char GENERATEURTOMATE = 't';
    public static final char GENERATEURPAIN = 'p';
    public static final char GENERATEURSALADE = 's';
    public static final char GENERATEURVIANDE = 'v';

    private Bloc[][] objetsFixes;
    private int longueur, hauteur;
    private List<Joueur> joueurs;
//    private List<Couteau> couteaux;
//    //private List<Poele> poeles;
//    private List<Generateur> generateurs;

    private Mouvable[][] objetsDeplacables;
    private List<Plat> platsBut;

    public DonneesJeu(String chemin) {
        platsBut = new ArrayList<>();
        Plat saladePain = new Plat("saladePain", new Coupe(new Salade()), new Pain());
        platsBut.add(saladePain);
        try {
            // ouvrir fichier
            File fichier = new File(chemin);
            FileReader reader = new FileReader(fichier);
            BufferedReader bfRead = new BufferedReader(reader);

            joueurs = new ArrayList<>();
            objetsFixes = new Bloc[getHauteur(fichier)][getLongueur(fichier)];
//            couteaux = new ArrayList<>();

            // lecture des cases
            String ligne = bfRead.readLine();
            longueur = ligne.length();

            // stocke les indices courants
            int indexColonne = 0;
            int indexLigne = 0;

            // parcours le fichier
            while (ligne != null) {
                System.out.println("Ligne " + indexLigne + " : " + ligne);
                for (indexColonne = 0; indexColonne < ligne.length(); indexColonne++) {
                    char c = ligne.charAt(indexColonne);
                    System.out.println("Colonne " + indexColonne + " : " + c);
                    switch (c) {
                        case PLAN_DE_TRAVAIL:
                            objetsFixes[indexLigne][indexColonne] = new Bloc(indexLigne, indexColonne);
                            break;
                        case DEPOT:
                            objetsFixes[indexLigne][indexColonne] = new Depot(indexLigne, indexColonne);
                            break;
                        case JOUEUR:
                            joueurs.add(new JoueurIA(indexLigne, indexColonne));
//                            joueurs.add(new JoueurHumain(indexLigne, indexColonne));
                            objetsFixes[indexLigne][indexColonne] = null;
                            break;
                        case GENERATEURSALADE:
                            objetsFixes[indexLigne][indexColonne] = new Generateur(indexLigne, indexColonne, new Salade());
                            break;
                        case GENERATEURPAIN:
                            objetsFixes[indexLigne][indexColonne] = new Generateur(indexLigne, indexColonne, new Pain());
                            break;
                        case PLANCHE:
                            objetsFixes[indexLigne][indexColonne] = new Planche(indexLigne, indexColonne);
                            break;
                        case POELE:
                            objetsFixes[indexLigne][indexColonne] = new Poele(indexLigne, indexColonne);
                            break;
                        default:
                            objetsFixes[indexLigne][indexColonne] = null;
                    }
                }
                indexLigne++;
                ligne = bfRead.readLine();
            }
            hauteur = indexLigne;

            System.out.println(this);
            for (Joueur joueur : joueurs) {
                //System.out.println("Etat initial du joueur " + joueur.getNumJoueur() + " : " + joueur.getPosition()[0] + ", " + joueur.getPosition()[1]);
            }
            objetsDeplacables = new Mouvable[getHauteur()][getLongueur()];
            for (int i = 0; i < joueurs.size(); i++) {
                joueurs.get(i).setNumJoueur(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public DonneesJeu(DonneesJeu donneesJeu) {
        platsBut = donneesJeu.platsBut;
        this.longueur = donneesJeu.longueur;
        this.hauteur = donneesJeu.hauteur;
        this.objetsFixes = donneesJeu.objetsFixes;
        this.objetsDeplacables = donneesJeu.objetsDeplacables;

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
                    joueurs.add(new JoueurIA(coordonnees[0], coordonnees[1]));
                }
            }
        }
    }


    public void faireAction(Action a, int numJoueur) {
        Joueur joueur = joueurs.get(numJoueur);
        joueur.changeDirection(a);
        int[] positionJoueurCible = joueur.retournePositionCible();
        switch (a) {
            case DROITE, GAUCHE, HAUT, BAS -> {
                if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] == null) {
                    joueur.deplacer(a);
                }
            }
            case PRENDRE -> {
                // Premier cas avec la position du joueur (sous le joueur, car il est possible de chevaucher un objet)
                prendre(joueur);
            }
            case POSER -> objetsDeplacables[joueur.getPosition()[0]][joueur.getPosition()[1]] = joueur.poser();
            default -> throw new IllegalArgumentException("DonneesJeu.faireAction, action invalide" + a);
        }
    }

    private void prendre(Joueur joueur) {
        // Position du joueur (blocs mouvables)
        int[] positionJoueurCible = joueur.retournePositionCible();
        Mouvable objetsDeplacableCible = objetsDeplacables[positionJoueurCible[0]][positionJoueurCible[1]];
        if (objetsDeplacableCible != null) {
            joueur.prendre(objetsDeplacableCible);
            objetsDeplacables[positionJoueurCible[0]][positionJoueurCible[1]] = null;
            return;
        }
        // Position cible du joueur en fonction de sa direction
        Bloc objetsFix = objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]];
        if (objetsFix instanceof Generateur) {
            System.out.println("On est là");
            joueur.prendre(((Generateur) objetsFix).getAliment());
        }
        int[] positionJoueur = joueur.getPosition();
        Mouvable objetsDeplacable = objetsDeplacables[positionJoueur[0]][positionJoueur[1]];
        if (objetsDeplacable != null) {
            joueur.prendre(objetsDeplacable);
            objetsDeplacables[positionJoueur[0]][positionJoueur[1]] = null;
        }
    }

    /**
     * Retourne vrai si l'action a est légale dans l'état courant pour le joueur numJoueur
     */
    public boolean isLegal(Action a, int numJoueur) {
        Joueur joueur = joueurs.get(numJoueur);
        int[] positionJoueur = joueur.getPosition();
        Action direction = joueur.getDirection();
        switch (a) {
            case HAUT -> {
                return positionJoueur[0] != 0 || direction != HAUT;
            }
            case GAUCHE -> {
                return positionJoueur[1] != 0 || direction != GAUCHE;
            }
            case BAS -> {
                return positionJoueur[0] != hauteur - 1 || direction != BAS;
            }
            case DROITE -> {
                return positionJoueur[1] != longueur - 1 || direction != DROITE;
            }
            case PRENDRE -> {
                //On vérifie que ses mains sont libres
                if (joueur.getInventaire() == null) {
                    return true;
                }

                // Calcul des coordonnes de la case devant le joueur
                int[] caseDevant = new int[2];
                caseDevant = joueur.retournePositionCible();
                //TODO: vérifié si la case devant est un générateur

                //Recherche dans objetDeplacable s'il y a un objet à prendre
                if (objetsDeplacables[positionJoueur[0]][positionJoueur[1]] != null) {
                    return true;
                }
                return objetsDeplacables[caseDevant[0]][caseDevant[1]] != null;
            }
            case POSER -> {
                // On vérifie si le joueur à quelque chose dans les mains
                if (joueur.getInventaire() == null) {
                    return false;
                }
                // Calcul des coordonnés de la case devant le joueur
                int[] caseDevant = new int[2];
                caseDevant = joueur.retournePositionCible();
                //Recherche dans objetDeplacable s'il y a un objet devant le joueur
                return objetsDeplacables[caseDevant[0]][caseDevant[1]] == null;
                //TODO: Vérifier que la case devant soit compatible avec l'objet à déplacer (ex: pas d'aliment sur le feu sans poele)
            }
            default -> throw new IllegalArgumentException("DonneesJeu.isLegal, action invalide" + a);
        }
    }


    public int getLongueur(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        return br.readLine().length();
    }

    public int getHauteur(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        int hauteur = 0;
        while (br.readLine() != null) {
            hauteur++;
        }
        return hauteur;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public Bloc[][] getObjetsFixes() {
        return objetsFixes;
    }

    public boolean equals(DonneesJeu donneesJeu) {
        if (donneesJeu == null) {
            throw new IllegalArgumentException("DonneesJeu.equals, donneesJeu est null");
        }
        if (donneesJeu == this) {
            return true;
        }
        if (donneesJeu.getClass() != getClass()) {
            throw new IllegalArgumentException("DonneesJeu.equals, donneesJeu n'est pas de la même classe");
        }
        for (int i = 0; i < objetsDeplacables.length; i++) {
            for (int j = 0; j < objetsDeplacables[i].length; j++) {
                if (objetsDeplacables[i][j] == null) {
                    if (donneesJeu.objetsDeplacables[i][j] != null) {
                        return false;
                    }
                } else if (!objetsDeplacables[i][j].equals(donneesJeu.objetsDeplacables[i][j])) {
                    return false;
                }
            }
        }
        for (Joueur joueur : joueurs) {
            int numJoueur = joueur.getNumJoueur();
            if (!joueur.equals(donneesJeu.joueurs.get(numJoueur))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        char[][] res = new char[hauteur][longueur];
        for (int i = 0; i < hauteur; i++) {
            Arrays.fill(res[i], '.');
        }

        for (int i = 0; i < objetsFixes.length; i++) {
            Bloc[] bloc = objetsFixes[i];
            for (int j = 0; j < bloc.length; j++) {
                switch (bloc[j]) {
                    case null -> res[i][j] = SOL;
                    case Depot ignored -> res[bloc[j].getX()][bloc[j].getY()] = DEPOT;
                    case Generateur generateur -> {
                        if (generateur.getAliment() instanceof Salade) {
                            res[bloc[j].getX()][bloc[j].getY()] = GENERATEURSALADE;
                        } else if (generateur.getAliment() instanceof Pain) {
                            res[bloc[j].getX()][bloc[j].getY()] = GENERATEURPAIN;
                        } else if (generateur.getAliment() instanceof Viande) {
                            res[bloc[j].getX()][bloc[j].getY()] = GENERATEURVIANDE;
                        } else if (generateur.getAliment() instanceof Tomate) {
                            res[bloc[j].getX()][bloc[j].getY()] = GENERATEURTOMATE;
                        }
                    }
                    case Planche ignored -> res[bloc[j].getX()][bloc[j].getY()] = PLANCHE;
                    case Poele ignored -> res[bloc[j].getX()][bloc[j].getY()] = POELE;
                    default -> res[bloc[j].getX()][bloc[j].getY()] = PLAN_DE_TRAVAIL;
                }
            }
        }

        for (Joueur joueur : joueurs) {
            res[joueur.getPosition()[0]][joueur.getPosition()[1]] = JOUEUR;
        }

        for (char[] re : res) {
            s.append(Arrays.toString(re)).append("\n");
        }
        return s.toString();
    }
}
