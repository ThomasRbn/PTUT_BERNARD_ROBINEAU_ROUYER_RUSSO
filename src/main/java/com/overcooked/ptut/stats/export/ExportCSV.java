package com.overcooked.ptut.stats.export;

import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.stats.Duo;
import com.overcooked.ptut.stats.strategieCollecte.StrategieCollecte;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.overcooked.ptut.MainCollecteStats.DUREE_PARTIE;

public class ExportCSV {

    public static void exportCSV(String fichierOrigine, Class<? extends StrategieCollecte> collecte, String contexte, Map<Duo, Set<Integer>> stats) throws IOException {
        String[][] tableau;
        String fichierSortie = "stats/Stats_" + contexte + "_" + collecte.getSimpleName() + "_" + fichierOrigine.split("/")[2].split("\\.")[0] + ".csv";
        File fichier = new File(fichierSortie);
        if (fichier.createNewFile()) {
            tableau = new String[][]{{contexte + "/" + DUREE_PARTIE + "s", "IA", "IADecentr", "IADecentrV2", "IADecentrV3", "Automate"}, {"IA", "0", "0", "0", "0", "0"}, {"IADecentr", "0", "0", "0", "0", "0"}, {"IADecentrV2", "0", "0", "0", "0", "0"}, {"IADecentrV3", "0", "0", "0", "0", "0"}, {"Automate", "0", "0", "0", "0", "0"}};
        } else {
            tableau = ImportCSV.lireCSV(fichierSortie);
        }

        assert tableau != null;

        stats.forEach((duo, points) -> tableau[getCellule(duo.j1())][getCellule(duo.j2())] = String.valueOf(calculerMediane(points)));


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
            case "JoueurIADecentrV3" -> 4;
            default -> 5;
        };
    }

    public static int calculerMediane(Set<Integer> points) {
        int[] pointsTri = points.stream().mapToInt(Integer::intValue).sorted().toArray();
        int taille = pointsTri.length;
        if (taille % 2 == 0) {
            return (pointsTri[taille / 2 - 1] + pointsTri[taille / 2]) / 2;
        } else {
            return pointsTri[taille / 2];
        }
    }

}
