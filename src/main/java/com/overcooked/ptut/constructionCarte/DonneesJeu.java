package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.controlleurs.ActionsDuTour;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.JoueurHumain;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Depot;
import com.overcooked.ptut.objet.Generateur;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Tomate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.overcooked.ptut.constructionCarte.CaracteresCarte.*;
import static com.overcooked.ptut.constructionCarte.Creation.getCurrentPlatBut;

public class DonneesJeu {

    private final List<Plat> platsBut;
    //Initialisation des attributs
    private Bloc[][] objetsFixes;
    private int longueur, hauteur;
    private List<Joueur> joueurs;
    private Plat[][] objetsDeplacables;
    private Depot depot;

    private ActionsDuTour actionsDuTour;
    private boolean jeuTermine;
    private List<int[]> coordonneesJoueurs;

    /**
     * Constructeur de DonneesJeu
     *
     * @param chemin chemin du fichier texte
     */
    public DonneesJeu(String chemin, boolean... test) {
        platsBut = new ArrayList<>();
        platsBut.add(new Plat(new Tomate()));
        coordonneesJoueurs = new ArrayList<>();
        try {
            initialiserDonnees(chemin, test);
        } catch (IOException e) {
            e.printStackTrace();
        }
        actionsDuTour = new ActionsDuTour(this);
    }

    /**
     * Initialise les données du jeu
     *
     * @param chemin chemin du fichier texte
     * @throws IOException
     */
    private void initialiserDonnees(String chemin, boolean... test) throws IOException {
        // ouvrir fichier
        File fichier = new File(chemin);
        FileReader reader = new FileReader(fichier);
        BufferedReader bfRead = new BufferedReader(reader);

        loadLevel(test, fichier, bfRead);
        loadRecipes(bfRead);
    }

    /**
     * Charge les recettes
     *
     * @param bfRead BufferedReader du fichier
     */
    private void loadRecipes(BufferedReader bfRead) {
        platsBut.clear();
        try {
            String ligne = bfRead.readLine();
            while (ligne != null) {
                String[] plat = ligne.split(", ");
                Plat currPlat = new Plat();
                for (String s : plat) {
                    List<String> recette = new ArrayList<>(List.of(s.split(" ")));
                    Aliment currAliment = getCurrentPlatBut(recette);
                    currPlat.ajouterAliment(currAliment);
                }
                platsBut.add(currPlat);
                ligne = bfRead.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge le niveau complet
     *
     * @param test
     * @param fichier
     * @param bfRead
     * @throws IOException
     */
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
        if (test.length > 0) {
            for (int i = 0; i < joueurs.size(); i++) {
                joueurs.get(i).setNumJoueur(i);
            }
        }
    }

    /**
     * Traite une ligne du fichier pour la charger dans les données du jeu
     *
     * @param ligne
     * @param indexLigne
     * @param test
     */
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
                    } else {
                        coordonneesJoueurs.add(new int[]{indexLigne, indexColonne});
                    }
                    objetsFixes[indexLigne][indexColonne] = null;
                }
                //Création des objets fixes
                case DEPOT -> {
                    objetsFixes[indexLigne][indexColonne] = new Depot(indexLigne, indexColonne, platsBut);
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

    /**
     * Retourne les coordonnées des éléments correspondant au paramètre
     *
     * @param element String correspondant au nom de l'élément
     * @return Liste de coordonnées
     */
    public List<int[]> getCoordonneesElement(String element) {
        List<int[]> coordonneesElem = new ArrayList<>();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < longueur; j++) {
                Bloc currBloc = objetsFixes[i][j];

                //On regarde si le bloc est accessible
                if ((estPasAccessible(i, j))) continue;


                if (currBloc instanceof PlanDeTravail planDeTravail && planDeTravail.getNomPlat().equals(element)) {
                    coordonneesElem.add(new int[]{i, j});
                }
                if (currBloc instanceof Transformateur transformateur) {
                    if (transformateur.getInventaire() == null && transformateur.getType().equals(element)) {
                        coordonneesElem.add(new int[]{i, j});
                        continue;
                    } else if (transformateur.getNomPlat().equals(element))
                        coordonneesElem.add(new int[]{i, j});
                }
                if (currBloc != null) {
                    if (currBloc instanceof Generateur) {
                        if (element.equals("Generateur")) {
                            coordonneesElem.add(new int[]{i, j});
                        }
                    }
                    if (currBloc.getType().equals(element)) {
                        coordonneesElem.add(new int[]{i, j});
                    }
                }
                Plat objetsDeplacable = objetsDeplacables[i][j];
                if (objetsDeplacable != null) {
                    if (objetsDeplacable.getNom().equals(element)) {
                        coordonneesElem.add(new int[]{i, j});
                    }
                }

                // Trouver un joueur
                for (Joueur joueur : joueurs) {
                    int[] position = joueur.getPosition();
                    if (position[0] == i && position[1] == j) {
                        String numJoueur = joueur.getNumJoueur() + "";
                        if (element.equals(numJoueur)) {
                            coordonneesElem.add(new int[]{i, j});
                        }
                    }
                }
            }
        }
        return coordonneesElem;
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
            if (joueur.getInventaire() != null) {
                s.append("Et possède dans son inventaire: ");
                for (Aliment aliment : joueur.getInventaire().getRecettesComposees()) {
                    s.append(aliment);
                }
                s.append("\n");
            }
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
     * Retourne les coordonnées du plan de travail vide le plus proche
     *
     * @param position
     * @return
     */
    public int[] getPlanDeTravailVidePlusProche(int[] position) {
        int[] planDeTravailVide = new int[2];
        int distanceMin = Integer.MAX_VALUE;
        for (int i = 0; i < objetsFixes.length; i++) {
            for (int j = 0; j < objetsFixes[i].length; j++) {
                if (objetsFixes[i][j] instanceof PlanDeTravail planDeTravail) {
                    if (planDeTravail.getInventaire() == null) {
                        int distance = Math.abs(position[0] - i) + Math.abs(position[1] - j);
                        if (distance < distanceMin) {
                            distanceMin = distance;
                            planDeTravailVide[0] = i;
                            planDeTravailVide[1] = j;
                        }
                    }
                }
            }
        }
        return planDeTravailVide;
    }

    public boolean estPasAccessible(int i, int j){
        return ((i == 0 && j == 0 && j + 1 < longueur && objetsFixes[i][j + 1] != null && i + 1 < hauteur && objetsFixes[i + 1][j] != null)
                || (i == 0 && j == longueur - 1 && j - 1 >= 0 && objetsFixes[i][j - 1] != null && i + 1 < hauteur && objetsFixes[i + 1][j] != null)
                || (i == hauteur - 1 && j == 0 && j + 1 < longueur && objetsFixes[i][j + 1] != null && i - 1 >= 0 && objetsFixes[i - 1][j] != null)
                || (i == 0 && j < longueur - 1 && j + 1 < longueur && objetsFixes[i][j + 1] != null && i + 1 < hauteur && objetsFixes[i + 1][j] != null && objetsFixes[i][j - 1] != null)
                || (i < hauteur - 1 && j == 0 && j + 1 < longueur && objetsFixes[i][j + 1] != null && i + 1 < hauteur && objetsFixes[i + 1][j] != null && objetsFixes[i - 1][j] != null)
                || (i == hauteur - 1 && j < longueur - 1 && j + 1 < longueur && objetsFixes[i][j + 1] != null && i - 1 >= 0 && objetsFixes[i - 1][j] != null && j - 1 >= 0 && objetsFixes[i][j - 1] != null)
                || (i < hauteur - 1 && j == longueur - 1 && j - 1 >= 0 && objetsFixes[i][j - 1] != null && i + 1 < hauteur && objetsFixes[i + 1][j] != null && i - 1 >= 0 && objetsFixes[i - 1][j] != null)
                || (i < hauteur - 1 && j < longueur - 1 && j + 1 < longueur && objetsFixes[i][j + 1] != null && i + 1 < hauteur && objetsFixes[i + 1][j] != null && j - 1 >= 0 && objetsFixes[i][j - 1] != null && i - 1 >= 0 && objetsFixes[i - 1][j] != null));
    }

    /**
     * Retourne la longueur du fichier (taille des lignes)
     */
    public int getLongueur(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        return br.readLine().length();
    }

    /**
     * Retourne la hauteur du fichier (nombre de lignes)
     */
    public int getHauteur(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        int hauteur = 0;
        String ligne = br.readLine();
        while (ligne != null && !ligne.equals("-")) {
            hauteur++;
            ligne = br.readLine();
        }
        return hauteur;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public Joueur getJoueur(int numJoueur) {
        return joueurs.get(numJoueur);
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

    public List<Plat> getPlatsBut() {
        return platsBut;
    }

    public Depot getDepot() {
        return depot;
    }

    public ActionsDuTour getActionsDuTour() {
        return actionsDuTour;
    }

    public Plat[][] getObjetsDeplacables() {
        return objetsDeplacables;
    }

    public boolean isJeuTermine() {
        return jeuTermine;
    }

    public DonneesJeu setJeuTermine(boolean jeuTermine) {
        this.jeuTermine = jeuTermine;
        return this;
    }

    public List<int[]> getCoordonneesJoueurs() {
        return coordonneesJoueurs;
    }
}
