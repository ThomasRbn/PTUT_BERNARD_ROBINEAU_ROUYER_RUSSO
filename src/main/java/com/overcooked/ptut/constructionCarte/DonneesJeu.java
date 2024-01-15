package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.entites.Depot;
import com.overcooked.ptut.entites.Generateur;
import com.overcooked.ptut.entites.PlanDeTravail;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.*;
import com.overcooked.ptut.recettes.etat.Etat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.overcooked.ptut.constructionCarte.CaracteresCarte.*;
import static com.overcooked.ptut.joueurs.utilitaire.Action.*;

public class DonneesJeu {

    //Initialisation des attributs
    private Bloc[][] objetsFixes;
    private int longueur, hauteur;
    private List<Joueur> joueurs;
    private Plat[][] objetsDeplacables;
    private final List<Plat> platsBut;
    private Depot depot;

    /**
     * Constructeur de DonneesJeu
     *
     * @param chemin chemin du fichier texte
     */
    public DonneesJeu(String chemin, boolean... test) {
        platsBut = new ArrayList<>();
        platsBut.add(new Plat(new Tomate()));
        try {
            initialiserDonnees(chemin, test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialiserDonnees(String chemin, boolean... test) throws IOException {
        // ouvrir fichier
        File fichier = new File(chemin);
        FileReader reader = new FileReader(fichier);
        BufferedReader bfRead = new BufferedReader(reader);

        loadLevel(test, fichier, bfRead);
        loadRecipes(bfRead);
    }

    private void loadRecipes(BufferedReader bfRead) {
        platsBut.clear();
        try {
            String ligne = bfRead.readLine();
            while (ligne != null) {
                List<String> recette = new ArrayList<>(List.of(ligne.split(" ")));
                Collections.reverse(recette);
                Aliment currAliment = getCurrentPlatBut(recette);
                platsBut.add(new Plat(currAliment));
                ligne = bfRead.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Aliment getCurrentPlatBut(List<String> recette) {
        Aliment currAliment = new Aliment();
        for (String s : recette) {
            switch (s) {
                case "S" -> currAliment = new Salade();
                case "T" -> currAliment = new Tomate();
                case "P" -> currAliment = new Pain();
                case "V" -> currAliment = new Viande();
                case "C" -> currAliment.setEtat(Etat.COUPE);
                case "R" -> currAliment.setEtat(Etat.CUIT);
                default -> throw new IllegalStateException("Unexpected value: " + s);
            }
        }
        return currAliment;
    }

    private void loadLevel(boolean[] test, File fichier, BufferedReader bfRead) throws IOException {
        joueurs = new ArrayList<>();
        objetsFixes = new Bloc[getHauteur(fichier)][getLongueur(fichier)];

        // lecture des cases
        String ligne = bfRead.readLine();
        longueur = ligne.length();

        // stocke les indices courants
        int indexLigne = 0;

        // parcours le fichier
        while (!ligne.equals("-")) {
            traiterLigne(ligne, indexLigne, test);
            indexLigne++;
            ligne = bfRead.readLine();
        }
        hauteur = indexLigne;

        objetsDeplacables = new Plat[getHauteur()][getLongueur()];
        for (int i = 0; i < joueurs.size(); i++) {
            joueurs.get(i).setNumJoueur(i);
        }
    }

    private void traiterLigne(String ligne, int indexLigne, boolean... test) {
        for (int indexColonne = 0; indexColonne < ligne.length(); indexColonne++) {
            char c = ligne.charAt(indexColonne);
            switch (c) {
                case SOL -> {
                }
                case JOUEUR -> {
                    if (test.length > 0) {
                        joueurs.add(new JoueurHumain(indexLigne, indexColonne));
                        break;
                    }
                    joueurs.add(Creation.CreationJoueur(indexLigne, indexColonne));

                    objetsFixes[indexLigne][indexColonne] = null;
                }
                //Création des objets fixes
                case DEPOT -> {
                    objetsFixes[indexLigne][indexColonne] = new Depot(indexLigne, indexColonne);
                    depot = (Depot) objetsFixes[indexLigne][indexColonne];
                }
                default -> objetsFixes[indexLigne][indexColonne] = Creation.CreationBloc(c, indexColonne, indexLigne);
            }
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
        this.objetsFixes = Copie.CopieObjetFixe(hauteur, longueur, donneesJeu.objetsFixes);

        // Copie des objets déplacables
        this.objetsDeplacables = Copie.CopieObjetDeplacables(hauteur, longueur, donneesJeu.objetsDeplacables);

        // Copie des joueurs
        this.joueurs = Copie.CopieJoueurs(donneesJeu.joueurs);
    }


    public List<int[]> getCoordonneesElement(String element) {
        System.out.println(element);
        switch (element) {
            case "Tomate":
                List<int[]> coordonneesTomates = new ArrayList<>();
                for (int i = 0; i < hauteur; i++) {
                    for (int j = 0; j < longueur; j++) {
                        if (objetsFixes[i][j] instanceof Generateur && ((Generateur) objetsFixes[i][j]).getAliment().getRecettesComposees().get(0) instanceof Tomate) {
                            coordonneesTomates.add(new int[]{i, j});
                        } else if (objetsFixes[i][j] instanceof PlanDeTravail) {
                            PlanDeTravail planDeTravail = ((PlanDeTravail) objetsFixes[i][j]);
                            if (planDeTravail.getInventaire() != null && planDeTravail.getInventaire().getRecettesComposees().get(0) instanceof Tomate)
                                coordonneesTomates.add(new int[]{i, j});
                        }
                        if (objetsDeplacables[i][j] != null) {
                            if (objetsDeplacables[i][j].getRecettesComposees().get(0) instanceof Tomate) {
                                coordonneesTomates.add(new int[]{i, j});
                            }
                        }
                    }
                }
                return coordonneesTomates;

            case "Salade":
                List<int[]> coordonneesSalades = new ArrayList<>();
                for (int i = 0; i < hauteur; i++) {
                    for (int j = 0; j < longueur; j++) {
                        if (objetsFixes[i][j] instanceof Generateur && ((Generateur) objetsFixes[i][j]).getAliment().getRecettesComposees().get(0) instanceof Salade) {
                            coordonneesSalades.add(new int[]{i, j});
                        } else if (objetsFixes[i][j] instanceof PlanDeTravail) {
                            PlanDeTravail planDeTravail = ((PlanDeTravail) objetsFixes[i][j]);
                            if (planDeTravail.getInventaire() != null && planDeTravail.getInventaire().getRecettesComposees().get(0) instanceof Salade)
                                coordonneesSalades.add(new int[]{i, j});
                        }
                        if (objetsDeplacables[i][j] != null) {
                            if (objetsDeplacables[i][j].getRecettesComposees().get(0) instanceof Salade) {
                                coordonneesSalades.add(new int[]{i, j});
                            }
                        }
                    }
                }
                return coordonneesSalades;

            case "Plan de travail":
                List<int[]> coordonneesPlanDeTravail = new ArrayList<>();
                for (int i = 0; i < hauteur; i++) {
                    for (int j = 0; j < longueur; j++) {
                        if (objetsFixes[i][j] instanceof PlanDeTravail) {
                            coordonneesPlanDeTravail.add(new int[]{i, j});
                        }
                    }
                }
                return coordonneesPlanDeTravail;

                case "Generateur":
                List<int[]> coordonneesGenerateur = new ArrayList<>();
                for (int i = 0; i < hauteur; i++) {
                    for (int j = 0; j < longueur; j++) {
                        if (objetsFixes[i][j] instanceof Generateur) {
                            coordonneesGenerateur.add(new int[]{i, j});
                        }
                    }
                }
                return coordonneesGenerateur;

                case "Cuisson":
                List<int[]> coordonneesPoele = new ArrayList<>();
                for (int i = 0; i < hauteur; i++) {
                    for (int j = 0; j < longueur; j++) {
                        if (objetsFixes[i][j] instanceof Poele) {
                            coordonneesPoele.add(new int[]{i, j});
                        }
                    }
                }
                return coordonneesPoele;

                case "Planche":
                List<int[]> coordonneesPlanche = new ArrayList<>();
                for (int i = 0; i < hauteur; i++) {
                    for (int j = 0; j < longueur; j++) {
                        if (objetsFixes[i][j] instanceof Planche) {
                            coordonneesPlanche.add(new int[]{i, j});
                        }
                    }
                }
                return coordonneesPlanche;

                case "Depot":
                List<int[]> coordonneesDepot = new ArrayList<>();
                for (int i = 0; i < hauteur; i++) {
                    for (int j = 0; j < longueur; j++) {
                        if (objetsFixes[i][j] instanceof Depot) {
                            coordonneesDepot.add(new int[]{i, j});
                        }
                    }
                }
                return coordonneesDepot;

            default:
                throw new IllegalArgumentException("DonneesJeu.getCoordonneesAliment, Aliment pas encore implémenté");
        }
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
                if (bloc[j] == null) {
                    res[i][j] = SOL;
                } else {
                    res[bloc[j].getX()][bloc[j].getY()] = switch (bloc[j]) {
                        case Depot ignored -> DEPOT;
                        //On regarde le type de générateur pour l'afficher
                        case Generateur generateur -> switch (generateur.getType()) {
                            case "Salade" -> GENERATEURSALADE;
                            case "Pain" -> GENERATEURPAINBURGER;
                            case "Viande" -> GENERATEURVIANDE;
                            case "Tomate" -> GENERATEURTOMATE;
                            default ->
                                    throw new IllegalStateException("DonneesJeu.toString type inconnu: " + generateur.getType());
                        };
                        case Planche ignored -> PLANCHE;
                        case Poele ignored -> POELE;
                        default -> PLAN_DE_TRAVAIL;
                    };
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
     * Retourne le dépot
     *
     * @return
     */
    public Depot getDepot() {
        return depot;
    }

    public Plat[][] getObjetsDeplacables() {
        return objetsDeplacables;
    }
}
