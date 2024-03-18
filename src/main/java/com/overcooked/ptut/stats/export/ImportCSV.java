package com.overcooked.ptut.stats.export;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportCSV {

    public static void main(String[] args) {
        // Chemin du fichier CSV d'entrée
        String fichierEntree = "tableau_double_entree.csv";

        // Appel de la méthode pour lire le fichier CSV
        String[][] tableau = lireCSV(fichierEntree);

        System.out.println(Arrays.deepToString(tableau));

        // Affichage des données
        if (tableau != null) {
            for (String[] ligne : tableau) {
                for (String cellule : ligne) {
                    System.out.print(cellule + "\t");
                }
                System.out.println();
            }
        } else {
            System.out.println("Le fichier CSV est vide ou n'a pas pu être lu.");
        }
    }

    public static String[][] lireCSV(String cheminFichier) {
        List<String[]> lignes = new ArrayList<>();
        String ligne;

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            while ((ligne = br.readLine()) != null) {
                String[] cellules = ligne.split(";");
                lignes.add(cellules);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            return null;
        }

        // Conversion de la liste en tableau à double entrée
        String[][] tableau = new String[lignes.size()][];
        for (int i = 0; i < lignes.size(); i++) {
            tableau[i] = lignes.get(i);
        }

        return tableau;
    }
}
