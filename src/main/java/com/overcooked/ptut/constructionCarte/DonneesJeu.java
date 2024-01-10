package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
import com.overcooked.ptut.entites.PlanDeTravail;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;

public class DonneesJeu {

    // Ensemble des caractères utilisés dans le fichier texte
    public static final char PLAN_DE_TRAVAIL = 'X';
    public static final char JOUEUR = 'J';
    public static final char SOL = '.';
    public static final char DEPOT = 'D';
    public static final char PLANCHE = 'C';
    public static final char POELE = 'P';
    public static final char GENERATEURTOMATE = 'T';
    public static final char GENERATEURPAINBURGER = 'B';
    public static final char GENERATEURSALADE = 'S';
    public static final char GENERATEURVIANDE = 'V';

    //Initialisation des attributs
    private Bloc[][] objetsFixes;
    private int longueur, hauteur;
    private List<Joueur> joueurs;
    private Plat[][] objetsDeplacables;
    private final List<Plat> platsBut;

    /**
     * Constructeur de DonneesJeu
     *
     * @param chemin chemin du fichier texte
     */
    public DonneesJeu(String chemin, boolean... test) {
        platsBut = new ArrayList<>();
        Plat saladePain = new Plat("Tomate", new Tomate());
        platsBut.add(saladePain);
        try {
            // ouvrir fichier
            File fichier = new File(chemin);
            FileReader reader = new FileReader(fichier);
            BufferedReader bfRead = new BufferedReader(reader);

            joueurs = new ArrayList<>();
            objetsFixes = new Bloc[getHauteur(fichier)][getLongueur(fichier)];

            // lecture des cases
            String ligne = bfRead.readLine();
            longueur = ligne.length();

            // stocke les indices courants
            int indexColonne = 0;
            int indexLigne = 0;

            // parcours le fichier
            while (ligne != null) {
                for (indexColonne = 0; indexColonne < ligne.length(); indexColonne++) {
                    char c = ligne.charAt(indexColonne);
                    switch (c) {
                        case SOL:
                            break;
                        case PLAN_DE_TRAVAIL:
                            objetsFixes[indexLigne][indexColonne] = new PlanDeTravail(indexLigne, indexColonne);
                            break;
                        case DEPOT:
                            objetsFixes[indexLigne][indexColonne] = new Depot(indexLigne, indexColonne);
                            break;
                        case JOUEUR:
                            if (test.length > 0) {
                                joueurs.add(new JoueurHumain(indexLigne, indexColonne));
                                break;
                            }
                            // Demande le type de joueur
                            Scanner sc = new Scanner(System.in);
                            System.out.println("Entrez le type de joueur (H, IA)");
                            String choix = "";
                            boolean estConforme = false;
                            while (!estConforme) {
                                choix = sc.nextLine();
                                if (choix.equalsIgnoreCase("H") || choix.equalsIgnoreCase("IA")) {
                                    estConforme = true;
                                } else {
                                    System.out.println("Entrée invalide : " + choix + " (H, IA)");
                                }
                            }

                            // Création du joueur
                            if (choix.equalsIgnoreCase("H")) {
                                joueurs.add(new JoueurHumain(indexLigne, indexColonne));
                            } else {
                                joueurs.add(new JoueurIA(indexLigne, indexColonne));
                            }
                            objetsFixes[indexLigne][indexColonne] = null;
                            break;

                        //Création des objets fixes
                        case GENERATEURSALADE:
                            objetsFixes[indexLigne][indexColonne] = new Generateur(indexLigne, indexColonne, new Plat("Salade", new Salade()));
                            break;
                        case GENERATEURTOMATE:
                            objetsFixes[indexLigne][indexColonne] = new Generateur(indexLigne, indexColonne, new Plat("Tomate", new Tomate()));
                            break;
                        case GENERATEURPAINBURGER:
                            objetsFixes[indexLigne][indexColonne] = new Generateur(indexLigne, indexColonne, new Plat("Pain", new Pain()));
                            break;
                        case PLANCHE:
                            objetsFixes[indexLigne][indexColonne] = new Planche(indexLigne, indexColonne);
                            break;
                        case POELE:
                            objetsFixes[indexLigne][indexColonne] = new Poele(indexLigne, indexColonne);
                            break;
                        // Exception si le caractère lu est inconnu
                        default:
                            throw new IllegalArgumentException("DonneesJeu.constructeur, caractère inconnu : " + c);
                    }
                }
                indexLigne++;
                ligne = bfRead.readLine();
            }
            hauteur = indexLigne;

            objetsDeplacables = new Plat[getHauteur()][getLongueur()];
            for (int i = 0; i < joueurs.size(); i++) {
                joueurs.get(i).setNumJoueur(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Constructeur par copie de DonneesJeu
     *
     * @param donneesJeu DonneesJeu à copier
     */
    public DonneesJeu(DonneesJeu donneesJeu) {
        platsBut = new ArrayList<>();
        // Copie des plats but
        for (Plat plat : donneesJeu.platsBut) {
            platsBut.add(new Plat(plat));
        }
        // Copie des objets fixes
        this.longueur = donneesJeu.longueur;
        this.hauteur = donneesJeu.hauteur;
        this.objetsFixes = new Bloc[hauteur][longueur];
        for (int i = 0; i < donneesJeu.objetsFixes.length; i++) {
            for (int j = 0; j < donneesJeu.objetsFixes[i].length; j++) {
                if (donneesJeu.objetsFixes[i][j] != null) {
                    switch (donneesJeu.objetsFixes[i][j]) {
                        case Depot depot -> this.objetsFixes[i][j] = new Depot(depot);
                        case Generateur generateur -> this.objetsFixes[i][j] = new Generateur(generateur);
                        case Planche planche -> this.objetsFixes[i][j] = new Planche(planche);
                        case Poele poele -> this.objetsFixes[i][j] = new Poele(poele);
                        case null, default -> this.objetsFixes[i][j] = new Bloc(donneesJeu.objetsFixes[i][j]);
                    }
                }
            }
        }

        // Copie des objets déplacables
        this.objetsDeplacables = new Plat[hauteur][longueur];
        for (int i = 0; i < donneesJeu.objetsDeplacables.length; i++) {
            for (int j = 0; j < donneesJeu.objetsDeplacables[i].length; j++) {
                if (donneesJeu.objetsDeplacables[i][j] != null) {
                    objetsDeplacables[i][j] = donneesJeu.objetsDeplacables[i][j];
                }
            }
        }

        // Copie des joueurs
        this.joueurs = new ArrayList<>();
        for (Joueur joueur : donneesJeu.joueurs) {
            if (joueur instanceof JoueurIA) {
                if (joueur.getInventaire() != null) {
                    this.joueurs.add(new JoueurIA(joueur.getPosition()[0], joueur.getPosition()[1], joueur.getCloneInventaire(), joueur.getDirection(), joueur.getNumJoueur()));
                } else {
                    this.joueurs.add(new JoueurIA(joueur.getPosition()[0], joueur.getPosition()[1], null, joueur.getDirection(), joueur.getNumJoueur()));
                }
            } else {
                if (joueur.getInventaire() != null) {
                    this.joueurs.add(new JoueurHumain(joueur.getPosition()[0], joueur.getPosition()[1], joueur.getCloneInventaire(), joueur.getDirection(), joueur.getNumJoueur()));
                } else {
                    this.joueurs.add(new JoueurHumain(joueur.getPosition()[0], joueur.getPosition()[1], null, joueur.getDirection(), joueur.getNumJoueur()));
                }
            }
        }
    }

    /**
     * Methode permettant de faire une action
     *
     * @param a
     * @param numJoueur
     */
    public void faireAction(Action a, int numJoueur) {
        Joueur joueur = joueurs.get(numJoueur);
        joueur.changeDirection(a);
        int[] positionJoueurCible = joueur.getPositionCible();
        switch (a) {
            //Deplacement du joueur si possible
            case DROITE, GAUCHE, HAUT, BAS -> {
                if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] == null) {
                    joueur.deplacer(a);
                }
            }
            //Prendre un objet
            case PRENDRE -> {
                // Premier cas avec la position du joueur (sous le joueur, car il est possible de chevaucher un objet)
                prendre(joueur);
            }
            //Poser un objet (dans la case devant le joueur)
            case POSER -> {
                if (objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]] instanceof Depot) {
                    if (joueur.getInventaire() != null) {
                        Depot depot = (Depot) objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]];
                        depot.deposerPlat(joueur.poser());
                        return;
                    }
                }
                objetsDeplacables[joueur.getPosition()[0]][joueur.getPosition()[1]] = joueur.poser();
            }
            //Exception si l'action n'est pas reconnue
            default -> throw new IllegalArgumentException("DonneesJeu.faireAction, action invalide" + a);
        }
    }

    /**
     * Methode permettant de prendre un objet
     *
     * @param joueur
     */
    private void prendre(Joueur joueur) {
        // Position cible du joueur en fonction de sa direction
        int[] positionJoueurCible = joueur.getPositionCible();
//        System.out.println(Arrays.toString(joueur.getPosition()));
        Plat objetsDeplacableCible = objetsDeplacables[positionJoueurCible[0]][positionJoueurCible[1]];
        if (objetsDeplacableCible != null) {
            joueur.prendre(objetsDeplacableCible);
            objetsDeplacables[positionJoueurCible[0]][positionJoueurCible[1]] = null;
            return;
        }

        // Position du joueur (blocs mouvables)
        Bloc objetsFix = objetsFixes[positionJoueurCible[0]][positionJoueurCible[1]];
        if (objetsFix instanceof Generateur) {
            joueur.prendre(((Generateur) objetsFix).getAliment());
        }

        //Prendre un objet déjà présent sur la carte
        int[] positionJoueur = joueur.getPosition();
        Plat objetsDeplacable = objetsDeplacables[positionJoueur[0]][positionJoueur[1]];
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
                if (joueur.getInventaire() != null) {
                    return false;
                }

                // Calcul des coordonnes de la case devant le joueur
                int[] caseDevant = new int[2];
                caseDevant = joueur.getPositionCible();
                //TODO: vérifié si la case devant est un générateur

                //Recherche dans objetDeplacable s'il y a un objet à prendre
                return objetsDeplacables[caseDevant[0]][caseDevant[1]] != null
                        || objetsDeplacables[positionJoueur[0]][positionJoueur[1]] != null
                        || objetsFixes[caseDevant[0]][caseDevant[1]] instanceof Generateur;
            }

            case POSER -> {
                // On vérifie si le joueur à quelque chose dans les mains
                if (joueur.getInventaire() == null) {
                    return false;
                }
                // Calcul des coordonnés de la case devant le joueur
                int[] caseDevant = new int[2];
                caseDevant = joueur.getPositionCible();
                //Recherche dans objetDeplacable s'il y a un objet devant le joueur
                return objetsDeplacables[caseDevant[0]][caseDevant[1]] == null;
                //TODO: Vérifier que la case devant soit compatible avec l'objet à déplacer (ex: pas d'aliment sur le feu sans poele)
            }
            //Exception si l'action n'est pas reconnue
            default -> throw new IllegalArgumentException("DonneesJeu.isLegal, action invalide" + a);
        }
    }


    /**
     * Retourne la longueur du fichier (taille des lignes)
     *
     * @param f
     * @return
     * @throws IOException
     */
    public int getLongueur(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        return br.readLine().length();
    }

    /**
     * Retourne la hauteur du fichier (nombre de lignes)
     *
     * @param f
     * @return
     * @throws IOException
     */
    public int getHauteur(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        int hauteur = 0;
        while (br.readLine() != null) {
            hauteur++;
        }
        return hauteur;
    }

    /**
     * Retourne la liste des joueurs
     *
     * @return
     */
    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public Joueur getJoueur(int numJoueur) {
        return joueurs.get(numJoueur);
    }

    /**
     * Retourne la longueur de la carte
     *
     * @return
     */
    public int getLongueur() {
        return longueur;
    }

    /**
     * Retourne la hauteur de la carte
     *
     * @return
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Retourne la liste des blocs
     *
     * @return
     */
    public Bloc[][] getObjetsFixes() {
        return objetsFixes;
    }

    /**
     * Retourne les plats but
     *
     * @return
     */
    public List<Plat> getPlatsBut() {
        return platsBut;
    }

    /**
     * Retourne vrai si deux jeux de données sont strictement identiques
     *
     * @param donneesJeu
     * @return
     */
    public boolean equals(DonneesJeu donneesJeu) {
        //Lève une exception si le jeu de données est null
        if (donneesJeu == null) {
            throw new IllegalArgumentException("DonneesJeu.equals, donneesJeu est null");
        }

        //Retourne vrai si les deux jeux de données sont la même instance
        if (donneesJeu == this) {
            return true;
        }

        // Lève une exception si les deux jeux de données n'ont pas la même classe
        if (donneesJeu.getClass() != getClass()) {
            throw new IllegalArgumentException("DonneesJeu.equals, donneesJeu n'est pas de la même classe");
        }

        //On parcours l'ensemble des objets deplacables
        for (int i = 0; i < objetsDeplacables.length; i++) {
            for (int j = 0; j < objetsDeplacables[i].length; j++) {
                if (objetsDeplacables[i][j] == null) {
                    //Si l'objet est null, on vérifie que l'objet deplacable de l'autre jeu de données est null
                    //Si ce n'est pas le cas, on retourne faux
                    if (donneesJeu.objetsDeplacables[i][j] != null) {
                        return false;
                    }

                    //On verifie que les deux objets deplacables sont identiques
                } else if (!objetsDeplacables[i][j].equals(donneesJeu.objetsDeplacables[i][j])) {
                    return false;
                }
            }
        }

        //On parcours l'ensemble des joueurs
        for (Joueur joueur : joueurs) {
            int numJoueur = joueur.getNumJoueur();
            //Si joueur différent, on retourne faux
            if (!joueur.equals(donneesJeu.joueurs.get(numJoueur))) {
                return false;
            }
        }
        return this.getPlatDepose().size() == donneesJeu.getPlatDepose().size();
    }


    /**
     * Méthode permettant de récupérer les coordonnées des tomates et des générateur de tomates
     *
     * @return liste de coordonnees des tomates et des générateur de tomates
     */
    public List<int[]> getCoordonneesTomates() {
        List<int[]> coordonneesTomates = new ArrayList<>();
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < longueur; j++) {
                if (objetsFixes[i][j] instanceof Generateur && ((Generateur) objetsFixes[i][j]).getAliment().getRecettesComposees().get(0) instanceof Tomate) {
                    coordonneesTomates.add(new int[]{i, j});
                }
            }
        }
        return coordonneesTomates;
    }

    public List<int[]> getCoordonneesAliment(Aliment aliment){
        switch (aliment.getNom()){
            case "Tomate":
                List<int[]> coordonneesTomates = new ArrayList<>();
                for (int i = 0; i < hauteur; i++) {
                    for (int j = 0; j < longueur; j++) {
                        if (objetsFixes[i][j] instanceof Generateur && ((Generateur) objetsFixes[i][j]).getAliment().getRecettesComposees().get(0) instanceof Tomate) {
                            coordonneesTomates.add(new int[]{i, j});
                        }
                    }
                }
                return coordonneesTomates;
            default:
                throw new IllegalArgumentException("DonneesJeu.getCoordonneesAliment, Aliment pas encore implémenté");
        }
    }

    /**
     * Méthode permettant de récupérer les coordonnées du dépot
     *
     * @return oordonnees du dépot
     */
    public int[] getCoordonneesDepot() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < longueur; j++) {
                if (objetsFixes[i][j] instanceof Depot) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public List<Plat> getPlatDepose() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < longueur; j++) {
                if (objetsFixes[i][j] instanceof Depot) {
                    return ((Depot) objetsFixes[i][j]).getPlatsDeposes();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        char[][] res = new char[hauteur][longueur];
        for (int i = 0; i < hauteur; i++) {
            Arrays.fill(res[i], '.');
        }

        //On récupère l'ensemble des objets fixes
        for (int i = 0; i < objetsFixes.length; i++) {
            Bloc[] bloc = objetsFixes[i];
            for (int j = 0; j < bloc.length; j++) {
                switch (bloc[j]) {
                    case null -> res[i][j] = SOL;
                    case Depot ignored -> res[bloc[j].getX()][bloc[j].getY()] = DEPOT;
                    //On regarde le type de générateur pour l'afficher
                    case Generateur generateur -> {
                        if (generateur.getAliment().getNom().equals("salade")) {
                            res[bloc[j].getX()][bloc[j].getY()] = GENERATEURSALADE;
                        } else if (generateur.getAliment().getNom().equals("pain")) {
                            res[bloc[j].getX()][bloc[j].getY()] = GENERATEURPAINBURGER;
                        } else if (generateur.getAliment().getNom().equals("viande")) {
                            res[bloc[j].getX()][bloc[j].getY()] = GENERATEURVIANDE;
                        } else if (generateur.getAliment().getNom().equals("tomate")) {
                            res[bloc[j].getX()][bloc[j].getY()] = GENERATEURTOMATE;
                        }
                    }
                    case Planche ignored -> res[bloc[j].getX()][bloc[j].getY()] = PLANCHE;
                    case Poele ignored -> res[bloc[j].getX()][bloc[j].getY()] = POELE;
                    default -> res[bloc[j].getX()][bloc[j].getY()] = PLAN_DE_TRAVAIL;
                }
            }
        }

        //On récupère l'ensemble des joueurs
        for (Joueur joueur : joueurs) {
            res[joueur.getPosition()[0]][joueur.getPosition()[1]] = JOUEUR;
        }

        for (char[] re : res) {
            s.append(Arrays.toString(re)).append("\n");
        }

        //On affiche la direction de chaque joueur
        for (Joueur joueur : joueurs) {
            s.append("Le joueur ").append(joueur.getNumJoueur()).append(" est orienté vers : ").append(joueur.getDirection().getName()).append("\n");
        }
        return s.toString();
    }

}
