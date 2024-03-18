package com.overcooked.ptut.stats.export;

import java.io.FileWriter;
import java.io.IOException;

public class TableauDoubleEntreeCSV {

    public static void main(String[] args) {
        // Données du tableau à double entrée
        String[][] tableau = {
                {"Nom", "Âge", "Genre"},
                {"Alice", "30", "Femme"},
                {"Bob", "25", "Homme"},
                {"Charlie", "40", "Homme"},
                {"Diana", "35", "Femme"}
        };

        // Chemin du fichier CSV de sortie
        String fichierSortie = "tableau_double_entree.csv";

        // Création et écriture dans le fichier CSV
        try {
            FileWriter writer = new FileWriter(fichierSortie);

            for (String[] ligne : tableau) {
                for (int i = 0; i < ligne.length; i++) {
                    writer.append(ligne[i]);
                    if (i < ligne.length - 1) {
                        writer.append(';');
                    }
                }
                writer.append('\n');
            }

            writer.flush();
            writer.close();

            System.out.println("Le fichier CSV a été créé avec succès : " + fichierSortie);
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier CSV : " + e.getMessage());
        }
    }
}
