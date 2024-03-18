package com.overcooked.ptut.stats.export;

import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.stats.DonneesStats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.overcooked.ptut.MainCollecteStats.DUREE_PARTIE;

public class ExportCSV {

    public static void exportCSV(DonneesStats donneesStats) throws IOException {
//        File fichier = new File("stats.csv");
//        if (fichier.createNewFile()) {
//            System.out.println("File created: " + fichier.getName());
//            FileWriter writer = new FileWriter(fichier);
//            String entete = "Strategie;Joueur1;Joueur2;Points";
//            writer.write(entete + "\n");
//            writer.close();
//        } else {
//            System.out.println("File already exists.");
//        }
//        FileWriter writer = new FileWriter(fichier, true);
//        String ligne = donneesStats.getStrategieCollecte().getClass().getSimpleName() + ";" + donneesStats.getCombinaison().j1().getSimpleName() + ";" + donneesStats.getCombinaison().j2().getSimpleName() + ";" + donneesStats.getDonneesJeu().getDepot().getPoints();

        String[][] tableau;
        System.out.println(donneesStats.getDonneesJeu().getNomFichier().split("/")[2]);
        String fichierSortie = "Stats_" + donneesStats.getStrategieCollecte().getClass().getSimpleName() + "_" + donneesStats.getDonneesJeu().getNomFichier().split("/")[2].split("\\.")[0] + ".csv";
        File fichier = new File(fichierSortie);
        if (fichier.createNewFile()) {
            tableau = new String[][]{{"Points/" + DUREE_PARTIE + "s", "IA", "IADecentr", "IADecentrV2", "Automate"}, {"IA", "0", "0", "0", "0"}, {"IADecentr", "0", "0", "0", "0"}, {"IADecentrV2", "0", "0", "0", "0"}, {"Automate", "0", "0", "0", "0"}};
        } else {
            tableau = ImportCSV.lireCSV(fichierSortie);
        }

        assert tableau != null;
        tableau[getCellule(donneesStats.getCombinaison().j1())][getCellule(donneesStats.getCombinaison().j2())] = String.valueOf(donneesStats.getDonneesJeu().getDepot().getPoints());

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

    public static int getCellule(Class<? extends JoueurIA> j) {
        return switch (j.getSimpleName()) {
            case "JoueurIA" -> 1;
            case "JoueurIADecentr" -> 2;
            case "JoueurIADecentrV2" -> 3;
            default -> 4;
        };
    }

}
